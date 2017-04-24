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
	String[] fnames = {"earth", "blue", "brown", "magma", "water", "gas", "ice", "red", "coarse", "sun", "moon", "moon2", "moon3"};
	PImage[] imgs = new PImage[15];
	PFont font;
	boolean check = false;

	
	public void setup()
	{
		smooth();
		
		for(int i=0; i < fnames.length; i++)
		{
			imgs[i] = loadImage(fnames[i] + ".png");
		}
		
		
		font = createFont("font.ttf", 25);
		
		// Make Mover object
		mover = new Planet(this);
		menu = new Menu(this, imgs, font);
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
		//background(45, 20, 55);
		background(0);
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


