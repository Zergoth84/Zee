package UI;

import static UI.About.aboutPanel;
import static UI.PrepareDevices.preparePanel;
import static UI.Settings.settingsPanel;
import org.apache.commons.io.FileUtils;
import static UI.Tools.execute;
import static UI.ZipAndSendUI.panelFtp;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.apache.commons.io.filefilter.IOFileFilter;


public class Zee extends Canvas implements ActionListener{

    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    //private int ii;
    private static boolean mover = false, enter = false, push = false, system = false, anotherVideo = false;
    public static boolean isrestore;
    public HashMap sprites;
    public static String output = "", modulesLocation = "", backupLocation = "", logsLocation;
    private static JTextPane console;
    public JFrame floc;
    public static JTabbedPane tabbedPane;
    //private static JPanel panel2, panel3;
    public static JPanel panel, mainPanel;
    public static JTextArea dArea, dArea2;
    private JLabel modulesLabel, backupLabel, connectedDevicesLabel,consoleLabel, logsLabel, backgroundLabel;
    public static JLabel logoLabel;
    //public static JTextField custom, custom2, custom3, logsTField, modulesTField, backupTField;
    //public static JCheckBox secimsfw, libSECIMSJni, mainTabActivity, secPhone, customBox3, libims_engine, customBox2, imsSettings, customBox, backup, checkSilentLogs, checkDumpstate, checkIMS, checkVideo;
    public static JComboBox comboBox;
    public String line;
    private String[] strings = {"Auto answer", "Change CSC", "CP modem test", "Start Logs", "Stop Logs"};
    private JButton gatherApLogs, gatherSilentLogs, downloadIms,gatherDumpstate, play, install, photo, autoscroll, refreshModule;
    public static JButton record, flightMode, loc;
    private DefaultCaret caret;
    private static JScrollPane scroll;
    //public static JScrollPane scroll2;
    private Process gLogs;
    public static short bckup = 2;
    public static File moduleFile, backupFile, logsFile, dir, f0, f1, f2;
    public static JFrame okno;
    private ImageIcon photoFlash, photoImage;
    int x, y;
    public static ImageIcon stopMovie, startMovie, flightModeOn, flightModeOff, folder, folder2, rModule, rModule2;
    private  ChangeIcon changeIcon;
    private CheckCustomZip checkCustomZip;
    Process[] proces = new Process[10];

    //Colors
    public static final Color COLOR_GREEN = Color.decode("0x00BC16");
    public static final Color COLOR_BLOODY = Color.decode("0x990000");

    public Zee() {

        changeIcon = new ChangeIcon();
        okno = new JFrame("Zedd");
        setBounds(0, 0, WIDTH, HEIGHT);
        UIManager.put("TabbedPane.darkShadow", Color.pink);
        UIManager.put("TabbedPane.shadow", Color.pink);
        UIManager.put("TabbedPane.selected", Color.lightGray);
        tabbedPane = new JTabbedPane(){
                public Color getForegroundAt(int index){
                    if (getSelectedIndex()==index) return Color.BLACK;

                    return COLOR_BLOODY;
                }
        };
        tabbedPane.setForeground(COLOR_BLOODY);
        UIManager.put("TabbedPane.selected", Color.DARK_GRAY);
        UIManager.put("TabbedPane.contentAreaColor", Color.BLACK);
        mainPanel = (JPanel) okno.getContentPane();
        mainPanel.add(tabbedPane);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setOpaque(true);
        panel = new JPanel();
       
    

        okno.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/Zedd.png")));
        panel.setLayout(null);
        panel.setBorder(null);
        panel.setBackground(Color.GRAY);
        panel.setOpaque(true);

        okno.setBounds(0, 0, WIDTH, HEIGHT);
        okno.setLocationRelativeTo(null);
        okno.setVisible(true);
        okno.setDefaultCloseOperation(EXIT_ON_CLOSE);
        okno.setBackground(Color.GRAY);
        okno.setResizable(false);
        console = new JTextPane();
        console.setLayout(null);
        //console.setLineWrap(true);
        console.setEditable(false);
        Color light_gray = new Color(0xF9F9F9);
        console.setBackground(Color.lightGray);
        caret = (DefaultCaret) console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        dArea = new JTextArea();
        dArea.setEditable(false);
        dArea.setBounds(520, 40, 250, 130);
        dArea.setBackground(Color.lightGray);

        ImageIcon blackRedBackground = new ImageIcon(getClass().getClassLoader().getResource("img/Background.jpg"));
        ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("img/Background.jpg"));
        backgroundLabel = new JLabel(background);
        backgroundLabel = new JLabel(blackRedBackground);
        backgroundLabel.setBounds(0, 0, WIDTH, HEIGHT);


//        install = new JButton("PushiT");
//        install.setMargin(new Insets(1, 1, 1, 1));
//        install.addActionListener(this);
//        install.setActionCommand("install");
//        install.setBounds(320, 1, 90, 18);

//        play = new JButton("Run");
//        play.addActionListener(this);
//        play.setActionCommand("run");
//        play.setBounds(750, 538, 28, 20);
//        play.setMargin(new Insets(0, 0, 0, 0));
//        play.setFocusPainted(false);

        ImageIcon cancel = new ImageIcon(getClass().getClassLoader().getResource("img/x.gif"));
        JButton cancelLogs = new JButton(cancel);
        cancelLogs.addActionListener(this);
        cancelLogs.setActionCommand("cancel");
        cancelLogs.setBounds(25, 190, 21, 35);
        
        gatherApLogs = new JButton("Start AP Logs");
        gatherApLogs.addActionListener(this);
        gatherApLogs.setActionCommand("gatherlogs");
        gatherApLogs.setBounds(45, 190, 130, 35);
        gatherApLogs.setFocusPainted(false);

        gatherSilentLogs = new JButton("Start Silent Logs");
        gatherSilentLogs.addActionListener(this);
        gatherSilentLogs.setActionCommand("gatherSilentLogs");
        gatherSilentLogs.setBounds(175, 190, 145, 35);
        gatherSilentLogs.setFocusPainted(false);

        downloadIms = new JButton("Download IMS Logs");
        downloadIms.addActionListener(this);
        downloadIms.setActionCommand("downloadIms");
        downloadIms.setBounds(320, 190, 145, 35);
        downloadIms.setFocusPainted(false);
        
        gatherDumpstate = new JButton("Gather Dumpstate");
        gatherDumpstate.addActionListener(this);
        gatherDumpstate.setActionCommand("gatherDumpstate");
        gatherDumpstate.setBounds(465,190,145,35);
        gatherDumpstate.setFocusPainted(false);

        startMovie = new ImageIcon(getClass().getClassLoader().getResource("img/startMovie.gif"));
        stopMovie = new ImageIcon(getClass().getClassLoader().getResource("img/stopMovie.gif"));
        record = new JButton(startMovie);
        record.setMargin(new Insets(0, 0, 0, 0));
        record.addActionListener(this);
        record.setActionCommand("record");
        record.setBounds(610, 190, 83, 35);
        record.setFocusPainted(false);
        
        photoImage = new ImageIcon(getClass().getClassLoader().getResource("img/photo.gif"));
        photoFlash = new ImageIcon(getClass().getClassLoader().getResource("img/photo2.gif"));
        photo = new JButton(photoImage);
        photo.setMargin(new Insets(0, 0, 0, 0));
        photo.addActionListener(this);
        photo.setActionCommand("screenshot");
        photo.setBounds(693, 190, 83, 35);
        photo.setFocusPainted(false);
        
        JButton clrscrn = new JButton("C");
        clrscrn.addActionListener(this);
        clrscrn.setMargin(new Insets(0,0,0,0));
        clrscrn.setActionCommand("clear");
        clrscrn.setBounds(735, 230, 18, 18);
        clrscrn.setFocusPainted(false);
        
//        JButton installExtras = new JButton("Extras");
//        installExtras.addActionListener(this);
//        installExtras.setActionCommand("installExtras");
//        installExtras.setBounds(289, 190, 120, 35);
//        installExtras.setFocusPainted(false);

//        JButton IMSlogger = new JButton("Install IMS Logger");
//        IMSlogger.setMargin(new Insets(0, 0, 0, 0));
//        IMSlogger.addActionListener(this);
//        IMSlogger.setActionCommand("IMSLogger");
//        IMSlogger.setBounds(289, 190, 120, 35);
//        IMSlogger.setFocusPainted(false);



//        flightModeOn = new ImageIcon(getClass().getClassLoader().getResource("img/flightModeOn.gif"));
//        flightModeOff = new ImageIcon(getClass().getClassLoader().getResource("img/flightModeOff.gif"));
//        flightMode = new JButton(flightModeOff);
//        flightMode.addActionListener(this);
//        flightMode.setActionCommand("flightMode");
//        flightMode.setBounds(750, 560, 30, 30);
//        flightMode.setFocusPainted(false);
//        flightMode.setBorderPainted(false);
//        flightMode.setContentAreaFilled(false);



//        JButton remove = new JButton("ZIP&Send");
//        remove.setMargin(new Insets(0, 0, 0, 0));
//        remove.addActionListener(this);
//        // remove.setActionCommand("remove");
//        remove.setBounds(411, 190, 120, 35);
//        remove.setFocusPainted(false);


        autoscroll = new JButton("A");
        Font autoscrollFont = new Font("Arial", Font.BOLD, 12);
        autoscroll.setFont(autoscrollFont);
        autoscroll.setMargin(new Insets(0, 0, 0, 0));
        autoscroll.addActionListener(this);
        autoscroll.setActionCommand("autoscroll");
        autoscroll.setBounds(757, 230, 18, 18);
        autoscroll.setFocusPainted(false);

//        JButton about = new JButton("About");
//        about.addActionListener(this);
//        about.setActionCommand("about");
//        about.setBounds(655, 190, 120, 35);
//        about.setFocusPainted(false);
        //folder = new ImageIcon(getClass().getClassLoader().getResource("img/folder_icon.png"));
        //folder2 = new ImageIcon(getClass().getClassLoader().getResource("img/folder_icon2.png"));
        
        loc = new JButton(folder);
        loc.addActionListener(this);
        loc.setActionCommand("loc");
        loc.setBounds(28, 542, 32, 32);
        loc.setFocusPainted(false);
        loc.setContentAreaFilled(false);
        loc.setBorderPainted(false);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(this);
        refresh.setActionCommand("refresh");
        refresh.setBounds(690, 20, 80, 20);
        refresh.setFocusPainted(false);

//        rModule = new ImageIcon(getClass().getClassLoader().getResource("img/refresh.png"));
//        rModule2 = new ImageIcon(getClass().getClassLoader().getResource("img/refresh2.png"));
//        refreshModule = new JButton(rModule);
//        refreshModule.addActionListener(this);
//        refreshModule.setActionCommand("refreshModule");
//        refreshModule.setBackground(Color.GRAY);
//        refreshModule.setBorder(null);
//        refreshModule.setFocusPainted(false);
//        refreshModule.setBounds(445, 25, 33, 33);
//        refreshModule.setContentAreaFilled(false);

//        comboBox = new JComboBox(strings);
//        comboBox.setSelectedIndex(0);
//        comboBox.setBounds(590, 538, 150, 20);
//        comboBox.addActionListener(this);

//        secimsfw = new JCheckBox("secimsfw.apk");
//        secimsfw.addActionListener(this);
//        secimsfw.setBounds(320, 20, 125, 20);
//        secimsfw.setBackground(Color.BLUE);
//        secimsfw.setContentAreaFilled(false);
//        secimsfw.setFocusPainted(false);
//
//        imsSettings = new JCheckBox("ImsSettings.apk");
//        imsSettings.addActionListener(this);
//        imsSettings.setBounds(320, 40, 125, 20);
//        imsSettings.setContentAreaFilled(false);
//        imsSettings.setFocusPainted(false);
//
//        mainTabActivity = new JCheckBox("MainTabActivity.apk");
//        mainTabActivity.addActionListener(this);
//        mainTabActivity.setBounds(320, 60, 140, 20);
//        mainTabActivity.setContentAreaFilled(false);
//        mainTabActivity.setFocusPainted(false);
//
//        libSECIMSJni = new JCheckBox("libSECIMSJni.so");
//        libSECIMSJni.addActionListener(this);
//        libSECIMSJni.setBounds(320, 80, 140, 20);
//        libSECIMSJni.setContentAreaFilled(false);
//        libSECIMSJni.setFocusPainted(false);
//
//        libims_engine = new JCheckBox("libims_engine.so");
//        libims_engine.addActionListener(this);
//        libims_engine.setBounds(320, 100, 140, 20);
//        libims_engine.setContentAreaFilled(false);
//        libims_engine.setFocusPainted(false);
//
//        customBox3 = new JCheckBox("");
//        customBox3.addActionListener(this);
//        customBox3.setBounds(320, 120, 20, 20);
//        customBox3.setContentAreaFilled(false);
//        customBox3.setFocusPainted(false);
//
//        customBox2 = new JCheckBox("");
//        customBox2.addActionListener(this);
//        customBox2.setBounds(320, 140, 20, 20);
//        customBox2.setContentAreaFilled(false);
//        customBox2.setFocusPainted(false);
//
//        customBox = new JCheckBox(":-)");
//        customBox.addActionListener(this);
//        customBox.setBounds(320, 160, 20, 20);
//        customBox.setContentAreaFilled(false);
//        customBox.setFocusPainted(false);
//
//        backup = new JCheckBox("backup");
//        backup.addActionListener(this);
//        backup.setBounds(410, 1, 80, 20);
//        backup.setContentAreaFilled(false);
//        backup.setFocusPainted(false);

//        checkSilentLogs = new JCheckBox("Silent");
//        checkSilentLogs.addActionListener(this);
//        checkSilentLogs.setBounds(589, 557, 70, 20);
//        checkSilentLogs.setContentAreaFilled(false);
//        checkSilentLogs.setMargin(new Insets(0, 0, 0, 0));
//        checkSilentLogs.setFocusPainted(false);
//        checkSilentLogs.setEnabled(false);

//        checkDumpstate = new JCheckBox("Dumpstate");
//        checkDumpstate.addActionListener(this);
//        checkDumpstate.setBounds(587, 573, 100, 20);
//        checkDumpstate.setContentAreaFilled(false);
//        checkDumpstate.setFocusPainted(false);
//        checkDumpstate.setEnabled(false);
//
//        checkVideo = new JCheckBox("Video");
//        checkVideo.addActionListener(this);
//        checkVideo.setBounds(683, 557, 60, 20);
//        checkVideo.setContentAreaFilled(false);
//        checkVideo.setFocusPainted(false);
//        checkVideo.setEnabled(false);
//
//        checkIMS = new JCheckBox("IMS");
//        checkIMS.addActionListener(this);
//        checkIMS.setBounds(683, 573, 50, 20);
//        checkIMS.setContentAreaFilled(false);
//        checkIMS.setFocusPainted(false);
//        checkIMS.setEnabled(false);

        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        Font font = new Font("Arial", Font.BOLD, 13);

//        logsLabel = new JLabel();
//        logsLabel.setFont(font);
//        logsLabel.setForeground(Color.BLACK);
//        logsLabel.setBounds(65, 530, 400, 20);
//        logsLabel.setOpaque(false);
//        //logsLabel.setBorder(border);
//
//        modulesLabel = new JLabel();
//        modulesLabel.setFont(font);
//        modulesLabel.setForeground(Color.BLACK);
//        modulesLabel.setBounds(65, 550, 400, 20);
//        modulesLabel.setOpaque(false);
        //modulesLabel.setBorder(border);

//        backupLabel = new JLabel();
//        backupLabel.setFont(font);
//        backupLabel.setForeground(Color.BLACK);
//        backupLabel.setBounds(65, 570, 400, 20);
//        backupLabel.setOpaque(false);
        //backupLabel.setBorder(border);

        connectedDevicesLabel = new JLabel();
        connectedDevicesLabel.setText("Connected Devices:");
        connectedDevicesLabel.setFont(font);
        connectedDevicesLabel.setBounds(520, 20, 200, 20);
        connectedDevicesLabel.setOpaque(false);
        connectedDevicesLabel.setForeground(Color.lightGray);

        consoleLabel = new JLabel();
        consoleLabel.setText("Console:");
        consoleLabel.setFont(font);
        consoleLabel.setForeground(Color.lightGray);
        consoleLabel.setBounds(27, 230, 200, 20);
        consoleLabel.setOpaque(false);
        

        //consoleLabel.setBorder(border);

        //ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/pavLogo_winter.png"));
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/Zedd.png"));
        logoLabel = new JLabel(icon);
        logoLabel.setForeground(Color.BLACK);
        logoLabel.setBounds(0, 0, 280, 200);
        logoLabel.setOpaque(false);

//        custom = new JTextField();
//        custom.setLayout(null);
//        custom.setBounds(340, 160, 130, 20);
//        custom.setBackground(Color.LIGHT_GRAY);
//        custom.setBorder(border);
//
//        custom2 = new JTextField();
//        custom2.setLayout(null);
//        custom2.setBounds(340, 140, 130, 20);
//        custom2.setBackground(Color.LIGHT_GRAY);
//        custom2.setBorder(border);
//
//        custom3 = new JTextField();
//        custom3.setLayout(null);
//        custom3.setBounds(340, 120, 130, 20);
//        custom3.setBackground(Color.LIGHT_GRAY);
//        custom3.setBorder(border);

        scroll = new JScrollPane(console);
        scroll.setBounds(25, 250, 750, 280);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        
//        
//        scroll2 = new JScrollPane(console);
//        scroll2.setBounds(25, 250, 750, 280);
//        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        
        panel.add(scroll);
        panel.add(clrscrn);
        panel.add(autoscroll);
        panel.add(refresh);
       // panel.add(refreshModule);
        panel.add(gatherApLogs);
        panel.add(gatherSilentLogs);
        panel.add(downloadIms);
        panel.add(gatherDumpstate);
        panel.add(cancelLogs);
        //panel.add(installExtras);
        //panel.add(about);
        //panel.add(IMSlogger);
        panel.add(dArea);
       // panel.add(libSECIMSJni);
        panel.add(loc);
       //// panel.add(mainTabActivity);
       // panel.add(secimsfw);
       // panel.add(customBox3);
     //   panel.add(libims_engine);
       // panel.add(customBox2);
      //  panel.add(backup);
      //  panel.add(imsSettings);
      //  panel.add(customBox);
//        panel.add(modulesLabel);
 //       panel.add(backupLabel);
  //      panel.add(logsLabel);
        panel.add(connectedDevicesLabel);
        panel.add(consoleLabel);
        panel.add(logoLabel);
//        panel.add(custom);
//        panel.add(custom2);
//        panel.add(custom3);
        // panel.add(remove);
       // panel.add(comboBox);
//        panel.add(play);
//        panel.add(install);
        panel.add(record);
        panel.add(photo);
        //panel.add(checkSilentLogs);
       // panel.add(checkDumpstate);
       // panel.add(checkIMS);
       // panel.add(checkVideo);
       // panel.add(flightMode);
        panel.add(backgroundLabel);
        // panel.add(this);
        panel.repaint();

        f0 = new File("files/logsLocation.txt");
        f1 = new File("files/modulesLocation.txt");
        f2 = new File("files/backupLocation.txt");

        try {
            Scanner scaner = new Scanner(f1);
            modulesLocation = scaner.nextLine();
            scaner = new Scanner(f2);
            backupLocation = scaner.nextLine();
            scaner = new Scanner(f0);
            logsLocation = scaner.nextLine();
            scaner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
        }

        logsFile = new File("files/logsLocation.txt");
        moduleFile = new File("files/modulesLocation.txt");
        backupFile = new File("files/backupLocation.txt");

//        modulesLabel.setText("Modules:    " + modulesLocation);
//        backupLabel.setText("Backup:      " + backupLocation);
//        logsLabel.setText("Logs:          " + logsLocation);

        //exists();

        okno.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        requestFocus();
        // addMouseListener(this);

    }

    public static double getWindowsLocationX() {
        return okno.getLocationOnScreen().getX();
    }

    public static double getWindowsLocationY() {
        return okno.getLocationOnScreen().getY();
    }

    public static int getWindowsWidth() {
        return okno.getWidth();
    }

    public static int getWindowHeight() {
        return okno.getHeight();
    }

    //Metoda append do TextPane
    public static void appendText(String text, Color c) {
        try {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            Document doc = console.getDocument();
            doc.insertString(doc.getLength(), text, aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

//    public void exists() {
//        File f1 = new File(modulesLocation + "/libSECIMSJni.so");
//        if (f1.exists()) {
//            libSECIMSJni.setSelected(true);
//        } else {
//            libSECIMSJni.setSelected(false);
//        }
//
//        File f2 = new File(modulesLocation + "/MainTabActivity-debug.apk");
//        File f2b = new File(modulesLocation + "/aligned_signed_secRCS_BB.apk");
//        if (f2.exists() || f2b.exists()) {
//            mainTabActivity.setSelected(true);
//        } else {
//            mainTabActivity.setSelected(false);
//        }
//
//        File f3 = new File(modulesLocation + "/secimsfw-debug.apk");
//        if (f3.exists()) {
//            secimsfw.setSelected(true);
//        } else {
//            secimsfw.setSelected(false);
//        }
//
//        File f5 = new File(modulesLocation + "/" + custom3.getText());
//        if (f5.exists() && !"".equals(custom3.getText())) {
//            customBox3.setSelected(true);
//        } else {
//            customBox3.setSelected(false);
//        }
//
//        ///File f6 = new File()
//        File f6 = new File(modulesLocation + "/libims_engine.so");
//        if (f6.exists()) {
//            libims_engine.setSelected(true);
//        } else {
//            libims_engine.setSelected(false);
//        }
//
//        File f7 = new File(modulesLocation + "/" + custom2.getText());
//        if (f7.exists() && !"".equals(custom2.getText())) {
//            customBox2.setSelected(true);
//        } else {
//            customBox2.setSelected(false);
//        }
//
//        File f8 = new File(modulesLocation + "/ImsSettings.apk");
//        if (f8.exists()) {
//            imsSettings.setSelected(true);
//        } else {
//            imsSettings.setSelected(false);
//        }
//
//        File f9 = new File(modulesLocation + "/" + custom.getText());
//        if (f9.exists() && !"".equals(custom.getText())) {
//            customBox.setSelected(true);
//        } else {
//            customBox.setSelected(false);
//        }
//
//    }

    //metoda wczytywania obrazków
    public BufferedImage loadImage(String sciezka) {
        URL url = null;
        try {
            url = getClass().getClassLoader().getResource(sciezka);
            return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Przy otwieraniu " + sciezka + " jako " + url);
            System.out.println("Wystapil blad : " + e.getClass().getName() + "" + e.getMessage());
            System.exit(0);
            return null;
        }

    }

    public static File saveTemp(String name) {
        InputStream is = Class.class.getResourceAsStream(name);
        File tempFile = null;
        try {
            tempFile = File.createTempFile(name, ".bat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        copyInputStreamToFile(is, tempFile);
        return tempFile;
    }

    private static void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GatherLogs extends Thread {

        public void run() {
            try {
                String path = saveTemp("/script/gLogs.sh").getCanonicalPath();
                gLogs = Runtime.getRuntime().exec("bin/sh.exe " + path);
                Scanner s = new Scanner(gLogs.getInputStream());
                while (s.hasNextLine()) {
                    line = s.nextLine();
                    if (line.contains("") && !line.contains("Error")) {
                        appendText(line + "\n", Color.BLACK);
                    }
                    if (line.contains("Gathering logs finished")) {
                        CopyLogs copyLogs = new CopyLogs();
                        copyLogs.start();
                    }
                }
                gLogs.destroy();
                console.setCaretPosition(console.getDocument().getLength());

            } catch (IOException ex) {
                Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void deleteDir(File file) throws IOException {

        for (File c : file.listFiles()) {
            if (!c.equals(logsFile) && !c.equals(moduleFile) && !c.equals(backupFile)) {
                System.out.println("kuts " + c);
                //c.delete();
                if (c.isDirectory()) {
                    FileUtils.deleteDirectory(c);
                } else {
                    FileUtils.deleteQuietly(c);
                }

            }
        }
    }

    // obsługa przycisków 
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        try {
            if (action.equals("gatherSilentLogs")) {

                if (gatherSilentLogs.getText().equalsIgnoreCase("Start Silent Logs")) {

                    execute("startSilentLogs.bat",1);
                    gatherSilentLogs.setText("Stop Silent Logs");

                } else {
                    execute("stopSilentLogs.bat",1);
                    gatherSilentLogs.setText("Start Silent Logs");
                }
            }
            
            if (action.equals("downloadIms")) {
                execute("latestIMS.bat",1);
            }
            
            if (action.equals("gatherDumpstate")){
                execute("dumpstate.bat",1);
            }
            if (action.equals("clear")) {
                console.setText("");
            }
            if (action.equals("autoscroll")) {
                //DefaultCaret caret = (DefaultCaret)console.getCaret();
                if (autoscroll.getText().equals("M")) {
                    autoscroll.setText("A");

                    console.setCaretPosition(console.getDocument().getLength());
                    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                    appendText("Autoscroll is turned on \n", Color.BLACK);
                } else {
                    console.setCaretPosition(console.getCaretPosition());
                    caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
                    appendText("Autoscroll is turned off \n", Color.BLACK);
                    autoscroll.setText("M");
                }

            }
            if (action.equals("record")) {
                try {
                    Video.startRecordVideo();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

//            if (action.equals("flightMode")) {
//                if (flightMode.getIcon().equals(flightModeOff)) {
//                    flightMode.setIcon(flightModeOn);
//                } else {
//                    flightMode.setIcon(flightModeOff);
//                }
//            }

            if (action.equals("screenshot")) {
                changeIcon.setButton(photo);
                changeIcon.setIcon1(photoImage);
                changeIcon.setIcon2(photoFlash);
                changeIcon.setTime(600);
                Thread flash = new Thread(changeIcon);
                flash.start();
                for (int i = 0; i < DeviceUpdate.connected.length - 1; i = i + 2) {
                    if (DeviceUpdate.connected[i] != null) {
                        RunProcess runProcess = new RunProcess();
                        try {
                            runProcess.processString = saveTemp("/script/screenShot.bat").getCanonicalPath() + " " + DeviceUpdate.connected[i] + " " + logsLocation;
                        } catch (IOException ex) {
                            Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Thread thread = new Thread(runProcess);
                        thread.start();
                    }

                }

            }

//            if (comboBox.getSelectedItem().equals("Start Logs")) {
//                checkSilentLogs.setEnabled(true);
//                checkVideo.setEnabled(true);
//                checkIMS.setEnabled(false);
//                checkDumpstate.setEnabled(false);
//            } else if (comboBox.getSelectedItem().equals("Stop Logs")) {
//                checkSilentLogs.setEnabled(true);
//                checkVideo.setEnabled(true);
//                checkIMS.setEnabled(true);
//                checkDumpstate.setEnabled(true);
//            } else {
//                checkSilentLogs.setEnabled(false);
//                checkVideo.setEnabled(false);
//                checkIMS.setEnabled(false);
//                checkDumpstate.setEnabled(false);
//            }

            if (action.equals("run")) {
                Strings strings = new Strings();
                strings.start();
            }

            if (action.equals("IMSLogger")) {
                InstallLogger t = new InstallLogger();
                t.start();
            }
//
//            if (action.equals("refreshModule")) {
//                changeIcon.setButton(refreshModule);
//                changeIcon.setIcon1(rModule);
//                changeIcon.setIcon2(rModule2);
//                changeIcon.setTime(100);
//                Thread refMod = new Thread(changeIcon);
//                refMod.start();
//               // exists();
//            }

//            if (action.equals("installExtras")) {
//                InstallExtras install = new InstallExtras();
//                install.start();
//            }

            if (action.equals("gatherlogs")) {
                if (gatherApLogs.getText().equals("Start AP Logs")) {
                    GatherLogs getLogs = new GatherLogs();
                    getLogs.start();
                    gatherApLogs.setText("Stop AP Logs");
                } else {
                    gatherApLogs.setText("Start AP Logs");
                    if (autoscroll.getText().equals("A")) {
                        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
                        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                    }

                    OutputStream out = gLogs.getOutputStream();
                    try {
                        out.write("\n".getBytes());
                        out.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            if (action.equals("cancel")) {
                if (gLogs != null) {
                    appendText("\n Gathering AP logs canceled by user \n", Color.BLACK);
                    gLogs.destroy();
                    gatherApLogs.setText("Start AP Logs");
                } else {
                    System.err.println("No gather logs started");
                }

            }

            if (action.equals("about")) {
                about();
            }
       // if (action.equals("ZIP&Send")) {
//            ZipAndSendUI zipAndSendUI = new ZipAndSendUI();
//            zipAndSendUI.uiZIP();
            // Thread thread = new Thread(checkCustomZip);
            // thread.start();
            // remove();
            //appendText("Backup files REMOVED! \n", Color.BLACK);
            //console.append("Backup files REMOVED! \n");
            //}
//            if (action.equals("loc") && floc == null) {
//                changeIcon.setButton(loc);
//                changeIcon.setIcon1(folder);
//                changeIcon.setIcon2(folder2);
//                changeIcon.setTime(100);
//                Thread thread = new Thread(changeIcon);
//                thread.start();
//                floc();
//
//            }

            if (action.equals("install")) {
                PushModules t = new PushModules();
                t.start();
            }

            final IOFileFilter myFilter = new IOFileFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return true;
                }

                @Override
                public boolean accept(File file) {
                    return true;

                }
            };

            // File menager
            if (action.equals("delLogs")) {
                if (logsFile.exists()) {
                    try {
                    //System.out.println(FileUtils.listFilesAndDirs(logsFile,TrueFileFilter.TRUE,TrueFileFilter.TRUE));
                        //  FileUtils.cleanDirectory(logsFile);
                        deleteDir(logsFile);
                    } catch (IOException ex) {
                        Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appendText("Files from " + logsLocation + " directory removed \n", Color.BLACK);
                } else {
                    appendText("Directory " + logsLocation + " does not exist \n", Color.RED);
                }

            }
            if (action.equals("delModules")) {
                if (moduleFile.exists()) {
                    try {
                        deleteDir(moduleFile);
                        //FileUtils.cleanDirectory(file);
                        appendText("Files in " + modulesLocation + " directory removed \n", Color.BLACK);
                    } catch (IOException ex) {
                        Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    appendText("Directory " + logsLocation + " does not exist \n", Color.RED);
                }

            }
            if (action.equals("delBackup")) {
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

            if (action.equals("ok")) {
               // update();
            }
        } catch (IOException ex) {
            Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // About VoLTE APK
    public void about() {
        JFrame About = new JFrame("About VoLTE APK");
        Toolkit zestaw = Toolkit.getDefaultToolkit();
        Dimension wymiary = zestaw.getScreenSize();
        About.setBounds(wymiary.width / 2 - 200, wymiary.height / 2 - 150, 400, 300);
        About.setResizable(false);
        About.setVisible(true);
        JPanel panel3 = (JPanel) About.getContentPane();
        ImageIcon imageicon = new ImageIcon(getClass().getClassLoader().getResource("img/pavLogo.png"));
        About.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/pavLogo.jpg")));
        JLabel label = new JLabel("<html><center>Zedd Version 1.0 <br><br>Created By<b> Zbigniew Moszko</b><br><br>z.moszko@samsung.com<br><br>zbigniew.moszko@gmail.com</center>", imageicon, JLabel.CENTER);
        panel3.add(label);
        panel3.setBackground(Color.GRAY);
        label.setAlignmentY(TOP_ALIGNMENT);

    }

    // file location
//    private void floc() {
//        floc = new JFrame("File location");
//        floc.setBounds((int) okno.getLocationOnScreen().getX() + (okno.getWidth() - 500) / 2, (int) okno.getLocationOnScreen().getY() + (okno.getHeight() - 200) / 2, 500, 200);
//        floc.setResizable(false);
//        floc.setVisible(true);
//        floc.setAlwaysOnTop(true);
//        floc.setLayout(null);
//        floc.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/folder_icon.png")));
//        panel2 = (JPanel) floc.getContentPane();
//        panel2.setBackground(Color.GRAY);
//
//        floc.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                floc = null;
//            }
//        });
//
//        logsTField = new JTextField();
//        logsTField.setLayout(null);
//        logsTField.setBounds(20, 20, 420, 20);
//        logsTField.setText(logsLocation);
//
//        modulesTField = new JTextField();
//        modulesTField.setLayout(null);
//        modulesTField.setBounds(20, 65, 420, 20);
//        modulesTField.setText(modulesLocation);
//
//        backupTField = new JTextField();
//        backupTField.setLayout(null);
//        backupTField.setBounds(20, 115, 420, 20);
//        backupTField.setText(backupLocation);
//
//        JLabel label = new JLabel("<html><center>New logs location:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<br><br><br>New modules location:<br><br><br>New backup location:&nbsp&nbsp</center>", JLabel.LEFT);
//        label.setBounds(22, -17, 300, 150);
//
//        JButton ok = new JButton("OK");
//        ok.addActionListener(this);
//        ok.setBounds(220, 140, 60, 20);
//        ok.setActionCommand("ok");
//
//        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("img/trash2.png"));
//        JButton deleteLogs = new JButton(imageIcon);
//        deleteLogs.setBounds(450, 20, 20, 20);
//        deleteLogs.addActionListener(this);
//        deleteLogs.setActionCommand("delLogs");
//        deleteLogs.setMargin(new Insets(0, 0, 0, 0));
//
//        JButton deleteModules = new JButton(imageIcon);
//        deleteModules.setBounds(450, 65, 20, 20);
//        deleteModules.addActionListener(this);
//        deleteModules.setActionCommand("delModules");
//
//        JButton deleteBackup = new JButton(imageIcon);
//        deleteBackup.setBounds(450, 115, 20, 20);
//        deleteBackup.addActionListener(this);
//        deleteBackup.setActionCommand("delBackup");
//
//        panel2.add(label);
//        panel2.add(ok);
//        panel2.add(deleteLogs);
//        panel2.add(deleteModules);
//        panel2.add(deleteBackup);
//        panel2.add(logsTField);
//        panel2.add(modulesTField);
//        panel2.add(backupTField);
//
//        panel2.getActionMap().put("action", new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                update();
//            }
//        });
//
//        panel2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "action");
//
//    }

//instalacja IMS loggera
    public class InstallLogger extends Thread {

        public void run() {
            try {
                Process p = Runtime.getRuntime().exec(saveTemp("/script/Logger.bat").getCanonicalPath());
                Scanner s = new Scanner(p.getInputStream());
                while (s.hasNextLine()) {
                    appendText(s.nextLine() + "\n", Color.BLACK);
                }
                p.destroy();
            } catch (IOException ex) {
                Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

//    public void update() {
//
//        if (logsTField.getText().isEmpty() == false) {
//            logsLocation = logsTField.getText();
//            while (logsLocation.endsWith("\\") || logsLocation.endsWith("/")) {
//                logsLocation = logsLocation.substring(0, logsLocation.length() - 1);
//            }
//        }
//
//        if (modulesTField.getText().equals(backupTField.getText())) {
//            if (modulesTField.getText().isEmpty() == false) {
//                appendText("Location for Modules and Backup files can't be the same\n", Color.BLACK);
//            }
//            floc.dispose();
//            exists();
//        } else {
//            if (backupTField.getText().isEmpty() == false) {
//                if (modulesTField.getText().isEmpty() == false) {
//                    modulesLocation = modulesTField.getText();
//                    while (modulesLocation.endsWith("\\") || modulesLocation.endsWith("/")) {
//                        modulesLocation = modulesLocation.substring(0, modulesLocation.length() - 1);
//                    }
//                }
//            } else {
//                if (modulesTField.getText().equals(backupLocation)) {
//
//                    appendText("Location for Modules and Backup files can't be the same\n", Color.BLACK);
//                } else {
//                    modulesLocation = modulesTField.getText();
//                    while (modulesLocation.endsWith("\\") || modulesLocation.endsWith("/")) {
//                        modulesLocation = modulesLocation.substring(0, modulesLocation.length() - 1);
//                    }
//
//                    if (modulesTField.getText().equals(modulesLocation) == false) {
//                    }
//                }
//
//            }
//
//            if (modulesTField.getText().isEmpty() == false) {
//                if (backupTField.getText().isEmpty() == false) {
//                    backupLocation = backupTField.getText();
//                    while (backupLocation.endsWith("\\") || backupLocation.endsWith("/")) {
//                        backupLocation = backupLocation.substring(0, backupLocation.length() - 1);
//                    }
//
//                }
//            } else {
//                if (backupTField.getText().equals(modulesLocation)) {
//                    appendText("Location for Modules and Backup files can't be the same\n", Color.BLACK);
//                } else {
//                    backupLocation = backupTField.getText();
//                    while (backupLocation.endsWith("\\") || backupLocation.endsWith("/")) {
//                        backupLocation = backupLocation.substring(0, backupLocation.length() - 1);
//                    }
//                }
//            }
//            exists();
//
//        }
//        BufferedWriter writer;
//        try {
//            writer = new BufferedWriter(new FileWriter(logsFile));
//            writer.write(logsLocation);
//            System.out.println(logsLocation);
//            writer.flush();
//            writer.close();
//            writer = new BufferedWriter(new FileWriter(moduleFile));
//            writer.write(modulesLocation);
//            System.out.println(modulesLocation);
//            writer.flush();
//            writer.close();
//            writer = new BufferedWriter(new FileWriter(backupFile));
//            writer.write(backupLocation);
//            System.out.println(backupLocation);
//            writer.flush();
//            writer.close();
//        } catch (IOException ex) {
//            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        modulesLabel.setText("Modules:    " + modulesLocation);
//        backupLabel.setText("Backup:      " + backupLocation);
//        logsLabel.setText("Logs:          " + logsLocation);
//        floc.dispose();
//        floc = null;
//    }

    private class CopyLogs extends Thread {

        public void run() {
            for (int i = 0; i < DeviceUpdate.connected.length - 1; i = i + 2) {
                if (DeviceUpdate.connected[i] != null) {
                    appendText("Copy Logs from " + DeviceUpdate.connected[i] + " to  " + logsLocation + "\n", Color.BLACK);
                    try {
                        RunProcess runProcess = new RunProcess();
                        runProcess.processString = saveTemp("/script/copyLogs.bat").getCanonicalPath() + " " + DeviceUpdate.connected[i] + " " + logsLocation;
                        Thread thread = new Thread(runProcess);
                        thread.start();

                    } catch (IOException ex) {
                        Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        console.setCaretPosition(console.getDocument().getLength());

        //strategia.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void start() {
        DeviceUpdate update = new DeviceUpdate();
        update.start();

    }

    private static void insertTabs(){
        tabbedPane.insertTab("Prepare Devices                                ", null, preparePanel, null, 0);
        tabbedPane.insertTab("Gather Logs                                ", null, panel, null, 1);
        tabbedPane.insertTab("Zip & Send                ", null, panelFtp, null, 2);
        tabbedPane.insertTab("Settings           ", null, settingsPanel, null, 3);
        tabbedPane.insertTab("About                               ", null, aboutPanel, null, 4);
        tabbedPane.setBackground(Color.BLACK);
        tabbedPane.setBackgroundAt(0, Color.BLACK);

        
        //SwingUtilities.updateComponentTreeUI(tabbedPane);
        
        
    }
    
    public static void main(String[] args) {
        Zee gui = new Zee();
        gui.start();
        PrepareDevices prepare = new PrepareDevices();
        prepare.prepareDevices();
        ZipAndSendUI zipAndSendUI = new ZipAndSendUI();
        zipAndSendUI.uiZIP();
        About about = new About();
        about.about();
        Settings settings = new Settings();
        settings.settings();
        insertTabs();

    }
}
