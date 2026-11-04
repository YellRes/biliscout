package com.yellres.biliscout.bilibili;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Component
public class BilibiliClient {
    private final RestClient restClient;

    public BilibiliClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://api.bilibili.com").build();
    }

    public JsonNode fetchNav() {
        return restClient.get().uri("/x/web-interface/nav").retrieve().body(JsonNode.class);
    }
}
