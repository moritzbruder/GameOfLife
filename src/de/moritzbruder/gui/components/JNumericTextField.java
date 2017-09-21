package de.moritzbruder.gui.components;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by morit on 21.09.2017.
 */
public class JNumericTextField extends JTextField {

    boolean isEmpty = true;
    int lastValidNumber = 0;

    public JNumericTextField (String text) {
        super(text);
        setNumericOnly();

    }

    public JNumericTextField () {
        super();
        setNumericOnly();

    }

    public int getValue() {
        return lastValidNumber;
    }

    public void setNumericOnly () {
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
                    if (getText().isEmpty()) {
                        isEmpty = true;
                    } else {
                        lastValidNumber = Integer.valueOf(getText());
                        isEmpty = false;
                    }
                } catch (NumberFormatException nfe) {
                    setText(isEmpty ? "" : "" + lastValidNumber);
                }
            }
        };
        this.addKeyListener(listener);
        listener.keyReleased(null);
    }

}
