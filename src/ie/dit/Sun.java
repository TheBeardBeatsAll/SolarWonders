package ie.dit;

import processing.core.PApplet;
import processing.core.PShape;

public class Sun 
{
	  PApplet parent;
	  PShape sun;
	  float size, mass, width, height;
	
	  Sun(PApplet p) 
	  {
		parent = p;
		width = p.width;
		height = p.height;
		size = 100;
		mass = 1000;
		sun = parent.createShape(parent.SPHERE, size);
		sun.setStroke(255);
	  }

	  public void display() 
	  {
		parent.pushMatrix();
		parent.translate(width/2, height/2, 0);
	    parent.shape(sun);
	    parent.popMatrix();
	  }
}
