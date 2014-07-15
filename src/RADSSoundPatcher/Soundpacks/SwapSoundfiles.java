package RADSSoundPatcher.Soundpacks;

import RADSSoundPatcher.Misc.Misc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 22.03.13
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */
public class SwapSoundfiles {

    public static boolean fileismissing = false;
    static ArrayList<String> AlliedKill = new ArrayList<String>();
    static ArrayList<String> EnemyKill = new ArrayList<String>();
    static ArrayList<String> AlliedDoubleKill = new ArrayList<String>();
    static ArrayList<String> EnemyDoubleKill = new ArrayList<String>();
    static ArrayList<String> AlliedTripleKill = new ArrayList<String>();
    static ArrayList<String> EnemyTripleKill = new ArrayList<String>();
    static ArrayList<String> AlliedQuadraKill = new ArrayList<String>();
    static ArrayList<String> EnemyQuadraKill = new ArrayList<String>();
    static ArrayList<String> AlliedPentaKill = new ArrayList<String>();
    static ArrayList<String> EnemyPentaKill = new ArrayList<String>();
    static ArrayList<String> AlliedLegendaryKill = new ArrayList<String>();
    static ArrayList<String> EnemyLegendaryKill = new ArrayList<String>();
    static ArrayList<String> AlliedKillingspree = new ArrayList<String>();
    static ArrayList<String> EnemyKillingspree = new ArrayList<String>();
    static ArrayList<String> AlliedKillingspree2 = new ArrayList<String>();
    static ArrayList<String> EnemyKillingspree2 = new ArrayList<String>();
    static ArrayList<String> AlliedKillingspree3 = new ArrayList<String>();
    static ArrayList<String> EnemyKillingspree3 = new ArrayList<String>();
    static ArrayList<String> AlliedKillingspree4 = new ArrayList<String>();
    static ArrayList<String> EnemyKillingspree4 = new ArrayList<String>();
    static ArrayList<String> AlliedKillingspree5 = new ArrayList<String>();
    static ArrayList<String> EnemyKillingspree5 = new ArrayList<String>();
    static ArrayList<String> AlliedKillingspree6 = new ArrayList<String>();
    static ArrayList<String> EnemyKillingspree6 = new ArrayList<String>();

    public static void Swap(String Soundpack, int SwapWhat, String Region, String Constructor) throws IOException {
        AlliedKill.clear();
        EnemyKill.clear();
        AlliedDoubleKill.clear();
        EnemyDoubleKill.clear();
        AlliedTripleKill.clear();
        EnemyTripleKill.clear();
        AlliedQuadraKill.clear();
        EnemyQuadraKill.clear();
        AlliedPentaKill.clear();
        EnemyPentaKill.clear();
        AlliedLegendaryKill.clear();
        EnemyLegendaryKill.clear();
        AlliedKillingspree.clear();
        AlliedKillingspree2.clear();
        AlliedKillingspree3.clear();
        AlliedKillingspree4.clear();
        AlliedKillingspree5.clear();
        AlliedKillingspree6.clear();
        EnemyKillingspree.clear();
        EnemyKillingspree2.clear();
        EnemyKillingspree3.clear();
        EnemyKillingspree4.clear();
        EnemyKillingspree5.clear();
        EnemyKillingspree6.clear();

        if (!Region.trim().equals("lol_game_client_en_gb") && !Region.trim().equals("lol_game_client_en_us")) {
            DeclareOtherSoundfiles(Soundpack, SwapWhat, Region, Constructor);
        } else {
            DeclareEnglishSoundfiles(Soundpack, SwapWhat, Region, Constructor);
        }


    }

    ;

    public static void DeclareEnglishSoundfiles(String Soundpack, int SwapWhat, String Region, String Constructor) throws IOException {
        File Folder = new File("Soundpacks/" + Soundpack + "/English/");
        File[] ListedFiles = Folder.listFiles();
        for (File Listed : ListedFiles) {
            if (Listed.getName().toString().contains("DoubleKillY")) {
                AlliedDoubleKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("DoubleKillE")) {
                EnemyDoubleKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("TripleKillY")) {
                AlliedTripleKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("TripleKillE")) {
                EnemyTripleKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("QuadraKillY")) {
                AlliedQuadraKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("QuadraKillE")) {
                EnemyQuadraKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("PentaKillYo")) {
                AlliedPentaKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("PentaKillEn")) {
                EnemyPentaKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("UnrealKillY")) {
                AlliedLegendaryKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("UnrealKillE")) {
                EnemyLegendaryKill.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet1Yo")) {
                AlliedKillingspree.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet1En")) {
                EnemyKillingspree.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet2Yo")) {
                AlliedKillingspree2.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet2En")) {
                EnemyKillingspree2.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet3Yo")) {
                AlliedKillingspree3.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet3En")) {
                EnemyKillingspree3.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet4Yo")) {
                AlliedKillingspree4.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet4En")) {
                EnemyKillingspree4.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet5Yo")) {
                AlliedKillingspree5.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet5En")) {
                EnemyKillingspree5.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet6Yo")) {
                AlliedKillingspree6.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet6En")) {
                EnemyKillingspree6.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("KillingSpreeSet6En")) {
                EnemyKillingspree6.add(Listed.getName());
            }
            if (Listed.getName().toString().contains("female1_OnChampionKillHeroHer.mp3") || Listed.getName().toString().contains("female1_OnChampionKillHeroHer_1.mp3") || Listed.getName().toString().contains("female1_OnChampionKillHeroYou")) {
                EnemyKill.add(Listed.getName());
            }
            for (int index2 = 2; index2 < 7; index2++) {
                if (Listed.getName().toString().contains("female1_OnChampionKillHeroHer_" + index2)) {
                    AlliedKill.add(Listed.getName());
                }
            }

        }
        if (SwapWhat == 3) {
            SwapSoundfiles(AlliedLegendaryKill, EnemyLegendaryKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyLegendaryKill, AlliedLegendaryKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedPentaKill, EnemyPentaKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyPentaKill, AlliedPentaKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedQuadraKill, EnemyQuadraKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyQuadraKill, AlliedQuadraKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyTripleKill, AlliedTripleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedTripleKill, EnemyTripleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedDoubleKill, EnemyDoubleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyDoubleKill, AlliedDoubleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree, EnemyKillingspree, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree, AlliedKillingspree, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree2, EnemyKillingspree2, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree2, AlliedKillingspree2, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree3, EnemyKillingspree3, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree3, AlliedKillingspree3, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree4, EnemyKillingspree4, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree4, AlliedKillingspree4, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree5, EnemyKillingspree5, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree5, AlliedKillingspree5, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree6, EnemyKillingspree6, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree6, AlliedKillingspree6, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKill, AlliedKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKill, EnemyKill, Soundpack, Region, Constructor);
        }
        if (SwapWhat == 1) {
            SwapSoundfiles(AlliedLegendaryKill, EnemyLegendaryKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedPentaKill, EnemyPentaKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedQuadraKill, EnemyQuadraKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedTripleKill, EnemyTripleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedDoubleKill, EnemyDoubleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree, EnemyKillingspree, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree2, EnemyKillingspree2, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree3, EnemyKillingspree3, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree4, EnemyKillingspree4, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree5, EnemyKillingspree5, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree6, EnemyKillingspree6, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKill, EnemyKill, Soundpack, Region, Constructor);
        }
        if (SwapWhat == 2) {
            SwapSoundfiles(EnemyLegendaryKill, AlliedLegendaryKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyPentaKill, AlliedPentaKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyQuadraKill, AlliedQuadraKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyDoubleKill, AlliedDoubleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree, AlliedKillingspree, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree2, AlliedKillingspree2, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree3, AlliedKillingspree3, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree4, AlliedKillingspree4, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree5, AlliedKillingspree5, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree6, AlliedKillingspree6, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKill, AlliedKill, Soundpack, Region, Constructor);
        }

    }

    public static void DeclareOtherSoundfiles(String Soundpack, int SwapWhat, String Region, String Constructor) throws IOException {


        AlliedKill.add("female1_OnChampionKillH_2.mp3");
        AlliedKill.add("female1_OnChampionKillH_3.mp3");
        AlliedKill.add("female1_OnChampionKillH_4.mp3");
        AlliedKill.add("female1_OnChampionKillH_5.mp3");
        AlliedKill.add("female1_OnChampionKillH_6.mp3");
        AlliedKill.add("female1_OnChampionKillH_7.mp3");

        EnemyKill.add("female1_OnChampionKillH.mp3");
        EnemyKill.add("female1_OnChampionKillH_1.mp3");
        EnemyKill.add("female1_OnChampionKillH_8.mp3");
        EnemyKill.add("female1_OnChampionKillH_9.mp3");

        AlliedDoubleKill.add("female1_OnChampionDoubl_2.mp3");
        AlliedDoubleKill.add("female1_OnChampionDoubl_3.mp3");
        AlliedDoubleKill.add("female1_OnChampionDoubl_4.mp3");

        EnemyDoubleKill.add("female1_OnChampionDoubl.mp3");
        EnemyDoubleKill.add("female1_OnChampionDoubl_1.mp3");

        AlliedTripleKill.add("female1_OnChampionTripl_2.mp3");
        AlliedTripleKill.add("female1_OnChampionTripl_3.mp3");

        EnemyTripleKill.add("female1_OnChampionTripl.mp3");
        EnemyTripleKill.add("female1_OnChampionTripl_1.mp3");

        AlliedQuadraKill.add("female1_OnChampionQuadr_1.mp3");
        AlliedQuadraKill.add("female1_OnChampionQuadr_2.mp3");

        EnemyQuadraKill.add("female1_OnChampionQuadr.mp3");

        AlliedPentaKill.add("female1_OnChampionPenta_2.mp3");
        AlliedPentaKill.add("female1_OnChampionPenta_3.mp3");

        EnemyPentaKill.add("female1_OnChampionPenta.mp3");
        EnemyPentaKill.add("female1_OnChampionPenta_1.mp3");

        AlliedLegendaryKill.add("female1_OnChampionUnrea_1.mp3");
        AlliedLegendaryKill.add("female1_OnChampionUnrea_2.mp3");

        EnemyLegendaryKill.add("female1_OnChampionUnrea.mp3");

        AlliedKillingspree.add("female1_OnKillingSpreeS_2.mp3");
        AlliedKillingspree.add("female1_OnKillingSpreeS_3.mp3");
        AlliedKillingspree.add("female1_OnKillingSpreeS_4.mp3");

        EnemyKillingspree.add("female1_OnKillingSpreeS.mp3");
        EnemyKillingspree.add("female1_OnKillingSpreeS_1.mp3");

        AlliedKillingspree2.add("female1_OnKillingSpreeS_6.mp3");
        AlliedKillingspree2.add("female1_OnKillingSpreeS_7.mp3");
        AlliedKillingspree2.add("female1_OnKillingSpreeS_8.mp3");
        AlliedKillingspree2.add("female1_OnKillingSpreeS_9.mp3");

        EnemyKillingspree2.add("female1_OnKillingSpreeS_5.mp3");

        AlliedKillingspree3.add("female1_OnKillingSpreeS_12.mp3");
        AlliedKillingspree3.add("female1_OnKillingSpreeS_13.mp3");
        AlliedKillingspree3.add("female1_OnKillingSpreeS_14.mp3");

        EnemyKillingspree3.add("female1_OnKillingSpreeS_10.mp3");
        EnemyKillingspree3.add("female1_OnKillingSpreeS_11.mp3");

        AlliedKillingspree4.add("female1_OnKillingSpreeS_16.mp3");
        AlliedKillingspree4.add("female1_OnKillingSpreeS_17.mp3");

        EnemyKillingspree4.add("female1_OnKillingSpreeS_15.mp3");

        AlliedKillingspree5.add("female1_OnKillingSpreeS_20.mp3");
        AlliedKillingspree5.add("female1_OnKillingSpreeS_21.mp3");
        AlliedKillingspree5.add("female1_OnKillingSpreeS_22.mp3");
        AlliedKillingspree5.add("female1_OnKillingSpreeS_23.mp3");

        EnemyKillingspree5.add("female1_OnKillingSpreeS_18.mp3");
        EnemyKillingspree5.add("female1_OnKillingSpreeS_19.mp3");

        AlliedKillingspree6.add("female1_OnKillingSpreeS_26.mp3");
        AlliedKillingspree6.add("female1_OnKillingSpreeS_27.mp3");
        AlliedKillingspree6.add("female1_OnKillingSpreeS_28.mp3");
        AlliedKillingspree6.add("female1_OnKillingSpreeS_29.mp3");
        AlliedKillingspree6.add("female1_OnKillingSpreeS_30.mp3");

        EnemyKillingspree6.add("female1_OnKillingSpreeS_24.mp3");
        EnemyKillingspree6.add("female1_OnKillingSpreeS_25.mp3");

        if (SwapWhat == 3) {
            SwapSoundfiles(AlliedLegendaryKill, EnemyLegendaryKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyLegendaryKill, AlliedLegendaryKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedPentaKill, EnemyPentaKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyPentaKill, AlliedPentaKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedQuadraKill, EnemyQuadraKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyQuadraKill, AlliedQuadraKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyTripleKill, AlliedTripleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedTripleKill, EnemyTripleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedDoubleKill, EnemyDoubleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyDoubleKill, AlliedDoubleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree, EnemyKillingspree, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree, AlliedKillingspree, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree2, EnemyKillingspree2, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree2, AlliedKillingspree2, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree3, EnemyKillingspree3, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree3, AlliedKillingspree3, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree4, EnemyKillingspree4, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree4, AlliedKillingspree4, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree5, EnemyKillingspree5, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree5, AlliedKillingspree5, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree6, EnemyKillingspree6, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree6, AlliedKillingspree6, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKill, AlliedKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKill, EnemyKill, Soundpack, Region, Constructor);
        }
        if (SwapWhat == 1) {
            SwapSoundfiles(AlliedLegendaryKill, EnemyLegendaryKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedPentaKill, EnemyPentaKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedQuadraKill, EnemyQuadraKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedTripleKill, EnemyTripleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedDoubleKill, EnemyDoubleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree, EnemyKillingspree, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree2, EnemyKillingspree2, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree3, EnemyKillingspree3, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree4, EnemyKillingspree4, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree5, EnemyKillingspree5, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKillingspree6, EnemyKillingspree6, Soundpack, Region, Constructor);
            SwapSoundfiles(AlliedKill, EnemyKill, Soundpack, Region, Constructor);
        }
        if (SwapWhat == 2) {
            SwapSoundfiles(EnemyLegendaryKill, AlliedLegendaryKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyPentaKill, AlliedPentaKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyQuadraKill, AlliedQuadraKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyDoubleKill, AlliedDoubleKill, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree, AlliedKillingspree, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree2, AlliedKillingspree2, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree3, AlliedKillingspree3, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree4, AlliedKillingspree4, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree5, AlliedKillingspree5, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKillingspree6, AlliedKillingspree6, Soundpack, Region, Constructor);
            SwapSoundfiles(EnemyKill, AlliedKill, Soundpack, Region, Constructor);
        }

    }

    public static void SwapSoundfiles(ArrayList<String> Input, ArrayList<String> Output, String Soundpack, String Region, String Constructor) throws IOException {
        File File;
        if (!Input.isEmpty()) {
            if (!Region.trim().equals("lol_game_client_en_gb") && !Region.trim().equals("lol_game_client_en_us")) {
                File = new File("Soundpacks/" + Soundpack + "/Other/Stereo/" + Input.get(0));
            } else {
                File = new File("Soundpacks/" + Soundpack + "/English/" + Input.get(0));
            }

            for (int index = 0; index < Output.size(); index++) {
                File OutputFile = new File("Sounds/Convert/" + Constructor + Output.get(index));
                Misc.copyFile(File, OutputFile);
            }
        }
    }

}
