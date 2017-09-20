package de.moritzbruder.gui;

import de.moritzbruder.game.Field;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Moritz Bruder on 20.09.2017.
 */
public class StatsComponent extends JComponent {

    Field field;

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
