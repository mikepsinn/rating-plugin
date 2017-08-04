package com.quantimodo.ratingplugin.model;

public class Measurement {
    public long timestamp;
    public double value;
    public int duration;

    private Measurement() {
    }

    public Measurement(long epochTimestamp, double value, int durationSeconds) {
        this.timestamp = epochTimestamp;
        this.value = value;
        this.duration = durationSeconds;
    }

    public Measurement(long epochTimestamp, double value) {
        this.timestamp = epochTimestamp;
        this.value = value;
        this.duration = -1;
    }
}
