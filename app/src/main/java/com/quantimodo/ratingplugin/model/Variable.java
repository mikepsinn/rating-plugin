package com.quantimodo.ratingplugin.model;

/*
{
    "id": 1244,
    "name": "2C-E",
    "originalName": "2C-E",
    "category": "Medications",
    "unit": "mg",
    "sources": "Github,Med Helper",
    "minimumValue": 0,
    "maximumValue": "Infinity",
    "combinationOperation": "MEAN",
    "fillingValue": null,
    "joinWith": null,
    "joinedVariables": [],
    "onsetDelay": 0,
    "durationOfAction": 86400
}
*/

import java.util.Date;

public class Variable {
    public static final String COMBINE_SUM = "SUM";
    public static final String COMBINE_MEAN = "MEAN";

    public final long id;
    public final String name;
    public final String originalName;
    public final String parent;
    public final String category;
    public final String unit;
    public final String combinationOperation;

    public Date updated;
    public Date latestMeasurementTime;

    public Variable(long id, String originalName, String parent, String category, String unit, String combinationOperation) {
        this.id = id;
        this.name = originalName;
        this.originalName = originalName;
        this.parent = parent;
        this.category = category;
        this.unit = unit;
        if (combinationOperation.equals(COMBINE_SUM) || combinationOperation.equals(COMBINE_MEAN)) {
            this.combinationOperation = combinationOperation;
        } else {
            throw new IllegalArgumentException("combinationOperation must be " + COMBINE_SUM + " or " + COMBINE_MEAN);
        }
    }

    public Variable(long id, String name, String originalName, String parent, String category, String unit, String combinationOperation) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.parent = parent;
        this.category = category;
        this.unit = unit;
        if (combinationOperation.equals(COMBINE_SUM) || combinationOperation.equals(COMBINE_MEAN)) {
            this.combinationOperation = combinationOperation;
        } else {
            throw new IllegalArgumentException("combinationOperation must be " + COMBINE_SUM + " or " + COMBINE_MEAN);
        }
    }
}
