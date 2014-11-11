package RADSSoundPatcher.GUI;

import RADSSoundPatcher.Find.Tools;
import RADSSoundPatcher.Misc.Misc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Options extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Confirm");
    private JPanel contentPane;
    public static JComboBox<String> comboBox_1 = new JComboBox<String>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    Options frame = new Options();
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the frame.
     */
    public Options() {
        setModal(true);
        //URL bildURL2 = getClass().getResource("Monsoon.jpg");

        //setIconImage(Toolkit.getDefaultToolkit().getImage(bildURL2));
        setTitle("Options");
        setResizable(false);


        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
       // Point l = Gui.dialog.getLocation();
        //setBounds(l.x, l.y, 426, 217);
        contentPane = new JPanel();
        contentPane.setBackground(Gui.myColor);
        contentPane.setForeground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnOpenVobankfolder = new JButton("Open VOBank folder");
        btnOpenVobankfolder.setFont(new Font("SansSerif", Font.PLAIN, 10));
        btnOpenVobankfolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnOpenVobankfolder.setForeground(Color.LIGHT_GRAY);
        btnOpenVobankfolder.setBackground(Gui.myColor);
        btnOpenVobankfolder.setBounds(240, 152, 166, 25);
        contentPane.add(btnOpenVobankfolder);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(0, 0, 639, 68);
        lblNewLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("RADSSoundPatcher/Pictures/Header/Jarvan.jpg"))));
        contentPane.add(lblNewLabel);

        JButton btnOpenLolfolder = new JButton("Open LoLFolder");
        btnOpenLolfolder.setFont(new Font("SansSerif", Font.PLAIN, 10));
        btnOpenLolfolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

            }
        });
        btnOpenLolfolder.setForeground(Color.LIGHT_GRAY);
        btnOpenLolfolder.setBackground(Gui.myColor);

        btnOpenLolfolder.setBounds(240, 125, 166, 25);
        contentPane.add(btnOpenLolfolder);

        JButton btnOpenFilearchive = new JButton("Open managedFiles");
        btnOpenFilearchive.setFont(new Font("SansSerif", Font.PLAIN, 10));
        btnOpenFilearchive.setForeground(Color.LIGHT_GRAY);
        btnOpenFilearchive.setBackground(Gui.myColor);

        btnOpenFilearchive.setBounds(240, 99, 166, 25);
        contentPane.add(btnOpenFilearchive);

        btnOpenFilearchive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JLabel dtrpnNotAvaiable = new JLabel();
        dtrpnNotAvaiable.setText("Available for all Regions!");
        dtrpnNotAvaiable.setForeground(Color.LIGHT_GRAY);
        dtrpnNotAvaiable.setToolTipText("");
        dtrpnNotAvaiable.setFont(new Font("Arial", Font.PLAIN, 10));
        dtrpnNotAvaiable.setBackground(Gui.myColor);
        dtrpnNotAvaiable.setBounds(12, 149, 199, 23);
        contentPane.add(dtrpnNotAvaiable);

        JLabel dtrpnQuickAccess = new JLabel();
        dtrpnQuickAccess.setForeground(Color.LIGHT_GRAY);
        dtrpnQuickAccess.setText("Quick access:");
        dtrpnQuickAccess.setFont(new Font("Arial", Font.PLAIN, 10));
        dtrpnQuickAccess.setBackground(Color.GRAY);
        dtrpnQuickAccess.setBounds(240, 69, 138, 23);
        contentPane.add(dtrpnQuickAccess);

        JLabel dtrpnSwapAnnouncerFiles = new JLabel();
        dtrpnSwapAnnouncerFiles.setForeground(Color.LIGHT_GRAY);
        dtrpnSwapAnnouncerFiles.setText("Swap Announcer Voice:");
        dtrpnSwapAnnouncerFiles.setFont(new Font("Arial", Font.PLAIN, 10));
        dtrpnSwapAnnouncerFiles.setBackground(Color.GRAY);
        dtrpnSwapAnnouncerFiles.setBounds(10, 69, 199, 23);
        contentPane.add(dtrpnSwapAnnouncerFiles);
        comboBox_1.setFont(new Font("SansSerif", Font.PLAIN, 10));
        comboBox_1.setForeground(Color.LIGHT_GRAY);
        comboBox_1.setBackground(Gui.myColor);
        comboBox_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.setEnabled(true);
            }
        });
        comboBox_1.setBounds(10, 99, 188, 25);
        contentPane.add(comboBox_1);
        button.setFont(new Font("SansSerif", Font.PLAIN, 10));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("- Announcer Mode: " + comboBox_1.getSelectedItem());
            }
        });
        button.setForeground(Color.LIGHT_GRAY);
        button.setBackground(Gui.myColor);
        button.setVerticalAlignment(SwingConstants.BOTTOM);
        button.setBounds(14, 127, 103, 25);
        contentPane.add(button);
    }

    public static void Soundpacks() {
        comboBox_1.removeAllItems();
        comboBox_1.addItem("Default");
        comboBox_1.addItem("Swap");
        comboBox_1.addItem("Allied Announcer Only");
        comboBox_1.addItem("Enemy Announcer Only");
    }
}


	

	


