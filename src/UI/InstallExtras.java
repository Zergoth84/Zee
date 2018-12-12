package UI;

import static UI.PrepareDevices.appendText2;
import static UI.Zee.saveTemp;
import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z.moszko
 */
public class InstallExtras extends Thread{
    private String line;
       
        public void run(){
            try {
                Process p = Runtime.getRuntime().exec(saveTemp("/script/install_extras.bat").getCanonicalPath());
                Scanner s = new Scanner(p.getInputStream());
                while (s.hasNextLine()){
                                                line=s.nextLine();
                                                if (line.contains("Failure")){
                                                appendText2(line+"\n", Color.RED);    
                                                }
                                                if (line.contains("Success")){
                                                appendText2(line+"\n", Color.decode("0x00BC16"));    
                                                }
                                                if (line.contains("install")){
                                                appendText2(line+"\n", Color.BLACK);    
                                                }
                                                if (line.contains("task")){
                                                appendText2(line+"\n", Color.decode("0x000CFF"));
                                                }
                }s.close();
                p.destroy();
            } catch (IOException ex) {
                Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    
    
}
