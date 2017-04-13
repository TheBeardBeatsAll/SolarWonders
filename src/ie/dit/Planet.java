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
	  PVector adjacent;
	  PShape planet;
	  float width, height;
	  float size, mass;
	  double theta, grav;
	  Sun sun;

	  Planet(PApplet parent, Sun sun) 
	  {
		  this.sun = sun;
		  this.parent = parent;
		  width = parent.width;
		  height = parent.height;
	      location = new PVector(width/8f,height/8f, 0f);
	      velocity = new PVector(0f,0f,0f);
	      acceleration = new PVector(0.01f,0.01f,0.01f);
	      size = 30;
	      grav = 6.67384 * Math.pow(10, -11);
	      mass = 100;
	      planet = parent.createShape(PConstants.SPHERE, size);
		  planet.setStroke(255);
	  }

	  public void acceleration_cal()
	  {
		  adjacent = new PVector(location.x, 0, 0);
		  theta = Math.acos(adjacent.mag()/location.mag());
		  
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
