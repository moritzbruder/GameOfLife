package de.moritzbruder.gui.interaction;

import de.moritzbruder.game.Field;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by morit on 20.09.2017.
 */
public class AutoRound {

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    int rate = 500;
    Field field;

    public AutoRound (Field field) {
        this.field = field;
        executor.shutdownNow();

    }

    Runnable helloRunnable = () -> AutoRound.this.field.nextRound();

    public void start () {
        executor.shutdownNow();
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, this.rate, this.rate, TimeUnit.MILLISECONDS);
    }

    public void stop () {
        executor.shutdownNow();
    }

    public void setRate(int rate) {
        this.rate = rate;
        if (!executor.isShutdown()) start();
    }
}
