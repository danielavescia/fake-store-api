package com.db.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {

    private Float rate;
    
    private Integer count;

    public Rating(Float rate, Integer count) {
        this.rate = rate;
        this.count = count;
    }

    public Rating(){

    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
