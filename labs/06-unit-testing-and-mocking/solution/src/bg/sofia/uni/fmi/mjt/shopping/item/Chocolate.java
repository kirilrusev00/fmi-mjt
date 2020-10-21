package bg.sofia.uni.fmi.mjt.shopping.item;

public class Chocolate implements Item {

    private String name = "";
    private String description = "";
    private double price = 0;

    public Chocolate(String name, String desc, double price) {
        this.name = name;
        this.description = desc;
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Chocolate)) {
            return false;
        }

        Chocolate chocolate = (Chocolate) o;

        return (this.name.equals(chocolate.getName()) && this.description.equals(chocolate.getDescription())
                && this.price == chocolate.getPrice());
    }

}