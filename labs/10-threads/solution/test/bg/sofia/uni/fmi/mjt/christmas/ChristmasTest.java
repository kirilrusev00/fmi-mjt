package bg.sofia.uni.fmi.mjt.christmas;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChristmasTest {

    static Christmas christmas = null;
    private static final int NUMBER_OF_KIDS = 1000;
    private static final int TIME_TO_CHRISTMAS = 500;
    private static final int TIMEOUT_TIME = 5000;

    @BeforeClass
    public static void setUp() {
        christmas = new Christmas(new Workshop(), NUMBER_OF_KIDS, TIME_TO_CHRISTMAS);
        christmas.celebrate();
    }

    @Test(timeout = TIMEOUT_TIME)
    public void testWishCount() {
        assertEquals(christmas.getWorkshop().getWishCount(), NUMBER_OF_KIDS);
    }

    @Test(timeout = TIMEOUT_TIME)
    public void testElfBacklogDistribution() {
        double max = Arrays.stream(christmas.getWorkshop().getElves())
                .mapToDouble(Elf::getTotalGiftsCrafted)
                .max()
                .getAsDouble();

        double min = Arrays.stream(christmas.getWorkshop().getElves())
                .mapToDouble(Elf::getTotalGiftsCrafted)
                .min()
                .getAsDouble();

        assertTrue("One elf crafted " + max + " items while other just : " + min, ((min * 2) > max));
    }
}
