package com.au.constants;

public enum AllMessages {
    INVALID("Invalid entry for shopping. Please enter Category: 1-20 and Item:1-10"),
    ALREADY_EXISTS("Item already selected for this category"),
    REACHED_THRESHOLD("Exceeding shopping threshold - you can shop upto $50"),
    EMPTY_CART("Your shopping cart is empty"),
    INCORRECT_INPUT("Request body - incorrect"),
    SUCCESS("Item added to the basket"),
    ;

    private final String message;

    private AllMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
