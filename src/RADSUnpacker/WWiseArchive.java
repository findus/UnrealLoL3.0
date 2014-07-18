package RADSUnpacker;

import RADSSoundPatcher.Misc.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 18.07.2014.
 */
public class WWiseArchive {


    private byte[] content;
    private byte[] header;
    private String word;
    private List<WWiseArchiveEntry> entrys = new ArrayList<WWiseArchiveEntry>();

    public WWiseArchive(String archive) throws IOException {
        this.content = Files.readAllBytes(
                Paths.get("C:\\Riot Games\\League of Legends\\RADS\\projects\\lol_game_client_en_gb\\managedfiles\\0.0.0.214\\DATA\\Sounds\\Wwise\\VO\\en_US\\Characters\\Jax\\Skins\\Base\\Jax_Base_VO_audio.wpk"));

        StringBuffer buffer = new StringBuffer();
        for (byte t : content)
            buffer.append((char) ((int) (t) & 0xFF));

        this.word = buffer.toString();
        int headerEnd = word.indexOf("RIFF") - 1;
        this.header = new byte[headerEnd];
        ArrayUtils.insertArrayInAnotherArray(header, content, 0, headerEnd, 0);

        int[] entrys = searchEntrys("RIFF");
        for(int r : entrys)
            this.entrys.add(new WWiseArchiveEntry(content,r));

    }

    public static void main(String[] args) throws IOException {


        WWiseArchive a = new WWiseArchive("lol");


    }

    public  int[] searchEntrys(String input) {
        List<Integer> ints = new ArrayList<Integer>();
        int index = word.indexOf(input);
        while (index >= 0) {
            ints.add(index);
            index = word.indexOf(input, index + 1);
        }
        int[]  result = new int[ints.size()];
        for(int i = 0; i < ints.size();i++ )
            result[i] = ints.get(i);

        return result;
    }
}


