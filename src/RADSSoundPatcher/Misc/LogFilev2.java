package RADSSoundPatcher.Misc;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
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

    private static void winapicalls() {
        Kernel32 INSTANCE = Kernel32.INSTANCE;

        WinBase.SYSTEM_INFO sysinfo = new WinBase.SYSTEM_INFO();
        System.out.println(sysinfo);
        if (INSTANCE != null) {
            INSTANCE.GetSystemInfo(sysinfo);
            //System.out.println(sysinfo.dwProcessorType);
        }
    }

    public static void main(String[] args) {
        winapicalls();
    }


}
