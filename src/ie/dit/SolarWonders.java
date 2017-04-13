package ie.dit;

import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet test;
	Sun sun;
	
	public void setup()
	{
		smooth();
		sun = new Sun(this);
		test = new Planet(this, sun);
	}
	
	public void draw()
	{
		Max();
	}
	
	public void Max()
	{
		background(0);
		pushMatrix();
		translate(width/2f, height * 2f/3f, -width/2f);
		sun.display();
		test.update();
		test.display();
		popMatrix();
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


