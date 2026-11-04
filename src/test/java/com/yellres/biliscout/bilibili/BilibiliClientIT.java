package com.yellres.biliscout.bilibili;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

public class BilibiliClientIT {
    @Test
    void testClient() {
        BilibiliClient client = new BilibiliClient(RestClient.builder());
        JsonNode json = client.fetchNav();
        String imgUrl = json.get("data").get("wbi_img").get("img_url").stringValue();
        String[] pathParts = imgUrl.split("/");
        String filename = pathParts[pathParts.length - 1];
        String[] nameParts = filename.split("\\.");
        String imgKey = nameParts[nameParts.length - 2];
        System.out.println(imgKey);
        // System.out.println(json.get("data").get("wbi_img").get("img_url").stringValue());
        // System.out.println(json.get("data").get("wbi_img").get("sub_url").stringValue());
    }
}
