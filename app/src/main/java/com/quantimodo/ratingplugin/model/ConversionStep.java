package com.quantimodo.ratingplugin.model;

/*
{
    {
        "operation": "MULTIPLY",
        "value": 1000
    }
}
*/

public class ConversionStep {
    public static final String OPERATION_MULTIPLY = "MULTIPLY";
    public static final String OPERATION_SUM = "SUM";

    public final String operation;
    public final double value;

    public ConversionStep(String operation, double value) {
        this.operation = operation;
        this.value = value;
    }

}
