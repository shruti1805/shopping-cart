package com.au.exception;

/**
 *
 * Exception when the user tries to see status of shopping cart when it is empty
 *
 * @author Shruti Sinha Mahapatra
 */
public class CartEmptyException extends Exception
{
	public CartEmptyException(String message) {
        super(message);
    }
}
