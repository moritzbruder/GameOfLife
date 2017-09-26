package de.moritzbruder;

import de.moritzbruder.game.Field;
import de.moritzbruder.gui.FrameDisplayer;
import de.moritzbruder.io.Verbosity;

import javax.swing.*;

/**
 * Used to launch the GUI
 */
public class Main {

    /**
     * Main Method, called when the program is launched
     * @param args Arguments from the command-line
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            //ok, we'll stick to the default.
        }

        for (String arg : args) {
            switch (arg) {
                case "--verbose":
                    Verbosity.shared.enable();
                    break;
            }
        }

        //Create new Field
        Field field = new Field(20, 20);

        //Show in Frame
        new FrameDisplayer(field);

    }
}