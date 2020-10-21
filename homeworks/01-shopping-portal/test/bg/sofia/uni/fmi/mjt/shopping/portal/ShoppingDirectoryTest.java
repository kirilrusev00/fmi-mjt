package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.PremiumOffer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.RegularOffer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class ShoppingDirectoryTest {

    private static ShoppingDirectoryImpl shoppingDirectory;
    private static Offer regularOffer1;
    private static Offer premiumOffer1;

    @BeforeClass
    public static void beforeClass() {
        shoppingDirectory = new ShoppingDirectoryImpl();
        regularOffer1=new RegularOffer("product", LocalDate.now().minusDays(1),
                "great", 25, 1);
        premiumOffer1=new PremiumOffer("product", LocalDate.now(),
                "great", 20, 1, 19.5);
    }

    @Test
    public void addOffer() throws OfferAlreadySubmittedException, ProductNotFoundException, NoOfferFoundException {
        Offer regularOffer2 = new RegularOffer("product", LocalDate.now(),
                "great", 17, 1);
        Offer premiumOffer2 = new PremiumOffer("Product", LocalDate.now().minusDays(-7),
                "great", 20, 1, 1);
        shoppingDirectory.submitOffer(regularOffer1);
        shoppingDirectory.submitOffer(regularOffer2);
        shoppingDirectory.submitOffer(premiumOffer1);
        shoppingDirectory.submitOffer(premiumOffer2);
        for (Offer offer : shoppingDirectory.shoppingOffers.get("product")) {
            System.out.println(offer.getProductName() + ' ' + offer.getDate() + ' ' + offer.getTotalPrice());
        }
        System.out.println();
        TreeSet<Offer> allOffers = (TreeSet<Offer>) shoppingDirectory.findAllOffers("Product");
        for (Offer offer : allOffers) {
            System.out.println(offer.getProductName() + ' ' + offer.getDate() + ' ' + offer.getTotalPrice());
        }
        assertNotEquals(regularOffer2, premiumOffer1);
    }
}
