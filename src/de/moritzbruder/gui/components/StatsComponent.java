package de.moritzbruder.gui.components;

import de.moritzbruder.game.Field;

import javax.swing.*;
import java.awt.*;

/**
 * A simple component that shows the ratio of dead and alive cells
 * Created by Moritz Bruder on 20.09.2017.
 */
public class StatsComponent extends JComponent {

    /**
     * The Field that this component shows stats for
     */
    Field field;

    /**
     * Creates a new instance
     * @param field Field that the {@link StatsComponent} should show stats for ({@link #field})
     */
    public StatsComponent (Field field) {
        this.field = field;

    }

    @Override
    protected void paintComponent(Graphics g) {
        //Draw Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        //Draw Bar indicating how many cells are alive
        g.setColor(Color.RED);
        float progress = (float) field.getAliveCellCount() / (float) field.getCellCount();
        g.fillRect(0, 0, (int) (getWidth() * progress), getHeight());
    }
}
