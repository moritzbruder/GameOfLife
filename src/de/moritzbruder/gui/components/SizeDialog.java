package de.moritzbruder.gui.components;

import de.moritzbruder.game.Field;

import javax.swing.*;
import java.awt.*;

/**
 * A Dialog that allows the User to change the size of the Game Field
 * @author Created by Moritz Bruder on 21.09.2017.
 */
public class SizeDialog {

    /**
     * Displays a dialog which allows the user to resize the given {@link Field}
     * @param field The Field that the user may change
     * @param parent The Frame the dialog is launched from (needed to stay in foreground)
     */
    public static void show (Field field, Frame parent) {
        //Make new Dialog with given size and title
        Dialog frame = new Dialog(parent);
        frame.setTitle("Resize Field");
        frame.setLayout(null);
        frame.setSize(300, 170);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //Add Label that says "Width:"
        JLabel widthLabel = new JLabel("Width:");
        frame.add(widthLabel);
        widthLabel.setBounds(20, 40, 60, 25);

        //Add TextField for user to input th desired width
        JNumericTextField widthField = new JNumericTextField(field.getWidth());
        frame.add(widthField);
        widthField.setBounds(100, 40, 180, 25);

        //Add Label that says "Height:"
        JLabel heightLabel = new JLabel("Height:");
        frame.add(heightLabel);
        heightLabel.setBounds(20, 75, 60, 25);

        //Add TextField for user to input desired height
        JNumericTextField heightField = new JNumericTextField(field.getHeight());
        frame.add(heightField);
        heightField.setBounds(100, 75, 180, 25);

        //Add "apply" button
        JButton okButton = new JButton("Apply");
        frame.add(okButton);
        okButton.setBounds(210, 125, 70, 25);

        //Add "cancel" button
        JButton cancelButton = new JButton("Cancel");
        frame.add(cancelButton);
        cancelButton.setBounds(120, 125, 80, 25);

        okButton.addActionListener(e -> {
            //Apply the size to the given field and hide the dialog
            field.resize(widthField.getValue(), heightField.getValue());
            frame.setVisible(false);

        });

        cancelButton.addActionListener(e -> {
            //Hide dialog
            frame.setVisible(false);

        });

        //Show dialog
        frame.setVisible(true);
    }

}
