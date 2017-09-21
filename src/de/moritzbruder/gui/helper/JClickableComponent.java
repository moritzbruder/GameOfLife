package de.moritzbruder.gui.helper;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Just for better syntax. Like this only the needed methods have to be overridden
 * Created by Moritz Bruder on 20.09.2017.
 */
public class JClickableComponent extends JComponent implements MouseListener {

    /**
     * Creates a new instance and adds self as {@link MouseListener} to listen for clicks
     */
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
        //Show popupmenu
        if (e.isPopupTrigger())
            openPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Show popupmenu
        if (e.isPopupTrigger())
            openPopup(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * To be overridden. If mousePressed and mouseReleased are not overridden (or you call super in your subclass),
     * this will be called when it is appropriate to show a context-menu
     * @param e The MouseEvent that triggered the call
     */
    protected void openPopup(MouseEvent e){
    }
}
