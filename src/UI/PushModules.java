package UI;

import static UI.PrepareDevices.appendText2;
import static UI.Tools.copyFile;
import static UI.Zee.saveTemp;
import static UI.Tools.print;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *
 * @author z.moszko
 */
public class PushModules extends Thread {

    private boolean push = false;
    public static String myfile = "", line = "";
    boolean backup = false;
    private String modulesLocation;
    BufferedWriter writer;



    private void pushIt() throws IOException {
        if (Find.foundFilesCount == 1) {
            Process p1 = Runtime.getRuntime().exec(saveTemp("/script/remount.bat").getPath());
            appendText2("Start instalation of  Modules: \n\n", Color.BLACK);
        }
        Process p = Runtime.getRuntime().exec(saveTemp("/script/install.bat").getCanonicalPath() + " " + backup + " " + modulesLocation+" "+Zee.backupLocation+" "+Find.result1+" "+Find.result2+" "+myfile);
        print(p);
    }

    public void run() {
        try {

            if (PrepareDevices.backup.isSelected() == true) {
                backup = true;
            } else {
                backup = false;
            }
            
            if (PrepareDevices.restore==true){
                backup=false;
                modulesLocation=Zee.backupLocation;
            }else{
                modulesLocation=Zee.modulesLocation;
            }
            System.out.println(modulesLocation);

            if (PrepareDevices.libSECIMSJni.isSelected() || PrepareDevices.mainTabActivity.isSelected() || PrepareDevices.secimsfw.isSelected() || PrepareDevices.libims_engine.isSelected() || PrepareDevices.imsSettings.isSelected() || PrepareDevices.customBox3.isSelected()) {


            } else {
                appendText2("No selected files \n", Color.BLACK);
            }

            if (PrepareDevices.secimsfw.isSelected() == true) {
                push = true;
                myfile = "aligned_signed_secimsfw40.apk";
                File a = new File(modulesLocation + "/secimsfw-debug.apk");
                File b = new File(modulesLocation + "/" + myfile);
                File c = new File(modulesLocation + "/secimsfw-debug.apk");
                a.renameTo(b);
                Find.find();
                if (Find.found == true) {
                    pushIt();
                    b.renameTo(c);
                } else {

                    myfile = "ap_secimsfw40.apk";
                    File d = new File(modulesLocation + "/secimsfw-debug.apk");
                    b.renameTo(d);
                    Find.find();
                    if (Find.found == true) {
                    if (Find.foundFilesCount==1){
                Process p1 = Runtime.getRuntime().exec(saveTemp("/script/remount.bat").getPath());
                appendText2("Start instalation of  Modules: \n\n", Color.BLACK);
                    }
                        pushIt();
                        b.renameTo(c);
                    }
                }
                a.renameTo(c);
            }
            Find.found = false;

            if (PrepareDevices.imsSettings.isSelected() == true) {
                push = true;
                myfile = "ImsSettings.apk";
                Find.find();
                if (Find.found == true) {
                    pushIt();
                }
            }
            Find.found = false;

            if (PrepareDevices.mainTabActivity.isSelected() == true) {
                push = true;
                myfile = "aligned_signed_secRCS_BB.apk";
                Find.find();
                if (Find.found == true) {

                    File a = new File(modulesLocation + "/MainTabActivity-debug.apk");
                    File b = new File(modulesLocation + "/" + myfile);
                    a.renameTo(b);
                    pushIt();
                    b.renameTo(a);
                }
            }
            Find.found = false;

            if (PrepareDevices.libSECIMSJni.isSelected() == true) {
                push = true;
                myfile = "libSECIMSJni.so";
                Find.find();
                if (Find.found == true) {
                    pushIt();

                }
            }
            Find.found = false;

            if (PrepareDevices.libims_engine.isSelected() == true) {
                push = true;
                myfile = "libims_engine.so";
                Find.find();
                if (Find.found == true) {
                    pushIt();
                }
            }
            Find.found = false;

            if (PrepareDevices.customBox3.isSelected() == true) {
                push = true;
                myfile = PrepareDevices.custom3.getText();
                Find.find();
                if (Find.found == true) {
                    pushIt();

                }
            }
            Find.found = false;

            if (PrepareDevices.customBox2.isSelected() == true) {
                push = true;
                myfile = PrepareDevices.custom2.getText();
                Find.find();
                if (Find.found == true) {
                    pushIt();

                }
            }
            Find.found = false;

            if (PrepareDevices.customBox.isSelected() == true) {
                push = true;
                myfile = PrepareDevices.custom.getText();
                Find.find();
                if (Find.found == true) {
                    pushIt();
                }
            }
            Find.found = false;

            if (push == true && Find.reboot==true) {
                Process p = Runtime.getRuntime().exec(saveTemp("/script/delDatabase.bat").getCanonicalPath());
                Process p2 = Runtime.getRuntime().exec(saveTemp("/script/end.bat").getCanonicalPath());
                Scanner s = new Scanner(p2.getInputStream());
                while (s.hasNextLine()) {

                    appendText2(s.nextLine() + "\n", Color.BLACK);

                }
                PrepareDevices.backup.setSelected(false);
                push = false;
                //appendText2("All tasks done" + "\n", Color.decode("0x000CFF"));

            }
            appendText2("All tasks done" + "\n", Color.decode("0x000CFF"));
            Find.reboot=false;
            //File f0 = new File ("C:/Windows/Temp/systemApp.txt");
            //File f1 = new File (Zee.backupLocation+"/systemApp.txt");
           //copyFile(f0, f1);

        } catch (IOException ex) {
            Logger.getLogger(Zee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
