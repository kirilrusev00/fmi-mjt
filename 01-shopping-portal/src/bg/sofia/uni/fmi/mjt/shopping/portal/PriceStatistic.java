package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.TreeSet;

public class PriceStatistic {
    private LocalDate date;
    private TreeSet<Offer> offers;

    PriceStatistic(LocalDate date) {
        offers = new TreeSet<>(new Comparator<Offer>() {
            @Override
            public int compare(Offer o1, Offer o2) {
                return Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
            }
        });
        this.date = date;
    }

    /**
     * Returns the date for which the statistic is
     * collected.
     */
    public LocalDate getDate() {
        return date;
    }

    void addOffer(Offer offer) {
        offers.add(offer);
    }

    /**
     * Returns the lowest total price from the offers
     * for this product for the specific date.
     */
    public double getLowestPrice() {
        return offers.first().getTotalPrice();
    }

    /**
     * Return the average total price from the offers
     * for this product for the specific date.
     */
    public double getAveragePrice() {
        int size = offers.size();
        if (size == 0) {
            return 0d;
        }
        double sum = 0d;
        for (Offer offer : offers) {
            sum += offer.getTotalPrice();
        }
        return sum / (double) size;
    }
}
