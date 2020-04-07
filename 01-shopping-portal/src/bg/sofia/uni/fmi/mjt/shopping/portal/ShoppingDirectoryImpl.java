package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeSet;

public class ShoppingDirectoryImpl implements ShoppingDirectory {
    private static final int LAST_DAYS_TO_CHECK = 30;
    private Map<String, TreeSet<Offer>> shoppingOffers;

    public ShoppingDirectoryImpl() {
        shoppingOffers = new LinkedHashMap<>();
    }

    private void sortOffersByDateDescending(String productName) {
        TreeSet<Offer> sortedOffers = new TreeSet<>(new Comparator<Offer>() {
            @Override
            public int compare(Offer o1, Offer o2) {
                int differenceInDates = o2.getDate().compareTo(o1.getDate());
                if (differenceInDates != 0) {
                    return differenceInDates;
                }
                return Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
            }
        });
        sortedOffers.addAll(shoppingOffers.get(productName));
        shoppingOffers.replace(productName, sortedOffers);
    }

    private Collection<Offer> sortOffersByTotalPriceAscending(Collection<Offer> offers) {
        TreeSet<Offer> sortedOffers = new TreeSet<>(new Comparator<Offer>() {
            @Override
            public int compare(Offer o1, Offer o2) {
                int differenceInDates = Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
                if (differenceInDates != 0) {
                    return differenceInDates;
                }
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        sortedOffers.addAll(offers);
        return sortedOffers;
    }

    @Override
    public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException("Empty input of product name.");
        }
        String lowerCaseProductName = productName.toLowerCase();
        if (!shoppingOffers.containsKey(lowerCaseProductName)) {
            throw new ProductNotFoundException("There is no product with this name in the directory.");
        }
        Collection<Offer> allOffers = new LinkedHashSet<>();
        sortOffersByDateDescending(lowerCaseProductName);
        for (Offer offer : shoppingOffers.get(lowerCaseProductName)) {
            if (offer.getDate().compareTo(LocalDate.now()) > 0) {
                continue;
            }
            if (offer.getDate().compareTo(LocalDate.now()) <= 0 &&
                    offer.getDate().compareTo(LocalDate.now().minusDays(LAST_DAYS_TO_CHECK)) > 0) {
                allOffers.add(offer);
            } else {
                break;
            }
        }
        return sortOffersByTotalPriceAscending(allOffers);
    }

    @Override
    public Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException {
        if (productName == null) {
            throw new IllegalArgumentException("Empty input of product name.");
        }
        String lowerCaseProductName = productName.toLowerCase();
        if (!shoppingOffers.containsKey(lowerCaseProductName)) {
            throw new ProductNotFoundException("There is no product with this name in the directory.");
        }
        Collection<Offer> offersInTheLastDays = findAllOffers(lowerCaseProductName);
        if (offersInTheLastDays.isEmpty()) {
            throw new NoOfferFoundException("There is no offer found for this product.");
        }
        return offersInTheLastDays.iterator().next();
    }

    @Override
    public Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException("Empty input of product name.");
        }
        String lowerCaseProductName = productName.toLowerCase();
        if (!shoppingOffers.containsKey(lowerCaseProductName)) {
            throw new ProductNotFoundException("There is no product with this name in the directory.");
        }
        sortOffersByDateDescending(lowerCaseProductName);
        Map<LocalDate, PriceStatistic> priceStatistics = new LinkedHashMap<>();
        for (Offer offer : shoppingOffers.get(lowerCaseProductName)) {
            if (!priceStatistics.containsKey(offer.getDate())) {
                PriceStatistic statistic = new PriceStatistic(offer.getDate());
                statistic.addOffer(offer);
                priceStatistics.put(offer.getDate(), statistic);
            } else {
                priceStatistics.get(offer.getDate()).addOffer(offer);
            }
        }
        return priceStatistics.values();
    }

    @Override
    public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
        if (offer == null || offer.getProductName() == null || offer.getDescription() == null) {
            throw new IllegalArgumentException("Empty input of offer.");
        }
        String lowerCaseProductName = offer.getProductName().toLowerCase();
        if (shoppingOffers.containsKey(lowerCaseProductName)) {
            if (shoppingOffers.get(lowerCaseProductName).contains(offer)) {
                throw new OfferAlreadySubmittedException("The same offer is already submitted.");
            }
            shoppingOffers.get(lowerCaseProductName).add(offer);
        } else {
            TreeSet<Offer> offers = new TreeSet<>(new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    int differenceInDates = Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
                    if (differenceInDates != 0) {
                        return differenceInDates;
                    }
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
            offers.add(offer);
            shoppingOffers.put(lowerCaseProductName, offers);
        }
    }
}
