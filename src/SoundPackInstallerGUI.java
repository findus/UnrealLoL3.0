import RADSSoundPatcher.GUI.Gui;
import RADSSoundPatcher.Manager.ArchiveManager;

import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 12.03.13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
public class SoundPackInstallerGUI {

    public static void main(String[] args) throws Throwable {
        //Misc.SelfTest();
       Gui.RedirectOutputStream();
       ArchiveManager manager = new ArchiveManager("English");
       Gui gui = new Gui(manager);
    }
}
