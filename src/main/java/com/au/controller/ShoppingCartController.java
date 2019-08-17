package com.au.controller;

import com.au.exception.CartEmptyException;
import com.au.exception.InvalidEntryException;
import com.au.exception.MaximumCostException;
import com.au.model.Basket;
import com.au.model.Category;
import com.au.model.UserSelection;
import com.au.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShoppingCartController {

	@Autowired
	ShoppingCartService shoppingCartService;

	@CrossOrigin(origins = "*")
	@RequestMapping(method= RequestMethod.POST,value="/addtoBasket", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public String addtoBasket(@RequestBody UserSelection selectedItem) throws MaximumCostException, InvalidEntryException {
		return shoppingCartService.addtoBasket(selectedItem);
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/generateAllItems")
	public List<Category> generateAllItems() {
		return shoppingCartService.generateAllItems();
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/getBasketStatus")
	public Basket getBasketStatus() throws CartEmptyException {

		return shoppingCartService.getBasketStatus();
	}
}
