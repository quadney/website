package finalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class McrmPanel extends JPanel{
	BufferedImage person, image;
	AffineTransform[] afMatrix;
	int n;
	Graphics2D g2d;
	Color bgColor = Color.white;
	
	public McrmPanel(BufferedImage person){
		this.person = person;
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		g2d = image.createGraphics();
		g2d.setColor(bgColor);
		g2d.fillRect(0, 0, 500, 500);
		g2d.dispose();
		repaint();
		n = 1;
	}
	
	public void drawFractal(){
		try{
			g2d = image.createGraphics();
			g2d.setColor(bgColor);
			g2d.fillRect(0, 0, 500, 500);
			BufferedImage temp = person;
			for(int i = 0; i < n; i++){
				for(int j = 0; j < afMatrix.length; j++){
					g2d = image.createGraphics();
					g2d.translate(500*afMatrix[j].getTranslateX(), 500*afMatrix[j].getTranslateY());
					g2d.drawImage(temp, afMatrix[j], null);
					g2d.translate((-1)*500*afMatrix[j].getTranslateX(), (-1)*500*afMatrix[j].getTranslateY());
					g2d.dispose();
				}
				temp = copyImage(image);
			}
			repaint();
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(this, "Please, select a fractal first");
		}
	}
	
	private BufferedImage copyImage(BufferedImage image){
		return new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
	}
	
	public void setAffineMatrix(AffineTransform[] af){
		afMatrix = af;
		drawFractal();
	}
	
	public void changeColor(Color color){
		bgColor = color;
		drawFractal();
	}
	
	public void setNumberGenerations(int n){
		this.n = n;
		drawFractal();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public BufferedImage getCanvas(){
		return image;
	}
}
