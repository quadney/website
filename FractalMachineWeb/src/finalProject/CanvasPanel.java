package finalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.*;
import java.util.Random;
import java.util.Stack;

import javax.swing.*;

public class CanvasPanel extends JPanel{
	BufferedImage background;
	BufferedImage person;
	BufferedImage drawn;
	BufferedImage save;
	Timer timer;
	public final int MILLISECONDS_BETWEEN_FRAMES = 20;
	Point point;
	int x1, x2, y1, y2;
	int steps = 1;
	int gens = 1;
	int numStems;
	double alpha;
	double dTheta;
	int dR;
	Color plantColor = Color.GREEN;
	Color treeColor = Color.GREEN;
	Color backgroundColor;
	boolean plant, tree;
	LsysTurtle[] ft;
	boolean[] f;
	
	public CanvasPanel(BufferedImage person){
		this.person = person;
		this.background = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		this.drawn = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		save = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		initializeTimer();
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event){
				point = event.getPoint();
				timer.start();
			}

			public void mouseClicked(MouseEvent event){
				timer.stop();
				steps = 1;
				gens = 1;
			}
		});
		
	}
	
	public void setLArray(LsysTurtle[] ft){
		this.ft = ft;
	}
	
	public void setLTrueArray(boolean[] f){
		this.f = f;
		tree = true;
		plant = false;
	}
	
	public void background(Color color){
		backgroundColor = color;
		Graphics2D g2d = (Graphics2D) background.createGraphics();
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, 500, 500);
		g2d.dispose();
		repaint();
	}
	
	public void background(BufferedImage bg){
		Graphics2D g2d = (Graphics2D) background.createGraphics();
		g2d.drawImage(bg, 0, 0, null);
		g2d.dispose();
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawSave();
		g.drawImage(save, 0, 0, null);
	}
	
	public void initializeTimer(){
		timer = new Timer(MILLISECONDS_BETWEEN_FRAMES, new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//turn off timer so we can draw the image, and then restart it
				timer.stop();
				if(plant){
					steps++;
					drawPlant();
				}
				else{
					drawTree();
					if(gens < 6)
						gens++;
				}
				//restart timer (draw the next frame in MILLISECONDS_BETWEEN_FRAMES
				timer.restart();
			}
		});
	}

	public void drawPlantTrue(int numStems, int alpha, int dTheta, int dR, Color plantColor){
		this.numStems = numStems;
		this.alpha = alpha/10.0;
		this.dTheta = dTheta/10.0;
		this.dR = dR;
		this.plantColor = plantColor;
		plant = true;
		tree = false;
	}
	
	public void drawPlant(){
		Random random = new Random();
		Graphics2D g2d = (Graphics2D) drawn.createGraphics();
		g2d.setColor(plantColor);
		double r;
		double theta;
		double beta = 1.0 - alpha;
		int direction;
		double tau;
		
		for(int j = 0; j < numStems; j++){
			//reset the parameters of the coordinates
			x1 = point.x;
			y1 = point.y;
			x2 = point.x;
			y2 = y1 - 1;
			r = 1.0; 
			theta = Math.PI/2;
			direction = random.nextInt(2);
			//if the direction is 0, change it to -1, else it's already +1
			if(direction == 0)
				direction = -1;
			
			
			//plot the initial segment
			g2d.draw(new Line2D.Double(x1, y1, x2, y2));
			for(int i = 1; i < steps; i++){
	
				//find the virtual coins bias
				if(direction == -1) // right
					tau = alpha;
				else
					tau = beta;
				
				//flip the biased coin to determine which direction to turn next
				if(random.nextDouble() > tau)
					direction = 1;
				else
					direction = -1;
				
				//compute offset from end of the old growth segment to the new one
				r = r + dR;
				theta = theta + (dTheta*random.nextDouble()*direction);
				x1 = x2;
				y1 = y2;
				x2 += (int) Math.round(r*Math.cos(theta)); 
				y2 -= (int) Math.round(r*Math.sin(theta));
				
				//draw growth segment from old position to new
				g2d.draw(new Line2D.Double(x1, y1, x2, y2));
			}		
			repaint();
		}
	}

	public void drawTree(){
		Graphics2D g2d = (Graphics2D) drawn.createGraphics();
		g2d.setColor(treeColor);
		
		int rule = -1;
		for(int i = 0; i < f.length; i++){
			if(f[i])
				rule = i;
		}
		
		StringBuilder initiator = ft[rule].generationStrings[gens-1];
		double bearing = -90.0;
		int x1 = point.x;
		int y1 = point.y;
		int x2;
		int y2;
		//double segment = (relSegLength/10.0)*250.0;
		double segment = 5.0*250.0;
		double reduce = 1/Math.pow(ft[rule].scalingFactor, gens);
		double length = (segment*reduce);
		
		Stack<PersonTurtle> location = new Stack<PersonTurtle>();
		for(int i = 0; i < initiator.length(); i++){
			if(initiator.charAt(i) == 'F'){
				//draw a line from (x1,y1) to (x1-segmentLength, y1+segmentLength)
				x2 = x1 + (int) Math.round(5.0*Math.cos(bearing*Math.PI/180));
				y2 = y1 + (int) Math.round(5.0*Math.sin(bearing*Math.PI/180));
				g2d.drawLine(x1, y1, x2, y2);
				x1 = x2;
				y1 = y2;
			}
			else if(initiator.charAt(i) == 'f'){
				//updates the x and y coordinates
				x1 += (int) Math.round(length*Math.cos(bearing*Math.PI/180));
				y1 += (int) Math.round(length*Math.sin(bearing*Math.PI/180));
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
			repaint();
		}
		g2d.dispose();
	}
	
	public void setTreeColor(Color tree){
		this.treeColor = tree;
	}
	
	public void clearDrawn(){
		drawn = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		repaint();
	}
	
	public void drawSave(){
		Graphics2D g2 = (Graphics2D) save.createGraphics();
		g2.drawImage(background, 0, 0, null);
		g2.drawImage(person, 0, 0, null);
		g2.drawImage(drawn, 0, 0, null);
		g2.dispose();
	}
	
	public BufferedImage getCanvas(){
		drawSave();
		return save;
	}
	
}
