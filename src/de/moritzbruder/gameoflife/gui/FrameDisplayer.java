package de.moritzbruder.gameoflife.gui;

import de.moritzbruder.gameoflife.game.Field;
import de.moritzbruder.gameoflife.gui.components.FieldComponent;
import de.moritzbruder.gameoflife.gui.components.StatsComponent;
import de.moritzbruder.gameoflife.gui.interaction.AutoRound;
import de.moritzbruder.gameoflife.helper.Holder;
import de.moritzbruder.gameoflife.io.Verbosity;

import javax.swing.*;

/**
 * A helper-class to create A frame that displays a Game Of Life
 * @author Created by Moritz Bruder on 15.09.2017.
 */
public class FrameDisplayer {

    /**
     * Points at the {@link Field}, that we're displaying with this {@link FrameDisplayer}
     */
    private Field field;

    /**
     * The Frame that is created by this helper class
     */
    private JFrame frame;

    /**
     * A timed loop that automatically triggrs the next round
     */
    private AutoRound autoRound;

    /**
     * Creates a new instance
     * @param field The Field that should be displayed
     */
    public FrameDisplayer (Field field) {
        this.field = field;
        this.autoRound = new AutoRound(field);

        //Make Window
        frame = new JFrame("Moritz Bruder's Game Of Life");
        frame.setSize(620, 875);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);

        /*   Input for round control   */

        //Button to proceed to next round
        JButton nextRoundButton = new JButton("Next Round");
        frame.add(nextRoundButton);
        nextRoundButton.setBounds(50, 600, 150, 30);

        //check Box to enabled automatic round-continuing
        JCheckBox autoRoundCheckBox = new JCheckBox("Auto-Round");
        frame.add(autoRoundCheckBox);
        autoRoundCheckBox.setBounds(250, 600, 150, 30);

        //Slider to choose speed/round-frequency
        JSlider slider = new JSlider(0, 1000 - 20);
        frame.add(slider);
        slider.setBounds(43, 650 , 514, 30);
        slider.setEnabled(false);
        slider.setValue(500);

        //Text showing frequency
        JLabel frequencyLabel = new JLabel("Frequency: 500ms");
        frame.add(frequencyLabel);
        frequencyLabel.setBounds(50, 670, 150, 30);

        //Text Showing how many cells are alive/dead
        JLabel statsLabel = new JLabel("N/A");
        frame.add(statsLabel);
        statsLabel.setBounds(50, 750, 500, 30);

        //Progress bar showing how many cell are alive/dead
        StatsComponent statsComp = new StatsComponent(field);
        frame.add(statsComp);
        statsComp.setBounds(50, 720, 500, 25);

        //FieldComponent
        FieldComponent fieldComp = new FieldComponent(this.field, frame);
        fieldComp.setBounds(50, 50, 500, 500);
        frame.add(fieldComp);

        //Field Size
        JLabel sizeLabel = new JLabel("Size: ");
        frame.add(sizeLabel);
        sizeLabel.setBounds(50, 550, 150, 30);


        /*   Controls logic   */

        //Show Frequency hint
        slider.addChangeListener(e -> {
            int rate = 1000 - slider.getValue();
            frequencyLabel.setText("Frequency: " + rate + "ms");
            autoRound.setRate(rate);

        });

        Holder<Boolean> autoRoundOn = new Holder<>(false);

        //Enable/disable slider and auto-round
        autoRoundCheckBox.addChangeListener(e -> {
            if (autoRoundOn.v == autoRoundCheckBox.isSelected()) return;

            autoRoundOn.v = autoRoundCheckBox.isSelected();
            Verbosity.shared.log("Auto-Round " + (autoRoundCheckBox.isSelected() ? "enabled" : "disabled") + ".");
            slider.setEnabled(autoRoundCheckBox.isSelected());
            nextRoundButton.setEnabled(!autoRoundCheckBox.isSelected());
            if (autoRoundCheckBox.isSelected()) autoRound.start();
            else autoRound.stop();

        });

        //Manually trigger next round
        nextRoundButton.addActionListener(e -> {
            Verbosity.shared.log("Manually triggered next round. " + (field.nextRound() ? "The field changed." : "No changes happened."));

        });

        Field.OnFieldChangedListener listener = () -> {
            fieldComp.repaint();
            statsComp.repaint();
            statsLabel.setText(field.getAliveCellCount() + " of " + field.getCellCount() + " cells are alive.");
            sizeLabel.setText("Size: " + field.getWidth() + "x" + field.getHeight());
        };

        //Subscribe to changes on the field & trigger listener for initial setup
        field.subscribe(listener);
        listener.onFieldChanged();

        //Show Window
        frame.setVisible(true);

    }



}
