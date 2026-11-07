package com.yellres.biliscout.bilibili;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RateLimitInterceptorTest {
    @Test 
    void testWaitMillis() {
        assertEquals(1000, RateLimitInterceptor.waitMillis(10000, 9000, 2000));
        assertEquals(0, RateLimitInterceptor.waitMillis(10000, 7000, 2000));
        assertEquals(0, RateLimitInterceptor.waitMillis(10000, 0, 2000));
    }   
}