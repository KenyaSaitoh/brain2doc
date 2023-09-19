package pro.kensait.brain2doc.openai;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pro.kensait.brain2doc.params.Parameter;

public class ApiClient {
    public static List<String> ask(String requestContent) {
        Parameter param = Parameter.getParameter();

        // HttpClientオブジェクトを生成する
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        Message message = new Message("user", requestContent);
        RequestBody requestBody = new RequestBody(param.getOpenaiModel(), List.of(message), 0.7F);
        String requestStr = getRequestJson(requestBody);
        // System.out.println(requestStr);

        // HttpRequestオブジェクトを生成する
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(param.getOpenaiURL()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + param.getOpenaiApikey())
                .POST(HttpRequest.BodyPublishers.ofString(requestStr))
                .build();

        // HttpRequestを送信し、HTTPサーバーを非同期で呼び出す
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        String responseStr = response.body();
        ResponseBody responseBody = getResponseBody(responseStr);
        List<String> responseContents = new ArrayList<>();
        responseBody.getChoices().forEach(choice -> {
            String responseContent = choice.getMessage().getContent();
            responseContents.add(responseContent);
        });
        return responseContents;
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

    private static ResponseBody getResponseBody(String responseStr) {
        ObjectMapper mapper = new ObjectMapper();
        ResponseBody responseBody = null;
        try {
            responseBody = mapper.readValue(responseStr, ResponseBody.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseBody;
    }
}