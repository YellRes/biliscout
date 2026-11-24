package com.yellres.biliscout.bilibili;

public class Backoff {
    private static final long BASE_MILLIS = 5000;
    private static final long MAX_MILLIS = 60000;

    public static long backoffMillis(int attempt) {
        long res = BASE_MILLIS;
        int i = 1;
        while (i < attempt && res < MAX_MILLIS) {
            res = res * 2;
            i++;
        }

        return Math.min(res, MAX_MILLIS);
    }
}
