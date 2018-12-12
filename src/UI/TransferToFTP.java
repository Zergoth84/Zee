/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.Zee.logsLocation;
import static UI.ZipIt.appendText;
import static UI.ZipAndSendUI.isFlagship;
import static UI.ZipAndSendUI.custom;
import static UI.ZipAndSendUI.project;
import static UI.ZipAndSendUI.ftpServers;
import static UI.ZipAndSendUI.country;
import static UI.ZipAndSendUI.folders;
import static UI.ZipAndSendUI.zipNameField;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;








public class TransferToFTP implements Runnable {

    private static void createFolder(FTPClient ftpClient, String name) throws IOException {
        boolean folderCreated;
        folderCreated = ftpClient.changeWorkingDirectory(name);
        if (folderCreated == false) {
            folderCreated = ftpClient.makeDirectory(name);
        }
    }
      
    

    public void run() {
        String server = "106.116.34.11";
        String serverName="";
        int port = 21;
        String user = "";
        String pass = "";
        
        
        if (custom.isSelected()){
            if (ftpServers.getSelectedItem().equals("Binaries")){
                    user = "sel-pav-sw";
                    pass = "poKiWTHAU43Q2dvK";
                    serverName="PAV_BINARIES";
            }if(ftpServers.getSelectedItem().equals("Flagship")){
                    user = "sel_pav";
                    pass = "lp4FQ8trhS061hg6";
                    serverName="PAV_FLAGSHIP";
            }if(ftpServers.getSelectedItem().equals("Internal")){
                    user= "mpa_pav";
                    pass= "0rRW4SSzRniI7SR4";
                    serverName="PAV_INTERNAL";     
            }if (ftpServers.getSelectedItem().equals("Projects")){
                    user = "sel-pav";
                    pass = "awJca0Y3Qtc8lpQg";
                    serverName="PAV_PROJECTS";
            }
            
            
            
        }else{
                if (isFlagship.isSelected() == true) {
                    user = "sel_pav";
                    pass = "lp4FQ8trhS061hg6";
                    serverName="PAV_FLAGSHIP";
                } else {
                    user = "sel-pav";
                    pass = "awJca0Y3Qtc8lpQg";
                    serverName="PAV_PROJECTS";
                }
        }
        
        
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            if (ftpClient.isConnected() == true) {
                appendText("Connected to "+serverName +"\n", Zee.COLOR_GREEN);
            } else {
                appendText("Unable connect to FTP server \n", Color.red);

            }
            
            
            String remoteFile="";
            if (custom.isSelected()){
                String path = "";
                for (int i=0;i<folders.length;i++){
                    path = path+"/"+folders[i];
                    createFolder(ftpClient, path);          
                    
                }
                
            remoteFile = path+"/"+zipNameField.getText()+".zip";
//                String[]folders=customPathName.getText().split("\\");
//                for(int i=0;i<folders.length;i++){
//                    System.out.println(folders[i]);
//                }
            }else{
                
            createFolder(ftpClient, "/"+project.getSelectedItem().toString());
            createFolder(ftpClient, "/"+project.getSelectedItem().toString()+"/Results");
            createFolder(ftpClient, "/"+project.getSelectedItem().toString()+"/Results/"+GetCurrentData.getDate());
            createFolder(ftpClient, "/"+project.getSelectedItem().toString()+"/Results/"+GetCurrentData.getDate()+"/"+country.getSelectedItem().toString());
            remoteFile = "/"+project.getSelectedItem().toString()+"/Results/"+GetCurrentData.getDate()+"/"+country.getSelectedItem().toString()+"/"+zipNameField.getText()+".zip";
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            //File firstLocalFile = new File("D:/Test/Projects.zip");
            File LocalFile = new File(logsLocation+"/"+zipNameField.getText()+".zip");

            //String firstRemoteFile = "Projects.zip";
            
            
            
            
            InputStream inputStream = new FileInputStream(LocalFile);
            InputStream check = ftpClient.retrieveFileStream(remoteFile);
            if (ftpClient.getReplyCode()==550){
            ftpClient.setBufferSize(1048576);
            appendText("Uploading file - please wait... \n", Color.BLACK);
            boolean done = ftpClient.storeFile(remoteFile, inputStream);
            inputStream.close();
            if (done) {
                ZipIt.appendText("File uploaded successfully \n", Zee.COLOR_GREEN);
                
            }      
            }else {
                ZipIt.appendText("File "+zipNameField.getText()+".zip exists, please select diffrent name", Color.red);
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
       
    

}
