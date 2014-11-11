package RADSSoundPatcher.GUI;

import RADSSoundPatcher.Updater.Updater;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 10.04.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class Changelog extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    static JTextArea textArea;
    static JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Changelog dialog = new Changelog();
                    dialog.setVisible(true);
                    //tesat dialog1 = new tesat();
                    //dialog1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    //dialog1.setVisible(true);
                    Load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Changelog() {
        setModalityType(ModalityType.TOOLKIT_MODAL);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                try {
                    Load();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        setResizable(false);
        setTitle("Changelog");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //oint l = Gui.dialog.getLocation();
        //setBounds(l.x, l.y, 783, 563);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 777, 535);
        contentPane.add(scrollPane);
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(Gui.myColor);
        textArea.setForeground(Color.LIGHT_GRAY);
        scrollPane.setViewportView(textArea);
        scrollPane.setBackground(Gui.myColor);
    }

    static void Load() throws IOException {
        new Updater().Download("http://nazatar.bplaced.net/Mod/Unreal.jar/Changelog.txt", "Changelog.txt", null);
        @SuppressWarnings("resource")
        BufferedReader Changelog = new BufferedReader(new FileReader("Changelog.txt"));
        String zeile = null;
        while ((zeile = Changelog.readLine()) != null) {
            textArea.append(zeile + "\n");
        }
        textArea.setCaretPosition(0);
    }
}
