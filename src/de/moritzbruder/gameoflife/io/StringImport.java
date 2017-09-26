package de.moritzbruder.gameoflife.io;

import de.moritzbruder.gameoflife.game.Cell;
import de.moritzbruder.gameoflife.game.Field;

import java.nio.charset.UnmappableCharacterException;

/**
 * Helper-class used to apply a previously exported state onto a field
 * @author Created by Moritz Bruder on 21.09.2017.
 * @see StringExport
 */
public class StringImport {

    /**
     * Exception that is thrown, when a given String does not match the format.
     */
    public static class FormatException extends Exception {

        public FormatException () {
            super("The given String does not match the expected format.");
        }

    }

    /**
     * Parses the given {@code String} and applies the state represented by it onto the given {@link Field}
     * @param input The input-String (should be a String created by {@link StringExport}
     * @param field The Field that should have the state represented by the given String after this operation
     * @throws FormatException When the given {@code String} doesn't match the required format.
     */
    public static void apply (String input, Field field) throws FormatException {
        try {
            //Split String into size and cellState
            String[] in = input.split(";");
            String[] size = in[0].split("x");

            //Read width and height from size
            int width = Integer.valueOf(size[0]);
            int height = Integer.valueOf(size[1]);

            //Split cellStates and check if there is the right amount of info
            String[] cellState = in[1].split(",");
            if (cellState.length != width * height) throw new FormatException();

            //Apply width & height
            field.resize(width, height);

            //go through every cell and apply state from string
            for (int x = 0; x < width; x++)
                for (int y = 0; y < height; y++) {
                    //Get field for current coords
                    Cell c  = field.getCell(x, y);
                    //Read cell state for column x and row y and apply to field
                    String state = cellState[(x * height + y)];
                    if (state.equals("+")) field.makeCellComeAlive(c);
                    else if (state.equals("-")) field.killCell(c);
                    else throw new UnmappableCharacterException(state.charAt(0));
                }

            //Done
        } catch (Exception e) {
            //Something went wrong. This must be caused by wrong input format, so we throw a FormatException
            throw (FormatException) new FormatException().initCause(e);
        }

    }

}
