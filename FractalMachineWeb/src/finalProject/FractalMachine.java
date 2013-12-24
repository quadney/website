package finalProject;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class FractalMachine extends JFrame{
	//properties
	BufferedImage person;
	BufferedImage image;
	BufferedImage nightmareImage;
	BufferedImage transparentBG;
	BufferedImage toSave;
	private final int WIDTH, HEIGHT;
	CreatePanel createP;
	LSysPanel lsysP;
	FractalPanel fracP;
	ScenePanel sceneP;
	NightmarePanel nightmareP;

	public FractalMachine(int width, int height){
		WIDTH = width;
		HEIGHT = height;
		//setTitle("The Art Machine");
                setSize(WIDTH,HEIGHT);
                setResizable(false);
		addMenu();
	}
	
	public void addMenu(){
		
		//-----file menu-----
		JMenu file = new JMenu("File");
		
		JMenuItem newPerson = new JMenuItem("Create a New Person");
		newPerson.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                new Thread(new Runnable(){
                    public void run(){
                    	createP = new CreatePanel();
                    	setContentPane(createP);
                        
                        image = createP.image;
                        transparentBG = createP.transparentBG;
                        nightmareImage = createP.nightmareImage;
                        toSave = createP.image;
                    	pack();
                        
                    	validate();
                    }
                }).start();
            }
        });
        file.add(newPerson);
		
		JMenuItem save = new JMenuItem("Save Image");
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                try{
                    String outputFile = JOptionPane.showInputDialog("Name of File: ");
                    javax.imageio.ImageIO.write(toSave, "png", new File(outputFile + ".png"));
                } catch(IOException e){
                    JOptionPane.showMessageDialog(FractalMachine.this, "Error saving file", "oops!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }); 
        file.add(save);
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                System.exit(0);
            }
        });
        file.add(exit);
        
        //-----another menu Item-----
        JMenu create = new JMenu("Create a...");
		
		JMenuItem lsys = new JMenuItem("L-System Fractal");
		lsys.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                new Thread(new Runnable(){
                    public void run(){
                        lsysP = new LSysPanel(transparentBG);
                        setContentPane(lsysP);
                        toSave = lsysP.getSavedImage();
                    	pack();
                    	validate();
                    }
                }).start();
            }
        });
		create.add(lsys);
		
		JMenuItem mcrm = new JMenuItem("MCRM Fractal");
		mcrm.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                new Thread(new Runnable(){
                    public void run(){
                        fracP = new FractalPanel(transparentBG);
                        setContentPane(fracP);
                        toSave = fracP.getSavedImage();
                    	pack();
                    	validate();
                    }
                }).start();
            }
        });
		create.add(mcrm);
		
		JMenuItem scene = new JMenuItem("Create a Scene");
		scene.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                new Thread(new Runnable(){
                    public void run(){
                        sceneP = new ScenePanel(transparentBG);
                        setContentPane(sceneP);
                        toSave = sceneP.getSavedImage();
                    	pack();
                    	validate();
                    }
                }).start();
            }
        });
		create.add(scene);
		
		JMenuItem nightmare = new JMenuItem("Nightmare!");
		nightmare.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                new Thread(new Runnable(){
                    public void run(){
                        nightmareP = new NightmarePanel(nightmareImage);
                        setContentPane(nightmareP);
                        toSave = nightmareP.getSavedImage();
                    	pack();
                    	validate();
                    }
                }).start();
            }
        });
		create.add(nightmare);
        
        //-----attach menus to menu bar-----
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(create);
        this.setJMenuBar(menuBar);
	}

}
