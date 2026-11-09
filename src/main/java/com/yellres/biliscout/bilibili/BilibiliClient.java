package com.yellres.biliscout.bilibili;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@Component
public class BilibiliClient {
    private final RestClient restClient;

    public BilibiliClient(
            RestClient.Builder builder,
                    @Value("${bilibili.sessdata}") String sessdata
    ) {
        if (!sessdata.isBlank()) {
            builder.defaultHeader("Cookie", "SESSDATA=" + sessdata);
        }
        this.restClient = builder.baseUrl("https://api.bilibili.com")
        .requestInterceptor(new RateLimitInterceptor())
        .build();
    }

    public JsonNode fetchNav() {
        return restClient.get().uri("/x/web-interface/nav").retrieve().body(JsonNode.class);
    }

    private String extractKey(String url) {
        String[] pathParts = url.split("/");
        String filename = pathParts[pathParts.length - 1];
        String[] nameParts = filename.split("\\.");
        String imgKey = nameParts[nameParts.length - 2];
        return imgKey;
    }

    public WbiKeys fetchWbiKeys() {
        JsonNode nav = fetchNav();
        String imgUrl = nav.get("data").get("wbi_img").get("img_url").stringValue();
        String subUrl = nav.get("data").get("wbi_img").get("sub_url").stringValue();
        return new WbiKeys(extractKey(imgUrl), extractKey(subUrl));
    }

    private Map<String, String> signParams(Map<String, String> params) {
        WbiKeys wbiKeys = fetchWbiKeys();
        Long now = System.currentTimeMillis() / 1000;
        return WbiSigner.sign(params, now, wbiKeys.imgKey(), wbiKeys.subKey());
    }

    public JsonNode search(String keyword) {
        Map<String, String> signedParams = signParams(Map.of("keyword", keyword, "search_type", "video", "page", "1"));

        return restClient.get().uri(
    "/x/web-interface/wbi/search/type"
        + "?keyword={keyword}&search_type={search_type}&page={page}"
        + "&wts={wts}&w_rid={w_rid}",
    signedParams
        ).header("Referer", "https://search.bilibili.com/").retrieve().body(JsonNode.class);
    }

}
