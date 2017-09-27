package de.moritzbruder.gameoflife.game;

import com.sun.istack.internal.NotNull;

/**
 * @author Created by Moritz Bruder on 15.09.2017.
 */
public class Cell {

    /**
     * Constant for state in which cell is dead
     */
    private static final int DEAD = -1;

    /**
     * Constant for undetermined state (inbetween rounds)
     */
    private static final int UNDETERMINED = 0;

    /**
     * Constant for state in which cell is alive
     */
    private static final int ALIVE = 1;

    /**
     * A simple Object containing the x- and y-coordinate of a {@link Cell}
     */
    public static class Position {

        /**
         * x- and y-coordinate of the position
         */
        int x, y;

        /**
         * Creates a new instance
         * @param x x-coordinate
         * @param y y-coordinate
         */
        public Position (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Flag showing whether this cell is alive.
     */
    private boolean mIsAlive = false;

    /**
     * Showing whether this cell will be alive in the next round;
     */
    private int upcomingState = UNDETERMINED;

    /**
     * Representing the x and y position of this cell on the field
     */
    private int xPos, yPos;

    /**
     * The {@link Field} that this Cell is on.
     */
    private Field field;

    /**
     * Simple object that is only known to the field that created this Cell. Just to make sure no wrong calls happen.
     */
    private Object key;

    /**
     * Creates anew instance
     * @param xPos x-coordinate of this cell
     * @param yPos y-coordinate of this cell
     * @param field The field that this cell is part of
     * @param alive Whether the cell is crated alive or dead
     * @param key Key that is only known to the creating field, used to prevent "unauthorized" access
     */
    public Cell (int xPos, int yPos, @NotNull Field field, boolean alive, Object key) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.field = field;
        this.mIsAlive = alive;
        this.key = key;
    }

    /**
     * @return whether this cell is currently alive
     */
    public boolean isAlive () {
        return this.mIsAlive;

    }

    /**
     * Sets the state of this cell to dead.
     * @param key A key-Object that is only known to the field, that created this cell. This is to prevent "unauthorized" access
     */
    public void kill (Object key) {
        if (key != this.key) throw new IllegalAccessError("Wrong caller tries to #kill this cell: " + this);
        this.mIsAlive = false;

    }

    /**
     * Sets the state of this cell to alive
     * @param key A key-Object that is only known to the field, that created this cell. This is to prevent "unauthorized" access
     */
    public void becomeAlive (Object key) {
        if (key != this.key) throw new IllegalAccessError("Wrong caller tries to #becomeAlive this cell: " + this);
        this.mIsAlive = true;

    }

    /**
     * Used to calculate whether this cell will be alive in the next round
     */
    public void determineUpcomingState () {
        //Get neighbours and count alive/dead cells
        int deadCount = 0;
        int aliveCount = 0;

        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++) {
                //Skip self
                if (x == 0 && y == 0) continue;

                //get neighbour, check state and increase according counter
                if (this.field.getCell(xPos + x, yPos + y).isAlive()) aliveCount++;
                else deadCount++;
            }

        //Sanity check: Check whether deadCount and aliveCount add up to the total neighbour count (8)
        if (8 != deadCount + aliveCount) throw new IllegalStateException("Something is wrong: This cell (" + this.xPos + ", " + this.yPos + ") has 8 neighbours, but " + aliveCount + " of them are alive while " + deadCount + " are dead.");

        //By default cells keep their state, this may be overwritten by the game rules.
        this.upcomingState = mIsAlive ? ALIVE : DEAD;

        //Game rules:
        if (this.mIsAlive) {
            if (aliveCount < 2 || aliveCount > 3) this.upcomingState = DEAD;
            else if (aliveCount == 2 || aliveCount == 3) this.upcomingState = ALIVE;
        } else {
            if (aliveCount == 3) this.upcomingState = ALIVE;
        }

    }

    /**
     * Commits the change that is represented by upcomingState
     * @return whether the state of the cell changed
     */
    public boolean commit () {
        //Throw error if inconsistent state
        if (upcomingState == UNDETERMINED) throw new IllegalStateException("Trying to commit without determining upcoming state first. " + this);

        //Set alive-state according to willBe-state, and store if any change happened
        boolean didChange = (upcomingState == ALIVE) != mIsAlive;
        this.mIsAlive = (upcomingState == ALIVE);

        //Reset willBe-state
        this.upcomingState = UNDETERMINED;

        //return
        return didChange;

    }

    @Override
    public String toString() {
        return super.toString() + " [" + xPos + ", " + yPos + "]";
    }

    /**
     * @return The x-coordinate of the {@link Cell}
     */
    public int getX() {
        return xPos;
    }

    /**
     * @return The y-coordinate of the {@link Cell}
     */
    public int getY() {
        return yPos;
    }
}
