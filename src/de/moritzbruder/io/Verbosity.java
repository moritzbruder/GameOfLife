package de.moritzbruder.io;

import java.text.DateFormat;
import java.util.Date;

/**
 * Singleton instance that is used to log verbose input to console
 * Created by Moritz Bruder on 26.09.2017.
 */
public class Verbosity {

    /**
     * Shared {@link Verbosity}-instance
     */
    public static final Verbosity shared = new Verbosity();

    /**
     * {@link DateFormat} used to print the current time to the console
     */
    private DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);

    /**
     * Flag determining whether verbose logging is active
     */
    private boolean enabled = false;

    /**
     * Used to log a value to the console if verbose logging is enabled
     * @param message The Text to be printed to the console
     */
    public void log (String message) {
        if (this.enabled) System.out.println(format.format(new Date()) + "]   " + message);

    }

    /**
     * Used to enable verbose logging
     */
    public void enable () {
        this.enabled = true;

    }

    /**
     * Used to disable verbose logging
     */
    public void disable () {
        this.enabled = false;

    }

}
