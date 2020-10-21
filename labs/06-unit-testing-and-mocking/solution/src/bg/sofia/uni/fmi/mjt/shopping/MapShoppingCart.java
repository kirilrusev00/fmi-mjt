package bg.sofia.uni.fmi.mjt.shopping;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

import java.util.*;

public class MapShoppingCart implements ShoppingCart {

    private Map<Item, Integer> items = new HashMap<>();

    @Override
    public Collection<Item> getUniqueItems() {
        return items.keySet();
    }

    @Override
    public void addItem(Item item) {
        if (item == null || item.getName() == null || item.getDescription() == null) {
            throw new IllegalArgumentException();
        } else {
            Integer occurrences = items.get(item);
            if (occurrences == null) {
                occurrences = 0;
            }
            items.put(item, ++occurrences);
        }
    }

    @Override
    public void removeItem(Item item) throws ItemNotFoundException {
        if (item == null || item.getName() == null || item.getDescription() == null) {
            throw new IllegalArgumentException();
        }
        if (!items.containsKey(item)) {
            throw new ItemNotFoundException();
        }
        Integer occurrences = items.get(item);
        if (occurrences == 1) {
            items.remove(item);
        } else {
            items.put(item, --occurrences);
        }
    }

    @Override
    public double getTotal() {
        int total = 0;
        for (Map.Entry<Item, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        return total;
    }

    @Override
    public Collection<Item> getSortedItems() {
        List<Map.Entry<Item, Integer>> itemsList = new ArrayList<>(items.entrySet());
        Collections.sort(itemsList, new Comparator<Map.Entry<Item, Integer>>() {
            @Override
            public int compare(Map.Entry<Item, Integer> o1, Map.Entry<Item, Integer> o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });
        Collection<Item> sortedItems = new ArrayList<>();
        for (Map.Entry<Item, Integer> entry : itemsList) {
            sortedItems.add(entry.getKey());
        }
        return sortedItems;
    }
}