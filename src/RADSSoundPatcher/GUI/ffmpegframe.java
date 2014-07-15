package RADSSoundPatcher.GUI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * \brief Frame für die FFMpeg konvertierung
 */
public class ffmpegframe extends JDialog {

    public JProgressBar progressBar;
    public JTextArea txt;
    public JScrollPane scrollPane_1;
    public Point l;

    /**
     * Create the dialog.
     */
    public ffmpegframe() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setResizable(false);
        setBackground(new Color(35, 35, 35));
        setBounds(0, 0, 442, 236);

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
            }
        }

        setTitle("FFmpeg convert progress");

        getContentPane().setLayout(null);
        {
            progressBar = new JProgressBar();
            progressBar.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent arg0) {


                }
            });
            progressBar.setBounds(0, 0, 434, 37);
            getContentPane().add(progressBar);
        }
        txt = new JTextArea();
        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(0, 39, 434, 169);
        getContentPane().add(scrollPane_1);

        txt.setFont(new Font("Miriam", Font.PLAIN, 13));
        txt.setForeground(new Color(192, 192, 192));
        scrollPane_1.setViewportView(txt);
        txt.setBackground(new Color(35, 35, 35));
    }

    public void Setcaretposition() {
        txt.setCaretPosition(txt.getText().length());

    }

    public void Location() {

    }
}
