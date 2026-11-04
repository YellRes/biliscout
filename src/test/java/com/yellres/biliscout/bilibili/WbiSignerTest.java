package com.yellres.biliscout.bilibili;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

class WbiSignerTest {

    @Test
    void testWbiSigner() {
        assertEquals(
                "ea1db124af3c7062474693fa704f4ff8",
                WbiSigner.mixinKey("7cd084941338484aae1ad9425b84077c", "4932caff0ff746eab6f01bf08b70ac45"));
    }

    @Test
    void testWbiSigner2() {
        assertEquals(
                "8f6f2b5b3d485fe1886cec6a0be8c5d4",
                WbiSigner.sign(Map.of("foo", "114", "bar", "514", "zab", "1919810"), 1702204169L,
                        "7cd084941338484aae1ad9425b84077c",
                        "4932caff0ff746eab6f01bf08b70ac45").get("w_rid"));
    }

}