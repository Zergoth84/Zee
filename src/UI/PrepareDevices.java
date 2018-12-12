/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package UI;

import static UI.DeviceUpdate.connected;
import static UI.Tools.execute;
import static UI.Tools.print;
import static UI.Zee.HEIGHT;
import static UI.Zee.WIDTH;
import static UI.Zee.backupLocation;
import static UI.Zee.logsLocation;
import static UI.Zee.modulesLocation;
//import static UI.Zee.appendText;

//import static UI.UI.tabbedPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class PrepareDevices implements ActionListener{

    public static JPanel preparePanel;
    private JButton pushIt, refreshModule, autoscroll;
    private ImageIcon rModule, rModule2;
    public static JCheckBox secimsfw, imsSettings, mainTabActivity, libSECIMSJni, libims_engine, customBox, customBox2, customBox3, backup;
    public static JTextField custom, custom2, custom3;
    private static JTextField logsTField, modulesTField, backupTField;
    public static JTextArea dArea2;
    private File moduleFile, backupFile, logsFile, dir, f0, f1, f2;
    public static String output = "";
    private JLabel modulesLabel, backupLabel, connectedDevicesLabel2, logsLabel, logoLabel2, consoleLabel2;
    public static JTabbedPane tabbedPane;
    public static JTextPane console2;
    private static JScrollPane scroll2;
    private  ChangeIcon changeIcon;
    public static boolean restore=false;

    public void prepareDevices() {
        preparePanel = new JPanel();
        preparePanel.setBackground(Color.lightGray);
        preparePanel.setLayout(null);
        changeIcon = new ChangeIcon();
//        
//        tabbedPane = new JTabbedPane();
//        mainPanel.add(tabbedPane);      


        Border border = BorderFactory.createLineBorder(Color.gray, 1);
        Font font = new Font("Arial", Font.BOLD, 13);

        pushIt = new JButton("PushiT");
        pushIt.setMargin(new Insets(1, 1, 1, 1));
        pushIt.addActionListener(this);
        pushIt.setActionCommand("pushModules");
        pushIt.setFocusPainted(false);
        pushIt.setBounds(320, 1, 90, 18);

        JButton initialize = new JButton("Initialize");
        initialize.addActionListener(this);
        initialize.setMargin(new Insets (0,0,0,0));
        initialize.setActionCommand("installExtras");
        initialize.setBounds(25, 190, 100, 35);
        initialize.setFocusPainted(false);
        
        JButton changeCsc = new JButton("Change CSC");
        changeCsc.addActionListener(this);
        changeCsc.setMargin(new Insets (0,0,0,0));
        changeCsc.setActionCommand("changeCsc");
        changeCsc.setBounds(125, 190, 100, 35);
        changeCsc.setFocusPainted(false);

        JButton autoAnswer = new JButton("AutoAnswer");
        autoAnswer.addActionListener(this);
        autoAnswer.setMargin(new Insets (0,0,0,0));
        autoAnswer.setActionCommand("autoAnswer");
        autoAnswer.setBounds(225, 190, 100, 35);
        autoAnswer.setFocusPainted(false);

        JButton cpModemTest = new JButton("CP Modem Test");
        cpModemTest.addActionListener(this);
        cpModemTest.setMargin(new Insets(0,0,0,0));
        cpModemTest.setActionCommand("cpModemTest");
        cpModemTest.setBounds(325, 190, 100, 35);
        cpModemTest.setFocusPainted(false);

        JButton cscExecutor = new JButton("CSCExecutor");
        cscExecutor.addActionListener(this);
        cscExecutor.setMargin(new Insets (0,0,0,0));
        cscExecutor.setActionCommand("cscExecutor");
        cscExecutor.setBounds(425, 190, 100, 35);
        cscExecutor.setFocusPainted(false);
        
        JButton rita = new JButton("Rita");
        rita.addActionListener(this);
        rita.setMargin(new Insets (0,0,0,0));
        rita.setActionCommand("cscExecutor");
        rita.setBounds(525, 190, 100, 35);
        rita.setFocusPainted(false);
        
        JButton restoreButton = new JButton("Restore");
        restoreButton.addActionListener(this);
        restoreButton.setActionCommand("restore");
        restoreButton.setBounds(625, 190, 100, 35);
        restoreButton.setFocusPainted(false);
        
        JButton clrscrn = new JButton("C");
        clrscrn.addActionListener(this);
        clrscrn.setMargin(new Insets(0,0,0,0));
        clrscrn.setActionCommand("clear");
        clrscrn.setBounds(735, 230, 18, 18);
        
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(this);
        refresh.setActionCommand("refresh");
        refresh.setBounds(690, 20, 80, 20);
        refresh.setFocusPainted(false);
        

        
        autoscroll = new JButton("A");
        Font autoscrollFont = new Font("Arial", Font.BOLD, 12);
        autoscroll.setFont(autoscrollFont);
        autoscroll.setMargin(new Insets(0, 0, 0, 0));
        autoscroll.addActionListener(this);
        autoscroll.setActionCommand("autoscroll");
        autoscroll.setBounds(757, 230, 18, 18);
        autoscroll.setFocusPainted(false);
        

        rModule = new ImageIcon(getClass().getClassLoader().getResource("img/refresh.png"));
        rModule2 = new ImageIcon(getClass().getClassLoader().getResource("img/refresh2.png"));
        refreshModule = new JButton(rModule);
        refreshModule.addActionListener(this);
        refreshModule.setActionCommand("refreshModule");
        refreshModule.setBackground(Color.lightGray);
        refreshModule.setBorder(null);
        refreshModule.setFocusPainted(false);
        refreshModule.setBounds(445, 25, 33, 33);
        refreshModule.setContentAreaFilled(false);

        backup = new JCheckBox("backup");
        backup.addActionListener(this);
        backup.setBounds(410, 1, 80, 20);
        backup.setContentAreaFilled(false);
        backup.setFocusPainted(false);
        
        secimsfw = new JCheckBox("secimsfw.apk");
        secimsfw.addActionListener(this);
        secimsfw.setBounds(320, 20, 125, 20);
        secimsfw.setContentAreaFilled(false);
        secimsfw.setFocusPainted(false);
        secimsfw.setForeground(Color.lightGray);

        imsSettings = new JCheckBox("ImsSettings.apk");
        imsSettings.addActionListener(this);
        imsSettings.setBounds(320, 40, 125, 20);
        imsSettings.setContentAreaFilled(false);
        imsSettings.setFocusPainted(false);
        imsSettings.setForeground(Color.lightGray);
        

        mainTabActivity = new JCheckBox("MainTabActivity.apk");
        mainTabActivity.addActionListener(this);
        mainTabActivity.setBounds(320, 60, 140, 20);
        mainTabActivity.setContentAreaFilled(false);
        mainTabActivity.setFocusPainted(false);
        mainTabActivity.setForeground(Color.lightGray);

        libSECIMSJni = new JCheckBox("libSECIMSJni.so");
        libSECIMSJni.addActionListener(this);
        libSECIMSJni.setBounds(320, 80, 140, 20);
        libSECIMSJni.setContentAreaFilled(false);
        libSECIMSJni.setFocusPainted(false);
        libSECIMSJni.setForeground(Color.lightGray);

        libims_engine = new JCheckBox("libims_engine.so");
        libims_engine.addActionListener(this);
        libims_engine.setBounds(320, 100, 140, 20);
        libims_engine.setContentAreaFilled(false);
        libims_engine.setFocusPainted(false);
        libims_engine.setForeground(Color.lightGray);

        customBox = new JCheckBox(":-)");
        customBox.addActionListener(this);
        customBox.setBounds(320, 160, 20, 20);
        customBox.setContentAreaFilled(false);
        customBox.setFocusPainted(false);
        customBox.setForeground(Color.lightGray);

        customBox2 = new JCheckBox("");
        customBox2.addActionListener(this);
        customBox2.setBounds(320, 140, 20, 20);
        customBox2.setContentAreaFilled(false);
        customBox2.setFocusPainted(false);

        customBox3 = new JCheckBox("");
        customBox3.addActionListener(this);
        customBox3.setBounds(320, 120, 20, 20);
        customBox3.setContentAreaFilled(false);
        customBox3.setFocusPainted(false);

        custom = new JTextField();
        custom.setLayout(null);
        custom.setBounds(340, 160, 130, 20);
        custom.setBackground(Color.LIGHT_GRAY);
        custom.setBorder(border);

        custom2 = new JTextField();
        custom2.setLayout(null);
        custom2.setBounds(340, 140, 130, 20);
        custom2.setBackground(Color.LIGHT_GRAY);
        custom2.setBorder(border);

        custom3 = new JTextField();
        custom3.setLayout(null);
        custom3.setBounds(340, 120, 130, 20);
        custom3.setBackground(Color.LIGHT_GRAY);
        custom3.setBorder(border);

        backup = new JCheckBox("backup");
        backup.addActionListener(this);
        backup.setBounds(410, 1, 80, 20);
        backup.setContentAreaFilled(false);
        backup.setFocusPainted(false);
        backup.setForeground(Color.lightGray);

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/Zedd.png"));
        logoLabel2 = new JLabel(icon);
        logoLabel2.setForeground(Color.BLACK);
        logoLabel2.setBounds(0, 0, 280, 200);
        logoLabel2.setOpaque(false);


        connectedDevicesLabel2 = new JLabel();
        connectedDevicesLabel2.setText("Connected Devices:");
        connectedDevicesLabel2.setForeground(Color.lightGray);
        connectedDevicesLabel2.setFont(font);
        connectedDevicesLabel2.setBounds(520, 20, 200, 20);
        connectedDevicesLabel2.setOpaque(false);
        
        Color light_gray = new Color(0xF9F9F9);
        
        dArea2 = new JTextArea();
        dArea2.setEditable(false);
        dArea2.setBounds(520, 40, 250, 130);
        dArea2.setBackground(Color.lightGray);
        
        
        consoleLabel2 = new JLabel();
        consoleLabel2.setText("Console:");
        consoleLabel2.setFont(font);
        consoleLabel2.setForeground(Color.lightGray);
        consoleLabel2.setBounds(27, 230, 200, 20);
        consoleLabel2.setOpaque(false);
        
        console2 = new JTextPane();
        console2.setLayout(null);
        //console.setLineWrap(true);
        console2.setEditable(false);
        console2.setBackground(Color.lightGray);
        
        scroll2 = new JScrollPane(console2);
        scroll2.setBounds(25, 250, 750, 280);
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        ImageIcon blackRedBackground = new ImageIcon(getClass().getClassLoader().getResource("img/Background.jpg"));
        JLabel backgroundLabel = new JLabel(blackRedBackground);
        backgroundLabel.setBounds(0, 0, WIDTH, HEIGHT);

        
        preparePanel.add(pushIt);
        preparePanel.add(initialize);
        preparePanel.add(changeCsc);
        preparePanel.add(refresh);
        preparePanel.add(autoAnswer);
        preparePanel.add(cpModemTest);
        preparePanel.add(cscExecutor);
        preparePanel.add(rita);
        preparePanel.add(restoreButton);
        preparePanel.add(clrscrn);
        preparePanel.add(refreshModule);
        preparePanel.add(backup);
        preparePanel.add(secimsfw);
        preparePanel.add(imsSettings);
        preparePanel.add(mainTabActivity);
        preparePanel.add(libSECIMSJni);
        preparePanel.add(libims_engine);
        preparePanel.add(customBox);
        preparePanel.add(customBox2);
        preparePanel.add(customBox3);
        preparePanel.add(custom);
        preparePanel.add(custom2);
        preparePanel.add(custom3);
        preparePanel.add(logoLabel2);
        preparePanel.add(consoleLabel2);
        preparePanel.add(dArea2);
        preparePanel.add(connectedDevicesLabel2);
        preparePanel.add(scroll2);
        preparePanel.add(autoscroll);
        preparePanel.add(backgroundLabel);

        f1 = new File("files/modulesLocation.txt");
        f2 = new File("files/backupLocation.txt");

    }

        //Metoda append do TextPane
    public static void appendText2(String text, Color c) {
        try {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            Document doc = console2.getDocument();
            doc.insertString(doc.getLength(), text, aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    public static void checkModules() {
        File f1 = new File(modulesLocation + "/libSECIMSJni.so");
        if (f1.exists()) {
            libSECIMSJni.setSelected(true);
        } else {
            libSECIMSJni.setSelected(false);
        }

        File f2 = new File(modulesLocation + "/MainTabActivity-debug.apk");
        File f2b = new File(modulesLocation + "/aligned_signed_secRCS_BB.apk");
        if (f2.exists() || f2b.exists()) {
            mainTabActivity.setSelected(true);
        } else {
            mainTabActivity.setSelected(false);
        }

        File f3 = new File(modulesLocation + "/secimsfw-debug.apk");
        if (f3.exists()) {
            secimsfw.setSelected(true);
        } else {
            secimsfw.setSelected(false);
        }

        File f5 = new File(modulesLocation + "/" + custom3.getText());
        if (f5.exists() && !"".equals(custom3.getText())) {
            customBox3.setSelected(true);
        } else {
            customBox3.setSelected(false);
        }

        ///File f6 = new File()
        File f6 = new File(modulesLocation + "/libims_engine.so");
        if (f6.exists()) {
            libims_engine.setSelected(true);
        } else {
            libims_engine.setSelected(false);
        }

        File f7 = new File(modulesLocation + "/" + custom2.getText());
        if (f7.exists() && !"".equals(custom2.getText())) {
            customBox2.setSelected(true);
        } else {
            customBox2.setSelected(false);
        }

        File f8 = new File(modulesLocation + "/ImsSettings.apk");
        if (f8.exists()) {
            imsSettings.setSelected(true);
        } else {
            imsSettings.setSelected(false);
        }

        File f9 = new File(modulesLocation + "/" + custom.getText());
        if (f9.exists() && !"".equals(custom.getText())) {
            customBox.setSelected(true);
        } else {
            customBox.setSelected(false);
        }

    }

    public void update() {

        if (logsTField.getText().isEmpty() == false) {
            logsLocation = logsTField.getText();
            while (logsLocation.endsWith("\\") || logsLocation.endsWith("/")) {
                logsLocation = logsLocation.substring(0, logsLocation.length() - 1);
            }
        }

        if (modulesTField.getText().equals(backupTField.getText())) {
            if (modulesTField.getText().isEmpty() == false) {
                appendText2("Location for Modules and Backup files can't be the same\n", Color.BLACK);
            }
            //floc.dispose();
           // checkModules();
        } else {
            if (backupTField.getText().isEmpty() == false) {
                if (modulesTField.getText().isEmpty() == false) {
                    modulesLocation = modulesTField.getText();
                    while (modulesLocation.endsWith("\\") || modulesLocation.endsWith("/")) {
                        modulesLocation = modulesLocation.substring(0, modulesLocation.length() - 1);
                    }
                }
            } else {
                if (modulesTField.getText().equals(backupLocation)) {

                    appendText2("Location for Modules and Backup files can't be the same\n", Color.BLACK);
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
                if (backupTField.getText().isEmpty() == false) {
                    backupLocation = backupTField.getText();
                    while (backupLocation.endsWith("\\") || backupLocation.endsWith("/")) {
                        backupLocation = backupLocation.substring(0, backupLocation.length() - 1);
                    }

                }
            } else {
                if (backupTField.getText().equals(modulesLocation)) {
                    appendText2("Location for Modules and Backup files can't be the same\n", Color.BLACK);
                } else {
                    backupLocation = backupTField.getText();
                    while (backupLocation.endsWith("\\") || backupLocation.endsWith("/")) {
                        backupLocation = backupLocation.substring(0, backupLocation.length() - 1);
                    }
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
            Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
        }

        modulesLabel.setText("Modules:    " + modulesLocation);
        backupLabel.setText("Backup:      " + backupLocation);
        logsLabel.setText("Logs:          " + logsLocation);
      //  floc.dispose();
        // floc = null;
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        try {
            if (action.equals("changeCsc")) {
                execute("changeCSC.bat",2);
            }
            if (action.equals("autoAnswer")) {
                execute("autoAnswer.bat",2);
            }
            if (action.equals("clear")) {
                console2.setText("");
            }
            if (action.equals("refreshModule")) {
                changeIcon.setButton(refreshModule);
                changeIcon.setIcon1(rModule);
                changeIcon.setIcon2(rModule2);
                changeIcon.setTime(100);
                Thread refMod = new Thread(changeIcon);
                refMod.start();
                checkModules();
            }
            if (action.equals("installExtras")) {
                InstallApplication install = new InstallApplication();
                install.setApplicationName("SettingsInitializer.apk");
                Thread thread = new Thread(install);
                thread.start();
//                InstallExtras install = new InstallExtras();
//                install.start();
            }
            if (action.equals("pushModules")) {
                PushModules t = new PushModules();
                t.start();
            }
            
            if (action.equals("restore")){
                restore=true;
                PushModules t = new PushModules();
                t.start();
            }
            
            if (action.equals("cscExecutor")){
                InstallApplication install = new InstallApplication();
                install.setApplicationName("csc_executor_signed.apk");
                Thread thread = new Thread(install);
                thread.start();
            }
            
            if (action.equals("autoscroll")) {
                DefaultCaret caret = (DefaultCaret)console2.getCaret();
                if (autoscroll.getText().equals("M")) {
                    autoscroll.setText("A");

                    console2.setCaretPosition(console2.getDocument().getLength());
                    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                    appendText2("Autoscroll is turned on \n", Color.BLACK);
                } else {
                    console2.setCaretPosition(console2.getCaretPosition());
                    caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
                    appendText2("Autoscroll is turned off \n", Color.BLACK);
                    autoscroll.setText("M");
                }

            }
            

        } catch (IOException ex) {
            Logger.getLogger(PrepareDevices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
