package bg.sofia.uni.fmi.mjt.christmas;

public class Elf extends Thread {

    private int id;
    private Workshop workshop;
    private int totalGiftsCrafted;

    public Elf(int id, Workshop workshop) {
        this.id = id;
        this.workshop = workshop;
        //this.totalGiftsCrafted = 0;
    }

    /**
     * Gets a wish from the backlog and creates the wanted gift.
     **/
    public void craftGift() {
        Gift wishedGift;
        while ((wishedGift = workshop.nextGift()) != null) {
            try {
                Thread.sleep(wishedGift.getCraftTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalGiftsCrafted++;
        }

        //System.out.println("Elf #" + id + " created " + totalGiftsCrafted + " gifts");
    }

    /**
     * Returns the total number of gifts that the given elf has crafted.
     **/
    public int getTotalGiftsCrafted() {
        return totalGiftsCrafted;
    }

    @Override
    public void run() {
        craftGift();
    }
}
