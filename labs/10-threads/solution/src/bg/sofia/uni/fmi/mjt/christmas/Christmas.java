package bg.sofia.uni.fmi.mjt.christmas;

public class Christmas {

    private Workshop workshop;
    private int numberOfKids;
    private int timeToChristmas;

    public Christmas(Workshop workshop, int numberOfKids, int timeToChristmas) {
        this.workshop = workshop;
        this.numberOfKids = numberOfKids;
        this.timeToChristmas = timeToChristmas;
    }
/*
    public static void main(String[] args) {
        int numberOfKids = 1000;
        int christmasTime = 2000;

        Christmas christmas = new Christmas(new Workshop(), numberOfKids, christmasTime);
        christmas.celebrate();
    }
*/
    public void celebrate() {
        Thread[] kids = new Thread[numberOfKids];
        for (int i = 0; i < kids.length; i++) {
            kids[i] = new Thread(new Kid(workshop));
            kids[i].start();
        }

        try {
            Thread.sleep(timeToChristmas);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (workshop) {
            workshop.setChristmasTime();
        }
        for (Thread kid : kids) {
            try {
                kid.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Workshop getWorkshop() {
        return workshop;
    }
}
