package finalProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;

public class ColorButton extends JButton{
	int x;
	int y;
	Color color;
	
	public ColorButton(Color c){
		color = c;
		x = 100;
		y = 100;
		setMinimumSize(new Dimension(x, y));
		setMaximumSize(new Dimension(x, y));
		setPreferredSize(new Dimension(x, y));
	}
	
	public ColorButton(Color c, int x, int y){
		color = c;
		this.x = x;
		this.y = y;
		setMinimumSize(new Dimension(x, y));
		setMaximumSize(new Dimension(x, y));
		setPreferredSize(new Dimension(x, y));
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(color);
		g2d.fillRect(0, 0, x, y);
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public Color getColor(){
		return color;
	}

}
