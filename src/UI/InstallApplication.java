/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.DeviceUpdate.connected;
import static UI.PrepareDevices.appendText2;
import static UI.Tools.print;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z.moszko
 */
public class InstallApplication implements Runnable{
    private String applicationName="";
    
    public  void setApplicationName(String name){
        applicationName=name;
    }
    
    @Override
    public void run(){
                for (int i=0;i<connected.length; i++){
                   if (connected[i]!=null){
                       try {
                           appendText2("Install "+applicationName+ " for "+connected[i]+"\n",Color.BLACK);
                           Process p = Runtime.getRuntime().exec("adb -s "+connected[i]+" install files/"+applicationName);
                           print(p);
                           if (applicationName.equalsIgnoreCase("SettingsInitializer.apk")){
                           appendText2("Initialize for "+connected[i]+"\n",Color.BLACK);
                           Process p1 = Runtime.getRuntime().exec("adb -s "+connected[i]+ " shell \"am start -n com.samsung.settingsinitializer/.SettingsActivity\"");
                   }
                       } catch (IOException ex) {
                           Logger.getLogger(InstallApplication.class.getName()).log(Level.SEVERE, null, ex);
                       }
                      
                   }

                }appendText2("All tasks done \n",Color.decode("0x000CFF"));
    }
}
