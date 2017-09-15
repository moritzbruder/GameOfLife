package de.moritzbruder.game;

import com.sun.istack.internal.NotNull;

/**
 * Created by Moritz Bruder on 15.09.2017.
 */
public class Cell {

    public static final int DEAD = -1;
    public static final int UNDETERMINED = 0;
    public static final int ALIVE = 1;

    /**
     * Enum Representing three possible states for the cell in a given round
     */
    enum State {
        DID_CHANGE, DID_NOT_CHANGE, UNDETERMINED;

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
     * Representing the x & y position of this cell on the field
     */
    private int xPos, yPos;

    /**
     * The {@link Field} that this Cell is on.
     */
    private Field field;

    /**
     * Creates a new {@link Cell}-instance
     */
    public Cell (int xPos, int yPos, @NotNull Field field) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.field = field;

    }

    /**
     * @return whether this cell is currently alive
     */
    public boolean isAlive () {
        return this.mIsAlive;

    }

    /**
     * Used to manually set this cell alive (for user-input or presets)
     */
    public void makeAlive () {
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
                if (this.field.getCell(x, y).isAlive()) aliveCount++;
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
     */
    public void commit () {
        if (upcomingState == UNDETERMINED) throw new IllegalStateException("Trying to commit without determining upcoming state first. " + this);

        //Set alive-state according to willBe-state
        this.mIsAlive = (upcomingState == ALIVE);
        //Reset willBe-state
        this.upcomingState = UNDETERMINED;

    }

    @Override
    public String toString() {
        return super.toString() + " [" + xPos + ", " + yPos + "]";
    }
}
