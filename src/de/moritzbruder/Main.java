package de.moritzbruder;

import de.moritzbruder.game.Field;
import de.moritzbruder.gui.FrameDisplayer;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Field field = new Field(20, 20);
        field.getCell(2, 1).toggleAlive();
        field.getCell(2, 2).toggleAlive();
        field.getCell(2, 3).toggleAlive();
        FrameDisplayer frameDisplayer = new FrameDisplayer(field);

    }
}