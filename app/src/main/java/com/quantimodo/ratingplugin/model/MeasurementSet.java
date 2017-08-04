package com.quantimodo.ratingplugin.model;

import java.util.ArrayList;

public class MeasurementSet {
    public static final String COMBINE_SUM = "SUM";
    public static final String COMBINE_MEAN = "MEAN";

    public String name;
    public String parent;
    public String category;
    public String unit;
    public String combinationOperation;
    public String source;

    public ArrayList<Measurement> measurements;

    // Do not use this constructor
    private MeasurementSet() {
    }

    public MeasurementSet(Variable variable, String source) {
        this.measurements = new ArrayList<Measurement>();

        this.name = variable.originalName;
        this.parent = variable.parent;
        this.category = variable.category;
        this.unit = variable.unit;
        this.combinationOperation = variable.combinationOperation;
        this.source = source;
    }

    public MeasurementSet(Variable variable, String source, ArrayList<Measurement> measurements) {
        this.measurements = measurements;

        this.name = variable.originalName;
        this.parent = variable.parent;
        this.category = variable.category;
        this.unit = variable.unit;
        this.combinationOperation = variable.combinationOperation;
        this.source = source;
    }

    public MeasurementSet(String name, String parent, String category, String unit, String combinationOperation, String source) {
        this.measurements = new ArrayList<Measurement>();

        this.name = name;
        this.parent = parent;
        this.category = category;
        this.unit = unit;
        this.combinationOperation = combinationOperation;
        this.source = source;
    }

    public MeasurementSet(String name, String parent, String category, String unit, String combinationOperation, String source, ArrayList<Measurement> measurements) {
        this.measurements = measurements;

        this.name = name;
        this.parent = parent;
        this.category = category;
        this.unit = unit;
        this.combinationOperation = combinationOperation;
        this.source = source;
    }
}
