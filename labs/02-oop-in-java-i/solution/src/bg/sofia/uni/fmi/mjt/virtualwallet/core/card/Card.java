package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public abstract class Card {

    private String name;
    protected double amount;

    public Card(String name) {
        this.name = name;
        amount = 0;
    }

    public abstract boolean executePayment(double cost);

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void feed(double amount){
        this.amount += amount;
    }
}
