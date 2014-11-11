package RADSSoundPatcher.Manager;

/**
 * Created by philipp on 11.11.2014.
 */
public class Soundpack {



    private ArchiveFile archive;
    private String name;


    public Soundpack(String name,ArchiveFile file)
    {
        this.archive = file;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArchiveFile getArchive() {
        return archive;
    }

    public void setArchive(ArchiveFile archive) {
        this.archive = archive;
    }


}
