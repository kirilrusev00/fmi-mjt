package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class GoldenCard extends Card {

    public GoldenCard(String name){
        super(name);
    }

    public boolean executePayment(double cost){
        if(cost <= 0){
            return false;
        }
        if(amount >= 0.85 * cost){
            amount -= 0.85 * cost;
            return true;
        }
        return false;
    }
}
