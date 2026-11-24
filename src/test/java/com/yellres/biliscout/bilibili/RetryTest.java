package com.yellres.biliscout.bilibili;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.yellres.biliscout.exception.TooManyRequestException;

public class RetryTest {
    @Test
    void test412() {
        List<Long> waits = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();
        assertThrows(TooManyRequestException.class, () -> {
            Retry.retry(() -> {
                count.incrementAndGet();
                throw new HttpClientErrorException(HttpStatus.PRECONDITION_FAILED);
            }, 3, ms -> waits.add(ms));
        });

        assertEquals(3, count.get());
        assertEquals(List.of(5000L, 10000L), waits);
    }

    @Test
    void test404() {
        AtomicInteger count = new AtomicInteger();
        assertThrows(HttpClientErrorException.class, () -> {
            Retry.retry(() -> {
                count.incrementAndGet();
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }, 3, ms -> {
            });
        });

        assertEquals(1, count.get());
    }

    @Test
    void testLastTimeSuccess() {
        AtomicInteger count = new AtomicInteger();
        assertEquals("ok", Retry.retry(() -> {
            if (count.incrementAndGet() == 3)
                return "ok";
            throw new HttpClientErrorException(HttpStatus.PRECONDITION_FAILED);
        }, 3, ms -> {
        }));

        assertEquals(3, count.get());
    }
}
