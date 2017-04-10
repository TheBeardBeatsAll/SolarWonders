package ie.dit;

import processing.core.PApplet;
import processing.core.PImage;

public class SolarWonders extends PApplet
{
	Planet mover;
	Menu menu;
	PImage img;
	boolean check = false;

	
	public void setup()
	{
		smooth();
		img = loadImage("Planet_text1.png");
		// Make Mover object
		mover = new Planet(this);
		menu = new Menu(this, img);
		  
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
		menu.MenPlanets();
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


