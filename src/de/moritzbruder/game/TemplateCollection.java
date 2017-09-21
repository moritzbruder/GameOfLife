package de.moritzbruder.game;

import de.moritzbruder.io.StringImport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Collection of Templates that come included with the program
 * @author Created by morit on 21.09.2017.
 */
public class TemplateCollection {

    private static final Template[] TEMPLATES = {
            new Template("Test", "20x20;+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,+")
    };

    private static class Template {

        private String name;
        private String content;

        public Template (String name, String content) {
            this.name = name;
            this.content = content;

        }

        public void applySelfToMenu (JMenu menu, Field field) {
            JMenuItem item = new JMenuItem(this.name);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        StringImport.apply(Template.this.content, field);
                    } catch (StringImport.FormatException exc) {
                        //This is no user-input so it is given that these are in the right format
                    }
                }
            });
            menu.add(item);

        }
    }

    public static void applyToMenu (JMenu menu, Field field) {
        for (int i = 0; i < TEMPLATES.length; i++) TEMPLATES[i].applySelfToMenu(menu, field);

    }

}
