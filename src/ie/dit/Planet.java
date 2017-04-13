package ie.dit;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Planet 
{
	  PApplet parent;
	  PVector location;
	  PVector velocity;
	  PVector acceleration;
	  PShape planet;
	  float width, height;
	  float size, mass;

	  Planet(PApplet p) 
	  {
		parent = p;
		width = p.width;
		height = p.height;
	    location = new PVector(width/8f,height/8f, 0f);
	    velocity = new PVector(0f,0f,0f);
	    acceleration = new PVector(0.01f,0.01f,0.01f);
	    size = 30;
	    mass = 100;
	    planet = parent.createShape(PConstants.SPHERE, size);
		planet.setStroke(255);
	  }

	  public void acceleration_cal()
	  {
		  
	  }
	  
	  public void update() 
	  {
		acceleration_cal();
	    velocity.add(acceleration);
	    location.add(velocity);
	  }

	  public void display() 
	  {
	    parent.pushMatrix();
	    parent.translate(location.x, location.y, location.z);
	    parent.shape(planet);
	    parent.popMatrix();
	  }
}
