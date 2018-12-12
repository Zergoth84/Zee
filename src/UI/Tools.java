/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

//import static UI.Zee.console;
import static UI.PrepareDevices.appendText2;
import static UI.Zee.saveTemp;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author z.moszko
 */
public class Tools {

    private static String line = "";
    private static InputStream inStream = null;
    private static OutputStream outStream = null;

    public static void print(Process p) {
        Scanner s = new Scanner(p.getInputStream());
        while (s.hasNextLine()) {
            line = s.nextLine();
            if (line.contains("FAIL")) {
                appendText2(line + "\n", Color.RED);
            }
            if (line.contains("was found")) {
                appendText2(line + "\n", Color.decode("0x00BC16"));
            }
            if (line.contains("SUCCESS") || line.contains("Success")) {
                appendText2(line + "\n", Color.decode("0x00BC16"));
            }
            if (line.contains("install")) {
                appendText2(line + "\n", Color.BLACK);
            }
            if (line.contains("task")) {
                appendText2(line + "\n", Color.decode("0x000CFF"));
            }
        }
    }

    public static void execute(String fileName, int consoleNumber) throws IOException {
        for (int i = 0; i < DeviceUpdate.connected.length - 1; i = i + 2) {
            if (DeviceUpdate.connected[i] != null) {
                RunProcess runProcess = new RunProcess();
                runProcess.setConsoleNumber(consoleNumber);
                runProcess.processString = saveTemp("/script/" + fileName).getCanonicalPath() + " " + DeviceUpdate.connected[i] + " " + Zee.logsLocation;
                Thread thread = new Thread(runProcess);
                thread.start();

            }
        }
    }

    public static void copyFile(File f1, File f2) {
        try {
            inStream = new FileInputStream(f1);
            outStream = new FileOutputStream(f2);
            byte[] buffer = new byte[1024];
            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    

}
