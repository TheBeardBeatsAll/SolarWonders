package ie.dit;

import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet test;
	Sun sun;
	Boolean moon;
	
	public void setup()
	{
		smooth();
		sun = new Sun(this);
		test = new Planet(this, sun.mass);
		moon = true;
	}
	
	public void draw()
	{
		Max();
	}
	
	public void Max()
	{
		background(0);
		pushMatrix();
		translate(width/2f, height * 3f/4f, -width * 2f/3f);
		sun.display();
		test.update(sun.mass);
		if(moon)
		{
			test.add_moon();
			moon = false;
		}
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


