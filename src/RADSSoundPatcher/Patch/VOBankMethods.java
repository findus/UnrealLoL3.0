package RADSSoundPatcher.Patch;


import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 12.03.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class VOBankMethods {

    public static Boolean CheckBackup(String Path, String Region, String RecentFolder) {
        Boolean BackupString;
        File Backup = new File(Path + "\\Rads\\projects\\" + Region + "\\managedfiles\\" + RecentFolder + "\\Data\\Sounds\\FMOD\\- Backup.fsb");
        {
            if (Backup.exists() == true) {
                BackupString = true;

            } else {
                BackupString = false;
            }
        }
        return BackupString;
    }
}

