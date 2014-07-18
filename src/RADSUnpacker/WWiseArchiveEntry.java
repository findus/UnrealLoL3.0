package RADSUnpacker;

import RADSSoundPatcher.Misc.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by philipp on 16.07.2014.
 */
public class WWiseArchiveEntry {

    private byte[] content;

    //RIFF
    private LittleEndian chunkID;
    private LittleEndian chunkSize;
    private LittleEndian format;

    //FMT
    private LittleEndian subchunkID;
    private LittleEndian subchunkSize;
    private LittleEndian audioFormat;
    private LittleEndian numChannels;
    private LittleEndian sampleRate;
    private LittleEndian byteRate;
    private LittleEndian blockAlign;
    private LittleEndian bitsPerSample;

    //DATA
    private LittleEndian subChunk2ID;
    private LittleEndian subChunk2Size;
    private byte[] data;

    public WWiseArchiveEntry(byte[] contentAsArray, int start)
    {
        //Chunksize einschließlich mit WAVE offset(Format)

        //Either I dont geht the Header Information or some values a not correct (or fake data)
        this.chunkID = new LittleEndian(contentAsArray,start,4);
        this.chunkSize = new LittleEndian(contentAsArray,start+4,4);
        this.format = new LittleEndian(contentAsArray,start+8,4);
        this.subchunkID = new LittleEndian(contentAsArray,start+12,4);
        this.subchunkSize = new LittleEndian(contentAsArray,start+16,4);
        this.audioFormat = new LittleEndian(contentAsArray,start+20,2);
        this.numChannels = new LittleEndian(contentAsArray,start+22,2);
        this.sampleRate = new LittleEndian(contentAsArray,start+24,4);
        this.byteRate = new LittleEndian(contentAsArray,start+28,4);
        this.blockAlign = new LittleEndian(contentAsArray,start+32,2);
        this.bitsPerSample = new LittleEndian(contentAsArray,start+34,2);
        this.subChunk2ID = new LittleEndian(contentAsArray,start+36,4);
        this.subChunk2Size = new LittleEndian(contentAsArray,start+40,4);
        this.data = new byte[(int)chunkSize.getContent()];
        ArrayUtils.insertArrayInAnotherArray(this.data, contentAsArray, 0, (int) chunkSize.getContent(), start+8);
        debugOutput();

    }

    public static void main(String[] args) throws IOException {

        WWiseArchiveEntry wWiseArchive = new WWiseArchiveEntry( Files.readAllBytes(Paths.get("test.wwise")),0);
        wWiseArchive.debugOutput();
    }

    public void debugOutput()
    {
        System.out.println("chunkID       :"+this.chunkID.getString());
        System.out.println("chunkSize     :"+this.chunkSize.getContent());
        System.out.println("format        :"+this.format.getString());
        System.out.println("subchunkID    :"+this.subchunkID.getString());
        System.out.println("subchunkSize  :"+this.subchunkSize.getContent());
        System.out.println("audioFormat   :"+this.audioFormat.getContent());
        System.out.println("numChannels   :"+this.numChannels.getContent());
        System.out.println("sampleRate    :"+this.sampleRate.getContent());
        System.out.println("byteRate      :"+this.byteRate.getContent());
        System.out.println("blockAlign    :"+this.blockAlign.getContent());
        System.out.println("bitsPerSample :"+this.bitsPerSample.getContent());
        System.out.println("subChunk2ID   :"+this.subChunk2ID.getString());
        System.out.println("subChunk2Size :"+this.subChunk2Size.getContent());
        System.out.println("");

    }




}
