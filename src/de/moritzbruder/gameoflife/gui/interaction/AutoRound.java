package de.moritzbruder.gameoflife.gui.interaction;

import de.moritzbruder.gameoflife.game.Field;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A timed loop that is used to trigger the next round of a game in a given frequency
 * @author Created by Moritz Bruder on 20.09.2017.
 */
public class AutoRound {

    /**
     * Timed executor
     */
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /**
     * Round-frequency in ms
     */
    private int rate = 500;

    /**
     * The Field that this loop should trigger the next round on
     */
    private Field field;

    /**
     * Creates a new instacne
     * @param field The {@link Field} that this {@link AutoRound} should trigger the next round on
     */
    public AutoRound (Field field) {
        this.field = field;
        executor.shutdownNow();

    }

    /**
     * Runnable that triggers the next round on {@link #field}
     */
    private Runnable nextRoundRunnable = () -> AutoRound.this.field.nextRound();

    /**
     * Starts the loop
     */
    public void start () {
        executor.shutdownNow();
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(nextRoundRunnable, this.rate, this.rate, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the loop
     */
    public void stop () {
        executor.shutdownNow();
    }

    /**
     * Changes the frequency, and therefore starts and stops the timer
     * @param rate The new frequency (in ms)
     */
    public void setRate(int rate) {
        this.rate = rate;
        if (!executor.isShutdown()) start();

    }
}
