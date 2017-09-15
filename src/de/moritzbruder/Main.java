package de.moritzbruder;

import de.moritzbruder.game.Field;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Field field = new Field(4, 1000000);

        //Measure time of 20 rounds
        long start = System.currentTimeMillis();
        for (int j = 0; j < 20; j++) {
            field.nextRound();
        }
        long multi = (System.currentTimeMillis() - start);

        System.out.print(multi);

    }
}
