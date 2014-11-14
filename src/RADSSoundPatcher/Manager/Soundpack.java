package RADSSoundPatcher.Manager;

import RADSSoundPatcher.exception.ArchiveException;
import RADSSoundPatcher.exception.SoundpackNotValidException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by philipp on 11.11.2014.
 */
public class Soundpack {



    private String name;
    private File folder;
    List<File> soundpackFiles = new ArrayList();
    Logger logger = Logger.getRootLogger();

    public ArchiveFile getArchiveFile() {
        return archiveFile;
    }

    ArchiveFile archiveFile;


    public Soundpack(File file,File basePath) throws SoundpackNotValidException, ArchiveException {
        logger.debug("Instantiate Soundpack: " + file.getAbsolutePath());
    	if(file.exists() && file.isDirectory()) {
            this.name = file.getName();
            this.folder = file;
            loadFiles();
            archiveFile = new ArchiveFile(this,basePath);
        }else
        {
            logger.error(file.getAbsolutePath() + " does not exist or is not a directory");
        	throw new SoundpackNotValidException();
        }
    }

    private void loadFiles() throws SoundpackNotValidException {
        if(folder.listFiles().length < 1)
        {
            logger.error("Soundpack contains no files");
        	throw new SoundpackNotValidException();
        }
        for(File temp : folder.listFiles())
        {
            logger.debug("Loading file: " + temp.getName());
        	soundpackFiles.add(temp);
        }
    }

    public String getName() {
        return name;
    }

    public List<File> getSoundpackFiles() {
        return soundpackFiles;
    }

	@Override
	public String toString() {
		return name;
	}
    


}
