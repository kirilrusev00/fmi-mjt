package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class PremiumOffer extends OfferImpl {
    private static final double PERCENT_DIVISION = 100.0;
    private static final double ROUND_TO_TWO_DECIMAL_PLACES = 100.0;
    private double discount;

    public PremiumOffer(String productName, LocalDate date, String description, double price,
                        double shippingPrice, double discount) {
        super(productName, date, description, price, shippingPrice);
        this.discount = calculateDiscountPercent(discount);
    }

    private double calculateDiscountPercent(double discount) {
        return Math.round(discount * ROUND_TO_TWO_DECIMAL_PLACES) / ROUND_TO_TWO_DECIMAL_PLACES;
    }

    @Override
    public double getTotalPrice() {
        double multiplyTotalPriceBy = (PERCENT_DIVISION - discount) / PERCENT_DIVISION;
        return multiplyTotalPriceBy * (getPrice() + getShippingPrice());
    }
}
