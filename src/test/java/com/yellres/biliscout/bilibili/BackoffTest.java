package com.yellres.biliscout.bilibili;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BackoffTest {
    @Test
    void testBackoff() {
        assertEquals(5000, Backoff.backoffMillis(1));
        assertEquals(20000, Backoff.backoffMillis(3));

        assertEquals(60000, Backoff.backoffMillis(5));
        assertEquals(60000, Backoff.backoffMillis(70));

    }
}
