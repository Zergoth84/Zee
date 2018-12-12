/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.PrepareDevices.tabbedPane;
import java.awt.Color;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author z.moszko
 */
public class About {
        public static JPanel aboutPanel;
        private static JLabel logoLabel;
        
        public void about() {
        //JFrame About = new JFrame("About VoLTE APK");
        
       // Toolkit zestaw = Toolkit.getDefaultToolkit();
        //Dimension wymiary = zestaw.getScreenSize();
        //About.setBounds(wymiary.width / 2 - 200, wymiary.height / 2 - 150, 400, 300);
       // About.setResizable(false);
        //About.setVisible(true);
        aboutPanel = new JPanel();
        aboutPanel.setLayout(null);
        
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("img/Zedd.png"));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0,50,Zee.WIDTH,imageIcon.getIconHeight());
        JLabel label = new JLabel("<html><center>Zedd Version 1.0 <br><br>Created By<b> Zbigniew Moszko</b><br><br>z.moszko@samsung.com<br><br>zbigniew.moszko@gmail.com<br></center>", JLabel.CENTER);
        label.setBounds(0,0,Zee.WIDTH,Zee.HEIGHT);
        label.setForeground(Color.lightGray);
        
        
        
        ImageIcon blackRedBackground = new ImageIcon(getClass().getClassLoader().getResource("img/Background.jpg"));
        JLabel backgroundLabel = new JLabel(blackRedBackground);
        backgroundLabel.setBounds(0, 0, Zee.WIDTH, Zee.HEIGHT);
       // backgroundLabel.add(logoLabel);
        
        
        aboutPanel.add(imageLabel);
        aboutPanel.add(label);
        aboutPanel.add(backgroundLabel);

    }
}
