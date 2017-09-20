package de.moritzbruder.gui;

import de.moritzbruder.game.Cell;
import de.moritzbruder.game.Field;
import de.moritzbruder.gui.helper.ClickableComponent;
import de.moritzbruder.gui.interaction.AutoRound;
import de.moritzbruder.gui.interaction.FieldMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.*;

/**
 * Created by Moritz Bruder on 15.09.2017.
 */
public class FrameDisplayer {

    /**
     * Points at the {@link Field}, that we're displaying with this {@link FrameDisplayer}
     */
    Field field;

    JFrame frame;

    AutoRound autoRound;

    ThreadPoolExecutor drawUpdateQueue = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));

    public FrameDisplayer (Field field) {
        this.field = field;
        this.autoRound = new AutoRound(field);

        //Make Window
        frame = new JFrame("Moritz Bruder's Game Of Life");
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        /*   Input for round control   */

        //Button to proceed to next round
        JButton nextRoundButton = new JButton("Next Round");
        frame.add(nextRoundButton);
        nextRoundButton.setBounds(700, 100, 150, 30);

        //check Box to enabled automatic round-continueing
        JCheckBox autoRoundCheckBox = new JCheckBox("Auto-Round");
        frame.add(autoRoundCheckBox);
        autoRoundCheckBox.setBounds(900, 100, 150, 30);

        //Slider to choose speed/round-frequency
        JSlider slider = new JSlider(20, 2000);
        frame.add(slider);
        slider.setBounds(695, 150 , 405, 30);
        slider.setEnabled(false);
        slider.setValue(500);

        //Text showing frequency
        JLabel frequencyLabel = new JLabel("Frequency: 500ms");
        frame.add(frequencyLabel);
        frequencyLabel.setBounds(700, 180, 150, 30);

        //Add FieldPanel
        FieldPanel fieldComp = new FieldPanel(this.field);
        fieldComp.setBounds(50, 50, 500, 500);
        frame.add(fieldComp);



        /*   Controls logic   */

        //Show Frequency hint
        slider.addChangeListener(e -> {
            frequencyLabel.setText("Frequency: " + slider.getValue() + "ms");
            autoRound.setRate(slider.getValue());

        });

        //Enable/disable slider and auto-round
        autoRoundCheckBox.addChangeListener(e -> {
            slider.setEnabled(autoRoundCheckBox.isSelected());
            nextRoundButton.setEnabled(!autoRoundCheckBox.isSelected());
            //TODO; Enable auto-round
            if (autoRoundCheckBox.isSelected()) autoRound.start();
            else autoRound.stop();


        });

        //Manually trigger next round
        nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("NextRound-Button Clicked");
                field.nextRound();
                fieldComp.invalidate();
                fieldComp.repaint();
            }
        });


        //Subscribe to changes on the field
        field.subscribe(new Field.FieldDisplayDelegate() {
            @Override
            public void onRepaintRequired() {
                fieldComp.repaint();
            }
        });

        //Show Window
        frame.setVisible(true);

    }



}
