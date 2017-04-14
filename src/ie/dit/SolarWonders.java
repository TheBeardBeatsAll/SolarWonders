package ie.dit;

import java.util.ArrayList;
import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet mover;
	AddPlanet button;
	ArrayList<Planet> solarSystem;
	public void setup()
	{
		smooth();
		// Make Mover object
		mover = new Planet(this);
		button = new AddPlanet(this);
		solarSystem = new ArrayList<Planet>();
	}
	
	public void draw()
	{
		//Max();
		Lorcan();
		//Finn();
	}
	
	public void mousePressed()
	{
		//mover.check();
	}
	
	public void Max()
	{
		background(0);
		mover.update();
		mover.checkEdges();
		mover.display();
	}
	
	public void Lorcan()
	{
		background(0);
		//mover.update();
		mover.checkEdges();
		mover.display();
		mover.info();
		button.display();
	}
	
	public void Finn()
	{
		
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


