/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *
 * @author z.moszko
 */
public class CheckCustomZip implements Runnable{


    public void run() {
        while (true){
        if (ZipAndSendUI.custom.isSelected()){
            ZipAndSendUI.project.setVisible(false);
        }else{
            ZipAndSendUI.project.setVisible(true);
            System.out.println("kutas");
        }
    }
    }
    
}
