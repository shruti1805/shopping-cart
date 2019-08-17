package com.au;

/**
 * The executable class for the Shopping Cart.
 * @author Shruti Sinha Mahapatra
 *
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppingCart {

	/**
	 * The main method to be executed in order to start the Shopping Cart.
	 * 
	 * @param args
	 */

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ShoppingCart.class, args);
	}
}
