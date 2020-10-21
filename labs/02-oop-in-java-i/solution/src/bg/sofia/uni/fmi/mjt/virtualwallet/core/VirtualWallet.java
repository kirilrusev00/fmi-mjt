package bg.sofia.uni.fmi.mjt.virtualwallet.core;

import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.PaymentInfo;

public class VirtualWallet implements VirtualWalletAPI {

    private Card[] cardInfo = new Card[5];
    private int numberCards;

    public VirtualWallet() {
        numberCards = 0;
    }

    public boolean registerCard(Card card){
        if(card == null) {
            return false;
        }
        if(card.getAmount() < 0 || card.getName() == null) {
            return false;
        }
        for(int i = 0; i < numberCards; i++){
            if(card.getName().equals(cardInfo[i].getName())) {
                return false;
            }
        }
        if (numberCards >= 5){
            return false;
        }
        cardInfo[numberCards] = card;
        numberCards++;
        return true;
    }

    public boolean executePayment(Card card, PaymentInfo paymentInfo){
        if(card == null || paymentInfo == null) {
            return false;
        }
        if (paymentInfo.getCost() <= 0 || paymentInfo.getLocation() == null || paymentInfo.getReason() == null){
            return false;
        }
        int index = findCard(card);
        if (index == -1){
            return false;
        }
        return card.executePayment(paymentInfo.getCost());
    }

    public int findCard(Card card){
        if(card == null) {
            return -1;
        }
        if(card.getAmount() < 0 || card.getName() == null) {
            return -1;
        }
        for(int i = 0; i < numberCards; i++){
            if(card.getName().equals(cardInfo[i].getName()) && card.getAmount() == cardInfo[i].getAmount()) {
                return i;
            }
        }
        return -1;
    }

    public boolean feed(Card card, double amount){
        if (amount <= 0){
            return false;
        }
        int index = findCard(card);
        if (index == -1){
            return false;
        }
        cardInfo[index].feed(amount);
        return true;
    }

    public Card getCardByName(String name) {
        if(name == null) {
            return null;
        }
        for(int i = 0; i < numberCards; i++){
            if(name.equals(cardInfo[i].getName())) {
                return cardInfo[i];
            }
        }
        return null;
    }

    public int getTotalNumberOfCards(){
        return numberCards;
    }
}
