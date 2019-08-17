package com.au.service;

import com.au.constants.AllMessages;
import com.au.exception.CartEmptyException;
import com.au.exception.InvalidEntryException;
import com.au.exception.MaximumCostException;
import com.au.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 
 * ShoppingCartService provides logical retrieval of items based on categories
 * 
 * @author Shruti Mahapatra
 *
 */

@Service
public class ShoppingCartService {

	private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartService.class);

	@Value("${max.categories}")
	private String maxCategories;

	@Value("${max.items}")
	private String maxItems;

	@Value("${basket.threshold}")
	private String basket_threshold;

	static List<Category> categories = new ArrayList<> ();
	static Basket basket = new Basket();

	public int getMaxItems(){
		return Integer.parseInt(maxItems);
	}

	public int getMaxCategories(){
		return Integer.parseInt(maxCategories);
	}

	public int getBasketThreshold(){
		return Integer.parseInt(basket_threshold);
	}

	public List<Category> generateAllItems(){
		if(categories.isEmpty()) {
			LOG.info("Shopping Cart creation started");
			IntStream.range(1, getMaxCategories()).forEach(id -> {

				Category category = new Category();
				category.setId(id);
				List<Item> items = new ArrayList<>();
				IntStream.range(1, getMaxItems()).
						forEach( iid->{
							items.add(new Item(iid));
						});

				category.setItem(items);
				categories.add(category);
			});
			LOG.info("Shopping Cart created successfully");
		}
		return categories;
	}

	public Basket getBasketStatus()throws CartEmptyException{
		if(categories.isEmpty()) {
			LOG.error("User searching item in empty cart");
			throw new CartEmptyException(AllMessages.EMPTY_CART.getMessage());
		}
		else
			return basket;
	}

	public String addtoBasket(UserSelection userSelection)
			throws MaximumCostException, InvalidEntryException {

		createCartIfEmpty();
		checkUserInput(userSelection);
		checkAlreadyExists(userSelection);
		setBasket(userSelection);
		checkInvalidEntry(userSelection);

		return checkShoppingThreshold();
	}

	public void createCartIfEmpty()
	{
		if(categories.isEmpty())
			generateAllItems();
	}

	public void checkUserInput(UserSelection userSelection) throws InvalidEntryException {
		if(userSelection.getItem()==null || userSelection.getCategory()==null)
			throw new InvalidEntryException(AllMessages.INCORRECT_INPUT.getMessage());
	}

	public void checkAlreadyExists(UserSelection userSelection) throws InvalidEntryException {
		if(basket.getItem_coordinates().contains(userSelection.getCategory()+":")){
			LOG.error("Check for User Input ");
			throw new InvalidEntryException(AllMessages.ALREADY_EXISTS.getMessage());
		}
	}

	public void setBasket(UserSelection userSelection){
		categories.stream().forEach(b->{
			if(userSelection.getCategory().equals(b.getId())){
				b.getItem().stream().forEach(i->{
					if(userSelection.getItem().equals(i.getId())){
						basket.setItem_coordinates(b.getId()+":"+i.getId() +" " + basket.getItem_coordinates());
						basket.setTotal_cost(i.getShipping_cost() + i.getPrice() + basket.getTotal_cost());
						basket.setTotal_rating(i.getRating() + basket.getTotal_rating());
						LOG.info("Item added successfully");
					}
				});
			}
		});
	}

	public void checkInvalidEntry(UserSelection userSelection) throws InvalidEntryException {
		if(!basket.getItem_coordinates().contains(userSelection.getCategory()) ||
				!basket.getItem_coordinates().contains(userSelection.getItem()) ){
			LOG.error("Check for User Input ");
			throw new InvalidEntryException(AllMessages.INVALID.getMessage());
		}
	}

	public String checkShoppingThreshold() throws MaximumCostException {
		List<Basket> baskets = new ArrayList<>();
		if(basket.getTotal_cost() <= getBasketThreshold()) {
			baskets.add(basket);
			return AllMessages.SUCCESS.getMessage();
		}
		else {
			LOG.debug("Basket total cost is more than threshold");
			throw new MaximumCostException(AllMessages.REACHED_THRESHOLD.getMessage());
		}
	}

}
