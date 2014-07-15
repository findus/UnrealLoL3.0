package RADSSoundPatcher.Patch;

import RADSSoundPatcher.Misc.OperatingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 13.03.13
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class Fsbext {

    static final String FSBEXT = OperatingSystem.getInstance().getFsbext();

    public static void ExtractSoundbank(String Path, String VOBankName, String ExtractFilesTo, Patcher instance, int anzahl) throws Exception {

        double ProgressbarValue = (double) 20 / (double) anzahl;
        double temp = 15;

        File rebuild = new File("fsbext/rebuild.dat");
        if (rebuild.exists()) {
            rebuild.delete();
        }
        //Extract Files + Build Rebuild.dat

        String text11 = "";    // Lesepuffer
        BufferedReader in;
        PrintWriter out = new PrintWriter("Logs/Fsbextract.log");

        if (!new File(Path + VOBankName).exists()) {
            throw new Exception("Soundfile you want to extract does not exist");
        }
        instance.fsbext =
                Runtime.getRuntime().exec("fsbext/" + FSBEXT +" -d \"" + ExtractFilesTo + "\" -s \"fsbext/rebuild.dat\" \"" + Path + VOBankName + "\"");
        in = new BufferedReader(
                new InputStreamReader(instance.fsbext.getInputStream()));
        while ((text11 = in.readLine()) != null) {
            temp = temp + ProgressbarValue;
            instance.FireEvent("ErhoeheProgressbarWert", (int) temp);
            out.println(text11);
            out.flush();
        }
        instance.fsbext = null;
    }

    public static void RepackSoundbank(String PathtoRepackedVobank, String VOBankName, String SoundFilePath, Patcher instance, int anzahl) throws Exception {
        double ProgressbarValue = (double) 15 / (double) anzahl;
        double temp = 65;

        BufferedReader FsbextOutputstream;
        PrintWriter out1 = new PrintWriter("Fsbext.log");
        String text11;
        instance.fsbext =
                Runtime.getRuntime().exec("fsbext/" +FSBEXT+" -s fsbext/rebuild.dat -d \"" + SoundFilePath + "\" -r \"" + PathtoRepackedVobank + "\"" + VOBankName);
        FsbextOutputstream = new BufferedReader(
                new InputStreamReader(instance.fsbext.getInputStream()));
        while ((text11 = FsbextOutputstream.readLine()) != null) {
            temp = temp + (ProgressbarValue / 2);
            instance.FireEvent("ErhoeheProgressbarWert", (int) temp);
            out1.println(text11);
            out1.flush();
        }
        instance.fsbext = null;
    }

    public static int getFileCount(String Path, String VOBankName, Patcher instance) throws Exception {
        BufferedReader FsbextOutputstream;
        String text11;
        String[] s = new String[0];
        instance.fsbext =
                Runtime.getRuntime().exec("fsbext/"+FSBEXT+" -l \"" + Path + VOBankName + "\"");
        FsbextOutputstream = new BufferedReader(
                new InputStreamReader(instance.fsbext.getInputStream()));
        while ((text11 = FsbextOutputstream.readLine()) != null) {
            if (text11.contains("files listed")) {
                s = text11.split(" ");
                break;
            }

        }
        return Integer.parseInt(s[1]);
    }
}
