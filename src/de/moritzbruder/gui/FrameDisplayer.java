package de.moritzbruder.gui;

import de.moritzbruder.game.Field;
import de.moritzbruder.gui.components.FieldComponent;
import de.moritzbruder.gui.components.StatsComponent;
import de.moritzbruder.gui.interaction.AutoRound;

import javax.swing.*;
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

        //Text Showing how many cells are alive/dead
        JLabel statsLabel = new JLabel("N/A");
        frame.add(statsLabel);
        statsLabel.setBounds(700, 250, 250, 30);

        //Progress bar showing how many cell are alive/dead
        StatsComponent statsComp = new StatsComponent(field);
        frame.add(statsComp);
        statsComp.setBounds(700, 220, 400, 25);

        //FieldComponent
        FieldComponent fieldComp = new FieldComponent(this.field);
        fieldComp.setBounds(50, 50, 500, 500);
        frame.add(fieldComp);

        //Field Size
        JLabel sizeLabel = new JLabel("Size: ");
        frame.add(sizeLabel);
        sizeLabel.setBounds(50, 550, 150, 30);


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
        nextRoundButton.addActionListener(e -> {
            System.out.println("NextRound-Button Clicked");
            field.nextRound();
            fieldComp.repaint();
        });


        //Subscribe to changes on the field
        field.subscribe(new Field.FieldDisplayDelegate() {
            @Override
            public void onRepaintRequired() {
                fieldComp.repaint();
                statsComp.repaint();
                statsLabel.setText(field.getAliveCellCount() + " of " + field.getCellCount() + " cells are alive.");
                sizeLabel.setText("Size: " + field.getWidth() + "x" + field.getHeight());
            }
        });

        //Show Window
        frame.setVisible(true);

    }



}
