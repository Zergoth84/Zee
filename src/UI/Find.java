package UI;

import static UI.PrepareDevices.restore;
import static UI.Zee.saveTemp;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;






public class Find{
    
    
        private static int i;
        private static String file="";
        public static boolean found=false, reboot=false;
        public static String result1,result2;
        public static int foundFilesCount =0;
        private static Process p;
        
        

        public static void find (){
            String path="";
            try {
                if (PrepareDevices.backup.isSelected()){
                  p = Runtime.getRuntime().exec(saveTemp("/script/systemApp.bat").getCanonicalPath()+" 1 "+Zee.backupLocation);  
                }
                
                Scanner scanner = new Scanner(p.getInputStream());
                while (scanner.hasNextLine()){
                        path=scanner.nextLine();
                }
            } catch (IOException ex) {
                Logger.getLogger(Find.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            ArrayList<String>lines = new ArrayList();
            File f3 = new File ("src/file.txt");
                   
            try {
            File f0 = new File ("C:/Windows/Temp/systemApp.txt");
            if (restore==true){
                f0= new File (Zee.backupLocation+"/systemApp.txt");
            }
            //File f0 = new File (path+"/systemApp.txt"); 
                i=0;
                file=PushModules.myfile;
                
                Scanner scaner = new Scanner(f0);
                while (scaner.hasNext()){
                    
                    lines.add(scaner.next());
                    if (file.equals(lines.get(i))){
                    while (found==false){
                    if (lines.get(i).contains("/")){                       
                        result1=lines.get(i).substring(0,lines.get(i).length()-1);
                        result2=lines.get(i).substring(0,lines.get(i).length()-1)+"/"+file;
                        PrepareDevices.appendText2(file +" was found in " +result1 +"\n", Color.BLACK);  
                        found=true; 
                        foundFilesCount++;
                        reboot=true;
                    }else{
                        
                      i--;
                    }
                    }
                     
                    
                }else{
                     i++;  
                    }
                    
                }
                f0.delete();
                if (found==false){
                    PrepareDevices.appendText2(file +" was not found \n", Color.BLACK);
                    scaner.close();
                }
              
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Find.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            lines.clear();
            
    }     
}



