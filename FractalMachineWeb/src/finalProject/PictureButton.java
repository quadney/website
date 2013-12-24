package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PictureButton extends JButton{
	//this class sets the button to be an image
	BufferedImage icon;
	public final int SIZE = 100;
	
	public PictureButton(BufferedImage image){
		setLayout(new BorderLayout());
		icon = image;
		add(new JLabel(new ImageIcon(icon)), BorderLayout.CENTER);
		setMinimumSize(new Dimension(SIZE, SIZE));
		setMaximumSize(new Dimension(SIZE, SIZE));
		setPreferredSize(new Dimension(SIZE, SIZE));
	}
	
	public void paint(Graphics g){
		g.drawImage(icon, 0, 0, null);
	}
}
