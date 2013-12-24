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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

public class ScenePanel extends JPanel{
	private final int WIDTH = 1025;
	private final int HEIGHT = 550;
	ColorButton backColor;
	ColorButton plantC, treeC;
	Color plantColor = Color.GREEN;
	Color treeColor = Color.GREEN;
	Graphics2D g2d;
	CanvasPanel canvas;
	BufferedImage bg1, bg2, bg3, bg1icon, bg2icon, bg3icon, frac2, frac3, frac4, frac5;
	BufferedImage save;
	Color bgColor = Color.white;
	int numStems, alpha, dTheta, dR;
	JSlider stems, transP, maxRot, growthSeg;
	boolean plant, tree;
	LsysTurtle[] ft = new LsysTurtle[4];
	boolean[] f = new boolean[4];
	
	public ScenePanel(BufferedImage image){
		super(new BorderLayout());
		Dimension size = new Dimension(WIDTH, HEIGHT);
		Dimension panel = new Dimension(500, 500);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		getButtonImages();
		
		canvas = new CanvasPanel(image);
		add(canvas, BorderLayout.WEST);
		canvas.setMinimumSize(panel);
		canvas.setMaximumSize(panel);
		canvas.setPreferredSize(panel);
		populateLArray();
		
		JLabel title = new JLabel("Create a Scene",JLabel.CENTER);
		add(title, BorderLayout.NORTH);
		
		JButton reset = new JButton("Clear Drawing");
		reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	canvas.clearDrawn();
            }
        });
		add(reset, BorderLayout.SOUTH);
		
		JPanel rightPanel = new JPanel(new FlowLayout());
		rightPanel.setMinimumSize(panel);
		rightPanel.setMaximumSize(panel);
		rightPanel.setPreferredSize(panel);
		
		//-----Panel for setting a background image-----
		JPanel background = new JPanel(new GridLayout(1, 4));
		background.setMinimumSize(new Dimension(500, 110));
		background.setMaximumSize(new Dimension(500, 110));
		background.setPreferredSize(new Dimension(500, 110));
		background.setBorder(new EmptyBorder(0, 50, 10, 50));
		backColor = new ColorButton(bgColor);
		backColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(backColor, "Change the Background Color", backColor.getColor());
				if(c != null){
					backColor.setColor(c);
					bgColor = c;
					canvas.background(bgColor);
				}
			}
		});
		PictureButton backg1 = new PictureButton(bg1icon);
		backg1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	canvas.background(bg1);
            }
        });
		PictureButton backg2 = new PictureButton(bg2icon);
		backg2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	canvas.background(bg2);
            }
        });
		PictureButton backg3 = new PictureButton(bg3icon);
		backg3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	canvas.background(bg3);
            }
        });
		background.add(backColor);
		background.add(backg1);
		background.add(backg2);
		background.add(backg3);
		
	//-----Panel for creating the animated plant-----
		//-----create slider panel-----
		
		JPanel sliders = new JPanel(new GridLayout(4, 3));
		sliders.setMinimumSize(new Dimension(500, 200));
		sliders.setMaximumSize(new Dimension(500, 200));
		sliders.setPreferredSize(new Dimension(500, 200));
		sliders.setBorder(new EmptyBorder(0, 50, 0, 50));
				
		numStems = 20;
		stems = new JSlider(0, 30, numStems);
		stems.setMajorTickSpacing(5);
		stems.setPaintTicks(true);
		stems.setPaintLabels(true);
		stems.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					numStems = (int) source.getValue();
					canvas.drawPlantTrue(numStems, alpha, dTheta, dR, plantColor);
				}
			}
		});
		JLabel stemsLabel = new JLabel("Number of Stems: ", JLabel.LEFT);
		
		alpha = 5;
		transP = new JSlider(0, 10, alpha);
		transP.setMajorTickSpacing(1);
		transP.setPaintTicks(true);
		transP.setPaintLabels(true);
		transP.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					alpha = (int) source.getValue();
					canvas.drawPlantTrue(numStems, alpha, dTheta, dR, plantColor);
				}
			}
		});
		JLabel transLabel = new JLabel("Transmission Probability: ", JLabel.LEFT);
		
		dTheta = 5;
		maxRot = new JSlider(0, 10, dTheta);
		maxRot.setMajorTickSpacing(1);
		maxRot.setPaintTicks(true);
		maxRot.setPaintLabels(true);
		maxRot.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					dTheta = (int) source.getValue();
					canvas.drawPlantTrue(numStems, alpha, dTheta, dR, plantColor);
				}
			}
		});
		JLabel maxLabel = new JLabel("Maximum Rotation: ", JLabel.LEFT);
		
		dR = 1;
		growthSeg = new JSlider(0, 10, dR);
		growthSeg.setMajorTickSpacing(1);
		growthSeg.setPaintTicks(true);
		growthSeg.setPaintLabels(true);
		growthSeg.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					dR = (int) source.getValue();
					canvas.drawPlantTrue(numStems, alpha, dTheta, dR, plantColor);
				}
			}
		});
		JLabel growthLabel = new JLabel("Growth Segment: ", JLabel.LEFT);
		
		sliders.add(stemsLabel);
		sliders.add(transLabel);
		sliders.add(stems);
		sliders.add(transP);
		sliders.add(maxLabel);
		sliders.add(growthLabel);
		sliders.add(maxRot);
		sliders.add(growthSeg);
		
		
	//-----Panel for creating a tree-----
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.setMinimumSize(new Dimension(500, 110));
		buttonPanel.setMaximumSize(new Dimension(500, 110));
		buttonPanel.setPreferredSize(new Dimension(500, 110));
		buttonPanel.setBorder(new EmptyBorder(10, 50, 0, 50));
		
		PictureButton fractal2 = new PictureButton(frac2);
		fractal2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[0] = true;
            	canvas.setLTrueArray(f);
            }
        });
		PictureButton fractal3 = new PictureButton(frac3);
		fractal3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[1] = true;
            	canvas.setLTrueArray(f);
           }
        });
		PictureButton fractal4 = new PictureButton(frac4);
		fractal4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[2] = true;
            	canvas.setLTrueArray(f);
            }
        });
		PictureButton fractal5 = new PictureButton(frac5);
		fractal5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	for(int i = 0; i < f.length; i++){
            		f[i] = false;
            	}
            	f[3] = true;
            	canvas.setLTrueArray(f);
            }
        });
		
		buttonPanel.add(fractal2);
		buttonPanel.add(fractal3);
		buttonPanel.add(fractal4);
		buttonPanel.add(fractal5);
		
		plantC = new ColorButton(plantColor, 100, 20);
		plantC.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(plantC, "Change the first color", plantC.getColor());
				if(c != null){
					plantC.setColor(c);
					plantColor = c;
					canvas.drawPlantTrue(numStems, alpha, dTheta, dR, plantColor);
				}
			}
		});
		
		treeC = new ColorButton(treeColor, 100, 20);
		treeC.setSize(100, 20);
		treeC.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(treeC, "Change the first color", treeC.getColor());
				if(c != null){
					treeC.setColor(c);
					treeColor = c;
					canvas.setTreeColor(treeColor);
				}
			}
		});
		
		//add all that to the panel
		rightPanel.add(new JLabel("Background Image: ", JLabel.LEFT));
		rightPanel.add(background);
		rightPanel.add(new JLabel("Draw a Plant: ", JLabel.LEFT));
		rightPanel.add(plantC);
		rightPanel.add(sliders);
		rightPanel.add(new JLabel("Draw a Tree: ", JLabel.LEFT));
		rightPanel.add(treeC);
		rightPanel.add(buttonPanel);
		add(rightPanel, BorderLayout.EAST);
		
		canvas.drawPlantTrue(numStems, alpha, dTheta, dR, Color.BLACK);
	}
	
	public void populateLArray(){
		String[] array = {"F = F[-F]F[+F]F"};
    	ft[0] = new LsysTurtle(25.7, 2, "F", array);
    	String[] array2 = {"F = F[-F]F[+F][F]"};
    	ft[1] = new LsysTurtle(20, 2, "F", array2);
    	String[] array3 = {"X = F[-X]F[+X]-X", "F = FF"};
    	ft[2] = new LsysTurtle(20, 2, "X", array3);
    	String[] array4 = {"X = F[-X][+X]FX", "F = FF"};
    	ft[3] = new LsysTurtle(25.7, 2, "X", array4);
    	canvas.setLArray(ft);
	}
	
	public void getButtonImages(){
		try{
//			bg1 = ImageIO.read(new File("bg1.png"));
//			bg2 = ImageIO.read(new File("bg2.png"));
//			bg3 = ImageIO.read(new File("bg3.png"));
//			bg1icon = ImageIO.read(new File("bg1icon.png"));
//			bg2icon = ImageIO.read(new File("bg2icon.png"));
//			bg3icon = ImageIO.read(new File("bg3icon.png"));
//			frac2 = ImageIO.read(new File("frac2.png"));
//			frac3 = ImageIO.read(new File("frac3.png"));
//			frac4 = ImageIO.read(new File("frac4.png"));
//			frac5 = ImageIO.read(new File("frac5.png"));
			bg1 = ImageIO.read(ResourceLoader.load("bg1.png"));
			bg2 = ImageIO.read(ResourceLoader.load("bg2.png"));
			bg3 = ImageIO.read(ResourceLoader.load("bg3.png"));
			bg1icon = ImageIO.read(ResourceLoader.load("bg1icon.png"));
			bg2icon = ImageIO.read(ResourceLoader.load("bg2icon.png"));
			bg3icon = ImageIO.read(ResourceLoader.load("bg3icon.png"));
			frac2 = ImageIO.read(ResourceLoader.load("frac2.png"));
			frac3 = ImageIO.read(ResourceLoader.load("frac3.png"));
			frac4 = ImageIO.read(ResourceLoader.load("frac4.png"));
			frac5 = ImageIO.read(ResourceLoader.load("frac5.png"));
		}catch(Exception exception){
			//System.out.println("Something is wrong with an image");
		}
	}

	public BufferedImage getSavedImage(){
		return canvas.getCanvas();
	}
}
