package com.yellres.biliscout.bilibili;

import java.io.IOException;
import java.io.InterruptedIOException;
import org.springframework.http.HttpRequest;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RateLimitInterceptor implements ClientHttpRequestInterceptor {

    @Override 
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
        ClientHttpRequestExecution execution) throws IOException {
        waitForTurn();
        return execution.execute(request, body);
    }
    private long lastTime = 0 ;

    static long waitMillis(long now, long lastTime, long gap) {
        return Math.max(gap - now + lastTime, 0);
    }

    private synchronized void waitForTurn() throws InterruptedIOException{
        long gap = ThreadLocalRandom.current().nextLong(1000, 3001);
        long timeNeedSleep = waitMillis(System.currentTimeMillis(), lastTime, gap);
        try {
            Thread.sleep(timeNeedSleep);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("限速等待被中断");
        }
        lastTime = System.currentTimeMillis();
    }
}
