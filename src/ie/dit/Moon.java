package ie.dit;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Moon 
{
	  PApplet parent;
	  PVector location;
	  PVector velocity;
	  PVector acceleration;
	  PVector adjacent;
	  PShape moon;
	  int x_start, z_start, x_accel, z_accel;
	  float width, height;
	  float size, mass, period;
	  double theta, grav, vel;
	  Planet planet;
	  PVector orbit;

	  Moon(PApplet parent, Planet planet) 
	  {
		  this.planet = planet;
		  this.parent = parent;
		  width = parent.width;
		  height = parent.height;
	      location = new PVector(width/12f, 0, width/12f);
	      velocity = new PVector(0f, 0f, 0f);
	      acceleration = new PVector(0f, 0f, 0f);
	      size = 10f;
	      grav = 6.67384 * Math.pow(10, -11);
	      mass = 10f;
	      moon = parent.createShape(PConstants.SPHERE, size);
	      moon.setStroke(255);
	      moon.setFill(parent.color(125, 125, 125));
		  period = 0.1f;
		  orbit = new PVector(location.x, location.y, location.z);
		  initialise_vel();
	  }
	  
	  public void initialise_vel()
	  {
		  acceleration_cal();
		  vel = Math.sqrt(acceleration.mag() * location.mag());
		  if(location.x <= 0 && location.z > 0)
		  {
			  x_start = z_start = 1;
		  }
		  else if(location.x >= 0 && location.z < 0)
		  {
			  x_start = z_start = -1;
		  }
		  else if(location.x < 0 && location.z <= 0)
		  {
			  x_start = -1;
			  z_start = 1;
		  }
		  else if(location.x > 0 && location.z >= 0)
		  {
			  x_start = 1;
			  z_start = -1;
		  }
		  velocity.x = (float) (x_start * vel * Math.cos(theta));
		  velocity.z = (float) (z_start * vel * Math.sin(theta));
	  }
	  
	  public void acceleration_cal()
	  {
		  if(location.x <= 0 && location.z > 0)
		  {
			  x_accel = 1;
			  z_accel = -1;
		  }
		  else if(location.x >= 0 && location.z < 0)
		  {
			  x_accel = -1;
			  z_accel = 1;
		  }
		  else if(location.x < 0 && location.z <= 0)
		  {
			  x_accel = z_accel = 1;
		  }
		  else if(location.x > 0 && location.z >= 0)
		  {
			  x_accel = z_accel = -1;
		  }
		  adjacent = new PVector(location.x, 0, 0);
		  theta = Math.acos(adjacent.mag()/location.mag());
		  acceleration.x = (float) (x_accel * grav * planet.mass * Math.cos(theta) / Math.pow(location.mag(), 2));
		  acceleration.z = (float) (z_accel * grav * planet.mass * Math.sin(theta) / Math.pow(location.mag(), 2));
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
	      parent.shape(moon);
	      parent.popMatrix();
	      parent.pushMatrix();
	      parent.rotateX((float) (Math.PI/2));
	      parent.stroke(255);
	      parent.noFill();
	      parent.ellipse(0, 0, orbit.mag()*2, orbit.mag()*2);
	      parent.popMatrix();
	  }
}
