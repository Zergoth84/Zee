/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author z.moszko
 */
public class ChangeIcon implements Runnable {
    
    private JButton button;
    private ImageIcon icon1, icon2;
    private long time=100;
    
    
    public void setButton(JButton button){
        this.button=button;
    }
    public void setIcon1(ImageIcon icon){
        icon1=icon;
    }
    public void setIcon2(ImageIcon icon){
        icon2=icon;
    }
    public void setTime(long time){
        this.time=time;
    }
    
    
    
    public void run(){
      button.setIcon(icon2);
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangeIcon.class.getName()).log(Level.SEVERE, null, ex);
        }
      button.setIcon(icon1);
    }
}
