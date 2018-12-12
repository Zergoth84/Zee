/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.PrepareDevices.tabbedPane;
import static UI.Zee.folder;
import static UI.Zee.logsLocation;
//import static UI.UI.tabbedPane;
import static UI.ZipIt.appendText;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author z.moszko
 */
public class ZipAndSendUI implements ActionListener {

    public static String zipName = "";
    //private JFrame window;
    public static JPanel panelFtp;
    private JScrollPane scroll;
    private int WIDTH = 600;
    private int HEIGHT = 400;
    public static JTextField zipNameField, customPathName;
    private JButton ftp, zipftp, zip, autoscroll;
    public static JTextPane zipConsole;
    public static JCheckBox isFlagship, custom;
    public static JComboBox project, country, ftpServers;
    private String modelString = "", countryString = "";
    private ArrayList<String> modelsList = new ArrayList();
    private String[] countries;
    private String[] models = {""}, servers = {"", "Binaries", "Flagship", "Internal", "Projects"};
    public static String[] folders = {""};
    private JLabel modelsLabel, countryLabel, zipNameLabel, pathNameLabel, serverLabel;
    public static boolean sendToFTP = false;
    private static File countriesFile;

    public ZipAndSendUI() {

        int uiX = (int) Zee.getWindowsLocationX();
        int uiY = (int) Zee.getWindowsLocationY();
        try {

            project = new JComboBox(models);
            project.setBounds(20, 165, 300, 20);
            AutoCompletion.enable(project);
            project.setEditable(true);
            project.addActionListener(this);
            project.setFocusable(true);
            project.getEditor().getEditorComponent().setBackground(Color.lightGray);

            File file = new File("config/projects.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String x;
            while ((x = reader.readLine()) != null) {
                project.addItem(x);
            }

            countriesFile = new File("config/countries.txt");
            
            
            Scanner scanner2 = new Scanner(countriesFile);
            while (scanner2.hasNext()) {
                countryString = countryString + scanner2.next();
            }
            countries = countryString.split(",");

        } catch (IOException ex) {
            Logger.getLogger(ZipAndSendUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        //window = new JFrame("Zip&Send");
        // window.setBounds((int) uiX + (UI.getWindowsWidth() - WIDTH) / 2, uiY + (UI.getWindowHeight() - HEIGHT) / 2, WIDTH, HEIGHT);;
        // window.setResizable(false);
        // window.setVisible(true);
        // window.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/zands.png")));
        panelFtp = new JPanel();
        //panelFtp.setBackground(Color.lightGray);
        panelFtp.setLayout(null);
        panelFtp.setOpaque(true);
        zipConsole = new JTextPane();
        zipConsole.setLayout(null);
        zipConsole.setEditable(false);
        DefaultCaret caret = (DefaultCaret) zipConsole.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        scroll = new JScrollPane(zipConsole);
        //scroll.setBounds(20, 130, WIDTH - 50, HEIGHT - 180);
        scroll.setBounds(25, 250, 750, 280);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);

        modelsLabel = new JLabel("Project:");
        modelsLabel.setBounds(20, 145, 160, 20);
        modelsLabel.setForeground(Color.lightGray);

        countryLabel = new JLabel("Country:");
        countryLabel.setBounds(20, 185, 160, 20);
        countryLabel.setForeground(Color.lightGray);

        pathNameLabel = new JLabel("Path:");
        pathNameLabel.setBounds(20, 185, 160, 20);
        pathNameLabel.setVisible(false);
        pathNameLabel.setForeground(Color.lightGray);

        serverLabel = new JLabel("Server FTP:");
        serverLabel.setBounds(20, 145, 160, 20);
        serverLabel.setForeground(Color.lightGray);
        serverLabel.setVisible(false);

        zipNameLabel = new JLabel("Name:");
        zipNameLabel.setBounds(20, 105, 160, 20);
        zipNameLabel.setForeground(Color.lightGray);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                swichFields();
            }
        };

        UIManager.put("CheckBox.interiorBackground", Color.CYAN);
        
        custom = new JCheckBox("Custom");
        custom.setBounds(20, 35, 100, 20);
        custom.setFocusPainted(false);
        custom.setContentAreaFilled(false);
        custom.addActionListener(actionListener);
        custom.setForeground(Color.lightGray);
        custom.setBackground(Color.lightGray);

        isFlagship = new JCheckBox("Flagship");
        isFlagship.setBounds(150, 35, 100, 20);
        isFlagship.setFocusPainted(false);
        isFlagship.setContentAreaFilled(false);
        isFlagship.setForeground(Color.lightGray);
        

        ftpServers = new JComboBox(servers);
        ftpServers.setBounds(20, 165, 300, 20);
        ftpServers.addActionListener(this);
        ftpServers.setFocusable(true);
        ftpServers.setVisible(false);
        ftpServers.getEditor().getEditorComponent().setBackground(Color.lightGray);
        
        country = new JComboBox(countries);
        country.setBounds(20, 205, 300, 20);
        AutoCompletion.enable(country);
        country.setEditable(true);
        country.addActionListener(this);
        country.getEditor().getEditorComponent().setBackground(Color.lightGray);

        customPathName = new JTextField("");
        customPathName.setBounds(20, 205, 300, 20);
        customPathName.setLayout(null);
        customPathName.setBorder(border);
        customPathName.setBackground(Color.lightGray);


        zipNameField = new JTextField("");
        zipNameField.setLayout(null);
        zipNameField.setBounds(20, 125, 300, 20);
        zipNameField.setBorder(border);
        zipNameField.setBackground(Color.lightGray);

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

        zip = new JButton("ZIP");
        zip.setActionCommand("Zip");
        zip.setBounds(480, 150, 70, 35);
        zip.addActionListener(this);
        zip.setMargin(new Insets(0, 0, 0, 0));
        zip.setFocusPainted(false);

        ftp = new JButton("FTP");
        ftp.setActionCommand("ftp");
        ftp.setBounds(580, 150, 70, 35);
        ftp.addActionListener(this);
        ftp.setMargin(new Insets(0, 0, 0, 0));
        ftp.setFocusPainted(false);

        zipftp = new JButton("ZIP & FTP");
        zipftp.setActionCommand("zipFTP");
        zipftp.setBounds(680, 150, 70, 35);
        zipftp.addActionListener(this);
        zipftp.setMargin(new Insets(0, 0, 0, 0));
        zipftp.setFocusPainted(false);
        
        JButton addProject = new JButton("+");
        addProject.setActionCommand("addProject");
        addProject.setBounds(330,165,22,22);
        addProject.addActionListener(this);
        addProject.setFocusPainted(false);
        addProject.setMargin(new Insets(0,0,0,0));
        //addProject.setBackground(Color.lightGray);
        
        JButton addCountry = new JButton("+");
        addCountry.setActionCommand("addCountry");
        addCountry.setBounds(330,205,22,22);
        addCountry.addActionListener(this);
        addCountry.setFocusPainted(false);
        addCountry.setMargin(new Insets(0,0,0,0));
        
        ImageIcon blackRedBackground = new ImageIcon(getClass().getClassLoader().getResource("img/Background.jpg"));
        ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("img/Background.jpg"));
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel = new JLabel(blackRedBackground);
        backgroundLabel.setBounds(0, 0, Zee.WIDTH, Zee.HEIGHT);

        panelFtp.add(zipNameField);
        panelFtp.add(serverLabel);
        panelFtp.add(modelsLabel);
        panelFtp.add(zipNameLabel);
        panelFtp.add(countryLabel);
        panelFtp.add(pathNameLabel);
        panelFtp.add(country);
        panelFtp.add(customPathName);
        panelFtp.add(ftpServers);
        panelFtp.add(isFlagship);
        panelFtp.add(custom);
        panelFtp.add(project);
        panelFtp.add(ftp);
        panelFtp.add(zip);
        panelFtp.add(zipftp);
        panelFtp.add(scroll);
        panelFtp.add(autoscroll);
        panelFtp.add(addProject);
        panelFtp.add(addCountry);  
        panelFtp.add(clrscrn);
        panelFtp.add(backgroundLabel);

        swichFields();

    }

    private void swichFields() {
        if (custom.isSelected()) {
            country.setVisible(false);
            project.setVisible(false);
            ftpServers.setBackground(Color.lightGray);
            ftpServers.setVisible(true);
            isFlagship.setVisible(false);
            modelsLabel.setVisible(false);
            serverLabel.setVisible(true);
            pathNameLabel.setVisible(true);
            countryLabel.setVisible(false);
            customPathName.setVisible(true);
            
        } else {
            country.setVisible(true);
            isFlagship.setVisible(true);
            project.setVisible(true);
            ftpServers.setVisible(false);
            modelsLabel.setVisible(true);
            serverLabel.setVisible(false);
            pathNameLabel.setVisible(false);
            countryLabel.setVisible(true);
            customPathName.setVisible(false);
        }
    }

    class checkCustom extends Thread {

    }

    public void uiZIP() {

        panelFtp.repaint();
        panelFtp.revalidate();
    }

    private void zip() {
        zipName = Zee.logsLocation + "\\" + zipNameField.getText() + ".zip";
        ZipIt zipIt = new ZipIt();
        zipIt.generateFileList(new File(ZipIt.SOURCE_FOLDER));
        zipIt.setZipFile(zipName);
        Thread thread = new Thread(zipIt);
        thread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        //ZIP
        if (action.equals("Zip") && !zipNameField.getText().isEmpty()) {
            //Zip file
            zip();
        } else {
            if (zipNameField.getText().isEmpty() && action.equals("Zip")) {
                appendText("Please choose zip file name \n", Color.RED);
            }
        }
        //FTP
        File file = new File(logsLocation + "/" + zipNameField.getText() + ".zip");
        if (action.equals("ftp")) {
            if (!project.getSelectedItem().toString().equals("") && !country.getSelectedItem().toString().equals("") && !zipNameField.getText().isEmpty() && file.exists()) {
                TransferToFTP transfer = new TransferToFTP();
                Thread thread = new Thread(transfer);
                thread.start();
            } else {
                if (custom.isSelected()) {
                    folders = customPathName.getText().split("\\\\");
                    for (int i = 0; i < folders.length; i++) {
                        System.out.println(folders[i]);
                    }
                    TransferToFTP transfer = new TransferToFTP();
                    Thread thread = new Thread(transfer);
                    thread.start();
                } else {
                    if (zipNameField.getText().isEmpty()) {
                        appendText("Please choose Zip file name \n", Color.RED);
                    }
                    if (project.getSelectedItem().toString().equals("")) {
                        appendText("Please select project \n", Color.red);
                    }
                    if (country.getSelectedItem().toString().equals("")) {
                        appendText("Please select country \n", Color.red);
                    }
                    if (!zipNameField.getText().isEmpty() && !file.exists()) {
                        appendText("File " + logsLocation + "\\" + zipNameField.getText() + ".zip does not exist \n", Color.red);
                    }
                }
            }
        }
        //ZIP & FTP
        if (action.equals("zipFTP")) {
            if (!project.getSelectedItem().toString().equals("") && !country.getSelectedItem().toString().equals("") && !zipNameField.getText().isEmpty()) {
                sendToFTP = true;
                zip();
            } else {
                if (custom.isSelected()) {
                    sendToFTP = true;
                    zip();
                }

                if (zipNameField.getText().isEmpty()) {
                    appendText("Please choose Zip file name \n", Color.RED);
                }
                if (project.getSelectedItem().toString().equals("")) {
                    appendText("Please select project \n", Color.red);
                }
                if (country.getSelectedItem().toString().equals("")) {
                    appendText("Please select country \n", Color.red);
                }
            }
        }
        //autoscroll
        if (action.equals("autoscroll")) {
            DefaultCaret caret = (DefaultCaret) zipConsole.getCaret();
            if (autoscroll.getText().equals("M")) {
                autoscroll.setText("A");

                zipConsole.setCaretPosition(zipConsole.getDocument().getLength());
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                appendText("Autoscroll is turned on \n", Color.BLACK);
            } else {
                zipConsole.setCaretPosition(zipConsole.getCaretPosition());
                caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
                appendText("Autoscroll is turned off \n", Color.BLACK);
                autoscroll.setText("M");
            }

        }
        //clear console
        if (action.equals("clrscrn")){
            zipConsole.setText("");
        }
        if (action.equals("addCountry")){
            if (zipNameField.getText().isEmpty()){
                appendText("Name field can't be empty \n", Color.black);
            }else if (zipNameField.getText().contains(" ")){
                appendText("White symbols are not allowed in country name\n", Color.black);
            }else if (zipNameField.getText().contains(".")||zipNameField.getText().contains(",")||zipNameField.getText().contains("/")||zipNameField.getText().contains("\\")||zipNameField.getText().contains("?")||zipNameField.getText().contains(":")||zipNameField.getText().contains("!")||zipNameField.getText().contains("@")||zipNameField.getText().contains("#")||zipNameField.getText().contains("$")||zipNameField.getText().contains("%")||zipNameField.getText().contains("^")||zipNameField.getText().contains("&")||zipNameField.getText().contains("*")||zipNameField.getText().contains("(")||zipNameField.getText().contains(")")){
                appendText("Special characters (?,.!:\\/()@ etc0) are not allowed in country name \n", Color.black);
            }
            else {
                
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(countriesFile, true));
                writer.append(","+zipNameField.getText());
                //writer.write(","+zipNameField.getText());
                writer.flush();
                writer.close();
                Scanner scanner = new Scanner(countriesFile);
                while (scanner.hasNext()) {
                countryString = countryString + scanner.next();
                }
                countries = countryString.split(",");
                scanner.close();
                country.removeAllItems();
                for (int i=0;i<countries.length;i++){
                    country.addItem(countries[i]);
                }

                
            } catch (IOException ex) {
                Logger.getLogger(ZipAndSendUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
        if (action.equals("addProject")){
            System.out.println("kutas");
        }
        
        
        
        

    }

}
