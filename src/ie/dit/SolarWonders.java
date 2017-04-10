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
		//background(0);
		directionalLight(255, 255 , 255, 0, 5, -10);
		menu.MenPlanets();
	}
	
	public void settings()
	{
		size(700, 500, P3D);
	}
	
    public static void main(String[] args)
    {
        PApplet.main("ie.dit.SolarWonders");
    }
}


