package de.moritzbruder;

import de.moritzbruder.game.Field;
import de.moritzbruder.gui.FrameDisplayer;

/**
 * Used to launch the GUI
 */
public class Main {

    /**
     * Main Method, called when the program is launched
     * @param args Arguments from the command-line
     */
    public static void main(String[] args) {

        //Create new Field
        Field field = new Field(20, 20);

        //Show in Frame
        new FrameDisplayer(field);

    }
}