package RADSSoundPatcher.Manager;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import RADSSoundPatcher.Misc.Misc;
import RADSSoundPatcher.exception.AlreadyModdedException;
import RADSSoundPatcher.exception.ArchiveException;
import RADSSoundPatcher.exception.notModdedExcption;

/**
 * Created by philipp on 10.11.2014.
 */
public class ArchiveFile {
	private String name;
	private String championname;
	File wpkFile;
	File eventFile;
	File bnkFile;
	Logger logger = Logger.getRootLogger();

	public ArchiveFile(File wpkFile) throws ArchiveException {

		// Dont know if I have to handle this this way, some users had
		// troubles to install a mod on another Region if those files
		// remains the same

		this.wpkFile = wpkFile;
		File parentFolder = wpkFile.getParentFile();
		String filename = wpkFile.getName().replace("audio.wpk", "");
		for (File tempFile : parentFolder.listFiles()) {

			// TODO Check fertig machen
			if (tempFile.getName().equals(filename + "audio.bnk"))
				this.bnkFile = tempFile;

			if (tempFile.getName().equals(filename + "events.bnk"))
				this.eventFile = tempFile;
		}
		if (wpkFile != null && bnkFile != null && eventFile != null) {
			this.name = wpkFile.getName();
			this.championname = wpkFile.getName().split("_")[0] + " "
					+ wpkFile.getName().split("_")[1];


		} else {
			//logger.error("[" + wpkFile
			//		+ "] Archive does not contain all needed Files");
			throw new ArchiveException("Archive does not have all files.");
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getEventFile() {
		return eventFile;
	}

	public void setEventFile(File eventFile) {
		this.eventFile = eventFile;
	}

	public File getBnkFile() {
		return bnkFile;
	}

	public String getChampionname() {
		return championname;
	}

	public void setChampionname(String championname) {
		this.championname = championname;
	}


	public File getWpkFile() {
		return wpkFile;
	}

	public boolean hasBackup() {
		File wpkBackup = new File(wpkFile.getAbsolutePath() + "_bak");
		File bnkFileBackup = new File(bnkFile.getAbsolutePath() + "_bak");
		File eventFileBackup = new File(eventFile.getAbsolutePath() + "_bak");

		if (!wpkBackup.exists() && !bnkFileBackup.exists()
				&& !eventFileBackup.exists())
			return false;
		else
			return true;

	}

	public void createBackup() throws AlreadyModdedException {

		if (!hasBackup()) {
			try {
				Misc.copyFile(wpkFile, new File(wpkFile.getAbsolutePath() + "_bak"));		
				Misc.copyFile(bnkFile, new File(bnkFile.getAbsolutePath() + "_bak"));			
				Misc.copyFile(eventFile, new File(eventFile.getAbsolutePath() + "_bak"));
			} catch (IOException e) {
				logger.error("["+this.getName()+"] Backup error");
			}

		} else {

			logger.error("Archive \"" + this.getName() + "\" is already modded");
			throw new AlreadyModdedException("Archive \"" + this.getName()
					+ "\" is already modded");
		}
	}

	public void unpatch() throws notModdedExcption {
		if (hasBackup()) {
			File wpkbak = new File(wpkFile.getAbsolutePath() + "_bak");
			File bnkbak = new File(bnkFile.getAbsolutePath() + "_bak");
			File eventbak = new File(eventFile.getAbsolutePath() + "_bak");
			try {
				Misc.copyFile(wpkbak, wpkFile);
				Misc.copyFile(bnkbak, bnkFile);
				Misc.copyFile(eventbak, eventFile);
				wpkbak.delete();
				bnkbak.delete();
				eventbak.delete();
			} catch (IOException e) {
				logger.error("["+this.getName()+"] Unpatch error");
			}			

		} else {
			logger.error("Archive \"" + this.getName() + "\" is not modded");
			throw new notModdedExcption("Archive \"" + this.getName()
					+ "\" is not modded");
		}
	}

	public void patch(ArchiveFile soundpack) throws AlreadyModdedException {
		Date start = new Date();
		logger.info("File found in LoLClient: "
				+ this.getWpkFile().getAbsolutePath());
		createBackup();
		// Patchlogic
		try {
			Misc.copyFile(soundpack.getWpkFile(), this.getWpkFile());		
			Misc.copyFile(soundpack.getBnkFile(), this.getBnkFile());		
			Misc.copyFile(soundpack.getEventFile(), this.getEventFile());
			Date end = new Date();
			logger.info("Patching Done :) ("
					+ ((end.getTime() - start.getTime()) + "ms)"));
		} catch (IOException e) {
			logger.error("["+this.getName()+"] Patch error");
		}
	}
}
