/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.Zee.saveTemp;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static UI.Tools.execute;

/**
 *
 * @author z.moszko
 */
public class Strings extends Thread {
    
   // private int consoleNumber=0;

//    private void execute(String fileName) throws IOException {
//        for (int i = 0; i < DeviceUpdate.connected.length - 1; i = i + 2) {
//            if (DeviceUpdate.connected[i] != null) {
//                RunProcess runProcess = new RunProcess();
//                runProcess.setConsoleNumber(consoleNumber);
//                runProcess.processString = saveTemp("/script/" + fileName).getCanonicalPath() + " " + DeviceUpdate.connected[i] + " " + Zee.logsLocation;
//                Thread thread = new Thread(runProcess);
//                thread.start();
//
//            }
//        }
//    }

    public void run() {
        //Video video = new Video();

        try {
            if (Zee.comboBox.getSelectedItem().equals("Auto answer")) {
                execute("autoAnswer.bat",2);
            }
            if (Zee.comboBox.getSelectedItem().equals("Change CSC")) {
                execute("changeCSC.bat",2);
            }
            if (Zee.comboBox.getSelectedItem().equals("CP modem test")) {
                execute("cpModemTest.bat",2);
            }
            if (Zee.comboBox.getSelectedItem().equals("Download latest IMS logs")) {
                execute("latestIMS.bat",1);
            }
            if (Zee.comboBox.getSelectedItem().equals("Start Silent Logs")) {
                execute("startSilentLogs.bat",1);
            }
            if (Zee.comboBox.getSelectedItem().equals("Stop Silent Logs")) {
                execute("stopSilentLogs.bat",1);
            }
            if (Zee.comboBox.getSelectedItem().equals("Start Logs")) {
                if (Zee.flightMode.getIcon().equals(Zee.flightModeOn)) {
                    execute("flightModeOn.bat",1);
                }

//                if (Zee.checkSilentLogs.isSelected()) {
//                    execute("startSilentLogs.bat",1);
//                }
//                if (Zee.checkVideo.isSelected()) {
//                    try {
//                        Video.startRecordVideo();
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(Strings.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
                if (Zee.flightMode.getIcon().equals(Zee.flightModeOn)) {
                    Thread.sleep(17000);
                    execute("flightModeOff.bat",1);
                }

            }

            if (Zee.comboBox.getSelectedItem().equals("Stop Logs")) {
//                if (Zee.checkVideo.isSelected()) {
//                    if (Zee.record.getIcon().equals(Zee.stopMovie)) {
//
//                        try {
//                            Video.startRecordVideo();
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(Strings.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    } else {
//                        Zee.appendText("Video record not started\n", Color.RED);
//                    }
//
//                }
//                if (Zee.checkIMS.isSelected()) {
//                    execute("latestIMS.bat",1);
//                }
//                if (Zee.checkSilentLogs.isSelected()) {
//                    execute("stopSilentLogs.bat",1);
//                }
//                if (Zee.checkDumpstate.isSelected()) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(Strings.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    execute("dumpstate.bat",1);
//                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Strings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Strings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
