/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.PrepareDevices.dArea2;
import static UI.Zee.dArea;
import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z.moszko
 */
public class DeviceUpdate extends Thread{
    private String adbDevices;
    private String[] device;
    public static String[] connected = new String[10];
    private boolean system=false;
    
            
            
        public void adbDevice(){
            try{
                Process p0 = Runtime.getRuntime().exec("adb start-server"); 
                Process p = Runtime.getRuntime().exec("adb devices"); 
                Scanner s = new Scanner(p.getInputStream());
                while (s.hasNext()){
                 adbDevices = adbDevices +s.next()+" ";
                }
                adbDevices = adbDevices +"\n";
            }catch(IOException ex){
                
            }
            dArea.setText("");
            dArea2.setText("");
                device = adbDevices.split(" ");
                if(device.length>5&&!device[1].equals("daemon")){
                  connected =null;
                  connected = new String[10];
                  for (int i=4;i<device.length-1;i=i+2){                     
                      dArea.append(device[i]+"\n");
                      dArea2.append(device[i]+"\n");
                      connected[i-4]=device[i];
                                                              

                                        
                  }
                    
                }else{
                    dArea.append("no device connected");
                    dArea2.append("no device connected");
                    system=false;
                }
                adbDevices = "";
                for (int i=0; i<device.length;i++){
                    device[i]="";
                }

            
        }
    
    
    public void run(){
                
                while(dArea.isVisible()){
                try {
                    
                    adbDevice();
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            }
            
        
}
