///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package UI;
//
//import java.io.IOException;
//import java.util.Scanner;
//
///**
// *
// * @author z.moszko
// */
//public class AdbDevices {
//        String adbDevices;
//        String devices[];
//       
//            public void adbDevice(){
//                while(true){
//            try{
//                Process p = Runtime.getRuntime().exec("adb devices"); 
//                Scanner s = new Scanner(p.getInputStream());
//                while (s.hasNext()){
//                 //adbDevices ="";
//                 adbDevices = adbDevices +s.next()+" ";
//                }
//                adbDevices = adbDevices +"\n";
//            }catch(IOException ex){
//                
//            }
//                devices = adbDevices.split(" ");
//                if(devices.length>5){
//                  UI.dArea.append("Connected Devicaes:\n");
//                  for (int i=4;i<devices.length;i=i+2){                     
//                      UI.dArea.append(devices[i]+"\n");
//                      System.out.println("kupa");
//                  }
////                for (int i=0;i<devices.length;i++){
////                    System.out.println(devices[i]);
////                }
//                  
//                    
//                }else{
//                    UI.dArea.append("no device connected");
//                }
//                adbDevices = "";
//
//                }
//
//        }
//            
//            
//}
