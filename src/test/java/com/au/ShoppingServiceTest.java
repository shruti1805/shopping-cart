package com.au;

import com.au.constants.AllMessages;
import com.au.exception.CartEmptyException;
import com.au.exception.InvalidEntryException;
import com.au.exception.MaximumCostException;
import com.au.model.Basket;
import com.au.model.Category;
import com.au.model.UserSelection;
import com.au.service.ShoppingCartService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class ShoppingServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ShoppingCartService shoppingCartService;

    @Value("${max.categories}")
    private String maxCategories;

    @Value("{max.items}")
    private String maxItems;

    final ShoppingCartService mock = Mockito.spy(ShoppingCartService.getInstance());
    List<Category>  categories= new ArrayList();


    @Before
    public void initial()
    {
        Mockito.doReturn(21).when(mock).getMaxCategories();
        Mockito.doReturn(11).when(mock).getMaxItems();
        Mockito.doReturn(50).when(mock).getBasketThreshold();
    }

    // Negative test - CartEmptyException
    @Test
    public void testGetBasketStatusEmpty() throws Exception{
        try {
            mock.getBasketStatus();
        }catch (CartEmptyException e){
            assertEquals("Your shopping cart is empty", e.getMessage());
        }
    }

    @Test
    public void testEmptyCart() throws Exception{
        assertEquals(0, categories.size());

        mock.createCartIfEmpty();

        assertEquals(0, categories.size());
    }

    @Test
    public void testGenerateAllItems() throws Exception{
        categories = mock.generateAllItems();
        Mockito.doReturn(categories).when(mock).generateAllItems();

        assertEquals(20, categories.size());
    }

    // Positive scenario
    @Test
    public void testGetItemCoordinates() throws Exception{
        UserSelection userSelection = new UserSelection("Category11", "Item1");
        mock.addtoBasket(userSelection);
        assertEquals("Category11:Item1 ", mock.getBasketStatus().getItem_coordinates());
    }

    // Boundary Testing (first element)
    @Test
    public void testCheckFirstItem() throws Exception{
        UserSelection userSelection = new UserSelection("Category1", "Item1");
        setBasketCost(0);
        assertEquals(AllMessages.SUCCESS.getMessage(),mock.addtoBasket(userSelection));
    }

    // Boundary Testing (last element)
    @Test
    public void testCheckLastItem() throws Exception{
        UserSelection userSelection = new UserSelection("Category20", "Item10");
        setBasketCost(0);
        assertEquals(AllMessages.SUCCESS.getMessage(),mock.addtoBasket(userSelection));
    }

    @Test
    public void testAddtoBasket() throws Exception{
        UserSelection userSelection = new UserSelection("Category2", "Item3");

        setBasketCost(0);
        mock.addtoBasket(userSelection);
    }

    // Testing InvalidEntryException - Dulplicate Item
    @Test
    public void testCheckAlreadyExists() throws Exception{
        UserSelection userSelection = new UserSelection("Category7", "Item3");
        setBasketCost(0);
        mock.addtoBasket(userSelection);

        try {
            mock.checkAlreadyExists(userSelection);
        }catch (InvalidEntryException e){
            
            assertEquals(AllMessages.ALREADY_EXISTS.getMessage(), e.getMessage());
        }
    }

    // Testing InvalidEntryException - Invalid entry
    @Test
    public void testCheckInvalidCategory() throws Exception{
        try {
            UserSelection userSelection = new UserSelection("Category31", "Item2");
            setBasketCost(0);
            mock.addtoBasket(userSelection);
        }catch (InvalidEntryException e){
            assertEquals(AllMessages.INVALID.getMessage(), e.getMessage());
        }
    }

    // Testing InvalidEntryException - Invalid entry
    @Test
    public void testCheckInvalidItem() throws Exception{
        try {
            UserSelection userSelection = new UserSelection("Category13", "Item29");
            setBasketCost(0);
            mock.addtoBasket(userSelection);
        }catch (InvalidEntryException e){
            assertEquals(AllMessages.INVALID.getMessage(), e.getMessage());
        }
    }

    // Testing InvalidEntryException - Request body
    @Test
    public void checkUserInput() throws Exception{
        UserSelection userSelection = new UserSelection("Category31", null);

        try {
            setBasketCost(0);
            mock.addtoBasket(userSelection);
        }catch (InvalidEntryException e){
            assertEquals(AllMessages.INCORRECT_INPUT.getMessage(), e.getMessage());
        }
    }

    // Testing MaximumCostException
    @Test
    public void testCheckShoppingThreshold() throws Exception{
        setBasketCost(50);
        UserSelection userSelection = new UserSelection("Category4", "Item5");

        try {
            mock.addtoBasket(userSelection);
        }catch (MaximumCostException e){
            assertEquals(AllMessages.REACHED_THRESHOLD.getMessage(), e.getMessage());
        }
    }

    public void setBasketCost(int cost){
        Basket basket = new Basket();
        basket.setTotal_cost(cost);
    }

}
