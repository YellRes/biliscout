package com.yellres.biliscout.bilibili;

import java.io.InterruptedIOException;
import java.util.function.LongConsumer;
import java.util.function.Supplier;

import org.springframework.web.client.HttpClientErrorException;

import com.yellres.biliscout.exception.TooManyRequestException;

public class Retry {

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("超时");
        }
    }

    public static <T> T retry(Supplier<T> action, int maxAttempts) {
        return Retry.retry(action, maxAttempts, Retry::sleep);
    }

    public static <T> T retry(Supplier<T> action, int maxAttempts, LongConsumer sleeper) {

        for (int i = 0; i < maxAttempts; i++) {
            try {
                return action.get();
            } catch (HttpClientErrorException e) {

                if (e.getStatusCode().value() == 412)
                    if (i == maxAttempts - 1)
                        break;
                    else
                        sleeper.accept(Backoff.backoffMillis(i + 1));
                else
                    throw e;
            }
        }

        throw new TooManyRequestException(maxAttempts);

    }
}
