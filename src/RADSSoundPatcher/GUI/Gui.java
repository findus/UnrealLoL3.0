package RADSSoundPatcher.GUI;

import RADSSoundPatcher.Find.Tools;
import RADSSoundPatcher.Misc.Misc;
import RADSSoundPatcher.Patch.PatchException;
import RADSSoundPatcher.Patch.Patcher;
import RADSSoundPatcher.Patch.VOBankMethods;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Gui extends JFrame {

    public static boolean fader = true;
    public static String url;
    public static JComboBox<String> SoundpackcomBoBox;
    public static final String Version = "2.4.3.1";
    public static JLabel JLabel1 = new JLabel("JLabel1");
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public static JTextField txtEnterYourLeague;
    static JButton btnNewButton;
    static JButton ButtonPatch;
    static JRadioButton RadioButton2;
    static JRadioButton RadioButton1;
    static JButton btnUnpatch;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    static JButton btnOptions;
    static Gui dialog;
    public static JComboBox<String> comboBox;
    public static JComboBox<String> comboBox_1;
    public static JProgressBar progressBar;
    static JLabel lblNewLabel_1 = new JLabel("");
    private JMenuBar menuBar;
    public static final Color myColor = Color.decode("#121212");
    public static final Color fontColor = Color.decode("#969696");
    public static Console con = null;
    public static JButton btnNewButton_1;
    private static BufferedImage BUTTON_NORMAL = null;
    private static BufferedImage BUTTON_HOVER = null;
    private static BufferedImage BUTTON_PRESSED = null;
    private static JMenu mnAbout = null;
    private static JMenu mnTutorials = null;
    private static JMenu Links = null;
    private static JLabel lblNewLabel;
    private static JLabel label_3;
    private static JMenuItem oeffnen;
    private static JMenuItem mntmUsThread;
    private static JMenuItem mntmGerThread;
    private static JSeparator separator_2;
    private static JMenuItem mntmGetMoreSoundpacks;
    private static JMenuItem mntmHowToUse;
    private static JMenuItem mntmHowToInstall;
    private static JMenuItem mntmHowToCreate;
    private static JMenuItem mntmInfo;
    private static JMenuItem mntmCheckForUpdates;
    private static JMenuItem mntmMenuitem;
    private static JMenuItem Play;
    private static JLabel lblSelectSoundpack;
    private static JLabel lblSelectYourRegion;
    private static JSeparator separator_1;
    private static JSeparator separator;
    private static JLabel lblPatchUnpatch;
    private static JButton btnInfo;
    private static PropertyChangeListener Listener = null;
    private WindowAdapter adapter = null;
    private Patcher job;
    private static Logger logger = Logger.getRootLogger();

    /**
     * Launch the application.
     *
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws IOException {

        BUTTON_NORMAL = ImageIO.read(Gui.class.getResourceAsStream("/RADSSoundPatcher/Pictures/Theme/ButtonNormal.png"));
        BUTTON_HOVER = ImageIO.read(Gui.class.getResourceAsStream("/RADSSoundPatcher/Pictures/Theme/ButtonMouseover.png"));
        BUTTON_PRESSED = ImageIO.read(Gui.class.getResourceAsStream("/RADSSoundPatcher/Pictures/Theme/ButtonPressed.png"));


        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    //LookAndFeel();
                    //LookAndFeel(myColor);
                    RedirectOutputStream();

                    con = new Console(dialog);
                    dialog = new Gui();
                    dialog.initializeComponents();
                    dialog.initializeGUI();
                    dialog.searchLoL();
                    dialog.UpdateLookandFeel();
                    dialog.setVisible(true);
                    dialog.updatewindow();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    public Gui() throws URISyntaxException, SAXException, IOException, ParserConfigurationException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        adapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);    //To change body of overridden methods use File | Settings | File Templates.
                if (job != null) {
                    job.stopFsbExt();
                }
            }
        };
        this.addWindowListener(adapter);
    }

    public void initializeComponents() {
        comboBox = new JComboBox<String>();
        comboBox_1 = new JComboBox<String>();
        progressBar = new JProgressBar();
        contentPane = new JPanel();
        lblNewLabel = new JLabel("");
        txtEnterYourLeague = new JTextField();
        btnNewButton = new JButton("Open");
        ButtonPatch = new JButton("Patch");
        RadioButton2 = new JRadioButton("US");
        btnUnpatch = new JButton("Unpatch");
        btnOptions = new JButton("Customize");
        label_3 = new JLabel("New label");
        menuBar = new JMenuBar();
        oeffnen = new JMenuItem("EU thread");
        Links = new JMenu("Links");
        mntmUsThread = new JMenuItem("US thread");
        mntmGerThread = new JMenuItem("Ger thread");
        separator_2 = new JSeparator();
        mntmGetMoreSoundpacks = new JMenuItem("Get more Soundpacks");
        mntmHowToUse = new JMenuItem("How to use");
        mntmHowToInstall = new JMenuItem("How to install custom Soundpacks");
        mntmHowToCreate = new JMenuItem("How to create an own Soundpacks");
        mnAbout = new JMenu("About");
        mntmInfo = new JMenuItem("Info");
        mntmCheckForUpdates = new JMenuItem("Check for Updates");
        mntmMenuitem = new JMenuItem("Logfiles");
        Play = new JMenuItem("Play League of Legends");
        SoundpackcomBoBox = new JComboBox<String>();
        lblSelectSoundpack = new JLabel();
        lblSelectYourRegion = new JLabel();
        separator = new JSeparator();
        separator_1 = new JSeparator();
        lblPatchUnpatch = new JLabel();
        btnInfo = new JButton("Info");
        RadioButton1 = new JRadioButton("EU");
        Listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("ErhoeheProgressbarWert")) {
                    Object obj = evt.getNewValue();
                    int value = ((Integer) (obj)).intValue();
                    progressBar.setValue(value);
                } else if (evt.getPropertyName().equals("ErstelleMessagebox")) {
                    ShowMessagebox(evt.getNewValue().toString());
                } else if (evt.getPropertyName().equals("ERROR")) {
                    new RADSSoundPatcher.GUI.ErrorScreen((Exception) evt.getNewValue());
                }
            }
        };
    }

    public void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("RADSSoundPatcher/Pictures/Monsoon.jpg")));

        System.out.println("--------------------------------------------------------------");
        System.out.println("|  RADSSoundPatcher  for League of Legends                   |");
        System.out.println("|  V " + Version + "                                                 |");
        System.out.println("|  Created with IntelliJ Idea                                |");
        System.out.println("|  16.12.2013                                    by Fozruk   |");
        System.out.println("--------------------------------------------------------------");
        System.out.println("- Loading GUI....");
        RandomHeader();
        Options.Soundpacks();

        comboBox.setFont(new Font("Dialog", Font.PLAIN, 11));
        comboBox.setBorder(BorderFactory.createLineBorder(Color.gray, 0));
        comboBox.getEditor().getEditorComponent().setForeground(Color.GREEN);
        //comboBox.setForeground(Color.LIGHT_GRAY);
        comboBox.setBackground(myColor);

        comboBox_1.setFont(new Font("Dialog", Font.PLAIN, 11));
        //comboBox_1.setForeground(Color.LIGHT_GRAY);
        comboBox_1.setBackground(myColor);
        comboBox_1.addItem("English_US");
        comboBox_1.addItem("Portugues");
        comboBox_1.addItem("LATNLATS");
        comboBox_1.addItem("Oceania");

        progressBar.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
            }
        });
        progressBar.setValue(100);
        progressBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

        progressBar.setBackground(myColor);
        setTitle("Unreal Tournament Announcer Mod for League of Legends V " + Version);
        setResizable(false);
        setBounds(100, 100, 569, 306);


        contentPane.setBackground(myColor);
        //contentPane.setForeground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        lblNewLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("RADSSoundPatcher/Pictures/Theme/Fader.png"))));
        lblNewLabel.setBounds(0, 92, 568, 16);
        contentPane.add(lblNewLabel);


        //lblNewLabel_1.setIcon(new ImageIcon(bildURL21));
        lblNewLabel_1.setBounds(7, 180, 0, 0);
        contentPane.add(lblNewLabel_1);
        comboBox.setEnabled(false);
        comboBox.setToolTipText("");
        comboBox.setBounds(64, 194, 97, 25);
        comboBox.setForeground(myColor);
        comboBox_1.setForeground(myColor);
        contentPane.add(comboBox);
        comboBox_1.setEnabled(false);
        comboBox_1.setBounds(64, 220, 97, 25);
        comboBox.addItem("English");
        comboBox.addItem("German");
        comboBox.addItem("Greek");
        comboBox.addItem("Polski");
        comboBox.addItem("Espagnol");
        comboBox.addItem("French");
        comboBox.addItem("Romanian");
        comboBox.addItem("Russia");
        comboBox.addItem("Turkey");
        comboBox.addItem("Italian");
        contentPane.add(comboBox_1);


        JLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                RandomHeader();
                UpdateLookandFeel();
            }
        });


        JLabel1.setBounds(0, 18, 565, 90);
        contentPane.add(JLabel1);

        txtEnterYourLeague.setFont(new Font("Dialog", Font.PLAIN, 11));
        txtEnterYourLeague.setBackground(myColor);
        //txtEnterYourLeague.setForeground(Color.LIGHT_GRAY);
        txtEnterYourLeague.setText("Enter your League of Legends Path here...");
        txtEnterYourLeague.setEditable(false);
        txtEnterYourLeague.setBounds(12, 112, 410, 24);
        contentPane.add(txtEnterYourLeague);
        txtEnterYourLeague.setColumns(10);
        //txtEnterYourLeague.setForeground(Color.LIGHT_GRAY);

        btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 11));
        //btnNewButton.setForeground(Color.LIGHT_GRAY);
        btnNewButton.setBackground(myColor);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Folder = Tools.GetLoLFolder();
                    txtEnterYourLeague.setText(Folder);
                    if (Folder != "No installation found") {
                        RadioButton1.setEnabled(true);
                        RadioButton2.setEnabled(true);
                        comboBox.setEnabled(true);
                        comboBox_1.setEnabled(false);
                        GetData(comboBox);

                    } else {
                        RadioButton1.setEnabled(false);
                        RadioButton2.setEnabled(false);
                        comboBox.setEnabled(false);
                        comboBox_1.setEnabled(false);
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SAXException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ParserConfigurationException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (PatchException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        btnNewButton.setBounds(432, 110, 119, 29);
        contentPane.add(btnNewButton);
        //progressBar.setForeground(Color.WHITE);
        progressBar.setBounds(10, 140, 541, 25);
        contentPane.add(progressBar);
        ButtonPatch.setFont(new Font("Dialog", Font.PLAIN, 11));
        //ButtonPatch.setForeground(Color.LIGHT_GRAY);
        ButtonPatch.setBackground(myColor);
        ButtonPatch.setEnabled(false);
        ButtonPatch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (SoundpackcomBoBox.getSelectedItem() == null) {
                    System.out.println("- No Soundpack selected!");
                } else {
                    SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                        public String doInBackground() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, SAXException, ParserConfigurationException, URISyntaxException, PatchException {
                            JComboBox tempComboBox;
                            if (comboBox.isEnabled()) {
                                tempComboBox = comboBox;
                            } else {
                                tempComboBox = comboBox_1;
                            }
                            EnableAllButtons(false);
                            try {
                                job = new Patcher(txtEnterYourLeague.getText(), SoundpackcomBoBox.getSelectedItem().toString(), tempComboBox.getSelectedItem().toString(), Options.comboBox_1.getSelectedItem().toString());
                                job.addChangeListener(Listener);
                                job.PrepareSoundpackforPatch();

                                job = null;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                new ErrorScreen(e);
                            } finally {
                                EnableAllButtons(true);
                                GetData(tempComboBox);
                            }
                            return null;
                        }
                    };
                    worker.execute();
                }
            }
        });
        ButtonPatch.setBounds(432, 194, 119, 25);
        contentPane.add(ButtonPatch);

        //RadioButton1.setForeground(Color.LIGHT_GRAY);
        RadioButton1.setFont(new Font("Dialog", Font.PLAIN, 11));
        RadioButton1.setBackground(myColor);
        RadioButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                comboBox.setEnabled(true);
                comboBox_1.setEnabled(false);
                try {
                    GetData(comboBox);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SAXException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (URISyntaxException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (PatchException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        RadioButton1.setSelected(true);
        RadioButton1.setEnabled(false);
        buttonGroup.add(RadioButton1);
        RadioButton1.setBounds(7, 195, 39, 23);
        contentPane.add(RadioButton1);

        RadioButton2.setFont(new Font("Dialog", Font.PLAIN, 11));
        //RadioButton2.setForeground(Color.LIGHT_GRAY);
        RadioButton2.setBackground(myColor);
        RadioButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBox.setEnabled(false);
                comboBox_1.setEnabled(true);
                try {
                    GetData(comboBox_1);
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SAXException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ParserConfigurationException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (PatchException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        RadioButton2.setEnabled(false);
        buttonGroup.add(RadioButton2);
        RadioButton2.setBounds(7, 222, 45, 23);
        contentPane.add(RadioButton2);

        btnUnpatch.setFont(new Font("Dialog", Font.PLAIN, 11));
        //btnUnpatch.setForeground(Color.LIGHT_GRAY);
        btnUnpatch.setBackground(myColor);
        btnUnpatch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        JComboBox tempComboBox;
                        if (comboBox.isEnabled()) {
                            tempComboBox = comboBox;
                        } else {
                            tempComboBox = comboBox_1;
                        }
                        EnableAllButtons(false);
                        try {
                            job = new Patcher(txtEnterYourLeague.getText(), SoundpackcomBoBox.getSelectedItem().toString(), tempComboBox.getSelectedItem().toString(), Options.comboBox_1.getSelectedItem().toString());
                            job.addChangeListener(Listener);
                            job.DeleteVOBankBackup();
                            job = null;
                        } catch (IOException e) {
                            Misc.ErrorMessage();
                            new ErrorScreen(e);
                        } finally {
                            EnableAllButtons(true);
                            GetData(tempComboBox);
                        }

                        return null;
                    }
                };
                worker.execute();
            }
        });
        btnUnpatch.setEnabled(false);
        btnUnpatch.setBounds(432, 220, 119, 25);
        contentPane.add(btnUnpatch);

        btnOptions.setFont(new Font("Dialog", Font.PLAIN, 11));
        //btnOptions.setForeground(Color.LIGHT_GRAY);
        btnOptions.setBackground(myColor);
        btnOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Options().setVisible(true);
            }
        });
        btnOptions.setBounds(191, 220, 125, 25);
        contentPane.add(btnOptions);


        label_3.setBounds(-138, 164, 148, 17);
        contentPane.add(label_3);

        menuBar.setBackground(myColor);

        oeffnen.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        oeffnen.setForeground(Color.LIGHT_GRAY);
        oeffnen.setOpaque(true);
        oeffnen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                url = "http://euw.leagueoflegends.com/board/showthread.php?t=473081";
                Misc.OpenLink(url);
            }
        });

        Links.getPopupMenu().setBorder(null);
        Links.setForeground(fontColor);
        Links.setBackground(myColor);

        menuBar.setBounds(0, 0, 564, 21);
        menuBar.add(Links);
        Links.add(oeffnen);


        mntmUsThread.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmUsThread.setForeground(Color.LIGHT_GRAY);
        mntmUsThread.setOpaque(true);
        mntmUsThread.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                url = "http://na.leagueoflegends.com/board/showthread.php?t=2140185";
                Misc.OpenLink(url);
            }
        });
        Links.add(mntmUsThread);


        mntmGerThread.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmGerThread.setForeground(Color.LIGHT_GRAY);
        mntmGerThread.setOpaque(true);
        mntmGerThread.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                url = "http://euw.leagueoflegends.com/board/showthread.php?t=74443";
                Misc.OpenLink(url);
            }
        });
        Links.add(mntmGerThread);


        separator_2.setBackground(Color.DARK_GRAY);
        Links.add(separator_2);


        mntmGetMoreSoundpacks.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmGetMoreSoundpacks.setForeground(Color.LIGHT_GRAY);
        mntmGetMoreSoundpacks.setOpaque(true);
        Links.add(mntmGetMoreSoundpacks);
        contentPane.add(menuBar);

        mnTutorials = new JMenu("Tutorials");
        mnTutorials.getPopupMenu().setBorder(null);
        mnTutorials.setForeground(fontColor);
        mnTutorials.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                mnTutorials.setBackground(Color.LIGHT_GRAY);
            }
        });
        //mnTutorials.setForeground(Color.LIGHT_GRAY);
        menuBar.add(mnTutorials);


        mntmHowToUse.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmHowToUse.setForeground(Color.LIGHT_GRAY);
        mntmHowToUse.setOpaque(true);
        mntmHowToUse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                url = "http://www.youtube.com/watch?v=n0V0VvsGPSg";
                Misc.OpenLink(url);
            }
        });
        mnTutorials.add(mntmHowToUse);


        mntmHowToInstall.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmHowToInstall.setForeground(Color.LIGHT_GRAY);
        mntmHowToInstall.setOpaque(true);
        mnTutorials.add(mntmHowToInstall);


        mntmHowToCreate.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmHowToCreate.setForeground(Color.LIGHT_GRAY);
        mntmHowToCreate.setOpaque(true);
        mntmHowToCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                url = "http://www.youtube.com/watch?v=36VrROBQ85Q";
                Misc.OpenLink(url);
            }
        });
        mnTutorials.add(mntmHowToCreate);


        mnAbout.getPopupMenu().setBorder(null);
        //mnAbout.setForeground(Color.LIGHT_GRAY);
        menuBar.add(mnAbout);
        mnAbout.setForeground(fontColor);


        mntmInfo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmInfo.setForeground(Color.LIGHT_GRAY);
        mntmInfo.setOpaque(true);
        mntmInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new Info().setVisible(true);
            }
        });
        mnAbout.add(mntmInfo);


        mntmCheckForUpdates.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmCheckForUpdates.setForeground(Color.LIGHT_GRAY);
        mntmCheckForUpdates.setOpaque(true);
        mntmCheckForUpdates.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Update().setVisible(true);
            }
        });

        mntmMenuitem.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        mntmMenuitem.setForeground(Color.LIGHT_GRAY);
        mntmMenuitem.setOpaque(true);
        mntmMenuitem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Misc.OpenLink("Logs\\");
            }
        });
        mnAbout.add(mntmMenuitem);
        mnAbout.add(mntmCheckForUpdates);


        Play.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        Play.setForeground(Color.LIGHT_GRAY);
        Play.setOpaque(true);
        Play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                url = "http://signup.leagueoflegends.com/?ref=4cee835d0d9ba939041141";
                Misc.OpenLink(url);
            }
        });
        Links.add(Play);


        SoundpackcomBoBox.setFont(new Font("Dialog", Font.PLAIN, 11));
        //SoundpackcomBoBox.setForeground(Color.LIGHT_GRAY);
        SoundpackcomBoBox.setBackground(myColor);
        SoundpackcomBoBox.setBounds(191, 194, 216, 25);
        contentPane.add(SoundpackcomBoBox);


        lblSelectSoundpack.setText("Select a Soundpack:");
        //lblSelectSoundpack.setForeground(Color.LIGHT_GRAY);
        lblSelectSoundpack.setFont(new Font("Dialog", Font.PLAIN, 11));
        lblSelectSoundpack.setBackground(Color.GRAY);
        lblSelectSoundpack.setBounds(191, 166, 148, 23);
        contentPane.add(lblSelectSoundpack);


        lblSelectYourRegion.setText("Select your region:");
        //lblSelectYourRegion.setForeground(Color.LIGHT_GRAY);
        lblSelectYourRegion.setFont(new Font("Dialog", Font.PLAIN, 11));
        lblSelectYourRegion.setBackground(Color.GRAY);
        lblSelectYourRegion.setBounds(10, 166, 103, 23);
        contentPane.add(lblSelectYourRegion);

        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(174, 180, 3, 90);
        separator.setBackground(Color.DARK_GRAY);
        contentPane.add(separator);


        separator_1.setOrientation(SwingConstants.VERTICAL);
        separator_1.setBounds(419, 180, 3, 90);
        contentPane.add(separator_1);


        lblPatchUnpatch.setText("Patch / Unpatch:");
        //lblPatchUnpatch.setForeground(Color.LIGHT_GRAY);
        lblPatchUnpatch.setFont(new Font("Dialog", Font.PLAIN, 11));
        lblPatchUnpatch.setBackground(Color.GRAY);
        lblPatchUnpatch.setBounds(432, 166, 125, 23);
        contentPane.add(lblPatchUnpatch);

        btnNewButton_1 = new JButton("Console");
        btnNewButton_1.setFont(new Font("Dialog", Font.PLAIN, 8));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                con.Fader(fader);

            }
        });
        btnNewButton_1.setBounds(-4, 259, 56, 23);
        contentPane.add(btnNewButton_1);


        btnInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new Info().setVisible(true);
            }
        });
        btnInfo.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnInfo.setBounds(50, 259, 56, 23);
        contentPane.add(btnInfo);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    GetData(comboBox);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SAXException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (URISyntaxException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (PatchException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        comboBox_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    GetData(comboBox_1);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SAXException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (URISyntaxException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (PatchException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });


        addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentMoved(ComponentEvent e) {
                        con.setBounds(e.getComponent().getX(), e.getComponent().getY() + 318, con.getWidth(), con.getHeight());
                        con.toFront();
                    }
                }
        );
        ScanForSoundPacks();


    }

    public void searchLoL() {
        BufferedReader LAWL = null;
        String Folder = null;
        File Paath = new File("Path");
        if (Paath.exists() != true) {
        } else {
            try {
                LAWL = new BufferedReader(new FileReader("Path"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                Folder = LAWL.readLine();
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            File file = new File(Folder);
            if (file.exists()) {
                RadioButton1.setEnabled(true);
                RadioButton2.setEnabled(true);
                comboBox.setEnabled(true);

                comboBox_1.setEnabled(false);
                txtEnterYourLeague.setText(Folder);
                try {
                    try {
                        GetData(comboBox);
                    } catch (PatchException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SAXException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ParserConfigurationException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } else {
                RadioButton1.setEnabled(false);
                RadioButton2.setEnabled(false);
                comboBox.setEnabled(false);
                comboBox_1.setEnabled(false);
                System.out.println("- Predefined Path containts no LoL installation");
            }
        }

    }

    public void updatewindow() {
        int response = JOptionPane.showConfirmDialog(null, "Search for Updates ? (Recommended)", "Update?", JOptionPane.YES_NO_OPTION);
        if (response == 0) {
            new Update().setVisible(true);
        }
    }

    private void ScanForSoundPacks() {
        SoundpackcomBoBox.removeAllItems();
        File CustomPacks = new File("Soundpacks/");
        File[] fileList = CustomPacks.listFiles();
        String lol = "- Installed Soundpacks: [";
        for (File f : fileList) {
            lol = lol + f.getName() + ", ";
            SoundpackcomBoBox.addItem(f.getName());
        }
        File Soundpacks = new File("Soundpacks/Unreal Tournament/");
        if (Soundpacks.exists()) {
            SoundpackcomBoBox.setSelectedItem("Unreal Tournament");
        }
    }

    private void RandomHeader() {
        int a = 0;
        String Path = null;
        String[] PicturesArray = {"Ashe.jpg", "Fiddle.jpg", "Irelia.jpg", "Jax.jpg", "Karthus.jpg", "Kayle.jpg", "Kayle2.jpg", "LB.jpg", "Lux.jpg", "Maokai.jpg", "Morgana.jpg", "Morgana2.jpg", "Noc.jpg", "Swain.jpg", "Vayne.jpg", "Xin.jpg", "Zyra.jpg", "Nami.jpg", "Morgana3.jpg", "Nasus.jpg", "Riven.jpg", "Shyvana.jpg", "Thresh.jpg", "TF.jpg", "Xin2.jpg", "Anivia.jpg", "Draaaaaven.jpg"};
        for (int index = 0; index < PicturesArray.length; index++) {
            double i = Math.random() * PicturesArray.length;
            a = (int) i;
            Path = PicturesArray[a];
        }
        try {
            BufferedImage image = ImageIO.read(Gui.class.getResourceAsStream("/RADSSoundPatcher/Pictures/Header/" + Path));
            JLabel1.setIcon(new ImageIcon(image));
            Color c = Misc.getColor(image);
            LookAndFeel(c);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalArgumentException e) {
            logger.error("Error, image not found (" + Path + ")");
        }
    }


    private static class MenubarPainter implements Painter<JComponent> {
        private Color colorz = Color.DARK_GRAY;
        private Color colorz2 = Gui.myColor;

        public MenubarPainter(Color color) {
        }

        public MenubarPainter(Color color1, Color color2) {
            this.colorz = color1;
            this.colorz2 = color2;
        }

        public void paint(Graphics2D g, JComponent object, int width, int heigth) {
            GradientPaint gp = new GradientPaint(0, 0, this.colorz, 0, 20, colorz2);
            g.setPaint(gp);
            //g.setColor(Color.RED);
            g.fillRect(0, 0, width, heigth);
        }

    }

    private static class ImagePainter implements Painter<JComponent> {
        private final Color colorz;
        private BufferedImage path;

        public ImagePainter(Color color, BufferedImage path) {
            this.colorz = color;
            this.path = path;
        }

        public void paint(Graphics2D g, JComponent object, int width, int heigth) {
            g.drawImage(path, 0, 0, object.getWidth(), object.getHeight(), null);
        }
    }

    public static void GetData(JComboBox SelectedcomboBox) throws IOException, SAXException, ParserConfigurationException, URISyntaxException, PatchException {
        String Region2 = SelectedcomboBox.getSelectedItem().toString();
        ArrayList XMArrayList;
        XMArrayList = Tools.GetRegionInformation(Region2);
        String VOBankName = XMArrayList.get(2).toString();
        String Region = XMArrayList.get(0).toString();
        String NewestFolder = Tools.GetNewestFolder(txtEnterYourLeague.getText(), Region, VOBankName);
        if (NewestFolder != null) {
            Boolean Backupcheck = VOBankMethods.CheckBackup(txtEnterYourLeague.getText(), Region, NewestFolder);
            ButtonPatch.setEnabled(true);
            if (Backupcheck == true) {
                btnUnpatch.setEnabled(true);
            } else {
                btnUnpatch.setEnabled(false);
            }
            System.out.println("- Soundbank found [@ " + NewestFolder + "] [Region: " + SelectedcomboBox.getSelectedItem().toString() + "] [Backup available: " + Backupcheck + "]");
        } else {
            ButtonPatch.setEnabled(false);
            btnUnpatch.setEnabled(false);
            System.out.println("- NO VOBank detected [Region: " + SelectedcomboBox.getSelectedItem().toString() + "]");
        }
    }

    public void ShowMessagebox(String Message) {
        JOptionPane.showMessageDialog(null, Message, "Error", 0);
    }

    public void EnableAllButtons(Boolean Boolean) {
        for (Component comp : contentPane.getComponents()) {
            if (comp.toString().contains("Button")) {
                comp.setEnabled(Boolean);
            }
            if (comp.toString().contains("Combo")) {
                comp.setEnabled(Boolean);
            }
        }
        if (Boolean == true) {
            if (RadioButton2.isSelected()) {
                comboBox.setEnabled(false);
                comboBox_1.setEnabled(true);
            } else {
                comboBox.setEnabled(true);
                comboBox_1.setEnabled(false);
            }
        }
    }

    public static void LookAndFeel(Color c) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {


                    UIManager.setLookAndFeel(info.getClassName());

                    UIManager.getLookAndFeelDefaults().put("ComboBox:\"ComboBox.listRenderer\".background", new javax.swing.plaf.ColorUIResource(myColor));
                    UIManager.getLookAndFeelDefaults().put("ComboBox:\"ComboBox.renderer\"[Disabled].textForeground", new javax.swing.plaf.ColorUIResource(myColor));
                    UIManager.getLookAndFeelDefaults().put("ComboBox:\"ComboBox.listRenderer\"[Selected].background", new javax.swing.plaf.ColorUIResource(c));
                    UIManager.getLookAndFeelDefaults().put("ComboBox:\"ComboBox.renderer\"[Selected].background", new javax.swing.plaf.ColorUIResource(c));

                    UIManager.getLookAndFeelDefaults().put("ComboBox[Editable+Focused].backgroundPainter",
                            new MenubarPainter(c, c.brighter()));


                    UIManager.getLookAndFeelDefaults().put("ComboBox.disabledBackground", new javax.swing.plaf.ColorUIResource(Color.BLACK));
                    UIManager.put("TextField.background", myColor);
                    UIManager.put("TextField.foreground", Color.LIGHT_GRAY);
                    if (txtEnterYourLeague != null) {
                        txtEnterYourLeague.setBorder(BorderFactory.createLineBorder(c));
                        btnNewButton.setBorder(BorderFactory.createSoftBevelBorder(0));
                    }

                    UIManager.put("ToolBar.background", c);
                    UIManager.getLookAndFeelDefaults().put("FileChooser.listViewBackground", Gui.myColor);

                    UIManager.put("Viewport.background", myColor);
                    UIManager.put("Viewport.foreground", Color.LIGHT_GRAY);


                    UIManager.getLookAndFeelDefaults().put("FileChooser.background", Gui.myColor);
                    UIManager.getLookAndFeelDefaults().put("List.Background", Gui.myColor);
                    UIManager.getLookAndFeelDefaults().put("FileChooser[Enabled].backgroundPainter",
                            new MenubarPainter(myColor.brighter(), myColor));
                    UIManager.getLookAndFeelDefaults().put("MenuBar[Enabled].backgroundPainter",
                            new MenubarPainter(new Color(127, 255, 191)));

                    UIManager.getLookAndFeelDefaults().put("FileChooser.background", new javax.swing.plaf.ColorUIResource(Gui.myColor));
                    UIManager.getLookAndFeelDefaults().put("nimbusLightBackground", new javax.swing.plaf.ColorUIResource(Gui.myColor));

                    UIManager.getLookAndFeelDefaults().put("background", new javax.swing.plaf.ColorUIResource(Gui.myColor));
                    UIManager.getLookAndFeelDefaults().put("menuText", new javax.swing.plaf.ColorUIResource(Color.LIGHT_GRAY));
                    UIManager.getLookAndFeelDefaults().put("textForeground", new javax.swing.plaf.ColorUIResource(Gui.fontColor));
                    UIManager.getLookAndFeelDefaults().put("Menu.background", new javax.swing.plaf.ColorUIResource(myColor));
                    UIManager.getLookAndFeelDefaults().put("MenuBar.background", new javax.swing.plaf.ColorUIResource(myColor));
                    UIManager.getLookAndFeelDefaults().put("Button[Enabled].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_NORMAL));

                    UIManager.getLookAndFeelDefaults().put("Button[Disabled].textForeground",
                            new javax.swing.plaf.ColorUIResource(Gui.myColor));

                    UIManager.getLookAndFeelDefaults().put("Button[Focused].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_NORMAL));

                    UIManager.getLookAndFeelDefaults().put("Button[Focused+MouseOver].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_HOVER));

                    UIManager.getLookAndFeelDefaults().put("Button[MouseOver].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_HOVER));

                    UIManager.getLookAndFeelDefaults().put("Button[Default+Focused+MouseOver].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_HOVER));

                    UIManager.getLookAndFeelDefaults().put("Button[Default+Focused].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_NORMAL));

                    UIManager.getLookAndFeelDefaults().put("Button[Default+MouseOver].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_HOVER));

                    UIManager.getLookAndFeelDefaults().put("Button[Pressed].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_PRESSED));

                    UIManager.getLookAndFeelDefaults().put("Button[Default+Pressed].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_PRESSED));

                    UIManager.getLookAndFeelDefaults().put("Button[Default+Focused+Pressed].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_PRESSED));


                    UIManager.getLookAndFeelDefaults().put("Button[Focused+Pressed].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_PRESSED));

                    UIManager.getLookAndFeelDefaults().put("Button[Disabled].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_NORMAL));

                    UIManager.getLookAndFeelDefaults().put("Button[Default].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_NORMAL));

                    UIManager.getLookAndFeelDefaults().put("ComboBox[Enabled].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_NORMAL));

                    UIManager.getLookAndFeelDefaults().put("ComboBox[MouseOver].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_HOVER));

                    UIManager.getLookAndFeelDefaults().put("ComboBox[Focused+Pressed].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_PRESSED));

                    UIManager.getLookAndFeelDefaults().put("ComboBox[MouseOver].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_HOVER));

                    UIManager.getLookAndFeelDefaults().put("ComboBox[Focused+MouseOver].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_HOVER));

                    UIManager.getLookAndFeelDefaults().put("ComboBox[Focused].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_NORMAL));

                    UIManager.getLookAndFeelDefaults().put("ComboBox.background", Color.RED);

                    UIManager.getLookAndFeelDefaults().put("ComboBox:\"ComboBox.listRenderer\"[Selected].background",
                            new javax.swing.plaf.ColorUIResource(Gui.myColor));


                    UIManager.getLookAndFeelDefaults().put("ComboBox[Disabled].backgroundPainter",
                            new ImagePainter(new Color(127, 255, 191), BUTTON_NORMAL));

                    UIManager.getLookAndFeelDefaults().put("ScrollBar:ScrollBarThumb[Enabled].backgroundPainter",
                            new MenubarPainter(new Color(127, 255, 191).brighter().brighter()));

                    UIManager.getLookAndFeelDefaults().put("ScrollBar:ScrollBarThumb[MouseOver].backgroundPainter",
                            new MenubarPainter(new Color(127, 255, 191).brighter()));

                    UIManager.getLookAndFeelDefaults().put("ScrollBar:\"ScrollBar.button\"[Enabled].foregroundPainter",
                            new MenubarPainter(new Color(127, 255, 191).brighter()));

                    UIManager.getLookAndFeelDefaults().put("ProgressBar[Enabled].backgroundPainter",
                            new MenubarPainter(new Color(0, 0, 0), myColor));

                    UIManager.getLookAndFeelDefaults().put("ProgressBar[Enabled].foregroundPainter",
                            new MenubarPainter(c, c.darker().darker()));

                    UIManager.getLookAndFeelDefaults().put("ProgressBar[Enabled+Finished].foregroundPainter",
                            new MenubarPainter(c, c.darker().darker()));


                    UIManager.getLookAndFeelDefaults().put("MenuItem[MouseOver].backgroundPainter",
                            new MenubarPainter(c, c.darker().darker()));

                    UIManager.getLookAndFeelDefaults().put("MenuItem.background", new javax.swing.plaf.ColorUIResource(myColor));
                    UIManager.getLookAndFeelDefaults().put("MenuItem.foreground", new javax.swing.plaf.ColorUIResource(fontColor));
                    UIManager.getLookAndFeelDefaults().put("MenuItem[MouseOver].textForeground", new javax.swing.plaf.ColorUIResource(fontColor));

                    UIManager.getLookAndFeelDefaults().put("nimbusBase",
                            new javax.swing.plaf.ColorUIResource(myColor));

                    UIManager.getLookAndFeelDefaults().put("nimbusInfoBlue",
                            new javax.swing.plaf.ColorUIResource(c));

                    UIManager.getLookAndFeelDefaults().put("nimbusSelectionBackground",
                            new javax.swing.plaf.ColorUIResource(c));

                    UIManager.getLookAndFeelDefaults().put("Menu[Enabled+Selected].backgroundPainter",
                            new MenubarPainter(c));

                    UIManager.getLookAndFeelDefaults().put("MenuBar:Menu[Selected].backgroundPainter",
                            new MenubarPainter(c));


                    UIManager.getLookAndFeelDefaults().put("Separator.background",
                            new javax.swing.plaf.ColorUIResource(Color.DARK_GRAY));

                    UIManager.getLookAndFeelDefaults().put("Separator[Enabled].backgroundPainter",
                            new MenubarPainter(myColor.brighter().brighter(), myColor.brighter().brighter()));

                    UIManager.getLookAndFeelDefaults().put("Menu.background", new javax.swing.plaf.ColorUIResource(c));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void RedirectOutputStream() {
        System.setOut(new PrintStream(System.out) {
            boolean newline = false;


            @Override
            public void print(Object s) {
                append(s, newline);
                super.print(s);
                newline = false;
            }

            @Override
            public void print(String s) {
                append(s, newline);
                super.print(s);
                newline = false;
            }

            @Override
            public void println(String s) {
                newline = true;
                super.println((s));
            }

            private void append(Object s, boolean appender) {
                if (appender) {
                    con.area.append(s.toString() + "\n");
                } else {
                    con.area.append(s.toString());
                }
            }


        });
    }

    private void UpdateLookandFeel() {
        SwingUtilities.updateComponentTreeUI(Gui.this);
        Links.getPopupMenu().setBorder(null);
        mnTutorials.getPopupMenu().setBorder(null);
        mnAbout.getPopupMenu().setBorder(null);
    }
}