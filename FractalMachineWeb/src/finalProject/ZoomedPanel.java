package finalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ZoomedPanel extends JPanel{
	int[] colors;
	float z_i;
	float z_r;
	int t;
	int tMax = 100;
	float zA;
	float zB;
	float muA;
	float muB;
	float rXMin;
	float rXMax;
	float iYMin;
	float iYMax;
	boolean mandelbrot = false;
	boolean julia = false;
	float complexHeight;
	float complexWidth;
	BufferedImage image;
	Color c1, c2, c3, c4;
	Timer timer;
	Timer rotation;
	final int MILLISECONDS_BETWEEN_FRAMES = 30;
	final int STEPS = 180;
	boolean time = false;
	BufferedImage person;
	int frameNumber;
	Graphics2D g2d;
	
	public ZoomedPanel(BufferedImage person, boolean m, Color cc1, Color cc2, Color cc3, Color cc4){
		this.person = person;
		c1 = cc1;
		c2 = cc2;
		c3 = cc3;
		c4 = cc4;
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		interpolate(c1, c2, c3, c4);
		mandelbrot = m;
		julia = !m;
		if(mandelbrot)
			initializeMandelbrot();
		else
			initializeJulia();
		repaint();
		initializeAnimation();
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event){}

			public void mouseClicked(MouseEvent event){
				Point point = event.getPoint();
				if(time){
					time = false;
					timer.stop();
				}
				else{
					time = true;
					convert((float)point.x/500, (float)point.y/500);
					timer.start();
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent event){}
			public void mouseMoved(MouseEvent event){}
		});
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	public void drawFractal(){
		if(mandelbrot)
			initializeMandelbrot();
		else
			initializeJulia();
		repaint();
	}
	
	private void initializeAnimation(){		
		frameNumber = 0;
		//create a timer to display the animation
		timer = new Timer(MILLISECONDS_BETWEEN_FRAMES, new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//turn off timer so we can draw the image, and then restart it
				timer.stop();
				zoomIn(.025f, .975f, .975f, .025f);
				//display the next frame
				if(mandelbrot){
					mandelbrot();
				}
				else
					julia();
				drawPerson();
				
				frameNumber = ((frameNumber + 1) < STEPS) ? (frameNumber + 1) : 0;
				
				timer.restart();
			}
		});
	}
	
	public void drawPerson(){
		g2d = image.createGraphics();
		g2d.rotate(frameNumber*(2.0*Math.PI)/STEPS, 250, 250);
		g2d.drawImage(person, 0, 0, null);
		g2d.rotate((-1)*frameNumber*(2.0*Math.PI)/STEPS, 250, 250);
		repaint();
		g2d.dispose();
	}

	public void initializeMandelbrot(){
		mandelbrot = true;
		julia = false;
		rXMin = -2.2f;
		rXMax = 1.0f;
		iYMax = 1.6f;
		iYMin = -1.6f;
		frameNumber = 0;
		mandelbrot();
		drawPerson();
	}
	
	public void initializeJulia(){
		mandelbrot = false;
		julia = true;
		rXMin = -1.6f;
		rXMax = 1.6f;
		iYMax = 1.6f;
		iYMin = -1.6f;
		frameNumber = 0;
		julia();
		drawPerson();
	}
	
	private void mandelbrot(){
		complexHeight = Math.abs(iYMax - iYMin);
		complexWidth = Math.abs(rXMax - rXMin);
		float dComHeight = complexHeight/500.0f;
		float dComWidth = complexWidth/500.0f;
		
		muB = iYMax;
		for(int y = 0; y < 500; ++y){
			muA = rXMin;
			for(int x = 0; x < 500; ++x){
				//convert x and y to be proportional to the mandelbrot set, a and b respectively				
				
				//set z and t = 0
				z_i = 0;
				z_r = 0;
				t = 0;
				
				while(t < tMax){
					//square z, add it to mu
					float r = z_r*z_r - z_i*z_i + muA;
					float i = 2*z_r*z_i + muB;
					z_r = r;
					z_i = i;
					
					if(Math.sqrt(z_r*z_r + z_i*z_i) > 2)
						break;
					++t;
				}
				if (t < tMax){
					//z has diverged and mu is not part of the Mandelbrot set
					//point a pixel using a color based on t's value
					image.setRGB(x, y, colors[t]);
				}
				else{
					//it might be part of the set, so we'll assume that it is
					//at point mu plot a pixel using a color thats part of the mandelbrot set (aka black)
					image.setRGB(x, y, 0xFF000000);
				}
				muA += dComWidth;
			}
			muB -= dComHeight;
		}
	}
	
	private void julia(){
		complexHeight = Math.abs(iYMax - iYMin);
		complexWidth = Math.abs(rXMax - rXMin);
		float dComHeight = complexHeight/500.0f;
		float dComWidth = complexWidth/500.0f;
		muA = -0.8f;
		muB = 0.156f;
		
		zB = iYMax;
		for(int y = 0; y < 500; y++){
			zA = rXMin;
			for(int x = 0; x < 500; x++){
				
				z_i = zB;
				z_r = zA;
				t = 0;
				
				while(t < tMax){
					//square z, add it to mu
					float r = z_r*z_r - z_i*z_i + muA;
					float i = 2*z_r*z_i + muB;
					z_r = r;
					z_i = i;
					
					if(Math.sqrt(z_r*z_r + z_i*z_i) > 2)
						break;
					++t;
					
				}
				if (t < tMax){
					//z has diverged and mu is not part of the Mandelbrot set
					//point a pixel using a color based on t's value
					image.setRGB(x, y, colors[t]);
				}
				else{ // t == tMax
					//it might be part of the set, so we'll assume that it is
					//at point mu plot a pixel using a color thats part of the mandelbrot set (aka black)
					image.setRGB(x, y, 0xFF000000);
				}
				zA += dComWidth;
			}
			zB -= dComHeight;
		}
	}
	
	public void interpolate(Color c1, Color c2, Color c3, Color c4){
		colors = new int[100];
		interpolate(c1, c2, 0);
		interpolate(c2, c3, 25);
		interpolate(c3, c4, 50);
		interpolate(c4, new Color(0xFFc90202), 75);
	}
	
	private void interpolate(Color color1, Color color2, int n){
		double color1Red = (color1.getRGB() >>> 16) & 0x000000FF;
		double color1Green = (color1.getRGB() >>> 8) & 0x000000FF;
		double color1Blue = color1.getRGB() & 0x000000FF;
		
		double color2Red = (color2.getRGB() >>> 16) & 0x000000FF;
		double color2Green = (color2.getRGB() >>> 8) & 0x000000FF;
		double color2Blue = color2.getRGB() & 0x000000FF;
		
		double deltaRed = (color2Red - color1Red)/25;
		double deltaGreen = (color2Green - color1Green)/25;
		double deltaBlue = (color2Blue - color1Blue)/25;
		
		for(int x = n; x < 25+n; x++){
			color1Red += deltaRed;
			color1Green += deltaGreen;
			color1Blue += deltaBlue;
			colors[x] = ((255 << 24) | ((int) Math.round(color1Red) << 16) | ((int) Math.round(color1Green) << 8) | (int) Math.round(color1Blue));
		}
	}
	
	private void convert(float percentX, float percentY){
		float percentXMIN = percentX - .5f;
		float percentXMAX = percentX + .5f;
		float percentYMAX = percentY - .5f;
		float percentYMIN = percentY + .5f;
		
		zoomIn(percentXMIN, percentXMAX, percentYMIN, percentYMAX);
	}
	
	private void zoomIn(float x1, float x2, float y1, float y2){
		complexWidth = Math.abs(rXMax - rXMin);
		complexHeight = Math.abs(iYMin - iYMax);
		float rmin = rXMin;
		float imax = iYMax;
			
		rXMin = rXMin + (complexWidth*x1);

		iYMax = iYMax - (complexHeight*y2);

		rXMax = rmin + (complexWidth*x2);	

		iYMin = imax - (complexHeight*y1);
	}
	
	public BufferedImage getCanvas(){
		return image;
	}

}
