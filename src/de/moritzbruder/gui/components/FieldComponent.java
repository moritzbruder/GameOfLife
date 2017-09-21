package de.moritzbruder.gui.components;

import de.moritzbruder.game.Cell;
import de.moritzbruder.game.Field;
import de.moritzbruder.gui.helper.JClickableComponent;
import de.moritzbruder.gui.interaction.FieldMenu;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Moritz Bruder on 20.09.2017.
 */
public class FieldComponent extends JClickableComponent {

    Field field;
    Frame frame;

    public FieldComponent(Field field, Frame frame) {
        super();
        this.field = field;
        this.frame = frame;

    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);
        int width = this.getWidth() - 1;
        for (int i = 0; i <= field.getWidth(); i++) {
            int x = (i * (this.getWidth() - 1)) / field.getWidth();
            g.drawLine(x, 0, x, this.getHeight());
        }
        for (int i = 0; i <= field.getHeight(); i++) {
            int y = (i * (this.getHeight() - 1)) / field.getHeight();
            g.drawLine(0, y, this.getWidth(), y);
        }

        g.setColor(Color.red);
        for (int x = 0; x < field.getWidth(); x++)
            for (int y = 0; y < field.getHeight(); y++) {
                Cell c = field.getCell(x, y);
                if (c.isAlive()) {
                    Rectangle cellRect = makeRectForCell(c);
                    g.fillRect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
                }
            }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Get cell and kill/makeAlive depending on current state
        Cell cell = field.getCell(getCellCoords(e.getPoint()));
        if (cell.isAlive()) field.killCell(cell);
        else field.makeCellComeAlive(cell);

        this.repaint();
    }

    @Override
    protected void openPopup(MouseEvent e) {
        FieldMenu menu = new FieldMenu(this.field, this.frame);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    private Cell.Position getCellCoords (Point pixelCoords) {
        int x = ((pixelCoords.x * field.getWidth()) / this.getWidth());
        int y = ((pixelCoords.y * field.getHeight()) / this.getHeight());
        return new Cell.Position(x, y);
    }

    private Rectangle makeRectForCell (Cell c) {
        int x = (c.getX() * (this.getWidth())) / field.getWidth();
        int y = (c.getY() * (this.getWidth())) / field.getWidth();
        int w = (this.getWidth()) / field.getWidth() - 1;
        int h = (this.getHeight()) / field.getHeight() - 1;
        return new Rectangle(x, y, w, h);
    }
}