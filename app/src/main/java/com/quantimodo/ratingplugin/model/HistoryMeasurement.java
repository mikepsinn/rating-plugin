package com.quantimodo.ratingplugin.model;

import java.util.Date;

public class HistoryMeasurement {
    final String source;
    final String variable;
    final Date timestamp;
    final double value;
    final String unit;

    public HistoryMeasurement(String source, String variable, Date timestamp, double value, String unit) {
        this.source = source;
        this.variable = variable;
        this.timestamp = timestamp;
        this.value = value;
        this.unit = unit;
    }
}
