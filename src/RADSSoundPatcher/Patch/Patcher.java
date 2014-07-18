package RADSSoundPatcher.Patch;

import RADSSoundPatcher.Find.Tools;
import RADSSoundPatcher.Misc.LogFilev2;
import RADSSoundPatcher.Misc.Misc;
import RADSSoundPatcher.Soundpacks.FsbextractLogParser;
import RADSSoundPatcher.Soundpacks.SwapSoundfiles;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: philipp
 * Date: 18.10.13
 * Time: 08:59
 * To change this template use File | Settings | File Templates.
 */
public class Patcher {

    Process fsbext = null;
    String _leagueOfLegendsPath = null;
    String _newestFolder = null;
    String _soundpack = null;
    String _serverRegion = null;
    String _soundarchiveName = null;
    String _soundpackRegionFolder = null;
    String _constructor = null;
    String _comboboxRegion = null;
    String _vobankPath = null;
    int archivefiles = 0;
    ArrayList _regionInformation = new ArrayList();
    int _swapmode = 0; // 0: Default, 1: Allied Only, 2: Enemy Only, 3: Swap
    PropertyChangeSupport PropertySupport = null;
    static Logger logger = Logger.getRootLogger();

    private Patcher() {

    }

    public Patcher(String LeagueOfLegendsPath, String Soundpack, String Region, String SwapMode) throws Exception {
        PropertySupport = new PropertyChangeSupport(this);
        this._leagueOfLegendsPath = LeagueOfLegendsPath;
        this._soundpack = Soundpack;
        this._comboboxRegion = Region;
        try {
            this._regionInformation = Tools.GetRegionInformation(Region);  // 0:Region 1: Constructor 2: Vobank 3: Sounpackregionfolder.
        } catch (Exception e) {
            throw e;
        }
        this._serverRegion = _regionInformation.get(0).toString();
        this._constructor = _regionInformation.get(1).toString();
        this._soundarchiveName = _regionInformation.get(2).toString();
        this._soundpackRegionFolder = _regionInformation.get(3).toString();
        this._newestFolder = Tools.GetNewestFolder(_leagueOfLegendsPath, _serverRegion, _soundarchiveName);
        this._swapmode = getSwapInteger(SwapMode);
        this._vobankPath = _leagueOfLegendsPath + "/RADS/projects/" + _serverRegion + "/managedfiles/" + _newestFolder + "/Data/Sounds/FMOD/";
        try {
            this.CheckInput(false);
        } catch (PatchException e) {
            throw e;
        }
    }

    public Patcher(String LeagueOfLegendsPath, String Soundpack, String Region, int SwapMode) throws Exception {
        PropertySupport = new PropertyChangeSupport(this);
        this._leagueOfLegendsPath = LeagueOfLegendsPath;
        this._soundpack = Soundpack;
        this._comboboxRegion = Region;
        try {
            this._regionInformation = Tools.GetRegionInformation(Region);  // 0:Region 1: Constructor 2: Vobank 3: Sounpackregionfolder.
        } catch (Exception e) {
            throw e;
        }
        this._serverRegion = _regionInformation.get(0).toString();
        this._constructor = _regionInformation.get(1).toString();
        this._soundarchiveName = _regionInformation.get(2).toString();
        this._soundpackRegionFolder = _regionInformation.get(3).toString();
        this._newestFolder = Tools.GetNewestFolder(_leagueOfLegendsPath, _serverRegion, _soundarchiveName);
        this._swapmode = SwapMode;
        this._vobankPath = _leagueOfLegendsPath + "/RADS/projects/" + _serverRegion + "/managedfiles/" + _newestFolder + "/Data/Sounds/FMOD/";
        try {
            this.CheckInput(false);
        } catch (PatchException e) {
            throw e;
        }
    }

    //Unpatch
    public Patcher(String LeagueOfLegendsPath, String Region) throws Exception {
        PropertySupport = new PropertyChangeSupport(this);
        this._leagueOfLegendsPath = LeagueOfLegendsPath;
        this._comboboxRegion = Region;
        try {
            this._regionInformation = Tools.GetRegionInformation(Region);  // 0:Region 1: Constructor 2: Vobank 3: Sounpackregionfolder.
        } catch (Exception e) {
            throw e;
        }
        this._serverRegion = _regionInformation.get(0).toString();
        this._constructor = _regionInformation.get(1).toString();
        this._soundarchiveName = _regionInformation.get(2).toString();
        this._soundpackRegionFolder = _regionInformation.get(3).toString();
        this._newestFolder = Tools.GetNewestFolder(_leagueOfLegendsPath, _serverRegion, _soundarchiveName);
        this._vobankPath = _leagueOfLegendsPath + "/RADS/projects/" + _serverRegion + "/managedfiles/" + _newestFolder + "/Data/Sounds/FMOD/";
        try {
            this.CheckInput(true);
        } catch (PatchException e) {
            throw e;
        }
    }

    public Patcher(File PathToCustomSoundbank, String Soundpack, String Soundbankname) throws PatchException {
        PropertySupport = new PropertyChangeSupport(this);
        this._vobankPath = PathToCustomSoundbank.getAbsolutePath() + "/";
        this._soundpack = Soundpack;
        this._soundarchiveName = Soundbankname;
    } //Custom Soundbank


    private int getSwapInteger(String Swap) {
        int retval = 0;
        if (Swap.equals("Allied Announcer Only")) {
            retval = 1;
        }
        if (Swap.equals("Enemy Announcer Only")) {
            retval = 2;
        }
        if (Swap.equals("Swap")) {
            retval = 3;
        }
        return retval;
    }

    protected String getSwapString(int Swap) {
        String retval = null;
        if (Swap == 0) {
            retval = "Default";
        }
        if (Swap == 1) {
            retval = "Allied Announcer Only";
        }
        if (Swap == 2) {
            retval = "Enemy Announcer Only";
        }
        if (Swap == 3) {
            retval = "Swap Announcer";
        }
        return retval;
    }

    public void PrepareSoundpackforPatch() {
        LogFilev2.setDate();
        File FFmpgeg = new File("fsbext/ffmpeg.exe");
        if (!FFmpgeg.exists()) {
            JOptionPane.showMessageDialog(null, "FFMpeg not found, please restart the mod and download it!");
            logger.fatal("FFMpeg not found, please restart the mod and download it!");
        } else {
            Boolean Compatibility = CheckCompatibility(_soundpack, _serverRegion.trim());
            if (Compatibility == false) {
                System.out.println("- Selected Soundpack " + _soundpack + " is not compatible with " + _serverRegion);
                String Msg = "Selected Soundpack " + _soundpack + " is not compatible with " + _serverRegion;
                logger.fatal("Selected Soundpack " + _soundpack + " is not compatible with " + _serverRegion);
                FireEvent("ErstelleMessagebox", Msg);
            } else {
                System.out.println("- Soundpack.....: " + this._soundpack);
                System.out.println("- Region........: " + this._comboboxRegion);
                System.out.println("- Announcer Mode: " + this.getSwapString(_swapmode));

                logger.info("Soundpack.....: " + this._soundpack);
                logger.info("Region........: " + this._comboboxRegion);
                logger.info("Announcer Mode: " + this.getSwapString(_swapmode));
                logger.info("VOBank  Folder: " + this._vobankPath + this._soundarchiveName);

                PatchSoundBankWithSpecificSoundpack();
            }
        }
    }

    public void PatchSoundBankWithSpecificSoundpack() {
        //VoBANK kopieren
        try {
            FireEvent("ErhoeheProgressbarWert", 2);
            System.out.print("- Copy VOBank...");
            //CopyVOBank();
            FireEvent("ErhoeheProgressbarWert", 5);
            CreateVOBankBackup();
            // FSBEXT Delete/Rebuilding rebuild.dat
            System.out.println("done!");
            FireEvent("ErhoeheProgressbarWert", 15);
            archivefiles = Fsbext.getFileCount(_vobankPath, _soundarchiveName, this);
            System.out.println("- " + archivefiles + " Soundfiles needs to be extracted");
            System.out.print("- Extracting SoundFiles + Build Rebuild.dat...");

            Fsbext.ExtractSoundbank(this._vobankPath, _soundarchiveName, "Sounds/", this, archivefiles); //Rebuild.dat wird ggf. gelöscht und neu erstellt
            System.out.println("done!");
            System.out.print("- Copy Soundfiles...");
            File Soundpack = new File("Soundpacks/" + _soundpack + "/" + _soundpackRegionFolder);
            File[] SoundpackSoundfiles = Soundpack.listFiles();
            FireEvent("ErhoeheProgressbarWert", 35);
            int Anzahl = SoundpackSoundfiles.length;
            File FFMpeg = new File("FFMPEG.log");
            FileWriter writer;
            writer = new FileWriter(FFMpeg);
            writer.write("--FFMPEG ENCODE PROCESS-- \n \n");
            int _convertedFilesCounter = 0;
            //Copy Soundfiles into "Convert" folder + Swap them if necessary.

            double LaengeValue = SoundpackSoundfiles.length;
            double feggit = (double) 20 / LaengeValue;
            double tempval = 35;
            for (File mp3ext : SoundpackSoundfiles) {
                File inF = new File("Soundpacks/" + _soundpack + "/" + _soundpackRegionFolder + mp3ext.getName());
                File outF = new File("Sounds/Convert/" + _constructor + mp3ext.getName());
                try {
                    Misc.copyFile(inF, outF);
                } catch (IOException e) {
                    e.printStackTrace();
                    Misc.ErrorMessage();
                }
                tempval = tempval + feggit;
                FireEvent("ErhoeheProgressbarWert", (int) tempval);
            }
            FireEvent("ErhoeheProgressbarWert", 55);
            if (_swapmode != 0) {
                SwapSoundfiles.Swap(_soundpack, _swapmode, _serverRegion, _constructor);
            }
            System.out.println("done!");
            System.out.print("- Convert " + Anzahl + " Soundfiles...");
            File ConvertFolder = new File("Sounds/Convert/");
            double ProgressbarValue = (double) 10 / (double) Anzahl;
            double tempvalue = 55;
            for (File Mp3Files : ConvertFolder.listFiles()) {
                if (Mp3Files.isDirectory() == false) {
                    String mp3String = Mp3Files.getName();
                    if (mp3String.endsWith(".mp3") == true) {
                        String inF = "Sounds/Convert/" + Mp3Files.getName();
                        String Long = mp3String.replace(".mp3", "");
                        String Short = "";
                        for (int i = 0; i < Long.length(); i++) {
                            if (Short.length() < 29) {
                                Short = Short + Long.charAt(i);
                            }
                        }
                        Soundfile ConvertInfo = FsbextractLogParser.getRegionInformation(Short); //23

                        //System.out.println(ConvertInfo);
                        //Normal Copy
                        if (ConvertInfo != null) {
                            writer.write(_constructor + mp3String + "\n" + "SamplingRate: " + ConvertInfo.getFrequency() + " Channel: " + ConvertInfo.getChannels() + "\n");
                            if (_serverRegion.equals("lol_game_client_ru_ru")) {
                                Tools.FFMPEGConvert(inF, ConvertInfo.getFrequency(),ConvertInfo.getChannels(), "mp3", "Sounds/" + Mp3Files.getName(), "mp3");
                            } else {
                                Tools.FFMPEGConvert(inF, ConvertInfo.getFrequency(), ConvertInfo.getChannels(), "mp2", "Sounds/" + Mp3Files.getName(), "mp3");
                            }
                            _convertedFilesCounter = _convertedFilesCounter + 1;
                            tempvalue = tempvalue + ProgressbarValue;
                            int ttt = (int) tempvalue;
                            FireEvent("ErhoeheProgressbarWert", ttt);
                        } else {
                            writer.write("ERROR \n");
                            System.out.println("\n- Error: " + Mp3Files.getName() + " not Modded!");
                            logger.error("NOT MODDED>>" + _constructor + mp3String);
                        }
                    }
                }
            }
            System.out.println("done!");
            writer.flush();
            writer.close();
            FireEvent("ErhoeheProgressbarWert", 65);
            System.out.print("- Repacking Soundbank...");

            Fsbext.RepackSoundbank("Files/", _soundarchiveName, "Sounds/", this, archivefiles);
            System.out.println("done!");
            FireEvent("ErhoeheProgressbarWert", 80);
            File mp311 = new File("Sounds/");
            File Convert = new File("Sounds/Convert");
            System.out.print("- Install Soundbank...");
            File[] fileList111 = mp311.listFiles();
            ProgressbarValue = (double) 10 / (double) fileList111.length;
            double temp = 80;

            for (File mp3ext : fileList111) {
                if (mp3ext.isDirectory() == false) {
                    mp3ext.delete();
                }
                temp = temp + (ProgressbarValue);
                FireEvent("ErhoeheProgressbarWert", (int) temp);
            }


            temp = 90;
            File[] fileList112 = Convert.listFiles();
            ProgressbarValue = (double) 10 / (double) fileList112.length;
            for (File mp3ext : fileList112) {
                if (mp3ext.isDirectory() == false) {
                    mp3ext.delete();
                }
                temp = temp + (ProgressbarValue);
                FireEvent("ErhoeheProgressbarWert", (int) temp);
            }
            FireEvent("ErhoeheProgressbarWert", 100);
            //copy VOBank
            CopyVOBankIntoLoL();
            System.out.println("done!");
            FireEvent("ErhoeheProgressbarWert", 100);
            //Cleanup

            System.out.println("- Done!");

            _leagueOfLegendsPath = null;
            _newestFolder = null;
            _soundpack = null;
            _serverRegion = null;
            _soundarchiveName = null;
            _soundpackRegionFolder = null;
            _constructor = null;
            _regionInformation = null;
            logger.info("No fatal errors Occured");
            LogFilev2.finalisieren();
        } catch (Exception e) {

            // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Misc.ErrorMessage();
            this.stopFsbExt();
            logger.fatal("FATAL ERROR:");
            logger.error(e.getMessage());
            Misc.logStacktrace(e);

            FireEvent("ERROR", e);

        } finally {
            File VOBank2 = new File("Files/" + _soundarchiveName);
            {
                if (VOBank2.exists()) {

                    VOBank2.delete();
                }
            }
            File VOBANK = new File("Files/");
            for (File f : VOBANK.listFiles()) {
                if (f.toString().contains(".fsb")) {
                    f.delete();
                    System.out.println("- " + f.getName() + " found, deleting...");
                }
            }
            FireEvent("ErhoeheProgressbarWert", 100);
            LogFilev2.finalisieren();
        }

    }

    protected boolean CheckCompatibility(String Soundpack, String Region) {
        File StereoNonEnglishBasedSoundpack = new File("Soundpacks/" + Soundpack + "/Other/Stereo/");
        File EnglishSoundpack = new File("Soundpacks/" + Soundpack + "/English/");
        Boolean Compatibility;
        if (Region.equals("lol_game_client_en_gb") || Region.equals("lol_game_client_en_us")) {
            if (EnglishSoundpack.exists()) {
                Compatibility = true;
            } else {
                Compatibility = false;
            }
        } else if (StereoNonEnglishBasedSoundpack.exists() == true) {
            Compatibility = true;
        } else {
            Compatibility = false;
        }
        return Compatibility;
    }

    /**
     * \brief Erstellt ein Backup vom Soundarchiv des Lolclienten
     * <p/>
     * \param[in] Path          Pfad von League of Legends
     * \param[in] RecentFolder  Ordner, der bei jedem neuen Patch geändert wird. Beispiel(0.0.0.23)
     * \param[in] Region        Region die ihr benutzen wollt Beispiel(lol_game_client_en_gb)
     * \param[in] VOBankname    Name des Soundarchives
     */
    protected void CreateVOBankBackup() throws IOException {
        File Backup = new File(this._vobankPath + "- Backup.fsb");
        {
            if (Backup.exists() == false) {
                File inF = new File(this._vobankPath + this._soundarchiveName);
                File outF = new File(this._vobankPath + "- Backup.fsb");
                Misc.copyFile(inF, outF);
            }
        }
    }

    void FireEvent(String ID, Object neuerWert) {
        this.PropertySupport.firePropertyChange(ID, 0, neuerWert);
    }

    public void addChangeListener(PropertyChangeListener listener) {
        this.PropertySupport.addPropertyChangeListener(listener);
    }

    /**
     * \brief Löscht das Backup
     * <p/>
     * \param[in] Path          Pfad von League of Legends
     * \param[in] RecentFolder  Ordner, der bei jedem neuen Patch geändert wird. Beispiel(0.0.0.23)
     * \param[in] Region        Region die ihr benutzen wollt Beispiel(lol_game_client_en_gb)
     * \param[in] VOBankname    Name des Soundarchives
     *
     * @throws IOException
     */
    public void DeleteVOBankBackup() throws IOException {
        System.out.print("- Uninstall Soundmod...");
        File inF = new File(this._vobankPath + _soundarchiveName);
        File outF = new File(this._vobankPath + "- Backup.fsb");
        try {
            FireEvent("ErhoeheProgressbarWert", 0);
            Misc.copyFile(outF, inF);
            FireEvent("ErhoeheProgressbarWert", 50);
            outF.delete();
            FireEvent("ErhoeheProgressbarWert", 100);
            FireEvent("LogFile", "No errors occured.");
            System.out.println("done!");
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }


    }

    private void CheckInput(boolean unpatch) throws PatchException {
        File file = new File("Soundpacks/" + _soundpack);
        if (!unpatch) {
            if (!file.exists()) {
                logger.fatal("ERROR: Soundpack not found! (" + _soundpack + ")");
                throw new PatchException("- ERROR: Soundpack not found! (" + _soundpack + ")");
            }
        }
        File LoLPath = new File(_leagueOfLegendsPath + "/lol.launcher.exe");
        if (!LoLPath.exists()) {
            logger.fatal("ERROR: LoLPath not found (" + _leagueOfLegendsPath + ")");
            throw new PatchException("- ERROR: LoLPath not found (" + _leagueOfLegendsPath + ")");
        }
        if (_swapmode < 0 || _swapmode > 3) {
            _swapmode = 0;
        }
        if (_newestFolder == null) {

            logger.fatal("ERROR: Region not installed! (" + _serverRegion + ")");
            throw new PatchException("- ERROR: Region not installed! (" + _serverRegion + ")");
        }
    }

    protected void CopyVOBankIntoLoL() throws IOException {
        File outF = new File(this._vobankPath + this._soundarchiveName);
        File inF = new File("Files/" + this._soundarchiveName);
        Misc.copyFile(inF, outF);
    }

    public void stopFsbExt() {
        System.out.print("\n- Kill fsbext....");
        if (this.fsbext != null) {
            this.fsbext.destroy();
            System.out.println("done!");
        } else {
            System.out.println("Not active.");
        }
    }
}
