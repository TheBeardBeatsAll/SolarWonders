package ie.dit;

import java.awt.Image;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class SolarWonders extends PApplet
{
 	Planet mover;
	Menu menu;
	Textures tex;
	PImage red, blue, coarse;
	PImage[] imgs = new PImage[15];
	PFont font;
	boolean check = false;

	
	public void setup()
	{
		smooth();

		imgs[0] = loadImage("red.png");
		imgs[1] = loadImage("blue.png");
		imgs[2] = loadImage("coarse.jpg");
		
		
		/*red = loadImage("red.png");
		blue = loadImage("blue.png");
		coarse = loadImage("coarse.jpg");*/
		
		font = createFont("font.ttf", 25);
		
		// Make Mover object
		mover = new Planet(this);
		menu = new Menu(this, red, blue, coarse, font);
		tex = new Textures(this, imgs);
	}
	
	public void draw()
	{
		//Max();
		Finn();
		//Lorcan();
	}
	
	public void Max()
	{
		background(0);
		mover.update();
		mover.checkEdges();
		mover.display();
	}
	
	public void Finn()
	{
		background(45, 20, 55);
		//menu.menu();
		tex.planet();
	}
	
	public void settings()
	{
		fullScreen(P3D);
	}
	
    public static void main(String[] args)
    {
        PApplet.main("ie.dit.SolarWonders");
    }
}


