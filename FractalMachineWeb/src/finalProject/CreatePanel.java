package finalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class CreatePanel extends JPanel{
	private final int WIDTH = 1025;
	private final int HEIGHT = 550;
	Dimension size = new Dimension(WIDTH, HEIGHT);
	BufferedImage image;
	BufferedImage nightmareImage;
	BufferedImage transparentBG;
	Graphics2D g2d;
	JButton generate;
	Color eyeColor = Color.BLUE;
	Color bodyColor = new Color(0xFFF8C07C);
	Color hairColor = Color.BLACK;
	BufferedImage male, fatMale, female, fatFemale, happy, neutral, sad, angry, fro, tupe, shortHair, longHair, fmClothes, mClothes, ffClothes, fClothes;
	boolean m, fm, f, ff, hFace, nFace, sFace, aFace, fHair, tHair, sHair, lHair = false;	
	ColorButton changeSkin;
	ColorButton changeEyes;
	ColorButton changeHair;
	
	public CreatePanel(){
		super(new BorderLayout());
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		getButtonImages();
		
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		nightmareImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		transparentBG = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		add(new JLabel(new ImageIcon(image)), BorderLayout.WEST);
		
		generate = new JButton("Generate");
		generate.setMaximumSize(new Dimension(200, 25));
		generate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	new Thread(new Runnable(){
                    public void run(){
                    	g2d = image.createGraphics();
                    	background();
                    	generatePicture();
                    	g2d.dispose();
                    	
                    	g2d = transparentBG.createGraphics();
                    	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
                    	g2d.fillRect(0, 0, 500, 500);
                    	g2d.dispose();
                    	g2d = transparentBG.createGraphics();
                    	generatePicture();
                    	g2d.dispose();
                    	
                    	g2d = nightmareImage.createGraphics();
                    	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
                    	g2d.fillRect(0, 0, 500, 500);
                    	g2d.dispose();
                    	g2d = nightmareImage.createGraphics();
                    	generateNightmare();
                    	g2d.dispose();
                    	
                    	
                    }
                }).start();
            }
        });
		add(generate, BorderLayout.SOUTH);
		JLabel title = new JLabel("Create a Person",JLabel.CENTER);
		title.setBorder(new EmptyBorder(5,0,5,0));
		add(title, BorderLayout.NORTH);
		
		JPanel rightPanel = new JPanel(new GridLayout(3, 5));
		Dimension panel = new Dimension(500, 500);
		rightPanel.setMinimumSize(panel);
		rightPanel.setMaximumSize(panel);
		rightPanel.setPreferredSize(panel);
		rightPanel.setBorder(new EmptyBorder(100, 0, 100, 0));
		
	//-----Body Selection Options-----
		//body options
		PictureButton maleB = new PictureButton(male);
		maleB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	m = true;
            	fm = false;
            	f = false;
            	ff = false;
            }
        });
		PictureButton fatMaleB = new PictureButton(fatMale);
		fatMaleB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	m = false;
            	fm = true;
            	f = false;
            	ff = false;
            }
        });
		PictureButton femaleB = new PictureButton(female);
		femaleB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	m = false;
            	fm = false;
            	f = true;
            	ff = false;
            }
        });
		PictureButton fatFemaleB = new PictureButton(fatFemale);
		fatFemaleB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	m = false;
            	fm = false;
            	f = false;
            	ff = true;
            }
        });
		//skin color options
		changeSkin = new ColorButton(bodyColor);
		changeSkin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(changeSkin, "Change the Skin Color", changeSkin.getColor());
				if(c != null){
					changeSkin.setColor(c);
					bodyColor = c;
				}
			}
		});
		
		rightPanel.add(maleB);
		rightPanel.add(fatMaleB);
		rightPanel.add(femaleB);
		rightPanel.add(fatFemaleB);
		rightPanel.add(changeSkin);
		
	//-----Face Selection Options-----
		//face options
		PictureButton happyB = new PictureButton(happy);
		maleB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				hFace = true;
		        nFace = false;
		        sFace = false;
		        aFace = false;
			}
		});
		PictureButton neutralB = new PictureButton(neutral);
		neutralB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				hFace = false;
		        nFace = true;
		        sFace = false;
		        aFace = false;
		    }
		});
		PictureButton sadB = new PictureButton(sad);
		sadB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				hFace = false;
		        nFace = false;
		        sFace = true;
		        aFace = false;
			}
		});
		PictureButton angryB = new PictureButton(angry);
		angryB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				hFace = false;
		        nFace = false;
		        sFace = false;
		        aFace = true;
			}
		});
		changeEyes = new ColorButton(eyeColor);
		changeEyes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(changeEyes, "Change the Eye Color", changeEyes.getColor());
				if(c != null){
					changeEyes.setColor(c);
					eyeColor = c;
				}
			}
		});
		
		rightPanel.add(happyB);
		rightPanel.add(neutralB);
		rightPanel.add(sadB);
		rightPanel.add(angryB);
		rightPanel.add(changeEyes);
		
		//-----Hair Selection Panel-----
		//hair options
		PictureButton froB = new PictureButton(fro);
		froB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				fHair = true;
				tHair = false;
				sHair = false;
				lHair = false;
			}
		});
		PictureButton tupeB = new PictureButton(tupe);
		tupeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				fHair = false;
				tHair = true;
				sHair = false;
				lHair = false;
		    }
		});
		PictureButton shortHairB = new PictureButton(shortHair);
		shortHairB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				fHair = false;
				tHair = false;
				sHair = true;
				lHair = false;
			}
		});
		PictureButton longHairB = new PictureButton(longHair);
		longHairB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				fHair = false;
				tHair = false;
				sHair = false;
				lHair = true;
			}
		});
		changeHair = new ColorButton(hairColor);
		changeHair.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Color c = JColorChooser.showDialog(changeHair, "Change the Hair Color", changeHair.getColor());
				if(c != null){
					changeHair.setColor(c);
					hairColor = c;
				}
			}
		});
			
		rightPanel.add(froB);
		rightPanel.add(tupeB);
		rightPanel.add(shortHairB);
		rightPanel.add(longHairB);
		rightPanel.add(changeHair);
	
		add(rightPanel, BorderLayout.EAST);
	}

	public void getButtonImages(){
		try{
//			male = ImageIO.read(new File("male.jpg"));
//			fatMale = ImageIO.read(new File("fatMale.jpg"));
//			female = ImageIO.read(new File("female.jpg"));
//			fatFemale = ImageIO.read(new File("fatFemale.jpg"));
//			happy = ImageIO.read(new File("happy.jpg"));
//			neutral = ImageIO.read(new File("neutral.jpg"));
//			sad = ImageIO.read(new File("sad.jpg"));
//			angry = ImageIO.read(new File("angry.jpg"));
//			fro = ImageIO.read(new File("fro.jpg"));
//			tupe = ImageIO.read(new File("tupe.jpg"));
//			shortHair = ImageIO.read(new File("shortHair.jpg"));
//			longHair = ImageIO.read(new File("longHair.jpg"));
//			fmClothes = ImageIO.read(new File("fatMaleClothes.png"));
//			mClothes = ImageIO.read(new File("maleClothes.png"));
//			ffClothes = ImageIO.read(new File("fatFemaleClothes.png"));
//			fClothes = ImageIO.read(new File("FemaleClothes.png"));
			male = ImageIO.read(ResourceLoader.load("male.jpg"));
			fatMale = ImageIO.read(ResourceLoader.load("fatMale.jpg"));
			female = ImageIO.read(ResourceLoader.load("female.jpg"));
			fatFemale = ImageIO.read(ResourceLoader.load("fatFemale.jpg"));
			happy = ImageIO.read(ResourceLoader.load("happy.jpg"));
			neutral = ImageIO.read(ResourceLoader.load("neutral.jpg"));
			sad = ImageIO.read(ResourceLoader.load("sad.jpg"));
			angry = ImageIO.read(ResourceLoader.load("angry.jpg"));
			fro = ImageIO.read(ResourceLoader.load("fro.jpg"));
			tupe = ImageIO.read(ResourceLoader.load("tupe.jpg"));
			shortHair = ImageIO.read(ResourceLoader.load("shortHair.jpg"));
			longHair = ImageIO.read(ResourceLoader.load("longHair.jpg"));
			fmClothes = ImageIO.read(ResourceLoader.load("fatMaleClothes.png"));
			mClothes = ImageIO.read(ResourceLoader.load("maleClothes.png"));
			ffClothes = ImageIO.read(ResourceLoader.load("fatFemaleClothes.png"));
			fClothes = ImageIO.read(ResourceLoader.load("FemaleClothes.png"));
		}catch(Exception exception){
			//JOptionPane.showMessageDialog(this, "Something is wrong with an image!");
		}
	}
	
	public void generateMale(){
		g2d.setColor(bodyColor);
		//body
		g2d.fillRoundRect(218, 172, 63, 45, 20, 20);
		//feet/legs
		g2d.fillRoundRect(200, 367, 40, 11, 20, 20);
		g2d.fillRoundRect(260, 367, 40, 11, 20, 20);
		//arms
		g2d.rotate(0.401, 215, 169);
		g2d.fillOval(215, 169, 16, 66);
		g2d.rotate(-0.401, 215, 169);
		
		g2d.rotate(-0.26, 191, 224);
		g2d.fillOval(191, 224, 13, 55);
		g2d.rotate(0.26, 191, 224);
		
		g2d.rotate(-0.401, 270, 176);
		g2d.fillOval(270, 176, 16, 66);
		g2d.rotate(0.401, 270, 176);
		
		g2d.rotate(0.26, 296, 221);
		g2d.fillOval(296, 221, 13, 55);
		g2d.rotate(-0.26, 296, 221);
		//neck
		g2d.fillRoundRect(239, 155, 22, 23, 30, 30);
		//head
		g2d.fillRoundRect(234, 124, 32, 43, 20, 20);
		g2d.drawImage(mClothes, 0, 0, null);
	}
	
	public void generateFatMale(){
		g2d.setColor(bodyColor);
		//chest
		g2d.fillOval(206, 170, 88, 65);
		//arms
		g2d.translate(278, 183);
		g2d.rotate(-0.2965);
		g2d.fillOval(0, 0, 23, 73);
		g2d.rotate(0.2965);
		g2d.translate(-278, -183);
		
		g2d.rotate(0.244, 306, 235);
		g2d.fillOval(306,  235, 13, 50);
		g2d.rotate(-0.244, 306, 235);
		
		g2d.rotate(0.2965, 190, 180);
		g2d.fillOval(197, 180, 23, 73);
		g2d.rotate(-0.2965, 190, 180);
		
		g2d.rotate(-0.244, 180, 235);
		g2d.fillOval(180,  235, 13, 50);
		g2d.rotate(0.244, 180, 235);
		
		//legs
		g2d.fillRoundRect(211, 273, 32, 60, 60, 60);
		g2d.fillRoundRect(256, 273, 32, 60, 60, 60);
		g2d.fillOval(211, 316, 32, 63);
		g2d.fillOval(258, 316, 32, 63);
		g2d.fillRoundRect(199, 365, 40, 17, 30, 30);
		g2d.fillRoundRect(261, 365, 40, 17, 30, 30);
		//neck
		g2d.fillRoundRect(238, 154, 25, 23, 60, 60);
		//head
		g2d.fillOval(230, 124, 39, 46);
		//clothes
		g2d.drawImage(fmClothes, 1, 1, null);
	}
	
	public void generateFemale(){
		g2d.setColor(bodyColor);
		//chest
		g2d.fillRoundRect(232, 172, 38, 35, 30, 30);
		//legs
		g2d.fillRoundRect(225, 268, 24, 61, 30, 30);
		g2d.fillOval(226, 324, 17, 57);
		g2d.fillOval(227, 334, 19, 38);
		g2d.fillRoundRect(206, 376, 36, 11, 30, 30);
		
		g2d.fillRoundRect(253, 268, 24, 61, 30, 30);
		g2d.fillOval(259, 324, 17, 57);
		g2d.fillOval(256, 334, 19, 38);
		g2d.fillRoundRect(260, 376, 36, 11, 30, 30);
		//arms
		g2d.rotate(0.44, 227, 174);
		g2d.fillOval(227, 174, 12, 54);
		g2d.rotate(-0.44, 227, 174);
		
		g2d.rotate(-0.26, 206, 220);
		g2d.fillOval(206, 220, 11, 43);
		g2d.rotate(0.26, 206, 220);
		
		g2d.rotate(-0.44, 264, 178);
		g2d.fillOval(264, 178, 12, 54);
		g2d.rotate(0.44, 264, 178);
		
		g2d.rotate(0.26, 285, 218);
		g2d.fillOval(285, 218, 11, 43);
		g2d.rotate(-0.26, 285, 218);
		
		//neck
		g2d.fillRoundRect(242, 156, 17, 23, 30, 30);
		//head
		g2d.fillRoundRect(236, 129, 29, 38, 25, 25);
		
		g2d.drawImage(fClothes, 1, 1, null);
	}
	
	public void generateFatFemale(){
		g2d.setColor(bodyColor);
		//chest
		g2d.fillOval(226, 171, 48, 30);
		//arms
		g2d.translate(267, 190);
		g2d.rotate(-0.2965);
		g2d.fillOval(8, 0, 23, 73);
		g2d.rotate(0.2965);
		g2d.translate(-267, -190);
		
		g2d.translate(302, 235);
		g2d.rotate(0.244);
		g2d.fillOval(0,  0, 13, 50);
		g2d.rotate(-0.244);
		g2d.translate(-302, -235);
		
		g2d.translate(203, 183);
		g2d.rotate(0.2965);
		g2d.fillOval(0, 0, 23, 73);
		g2d.rotate(-0.2965);
		g2d.translate(-203, -183);
				
		g2d.translate(184, 238);
		g2d.rotate(-0.244);
		g2d.fillOval(0,  0, 13, 50);
		g2d.rotate(0.244);
		g2d.translate(-184, -238);
		
		//legs
		g2d.fillRoundRect(211, 273, 32, 60, 60, 60);
		g2d.fillRoundRect(256, 273, 32, 60, 60, 60);
		g2d.fillOval(211, 316, 32, 63);
		g2d.fillOval(258, 316, 32, 63);
		g2d.fillRoundRect(199, 365, 40, 17, 30, 30);
		g2d.fillRoundRect(261, 365, 40, 17, 30, 30);
		//neck
		g2d.fillRoundRect(238, 154, 25, 23, 60, 60);
		//head
		g2d.fillOval(230, 124, 39, 46);
		//clothes
		g2d.drawImage(ffClothes, 1, 1, null);
	}

	public void generateFace(){
		//generates the eyes and the nose
		//draw white part of eyes
		g2d.setColor(Color.WHITE);
		g2d.fillOval(240, 142, 6, 4);
		g2d.fillOval(254, 142, 6, 4);
		
		//draw iris using color given
		g2d.setColor(eyeColor);
		g2d.fillOval(241, 142, 3, 3);
		g2d.fillOval(255, 142, 3, 3);
		
		//draw pupil and other face features
		g2d.setColor(Color.BLACK);
		g2d.fillOval(242, 143, 1, 1);
		g2d.fillOval(256, 143, 1, 1);
		g2d.drawLine(250, 143, 250, 151);
		g2d.drawOval(248, 152, 1, 1);
		g2d.drawOval(251, 152, 1, 1);
	}
	
	public void generateHappy(){
		generateFace();
		//draw happy mouth
		g2d.setColor(Color.RED);
		g2d.drawArc(245, 156, 10, 6, 180, 180);
		
		//draw neutral eyebrows
		g2d.setColor(hairColor);
		g2d.drawLine(238, 143, 240, 141);
		g2d.drawLine(240, 141, 246, 141);
		g2d.drawLine(255, 141, 261, 141);
		g2d.drawLine(261, 141, 263, 143);
	}
	
	public void generateAngry(){
		generateFace();
		//draw angry eyebrows
		g2d.setColor(hairColor);
		g2d.drawLine(240, 142, 243, 141);
		g2d.drawLine(243, 141, 248, 144);
		g2d.drawLine(253, 144, 258, 141);
		g2d.drawLine(258, 141, 261, 142);
		
		//draw mouth
		g2d.setColor(Color.RED);
		g2d.drawArc(245, 156, 10, 6, 180, 180);
	}
	
	public void generateSad(){
		generateFace();
		//draw sad eyebrows
		g2d.setColor(hairColor);
		g2d.drawLine(239, 143, 240, 141);
		g2d.drawLine(240, 141, 246, 139);
		g2d.drawLine(255, 139, 261, 141);
		g2d.drawLine(261, 141, 262, 143);
		
		//draw sad mouth
		g2d.setColor(Color.RED);
		g2d.drawArc(245, 158, 10, 6, 0, 180);
	}
	
	public void generateNeutral(){
		generateFace();
		//draw whatever mouth
		g2d.setColor(Color.RED);
		g2d.drawLine(245, 158, 255, 158);
		
		//draw neutral eyebrows
		g2d.setColor(hairColor);
		g2d.drawLine(238, 143, 240, 141);
		g2d.drawLine(240, 141, 246, 141);
		g2d.drawLine(255, 141, 261, 141);
		g2d.drawLine(261, 141, 263, 143);
	}
	
	public void generateTupe(){
		g2d.setColor(hairColor);
		g2d.rotate(1.07, 250, 118);
		g2d.fillOval(250, 118, 15, 28);
		g2d.rotate(-1.07, 250, 118);
		
		g2d.rotate(-1.04, 250, 131);
		g2d.fillOval(250, 131, 12, 20);
		g2d.rotate(1.04, 250, 131);
	}
	
	public void generateFro(){
		
		g2d.setColor(hairColor);
		g2d.fillOval(228, 134, 7, 7);
		g2d.fillOval(229, 131, 7, 7);
		g2d.fillOval(231, 126, 7, 7);
		g2d.fillOval(233, 129, 7, 7);
		g2d.fillOval(234, 124, 7, 7);
		g2d.fillOval(237, 126, 7, 7);
		g2d.fillOval(237, 122, 7, 7);
		g2d.fillOval(241, 120, 7, 7);
		g2d.fillOval(242, 124, 7, 7);
		g2d.fillOval(245, 119, 7, 7);
		g2d.fillOval(246, 123, 7, 7);
		g2d.fillOval(249, 119, 7, 7);
		g2d.fillOval(250, 123, 7, 7);
		g2d.fillOval(253, 120, 7, 7);
		g2d.fillOval(254, 125, 7, 7);
		g2d.fillOval(257, 122, 7, 7);
		g2d.fillOval(259, 128, 7, 7);
		g2d.fillOval(261, 125, 7, 7);
		g2d.fillOval(262, 128, 7, 7);
		g2d.fillOval(264, 131, 7, 7);
		g2d.fillOval(266, 134, 7, 7);
	}
	
	public void generateShortHair(){
		generateTupe();
		g2d.fillRoundRect(228, 132, 11, 36, 10, 10);
		g2d.fillRoundRect(263, 133, 10, 35, 10, 10);
	}
	
	public void generateLongHair(){
		generateTupe();
		g2d.fillRoundRect(228, 132, 11, 56, 10, 10);
		g2d.fillRoundRect(263, 133, 10, 55, 10, 10);
	}
	
	public void background(){
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 500, 500);
	}
	
	public void generatePicture(){
		//draw the body first
		if(fm)
			generateFatMale();
		else if(f)
			generateFemale();
		else if(ff)
			generateFatFemale();
		else
			generateMale();//default
		
		//draw the face second
		if(nFace)
			generateNeutral();
		else if(sFace)
			generateSad();
		else if(aFace)
			generateAngry();
		else
			generateHappy();
		
		//draw the hair last
		if(fHair)
			generateFro();
		else if(sHair)
			generateShortHair();
		else if(lHair)
			generateLongHair();
		else
			generateTupe();
		repaint();
	}
	
	public void generateNightmare(){
		//draw the body first
		if(fm)
			generateFatMale();
		else if(f)
			generateFemale();
		else if(ff)
			generateFatFemale();
		else
			generateMale();//default
		
		generateSad();
		
		//draw the hair last
		if(fHair)
			generateFro();
		else if(sHair)
			generateShortHair();
		else if(lHair)
			generateLongHair();
		else
			generateTupe();
		repaint();
	}
}
