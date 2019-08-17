package com.au.exception;

/**
 *
 * Exception when the user exceeds shopping threshold
 *
 * @author Shruti Sinha Mahapatra
 */
public class MaximumCostException extends Exception
{
	public MaximumCostException(String message) {
        super(message);
    }
}
