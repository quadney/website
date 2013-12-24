package finalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class LSysPanel extends JPanel{
	private final int WIDTH = 1025;
	private final int HEIGHT = 550;
	BufferedImage person;
	BufferedImage toSave;
	LPersonPanel display;
	JSlider xCoord, yCoord, degrees, segLength, numGens;
	BufferedImage frac1, frac2, frac3, frac4, frac5, frac6, frac7, frac8;
	boolean[] f = new boolean[8];
	LsysTurtle[] ft = new LsysTurtle[8];
	int rXCoord, rYCoord, gens, inDegrees, relSegLength;
	
	public LSysPanel(BufferedImage im){
		super(new BorderLayout());
		person = im.getSubimage(169, 108, 162, 284);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		getButtonImages();
		generateLArray();
		
		JLabel title = new JLabel("L-System Generator",JLabel.CENTER);
		title.setBorder(new EmptyBorder(5,0,5,0));
		add(title, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel(new GridLayout(2, 4));
		Dimension panel = new Dimension(500, 500);
		buttonPanel.setMinimumSize(new Dimension(500, 200));
		buttonPanel.setMaximumSize(new Dimension(500, 200));
		buttonPanel.setPreferredSize(new Dimension(500, 200));
		buttonPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
		
		//-----create button panel-----
		PictureButton fractal1 = new PictureButton(frac1);
		fractal1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[0] = true;
            	display.setF(f);
            }
        });
		PictureButton fractal2 = new PictureButton(frac2);
		fractal2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[1] = true;
            	display.setF(f);
            }
        });
		PictureButton fractal3 = new PictureButton(frac3);
		fractal3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[2] = true;
            	display.setF(f);
            }
        });
		PictureButton fractal4 = new PictureButton(frac4);
		fractal4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[3] = true;
            	display.setF(f);
            }
        });
		PictureButton fractal5 = new PictureButton(frac5);
		fractal5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[4] = true;
            	display.setF(f);
            }
        });
		PictureButton fractal6 = new PictureButton(frac6);
		fractal6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[5] = true;
            	display.setF(f);
            }
        });
		PictureButton fractal7 = new PictureButton(frac7);
		fractal7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[6] = true;
            	display.setF(f); 
            }
        });
		PictureButton fractal8 = new PictureButton(frac8);
		fractal8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[7] = true;
            	display.setF(f);
            }
        });
		
		buttonPanel.add(fractal1);
		buttonPanel.add(fractal2);
		buttonPanel.add(fractal3);
		buttonPanel.add(fractal4);
		buttonPanel.add(fractal5);
		buttonPanel.add(fractal6);
		buttonPanel.add(fractal7);
		buttonPanel.add(fractal8);
		
		//-----create slider panel-----
		JPanel sliders = new JPanel(new GridLayout(6, 2));
		sliders.setMinimumSize(new Dimension(500, 275));
		sliders.setMaximumSize(new Dimension(500, 275));
		sliders.setPreferredSize(new Dimension(500, 275));
		sliders.setBorder(new EmptyBorder(0, 50, 0, 50));
		
		rXCoord = 250;
		xCoord = new JSlider(0, 500, rXCoord);
		xCoord.add(new JLabel("Relative y-Coordinate: "));
		xCoord.setMajorTickSpacing(100);
		xCoord.setPaintTicks(true);
		xCoord.setPaintLabels(true);
		xCoord.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					rXCoord = (int) source.getValue();
					display.setX(rXCoord);
				}
			}
		});
		JLabel xLabel = new JLabel("y-Coordinate: ", JLabel.LEFT);
		
		rYCoord = 250;
		yCoord = new JSlider(0, 500, rYCoord);
		yCoord.add(new JLabel("Relative x-Coordinate: "));
		yCoord.setMajorTickSpacing(100);
		yCoord.setPaintTicks(true);
		yCoord.setPaintLabels(true);
		yCoord.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					rYCoord = (int) source.getValue();
					display.setY(rYCoord);
				}
			}
		});
		JLabel yLabel = new JLabel("x-Coordinate: ", JLabel.LEFT);
		
		inDegrees = 0;
		degrees = new JSlider(0, 360, inDegrees);
		degrees.add(new JLabel("Initial Degrees: "));
		degrees.setMajorTickSpacing(90);
		degrees.setPaintTicks(true);
		degrees.setPaintLabels(true);
		degrees.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					inDegrees = (int) source.getValue();
					display.setDegrees(inDegrees);
				}
			}
		});
		JLabel degLabel = new JLabel("Initial Degrees: ", JLabel.LEFT);
		
		relSegLength = 50;
		segLength = new JSlider(0, 100, relSegLength);
		segLength.add(new JLabel("Relative Segment Length: "));
		segLength.setMajorTickSpacing(10);
		segLength.setPaintTicks(true);
		segLength.setPaintLabels(true);
		segLength.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					relSegLength = (int) source.getValue();
					display.setSegmentLength(relSegLength);
				}
			}
		});
		JLabel segLabel = new JLabel("Relative Segment Length: ", JLabel.LEFT);
		
		gens = 5;
		numGens = new JSlider(1, 5, gens);
		numGens.add(new JLabel("Number of Generations: "));
		numGens.setMajorTickSpacing(1);
		numGens.setPaintTicks(true);
		numGens.setPaintLabels(true);
		numGens.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					gens = (int) source.getValue();
					display.setGens(gens);
				}
			}
		});
		JLabel gensLabel = new JLabel("Number of Generations: ", JLabel.LEFT);
		
		sliders.add(xLabel);
		sliders.add(yLabel);
		sliders.add(xCoord);
		sliders.add(yCoord);
		sliders.add(degLabel);
		sliders.add(segLabel);
		sliders.add(degrees);
		sliders.add(segLength);
		sliders.add(gensLabel);
		sliders.add(new JLabel());
		sliders.add(numGens);
		
		JLabel lsysLabel = new JLabel("Select a Fractal: ", JLabel.LEFT);
		JPanel rightPanel = new JPanel(new FlowLayout());
		rightPanel.setMinimumSize(panel);
		rightPanel.setMaximumSize(panel);
		rightPanel.setPreferredSize(panel);
		rightPanel.add(lsysLabel);
		rightPanel.add(buttonPanel);
		rightPanel.add(sliders);
		
		display = new LPersonPanel(person, rXCoord, rYCoord, inDegrees, relSegLength, gens, ft);
		display.setMinimumSize(panel);
		display.setMaximumSize(panel);
		display.setPreferredSize(panel);
		
		add(display, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);
	}
	
	public void getButtonImages(){
		try{
//			frac1 = ImageIO.read(new File("frac1.png"));
//			frac2 = ImageIO.read(new File("frac2.png"));
//			frac3 = ImageIO.read(new File("frac3.png"));
//			frac4 = ImageIO.read(new File("frac4.png"));
//			frac5 = ImageIO.read(new File("frac5.png"));
//			frac6 = ImageIO.read(new File("frac6.png"));
//			frac7 = ImageIO.read(new File("frac7.png"));
//			frac8 = ImageIO.read(new File("frac8.png"));
			frac1 = ImageIO.read(ResourceLoader.load("frac1.png"));
			frac2 = ImageIO.read(ResourceLoader.load("frac2.png"));
			frac3 = ImageIO.read(ResourceLoader.load("frac3.png"));
			frac4 = ImageIO.read(ResourceLoader.load("frac4.png"));
			frac5 = ImageIO.read(ResourceLoader.load("frac5.png"));
			frac6 = ImageIO.read(ResourceLoader.load("frac6.png"));
			frac7 = ImageIO.read(ResourceLoader.load("frac7.png"));
			frac8 = ImageIO.read(ResourceLoader.load("frac8.png"));
		}catch(Exception exception){
			//System.out.println("Something is wrong with an image");
		}
	}

	public void generateLArray(){
		String[] array = {"F = FF[-F][F][+F]"};
    	ft[0] = new LsysTurtle(60, 2, "FF", array);
    	String[] array1 = {"F = F[-F]F[+F]F"};
    	ft[1] = new LsysTurtle(25.7, 2, "F", array1);
    	String[] array2 = {"F = F[-F]F[+F][F]"};
    	ft[2] = new LsysTurtle(20, 2, "F", array2);
    	String[] array3 = {"X = F[-X]F[+X]-X", "F = FF"};
    	ft[3] = new LsysTurtle(20, 2, "X", array3);
    	String[] array4 = {"X = F[-X][+X]FX", "F = FF"};
    	ft[4] = new LsysTurtle(25.7, 2, "X", array4);
    	String[] array5 = {"F = F+FF-FF-F-F+F+FF-F-F+F+FF+FF-F"};
    	ft[5] = new LsysTurtle(90, 2, "F+F+F+F", array5);
    	String[] array6 = {"F = F+F-F-F+F"};
    	ft[6] = new LsysTurtle(90, 2, "-F", array6);
    	String[] array7 = {"F = F--F--F--ff", "f = ff"};
    	ft[7] = new LsysTurtle(60, 2, "F--F--F", array7);
	}
	
	public BufferedImage getSavedImage(){
		return display.getCanvas();
	}
}
