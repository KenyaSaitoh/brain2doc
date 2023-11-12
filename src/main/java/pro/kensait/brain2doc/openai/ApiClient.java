package pro.kensait.brain2doc.openai;

import static pro.kensait.brain2doc.common.Util.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Builder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pro.kensait.brain2doc.exception.OpenAIClientException;
import pro.kensait.brain2doc.exception.OpenAIInsufficientQuotaException;
import pro.kensait.brain2doc.exception.OpenAIInvalidAPIKeyException;
import pro.kensait.brain2doc.exception.OpenAIRateLimitExceededException;
import pro.kensait.brain2doc.exception.OpenAITokenLimitOverException;
import pro.kensait.brain2doc.exception.RetryCountOverException;
import pro.kensait.brain2doc.exception.TimeoutException;

;public class ApiClient {
    // 定数
    private static final String INVALID_API_KEY_CODE = "invalid_api_key";
    private static final String INSUFFICIENT_QUOTA_CODE = "insufficient_quota";
    private static final String CONTEXT_LENGTH_EXCEEDED_CODE = "context_length_exceeded";
    private static final String RATE_LIMIT_EXCEEDED_CODE = "rate_limit_exceeded";

    /*
     * OpenAIのAPIを呼び出す（例外の種類に応じてリトライする）
     */
    public static ApiResult ask(
            String systemMessageStr,
            String assistantMessageStr,
            String userMessageContent,
            float temparature,
            String openaiURL,
            String openAiModel,
            String openAiApiKey,
            String proxyURL,
            int connectTimeout,
            int requestTimeout,
            int retryCount,
            int retryInterval) {

        // HttpClientオブジェクトを生成する
        HttpClient client = createHttpClient(proxyURL, connectTimeout);

        Message systemMessage = new Message("system", systemMessageStr);
        Message assistantMessage = new Message("assistant", assistantMessageStr);
        Message userMessage = new Message("user", userMessageContent);

        RequestBody requestBody = new RequestBody(
                openAiModel,
                List.of(systemMessage, assistantMessage, userMessage),
                temparature);

        String requestStr = getRequestJson(requestBody);

        // HttpRequestオブジェクトを生成する
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(openaiURL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openAiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestStr))
                .timeout(Duration.ofSeconds(requestTimeout))
                .build();

        int count = 0;
        while (true) {
            try {
                return sendRequest(client, request);
            } catch (OpenAIClientException oe) {
                // APIキーが異なる場合は、即例外スロー → その後プログラム停止
                if (Objects.equals(INVALID_API_KEY_CODE,
                        oe.getClientErrorBody().getError().getCode())) {
                    throw new OpenAIInvalidAPIKeyException(oe.getClientErrorBody());

                // クォータ不足の場合は、即例外スロー → その後プログラム停止
                } else if (Objects.equals(INSUFFICIENT_QUOTA_CODE,
                        oe.getClientErrorBody().getError().getCode())) {
                    throw new OpenAIInsufficientQuotaException(oe.getClientErrorBody());

                // トークンリミットオーバーの場合は、即例外スロー → その後分割実行
                } else if (Objects.equals(CONTEXT_LENGTH_EXCEEDED_CODE,
                        oe.getClientErrorBody().getError().getCode())) {
                    throw new OpenAITokenLimitOverException(oe.getClientErrorBody());

                // レートリミットオーバーの場合は、即例外スロー → その後分割実行
                } else if (Objects.equals(RATE_LIMIT_EXCEEDED_CODE,
                        oe.getClientErrorBody().getError().getCode())) {
                    throw new OpenAIRateLimitExceededException(oe.getClientErrorBody());
                }

                // それ以外の場合はそのまま例外をスロー
                throw oe;

            } catch(TimeoutException te) {
                System.out.print("[TIME_OUT]");
                if (count == retryCount) break;
                // タイムアウトの場合はリトライ
                count++;
                sleepAWhile(retryInterval);
            }
        }
        throw new RetryCountOverException("リトライ回数オーバー");
    }

    /*
     * OpenAIのAPIを実際に呼び出す
     */
    private static ApiResult sendRequest(HttpClient client, HttpRequest request) {
        // HttpRequestを送信し、HTTPサーバーを同期で呼び出す
        HttpResponse<String> response = null;
        LocalTime startTime = null;
        LocalTime finishTime = null;
        try {
            startTime = LocalTime.now();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            finishTime = LocalTime.now();
        } catch (HttpTimeoutException hte) { // タイムアウト
            throw new TimeoutException(
                    "OpenAIサービス呼び出しでタイムアウトが発生しました", hte);
        } catch (IOException | InterruptedException ex) {
            throw new TimeoutException(ex);
        }

        Duration duration = Duration.between(startTime, finishTime);
        long interval = duration.getSeconds();

        int statusCode = response.statusCode();
        String responseStr = response.body();

        if (200 <= statusCode && statusCode < 300) {
            SuccessResponseBody responseBody = getResponseBody(SuccessResponseBody.class,
                    responseStr);
            return new ApiResult(responseBody, interval);
        } else if (400 <= statusCode && statusCode < 500) {
            // RetryCountOverやRateLimitExceededを含むOpenAIClientExceptionを返す
            ClientErrorBody responseBody = getResponseBody(ClientErrorBody.class,
                    responseStr);
            throw new OpenAIClientException(responseBody);
        } else {
            // ステータスその他（500番台など）
            throw new RuntimeException();
        }
    }
    
    /*
     * リクエストJSONを文字列に変換する
     */
    private static String getRequestJson(RequestBody requestBody) {
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /*
     * レスポンスJSONをJavaオブジェクトに変換する
     */
    private static <T> T getResponseBody(Class<T> clazz, String responseStr) {
        ObjectMapper mapper = new ObjectMapper();
        T responseBody = null;
        try {
            responseBody = mapper.readValue(responseStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseBody;
    }

    /*
     * HttpClientを生成して返す（プロキシの設定があった場合はそれも加味する）
     */
    private static HttpClient createHttpClient(String proxyURL, int connectTimeout) {
        Builder builder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(connectTimeout));

        if (proxyURL != null && ! proxyURL.isEmpty()) {
            URI proxyUri;
            try {
                proxyUri = new URI(proxyURL);
            } catch (URISyntaxException ue) {
                throw new RuntimeException(ue);
            }
            InetSocketAddress proxyAddress =
                    new InetSocketAddress(proxyUri.getHost(), proxyUri.getPort());
            ProxySelector proxySelector =
                    new StaticProxySelector(Proxy.Type.HTTP, proxyAddress);
            return builder.proxy(proxySelector).build();
        }
        return builder.build();
    }
}