package bg.sofia.uni.fmi.mjt.christmas;

import java.util.LinkedList;
import java.util.Queue;

public class Workshop {

    private static final int NUMBER_OF_ELVES = 20;
    private int wishCount = 0;
    private volatile boolean isChristmasTime = false;

    private Queue<Gift> gifts;
    private Elf[] elves;

    public Workshop() {
        this.gifts = new LinkedList<>();
        this.elves = new Elf[NUMBER_OF_ELVES];
        for (int i = 0; i < NUMBER_OF_ELVES; i++) {
            elves[i] = new Elf(i, this);
            elves[i].start();
        }
    }

    /**
     * Adds a gift to the elves' backlog.
     **/
    public synchronized void postWish(Gift gift) {
        gifts.add(gift);
        wishCount++;
        this.notify();
    }

    /**
     * Returns an array of the elves working in Santa's workshop.
     **/
    public Elf[] getElves() {
        return elves;
    }

    /**
     * Returns the next gift from the elves' backlog that has to be manufactured.
     **/
    public synchronized Gift nextGift() {
        while (!isChristmasTime && gifts.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (isChristmasTime || gifts.isEmpty()) ? null : gifts.poll();
    }

    /**
     * Returns the total number of wishes sent to Santa's workshop by the kids.
     **/
    public int getWishCount() {
        return wishCount;
    }

    public synchronized void setChristmasTime() {
        this.isChristmasTime = true;
        this.notifyAll();
    }
}
