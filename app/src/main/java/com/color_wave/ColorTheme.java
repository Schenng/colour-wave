package com.color_wave;

public class ColorTheme {
    private String labelStr;
    private String queryStr;


    public ColorTheme (String label, String query) {
        queryStr = query;
        labelStr = label;
    }

    public String getLabel(){
        return labelStr;
    }

    public String getQuery(){
        return queryStr;
    }
}
