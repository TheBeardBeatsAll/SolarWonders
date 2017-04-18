package ie.dit;

import java.util.ArrayList;
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
	  int x_start, z_start, x_accel, z_accel;
	  float width, height;
	  float size, mass, period;
	  double theta, grav, vel;
	  Sun sun;
	  PVector orbit;
	  ArrayList<Moon> moons = new ArrayList<Moon>();

	  Planet(PApplet parent, Sun sun) 
	  {
		  this.sun = sun;
		  this.parent = parent;
		  width = parent.width;
		  height = parent.height;
	      location = new PVector(width/3f, 0, width/3f);
	      velocity = new PVector(0f, 0f, 0f);
	      acceleration = new PVector(0f, 0f, 0f);
	      size = 50f;
	      grav = 6.67384 * Math.pow(10, -11);
	      mass = 100f;
	      planet = parent.createShape(PConstants.SPHERE, size);
		  planet.setStroke(255);
		  planet.setFill(parent.color(125, 125, 125));
		  period = 0.1f;
		  orbit = new PVector(location.x, location.y, location.z);
		  initialise_vel();
		  Moon m = new Moon(parent, this);
		  moons.add(m);
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
		  acceleration.x = (float) (x_accel * grav * sun.mass * Math.cos(theta) / Math.pow(location.mag(), 2));
		  acceleration.z = (float) (z_accel * grav * sun.mass * Math.sin(theta) / Math.pow(location.mag(), 2));
		  acceleration.normalize();
		  acceleration.mult(period);
	  }
	  
	  public void update() 
	  {
		  acceleration_cal();
	      velocity.add(acceleration);
	      //location.add(velocity);
	  }
	  
	  public void display() 
	  {
	      parent.pushMatrix();
	      parent.translate(location.x, location.y, location.z);
	      parent.shape(planet);
	      for(Moon m : moons)
		  {
			  m.display();
		  }
	      parent.popMatrix();
	      parent.pushMatrix();
	      parent.rotateX((float) (Math.PI/2));
	      parent.stroke(255);
	      parent.noFill();
	      parent.ellipse(0, 0, orbit.mag()*2, orbit.mag()*2);
	      parent.popMatrix();
	  }
}
