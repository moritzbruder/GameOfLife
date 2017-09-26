package de.moritzbruder.gameoflife.io;

import de.moritzbruder.gameoflife.game.Field;

/**
 * Helper class to convert {@link Field}-instances to Strings
 * @author Created by Moritz Bruder on 21.09.2017.
 * @see StringImport
 */
public class StringExport {

    /**
     * Converts the given {@link Field} to a {@code String} that represents the current state of the field.
     * @param field The field that should be converted to a {@code String}
     * @return The String representing the given field
     * @see StringImport
     */
    public static String export (Field field) {
        //Write width and height
        String result = field.getWidth() + "x" + field.getHeight() + ";";

        //Draw alive state for cells
        for (int x = 0; x < field.getWidth(); x++)
            for (int y = 0; y < field.getHeight(); y++) {
                result += (field.getCell(x, y).isAlive() ? "+" : "-") + ",";
            }

        //Cut off last comma
        result = result.substring(0, result.length()-1);

        //done
        return result;
    }

}
