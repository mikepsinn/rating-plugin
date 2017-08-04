package com.quantimodo.ratingplugin.model;

/*
{
    "name": "Kilometers",
    "abbreviatedName": "km",
    "category": "Distance",
    "minimum": "-Infinity",
    "maximum": "Infinity",
    "conversionSteps": [
        {
            "operation": "MULTIPLY",
            "value": 1000
        }
    ]
}
*/

import java.util.ArrayList;

public class Unit {
    public static final String COMBINE_SUM = "SUM";
    public static final String COMBINE_MEAN = "MEAN";

    public final String name;
    public final String abbreviatedName;
    public final String category;
    public final String minimum;
    public final String maximum;
    public final ArrayList<ConversionStep> conversionSteps;

    public Unit(String name, String abbreviatedName, String category, String minimum, String maximum, ArrayList<ConversionStep> conversionSteps) {
        this.name = name;
        this.abbreviatedName = abbreviatedName;
        this.category = category;
        this.minimum = minimum;
        this.maximum = maximum;
        this.conversionSteps = conversionSteps;
    }
}
