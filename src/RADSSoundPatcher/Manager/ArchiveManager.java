package RADSSoundPatcher.Manager;

import RADSSoundPatcher.Find.Tools;
import RADSSoundPatcher.exception.ArchiveException;
import RADSSoundPatcher.exception.SoundpackNotValidException;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 10.11.2014.
 */
public class ArchiveManager {

	private static final File SOUNDPACKFOLDER = new File("Soundpacks/");
	private List<File> soundpacks;

	private String managedFilesPath;
	private String leagueOfLegendsBasePath;
	private String region;

	Logger logger = Logger.getRootLogger();

	public ArchiveManager(String region) throws FileNotFoundException {
		logger.debug("Instantiate ArchiveManager >> Region:" + region);
		this.soundpacks = new ArrayList<File>();
		searchLoL();
		loadSoundpackList();
		switchRegion("English");
	}

	public List<File> getSoundpacks() {
		return soundpacks;
	}

	public String getManagedFilesPath() {
		return managedFilesPath;
	}

	public void setLeagueOfLegendsBasePath(String path)
			throws FileNotFoundException {
		this.leagueOfLegendsBasePath = path;
	}

	public String getLeagueOfLegendsBasePath() {
		return leagueOfLegendsBasePath;
	}

	public File getFullLoLPath() {
		if (leagueOfLegendsBasePath != null) {
			return new File(leagueOfLegendsBasePath + managedFilesPath);
		}
		return null;

	}

	public void loadSoundpackList() {
		logger.debug("Loading all installed Soundpacks");
		this.soundpacks.clear();
		if (SOUNDPACKFOLDER.exists()) {
			for (File soundpackFolder : SOUNDPACKFOLDER.listFiles()) {
				if (soundpackFolder.isDirectory()) {
					soundpacks.add(soundpackFolder);
					logger.debug(soundpackFolder.getName() + " added to List");
				}
			}
		} else {
			logger.error("Soundpack folder is missing");
		}

		logger.info("Loaded " + soundpacks.size() + " Soundpacks.");
	}

	public void switchRegion(String region) {
		logger.info("Trying to switch region to " + region);

		this.region = Tools.GetRegionInformation(region);
		this.managedFilesPath = "\\RADS\\projects\\" + this.region
				+ "\\managedfiles\\";

		if (!new File(leagueOfLegendsBasePath + managedFilesPath).exists()) {
			logger.error("Region: " + region + " is not installed");
		}

		logger.debug("Region switch: BasePath: " + leagueOfLegendsBasePath
				+ managedFilesPath);
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
					this.leagueOfLegendsBasePath = Folder;
				} else {
					logger.info("- Predefined Path containts no LoL installation");
				}
			}
		} catch (IOException e) {
			logger.error("Excpetion while reading file: " + e.getClass() + " "
					+ e.getMessage());
		}

	}
}
