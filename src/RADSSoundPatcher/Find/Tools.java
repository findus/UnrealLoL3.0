package RADSSoundPatcher.Find;


import RADSSoundPatcher.GUI.Gui;
import RADSSoundPatcher.Misc.Misc;
import RADSSoundPatcher.Misc.OperatingSystem;
import RADSSoundPatcher.Patch.PatchException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 12.03.13
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */


public class Tools {

    static Logger logger = Logger.getRootLogger();
    static final String FSBEXT = OperatingSystem.getInstance().getFsbext();
    static final String FFMPEG = OperatingSystem.getInstance().getFFmpeg();

    /**
     * Searches the newest availabale Folder of the Soundarchive and returns the Foldername as String (Example: 0.0.0.199)
     *
     * @param LeagueOfLegendsPfad
     * @param Region
     * @param VOBankName
     * @return
     */
    public static String GetNewestFolder(String LeagueOfLegendsPfad, String Region, String VOBankName) {
        String Patchversion = null;
        int chars = 0;
        File Check = new File(LeagueOfLegendsPfad + "/RADS/projects/" + Region + "/managedfiles/");
        //System.out.println(Check.getAbsolutePath());
        if (Check.exists() == true) {

            File VOBANK1 = new File(LeagueOfLegendsPfad + "/RADS/projects/" + Region + "/managedfiles/");
            File[] fileList = VOBANK1.listFiles();
            Arrays.sort(fileList);
            for (File f : fileList) {
                String test = f.getName().replace(".", "");
                int charcheck = Integer.parseInt(test);
                File VOBANK2 = new File(LeagueOfLegendsPfad + "/RADS/projects/" + Region + "/managedfiles/" + f.getName() + "/Data/Sounds/FMOD/" + VOBankName);
               // System.out.println(VOBANK2.getAbsolutePath());
                if (VOBANK2.exists() == true) {
                    if (charcheck > chars) {
                        chars = charcheck;
                        Patchversion = f.getName();
                    }
                }
            }
        }
        return Patchversion;
    }

    /**
     * \brief Opens a filechooser and returns the Path of the File as String
     *
     * @return
     * @throws IOException
     */
    public static String GetLoLFolder() throws IOException {
        String Path = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Please set your Location of \"lol.launcher.exe\"");
        chooser.setBackground(Gui.myColor);
        chooser.setForeground(Color.LIGHT_GRAY);
        chooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".exe") || f.isDirectory();
            }

            public String getDescription() {
                return "lol.launcher.exe(*.exe)";
            }
        });
        int rueckgabeWert = chooser.showOpenDialog(null);
        if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            String AbsolutePath = chooser.getSelectedFile().getAbsolutePath();
            if (AbsolutePath.contains("lol.launcher.exe")) {
                System.out.println("- Found League of Legends Installation");
                Path = chooser.getSelectedFile().getParent();
                File FilePath = new File("Path");
                FileWriter writer;
                writer = new FileWriter(FilePath);
                writer.write(Path);
                writer.flush();
                writer.close();
            } else {
                System.out.println("- No League of Legends Installation found :(");
                Path = "No installation found";
            }
        }
        return Path;
    }

    /**
     * \brief Converts Inputfile with the inserted parameters
     *
     * @param Input
     * @param SamplingRate
     * @param AudioChannels
     * @param Format
     * @param Output
     * @param extension
     * @throws IOException
     * @throws InterruptedException
     */
    public static void FFMPEGConvert(String Input, Object SamplingRate, Object AudioChannels, String Format, String Output, String extension) throws IOException, InterruptedException {
        String text11;
        BufferedReader in;
        PrintWriter out = new PrintWriter("ffmpeg.log");
        Process k;

        logger.info("FFMEG: Name: " + Input + " SamplingRate: " + SamplingRate + " AudioChannels: " + AudioChannels + " Format: " + Format + " Extension: " + extension);
        k = Runtime.getRuntime().exec("fsbext/" + FFMPEG  +" -i \"" + Input + "\"" + " -ar " + SamplingRate + " -ac " + AudioChannels + " -ab 160 -acodec " + Format + "  -y -f " + extension + " \"" + Output + "\"");
        in = new BufferedReader(
                new InputStreamReader(k.getInputStream()));
        while ((text11 = in.readLine()) != null) {

            out.println(text11);
            out.flush();
        }
        k.waitFor();

    }

    /**
     * Checks if the Folder is Writeable
     *
     * @param Folder
     * @return
     */
    public static boolean Writeable(File Folder) {
        boolean Check;
        if (Folder.canWrite() == true) {
            Check = true;
        } else {
            Check = false;
        }
        return Check;
    }

    /**
     * Parses the Region.xml File and returns all needed data to Patch the Client. Needs the Region.xml file, which needs to be placed in the .jar File
     *
     * @param Region
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws URISyntaxException
     * @throws PatchException
     */
    public static ArrayList GetRegionInformation(String Region) throws ParserConfigurationException, IOException, SAXException, URISyntaxException, PatchException {
        ArrayList<String> List = new ArrayList<String>();
        Document doc;
        File Xml = new File("Regions.xml");
        if (!Xml.exists()) {
            System.out.println("- Extracting Region.xml...");
            try {
                InputStream is = Tools.class.getClass().getResourceAsStream("/Regions.xml");
                FileOutputStream out = new FileOutputStream(new File("Regions.xml"));

                int read;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Misc.ErrorMessage();
            }
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(Xml);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName(Region);

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String Regionz = eElement.getElementsByTagName("Region").item(0).getTextContent();
                String Constructor = eElement.getElementsByTagName("Constructor").item(0).getTextContent();
                String VOBank = eElement.getElementsByTagName("VOBank").item(0).getTextContent();
                String RegionFolder = eElement.getElementsByTagName("RegionFolder").item(0).getTextContent();

                List.clear();
                List.add(Regionz);
                List.add(Constructor);
                List.add(VOBank);
                List.add(RegionFolder);
            }
        }
        if (List.size() == 0) {
            List = null;
            throw new PatchException("- ERROR: Unsupported Region! (" + Region + ")");
        }
        return List;
    }
}