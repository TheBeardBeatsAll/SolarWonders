package ie.dit;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

public class Sun 
{
	  PApplet parent;
	  PShape sun;
	  PImage texture;
	  float size, mass;
	
	  Sun(PApplet p, PImage texture) 
	  {
		  parent = p;
		  size = 50;
		  mass = 1000;
		  this.texture = texture;
		  parent.noStroke();
		  sun = parent.createShape(PConstants.SPHERE, size);
		  sun.setTexture(texture);
	  }

	  public void display() 
	  {
	      parent.shape(sun);
	  }
}
