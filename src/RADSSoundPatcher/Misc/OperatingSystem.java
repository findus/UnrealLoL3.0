package RADSSoundPatcher.Misc;

/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 31.05.14
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class OperatingSystem {


    static final String OPERATING_SYSTEM = System.getProperty("os.name");
    OS os = new OS(OPERATING_SYSTEM);
    static OperatingSystem system = new OperatingSystem();

     private OperatingSystem(){
         System.out.println("- Your OS is: " + OPERATING_SYSTEM);
     };

    public  String getFsbext()
    {
        return os.fsbext;
    }

    public  String getFFmpeg()
    {
        return os.ffmpeg;
    }

    public static OperatingSystem getInstance()
    {
        return system;
    }


    class OS{
        String ffmpeg;
        String fsbext;

        public OS(String OS)
        {
            if(OS.contains("Windows"))
            {
                ffmpeg = "ffmpeg.exe";
                fsbext = "fsbext.exe";
            }
            if(OS.contains("Linux"))
            {
                fsbext = "fsbext";
                ffmpeg = "ffmpeg";
            }
        }
    }
}


