package bg.sofia.uni.fmi.mjt.shopping.item;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {
    private static final double TEST_APPLE_PRICE = 5d;
    private static final double TEST_CHOCOLATE_PRICE = 10d;
    private static final double EXPECTED_SUM = 15d;

    private Item apple;
    private Item chocolate;

    @Before
    public void beforeClass() {
        apple = new Apple("apple", "green", TEST_APPLE_PRICE);
        chocolate = new Chocolate("chocolate", "brown", TEST_CHOCOLATE_PRICE);
    }

    @Test
    public void testGetNameAppleNameIsTheSame() {
        assertTrue(apple.getName().equals("apple"));
    }

    @Test
    public void testGetNameChocolateNameIsTheSame() {
        assertTrue(chocolate.getName().equals("chocolate"));
    }

    @Test
    public void testGetDescriptionAppleNameIsTheSame() {
        assertTrue(apple.getDescription().equals("green"));
    }

    @Test
    public void testGetDescriptionChocolateNameIsTheSame() {
        assertTrue(chocolate.getDescription().equals("brown"));
    }

    @Test
    public void testGetPriceAppleNameIsTheSame() {
        assertTrue(apple.getPrice() == TEST_APPLE_PRICE);
    }

    @Test
    public void testGetPriceChocolateNameIsTheSame() {
        assertTrue(chocolate.getPrice() == TEST_CHOCOLATE_PRICE);
    }

    @Test
    public void testEqualsSameApples() {
        assertEquals(apple, apple);
    }

    @Test
    public void testEqualsSameChocolates() {
        assertEquals(chocolate, chocolate);
    }

    @Test
    public void testEqualsAppleWithOtherObject() {
        Double d = 0d;
        assertNotEquals(apple, d);
    }

    @Test
    public void testEqualsChocolateWithOtherObject() {
        Double d = 0d;
        assertNotEquals(chocolate, d);
    }

    @Test
    public void testEqualsEqualApples() {
        Item apple2 = new Apple("apple", "green", TEST_APPLE_PRICE);
        assertEquals(apple, apple2);
    }

    @Test
    public void testEqualsEqualChocolates() {
        Item chocolate2 = new Chocolate("chocolate", "brown", TEST_CHOCOLATE_PRICE);
        assertEquals(chocolate, chocolate2);
    }

    @Test
    public void testEqualsNotEqualNameOfApples() {
        Item apple2 = new Apple("apple2", "green", TEST_APPLE_PRICE);
        assertNotEquals(apple, apple2);
    }

    @Test
    public void testEqualsNotEqualNameOfChocolates() {
        Item chocolate2 = new Chocolate("chocolate2", "brown", TEST_CHOCOLATE_PRICE);
        assertNotEquals(chocolate, chocolate2);
    }

    @Test
    public void testEqualsNotEqualDescriptionOfApples() {
        Item apple2 = new Apple("apple", "red", TEST_APPLE_PRICE);
        assertNotEquals(apple, apple2);
    }

    @Test
    public void testEqualsNotEqualDescriptionOfChocolates() {
        Item chocolate2 = new Chocolate("chocolate", "white", TEST_CHOCOLATE_PRICE);
        assertNotEquals(chocolate, chocolate2);
    }

    @Test
    public void testEqualsNotEqualPriceOfApples() {
        Item apple2 = new Apple("apple", "green", TEST_CHOCOLATE_PRICE);
        assertNotEquals(apple, apple2);
    }

    @Test
    public void testEqualsNotEqualPriceOfChocolates() {
        Item chocolate2 = new Chocolate("chocolate", "brown", EXPECTED_SUM);
        assertNotEquals(chocolate, chocolate2);
    }
}
