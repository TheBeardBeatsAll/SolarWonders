package ie.dit;

import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet mover;
	public void setup()
	{
		smooth();
		// Make Mover object
		mover = new Planet(this);
	}
	
	public void draw()
	{
		//Max();
		Lorcan();
		//Finn();
	}
	
	public void mousePressed()
	{
		mover.check();
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


