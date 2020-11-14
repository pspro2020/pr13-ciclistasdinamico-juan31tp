package ciclistas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class LateCyclist implements Runnable {

    private final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault());
    private final Phaser phaser;

    public LateCyclist(CyclistPhaser phaser, String s) {
        this.phaser=phaser;
    }

    @Override
    public void run() {
        if (!phaser.isTerminated()){
            int joinPhase = phaser.register();
            System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I have just joined!");
            goToFuelStation();

            if (joinPhase <= CyclistPhaser.ARRIVE_FUELSTATION){
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I was interrupted while waiting at the fuel station");
                    return;
                }
            }

            goToShop();

            if (joinPhase <= CyclistPhaser.ARRIVE_SHOP){
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I was interrupted while waiting at the shop");
                    return;
                }
            }

            backToFuelStation();

            if (joinPhase <= CyclistPhaser.RETURN_FUELSTATION){
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I was interrupted while waiting at the fuel station in the returning.");
                    return;
                }
            }

            backToHome();
        } else {
            System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I woke up too late :(");
        }

    }

    private void backToHome() {
        System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": Im going home");
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3)+1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I'm at home!");
    }

    private void backToFuelStation() {
        System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": Im returning to the fuel station");
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10)+5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I've just arrived to the fuel station again");
    }

    private void goToShop() {
        System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": Im going to the shop");
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10)+5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I've just arrived to the shop");
    }

    private void goToFuelStation() {
        System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": Im going to the fuel station");
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3)+1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalTime.now().format(dateTimeFormatter) + " -> " + Thread.currentThread().getName() + ": I've just arrived to the fuel station");
    }
}
