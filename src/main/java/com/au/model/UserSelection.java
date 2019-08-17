package com.au.model;

public class UserSelection {

    public UserSelection(String category, String item) {
        this.category = category;
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public String getItem() {
        return item;
    }

    private String category;
    private String item;
}
