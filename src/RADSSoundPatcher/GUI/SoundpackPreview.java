package RADSSoundPatcher.GUI;

import RADSSoundPatcher.Manager.Soundpack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by philipp on 10.11.2014.
 */
public class SoundpackPreview extends JLayeredPane {
	private FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 1, 1);
	private BufferedImage icon;
	private JLabel pic;
	private JLabel panel;
	private boolean selected = false;
	private Soundpack file;

	public SoundpackPreview(Soundpack file) throws IOException {
		setOpaque(true);
		setPreferredSize(new Dimension(230, 30));
		setLayout(layout);
		icon = ImageIO.read(SoundpackPreview.class
                .getResourceAsStream("/RADSSoundPatcher/Pictures/Microphone.png"));
		this.pic = new JLabel("");
		this.file = file;
		this.pic.setPreferredSize(new Dimension(30, 30));
		Image scaledImage = icon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		this.pic.setIcon(new ImageIcon(scaledImage));
		this.add(pic);

		panel = new JLabel(file.getName());
		add(panel);
		panel.setBackground(new Color(0, 0, 0, 0));
		this.setVisible(true);

		this.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				
				if(selected)
					setBackground(Color.green.darker().darker().darker());
				else
					setBackground(Color.DARK_GRAY.darker());
			};

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				if(selected)
					setBackground(Color.green.darker().darker().darker());
				else
					setBackground(Color.black);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if (selected) {
					selected = !selected;
					setBackground(Color.black);
				} else {
					selected = !selected;
					setBackground(Color.green.darker().darker().darker().darker());
				}
			}
		});
	}

	public Soundpack getFile() {
		return file;
	}

	public void setFile(Soundpack file) {
		this.file = file;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
