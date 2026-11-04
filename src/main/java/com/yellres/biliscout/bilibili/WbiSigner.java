package com.yellres.biliscout.bilibili;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.util.DigestUtils;

public class WbiSigner {
    private static final int[] MIXIN_KEY_ENC_TAB = {
            46, 47, 18, 2, 53, 8, 23, 32, 15, 50, 10, 31, 58, 3, 45, 35,
            27, 43, 5, 49, 33, 9, 42, 19, 29, 28, 14, 39, 12, 38, 41, 13,
            37, 48, 7, 16, 24, 55, 40, 61, 26, 17, 0, 1, 60, 51, 30, 4,
            22, 25, 54, 21, 56, 59, 6, 63, 57, 62, 11, 36, 20, 34, 44, 52
    };

    static String mixinKey(String imgKey, String subKey) {
        String unionKey = imgKey + subKey;
        StringBuilder res = new StringBuilder("");
        for (int i = 0; i < MIXIN_KEY_ENC_TAB.length; i++) {
            int c = MIXIN_KEY_ENC_TAB[i];
            res.append(unionKey.charAt(c));
        }

        return res.substring(0, 32);
    }

    static Map<String, String> sign(Map<String, String> params, long wts, String imgKey, String subKey) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>(params);

        treeMap.put("wts", String.valueOf(wts));
        String salt = mixinKey(imgKey, subKey);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : treeMap.entrySet()) {
            String key = e.getKey();
            String value = e.getValue();
            String finalValue = value.replaceAll("[!'()*]", "");
            finalValue = URLEncoder.encode(finalValue, StandardCharsets.UTF_8).replace("+", "%20");

            sb.append(key);
            sb.append("=");
            sb.append(finalValue);
            sb.append("&");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(salt);

        treeMap.put("w_rid", DigestUtils.md5DigestAsHex(sb.toString().getBytes(StandardCharsets.UTF_8)));

        return treeMap;
    }

}