package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class StandardCard extends Card {

    public StandardCard(String name){
        super(name);
    }

    public boolean executePayment(double cost){
        if(cost <= 0){
            return false;
        }
        if(amount >= cost){
            amount -= cost;
            return true;
        }
        return false;
    }
}
