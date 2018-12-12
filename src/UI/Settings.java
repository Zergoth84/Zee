/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import UI.PrepareDevices;
import static UI.PrepareDevices.checkModules;
import static UI.PrepareDevices.console2;
import static UI.PrepareDevices.tabbedPane;
import static UI.Zee.backupFile;
import static UI.Zee.backupLocation;
import static UI.Zee.logsFile;
import static UI.Zee.logsLocation;
import static UI.Zee.moduleFile;
import static UI.Zee.modulesLocation;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.apache.commons.io.FileUtils;
//import static UI.UI.tabbedPane;

/**
 *
 * @author z.moszko
 */
public class Settings implements ActionListener {

    public static JPanel settingsPanel;
    public static JTextField custom, custom2, custom3, logsTField, modulesTField, backupTField;
    public static String output = "";
    private static JTextPane settingsConsole;
    private JScrollPane settingsScroll;
    private JButton autoscroll;

    Settings() {

    }

    public void settings() {
        settingsPanel = new JPanel();
        settingsPanel.setBackground(Color.GRAY);
        settingsPanel.setLayout(null);

        settingsConsole = new JTextPane();
        settingsConsole.setLayout(null);
        settingsConsole.setEditable(false);
        Color light_gray = new Color(0xF9F9F9);
        settingsConsole.setBackground(Color.lightGray);

        settingsScroll = new JScrollPane(settingsConsole);
        settingsScroll.setBackground(light_gray);
        settingsScroll.setBounds(25, 250, 750, 280);
        settingsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        logsTField = new JTextField();
        logsTField.setBackground(Color.lightGray);
        logsTField.setLayout(null);
        logsTField.setBounds(25, 60, 420, 20);
        logsTField.setText(logsLocation);

        modulesTField = new JTextField();
        modulesTField.setBackground(Color.lightGray);
        modulesTField.setLayout(null);
        modulesTField.setBounds(25, 105, 420, 20);
        modulesTField.setText(modulesLocation);

        backupTField = new JTextField();
        backupTField.setBackground(Color.lightGray);
        backupTField.setLayout(null);
        backupTField.setBounds(25, 155, 420, 20);
        backupTField.setText(backupLocation);

        JLabel label = new JLabel("<html><center>Logs location:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<br><br><br>Modules location:<br><br><br>Backup location:&nbsp&nbsp</center>", JLabel.LEFT);
        label.setBounds(25, 23, 300, 150);
        label.setForeground(Color.lightGray);

        JButton ok = new JButton("OK");
        ok.addActionListener(this);
        ok.setBounds(550, 100, 80, 30);
        ok.setActionCommand("ok");

        JButton clrscrn = new JButton("C");
        clrscrn.addActionListener(this);
        clrscrn.setMargin(new Insets(0, 0, 0, 0));
        clrscrn.setActionCommand("clear");
        clrscrn.setBounds(735, 230, 18, 18);

        autoscroll = new JButton("A");
        Font autoscrollFont = new Font("Arial", Font.BOLD, 12);
        autoscroll.setFont(autoscrollFont);
        autoscroll.setMargin(new Insets(0, 0, 0, 0));
        autoscroll.addActionListener(this);
        autoscroll.setActionCommand("autoscroll");
        autoscroll.setBounds(757, 230, 18, 18);
        autoscroll.setFocusPainted(false);

        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("img/trash2.png"));
        JButton deleteLogs = new JButton(imageIcon);
        deleteLogs.setBounds(450, 60, 20, 20);
        deleteLogs.addActionListener(this);
        deleteLogs.setActionCommand("delLogs");
        deleteLogs.setMargin(new Insets(0, 0, 0, 0));

        JButton deleteModules = new JButton(imageIcon);
        deleteModules.setBounds(450, 105, 20, 20);
        deleteModules.addActionListener(this);
        deleteModules.setActionCommand("delModules");

        JButton deleteBackup = new JButton(imageIcon);
        deleteBackup.setBounds(450, 155, 20, 20);
        deleteBackup.addActionListener(this);
        deleteBackup.setActionCommand("delBackup");

        ImageIcon blackRedBackground = new ImageIcon(getClass().getClassLoader().getResource("img/Background.jpg"));
        ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("img/Background.jpg"));
        JLabel backgroundLabel = new JLabel(blackRedBackground);
        backgroundLabel.setBounds(0, 0, Zee.WIDTH, Zee.HEIGHT);

        settingsPanel.add(label);
        settingsPanel.add(ok);
        settingsPanel.add(deleteLogs);
        settingsPanel.add(deleteModules);
        settingsPanel.add(deleteBackup);
        settingsPanel.add(autoscroll);
        settingsPanel.add(clrscrn);
        settingsPanel.add(logsTField);
        settingsPanel.add(modulesTField);
        settingsPanel.add(backupTField);
        settingsPanel.add(settingsScroll);
        settingsPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "action");
        settingsPanel.add(backgroundLabel);

    }

    private static void appendText(String text, Color c) {
        try {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            Document doc = settingsConsole.getDocument();
            doc.insertString(doc.getLength(), text, aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (logsTField.getText().isEmpty() == false && !logsTField.getText().equals(logsLocation)) {
            logsLocation = logsTField.getText();
            while (logsLocation.endsWith("\\") || logsLocation.endsWith("/")) {
                logsLocation = logsLocation.substring(0, logsLocation.length() - 1);
            }
            appendText("Location for logs files was changed to: " + logsLocation + "\n", Color.BLACK);
        }

        if (modulesTField.getText().equals(backupTField.getText())) {
            if (modulesTField.getText().isEmpty() == false) {
                appendText("Location for Modules and Backup files can't be the same\n", Color.BLACK);
                modulesTField.setText(modulesLocation);
                backupTField.setText(backupLocation);
            }
            //     floc.dispose();
            checkModules();
        } else {
            if (backupTField.getText().isEmpty() == false) {
                if (modulesTField.getText().isEmpty() == false && !modulesTField.getText().equals(modulesLocation)) {
                    modulesLocation = modulesTField.getText();
                    while (modulesLocation.endsWith("\\") || modulesLocation.endsWith("/")) {
                        modulesLocation = modulesLocation.substring(0, modulesLocation.length() - 1);
                    }
                    appendText("Location for modules files was changed to: " + modulesLocation + "\n", Color.BLACK);
                }
            } else {
                if (modulesTField.getText().equals(backupLocation)) {

                    appendText("Location for Modules and Backup files can't be the same\n", Color.BLACK);
                    modulesTField.setText(modulesLocation);
                } else {
                    modulesLocation = modulesTField.getText();
                    while (modulesLocation.endsWith("\\") || modulesLocation.endsWith("/")) {
                        modulesLocation = modulesLocation.substring(0, modulesLocation.length() - 1);
                    }

                    if (modulesTField.getText().equals(modulesLocation) == false) {
                    }
                }

            }

            if (modulesTField.getText().isEmpty() == false) {
                if (backupTField.getText().isEmpty() == false && !backupTField.getText().equals(backupLocation)) {
                    backupLocation = backupTField.getText();
                    while (backupLocation.endsWith("\\") || backupLocation.endsWith("/")) {
                        backupLocation = backupLocation.substring(0, backupLocation.length() - 1);
                    }
                    appendText("Location for backup files was changed to: " + backupLocation + "\n", Color.BLACK);

                }
            } else {
                if (backupTField.getText().equals(modulesLocation)) {
                    appendText("Location for Modules and Backup files can't be the same\n", Color.BLACK);
                    backupTField.setText(backupLocation);
                } else {
                    backupLocation = backupTField.getText();
                    while (backupLocation.endsWith("\\") || backupLocation.endsWith("/")) {
                        backupLocation = backupLocation.substring(0, backupLocation.length() - 1);
                    }
                    appendText("Location for backup files was changed to: " + backupLocation, Color.BLACK);
                }
            }
            checkModules();

        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(logsFile));
            writer.write(logsLocation);
            System.out.println(logsLocation);
            writer.flush();
            writer.close();
            writer = new BufferedWriter(new FileWriter(moduleFile));
            writer.write(modulesLocation);
            System.out.println(modulesLocation);
            writer.flush();
            writer.close();
            writer = new BufferedWriter(new FileWriter(backupFile));
            writer.write(backupLocation);
            System.out.println(backupLocation);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            //   Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }

//        modulesLabel.setText("Modules:    " + modulesLocation);
//        backupLabel.setText("Backup:      " + backupLocation);
//        logsLabel.setText("Logs:          " + logsLocation);
//        floc.dispose();
//        floc = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            update();
        }
        if (e.getActionCommand().equals("autoscroll")) {
            DefaultCaret caret = (DefaultCaret) settingsConsole.getCaret();
            if (autoscroll.getText().equals("M")) {
                autoscroll.setText("A");

                settingsConsole.setCaretPosition(settingsConsole.getDocument().getLength());
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                appendText("Autoscroll is turned on \n", Color.BLACK);
            } else {
                settingsConsole.setCaretPosition(settingsConsole.getCaretPosition());
                caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
                appendText("Autoscroll is turned off \n", Color.BLACK);
                autoscroll.setText("M");
            }

        }
        if (e.getActionCommand().equals("clear")){
            settingsConsole.setText("");
        }
        if (e.getActionCommand().equals("delLogs")) {
            File logsFile = new File(logsLocation);
            if (logsFile.exists()) {
                try {
                    //System.out.println(FileUtils.listFilesAndDirs(logsFile,TrueFileFilter.TRUE,TrueFileFilter.TRUE));
                    //  FileUtils.cleanDirectory(logsFile);
                    Zee.deleteDir(logsFile);
                } catch (IOException ex) {
                    Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                }
                appendText("Files from " + logsLocation + " directory removed \n", Color.BLACK);
            } else {
                appendText("Directory " + logsLocation + " does not exist \n", Color.RED);
            }

        }
        if (e.getActionCommand().equals("delModules")) {
            File moduleFile = new File(modulesLocation);
            if (moduleFile.exists()) {
                try {
                    Zee.deleteDir(moduleFile);
                    //FileUtils.cleanDirectory(file);
                    appendText("Files in " + modulesLocation + " directory removed \n", Color.BLACK);
                } catch (IOException ex) {
                    Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                appendText("Directory " + logsLocation + " does not exist \n", Color.RED);
            }

        }
        if (e.getActionCommand().equals("delBackup")) {
            File file = new File(backupLocation);
            if (file.exists()) {
                try {
                    FileUtils.cleanDirectory(file);
                    appendText("Files in " + backupLocation + " directory removed \n", Color.BLACK);

                } catch (IOException ex) {
                    Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);

                }
            } else {
                appendText("Directory " + logsLocation + " does note exist \n", Color.RED);
            }
        }
    }

}
