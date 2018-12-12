/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.PushModules.line;
import static UI.PrepareDevices.appendText2;
import static UI.Zee.appendText;
import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z.moszko
 */
public class RunProcess implements Runnable {
    
    String processString;
    Process p;
    private int consoleNumber=0;
    boolean isDone = false;
    
    public void setConsoleNumber(int number){
        consoleNumber=number;
    }
    
    public void transfer() throws IOException, InterruptedException {
        p = Runtime.getRuntime().exec(processString);
        Scanner s = new Scanner(p.getInputStream());
        while (s.hasNextLine()) {
            line = s.nextLine();
            if (line.contains("done") || line.contains("successful")) {
                if(consoleNumber==1)appendText(line + "\n", Zee.COLOR_GREEN);
                if(consoleNumber==2)appendText2(line + "\n", Zee.COLOR_GREEN);
            }
            if (line.contains("initialized")) {
                if(consoleNumber==1)appendText(line + "\n", Color.BLACK);
                if(consoleNumber==2)appendText2(line + "\n", Color.BLACK);
            }
            if (line.contains("Failed to copy from ")){
                if(consoleNumber==1)appendText(line + "\n", Color.RED);
                if(consoleNumber==2)appendText2(line + "\n", Color.RED);
            }
           //appendText(line + "\n", Color.RED);
        }
        p.waitFor();
        isDone = true;
        p.destroy();
        isDone = false;
    }
    
    public void run() {
        try {
            transfer();
            
        } catch (IOException ex) {
            Logger.getLogger(RunProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RunProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
