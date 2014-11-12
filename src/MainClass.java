import RADSSoundPatcher.GUI.Gui;
import RADSSoundPatcher.Manager.ArchiveManager;
import RADSSoundPatcher.Misc.Misc;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: philipp
 * Date: 24.10.13
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class MainClass {

    static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) throws IllegalAccessException, ParserConfigurationException, UnsupportedLookAndFeelException, IOException, InstantiationException, URISyntaxException, SAXException, ClassNotFoundException {

        Gui.RedirectOutputStream();
        ArchiveManager manager = new ArchiveManager("English");
        Gui gui = new Gui(manager);

// if (args.length == 0) {
//            System.out.println(
//                    "RADSSoundpatcher for League of Legends [v." + Gui.Version + "] \n" +
//
//                            "Available Commands: Unreal.jar [-Path] [-Soundpack] [-Region] [-Unpatch] [-Gui]\n" +
//                            "                               [-Swapmode]\n\n" +
//                            "Options:\n" +
//                            "   -Path      Path to your LoL Client Example: C:\\Riot Games\\League of Legends\\\n" +
//                            "   -Soundpack Soundpack you want to use\n" +
//                            "   -Region    Your Region/Language Supported Languages: English, English_US,\n" +
//                            "              German, Greek,\n" +
//                            "              Polski, Espagnol, French, Romanian, Russia, Turkey, Portugues,\n" +
//                            "              LATNLATS, Oceania\n" +
//                            "   -Unpatch   Removes all Custom Sounds\n" +
//                            "   -Swapmode  0: Default, 1: Allied Announcer Only, 2: Enemy Announcer Only,\n" +
//                            "              3: Swap Announcers\n" +
//                            "   -Gui       Launches Gui\n\n" +
//                            "Example: java -jar unreal.jar -Path \"C:\\Riot Games\\League of Legends\"\n" +
//                            "         -Soundpack \"Unreal Tournament\" -Region \"English\"\n"
//            );
//        } else {
//            System.out.println("RADSSoundpatcher for League of Legends [v." + Gui.Version + "]");
//            int path = getVariables(args, "-path");
//            int soundpack = getVariables(args, "-soundpack");
//            int region = getVariables(args, "-region");
//            int unpatch = getVariables(args, "-unpatch");
//            int swapmode = getVariables(args, "-swapmode");
//            int gui = getVariables(args, "-gui");
//
//            try {
//
//
//                if (gui != -1) {
//                    System.out.println("- Gui Mode...");
//                    Misc.SelfTest();
//                    //Gui.main(args);
//                } else if (path != -1 && region != -1 && unpatch != -1) {
//                    //Patcher job = new Patcher(args[path + 1].replace("\"", ""), args[region + 1].replace("\"", ""));
//                    //job.DeleteVOBankBackup();
//                } else if (path != -1 && soundpack != -1 && region != -1 && unpatch == -1) {
//
//                    Misc.SelfTest();
//                    if (swapmode != -1) {
//                        //Patcher job = new Patcher(args[path + 1].replace("\"", ""), args[soundpack + 1].replace("\"", ""), args[region + 1].replace("\"", ""), Integer.valueOf(args[swapmode + 1].replace("\"", "")));
//                       // job.PrepareSoundpackforPatch();
//                    } else {
//
//
//                        //Patcher job = new Patcher(args[path + 1].replace("\"", ""), args[soundpack + 1].replace("\"", ""), args[region + 1].replace("\"", ""), 0);
//                        //job.PrepareSoundpackforPatch();
//                    }
//
//
//                } else {
//                    System.out.println("Invalid Syntax\n");
//                    System.out.println(
//                            "RADSSoundpatcher for League of Legends [v." + Gui.Version + "] \n" +
//                                    "Available Commands: Unreal.jar [-Path] [-Soundpack] [-Region] [-Unpatch] [-Gui]\n" +
//                                    "                               [-Swapmode]\n\n" +
//                                    "Options:\n" +
//                                    "   -Path      Path to your LoL Client Example: C:\\Riot Games\\League of Legends\\\n" +
//                                    "   -Soundpack Soundpack you want to use\n" +
//                                    "   -Region    Your Region/Language Supported Languages: English, English_US,\n" +
//                                    "              German, Greek,\n" +
//                                    "              Polski, Espagnol, French, Romanian, Russia, Turkey, Portugues,\n" +
//                                    "              LATNLATS, Oceania\n" +
//                                    "   -Unpatch   Removes all Custom Sounds\n" +
//                                    "   -Swapmode  0: Default, 1: Allied Announcer Only, 2: Enemy Announcer Only,\n" +
//                                    "              3: Swap Announcers\n" +
//                                    "   -Gui       Launches Gui\n\n" +
//                                    "Example: java -jar unreal.jar -Path \"C:\\Riot Games\\League of Legends\"\n" +
//                                    "         -Soundpack \"Unreal Tournament\" -Region \"English\""
//                    );
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//
//
//        }
    }


    static int getVariables(String[] args, String gesuchteVariable) {
        int retval = -1;
        gesuchteVariable = gesuchteVariable.toLowerCase();
        for (int i = 0; i < args.length; i++) {
            if (gesuchteVariable.equals(args[i].toLowerCase())) {
                retval = i;
                break;
            }
        }
        return retval;
    }
}
