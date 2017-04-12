package ie.dit;

import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet mover;
	Sun sun;
	public void setup()
	{
		smooth();
		// Make Mover object
		mover = new Planet(this);
		sun = new Sun(this);
	}
	
	public void draw()
	{
		Max();
	}
	
	public void Max()
	{
		background(0);
		sun.display();
		mover.update();
		mover.checkEdges();
		mover.display();
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


