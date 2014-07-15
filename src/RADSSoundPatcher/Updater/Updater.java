package RADSSoundPatcher.Updater;

import RADSSoundPatcher.Misc.Misc;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: Philipp Date: 20.03.13 Time: 14:28 To
 * change this template use File | Settings | File Templates.
 */
public class Updater {
    private static Logger logger = Logger.getRootLogger();

    public void stopdownloading(boolean stopdownloading) {
        this.stopdownloading = stopdownloading;
    }

    boolean stopdownloading = false;

    public double getFileSize(String filename) {
        try {
            String[] urls = getUrlList();
            final URL url = new URL(urls[0] + filename);
            final URLConnection conn;
            conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            long Size = conn.getContentLength();
            double FileSize = (int) ((Size / 1024) / 1024);
            return FileSize;
        } catch (IOException e) {
            System.out.println("- Could not measure Filesize");
        }
        return -1;
    }

    // TODO Download sccesful wenn Connection abbricht

    /**
     * Lädt eine Datei herunter,
     *
     * @param URL    URL zur Datei, die heruntergeladen werden soll
     * @param output Pfad, wohin die Datei gespeichert werden soll (Inklusive Dateiname)
     * @param bar    Falls eine Progressbar angesteuert werden soll, kann diese hier angegeben werden, falls nicht auf NULL setzen
     * @return 1 = Succesfull , 0 aborted, -1 error
     * @throws IOException
     */
    public int Download(String URL, String output, JProgressBar bar)
            throws IOException {

        int retval = 1;
        final URL url;
        final URLConnection conn;
        InputStream is = null;
        OutputStream os = null;

        logger.info("Download-> Url: " + URL + " ,Destination: " + output);
        try {
            url = new URL(URL);
            conn = url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            File file = new File(output);
            os = new BufferedOutputStream(new FileOutputStream(file));

            // TODO besser machen
            // TODO CHangelog buggy
            if (bar == null)
                bar = new JProgressBar();

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            int size = conn.getContentLength();
            bar.setMaximum(size);

            byte[] chunk = new byte[1024];
            int chunkSize;
            while ((chunkSize = is.read(chunk)) != -1
                    && (stopdownloading == false)) {

                os.write(chunk, 0, chunkSize);
                int temp = bar.getValue() + 1024;
                bar.setValue(temp);
            }

            if (stopdownloading)
                retval = 0;
            else {
                retval = 1;
            }

        } catch (Exception e) {
            System.out.println("- ERROR - while downloading, connecting to mirror...");
            logger.error(e.getMessage());
            Misc.logStacktrace(e);
            retval = -1;
        } finally {
            if (os != null) {
                os.flush(); // Necessary for Java < 6
                os.close();
            }
            if (is != null)
                is.close();
        }

        return retval;
    }

    /**
     * Downloads the file you want to Download, if one site is not avaiable it will try to download it from the next specified mirror
     *
     * @param destinationFolder Folder + Filename
     * @param fileName
     * @param bar
     * @return
     */
    public int DownloadUrlPool(String destinationFolder, String fileName,
                               JProgressBar bar) {

        int availableURLs = 0;
        int retval = -1;
        int counter = 0;
        String[] urls;

        urls = getUrlList();
        if (urls == null) {
            logger.error("Cant read url file");
            return -1;
        }

        availableURLs = urls.length;
        while ((retval != 1) && (stopdownloading == false) && (counter != availableURLs)) {
            try {
                retval = Download(urls[counter] + fileName, destinationFolder, bar);

            } catch (IOException e) {
                logger.error("Cant download file: " + urls[counter] + fileName
                        + " [" + e.getMessage() + "]");
                logger.error(e.getMessage());
                Misc.logStacktrace(e);

            }
            counter++;
        }

        if (counter == availableURLs && retval == -1) {
            logger.error("No mirror available!");
            System.out.println("- ERROR! No Mirror is available.");
            retval = -1;
        }
        if (stopdownloading) {
            retval = 0;
            logger.info("Download aborted by user");
            System.out.println("- Info Download aborted by user");
        }

        return retval;
    }

    /**
     * Extracts urls and returns them in a String[]
     *
     * @return
     */
    private String[] getUrlList() {

        InputStream stream = Updater.class
                .getResourceAsStream("/url.txt");
        BufferedReader urlList = new BufferedReader(new InputStreamReader(
                stream));

        String test;
        ArrayList<String> array = new ArrayList<String>();

        try {
            while ((test = urlList.readLine()) != null) {
                array.add(test);
            }
        } catch (IOException e) {
            return null;
        }
        return array.toArray(new String[array.size()]);
    }

}
