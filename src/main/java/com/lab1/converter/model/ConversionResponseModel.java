package com.lab1.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversionResponseModel {

    @JsonProperty("result")
    private String result;

    @JsonProperty("conversion_result")
    private double conversionResult;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getConversionResult() {
        return conversionResult;
    }

    public void setConversionResult(double conversionResult) {
        this.conversionResult = conversionResult;
    }
}
