package ie.dit;

import processing.core.PApplet;

public class Star {
	
	PApplet parent;
	float size, x, y, z;
	
	Star(float x, float y, float z, float size, PApplet p)
	{
		this.parent = p;
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;
	}
	
	public void display()
	{
		parent.pushMatrix();
		parent.translate(x, y, z);
		parent.noStroke();
		parent.fill(255);
		parent.sphere(size);
		parent.popMatrix();
	}

}
