package RADSUnpacker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by philipp on 02.07.2014.
 */
public class Raf {

    private byte[] fileByteArray = null;

    char magicPacket;
    int version;

    public static void main(String[] args) throws IOException {
        Raf raf = new Raf();
        File file = new File("Archive_2.raf");
        Path path = Paths.get(file.getAbsolutePath());
        raf.fileByteArray = Files.readAllBytes(path);
        System.out.println(raf.fileByteArray.length);

        System.out.println(raf.byteArrayToInt(raf.fileByteArray, 0));
    }

    public int byteArrayToInt(byte[] array, int start) {
        int retVal = 0;
        for (int i = start; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            retVal += (array[i] & 0x000000FF) << shift;
        }
        return retVal;
    }


}


