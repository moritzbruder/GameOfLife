package de.moritzbruder.gui.interaction;

import de.moritzbruder.game.Field;
import de.moritzbruder.game.TemplateCollection;
import de.moritzbruder.gui.components.SizeDialog;
import de.moritzbruder.io.StringExport;
import de.moritzbruder.io.StringImport;
import de.moritzbruder.io.Verbosity;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;

/**
 * A context-menu that contains useful actions for a {@link Field}
 * @author Created by Moritz Bruder on 20.09.2017.
 */
public class FieldMenu extends JPopupMenu {

    /**
     * Creates a new instance
     * @param field The field that the operations should be executed on
     * @param parentFrame The Frame that this PopupMenu was triggered on
     */
    public FieldMenu(Field field, Frame parentFrame){

        //Add Item to kill all cells at once
        JMenuItem killAllItem = new JMenuItem("Kill All");
        killAllItem.addActionListener(e -> {
            Verbosity.shared.log("Killing all cells");
            field.forEach(cell -> field.killCell(cell));
        });
        add(killAllItem);

        //Add Item to open ResizeDialog for the given Field
        JMenuItem resizeItem = new JMenuItem("Resize");
        resizeItem.addActionListener(e -> SizeDialog.show(field, parentFrame));
        add(resizeItem);

        //Add Submenu for export-actions
        JMenu exportMenu = new JMenu("Export");

        JMenuItem exportClipboardItem = new JMenuItem("To Clipboard");
        exportClipboardItem.addActionListener(e -> {
            Verbosity.shared.log("Exported " + field + " to clipboard.");
            StringSelection selection = new StringSelection(StringExport.export(field));
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);

        });
        exportMenu.add(exportClipboardItem);

        //Add Item to export to file
        JMenuItem exportFileItem = new JMenuItem("To file");
        exportFileItem.addActionListener(e -> {
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
                    JOptionPane.showMessageDialog(parentFrame, "Saved to file!");
                    Verbosity.shared.log("Exported " + field + " to \"" + file.getAbsolutePath() + "\".");
                } catch (IOException exc) {
                    Verbosity.shared.log("Error while exporting " + field + " to \"" + file.getAbsolutePath() + "\".");
                    JOptionPane.showMessageDialog(parentFrame, "Error: Something went wrong while saving to the file :(");
                }
            }
        });
        exportMenu.add(exportFileItem);

        //add submenu
        add(exportMenu);

        //Add Submenu for import-actions
        JMenu importMenu = new JMenu("Import");

        //Add Item to import from a file
        JMenuItem importFileItem = new JMenuItem("From File");
        importFileItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "GameOfLife-File", "gol");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(parentFrame);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                try(BufferedReader fw = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
                    String input = "";
                    String cur;
                    while ((cur = fw.readLine()) != null) input += cur;

                    StringImport.apply(input, field);

                     Verbosity.shared.log("Imported from \"" + chooser.getSelectedFile().getAbsolutePath() + "\".");
                } catch (IOException exc) {
                    Verbosity.shared.log("Error while importing from \"" + chooser.getSelectedFile().getAbsolutePath() + "\":");
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(parentFrame, "Error: Something went wrong while reading the file :(");

                } catch (StringImport.FormatException exc) {
                    Verbosity.shared.log("Error while importing from \"" + chooser.getSelectedFile().getAbsolutePath() + "\":");
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(parentFrame, "The file's content is not readable :(");

                }
            }
        });
        importMenu.add(importFileItem);

        //Add Submenu with items to apply a template to the given field
        JMenu importCollectionMenu = new JMenu("From Pattern-Collection");
        TemplateCollection.applyToMenu(importCollectionMenu, field);
        importMenu.add(importCollectionMenu);

        //Add Item to input String directly
        JMenuItem importStringItem = new JMenuItem("From Text-Input");
        importStringItem.addActionListener(e -> {

            //Prompt user for input
            String input = JOptionPane.showInputDialog(parentFrame, "Game Of Life Input:");

            //Check if user pressed cancel
            if (input == null) return;

            //Try to apply input to field
            try {
                StringImport.apply(input, field);
                Verbosity.shared.log("Did import from input-prompt");

            } catch (StringImport.FormatException exc){
                Verbosity.shared.log("Error while exporting from input-prompt");
                JOptionPane.showMessageDialog(parentFrame, "Error: Invalid input (wrong format)");

            }

        });
        importMenu.add(importStringItem);

        //add submenu
        add(importMenu);


        //Add Item to simulate game until death
        JMenuItem simulateItem = new JMenuItem("Simulate to Death");
        simulateItem.addActionListener(e -> new Thread(() -> {
            Verbosity.shared.log("Simulating until all cells are dead.");

            long startTime = System.currentTimeMillis();
            int timeout = 1500; //1.5 sec

            long stepCount = 0;
            int maxAlive = 0;

            while (System.currentTimeMillis() - startTime < timeout && field.getAliveCellCount() != 0) {
                if (field.getAliveCellCount() > maxAlive) maxAlive = field.getAliveCellCount();
                field.nextRound();
                stepCount++;

            }

            if (field.getAliveCellCount() == 0) {
                //Simulation done
                Verbosity.shared.log("done simulating.");
                JOptionPane.showMessageDialog(parentFrame, "Done Simulating. It took " + stepCount + " rounds until all cells died. At most there were " + maxAlive + " cells alive.");

            } else {
                //Timeout
                Verbosity.shared.log("Error while simulating");
                JOptionPane.showMessageDialog(parentFrame, "Error (Timeout): It took longer than 1.5 seconds to simulate until all cells are dead.");
            }
        }).start());
        add(simulateItem);
    }

}
