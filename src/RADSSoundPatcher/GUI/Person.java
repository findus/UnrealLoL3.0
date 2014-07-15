package RADSSoundPatcher.GUI;

import RADSSoundPatcher.Misc.Misc;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


public class Person extends JFrame {

    private JPanel contentPane;
    private JLabel BildPane;
    private JPanel panel;
    private JLabel lblNewLabel;
    private int id;
    private static Logger logger = Logger.getRootLogger();
    private BufferedImage icon = null;


    /**
     * Create the frame.
     */
    public Person(final String naem, String kleinertext, final int id) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(80, 69, 311, 134);
        contentPane.add(panel);
        panel.setLayout(null);

        BildPane = new JLabel("");
        BildPane.setBounds(0, 0, 67, 67);
        panel.add(BildPane);
        BildPane.setOpaque(true);
        try {
            icon = ImageIO.read(Person.class.getResourceAsStream("/RADSSoundPatcher/Pictures/Monsoon.jpg"));

            BildPane.setIcon(new ImageIcon(icon.getScaledInstance(67, 67, BufferedImage.SCALE_SMOOTH)));
            new Thread(new Runnable() {
                @Override
                public void run() {

                    icon = getPicture(naem);
                    if (icon != null) {
                        BildPane.setIcon(new ImageIcon(icon.getScaledInstance(67, 67, BufferedImage.SCALE_SMOOTH)));
                    }
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        JLabel lblNewLabel_2 = new JLabel(naem);
        lblNewLabel_2.setBounds(70, 0, 150, 21);
        panel.add(lblNewLabel_2);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 17));

        lblNewLabel = new JLabel(kleinertext);
        lblNewLabel.setBounds(70, 23, 211, 44);
        panel.add(lblNewLabel);
        this.BildPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);    //To change body of overridden methods use File | Settings | File Templates.

                Misc.OpenLink("http://www.lolking.net/summoner/euw/" + id);
            }

        });
    }

    public BufferedImage getPicture(String naem) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new URL("http://avatar.leagueoflegends.com/euw/" + naem + ".png"));
        } catch (IOException e) {
            logger.error("No Connection available");

        }
        return img;

    }

    public JPanel xXxgetContentPane() {
        return this.panel;
    }

}
