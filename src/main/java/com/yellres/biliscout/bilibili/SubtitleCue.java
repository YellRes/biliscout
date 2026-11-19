package com.yellres.biliscout.bilibili;

public record SubtitleCue(double startSecond, double endSecond, String text) {
    public long startMs() {
        return Math.round(startSecond * 1000);
    }

    public long endMs() {
        return Math.round(endSecond * 1000);
    }
}
