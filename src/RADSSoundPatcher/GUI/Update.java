package RADSSoundPatcher.GUI;

/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 20.03.13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */


import RADSSoundPatcher.Misc.Misc;
import RADSSoundPatcher.Updater.Updater;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Update extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    static JLabel label_6;
    static JButton button;
    static JButton button_1;
    static JLabel label_2;
    static JLabel label_1;
    static JLabel label_3;
    static JLabel lblServer;
    static JLabel lblClient;
    static Logger logger = Logger.getRootLogger();
    static JProgressBar progressBar;
    private Thread downloader = null;
    boolean stopdownloading = false;
    private Updater updater = new Updater();


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            Update dialog = new Update();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            dialog.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public Update() {

        setModal(true);
        setTitle("Updater");

        setResizable(false);
        //Point l = Gui.dialog.getLocation();
        //setBounds(l.x, l.y, 441, 207);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setForeground(Color.LIGHT_GRAY);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Gui.myColor);

        JLabel label = new JLabel("New label");
        label.setForeground(Color.LIGHT_GRAY);
        //URL bildURL = getClass().getResource("p.png");

        label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("RADSSoundPatcher/Pictures/p.png"))));
        label.setBounds(0, 0, 438, 76);
        contentPanel.add(label);

        label_1 = new JLabel("Unreal.jar........");
        label_1.setForeground(Color.LIGHT_GRAY);
        label_1.setBounds(10, 120, 87, 14);
        contentPanel.add(label_1);

        label_2 = new JLabel("???");
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
        label_2.setForeground(Color.LIGHT_GRAY);
        label_2.setBounds(279, 117, 46, 20);
        contentPanel.add(label_2);

        label_3 = new JLabel("???");
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
        label_3.setForeground(Color.LIGHT_GRAY);
        label_3.setBounds(355, 117, 46, 20);
        contentPanel.add(label_3);

        lblServer = new JLabel("Server");
        lblServer.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblServer.setForeground(Color.LIGHT_GRAY);
        lblServer.setBounds(355, 90, 46, 20);
        contentPanel.add(lblServer);

        lblClient = new JLabel("Client");
        lblClient.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblClient.setForeground(Color.LIGHT_GRAY);
        lblClient.setBounds(279, 90, 46, 20);
        contentPanel.add(lblClient);

        button = new JButton("Download");
        button.setFont(new Font("Tahoma", Font.PLAIN, 10));
        button.setBackground(Gui.myColor);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ;
            }

        });
        button.setEnabled(false);
        button.setBounds(339, 148, 89, 23);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadUpdate();
            }
        });
        contentPanel.add(button);
        button_1 = new JButton("Changelog");
        button_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
        button_1.setForeground(Color.LIGHT_GRAY);
        button_1.setBackground(Gui.myColor);
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Changelog().setVisible(true);
            }
        });
        button_1.setBounds(8, 145, 89, 23);
        contentPanel.add(button_1);

        progressBar = new JProgressBar();
        progressBar.setBounds(115, 120, 146, 14);
        progressBar.setValue(0);
        contentPanel.add(progressBar);


        label_6 = new JLabel("");
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        label_6.setForeground(Color.GREEN);
        label_6.setBackground(Color.GREEN);
        label_6.setBounds(115, 120, 146, 14);
        contentPanel.add(label_6);
        final JCheckBox chckbxForceUpdate = new JCheckBox("Force Update");
        chckbxForceUpdate.setFont(new Font("Tahoma", Font.PLAIN, 10));
        chckbxForceUpdate.setForeground(Color.LIGHT_GRAY);
        chckbxForceUpdate.setBackground(Gui.myColor);
        chckbxForceUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (chckbxForceUpdate.isSelected() == true) {
                    button.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Do it at your own risk!");
                } else {
                    button.setEnabled(false);
                }
            }
        });
        chckbxForceUpdate.setVerticalAlignment(SwingConstants.TOP);
        chckbxForceUpdate.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxForceUpdate.setBounds(228, 148, 97, 23);
        contentPanel.add(chckbxForceUpdate);


        try {
            checkForUpdate();
        } catch (IOException e) {
            logger.error("Could not retrive version Information");
            JOptionPane.showMessageDialog(null, "Could not retrive version Information");
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);    //To change body of overridden methods use File | Settings | File Templates.
                updater.stopdownloading(true);

            }
        });
    }

    public void downloadUpdate() {
        button.setEnabled(false);
        downloader = new Thread(new Runnable() {
            @Override
            public void run() {
                double size = updater.getFileSize("http://nazatar.bplaced.net/Mod/Unreal.jar/Unreal.jar");
                Update.label_6.setText("" + size);
                int test = updater.DownloadUrlPool("Unreal.jar_Downloaded", "Unreal.jar", progressBar);
                switch (test) {
                    case -1:
                        Update.label_6.setText("ERROR");
                        JOptionPane.showMessageDialog(null, "Error while downloading.");

                        break;
                    case 0:
                        Update.label_6.setText("ABORTED");
                        JOptionPane.showMessageDialog(null, "Download aborted");
                        break;
                    case 1:
                        Update.label_6.setText("Finished");
                        try {
                            Misc.copyFile(new java.io.File("Unreal.jar_DOWNLOADED"), new java.io.File("Unreal.jar"));
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        ;
                        JOptionPane.showMessageDialog(null, "Download succesfull, please restart the Mod");
                        System.exit(-1);
                        break;
                }
            }
        });
        downloader.start();
    }

    public void checkForUpdate() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int retval = updater.DownloadUrlPool("Files/Version/Serverversion", "Serverversion", null);
                    if (retval == 1) {
                        BufferedReader Server = new BufferedReader(new FileReader("Files/Version/Serverversion"));
                        String zeile1 = Server.readLine();
                        label_3.setText(zeile1.replace(" Completeupdate", ""));
                        label_2.setText(Gui.Version);
                        String ClientVersion = Gui.Version.replace(".", "");
                        int INTClientVersion = Integer.parseInt(ClientVersion);
                        String ServerVersion = zeile1.replace(".", "").replace(" Completeupdate", "");
                        System.out.println("- Client-Version is: " + Gui.Version);
                        System.out.println("- Server-Version is: " + zeile1.replace(" Completeupdate", ""));
                        int INTServerVersion = Integer.parseInt(ServerVersion);
                        if (INTServerVersion > INTClientVersion) {
                            Update.button.setEnabled(true);
                        } else {
                            Update.button.setEnabled(false);
                        }
                        if (zeile1.contains(" Completeupdate") == true) {
                            System.out.println("Complete Update needed!");
                            Update.button.setEnabled(false);
                            Update.message();
                        }
                    } else {

                    }

                } catch (IOException IOException) {
                    button.setEnabled(false);
                }
            }
        }).start();

    }


    final static void message() {
        JOptionPane.showMessageDialog(null, "Complete Update needed, please visit the LoL Forum to download the newest Version");
    }
}

