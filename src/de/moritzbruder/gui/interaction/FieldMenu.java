package de.moritzbruder.gui.interaction;

import de.moritzbruder.game.Field;

import javax.swing.*;

/**
 * Created by Moritz Bruder on 20.09.2017.
 */
public class FieldMenu extends JPopupMenu {

    public FieldMenu(Field field){
        JMenuItem killAllItem = new JMenuItem("Kill All");
        killAllItem.addActionListener(e -> {
            System.out.println("Killing all cells");
            field.forEach(cell -> field.killCell(cell));
        });
        add(killAllItem);

        JMenuItem resizeItem = new JMenuItem("Resize");
        resizeItem.addActionListener(e -> {
            System.out.println("Resizing");
            field.resize(50, 50);
        });
        add(resizeItem);
    }

}
