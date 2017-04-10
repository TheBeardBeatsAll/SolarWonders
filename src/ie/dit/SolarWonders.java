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
		Max();
		Lorcan();
		Finn();
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


