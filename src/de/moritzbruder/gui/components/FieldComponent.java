package de.moritzbruder.gui.components;

import de.moritzbruder.game.Cell;
import de.moritzbruder.game.Field;
import de.moritzbruder.gui.helper.JClickableComponent;
import de.moritzbruder.gui.interaction.FieldMenu;
import de.moritzbruder.io.Verbosity;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A {@link JClickableComponent} subclass that shows a given {@link Field}
 * @author Created by Moritz Bruder on 20.09.2017.
 */
public class FieldComponent extends JClickableComponent {

    /**
     * Simple class holding the offsets required to draw the field into a square view
     */
    private class DrawOffsets {

        private int drawHeight;
        private int drawWidth;

        private int horiOffset;
        private int vertOffset;

        public DrawOffsets () {
            //Make width & height
            drawHeight = FieldComponent.this.getHeight();
            drawWidth = FieldComponent.this.getWidth();

            //Change drawHeight to support non-square configs
            if (field.getHeight() < field.getWidth()) drawHeight = (drawWidth / field.getWidth()) * field.getHeight();
            if (field.getWidth() < field.getHeight()) drawWidth = (drawHeight / field.getHeight()) * field.getWidth();

            //Make draw-offsets
            horiOffset = (FieldComponent.this.getWidth() - drawWidth) / 2;
            vertOffset = (FieldComponent.this.getHeight() - drawHeight) / 2;
        }

        public int getDrawHeight() {
            return drawHeight;
        }

        public int getDrawWidth() {
            return drawWidth;
        }

        public int getHoriOffset() {
            return horiOffset;
        }

        public int getVertOffset() {
            return vertOffset;
        }
    }

    /**
     * Exception used to indicate that a pixel-position is outside the displayed field
     */
    private static class IndexOutsideFieldException extends Exception {
        public IndexOutsideFieldException () {
            super("The Position is outside the bounds of the field, but still within the view (aka on the border)");
        }
    }

    /**
     * The Field that should be displayed by this {@link FieldComponent}
     */
    Field field;

    /**
     * The {@link Frame} which contains this {@link FieldComponent}
     */
    Frame frame;

    /**
     * Creates a new instance
     * @param field {@link #field}
     * @param frame {@link #frame}
     */
    public FieldComponent(Field field, Frame frame) {
        super();
        this.field = field;
        this.frame = frame;

    }

    @Override
    public void paintComponent(Graphics g) {
        DrawOffsets drw = new DrawOffsets();

        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);

        //Draw vertical grid-lines
        for (int i = 0; i <= field.getWidth(); i++) {
            int x = (i * (drw.getDrawWidth() - 1)) / field.getWidth();
            g.drawLine(x + drw.getHoriOffset(), 0 + drw.getVertOffset(), x + drw.getHoriOffset(), this.getHeight() - drw.getVertOffset());
        }

        //Draw horizontal grid-lines
        for (int i = 0; i <= field.getHeight(); i++) {
            int y = (i * (drw.getDrawHeight() - 1)) / field.getHeight();
            g.drawLine(0 + drw.getHoriOffset(), y + drw.getVertOffset(), this.getWidth() - drw.getHoriOffset(), y + drw.getVertOffset());
        }

        //Draw alive cells in red
        g.setColor(Color.red);
        for (int x = 0; x < field.getWidth(); x++)
            for (int y = 0; y < field.getHeight(); y++) {
                Cell c = field.getCell(x, y);
                if (c.isAlive()) {
                    Rectangle cellRect = makeRectForCell(c, drw);
                    g.fillRect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
                }
            }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Get cell and kill/makeAlive depending on current state
        try {
            Cell cell = field.getCell(getCellCoords(e.getPoint(), new DrawOffsets()));
            if (cell.isAlive()) {
                Verbosity.shared.log("Manually killed " + cell);
                field.killCell(cell);

            }
            else {
                Verbosity.shared.log("Manually made " + cell + " come alive.");
                field.makeCellComeAlive(cell);

            }

            this.repaint();
        } catch (IndexOutsideFieldException exc) {
            //Nothing to do, we just ignore this click
        }
    }

    @Override
    protected void openPopup(MouseEvent e) {
        FieldMenu menu = new FieldMenu(this.field, this.frame);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    /**
     * Claculates which cell-coords occupy the point of the given coordinates
     * @param pixelCoords The Target, for which the cell should be found
     * @return The field.coordinates of the cell that contains the Point with the given coords
     */
    private Cell.Position getCellCoords (Point pixelCoords, DrawOffsets drawOffsets) throws IndexOutsideFieldException {
        int x = (((pixelCoords.x - drawOffsets.getHoriOffset()) * field.getWidth()) / drawOffsets.getDrawWidth());
        int y = (((pixelCoords.y - drawOffsets.getVertOffset()) * field.getHeight()) / drawOffsets.getDrawHeight());
        return new Cell.Position(x, y);
    }

    /**
     * Helper Method that calculates the Pixel outlines for a given cell
     * @param c The {@link Cell} that should be displayed
     * @return The Position and Size of a rectangle that may represent the given cell
     */
    private Rectangle makeRectForCell (Cell c, DrawOffsets drawOffsets) {
        int x = (c.getX() * (drawOffsets.getDrawWidth())) / field.getWidth();
        x += drawOffsets.getHoriOffset();
        int y = (c.getY() * (drawOffsets.getDrawHeight())) / field.getHeight();
        y += drawOffsets.getVertOffset();
        int w = (drawOffsets.getDrawWidth()) / field.getWidth() - 1;
        int h = (drawOffsets.getDrawHeight()) / field.getHeight() - 1;
        return new Rectangle(x, y, w, h);
    }
}