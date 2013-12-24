package finalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FractalPanel extends JPanel{
	private final int WIDTH = 1025;
	private final int HEIGHT = 550;
	BufferedImage person;
	BufferedImage mcrm1, mcrm2, mcrm3, mcrm4;
	int n;
	JSlider gens;
	ColorButton bgColor;
	Color background = Color.white;
	McrmPanel fractal;
	
	public FractalPanel(BufferedImage image){
		super(new BorderLayout());
		person = image.getSubimage(169, 108, 162, 284);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		Dimension panel = new Dimension(500, 500);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		getButtonImages();
		
		//add(new JLabel(new ImageIcon(person)), BorderLayout.WEST);
		fractal = new McrmPanel(person);
		fractal.setMinimumSize(panel);
		fractal.setMaximumSize(panel);
		fractal.setPreferredSize(panel);	
	
		JLabel title = new JLabel("MCRM Fractal",JLabel.CENTER);
		title.setBorder(new EmptyBorder(5,0,5,0));
		add(title, BorderLayout.NORTH);
	
		JPanel rightPanel = new JPanel(new FlowLayout());
		rightPanel.setMinimumSize(panel);
		rightPanel.setMaximumSize(panel);
		rightPanel.setPreferredSize(panel);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.setMinimumSize(new Dimension(500, 200));
		buttonPanel.setMaximumSize(new Dimension(500, 200));
		buttonPanel.setPreferredSize(new Dimension(500, 200));
		buttonPanel.setBorder(new EmptyBorder(100, 50, 0, 50));
		
		//-----create button panel-----
		PictureButton mcrmB1 = new PictureButton(mcrm1);
		mcrmB1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	AffineTransform[] afMatrix = getMatrixStuff("laa1.txt");
            	if(afMatrix == null)
            		System.out.println("It's null");
            	else
            		fractal.setAffineMatrix(afMatrix);
            }
        });
		PictureButton mcrmB2 = new PictureButton(mcrm2);
		mcrmB2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	AffineTransform[] afMatrix = getMatrixStuff("laa2.txt");
            	fractal.setAffineMatrix(afMatrix);
            }
        });
		PictureButton mcrmB3 = new PictureButton(mcrm3);
		mcrmB3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	AffineTransform[] afMatrix = getMatrixStuff("laa3.txt");
            	fractal.setAffineMatrix(afMatrix);
            }
        });
		PictureButton mcrmB4 = new PictureButton(mcrm4);
		mcrmB4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	AffineTransform[] afMatrix = getMatrixStuff("laa4.txt");
            	fractal.setAffineMatrix(afMatrix);
            }
        });
		
		
		buttonPanel.add(mcrmB1);
		buttonPanel.add(mcrmB2);
		buttonPanel.add(mcrmB3);
		buttonPanel.add(mcrmB4);
		
		//-----Slider Panel-----
		JPanel slider = new JPanel(new GridLayout(1, 2));
		slider.setMinimumSize(new Dimension(500, 100));
		slider.setMaximumSize(new Dimension(500, 100));
		slider.setPreferredSize(new Dimension(500, 100));
		slider.setBorder(new EmptyBorder(0, 50, 0, 50));
		
		n = 1;
		gens = new JSlider(0, 10, n);
		gens.setMajorTickSpacing(1);
		gens.setPaintTicks(true);
		gens.setPaintLabels(true);
		gens.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					n = (int) source.getValue();
					fractal.setNumberGenerations(n);
				}
			}
		});
		JLabel genLabel = new JLabel("Number of Generations: ", JLabel.LEFT);
		JLabel colorLabel = new JLabel("Background Color: ");
		
		bgColor = new ColorButton(background);
		bgColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(bgColor, "Change the background color", bgColor.getColor());
				if(c != null){
					bgColor.setColor(c);
					background = c;
					fractal.changeColor(background);
				}
			}
		});
		bgColor.setBorder(new EmptyBorder(0, 0, 0, 100));
		
		JPanel labels = new JPanel(new GridLayout(1, 2));
		labels.setMinimumSize(new Dimension(500, 50));
		labels.setMaximumSize(new Dimension(500, 50));
		labels.setPreferredSize(new Dimension(500, 50));
		labels.setBorder(new EmptyBorder(10, 50, 10, 50));
	
		labels.add(genLabel);
		labels.add(colorLabel);
		slider.add(gens);
		slider.add(bgColor);
		
		rightPanel.add(buttonPanel);
		rightPanel.add(labels);
		rightPanel.add(slider);
		
		add(rightPanel, BorderLayout.EAST);
		add(fractal, BorderLayout.WEST);
	}
	
	public void getButtonImages(){
		try{
//			mcrm1 = ImageIO.read(new File("mcrm1.png"));
//			mcrm2 = ImageIO.read(new File("mcrm2.png"));
//			mcrm3 = ImageIO.read(new File("mcrm3.png"));
//			mcrm4 = ImageIO.read(new File("mcrm4.png"));
			mcrm1 = ImageIO.read(ResourceLoader.load("mcrm1.png"));
			mcrm2 = ImageIO.read(ResourceLoader.load("mcrm2.png"));
			mcrm3 = ImageIO.read(ResourceLoader.load("mcrm3.png"));
			mcrm4 = ImageIO.read(ResourceLoader.load("mcrm4.png"));
		}catch(IOException exception){
			//JOptionPane.showMessageDialog(this, "The image is missing or corrupt!");
		}
	}
	
	public AffineTransform[] getMatrixStuff(String readText){
		AffineTransform[] afMatrix = null;
		try{
			//File file = new File(readText);
			int numMatrix = 0;
			Scanner scanner = new Scanner(ResourceLoader.load(readText));
			
			while(scanner.hasNextLine()){
				scanner.nextLine();
				numMatrix++;
			}
			//reset the scanner
			scanner.close();
			
			//array of matrices
			scanner = new Scanner(ResourceLoader.load(readText));
			afMatrix = new AffineTransform[numMatrix];
			
			for(int i = 0; i < numMatrix; i++){
				double a = scanner.nextDouble();
				double b = scanner.nextDouble();
				double c = scanner.nextDouble();
				double d = scanner.nextDouble();
				double e = scanner.nextDouble();
				double f = scanner.nextDouble();
				afMatrix[i] = new AffineTransform(a, c, b, d, e, f);
			}
			scanner.close();
			
			
		}catch(Exception exception){
			JOptionPane.showMessageDialog(this, "Something went wrong!");
		}
		
		return afMatrix;
	}
	
	public BufferedImage getSavedImage(){
		return fractal.getCanvas(); 
	}
}
