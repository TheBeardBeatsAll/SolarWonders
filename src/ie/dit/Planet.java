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
	  int x_check, z_check;
	  float width, height;
	  float size, mass, period;
	  double theta, grav;
	  Sun sun;

	  Planet(PApplet parent, Sun sun) 
	  {
		  this.sun = sun;
		  this.parent = parent;
		  width = parent.width;
		  height = parent.height;
	      location = new PVector(width/6f, 0f, 0f);
	      velocity = new PVector(0f,0f,0f);
	      acceleration = new PVector(0f,0f,0f);
	      size = 30f;
	      grav = 6.67384 * Math.pow(10, -11);
	      mass = 100f;
	      planet = parent.createShape(PConstants.SPHERE, size);
		  planet.setStroke(255);
		  planet.setFill(parent.color(125, 125, 125));
		  period = 0.25f;
		  x_check = z_check = -1;
	  }

	  public void acceleration_cal()
	  {
		  adjacent = new PVector(location.x, 0, 0);
		  theta = Math.acos(adjacent.mag()/location.mag());
		  if(location.x <= 0 && location.z > 0 && z_check == 1)
		  {
			  z_check = -1;
		  }
		  else if(location.x < 0 && location.z <= 0 && x_check == -1)
		  {
			  x_check = 1;
		  }
		  else if(location.x >= 0 && location.z < 0 && z_check == -1)
		  {
			  z_check = 1;
		  }
		  else if(location.x > 0 && location.z >= 0 && x_check == 1)
		  {
			  x_check = -1;
		  }
		  acceleration.x = (float) (x_check * grav * sun.mass * Math.cos(theta) / Math.pow(location.mag(), 2));
		  acceleration.z = (float) (z_check * grav * sun.mass * Math.sin(theta) / Math.pow(location.mag(), 2));
		  acceleration.normalize();
		  acceleration.mult(period);  
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
