package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public abstract class OfferImpl implements Offer {
    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;

    OfferImpl(String productName, LocalDate date, String description, double price, double shippingPrice) {
        this.productName = productName;
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getShippingPrice() {
        return shippingPrice;
    }

    @Override
    public abstract double getTotalPrice();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productName == null) ? 0 : productName.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + (int) getTotalPrice();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof OfferImpl)) {
            return false;
        }

        OfferImpl offer = (OfferImpl) o;

        return (this.getProductName().equalsIgnoreCase(offer.getProductName()) &&
                this.getDate().equals(offer.getDate()) &&
                this.getTotalPrice() == offer.getTotalPrice());
    }
}
