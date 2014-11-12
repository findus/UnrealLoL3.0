package RADSSoundPatcher.Manager;

import RADSSoundPatcher.Find.Tools;
import RADSSoundPatcher.Misc.Misc;
import RADSSoundPatcher.exception.AlreadyModdedException;
import RADSSoundPatcher.exception.ArchiveException;

import RADSSoundPatcher.exception.notModdedExcption;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
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

	private String PATH;

	Logger logger = Logger.getRootLogger();

	public ArchiveManager(File lolpath, String region) {
		this.lolPath = lolpath;
		this.PATH = "\\RADS\\projects\\"
				+ Tools.GetRegionInformation("English") + "\\managedfiles\\";
		this.archiveFiles = new ArrayList();
		this.soundpacks = new ArrayList<Soundpack>();
        File file = new File(this.lolPath.getAbsolutePath() + this.PATH);
		if(!file.exists())
		{
			JOptionPane.showMessageDialog(null, "LoL Path not found.");
		}else
		{
			this.reloadArchiveList();
			
		}
		this.reloadSoundpackList();
		
		

	}

	public void reloadArchiveList() {
		this.archiveFiles.clear();
        if(new File(this.lolPath.getAbsolutePath() + this.PATH).exists())
            reloadArchiveList(new File(this.lolPath.getAbsolutePath() + this.PATH));
        logger.info("Loaded " + archiveFiles.size() + " Archives.");


	}

	private void reloadArchiveList(File folder) {
		for (File file : folder.listFiles()) {
			if (file.isDirectory() && file != null) {
				reloadArchiveList(file);
			} else {

				if (file.getName().endsWith("audio.wpk")) {

					try {
						ArchiveFile arcFile = new ArchiveFile(file);
						this.archiveFiles.add(arcFile);
					} catch (ArchiveException e) {
						//logger.error(e.getMessage());
					}
				}

			}
		}
	}

	public void reloadSoundpackList() {

		this.soundpacks.clear();
		for (File soundpackFolder : SOUNDPACKFOLDER.listFiles()) {
			if (soundpackFolder.isDirectory()) {

				for (File g : soundpackFolder.listFiles()) {
					if (g.getName().endsWith("audio.wpk")) {

						Soundpack soundPack;
						try {
							soundPack = new Soundpack(
									soundpackFolder.getName(), new ArchiveFile(
											g));
							this.soundpacks.add(soundPack);
							logger.info("Soundpack \"" + soundpackFolder
									+ "\" loaded");
						} catch (ArchiveException e) {
							logger.error(e.getMessage());
						}

					}
				}

			}

		}

		logger.info("Loaded " + soundpacks.size() + " Soundpacks.");
	}

	// TODO Code Method that detects duplicates
	public void installArchive(Soundpack soundpack) throws IOException,
			AlreadyModdedException {
		Date start = new Date();
		logger.info("Patching now! Soundpack: " + soundpack.getName());
		String archiveName = soundpack.getArchive().getName();
		for (ArchiveFile file : archiveFiles) {
			if (archiveName.equals(file.getName())) {
				file.patch(soundpack.getArchive());
				return;
			}
		}
		Date end = new Date();
		logger.error("File is not present in your LoLClient, patching aborted ("
				+ ((end.getTime() - start.getTime()) + "ms)"));
	}

    public void deinstallArchive(Soundpack soundpack) throws IOException,
            AlreadyModdedException, notModdedExcption {
        Date start = new Date();
        logger.info("UnPatching now! Soundpack: " + soundpack.getName());
        String archiveName = soundpack.getArchive().getName();
        for (ArchiveFile file : archiveFiles) {
            if (archiveName.equals(file.getName())) {
                file.unpatch();
                return;
            }
        }
        Date end = new Date();
        logger.error("File is not present in your LoLClient, patching aborted ("
                + ((end.getTime() - start.getTime()) + "ms)"));
    }


    public List<Soundpack> getSoundpacks() {
		return soundpacks;
	}

	public boolean isSoundpackInstalled(Soundpack p) throws FileNotFoundException
	{
		for(ArchiveFile file : this.archiveFiles)
		{
			if(file.getWpkFile().getName().equals(p.getArchive().getWpkFile().getName()))
			{
				logger.info("[" + p.getName() +"] Matching Archive found");
				return file.hasBackup();
			}
		}
		throw new FileNotFoundException("Matching archive not found") ;
		
	}
	
	public void switchRegion(String region) {
		logger.info("Trying to switch region to " + region);
		this.PATH = "\\RADS\\projects\\" + Tools.GetRegionInformation(region)
				+ "\\managedfiles\\";
		reloadArchiveList();
	}
}
