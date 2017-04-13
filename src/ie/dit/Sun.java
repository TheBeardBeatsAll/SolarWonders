package ie.dit;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

public class Sun 
{
	  PApplet parent;
	  PShape sun;
	  float size, mass;
	
	  Sun(PApplet p) 
	  {
		parent = p;
		size = 100;
		mass = 1000;
		sun = parent.createShape(PConstants.SPHERE, size);
		sun.setStroke(255);
	  }

	  public void display() 
	  {
	    parent.shape(sun);
	  }
}
