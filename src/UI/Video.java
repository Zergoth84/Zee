/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.Zee.appendText;
import static UI.Zee.logsLocation;
import static UI.Zee.saveTemp;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z.moszko
 */
public class Video {
    public static boolean anotherVideo;
    
    
    
    
    
    
    
    
        public static class RecordVideo extends Thread {

        long initialTime = System.currentTimeMillis();
        int j = 0;

        public void run() {
            //running=true;
            while (anotherVideo) {

                if ((System.currentTimeMillis() - initialTime) / 180000 == j) {
                    for (int i = 0; i < DeviceUpdate.connected.length - 1; i = i + 2) {
                        if (DeviceUpdate.connected[i] != null) {
                            switch (j + 1) {
                                case 1:
                                    appendText("1st Video record started for " + DeviceUpdate.connected[i] + "\n", Color.BLACK);
                                    break;
                                case 2:
                                    appendText("2nd Video record started for " + DeviceUpdate.connected[i] + "\n", Color.BLACK);
                                    break;
                                case 3:
                                    appendText("3rd Video record started for " + DeviceUpdate.connected[i] + "\n", Color.BLACK);
                                    break;
                                default:
                                    appendText(j + 1 + "th Video record started for " + DeviceUpdate.connected[i] + "\n", Color.BLACK);

                            }
                            try {
                                j++;
                                Process proces = (Runtime.getRuntime().exec(saveTemp("/script/recordVideo.bat").getCanonicalPath() + " " + DeviceUpdate.connected[i] + " movie_" + DeviceUpdate.connected[i] + "_" + j + ".mp4"));
                                j--;

                            } catch (IOException ex) {
                                Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    j++;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
        public static class CopyMovies extends Thread {

        public void run() {

            for (int i = 0; i < DeviceUpdate.connected.length - 1; i = i + 2) {
                if (DeviceUpdate.connected[i] != null) {
                    appendText("Copy Videos from " + DeviceUpdate.connected[i] + " "+logsLocation+"\n", Color.BLACK);
                    try {
//                        

                        RunProcess runProcess = new RunProcess();
                        runProcess.setConsoleNumber(1);
                        runProcess.processString = saveTemp("/script/copyMovie.bat").getCanonicalPath() + " " + DeviceUpdate.connected[i] + " " + logsLocation;
                        Thread thread = new Thread(runProcess);
                        thread.start();

                    } catch (IOException ex) {
                        Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }    
        
        
        
    
        public static void startRecordVideo() throws InterruptedException {
        RecordVideo recordVideo = new RecordVideo();
        if (Zee.record.getIcon().equals(Zee.startMovie)) {
            Zee.record.setIcon(Zee.stopMovie);
            anotherVideo = true;
            for (int i = 0; i < DeviceUpdate.connected.length - 1; i = i + 2) {
                if (DeviceUpdate.connected[i] != null) {

                    try {
                        Process remove = (Runtime.getRuntime().exec("adb -s " + DeviceUpdate.connected[i] + " shell rm -rf /sdcard/" + DeviceUpdate.connected[i]));
                    } catch (IOException ex) {
                        Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            recordVideo.start();

        } else {
            Zee.record.setIcon(Zee.startMovie);
            anotherVideo = false;

            try {
                Process p0 = (Runtime.getRuntime().exec("wmic process Where \"CommandLine Like \'%recordVideo.bat%\' \" Call Terminate"));
                Process p1 = (Runtime.getRuntime().exec("wmic process Where \"CommandLine Like \'%sdcard/%\' \" Call Terminate"));
                Thread.sleep(2000);
                CopyMovies copy = new CopyMovies();
                copy.start();
            } catch (IOException ex) {
                Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
