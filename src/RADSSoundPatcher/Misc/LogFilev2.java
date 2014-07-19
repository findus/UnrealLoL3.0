package RADSSoundPatcher.Misc;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogFilev2 {

    static Logger logger = Logger.getRootLogger();
    static int Patchrun = 0;
    static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss");
    static Date _calender = null;

    public static void setDate() {
        LogFilev2._calender = new Date();
    }

    public static void finalisieren() {
        try {
            Misc.copyFile(new File("Logs/current.log"), new File("Logs/RadsSoundPatcher - " + format.format(_calender) + ".log"));
            new File("Logs/current.log").delete();
        } catch (IOException e) {
            System.out.println(" - Can't save Logfile");
        }
    }




}
