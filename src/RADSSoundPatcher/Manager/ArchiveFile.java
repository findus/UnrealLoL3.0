package RADSSoundPatcher.Manager;

import java.io.File;

/**
 * Created by philipp on 10.11.2014.
 */
public class ArchiveFile {
    private String name;
    private String championname;
    private boolean modded;
    File wpkFile;
    File eventFile;
    File bnkFile;


    public ArchiveFile(File wpkFile,File eventFile,File bnkFile)
    {
        this.wpkFile = wpkFile;
        this.bnkFile = bnkFile;
        this.eventFile = eventFile;

        this.name = wpkFile.getName();
        this.championname = wpkFile.getName().split("_")[0] + " " +  wpkFile.getName().split("_")[1];

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

    public void setBnkFile(File bnkFile) {
        this.bnkFile = bnkFile;
    }

	public String getChampionname() {
		return championname;
	}

	public void setChampionname(String championname) {
		this.championname = championname;
	}

	public boolean isModded() {
		return modded;
	}

	public void setModded(boolean modded) {
		this.modded = modded;
	}

	public File getWpkFile() {
		return wpkFile;
	}

	public void setWpkFile(File wpkFile) {
		this.wpkFile = wpkFile;
	}
}
