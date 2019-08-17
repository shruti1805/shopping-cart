package com.au.model;

import java.util.List;

public class Category {
    public Category(){}

    private String id;
    List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = "Category" +id;
    }
}
