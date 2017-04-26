package ie.dit;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

public class Sun 
{
	  ArrayList<Planet> sSystem;
	  PApplet parent;
	  PShape sun;
	  PImage texture;
	  float width, height;	 
	  float size_s, mass, rot;
	  
	  Sun(PApplet p, PImage texture, ArrayList<Planet> sSystem) 
	  {
	      this.sSystem = sSystem;
		  parent = p;
		  size_s = 70;
		  mass = 1000;
		  rot = 0;
		  this.texture = texture;
		  parent.noStroke();
		  sun = parent.createShape(PConstants.SPHERE, size_s);
		  sun.setTexture(texture);
		  
		  width = parent.width;
		  height = parent.height;
	  }

	  public void display() 
	  {
		  parent.pushMatrix();
	      parent.rotateY(rot);
	      parent.shape(sun);
	      parent.popMatrix();
	      rot += 0.001;
	  }  
}
