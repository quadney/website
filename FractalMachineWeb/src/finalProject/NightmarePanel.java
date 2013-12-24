package finalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class NightmarePanel extends JPanel{
	private final int WIDTH = 1025;
	private final int HEIGHT = 550;
	BufferedImage person;
	//BufferedImage image;
	Timer timer;
	ZoomedPanel display;
	BufferedImage mandelbrot, julia;
	ColorButton color1, color2, color3, color4;
	Color c1 = new Color(0xFF4a0b7c);
	Color c2 = new Color(0xFF40d6b8);
	Color c3 = new Color(0xFF0f9d0c);
	Color c4 = new Color(0xFFffff00);
	
	public NightmarePanel(BufferedImage image){
		super(new BorderLayout());
		person = image;
		Dimension size = new Dimension(WIDTH, HEIGHT);
		Dimension panel = new Dimension(500, 500);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		getButtonImages();
		
		display = new ZoomedPanel(person, true, c1, c2, c3, c4);
		add(display, BorderLayout.WEST);
		display.setMinimumSize(panel);
		display.setMaximumSize(panel);
		display.setPreferredSize(panel);
		
		JLabel title = new JLabel("NightMare!",JLabel.CENTER);
		title.setBorder(new EmptyBorder(5,0,5,0));
		add(title, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		buttonPanel.setMinimumSize(new Dimension(500, 120));
		buttonPanel.setMaximumSize(new Dimension(500, 120));
		buttonPanel.setPreferredSize(new Dimension(500, 120));
		buttonPanel.setBorder(new EmptyBorder(0, 50, 20, 250));
		
		PictureButton mandel = new PictureButton(mandelbrot);
		mandel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	display.interpolate(c1, c2, c3, c4);
            	display.initializeMandelbrot();
            	display.repaint();
            }
        });
		PictureButton julie = new PictureButton(julia);
		julie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	display.interpolate(c1, c2, c3, c4);
            	display.initializeJulia();
            	display.repaint();
            }
        });
		buttonPanel.add(mandel);
		buttonPanel.add(julie);
		
		JPanel colorPanel = new JPanel(new GridLayout(1, 4));
		colorPanel.setMinimumSize(new Dimension(500, 120));
		colorPanel.setMaximumSize(new Dimension(500, 120));
		colorPanel.setPreferredSize(new Dimension(500, 120));
		colorPanel.setBorder(new EmptyBorder(0, 50, 20, 50));
		
		color1 = new ColorButton(c1);
		color1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(color1, "Change the first color", color1.getColor());
				if(c != null){
					color1.setColor(c);
					c1 = c;
					display.interpolate(c1, c2, c3, c4);
					display.drawFractal();
					display.repaint();
				}
			}
		});
		color2 = new ColorButton(c2);
		color2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(color2, "Change the second color", color2.getColor());
				if(c != null){
					color2.setColor(c);
					c2 = c;
					display.interpolate(c1, c2, c3, c4);
					display.drawFractal();
					display.repaint();
				}
			}
		});
		color3 = new ColorButton(c3);
		color3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(color3, "Change the third color", color3.getColor());
				if(c != null){
					color3.setColor(c);
					c3 = c;
					display.interpolate(c1, c2, c3, c4);
					display.drawFractal();
					display.repaint();
				}
			}
		});
		color4 = new ColorButton(c4);
		color4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(color4, "Change the fourth color", color4.getColor());
				if(c != null){
					color4.setColor(c);
					c4 = c;
					display.interpolate(c1, c2, c3, c4);
					display.drawFractal();
					display.repaint();
				}
			}
		});
		colorPanel.add(color1);
		colorPanel.add(color2);
		colorPanel.add(color3);
		colorPanel.add(color4);
		
		JLabel todo = new JLabel("Click in the fractal to begin");
		todo.setBorder(new EmptyBorder(50, 150, 20, 150));
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//reset the fractal
				display.drawFractal();
				repaint();
			}
		});
		
		JPanel rightPanel = new JPanel(new FlowLayout());
		rightPanel.setMinimumSize(panel);
		rightPanel.setMaximumSize(panel);
		rightPanel.setPreferredSize(panel);
		rightPanel.add(todo);
		rightPanel.add(new JLabel("Choose a Fractal: "));
		rightPanel.add(buttonPanel);
		rightPanel.add(new JLabel("Choose the Colors: "));
		rightPanel.add(colorPanel);
		rightPanel.add(reset);
		
		add(rightPanel, BorderLayout.EAST);
		
	}
	
	public void getButtonImages(){
		try{
//			mandelbrot = ImageIO.read(new File("mandelbrot.png"));
//			julia = ImageIO.read(new File("julia.png"));
			mandelbrot = ImageIO.read(ResourceLoader.load("mandelbrot.png"));
			julia = ImageIO.read(ResourceLoader.load("julia.png"));
		}catch(Exception exception){
			//System.out.println("Something is wrong with an image");
		}
	}
	
	public BufferedImage getSavedImage(){
		return display.getCanvas();
	}

}
