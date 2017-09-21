package de.moritzbruder.gui.interaction;

import com.sun.xml.internal.ws.wsdl.writer.document.OpenAtts;
import de.moritzbruder.game.Field;
import de.moritzbruder.game.TemplateCollection;
import de.moritzbruder.gui.components.SizeDialog;
import de.moritzbruder.io.StringExport;
import de.moritzbruder.io.StringImport;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Moritz Bruder on 20.09.2017.
 */
public class FieldMenu extends JPopupMenu {

    public FieldMenu(Field field, Frame parentFrame){
        JMenuItem killAllItem = new JMenuItem("Kill All");
        killAllItem.addActionListener(e -> {
            System.out.println("Killing all cells");
            field.forEach(cell -> field.killCell(cell));
        });
        add(killAllItem);

        JMenuItem resizeItem = new JMenuItem("Resize");
        resizeItem.addActionListener(e -> {
            SizeDialog.show(field, parentFrame);
        });
        add(resizeItem);

        JMenu exportMenu = new JMenu("Exportieren");
        JMenuItem exportClipboardItem = new JMenuItem("In die Zwischenablage");
        exportClipboardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(StringExport.export(field));
            }
        });
        exportMenu.add(exportClipboardItem);
        JMenuItem exportFileItem = new JMenuItem("In Datei");
        exportFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "GameOfLife-File", "gol");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(parentFrame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String content = StringExport.export(field);
                    File file = chooser.getSelectedFile();
                    try(FileWriter fw = new FileWriter(file + (file.getName().endsWith(".gol") ? "" : ".gol"))) {
                        fw.write(content);
                        JOptionPane.showMessageDialog(parentFrame, "Gespeichert!");
                    } catch (IOException exc) {
                        JOptionPane.showMessageDialog(parentFrame, "Error: Etwas ist beim abspeichern der Datei fehlgeschlagen :(");
                    }
                }
            }
        });
        exportMenu.add(exportFileItem);
        add(exportMenu);

        JMenu importMenu = new JMenu("Importieren");
        JMenuItem importFileItem = new JMenuItem("Aus Datei");
        importFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "GameOfLife-File", "gol");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(parentFrame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String content = StringExport.export(field);
                    try(BufferedReader fw = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
                        String input = "";
                        String cur;
                        while ((cur = fw.readLine()) != null) input += cur;

                        StringImport.apply(input, field);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                        JOptionPane.showMessageDialog(parentFrame, "Error: Etwas ist beim lesen der Datei fehlgeschlagen :(");

                    } catch (StringImport.FormatException exc) {
                        exc.printStackTrace();
                        JOptionPane.showMessageDialog(parentFrame, "Der Inhalt der Datei ist nicht lesbar :(");

                    }
                }
            }
        });
        importMenu.add(importFileItem);

        JMenu importCollectionMenu = new JMenu("Aus Pattern-Sammlung");
        TemplateCollection.applyToMenu(importCollectionMenu, field);
        importMenu.add(importCollectionMenu);

        add(importMenu);
    }

}
