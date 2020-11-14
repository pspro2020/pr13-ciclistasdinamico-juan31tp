package ciclistas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Phaser;

public class CyclistPhaser extends Phaser {

    public static final int ARRIVE_FUELSTATION = 0;
    public static final int ARRIVE_SHOP = 1;
    public static final int RETURN_FUELSTATION = 2;
    private final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault());

    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case ARRIVE_FUELSTATION:
                System.out.printf("%s -> All %d cyclists arrived to fuel station (executed in %s)\n",
                        LocalTime.now().format(dateTimeFormatter), registeredParties, Thread.currentThread().getName());
                break;
            case ARRIVE_SHOP:
                System.out.printf("%s -> All %d cyclists arrived to the shop (executed in %s)\n",
                        LocalTime.now().format(dateTimeFormatter), registeredParties, Thread.currentThread().getName());
                break;
            case RETURN_FUELSTATION:
                System.out.printf("%s -> All %d cyclists returned to the fuel station (executed in %s)\n",
                        LocalTime.now().format(dateTimeFormatter), registeredParties, Thread.currentThread().getName());
                return true;
        }
        return super.onAdvance(phase, registeredParties);
    }
}
