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
	Logger logger = Logger.getRootLogger();

	public ArchiveFile(Soundpack pack, File basePath) throws ArchiveException,
			SoundpackNotValidException {
		logger.debug("Instantiate new ArchiveFile >> Soundpack: "
				+ pack.getName());
		// Dont know if I have to handle this this way, some users had
		// troubles to install a mod on another Region if those files
		// remains the same

		this.pack = pack;

		for (File temp : pack.getSoundpackFiles()) {
			File tempFile = searchFiles(basePath, temp.getName());

			if (tempFile != null) {
				files.add(tempFile);
			} else {
				logger.error(temp.getName()
						+ " not found in filesystem. Soundpack is not valid.");
				throw new SoundpackNotValidException();
			}
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
			logger.debug("File " + temp.getAbsolutePath() + " has Backup: "
					+ hasBak);
		}
		return hasBak;
	}

	public void createBackup() throws AlreadyModdedException {
		logger.debug("Attempt to backup all Files from " + pack.getName());
		if (!hasBackup()) {
			try {
				for (File file : files) {
					File bakFile = new File(file.getAbsolutePath() + "_bak");
					if (!bakFile.exists()) {
						logger.debug("Creating Backup: File: "
								+ file.getAbsolutePath());
						Misc.copyFile(file, bakFile);
					} else {
						logger.error("Backup file still exists: ["
								+ bakFile.getAbsolutePath()
								+ "] this might be the original File, will leave this unchanged!");
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
		try {
			Date start = new Date();
			if (hasBackup()) {
				for (File file : files) {
					logger.debug(file.getName()
							+ " _bak will be restored, this file will be deleted");
					File bakFile = new File(file.getAbsolutePath() + "_bak");
					Misc.copyFile(bakFile, file);
					bakFile.delete();
				}
			Date end = new Date();
			logger.info("Unpatching done (" + (end.getTime() - start.getTime()) + "ms)");
			} else {
				logger.error("Archive \"" + this.getName() + "\" is not modded");
				throw new notModdedExcption("Archive \"" + this.getName()
						+ "\" is not modded");
			}
		} catch (IOException e) {
			logger.error("[" + this.getName() + "] Unpatch error ["
					+ e.getMessage() + "]");
		}

	}

	public void patch() throws AlreadyModdedException {
		Date start = new Date();
		createBackup();
		// Patchlogic
		try {
			for (File soundPackFile : pack.getSoundpackFiles()) {
				logger.debug("Gonna patch " + soundPackFile.getName());
				for (File archiveFile : files) {
					if (soundPackFile.getName().equals(archiveFile.getName())) {
						logger.debug("Found file in Archive: "
								+ archiveFile.getAbsolutePath());
						Misc.copyFile(soundPackFile, archiveFile);
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

	public File searchFiles(File basePath, String searchFile) {
		logger.debug("Gonna search for: " + searchFile + " in the LoL Filesystem");
		if (basePath.exists()) {
			File retval = searchOtherFiles(basePath, searchFile);
			if (retval != null) {
				logger.debug("File found: " + retval.getAbsolutePath());
				return retval;
			} else {
				logger.error("File not found: " + searchFile);
			}
		} else {
			logger.error("League of Legends path not found. ("
					+ basePath.getAbsolutePath() + ")");
		}
		return null;
	}

	private File searchOtherFiles(File folder, String searchFile) {
		File retVal = null;
		for (File file : reverseArray(folder.listFiles())) {
			if (file.isDirectory()) {
				retVal = searchOtherFiles(file, searchFile);
				if (retVal != null) {
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

	public File[] reverseArray(File[] array)
	{
		for(int i = 0; i < array.length / 2; i++)
		{
			File temp = array[i];
			array[i] = array[array.length - i - 1];
			array[array.length - i - 1] = temp;
		}
		return array;
	}
}
