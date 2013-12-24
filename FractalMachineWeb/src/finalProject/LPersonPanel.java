package finalProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class LPersonPanel extends JPanel{
	BufferedImage image, person;
	Graphics2D g2d;
	int inDegrees, rXCoord, rYCoord, relSegLength, gens;
	LsysTurtle[] ft;
	boolean[] f;
	
	
	public LPersonPanel(BufferedImage person, int x, int y, int degrees, int segLength, int gens, LsysTurtle[] ft){
		this.person = person;
		rXCoord = x;
		rYCoord = y;
		inDegrees = degrees;
		relSegLength = segLength;
		this.gens = gens;
		this.ft = ft;
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		g2d = image.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 500, 500);
		g2d.dispose();
	}
	
	public void setF(boolean[] f){
		this.f = f;
		generatePicture();
	}
	
	public void setX(int x){
		this.rXCoord = x;
		generatePicture();
	}
	
	public void setY(int y){
		this.rYCoord = y;
		generatePicture();
	}
	
	public void setDegrees(int degrees){
		this.inDegrees = degrees;
		generatePicture();
	}
	
	public void setSegmentLength(int segLength){
		this.relSegLength = segLength;
		generatePicture();
	}
	
	public void setGens(int gen){
		this.gens = gen;
		generatePicture();
	}
	
	public void generatePicture(){		
		int rule = -1;
		for(int i = 0; i < f.length; i++){
			if(f[i])
				rule = i;
		}
		
		StringBuilder initiator = ft[rule].generationStrings[gens-1];
		double bearing = inDegrees;
		int x1 = rXCoord;
		int y1 = rYCoord;
		int x2;
		int y2;
		double segment = relSegLength/100.0;
		double reduce = 1/Math.pow(ft[rule].scalingFactor, gens);
		double length = segment*reduce;
		
		g2d = image.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 500, 500);
		g2d.setColor(Color.BLACK);		
		
		Stack<PersonTurtle> location = new Stack<PersonTurtle>();
		for(int i = 0; i < initiator.length(); i++){
			
			if(initiator.charAt(i) == 'F'){
				//draw a line from (x1,y1) to (x1-segmentLength, y1+segmentLength)
				x2 = x1 + (int) Math.round(person.getHeight()*length*Math.cos(bearing*Math.PI/180));
				y2 = y1 + (int) Math.round(person.getWidth()*length*Math.sin(bearing*Math.PI/180));

				drawPerson(bearing, x1, y1, length);
				
				x1 = x2;
				y1 = y2;
				
			}
			else if(initiator.charAt(i) == 'f'){
				//updates the x and y coordinates
				x1 += (int) Math.round(person.getHeight()*length*Math.cos(bearing*Math.PI/180));
				y1 += (int) Math.round(person.getWidth()*length*Math.sin(bearing*Math.PI/180));
			}
			else if(initiator.charAt(i) == '+'){
				//turns the turtle left theta degrees
				bearing = bearing + ft[rule].theta;
			}
			else if(initiator.charAt(i) == '-'){
				//turns the turtle right theta degrees
				bearing = bearing - ft[rule].theta;
			}
			else if(initiator.charAt(i) == '['){
				//push's the turtle's x and y coordinates into the stack
				location.push(new PersonTurtle(x1, y1, bearing, length));
			}
			else if(initiator.charAt(i) == ']'){
				//pops the turtle's last x and y coordinates from the stack
				PersonTurtle turtle = (PersonTurtle) location.pop();
				x1 = turtle.x;
				y1 = turtle.y;
				bearing = turtle.bearing;
				length = turtle.length;
			}
			
		}
		g2d.dispose();
		repaint();
	}
	
	public void drawPerson(double bearing, int x1, int y1, double length){		
		g2d.translate(y1-(person.getWidth()*length), x1-(person.getHeight()*length));
		g2d.scale(length, length);
		g2d.rotate(bearing*Math.PI/180, person.getWidth()/2, person.getHeight());
		g2d.drawImage(person, 0, 0, null);
		g2d.rotate((-1)*bearing*Math.PI/180, person.getWidth()/2, person.getHeight());
		g2d.scale(1/length, 1/length);
		g2d.translate((-1)*(y1-(person.getWidth()*length)), (-1)*(x1-(person.getHeight()*length)));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	public BufferedImage getCanvas(){
		return image;
	}

}
