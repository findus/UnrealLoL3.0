package RADSSoundPatcher.Manager;

import RADSSoundPatcher.Find.Tools;
import RADSSoundPatcher.exception.AlreadyModdedException;
import RADSSoundPatcher.exception.ArchiveException;

import RADSSoundPatcher.exception.SoundpackNotValidException;
import RADSSoundPatcher.exception.notModdedExcption;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by philipp on 10.11.2014.
 */
public class ArchiveManager {



    private File lolPath;
	private static final File SOUNDPACKFOLDER = new File("Soundpacks/");
	private List<Soundpack> soundpacks;



    private File path;

	Logger logger = Logger.getRootLogger();

	public ArchiveManager(String region) {
		searchLoL();

		this.soundpacks = new ArrayList<Soundpack>();

        if(lolPath != null)
        {
            this.path =new File(this.lolPath.getAbsolutePath()+  "\\RADS\\projects\\"
                    + Tools.GetRegionInformation("English") + "\\managedfiles\\");
            if(path.exists())
            {
                //this.searchSoundArchive();
            }
            else
            {
                logger.info("No LoL installation found");
            }

        }
        else
        {
            logger.info("No LoL installation found");
        }


        this.reloadSoundpackList();
	}

    public File getLolPath() {
        return lolPath;
    }

	public void reloadSoundpackList() {

		this.soundpacks.clear();
        if(SOUNDPACKFOLDER.exists()) {
            for (File soundpackFolder : SOUNDPACKFOLDER.listFiles()) {
                if (soundpackFolder.isDirectory()) {
                    try {
                        soundpacks.add(new Soundpack(soundpackFolder,path));
                    } catch (SoundpackNotValidException e) {
                        logger.error(e.getMessage());
                    } catch (ArchiveException e) {
                        e.printStackTrace();
                    }
                }

            }
        } else {
            logger.error("Soundpack folder is missing");
        }

		logger.info("Loaded " + soundpacks.size() + " Soundpacks.");
	}

	// TODO Code Method that detects duplicates
	public void installArchive(Soundpack soundpack) throws IOException,
			AlreadyModdedException {
		Date start = new Date();
		logger.info("Patching now! Soundpack: " + soundpack.getName());
		soundpack.getArchiveFile().patch();
		Date end = new Date();
		logger.error("Patching done ("
				+ ((end.getTime() - start.getTime()) + "ms)"));
	}

    public void deinstallArchive(Soundpack soundpack) throws IOException,
            AlreadyModdedException, notModdedExcption {
        Date start = new Date();
        soundpack.getArchiveFile().unpatch();
        Date end = new Date();
        logger.error("Unpatching done ("
                + ((end.getTime() - start.getTime()) + "ms)"));
    }


    public List<Soundpack> getSoundpacks() {
		return soundpacks;
	}


	public void switchRegion(String region) {
        logger.info("Trying to switch region to " + region);
        soundpacks.clear();
        this.path = new File(this.lolPath.getAbsolutePath()+  "\\RADS\\projects\\"
                + Tools.GetRegionInformation(region) + "\\managedfiles\\");
		reloadSoundpackList();
	}

    public void setLolPath(String path)
    {
     this.lolPath =new File(path);
        //TODO maybe refresh
    }

    public void searchLoL() {
        try {
            BufferedReader LAWL = null;
            String Folder = null;
            File Paath = new File("Path");
            if (Paath.exists()) {
                LAWL = new BufferedReader(new FileReader("Path"));
                Folder = LAWL.readLine();
                File file = new File(Folder);
                if (file.exists()) {
                    this.lolPath = new File(Folder);
                } else {
                    logger.info("- Predefined Path containts no LoL installation");
                }
            }
        } catch (IOException e) {
            logger.error("Excpetion while reading file: " + e.getClass()+ " " + e.getMessage());
        }


    }
}
