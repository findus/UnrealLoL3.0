package RADSSoundPatcher.Soundpacks;

import RADSSoundPatcher.Patch.Soundfile;

import java.io.*;


/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 17.06.13
 * Time: 20:19
 * To change this template use File | Settings | File Templates.
 */
public class FsbextractLogParser {

    public static void main(String[] args) throws IOException {
        Soundfile file = getRegionInformation("female1_OnChampionPentaKillEn");

    }

    public static Soundfile getRegionInformation(String Input) throws IOException {
        Soundfile file = new Soundfile();
        File log = new File("Logs/Fsbextract.log");
        if (!log.exists()) {
            throw new FileNotFoundException("Cannot Parse Logfile (File not found)");
        }
        BufferedReader Fsb = new BufferedReader(new FileReader(log));
        String zeile = null;
        Object[] params = new Object[7];
        boolean foundSoundfile = false;
        try {
            while ((zeile = Fsb.readLine()) != null) {
                String[] lol = zeile.split(" ");
                // System.out.println(lol[0].toLowerCase() + "<>" + Input.toLowerCase());
                if (lol[0].toLowerCase().equals(Input.toLowerCase())) {
                    int arraycounter = 0;
                    for (int i = 0; i < lol.length; i++) {
                        if (!lol[i].contains(" ") || !lol[i].contains(" ")) {
                            String trim = lol[i].trim();
                            if (!trim.isEmpty()) {
                                params[arraycounter++] = trim;
                                foundSoundfile = true;
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            throw new IOException("Cannot Parse Logfile(ParseError)");
        }

        if (!foundSoundfile)
             return null;
        Soundfile soundfile = new Soundfile(params[0].toString(),
                params[2].toString(),
                Integer.valueOf(params[5].toString()),
                Integer.valueOf(params[4].toString()),
                Integer.valueOf(params[3].toString()),
                Integer.valueOf(params[1].toString())
        );




        return soundfile;
    }
}
