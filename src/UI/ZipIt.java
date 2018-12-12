/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.ZipAndSendUI.sendToFTP;
import static UI.ZipAndSendUI.zipConsole;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.apache.commons.io.FileUtils;

public class ZipIt implements Runnable {
    

    private List<String> fileList;
    public static final String OUTPUT_ZIP_FILE = "C:\\MyFile.zip";
    public static final String SOURCE_FOLDER = Zee.logsLocation;
    private String zipFile;
    public static boolean packed = false;

    ZipIt() {
        fileList = new ArrayList<String>();
    }
    

    public static void deleteEmptyDir(File file) throws IOException {

            for (File c : file.listFiles()) {
                        if (c.isDirectory()&&!file.getName().endsWith("zip")) {
                System.out.println("kuts " +c);
                //c.delete();
                FileUtils.deleteDirectory(c);
            }
        }
    }

    public static void appendText(String text, Color c) {
        try {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            Document doc = zipConsole.getDocument();
            doc.insertString(doc.getLength(), text, aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zip it
     *
     * @param zipFile output ZIP file location
     */
    public void setZipFile(String zipFile) {
        this.zipFile = zipFile;
    }

    public void run() {
        packed = false;
        File f0 = new File(zipFile);

        byte[] buffer = new byte[1024];
        if (f0.exists()) {
            appendText("File " + zipFile + " already exist \n", Color.RED);

        } else {
            //System.out.println(zipNameField.getText());
            try {

                FileOutputStream fos = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(fos);

                System.out.println("Output to Zip : " + zipFile);

                for (String file : this.fileList) {
                    if (!file.endsWith("zip")) {
                        appendText("Packing file : " + file + "\n", Color.BLACK);
                        //System.out.println("File Added : " + file);
                        ZipEntry ze = new ZipEntry(file);
                        zos.putNextEntry(ze);

                        FileInputStream in = new FileInputStream(SOURCE_FOLDER + File.separator + file);

                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }

                        in.close();

                        File file2 = new File(SOURCE_FOLDER + File.separator + file);
                   //     file2.delete();
                        

                    }
                }

                zos.closeEntry();
                //remember close it
                zos.close();
                zos.flush();
                fos.close();
                fos.flush();
                appendText("File packing finished \n", Zee.COLOR_GREEN);
                deleteEmptyDir(new File(SOURCE_FOLDER));

                if (sendToFTP == true) {
                    TransferToFTP transfer = new TransferToFTP();
                    Thread thread2 = new Thread(transfer);
                    thread2.start();
                    sendToFTP = false;
                }
                packed = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Traverse a directory and get all files, and add the file into fileList
     *
     * @param node file or directory
     */
    public void generateFileList(File node) {

        //add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(new File(node, filename));
            }
        }

    }

    /**
     * Format the file path for zip
     *
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
}
