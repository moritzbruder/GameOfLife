package de.moritzbruder.gui.components;

import de.moritzbruder.game.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by morit on 21.09.2017.
 */
public class SizeDialog {

    public static void show (Field field) {
        JFrame frame = new JFrame();
        frame.setTitle("Resize Field");
        frame.setLayout(null);
        frame.setSize(300, 170);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JLabel widthLabel = new JLabel("Width:");
        frame.add(widthLabel);
        widthLabel.setBounds(20, 15, 60, 25);

        JNumericTextField widthField = new JNumericTextField("" + field.getWidth());
        frame.add(widthField);
        widthField.setBounds(100, 15, 180, 25);

        JLabel heightLabel = new JLabel("Height:");
        frame.add(heightLabel);
        heightLabel.setBounds(20, 50, 60, 25);

        JNumericTextField heightField = new JNumericTextField("" + field.getHeight());
        frame.add(heightField);
        heightField.setBounds(100, 50, 180, 25);

        JButton okButton = new JButton("Apply");
        frame.add(okButton);
        okButton.setBounds(210, 100, 70, 25);

        JButton cancelButton = new JButton("Cancel");
        frame.add(cancelButton);
        cancelButton.setBounds(120, 100, 80, 25);

        okButton.addActionListener(e -> {
            field.resize(widthField.getValue(), heightField.getValue());
            frame.setVisible(false);

        });

        cancelButton.addActionListener(e -> {
            frame.setVisible(false);

        });

        frame.setVisible(true);
    }

}
