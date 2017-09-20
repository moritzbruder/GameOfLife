package de.moritzbruder.game;

import java.util.function.Consumer;

/**
 * Created by Moritz Bruder on 15.09.2017.
 */
public class Field {

    /*   SUBCLASSES   */
    public interface FieldDisplayDelegate {

        void onRepaintRequired();

    }

    /*   CONSTANTS   */
    public static final int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_HEIGHT = 20;


    /*   VARIABLES   */
    private int width, height;

    private Cell[][] field;

    private FieldDisplayDelegate delegate;


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
     * Generates the 2d-array representing the field
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
                result[x][y] = new Cell(x, y, this, alive);
            }

        this.width = width;
        this.height = height;

        return result;
    }

    public void resize (int width, int height) {
        this.field = makeField(width, height);
        if (delegate != null) delegate.onRepaintRequired();

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

    public Cell getCell (Cell.Position position) {
        return getCell(position.x, position.y);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Triggers the calculation of the following states for each cell. Iterates through all cells to calculate and then again to commit and draw
     */
    public void nextRound () {
        //Go through all cells and calculate upcoming state and then commit

        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < Field.this.field[x].length; y++) {
                Field.this.getCell(x, y).determineUpcomingState();
            }

        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < Field.this.field[x].length; y++) {
                Cell cell = this.getCell(x, y);
                cell.commit();
            }

            if (delegate != null) delegate.onRepaintRequired();
    }

    public void forEach (Consumer<Cell> consumer) {
        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++) {
                consumer.accept(getCell(x, y));
            }
    }

    public void killCell (Cell c) {
        c.kill();
        if (delegate != null) delegate.onRepaintRequired();
    }

    public void makeCellComeAlive (Cell c) {
        c.becomeAlive();
        if (delegate != null) delegate.onRepaintRequired();
    }

    /**
     * Used to subscribe a {@link FieldDisplayDelegate} to any changes made to the field.
     * @param delegate the delegate to subscribe
     */
    public void subscribe (FieldDisplayDelegate delegate) {
        this.delegate = delegate;

    }

}