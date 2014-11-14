package RADSSoundPatcher.Find;

import RADSSoundPatcher.GUI.Gui;
import RADSSoundPatcher.Misc.OperatingSystem;

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
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA. User: Philipp Date: 12.03.13 Time: 16:38 To
 * change this template use File | Settings | File Templates.
 */

public class Tools {

	static Logger logger = Logger.getRootLogger();

	/**
	 * \brief Opens a filechooser and returns the Path of the File as String
	 *
	 * @return
	 * @throws IOException
	 */
	public static String GetLoLFolder() {
		File file = null;
        String path = null;
		try {

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle("Please set your Location of \"lol.launcher.exe\"");
            chooser.setBackground(Gui.myColor);
            chooser.setForeground(Color.LIGHT_GRAY);
            chooser.setFileFilter(new FileFilter() {
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith("lol.launcher.exe")
                            || f.isDirectory();
                }

                public String getDescription() {
                    return "lol.launcher.exe(*.exe)";
                }
            });
            int rueckgabeWert = chooser.showOpenDialog(null);
            if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
               file = chooser.getSelectedFile();
                if (file.getName().contains("lol.launcher.exe")) {
                    System.out.println("- Found League of Legends Installation");
                    path = chooser.getSelectedFile().getParent();
                    File FilePath = new File("Path");
                    FileWriter writer;
                    writer = new FileWriter(FilePath);
                    writer.write(path);
                    writer.flush();
                    writer.close();
                } else {
                    System.out
                            .println("- No League of Legends Installation found :(");
                    path = "No installation found";
                }
            }
        } catch (IOException e)
        {
            logger.error("Write Error");
        }
		return path;
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
	 * Parses the Region.xml File and returns all needed data to Patch the
	 * Client. Needs the Region.xml file, which needs to be placed in the .jar
	 * File
	 *
	 * @param Region
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws URISyntaxException
	 */
	public static String GetRegionInformation(String Region) {
		String regionString = null;
		Document doc;
		File Xml = new File("Regions.xml");
        try {
            if (!Xml.exists()) {
			System.out.println("- Extracting Region.xml...");

                InputStream is = Tools.class.getClass().getResourceAsStream(
                        "/Regions.xml");
                FileOutputStream out = new FileOutputStream(new File(
                        "Regions.xml"));

                int read;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            }


				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder;

				dBuilder = dbFactory.newDocumentBuilder();

				doc = dBuilder.parse(Xml);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName(Region);

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						regionString = eElement.getElementsByTagName("Region")
								.item(0).getTextContent();
					}
				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.error(e.getClass() + " " + e.getMessage());
				regionString = null;
			}
        return regionString;
		}


}