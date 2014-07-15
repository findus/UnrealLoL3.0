package RADSSoundPatcher.Misc;


import RADSSoundPatcher.Updater.Updater;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 12.03.13
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */
public class Misc {

    static Logger logger = Logger.getRootLogger();

    public static void copyFile(File inF, File outF) throws IOException {
        // TODO Auto-generated method stub
        //System.out.println("Copying");
        @SuppressWarnings("resource")
        FileChannel inChannel = new FileInputStream(inF).getChannel();
        @SuppressWarnings("resource")
        FileChannel outChannel = new FileOutputStream(outF).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            Misc.ErrorMessage();
            throw e;
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    public static void OpenLink(String url) {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void SelfTest() {

        File FFMpeg = new File("fsbext/ffmpeg.exe");
        if (!FFMpeg.exists()) {


            int response = JOptionPane.showConfirmDialog(null, " FFmpeg not found, you need FFmpeg to use the mod! Download? ", "Error", JOptionPane.YES_NO_OPTION);
            if (response == 0) {
                System.out.println("- Downloading FFmpeg! Filesize: " + new Updater().getFileSize("ffmpeg.exe") + " MB");
                new Updater().DownloadUrlPool("fsbext/ffmpeg.exe", "ffmpeg.exe", null);
                System.out.println("- Done!");
            }
        }

        File mp3 = new File("Sounds/");
        if (mp3.exists() == false) {
            System.out.println("- " + mp3 + " not found, creating Folder...");
            mp3.mkdir();

        }
        File mp4 = new File("Sounds/Convert/");

        if (mp4.exists() == false) {
            System.out.println("- " + mp4 + " not found, creating Folder...");
            mp4.mkdir();

        }

        System.out.println("- Deleting temp. Soundfiles");
        File[] fileList1 = mp3.listFiles();
        for (File mp3ext : fileList1) {
            if (mp3ext.isDirectory() == false) {
                String mp3String = mp3ext.getName();
                if (mp3String.endsWith(".mp3") == true) {
                    mp3ext.delete();
                }
            }
        }
        File[] fileList2 = mp4.listFiles();
        for (File mp3ext : fileList2) {
            if (mp3ext.isDirectory() == false) {
                String mp3String = mp3ext.getName();
                if (mp3String.endsWith(".mp3") == true) {
                    mp3ext.delete();
                }
            }
        }

        File Files = new File("Files/");
        if (Files.exists() == false) {
            System.out.println("- " + Files + " not found, creating Folder...");
            Files.mkdir();
        }

        File fsbextFolder = new File("fsbext/");
        if (fsbextFolder.exists() == false) {
            System.out.println("Mod files corrupted! (fsbext not found), please redownload");
            JOptionPane.showMessageDialog(null, "ERROR! \n" + "\n FSBext is missing! Programm will not exit. ");
            System.exit(-1);
        }

        File VOBANK = new File("Files/");
        for (File f : VOBANK.listFiles()) {
            if (f.toString().contains(".fsb")) {
                f.delete();
                System.out.println("- " + f.getName() + " found, deleting...");
            }
        }


        File Version = new File("Files/Version");
        if (Version.exists() == false) {
            Version.mkdir();
            System.out.println("- Recreating Version Folder");
        }

        File ServerVersion = new File("Files/Version/Serverversion");
        if (ServerVersion.exists() == true) {
            ServerVersion.delete();
            System.out.println("- Deleting Temp Version Files");
        }

        File Soundpack = new File("Soundpacks/");
        if (!Soundpack.exists()) {
            Soundpack.mkdir();
            System.out.println("- " + Soundpack + " not found: Recreating...");
        }
    }

    public static void ErrorMessage() {
        System.out.println("ERROR");
        System.out.println("- An error occured, check Logfiles for further information\n  Path: [Modfolder]\\Logs\\ or Menu: About->Logs");
    }

    public static void logStacktrace(Exception e) {
        StackTraceElement[] str = e.getStackTrace();
        for (int i = 0; i < str.length; i++) {
            logger.error(str[i].toString());
        }
    }

    public static Color getColor(BufferedImage img) {
        int Blau = 0;
        int Gruen = 0;
        int Rot = 0;

        int[] buffer = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        for (int i = 0; i < buffer.length; i++) {
            Blau = Blau + new Color(buffer[i]).getBlue();
            Gruen = Gruen + new Color(buffer[i]).getGreen();
            Rot = Rot + new Color(buffer[i]).getRed();
        }

        Blau = Blau / buffer.length;
        Rot = Rot / buffer.length;
        Gruen = Gruen / buffer.length;

        return new Color(Rot, Gruen, Blau);

    }


}

