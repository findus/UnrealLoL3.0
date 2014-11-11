package RADSSoundPatcher.Manager;

import RADSSoundPatcher.Find.Tools;
import RADSSoundPatcher.Misc.Misc;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by philipp on 10.11.2014.
 */
public class ArchiveManager {

	private File lolPath;
	private static final File SOUNDPACKFOLDER = new File("Soundpacks/");
	private List<ArchiveFile> archiveFiles;
	private List<Soundpack> soundpacks;

	private final String PATH;

	Logger logger = Logger.getRootLogger();

	public ArchiveManager(File lolpath,String region) throws URISyntaxException,
			ParserConfigurationException, SAXException, IOException {
		this.lolPath = lolpath;
		this.PATH = "\\RADS\\projects\\" + Tools.GetRegionInformation("English")
				+ "\\managedfiles\\";
		this.archiveFiles = new ArrayList();
		this.soundpacks = new ArrayList<Soundpack>();

        this.reloadArchiveList();
        this.reloadSoundpackList();

	}

	public void reloadArchiveList() {
		this.archiveFiles.clear();
		reloadArchiveList(new File(this.lolPath.getAbsolutePath() + this.PATH));
		logger.info("Loaded " + archiveFiles.size() + " Archives.");
	}

	private void reloadArchiveList(File folder) {
        File audiowpk,event,audiobnk;
        audiobnk = event = audiowpk = null;
        String filename = null;
        for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				reloadArchiveList(file);
			} else {

                if (file.getName().endsWith("audio.wpk")) {
                    audiowpk = file;
                    filename = file.getName().replace("audio.wpk","");

                    audiobnk = new File(audiowpk.getAbsoluteFile().getParent() + "\\" +filename + "audio.bnk");
                    if(!audiobnk.exists())
                        audiobnk = null;
                    event = new File(audiowpk.getAbsoluteFile().getParent() + "\\" +filename  + "events.bnk");
                    if(!event.exists())
                        event = null;

                    if(audiowpk != null && audiobnk != null && event != null)
                        this.archiveFiles.add(new ArchiveFile(audiowpk,event,audiobnk));
                    else
                        logger.error("Archive: \"" + file + "\" does not contain all needed Files");

            }

            }
		}	
	}

	public void reloadSoundpackList() {
		
		this.soundpacks.clear();
		for (File soundpackFolder : SOUNDPACKFOLDER.listFiles()) {
			if(soundpackFolder.isDirectory())
            {
                File audiowpk,event,audiobnk;
                audiobnk = event = audiowpk = null;
                String filename;


                for(File g : soundpackFolder.listFiles())
                {
                    if (g.getName().endsWith("audio.wpk")) {
                        audiowpk = g;

                        filename = g.getName().replace("audio.wpk","");

                        audiobnk = new File(audiowpk.getAbsoluteFile().getParent() + "\\" +filename + "audio.bnk");
                        if(!audiobnk.exists())
                            audiobnk = null;
                        event = new File(audiowpk.getAbsoluteFile().getParent() + "\\" +filename  + "events.bnk");
                        if(!event.exists())
                            event = null;

                        if(audiowpk != null && audiobnk != null && event != null) {
                            this.soundpacks.add(new Soundpack(soundpackFolder.getName(),new ArchiveFile(audiowpk,event,audiobnk)));
                            logger.info("Soundpack \"" + soundpackFolder + "\" loaded");
                        }
                        else {
                            logger.error("Soundpack: \"" + soundpackFolder + "\" does not contain all needed Files");
                        }
                    }

                }

            }

		}
		logger.info("Loaded " + soundpacks.size() + " Soundpacks.");
	}

	//TODO Code Method that detects duplicates
	public void installArchive(ArchiveFile installFile) throws IOException {
		Date start = new Date();
		logger.info("Patching now! File: " + installFile.getName());
		String name = installFile.getName();
		for (ArchiveFile file : archiveFiles) {
			if (name.equals(file.getName())) {
				logger.info("File found in LoLClient: "
						+ file.getWpkFile().getAbsolutePath());
				if (!hasBackup(file))
					createBackup(file);				
				//Patchlogic
				Misc.copyFile(installFile.getWpkFile(), file.getWpkFile());
				//TODO Patchlogic
				Date end = new Date();
				logger.info("Patching Done :) (" + ((end.getTime()-start.getTime()) + "ms)"));
				return;
			}
		}
		Date end = new Date();
		logger.error("File is not present in your LoLClient, patching aborted (" + ((end.getTime()-start.getTime()) + "ms)"));
	}

	public void deleteArchive(ArchiveFile file) throws IOException {
		if (!hasBackup(file)) {
			logger.error("Can't delete wpkFile, no Backup available");
		} else {
			Misc.copyFile(new File(file.getWpkFile().getAbsoluteFile()+"_bak"), file.getWpkFile());
		}
	}

	private boolean hasBackup(ArchiveFile file) {
		File backupFile = new File(file.getWpkFile().getAbsolutePath() + "_bak");
		if (!backupFile.exists())
			return false;
		else
			return true;
	}

	private void createBackup(ArchiveFile file) throws IOException {
		Misc.copyFile(file.getWpkFile(), new File(file.getWpkFile().getAbsolutePath()
				+ "_bak"));
	}

	public List<Soundpack> getSoundpacks() {
		return soundpacks;
	}


}
