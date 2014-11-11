import RADSSoundPatcher.Misc.Misc;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 11.11.2014.
 */
public class Creator {

    private List<Sound> list = new ArrayList<Sound>();



    public Creator() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("D:\\Dropbox\\Kills.txt"));
        String lol = null;
        int i = 0;
        while((lol = reader.readLine()) != null)
        {

           System.out.println(++i);
            String name = lol.substring(1,lol.indexOf("]"));
            String[] rest = lol.substring(lol.indexOf("]")+1,lol.length()).split(",");
            Sound sound = new Sound(name);
            for(String rs : rest)
                sound.stringList.add(rs);

            list.add(sound);

        }


       for(Sound d:list)
       {

           JFileChooser chooser = new JFileChooser("E:\\Dateien\\Desktop\\English\\cleaned");
           chooser.setPreferredSize(new Dimension(1600,1000));
           chooser.setDialogTitle(d.name);
           int rueckgabeWert = chooser.showOpenDialog(null);
           if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {

           }

          for(String s : d.stringList)
          {
              if(chooser.getSelectedFile() != null)
                Misc.copyFile(chooser.getSelectedFile(), new File("E:\\Dateien\\Desktop\\wwise soundpack ut\\" + s + ".mp3"));
          }

       }


    }

    class Sound{
        List<String> stringList = new ArrayList<String>();
        String name;

        public Sound(String name)
        {
            this.name = name;
        }
    }

    public static void main(String[] args) throws IOException {
        new Creator();
    }
}
