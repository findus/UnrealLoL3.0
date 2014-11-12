package RADSSoundPatcher.GUI;

import RADSSoundPatcher.Manager.ArchiveManager;
import RADSSoundPatcher.Manager.Soundpack;
import RADSSoundPatcher.Misc.Misc;
import RADSSoundPatcher.exception.AlreadyModdedException;

import RADSSoundPatcher.exception.notModdedExcption;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

public class Gui extends JFrame {

	public static boolean fader = true;
	public static String url;
	public static final String Version = "3.0.0.1";
	public static JLabel headerLabel = new JLabel("JLabel1");
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextField lolPath;
	static JButton openButton;
	static JButton ButtonPatch;
	static JButton btnUnpatch;
	public static JComboBox<String> regionCombobox;
	public static JProgressBar progressBar;
	private JMenuBar menuBar;
	public static final Color myColor = Color.decode("#121212");
	public static final Color fontColor = Color.decode("#969696");
	public static Console con = null;
	public static JButton consoleButton;
	private static BufferedImage BUTTON_NORMAL = null;
	private static BufferedImage BUTTON_HOVER = null;
	private static BufferedImage BUTTON_PRESSED = null;
	private static JMenu mnAbout = null;
	private static JMenu mnTutorials = null;
	private static JMenu Links = null;
	private static JLabel coverLabel;
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
	private static JSeparator separatorRight;
	private static JSeparator separatorLeft;
	private static JLabel lblPatchUnpatch;
	private static JButton infoButton;
	private static Logger logger = Logger.getRootLogger();
	private static DefaultComboBoxModel model;

	private ArchiveManager manager;
	private JComboBox comboBox;
    private Gui gui;

	/**
	 * Create the frame.
	 *
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public Gui(ArchiveManager manager) throws URISyntaxException, SAXException,
			IOException, ParserConfigurationException, ClassNotFoundException,
			UnsupportedLookAndFeelException, InstantiationException,
			IllegalAccessException {


		BUTTON_NORMAL = ImageIO
				.read(Gui.class
						.getResourceAsStream("/RADSSoundPatcher/Pictures/Theme/ButtonNormal.png"));
		BUTTON_HOVER = ImageIO
				.read(Gui.class
						.getResourceAsStream("/RADSSoundPatcher/Pictures/Theme/ButtonMouseover.png"));
		BUTTON_PRESSED = ImageIO
				.read(Gui.class
						.getResourceAsStream("/RADSSoundPatcher/Pictures/Theme/ButtonPressed.png"));

		LookAndFeel(myColor);
        this.manager = manager;
		this.initializeComponents();
		this.initializeGUI();
		this.addListener();
        this.gui = this;



		loadSoundpacks();

		this.setVisible(true);
	}

	private void loadSoundpacks() {
		java.util.List<Soundpack> files = this.manager.getSoundpacks();
		for (Soundpack file : files) {
			model.addElement(file);

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initializeComponents() {
		regionCombobox = new JComboBox<String>();
		progressBar = new JProgressBar();
		contentPane = new JPanel();
		coverLabel = new JLabel("");
		lolPath = new JTextField();
		openButton = new JButton("Open");
		ButtonPatch = new JButton("Patch");

		btnUnpatch = new JButton("Unpatch");
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
		lblSelectSoundpack = new JLabel();
		lblSelectYourRegion = new JLabel();
		separatorLeft = new JSeparator();
		separatorRight = new JSeparator();
		lblPatchUnpatch = new JLabel();
		infoButton = new JButton("Info");
		model = new DefaultComboBoxModel();
		comboBox = new JComboBox(model);
        con = new Console(this);

		new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("ErhoeheProgressbarWert")) {
					Object obj = evt.getNewValue();
					int value = ((Integer) (obj)).intValue();
					progressBar.setValue(value);
				} else if (evt.getPropertyName().equals("ErstelleMessagebox")) {
					ShowMessagebox(evt.getNewValue().toString());
				} else if (evt.getPropertyName().equals("ERROR")) {
					new RADSSoundPatcher.GUI.ErrorScreen(
							(Exception) evt.getNewValue());
				}
			}
		};
	}

	public void initializeGUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getClassLoader().getResource(
						"RADSSoundPatcher/Pictures/Monsoon.jpg")));

		System.out
				.println("--------------------------------------------------------------");
		System.out
				.println("|  RADSSoundPatcher  for League of Legends                   |");
		System.out.println("|  V " + Version
				+ "                                                 |");
		System.out
				.println("|  Created with IntelliJ Idea                                |");
		System.out
				.println("|  16.12.2013                                    by Fozruk   |");
		System.out
				.println("--------------------------------------------------------------");
		System.out.println("- Loading GUI....");
		RandomHeader();
		Options.Soundpacks();

		regionCombobox.setFont(new Font("Dialog", Font.PLAIN, 11));
		regionCombobox.setBorder(BorderFactory.createLineBorder(Color.gray, 0));
		regionCombobox.getEditor().getEditorComponent()
				.setForeground(Color.GREEN);
		// comboBox.setForeground(Color.LIGHT_GRAY);
		regionCombobox.setBackground(myColor);

		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
			}
		});
		progressBar.setValue(100);
		progressBar
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		progressBar.setBackground(myColor);
		setTitle("Unreal Tournament Announcer Mod for League of Legends V "
				+ Version);
		setResizable(false);
		setBounds(100, 100, 569, 306);

		contentPane.setBackground(myColor);
		// contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		coverLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getClassLoader().getResource(
						"RADSSoundPatcher/Pictures/Theme/Fader.png"))));
		coverLabel.setBounds(0, 92, 568, 16);
		contentPane.add(coverLabel);
		regionCombobox.setToolTipText("");
		regionCombobox.setBounds(7, 194, 155, 25);
		regionCombobox.setForeground(myColor);
		contentPane.add(regionCombobox);
		regionCombobox.addItem("English");
		regionCombobox.addItem("German");
		regionCombobox.addItem("Greek");
		regionCombobox.addItem("Polski");
		regionCombobox.addItem("Espagnol");
		regionCombobox.addItem("French");
		regionCombobox.addItem("Romanian");
		regionCombobox.addItem("Russia");
		regionCombobox.addItem("Turkey");
		regionCombobox.addItem("Italian");
        regionCombobox.addItem("English_US");
        regionCombobox.addItem("Portugues");
        regionCombobox.addItem("LATNLATS");
        regionCombobox.addItem("Oceania");

		headerLabel.setBounds(0, 18, 565, 90);
		contentPane.add(headerLabel);

		lolPath.setFont(new Font("Dialog", Font.PLAIN, 11));
		lolPath.setBackground(myColor);
		// txtEnterYourLeague.setForeground(Color.LIGHT_GRAY);
		lolPath.setText("Enter your League of Legends Path here...");
		if(manager.getLolPath() != null)
         lolPath.setText(manager.getLolPath().getAbsolutePath());
        lolPath.setEditable(false);
		lolPath.setBounds(12, 112, 410, 24);
		contentPane.add(lolPath);
		lolPath.setColumns(10);
		// txtEnterYourLeague.setForeground(Color.LIGHT_GRAY);

		openButton.setFont(new Font("Dialog", Font.PLAIN, 11));
		// btnNewButton.setForeground(Color.LIGHT_GRAY);
		openButton.setBackground(myColor);

		openButton.setBounds(432, 110, 119, 29);
		contentPane.add(openButton);
		// progressBar.setForeground(Color.WHITE);
		progressBar.setBounds(10, 140, 541, 25);
		contentPane.add(progressBar);
		ButtonPatch.setFont(new Font("Dialog", Font.PLAIN, 11));
		// ButtonPatch.setForeground(Color.LIGHT_GRAY);
		ButtonPatch.setBackground(myColor);
		ButtonPatch.setBounds(432, 194, 119, 25);
		contentPane.add(ButtonPatch);

		btnUnpatch.setFont(new Font("Dialog", Font.PLAIN, 11));
		// btnUnpatch.setForeground(Color.LIGHT_GRAY);
		btnUnpatch.setBackground(myColor);
		btnUnpatch.setEnabled(false);
		btnUnpatch.setBounds(432, 220, 119, 25);
		contentPane.add(btnUnpatch);

		menuBar.setBackground(myColor);

		oeffnen.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		oeffnen.setForeground(Color.LIGHT_GRAY);
		oeffnen.setOpaque(true);

		Links.getPopupMenu().setBorder(null);
		Links.setForeground(fontColor);
		Links.setBackground(myColor);

		menuBar.setBounds(0, 0, 564, 21);
		menuBar.add(Links);
		Links.add(oeffnen);

		mntmUsThread.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		mntmUsThread.setForeground(Color.LIGHT_GRAY);
		mntmUsThread.setOpaque(true);

		Links.add(mntmUsThread);

		mntmGerThread.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		mntmGerThread.setForeground(Color.LIGHT_GRAY);
		mntmGerThread.setOpaque(true);

		Links.add(mntmGerThread);

		separator_2.setBackground(Color.DARK_GRAY);
		Links.add(separator_2);

		mntmGetMoreSoundpacks.setFont(new Font("Segoe UI Semibold", Font.PLAIN,
				11));
		mntmGetMoreSoundpacks.setForeground(Color.LIGHT_GRAY);
		mntmGetMoreSoundpacks.setOpaque(true);
		Links.add(mntmGetMoreSoundpacks);
		contentPane.add(menuBar);

		mnTutorials = new JMenu("Tutorials");
		mnTutorials.getPopupMenu().setBorder(null);
		mnTutorials.setForeground(fontColor);

		// mnTutorials.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnTutorials);

		mntmHowToUse.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		mntmHowToUse.setForeground(Color.LIGHT_GRAY);
		mntmHowToUse.setOpaque(true);

		mnTutorials.add(mntmHowToUse);

		mntmHowToInstall.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		mntmHowToInstall.setForeground(Color.LIGHT_GRAY);
		mntmHowToInstall.setOpaque(true);
		mnTutorials.add(mntmHowToInstall);

		mntmHowToCreate.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		mntmHowToCreate.setForeground(Color.LIGHT_GRAY);
		mntmHowToCreate.setOpaque(true);

		mnTutorials.add(mntmHowToCreate);

		mnAbout.getPopupMenu().setBorder(null);
		// mnAbout.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnAbout);
		mnAbout.setForeground(fontColor);

		mntmInfo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		mntmInfo.setForeground(Color.LIGHT_GRAY);
		mntmInfo.setOpaque(true);

		mnAbout.add(mntmInfo);

		mntmCheckForUpdates.setFont(new Font("Segoe UI Semibold", Font.PLAIN,
				11));
		mntmCheckForUpdates.setForeground(Color.LIGHT_GRAY);
		mntmCheckForUpdates.setOpaque(true);

		mntmMenuitem.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		mntmMenuitem.setForeground(Color.LIGHT_GRAY);
		mntmMenuitem.setOpaque(true);

		mnAbout.add(mntmMenuitem);
		mnAbout.add(mntmCheckForUpdates);

		Play.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		Play.setForeground(Color.LIGHT_GRAY);
		Play.setOpaque(true);

		Links.add(Play);

		lblSelectSoundpack.setText("Select a Soundpack:");
		// lblSelectSoundpack.setForeground(Color.LIGHT_GRAY);
		lblSelectSoundpack.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblSelectSoundpack.setBackground(Color.GRAY);
		lblSelectSoundpack.setBounds(191, 166, 148, 23);
		contentPane.add(lblSelectSoundpack);

		lblSelectYourRegion.setText("Select your region:");
		// lblSelectYourRegion.setForeground(Color.LIGHT_GRAY);
		lblSelectYourRegion.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblSelectYourRegion.setBackground(Color.GRAY);
		lblSelectYourRegion.setBounds(10, 166, 103, 23);
		contentPane.add(lblSelectYourRegion);

		separatorLeft.setOrientation(SwingConstants.VERTICAL);
		separatorLeft.setBounds(174, 180, 3, 90);
		separatorLeft.setBackground(Color.DARK_GRAY);
		contentPane.add(separatorLeft);

		separatorRight.setOrientation(SwingConstants.VERTICAL);
		separatorRight.setBounds(419, 180, 3, 90);
		contentPane.add(separatorRight);

		lblPatchUnpatch.setText("Patch / Unpatch:");
		// lblPatchUnpatch.setForeground(Color.LIGHT_GRAY);
		lblPatchUnpatch.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblPatchUnpatch.setBackground(Color.GRAY);
		lblPatchUnpatch.setBounds(432, 166, 125, 23);
		contentPane.add(lblPatchUnpatch);

		consoleButton = new JButton("Console");
		consoleButton.setFont(new Font("Dialog", Font.PLAIN, 8));

		consoleButton.setBounds(-4, 259, 56, 23);
		contentPane.add(consoleButton);

		infoButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		infoButton.setBounds(50, 259, 56, 23);
		contentPane.add(infoButton);

		comboBox.setBounds(181, 194, 228, 25);
		contentPane.add(comboBox);

	}

	public void addListener() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				RandomHeader();
				UpdateLookandFeel();
                checkPatchState();
			}
		});

		infoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Info(gui).setVisible(true);
			}
		});

		consoleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				con.Fader(fader);

			}
		});

		Play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				url = "http://signup.leagueoflegends.com/?ref=4cee835d0d9ba939041141";
				Misc.OpenLink(url);
			}
		});

		mntmMenuitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Misc.OpenLink("Logs\\");
			}
		});

//		mntmCheckForUpdates.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				new Update(gui).setVisible(true);
//			}
//		});

		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Info(gui).setVisible(true);
			}
		});

		mntmHowToCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				url = "http://www.youtube.com/watch?v=36VrROBQ85Q";
				Misc.OpenLink(url);
			}
		});

		mntmHowToUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				url = "http://www.youtube.com/watch?v=n0V0VvsGPSg";
				Misc.OpenLink(url);
			}
		});

		mnTutorials.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				mnTutorials.setBackground(Color.LIGHT_GRAY);
			}
		});

		mntmGerThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				url = "http://euw.leagueoflegends.com/board/showthread.php?t=74443";
				Misc.OpenLink(url);
			}
		});

		mntmUsThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				url = "http://na.leagueoflegends.com/board/showthread.php?t=2140185";
				Misc.OpenLink(url);
			}
		});

		oeffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				url = "http://euw.leagueoflegends.com/board/showthread.php?t=473081";
				Misc.OpenLink(url);
			}
		});

		headerLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RandomHeader();
				UpdateLookandFeel();
			}
		});

		ButtonPatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

                try {
                    manager.installArchive((Soundpack) model.getSelectedItem());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (AlreadyModdedException e1) {
                    e1.printStackTrace();
                }
                checkPatchState();

            }
		});

        btnUnpatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPatchState();
                try {
                    manager.deinstallArchive((Soundpack) model.getSelectedItem());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (AlreadyModdedException e1) {
                    e1.printStackTrace();
                } catch (RADSSoundPatcher.exception.notModdedExcption notModdedExcption) {
                    notModdedExcption.printStackTrace();
                }
                checkPatchState();
            }
        });
        
        regionCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				manager.switchRegion(regionCombobox.getSelectedItem().toString());
				checkPatchState();
			}
		});
        comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				checkPatchState();				
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

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String test = RADSSoundPatcher.Find.Tools.GetLoLFolder();
                manager.setLolPath(test);
                lolPath.setText(test);
                checkPatchState();
            }
        });
	}

	public void checkPatchState() {
		try {
            Soundpack pack = (Soundpack) model.getSelectedItem();
			if (!manager.isSoundpackInstalled(pack)) {
				ButtonPatch.setEnabled(true);
				btnUnpatch.setEnabled(false);
			} else {
				ButtonPatch.setEnabled(false);
				btnUnpatch.setEnabled(true);
			}
		} catch (FileNotFoundException | NullPointerException e1) {
			ButtonPatch.setEnabled(false);
			btnUnpatch.setEnabled(false);
		}
	}

	public void updatewindow() {
		int response = JOptionPane.showConfirmDialog(null,
				"Search for Updates ? (Recommended)", "Update?",
				JOptionPane.YES_NO_OPTION);
		if (response == 0) {
			new Update(gui).setVisible(true);
		}
	}

	private void RandomHeader() {
		int a = 0;
		String Path = null;
		String[] PicturesArray = { "Ashe.jpg", "Fiddle.jpg", "Irelia.jpg",
				"Jax.jpg", "Karthus.jpg", "Kayle.jpg", "Kayle2.jpg", "LB.jpg",
				"Lux.jpg", "Maokai.jpg", "Morgana.jpg", "Morgana2.jpg",
				"Noc.jpg", "Swain.jpg", "Vayne.jpg", "Xin.jpg", "Zyra.jpg",
				"Nami.jpg", "Morgana3.jpg", "Nasus.jpg", "Riven.jpg",
				"Shyvana.jpg", "Thresh.jpg", "TF.jpg", "Xin2.jpg",
				"Anivia.jpg", "Draaaaaven.jpg" };
		for (int index = 0; index < PicturesArray.length; index++) {
			double i = Math.random() * PicturesArray.length;
			a = (int) i;
			Path = PicturesArray[a];
		}
		try {
			BufferedImage image = ImageIO.read(Gui.class
					.getResourceAsStream("/RADSSoundPatcher/Pictures/Header/"
							+ Path));
			headerLabel.setIcon(new ImageIcon(image));
			Color c = Misc.getColor(image);
			LookAndFeel(c);
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
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
			GradientPaint gp = new GradientPaint(0, 0, this.colorz, 0, 20,
					colorz2);
			g.setPaint(gp);
			// g.setColor(Color.RED);
			g.fillRect(0, 0, width, heigth);
		}

	}

	private static class ImagePainter implements Painter<JComponent> {
		private BufferedImage path;

		public ImagePainter(Color color, BufferedImage path) {
			this.path = path;
		}

		public void paint(Graphics2D g, JComponent object, int width, int heigth) {
			g.drawImage(path, 0, 0, object.getWidth(), object.getHeight(), null);
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

	}

	public static void LookAndFeel(Color c) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {

					UIManager.setLookAndFeel(info.getClassName());

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox:\"ComboBox.listRenderer\".background",
							new javax.swing.plaf.ColorUIResource(myColor));
					UIManager
							.getLookAndFeelDefaults()
							.put("ComboBox:\"ComboBox.renderer\"[Disabled].textForeground",
									new javax.swing.plaf.ColorUIResource(
											myColor));
					UIManager
							.getLookAndFeelDefaults()
							.put("ComboBox:\"ComboBox.listRenderer\"[Selected].background",
									new javax.swing.plaf.ColorUIResource(c));
					UIManager
							.getLookAndFeelDefaults()
							.put("ComboBox:\"ComboBox.renderer\"[Selected].background",
									new javax.swing.plaf.ColorUIResource(c));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox[Editable+Focused].backgroundPainter",
							new MenubarPainter(c, c.brighter()));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox.disabledBackground",
							new javax.swing.plaf.ColorUIResource(Color.BLACK));
					UIManager.put("TextField.background", myColor);
					UIManager.put("TextField.foreground", Color.LIGHT_GRAY);
					if (lolPath != null) {
						lolPath.setBorder(BorderFactory.createLineBorder(c));

						openButton.setBorder(BorderFactory
								.createSoftBevelBorder(0));

					}

					UIManager.put("ToolBar.background", c);
					UIManager.getLookAndFeelDefaults().put(
							"FileChooser.listViewBackground", Gui.myColor);

					UIManager.put("Viewport.background", myColor);
					UIManager.put("Viewport.foreground", Color.LIGHT_GRAY);

					UIManager.getLookAndFeelDefaults().put(
							"FileChooser.background", Gui.myColor);
					UIManager.getLookAndFeelDefaults().put("List.Background",
							Gui.myColor);
					UIManager.getLookAndFeelDefaults().put(
							"FileChooser[Enabled].backgroundPainter",
							new MenubarPainter(myColor.brighter(), myColor));
					UIManager.getLookAndFeelDefaults().put(
							"MenuBar[Enabled].backgroundPainter",
							new MenubarPainter(new Color(127, 255, 191)));

					UIManager.getLookAndFeelDefaults().put(
							"FileChooser.background",
							new javax.swing.plaf.ColorUIResource(Gui.myColor));
					UIManager.getLookAndFeelDefaults().put(
							"nimbusLightBackground",
							new javax.swing.plaf.ColorUIResource(Gui.myColor));

					UIManager.getLookAndFeelDefaults().put("background",
							new javax.swing.plaf.ColorUIResource(Gui.myColor));
					UIManager.getLookAndFeelDefaults().put(
							"menuText",
							new javax.swing.plaf.ColorUIResource(
									Color.LIGHT_GRAY));
					UIManager.getLookAndFeelDefaults()
							.put("textForeground",
									new javax.swing.plaf.ColorUIResource(
											Gui.fontColor));
					UIManager.getLookAndFeelDefaults().put("Menu.background",
							new javax.swing.plaf.ColorUIResource(myColor));
					UIManager.getLookAndFeelDefaults().put(
							"MenuBar.background",
							new javax.swing.plaf.ColorUIResource(myColor));
					UIManager.getLookAndFeelDefaults().put(
							"Button[Enabled].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_NORMAL));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Disabled].textForeground",
							new javax.swing.plaf.ColorUIResource(Gui.myColor));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Focused].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_NORMAL));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Focused+MouseOver].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_HOVER));

					UIManager.getLookAndFeelDefaults().put(
							"Button[MouseOver].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_HOVER));

					UIManager
							.getLookAndFeelDefaults()
							.put("Button[Default+Focused+MouseOver].backgroundPainter",
									new ImagePainter(new Color(127, 255, 191),
											BUTTON_HOVER));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Default+Focused].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_NORMAL));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Default+MouseOver].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_HOVER));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Pressed].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_PRESSED));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Default+Pressed].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_PRESSED));

					UIManager
							.getLookAndFeelDefaults()
							.put("Button[Default+Focused+Pressed].backgroundPainter",
									new ImagePainter(new Color(127, 255, 191),
											BUTTON_PRESSED));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Focused+Pressed].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_PRESSED));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Disabled].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_NORMAL));

					UIManager.getLookAndFeelDefaults().put(
							"Button[Default].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_NORMAL));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox[Enabled].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_NORMAL));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox[MouseOver].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_HOVER));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox[Focused+Pressed].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_PRESSED));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox[MouseOver].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_HOVER));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox[Focused+MouseOver].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_HOVER));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox[Focused].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_NORMAL));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox.background", Color.RED);

					UIManager
							.getLookAndFeelDefaults()
							.put("ComboBox:\"ComboBox.listRenderer\"[Selected].background",
									new javax.swing.plaf.ColorUIResource(
											Gui.myColor));

					UIManager.getLookAndFeelDefaults().put(
							"ComboBox[Disabled].backgroundPainter",
							new ImagePainter(new Color(127, 255, 191),
									BUTTON_NORMAL));

					UIManager
							.getLookAndFeelDefaults()
							.put("ScrollBar:ScrollBarThumb[Enabled].backgroundPainter",
									new MenubarPainter(new Color(127, 255, 191)
											.brighter().brighter()));

					UIManager
							.getLookAndFeelDefaults()
							.put("ScrollBar:ScrollBarThumb[MouseOver].backgroundPainter",
									new MenubarPainter(new Color(127, 255, 191)
											.brighter()));

					UIManager
							.getLookAndFeelDefaults()
							.put("ScrollBar:\"ScrollBar.button\"[Enabled].foregroundPainter",
									new MenubarPainter(new Color(127, 255, 191)
											.brighter()));

					UIManager.getLookAndFeelDefaults().put(
							"ProgressBar[Enabled].backgroundPainter",
							new MenubarPainter(new Color(0, 0, 0), myColor));

					UIManager.getLookAndFeelDefaults().put(
							"ProgressBar[Enabled].foregroundPainter",
							new MenubarPainter(c, c.darker().darker()));

					UIManager.getLookAndFeelDefaults().put(
							"ProgressBar[Enabled+Finished].foregroundPainter",
							new MenubarPainter(c, c.darker().darker()));

					UIManager.getLookAndFeelDefaults().put(
							"MenuItem[MouseOver].backgroundPainter",
							new MenubarPainter(c, c.darker().darker()));

					UIManager.getLookAndFeelDefaults().put(
							"MenuItem.background",
							new javax.swing.plaf.ColorUIResource(myColor));
					UIManager.getLookAndFeelDefaults().put(
							"MenuItem.foreground",
							new javax.swing.plaf.ColorUIResource(fontColor));
					UIManager.getLookAndFeelDefaults().put(
							"MenuItem[MouseOver].textForeground",
							new javax.swing.plaf.ColorUIResource(fontColor));

					UIManager.getLookAndFeelDefaults().put("nimbusBase",
							new javax.swing.plaf.ColorUIResource(myColor));

					UIManager.getLookAndFeelDefaults().put("nimbusInfoBlue",
							new javax.swing.plaf.ColorUIResource(c));

					UIManager.getLookAndFeelDefaults().put(
							"nimbusSelectionBackground",
							new javax.swing.plaf.ColorUIResource(c));

					UIManager.getLookAndFeelDefaults().put(
							"Menu[Enabled+Selected].backgroundPainter",
							new MenubarPainter(c));

					UIManager.getLookAndFeelDefaults().put(
							"MenuBar:Menu[Selected].backgroundPainter",
							new MenubarPainter(c));

					UIManager.getLookAndFeelDefaults().put(
							"Separator.background",
							new javax.swing.plaf.ColorUIResource(
									Color.DARK_GRAY));

					UIManager.getLookAndFeelDefaults().put(
							"Separator[Enabled].backgroundPainter",
							new MenubarPainter(myColor.brighter().brighter(),
									myColor.brighter().brighter()));

					UIManager.getLookAndFeelDefaults().put("Menu.background",
							new javax.swing.plaf.ColorUIResource(c));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void RedirectOutputStream() {
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
				if(con != null) {
                    if (appender) {
                        con.area.append(s.toString() + "\n");
                    } else {
                        con.area.append(s.toString());
                    }
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

	private void loadOtherRegion() {
		// TODO ArchiveManager neu Starten und wpk's neu scannen;
		// this.manager = new ArchiveManager("",regionCombobox.getT);
	}
}