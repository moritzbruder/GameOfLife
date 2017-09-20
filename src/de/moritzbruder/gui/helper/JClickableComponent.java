package de.moritzbruder.gui.helper;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Just for better syntax. Like this only the needed methods have to be overridden
 * Created by Moritz Bruder on 20.09.2017.
 */
public class JClickableComponent extends JComponent implements MouseListener {

    public JClickableComponent() {
        //Call super & add self as mouse listener
        super();
        this.addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            openPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            openPopup(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    protected void openPopup(MouseEvent e){
    }
}
