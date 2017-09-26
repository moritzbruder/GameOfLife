package de.moritzbruder.gameoflife.gui.components;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Extension of {@link JTextField} that is pecified to allow only input of numbers
 * Created by morit on 21.09.2017.
 */
public class JNumericTextField extends JTextField {

    /**
     * Flag that shows whether last input was empty field.
     */
    private boolean isEmpty = true;

    /**
     * Stores last input that was a valid number
     */
    private int lastValidNumber = 0;

    /**
     * Creates a new instance
     * @param num Number that should be in the Field when creating the field
     */
    public JNumericTextField (int num) {
        super("" + num);
        setNumericOnly();

    }

    /**
     * Creates a new instance
     */
    public JNumericTextField () {
        super();
        setNumericOnly();

    }

    /**
     * @return The current value that the user sees written in the field
     */
    public int getValue() {
        return lastValidNumber;
    }

    /**
     * Adds KeyListener that ensures, that no non-numeric characters are accepted
     */
    private void setNumericOnly () {
        //Create KeyListener
        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    //Store values (empty or number-value)
                    if (getText().isEmpty()) {
                        isEmpty = true;
                    } else {
                        lastValidNumber = Integer.valueOf(getText());
                        isEmpty = false;
                    }
                } catch (NumberFormatException nfe) {
                    //input was not a valid number so we return to the last known number
                    setText(isEmpty ? "" : "" + lastValidNumber);
                }
            }
        };
        this.addKeyListener(listener);

        //Invoke initially
        listener.keyReleased(null);
    }

}
