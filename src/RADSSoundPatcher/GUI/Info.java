package RADSSoundPatcher.GUI;

/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 20.03.13
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Info extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JPanel panel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                try {
                    Info dialog = new Info();
                    dialog.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * Create the frame.
     */
    public Info() {
        setModal(true);
        setAutoRequestFocus(false);
        setResizable(false);
        setTitle("Info");
        //URL bildURL = getClass().getResource("Pictures/Monsoon.jpg");
        setIconImage(Toolkit.getDefaultToolkit().getImage("RADSSoundPatcher/Pictures/Monsoon.jpg"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Point l = Gui.dialog.getLocation();
        //setBounds(l.x, l.y, 485, 315);
        contentPane = new JPanel();
        contentPane.setBackground(Gui.myColor);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JButton btnNewButton = new JButton("Check for Update");
        btnNewButton.setBackground(Gui.myColor);
        btnNewButton.setForeground(Color.LIGHT_GRAY);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new Update().setVisible(true);
            }
        });

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(0, 8, 272, 244);
        contentPane.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btnNewButton.setBounds(0, 265, 116, 23);
        contentPane.add(btnNewButton);

        JLabel dtrpnV = new JLabel();
        dtrpnV.setForeground(Color.LIGHT_GRAY);
        dtrpnV.setText("V. " + Gui.Version);
        dtrpnV.setFont(new Font("Arial", Font.BOLD, 12));

        dtrpnV.setBackground(Gui.myColor);
        dtrpnV.setBounds(0, 245, 116, 23);
        contentPane.add(dtrpnV);

        JLabel lblKassadin = new JLabel("");
        lblKassadin.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblKassadin.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("RADSSoundPatcher/Pictures/Elise.png"))));
        lblKassadin.setBounds(35, 0, 444, 299);
        contentPane.add(lblKassadin);


        addLeute(new Person("Fozruk", "<html>Creator of the Java Application, Updater</html>", 20377190).xXxgetContentPane());
        addLeute(new Person("NoXiouZ", "<html>Inventor of the Mod, he released <br> the original soundpack</html>", 19109533).xXxgetContentPane());
        addLeute(new Person("Ego Dieselsportz", "/)", 23860308).xXxgetContentPane());


    }

    private void addLeute(JPanel p) {
        panel.add(p);
        this.revalidate();
        this.repaint();

    }
}
