package com.au.model;

public class Item {
    private String id;

    private int price;
    private int shipping_cost;
    private int rating;

    public Item(int id){
        setPrice();
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = "Item" +id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice() {
        this.price = getRandomIntegerBetweenRange(1,20);
        if(this.price>10)
            setShipping_cost(4, 5);
        else
            setShipping_cost(2, 3);
    }

    public int getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(int min, int max) {
        this.shipping_cost = getRandomIntegerBetweenRange(min,max);

        if(this.shipping_cost>3)
            setRating(4, 5);
        else
            setRating(1, 3);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int min, int max) {
        this.rating = getRandomIntegerBetweenRange(min,max);
    }

    public static int getRandomIntegerBetweenRange(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
