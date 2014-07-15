package RADSSoundPatcher.Patch;

/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 03.04.14
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */
public class Soundfile {


    private int size = 0;
    private int frequency = 0;
    private int channels = 0;
    private int bits = 0;
    private String mode = null;
    private String name = null;

    public Soundfile() {

    }

    public Soundfile(String name, String mode, int bits, int channels, int frequency, int size) {
        this.size = size;
        this.frequency = frequency;
        this.channels = channels;
        this.bits = bits;
        this.mode = mode;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getChannels() {
        return channels;
    }

    public int getBits() {
        return bits;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setName(String name) {
        this.name = name;
    }


}
