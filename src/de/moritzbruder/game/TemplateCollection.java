package de.moritzbruder.game;

import de.moritzbruder.io.StringImport;

import javax.swing.*;

/**
 * Collection of Templates that come included with the program
 * @author Created by morit on 21.09.2017.
 */
public class TemplateCollection {

    /**
     * Array w/ all presets that come with the program
     */
    private static final Template[] TEMPLATES = {
            new Template("Test", "20x20;+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+"),
            new Template("Blinker", "5x5;-,-,-,-,-,-,-,+,-,-,-,-,+,-,-,-,-,+,-,-,-,-,-,-,-"),
            new Template("Glider", "20x20;-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,+,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-"),
            new Template("Die Hard", "15x15;-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,+,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-")
    };

    /**
     * An Object representing a Template
     */
    private static class Template {

        /**
         * Name as shown in UI
         */
        private String name;

        /**
         * The String for this template as created by {@link de.moritzbruder.io.StringExport}
         */
        private String content;

        /**
         * Creates a new instance
         * @param name The desired name for this template {@link #name}
         * @param content {@link #content}
         */
        public Template (String name, String content) {
            this.name = name;
            this.content = content;

        }

        /**
         * Adds the template to the given Menu
         * @param menu The Menu to add this template to
         * @param field The Field that the Template should be applied to when the menu item is selected
         */
        private void applySelfToMenu (JMenu menu, Field field) {
            //Create item
            JMenuItem item = new JMenuItem(this.name);
            item.addActionListener(e -> {
                try {
                    //Apply the template to field
                    StringImport.apply(Template.this.content, field);
                } catch (StringImport.FormatException exc) {
                    //This is no user-input so it is given that these are in the right format
                }
            });
            menu.add(item);

        }
    }

    /**
     * Adds all {@link Template Templates} to the given Menu
     * @param menu The Menu that the templates should be added to
     * @param field The Field that the templates should be applied to
     */
    public static void applyToMenu (JMenu menu, Field field) {
        //Iterate through templates and apply
        for (Template template : TEMPLATES) template.applySelfToMenu(menu, field);


    }

}
