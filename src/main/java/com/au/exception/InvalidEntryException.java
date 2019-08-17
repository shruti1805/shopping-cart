package com.au.exception;

/**
 *
 * Exception when the user an invalid item is entered
 *
 * @author Shruti Sinha Mahapatra
 */
public class InvalidEntryException extends Exception
{
	public InvalidEntryException(String message) {
        super(message);
    }
}
