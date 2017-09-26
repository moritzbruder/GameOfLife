package de.moritzbruder.helper;

/**
 * Simple class used to hold a single value
 * Created by Moritz Bruder on 26.09.2017.
 */
public class Holder<T> {

    /**
     * Creates a new instance wit the given value
     * @param initialValue intial value for {@link #v}
     */
    public Holder (T initialValue) {
        this.v = initialValue;
    }

    /**
     * The value that this {@link Holder}-instance is holding
     */
    public T v = null;

}
