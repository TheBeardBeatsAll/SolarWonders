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
		background(0);
		mover.update();
		mover.checkEdges();
		mover.display();
	}
	
	public void settings()
	{
		size(200, 200, P3D);
	}
	
    public static void main(String[] args)
    {
        PApplet.main("ie.dit.SolarWonders");
    }
}


