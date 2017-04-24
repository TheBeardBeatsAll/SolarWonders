package ie.dit;

import java.util.ArrayList;

import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet test;
	Sun sun;
	Boolean moon;
	ArrayList<Planet> planets = new ArrayList<Planet>();
	
	public void setup()
	{
		smooth();
		sun = new Sun(this);
		test = new Planet(this, sun.mass, width/3f, width/3f, -width/3f, 30, 100, 0.4);
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
		test.update();
		if(moon)
		{
			//test.add_moon(width/12f, width/12f, 0, 10, 10, 0);
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


