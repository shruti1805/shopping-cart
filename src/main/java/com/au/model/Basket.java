package com.au.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Basket {

    private String item_coordinates="";
    private static int total_cost=0;
    private static int total_rating = 0;

    public Basket(){}

    public String getItem_coordinates() {
        return item_coordinates;
    }

    public void setItem_coordinates(String item_coordinates) {
        this.item_coordinates = item_coordinates;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public int getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(int total_rating) {
        this.total_rating = total_rating;
    }

}
