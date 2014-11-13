package RADSSoundPatcher.Manager;

import RADSSoundPatcher.exception.ArchiveException;
import RADSSoundPatcher.exception.SoundpackNotValidException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 11.11.2014.
 */
public class Soundpack {



    private String name;
    private File folder;
    List<File> parts = new ArrayList();

    public ArchiveFile getArchiveFile() {
        return archiveFile;
    }

    ArchiveFile archiveFile;


    public Soundpack(File file,File basePath) throws SoundpackNotValidException, ArchiveException {
        if(file.exists() && file.isDirectory()) {
            this.name = file.getName();
            this.folder = file;
            loadFiles();
            archiveFile = new ArchiveFile(this,basePath);
        }else
        {
            throw new SoundpackNotValidException();
        }
    }

    private void loadFiles() throws SoundpackNotValidException {
        if(folder.listFiles().length < 1)
            throw new SoundpackNotValidException();
        for(File temp : folder.listFiles())
        {
            parts.add(temp);
        }
    }

    public String getName() {
        return name;
    }

    public List<File> getParts() {
        return parts;
    }

	@Override
	public String toString() {
		return name;
	}
    
    


}
