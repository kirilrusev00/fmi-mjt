package bg.sofia.uni.fmi.mjt.shopping;

import bg.sofia.uni.fmi.mjt.shopping.item.Apple;
import bg.sofia.uni.fmi.mjt.shopping.item.Chocolate;
import bg.sofia.uni.fmi.mjt.shopping.item.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class ShoppingCartTest {
    private static final double TEST_APPLE_PRICE = 5d;
    private static final double TEST_CHOCOLATE_PRICE = 10d;
    private static final double EXPECTED_SUM = 20d;

    private ShoppingCart listShoppingCart;
    private ShoppingCart mapShoppingCart;
    private Item apple;
    private Item chocolate;

    @Before
    public void beforeClass() {
        listShoppingCart = new ListShoppingCart();
        mapShoppingCart = new MapShoppingCart();
        apple = new Apple("apple", "green", TEST_APPLE_PRICE);
        chocolate = new Chocolate("chocolate", "brown", TEST_CHOCOLATE_PRICE);
    }

    @Test
    public void testGetUniqueItemsInListIsAnItemIncluded() {
        mapShoppingCart.addItem(apple);
        assertTrue(mapShoppingCart.getUniqueItems().contains(apple));
    }

    @Test
    public void testGetUniqueItemsInMapIsAnItemIncluded() {
        listShoppingCart.addItem(apple);
        assertTrue(listShoppingCart.getUniqueItems().contains(apple));
    }

    @Test
    public void testGetUniqueItemsInListIsAnItemIncluded2() throws ItemNotFoundException {
        listShoppingCart.addItem(apple);
        listShoppingCart.removeItem(apple);
        assertFalse(listShoppingCart.getUniqueItems().contains(apple));
    }

    @Test
    public void testGetUniqueItemsInMapIsAnItemIncluded2() throws ItemNotFoundException {
        mapShoppingCart.addItem(apple);
        //mapShoppingCart.addItem(apple);
        mapShoppingCart.removeItem(apple);
        assertFalse(mapShoppingCart.getUniqueItems().contains(apple));
    }

    @Test
    public void testGetUniqueItemsInListAreDuplicatesIncluded() {
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(apple);
        Collection<Item> collection = listShoppingCart.getUniqueItems();
        int counter = 0;
        for (Item item : collection) {
            if (item.equals(apple)) {
                counter++;
            }
        }
        assertTrue(counter == 1);
    }

    @Test
    public void testGetUniqueItemsInMapAreDuplicatesIncluded() {
        mapShoppingCart.addItem(apple);
        mapShoppingCart.addItem(apple);
        Collection<Item> collection = mapShoppingCart.getUniqueItems();
        int counter = 0;
        for (Item item : collection) {
            if (item.equals(apple)) {
                counter++;
            }
        }
        assertTrue(counter == 1);
    }

    @Test
    public void testAddItemToList() {
        listShoppingCart.addItem(apple);
        assertTrue(listShoppingCart.getUniqueItems().contains(apple));
    }

    @Test
    public void testAddItemToMap() {
        mapShoppingCart.addItem(apple);
        assertTrue(mapShoppingCart.getUniqueItems().contains(apple));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemToListIfItemNameIsNull() {
        Item nullApple = new Apple(null, "green", TEST_APPLE_PRICE);
        listShoppingCart.addItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemToMapIfItemNameIsNull() {
        Item nullApple = new Apple(null, "green", TEST_APPLE_PRICE);
        mapShoppingCart.addItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemToListIfItemDescriptionIsNull() {
        Item nullApple = new Apple("apple", null, TEST_APPLE_PRICE);
        listShoppingCart.addItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemToMapIfItemDescriptionIsNull() {
        Item nullApple = new Apple("apple", null, TEST_APPLE_PRICE);
        mapShoppingCart.addItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemToListIfItemIsNull() {
        Item nullApple = null;
        listShoppingCart.addItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemToMapIfItemIsNull() {
        Item nullApple = null;
        mapShoppingCart.addItem(nullApple);
    }

    @Test
    public void testRemoveItemFromListWhenIncludedOnce() throws ItemNotFoundException {
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(chocolate);
        listShoppingCart.removeItem(apple);
        assertFalse(listShoppingCart.getUniqueItems().contains(apple));
    }

    @Test
    public void testRemoveItemFromMapWhenIncludedOnce() throws ItemNotFoundException {
        mapShoppingCart.addItem(apple);
        mapShoppingCart.addItem(chocolate);
        mapShoppingCart.removeItem(apple);
        assertFalse(mapShoppingCart.getUniqueItems().contains(apple));
    }

    @Test
    public void testRemoveItemFromListIfItemIsIncludedMoreThanOnce() throws ItemNotFoundException {
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(apple);
        listShoppingCart.removeItem(apple);
        assertTrue(listShoppingCart.getUniqueItems().contains(apple));
    }

    @Test
    public void testRemoveItemFromMapIfItemIsIncludedMoreThanOnce() throws ItemNotFoundException {
        mapShoppingCart.addItem(apple);
        mapShoppingCart.addItem(apple);
        mapShoppingCart.removeItem(apple);
        assertTrue(mapShoppingCart.getUniqueItems().contains(apple));
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveItemFromListIfItemIsNotIncluded() throws ItemNotFoundException {
        listShoppingCart.addItem(chocolate);
        listShoppingCart.removeItem(apple);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveItemFromMapIfItemIsNotIncluded() throws ItemNotFoundException {
        mapShoppingCart.addItem(chocolate);
        mapShoppingCart.removeItem(apple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemFromListIfItemNameIsNull() throws ItemNotFoundException {
        Item nullApple = new Apple(null, "green", TEST_APPLE_PRICE);
        listShoppingCart.removeItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemFromMapIfItemNameIsNull() throws ItemNotFoundException {
        Item nullApple = new Apple(null, "green", TEST_APPLE_PRICE);
        mapShoppingCart.removeItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemFromListIfItemDescriptionIsNull() throws ItemNotFoundException {
        Item nullApple = new Apple("apple", null, TEST_APPLE_PRICE);
        listShoppingCart.removeItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemFromMapIfItemDescriptionIsNull() throws ItemNotFoundException {
        Item nullApple = new Apple("apple", null, TEST_APPLE_PRICE);
        mapShoppingCart.removeItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemFromListIfItemIsNull() throws ItemNotFoundException {
        Item nullApple = null;
        listShoppingCart.removeItem(nullApple);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemFromMapIfItemIsNull() throws ItemNotFoundException {
        Item nullApple = null;
        mapShoppingCart.removeItem(nullApple);
    }

    @Test
    public void getTotalOfList() {
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(chocolate);
        listShoppingCart.addItem(apple);
        assertTrue(listShoppingCart.getTotal() == EXPECTED_SUM);
    }

    @Test
    public void getTotalOfMap() {
        mapShoppingCart.addItem(apple);
        mapShoppingCart.addItem(chocolate);
        mapShoppingCart.addItem(apple);
        assertTrue(mapShoppingCart.getTotal() == EXPECTED_SUM);
    }

    @Test
    public void getTotalOfListWhenEmpty() {
        assertTrue(listShoppingCart.getTotal() == 0d);
    }

    @Test
    public void getTotalOfMapWhenEmpty() {
        assertTrue(mapShoppingCart.getTotal() == 0d);
    }

    @Test
    public void getSortedItemsInList() {
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(chocolate);
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(chocolate);
        Collection<Item> sortedItems = new HashSet<>();
        sortedItems.add(apple);
        sortedItems.add(chocolate);
        assertTrue(listShoppingCart.getSortedItems().containsAll(sortedItems));
    }

    @Test
    public void getSortedItemsInMap() {
        mapShoppingCart.addItem(apple);
        mapShoppingCart.addItem(apple);
        mapShoppingCart.addItem(chocolate);
        mapShoppingCart.addItem(apple);
        mapShoppingCart.addItem(chocolate);
        Collection<Item> sortedItems = new HashSet<>();
        sortedItems.add(apple);
        sortedItems.add(chocolate);
        assertTrue(mapShoppingCart.getSortedItems().containsAll(sortedItems));
    }
}
