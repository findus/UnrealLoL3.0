package RADSSoundPatcher.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorScreen extends JDialog {

    private final JPanel contentPanel = new JPanel();

    private String ll;
    Exception _exceptionz = null;

    /**
     * Launch the application.
     */

    /**
     * Create the dialog.
     */
    public ErrorScreen(Exception e) {

        _exceptionz = e;
        Load();

        setVisible(true);
    }


    public void Load() {
        setResizable(false);
        setModal(true);
        setBounds(100, 100, 640, 353);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JButton btnNewButton = new JButton("View Logfile");
            btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 10));
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    //Misc.OpenLink(Logfile.getInstance().getFilePath());
                }
            });
            btnNewButton.setBackground(Color.BLACK);
            btnNewButton.setForeground(Color.LIGHT_GRAY);
            btnNewButton.setBounds(43, 246, 141, 28);
            contentPanel.add(btnNewButton);
        }
        {
            JButton okButton = new JButton("Close");
            okButton.setFont(new Font("SansSerif", Font.PLAIN, 10));
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    dispose();
                }
            });
            okButton.setForeground(Color.LIGHT_GRAY);
            okButton.setBackground(Color.BLACK);
            okButton.setBounds(43, 276, 141, 28);
            contentPanel.add(okButton);
            okButton.setActionCommand("OK");
            getRootPane().setDefaultButton(okButton);
        }
        JButton btnViewException = new JButton("View Exception");
        btnViewException.setFont(new Font("SansSerif", Font.PLAIN, 10));
        btnViewException.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JDialog d = new JDialog();
                TextArea area = new TextArea(15, 130);
                area.setEditable(false);
                area.setForeground(Color.GRAY);

                d.getContentPane().add(area);
                d.setModal(true);
                d.pack();


                StackTraceElement[] str = _exceptionz.getStackTrace();
                area.append(_exceptionz.getMessage().toString() + "\n");
                for (int i = 0; i < str.length; i++) {
                    area.append(str[i].toString() + "\n");
                }
                area.setCaretPosition(0);
                d.setVisible(true);

            }
        });
        btnViewException.setBackground(Color.BLACK);
        btnViewException.setForeground(Color.LIGHT_GRAY);
        btnViewException.setBounds(43, 217, 141, 28);
        contentPanel.add(btnViewException);

        JLabel lblNewLabel_1 = new JLabel("ERROR");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 70));
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setBounds(-11, -23, 295, 94);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel = new JLabel("<html>A fatal error occured while patching this soundbank. <br>Click on \"View Exception\" to see additional information. <br>If you need help just visit the forum thread or add me ingame (Fozruk (EU-W))</html>");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNewLabel.setBounds(43, 43, 202, 169);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("RADSSoundPatcher/Pictures/ww.jpg"))));
        lblNewLabel_2.setBounds(-1, 0, 635, 329);
        contentPanel.add(lblNewLabel_2);
    }
}
