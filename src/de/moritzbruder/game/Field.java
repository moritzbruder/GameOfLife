package de.moritzbruder.game;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Moritz Bruder on 15.09.2017.
 */
public class Field {

    /*   CONSTANTS   */
    public static final int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_HEIGHT = 20;
    public static final int THREAD_COUNT = 4;


    /*   VARIABLES   */
    private int width, height;

    private Cell[][] field;


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

    private Cell[][] makeField (int width, int height) {
        this.width = width;
        this.height = height;

        //init 2d array and fill with cells
        Cell[][] result = new Cell[width][height];
        for (int x = 0; x < result.length; x++)
            for (int y = 0; y < result[x].length; y++)
                result[x][y] = new Cell(x, y, this);

        return result;
    }

    /**
     * Returns the cell with the given coordinates
     * @param x The x-Position of the requested cell. Allowed values: -1 to field-width
     * @param y The y-Position of the requested cell. Allowed values: -1 to field-height
     * @return the cell
     */
    public Cell getCell (int x, int y) {
        //Check if input values are over the sides and if so move to other side.
        int xIn = (x == -1) ? field.length - 1 : (x == field.length) ? 0 : x;
        int yIn = (y == -1) ? field[xIn].length - 1 : (x == field[xIn].length) ? 0 : y;
        //Return cell for appropriate indices
        return field[xIn][yIn];

    }

    public void nextRound () {
        //Go through all cells and calculate upcoming state and then commit

        //Split these tasks among threads, if there are enough rows, otherwise one thread
        int threadCount = (this.field.length < THREAD_COUNT) ? 1 : THREAD_COUNT;
        int colsPerThread = this.field.length / threadCount;
        int extraCols = this.field.length % threadCount;

        CountDownLatch workLatch = new CountDownLatch(threadCount);


        //Multithreading enabled if there are more than 3,800,000 cells. This threshold was determined by an experiment
        if (this.width * this.height < 3800000) {
            for (int x = 0; x < field.length; x++)
                for (int y = 0; y < Field.this.field[x].length; y++) {
                    Field.this.getCell(x, y).determineUpcomingState();
                }
        } else {
            for (int i = 1; i <= threadCount; i++) {
                int startOffset = ((i - 1) * colsPerThread) + ((i - 1 < extraCols) ? (i - 1) : extraCols);
                int count = (((i - 1) < extraCols) ? 1 : 0) + colsPerThread;

                final int threadNum = i;

                new Thread(() -> {
                    //iterate over given cells and calculate upcoming state
                    for (int x = startOffset; x < startOffset + count; x++)
                        for (int y = 0; y < Field.this.field[x].length; y++) {
                            Field.this.getCell(x, y).determineUpcomingState();
                        }

                    //mark self as not working anymore
                    workLatch.countDown();
                }).start();
            }

            try {
                workLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < Field.this.field[x].length; y++) {
                Field.this.getCell(x, y).commit();
            }
    }

    class IntHolder {
        public int i = 0;
    }

}