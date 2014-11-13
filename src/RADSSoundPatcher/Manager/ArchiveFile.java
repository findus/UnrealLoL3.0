package RADSSoundPatcher.Manager;

import RADSSoundPatcher.Misc.Misc;
import RADSSoundPatcher.exception.AlreadyModdedException;
import RADSSoundPatcher.exception.ArchiveException;
import RADSSoundPatcher.exception.SoundpackNotValidException;
import RADSSoundPatcher.exception.notModdedExcption;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by philipp on 10.11.2014.
 */
public class ArchiveFile {
    private String name;
    private List<File> files = new ArrayList<>();

    private Soundpack pack;
    private File basePath;
    Logger logger = Logger.getRootLogger();

    public ArchiveFile(Soundpack pack, File basePath) throws ArchiveException, SoundpackNotValidException {

        // Dont know if I have to handle this this way, some users had
        // troubles to install a mod on another Region if those files
        // remains the same

        this.basePath = basePath;
        this.pack = pack;

        for (File temp : pack.getParts()) {
            logger.debug("Searching for: " + temp.getName());
            File tempFile = searchFiles(basePath, temp.getName());

            if (tempFile != null) {
                logger.debug("Add file to Arraylist: " + tempFile.getAbsolutePath());
                files.add(tempFile);
            }
            else
                throw new SoundpackNotValidException();
        }

    }

    public String getName() {
        return name;
    }

    public boolean hasBackup() {

        boolean hasBak = true;

        for (File temp : files) {
            File tempFile = new File(temp.getAbsolutePath() + "_bak");
            if (!tempFile.exists())
                hasBak = false;
            logger.debug("File " + temp.getAbsolutePath() +" has Backup: " + hasBak);
        }

        return hasBak;
    }

    public void createBackup() throws AlreadyModdedException {

        if (!hasBackup()) {
            try {
                for (File file : files) {
                    File bakFile = new File(file.getAbsolutePath() + "_bak");
                    if (!bakFile.exists()) {
                        logger.debug("Creating Backup: File: " + file.getAbsolutePath());
                        Misc.copyFile(file, bakFile);
                    } else {
                        logger.error("Backup file still exists: [" + bakFile.getAbsolutePath() + "] this might be the original File, will leave this unchanged!");
                    }
                }
            } catch (IOException e) {
                logger.error("[" + this.getName() + "] Backup error");
            }

        } else {
            logger.error("Archive \"" + this.getName() + "\" is already modded");
            throw new AlreadyModdedException("Archive \"" + this.getName()
                    + "\" is already modded");
        }
    }

    public void unpatch() throws notModdedExcption {
        if (hasBackup()) {
            try {
                if (hasBackup()) {
                    for (File file : files) {
                        File bakFile = new File(file.getAbsolutePath() + "_bak");
                        Misc.copyFile(bakFile,file);
                        bakFile.delete();
                    }
                }
                else
                {
                    logger.error("Backup not found");
                }
            } catch (IOException e) {
                logger.error("[" + this.getName() + "] Unpatch error");
            }

        } else {
            logger.error("Archive \"" + this.getName() + "\" is not modded");
            throw new notModdedExcption("Archive \"" + this.getName()
                    + "\" is not modded");
        }
    }

    public void patch() throws AlreadyModdedException {
        Date start = new Date();
        createBackup();
        // Patchlogic
        try {
            for(File soundPackFile : pack.getParts())
            {
                logger.debug("Gonna patch " + soundPackFile.getName());
                for(File archiveFile : files)
                {
                    if(soundPackFile.getName().equals(archiveFile.getName()))
                    {
                        logger.debug("Found file in Archive: " + archiveFile.getAbsolutePath());
                        Misc.copyFile(soundPackFile,archiveFile);
                        logger.debug("File Patched");
                    }
                }
            }
            Date end = new Date();
            logger.info("Patching Done :) ("
                    + ((end.getTime() - start.getTime()) + "ms)"));
        } catch (IOException e) {
            logger.error("[" + this.getName() + "] Patch error");
        }
    }

    public File searchFiles(File basePath, String searchFile) {;
        if (basePath.exists()) {
            File retval =  searchOtherFiles(basePath, searchFile);
            if(retval != null) {
                logger.debug("retval: " + retval.getAbsolutePath());
                return retval;
            } else
            {
                logger.error("File not found: " + searchFile);
            }
        }
        else {
            logger.error("League of Legends path not found. (" + basePath.getAbsolutePath() + ")");
        }
        return null;
    }


    private File searchOtherFiles(File folder, String searchFile) {
        File retVal = null;
        for (File file : folder.listFiles()) {
            if (file.isDirectory() && file != null) {
                retVal =  searchOtherFiles(file, searchFile);
                if(retVal != null)
                {
                    return retVal;
                }
            } else {

                if (file.getName().equals(searchFile)) {

                    retVal = file;
                    break;
                }

            }
        }
        return retVal;
    }
}
