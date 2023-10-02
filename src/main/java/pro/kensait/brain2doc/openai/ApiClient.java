package pro.kensait.brain2doc.openai;

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
import pro.kensait.brain2doc.exception.OpenAIInvalidAPIKeyException;
import pro.kensait.brain2doc.exception.OpenAIRateLimitExceededException;
import pro.kensait.brain2doc.exception.OpenAITokenLimitOverException;
import pro.kensait.brain2doc.exception.RetryCountOverException;
import pro.kensait.brain2doc.exception.TimeoutException;

public class ApiClient {
    private static final String CONTEXT_LENGTH_EXCEEDED_CODE = "context_length_exceeded";
    private static final String RATE_LIMIT_EXCEEDED_CODE = "rate_limit_exceeded";
    private static final String INVALID_API_KEY_CODE = "invalid_api_key";    
    
    public static ApiResult ask(String requestContent,
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

        Message message = new Message("user", requestContent);
        RequestBody requestBody = new RequestBody(openAiModel, List.of(message), 0.7F);
        String requestStr = getRequestJson(requestBody);
        // System.out.println(requestStr);

        // HttpRequestオブジェクトを生成する
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(openaiURL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openAiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestStr))
                .timeout(Duration.ofSeconds(requestTimeout))
                .build();

        // リトライなし
        if (retryCount == 0) {
            return sendRequest(client, request, retryCount, retryInterval);
        }

        // リトライあり
        int count = 0;
        while (count <= retryCount) {
            try {
                return sendRequest(client, request, retryCount, retryInterval);
            } catch (OpenAIClientException oce) {
                // APIキーが異なる場合は、即例外スロー
                if (Objects.equals(INVALID_API_KEY_CODE,
                        oce.getClientErrorBody().getError().getCode())) {
                    throw new OpenAIInvalidAPIKeyException(oce.getClientErrorBody());

                // トークンリミットオーバーの場合は、即例外スロー → その後分割実行
                } else if (Objects.equals(CONTEXT_LENGTH_EXCEEDED_CODE,
                        oce.getClientErrorBody().getError().getCode())) {
                    throw new OpenAITokenLimitOverException(oce.getClientErrorBody());

                // レートリミットオーバーの場合は、リトライ
                } else if (Objects.equals(RATE_LIMIT_EXCEEDED_CODE,
                        oce.getClientErrorBody().getError().getCode())) {
                    // TODO
                    //System.out.println("###############" + retryCount + "," + retryInterval);
                    count++;
                    if (count == retryCount) {
                        throw new OpenAIRateLimitExceededException(oce.getClientErrorBody());
                    }
                    sleep(retryInterval);
                }

            // タイムアウトの場合はリトライ
            } catch(TimeoutException te) {
                sleep(retryInterval);
                count++;
            }
        }
        throw new RetryCountOverException("リトライ回数オーバー");
    }

    private static ApiResult sendRequest(HttpClient client, HttpRequest request,
            int retryCount, int retryInterval) {
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

    private static void sleep(int retryInterval) {
        try {
            Thread.sleep(retryInterval * 1000);
        } catch(InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }

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