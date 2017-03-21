package ie.dit;

import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	float x,y,z; 
	
	public void setup()
	{
		background(0);
		x = width/2;
		y = height/2;
		z = 0;
	}
	
	public void draw()
	{
		translate(x,y,z);
		rotateX(PI/8);
		rotateY(PI/8);
		rectMode(CENTER);
		rect(0,0,100,100);
	}
	
	public void settings()
	{
		size(1024, 512, P3D);
	}
	
    public static void main(String[] args)
    {
        PApplet.main("ie.dit.SolarWonders");
    }
}
