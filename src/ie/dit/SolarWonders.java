package ie.dit;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class SolarWonders extends PApplet
{
	Planet mover;
	Menu menu;
	PImage red, blue, coarse;
	PFont font;
	boolean check = false;

	
	public void setup()
	{
		smooth();
		red = loadImage("Planet_text1.png");
		blue = loadImage("blue.png");
		coarse = loadImage("coarse.jpg");
		font = createFont("font.ttf", 25);
		// Make Mover object
		mover = new Planet(this);
		menu = new Menu(this, red, blue, coarse, font);
		  
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
		menu.menu();
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


