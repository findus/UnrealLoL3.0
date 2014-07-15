package RADSSoundPatcher.GUI;

import javax.swing.*;
import java.awt.*;

public class Console extends JDialog {

    boolean visible = false;
    float i = 0.0F;
    Gui guireference = null;
    public TextArea area = null;

    /**
     * Create the dialog.
     */
    public Console(Gui gui) {
        setUndecorated(true);

        guireference = gui;
       // setBounds(guireference.getX(),guireference.getY() + 316, 411, 452);
        getContentPane().setLayout(new BorderLayout());
        {
            area = new TextArea(10, 100);
            area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
            area.setBackground(Gui.myColor);
            area.setEditable(false);
            area.setForeground(Color.GRAY);

            this.getContentPane().add(area);
            this.pack();
            setVisible(false);

        }
    }


    public void Fader(boolean lol) {

       this.setVisible(!visible);
        visible = !visible;
    }


}
