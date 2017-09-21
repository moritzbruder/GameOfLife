package de.moritzbruder.game;

import java.util.function.Consumer;

/**
 * An Object representing a game-field for Convway's Game of Life
 * @author Created by Moritz Bruder on 15.09.2017.
 */
public class Field {

    /*   SUBCLASSES   */

    /**
     * Listener that is notified when any changes happened (To repaint for example)
     */
    public interface OnFieldChangedListener {

        /**
         * Notifies listener that something changed (size, alive-state of cells, etc.)
         */
        void onFieldChanged();

    }

    /*   CONSTANTS   */
    public static final int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_HEIGHT = 20;


    /*   VARIABLES   */
    /**
     * Contains the current width of the {@link Field}
     */
    private int width;

    /**
     * Contains the current height of the {@link Field}
     */
    private int height;

    /**
     * Contains how many of all cells on the {@link Field} are currently alive.
     */
    private int currentlyAliveCount = 0;

    /**
     * 2-dimensional array holding all cells on the {@link Field} by x- and y-coordinate
     */
    private Cell[][] field;

    /**
     * Holds the listener that listens for changes on this field
     */
    private OnFieldChangedListener onChangeListener;

    /**
     * Holds an empty Object that serves as a key, to make sure that cells of the {@link Field} are never modified without the {@link Field}'s knowledge
     */
    Object key = new Object();


    /*   CLASS-BODY   */

    /**
     * Creates a new {@link Field}-instance
     * @param width number of columns
     * @param height number of rows
     */
    public Field (int width, int height) {
        //initialize empty array
        this.field = makeField(width, height);

    }

    /**
     * Generates the 2d-array representing the field, while keeping the state of the cells as far as possible
     * @param width number of columns on the field
     * @param height number of rows on the field
     * @return the 2d array of cells
     */
    private Cell[][] makeField (int width, int height) {
        //init 2d array and fill with cells
        Cell[][] result = new Cell[width][height];
        for (int x = 0; x < result.length; x++)
            for (int y = 0; y < result[x].length; y++) {
                //Determine if the cell was alive in the previous size
                boolean alive = (x < getWidth() && y < getHeight()) ? getCell(x, y).isAlive() : false;
                result[x][y] = new Cell(x, y, this, alive, this.key);
            }

        this.width = width;
        this.height = height;

        return result;
    }

    /**
     * Changes width and height of the field, reinitializes array
     * @param width The new desired width
     * @param height The new desired height
     */
    public void resize (int width, int height) {
        this.field = makeField(width, height);
        if (onChangeListener != null) onChangeListener.onFieldChanged();

    }

    /**
     * Returns the cell with the given coordinates
     * @param x The x-Position of the requested cell. Allowed values: -1 to field-width
     * @param y The y-Position of the requested cell. Allowed values: -1 to field-height
     * @return the cell
     */
    public Cell getCell (int x, int y) {
        //Check if input values are over the sides and if so move to other side.
        int xIn = (x == -1) ? getWidth() - 1 : (x == getWidth()) ? 0 : x;
        int yIn = (y == -1) ? getHeight() - 1 : (y == getHeight()) ? 0 : y;
        //Return cell for given indices
        return field[xIn][yIn];

    }

    /**
     * Returns the cell at the given position
     * @param position Position representing the coords of the required cell
     * @return the cell
     */
    public Cell getCell (Cell.Position position) {
        return getCell(position.x, position.y);

    }

    /**
     * @return The current {@link #width} of the {@link Field}
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The current {@link #height} of the {@link Field}
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The total count of {@link Cell Cells} on the {@link Field}
     */
    public int getCellCount () {
        return getWidth() * getHeight();

    }

    /**
     * @return The count of {@link Cell Cells} on the {@link Field} that are alive
     */
    public int getAliveCellCount () {
        return this.currentlyAliveCount;

    }

    /**
     * Triggers the calculation of the following states for each cell. Iterates through all cells to calculate and then again to commit and draw
     */
    public void nextRound () {
        //Go through all cells and calculate upcoming state and then commit

        currentlyAliveCount = 0;

        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < Field.this.field[x].length; y++) {
                Field.this.getCell(x, y).determineUpcomingState();
            }

        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < Field.this.field[x].length; y++) {
                Cell cell = this.getCell(x, y);
                cell.commit();
                if (cell.isAlive()) currentlyAliveCount++;
            }

        if (onChangeListener != null) onChangeListener.onFieldChanged();
    }

    /**
     * Executes a bit of code on every single {@link Cell}
     * @param consumer The Consumer consuming every {@link Cell}
     */
    public void forEach (Consumer<Cell> consumer) {
        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++) {
                consumer.accept(getCell(x, y));
            }
    }

    /**
     * Changes the state of the given cell to dead
     * @param c The {@link Cell} that shall be killed
     */
    public void killCell (Cell c) {
        if (c.isAlive()) currentlyAliveCount--;
        c.kill(this.key);
        if (onChangeListener != null) onChangeListener.onFieldChanged();

    }

    /**
     * Changes the state of the given cell to alive
     * @param c The {@link Cell} that shall become alive
     */
    public void makeCellComeAlive (Cell c) {
        if (!c.isAlive()) currentlyAliveCount++;
        c.becomeAlive(this.key);
        if (onChangeListener != null) onChangeListener.onFieldChanged();

    }

    /**
     * Used to subscribe a {@link OnFieldChangedListener} to any changes made to the field.
     * @param delegate the onChangeListener to subscribe
     */
    public void subscribe (OnFieldChangedListener delegate) {
        this.onChangeListener = delegate;

    }

}