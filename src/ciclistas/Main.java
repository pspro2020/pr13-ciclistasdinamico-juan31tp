package ciclistas;

public class Main {

    private static final int NUM_OF_CYLIST= 10;

    public static void main(String[] args) {

        CyclistPhaser phaser = new CyclistPhaser();

        for (int i=0; i<NUM_OF_CYLIST; i++){
            new Thread(new Cyclist(phaser), "Cyclist #" + i).start();
        }

        new Thread(new RushCyclist(phaser, "Cyclist #" + 10)).start();
        new Thread(new LateCyclist(phaser, "Cyclist #" + 11)).start();
        new Thread(new LateCyclist(phaser, "Cyclist #" + 12)).start();
    }
}