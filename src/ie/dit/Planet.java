package ie.dit;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Planet extends Sun
{
	  PApplet parent;
	  PVector location;
	  PVector velocity;
	  PVector acceleration;
	  PVector adjacent;
	  PShape planet;
	  int x_start, z_start, x_accel, z_accel;
	  float x_coord, y_coord, z_coord;
	  float period;
	  double theta, grav, vel;
	  Sun sun;
	  PVector orbit;
	  ArrayList<Planet> moons = new ArrayList<Planet>();

	  Planet(PApplet parent, float sun_mass, float x_coord, float y_coord, float z_coord,
			  float size, float mass) 
	  {
		  super(parent);
		  this.parent = parent;
		  this.x_coord = x_coord;
		  this.y_coord = y_coord;
		  this.z_coord = z_coord;
		  this.size = size;
		  this.mass = mass;
		  init_planet();
		  init_vel(sun_mass);
	  }
	  
	  private void init_planet()
	  {
		  location = new PVector(x_coord, y_coord, z_coord);
	      velocity = new PVector(0f, 0f, 0f);
	      acceleration = new PVector(0f, 0f, 0f);
	      grav = 6.67384 * Math.pow(10, -11);
	      planet = parent.createShape(PConstants.SPHERE, size);
		  planet.setStroke(255);
		  planet.setFill(parent.color(125, 125, 125));
		  period = 0.1f;
		  orbit = new PVector(location.x, location.y, location.z);
	  }
	  
	  public void add_moon(float m_x, float m_y, float m_z, float m_size, float m_mass)
	  {
		  Planet p = new Planet(parent, this.mass, m_x, m_y, m_z, m_size, m_mass);
		  moons.add(p);
	  }
	  
	  private void init_vel(float mass)
	  {
		  acceleration_cal(mass);
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
	  
	  private void acceleration_cal(float mass)
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
		  acceleration.x = (float) (x_accel * grav * mass * Math.cos(theta) / Math.pow(location.mag(), 2));
		  acceleration.z = (float) (z_accel * grav * mass * Math.sin(theta) / Math.pow(location.mag(), 2));
		  acceleration.normalize();
		  acceleration.mult(period);
	  }
	  
	  public void update(float mass) 
	  {
		  acceleration_cal(mass);
	      velocity.add(acceleration);
	      location.add(velocity);
	  }
	  
	  public void display() 
	  {
	      parent.pushMatrix();
	      parent.translate(location.x, location.y, location.z);
	      parent.shape(planet);
	      for(Planet p : moons)
		  {
	    	  parent.rotateX((float) (Math.PI/4));
	    	  p.update(p.mass);
			  p.display();
		  }
	      parent.popMatrix();
	      parent.pushMatrix();
	      parent.rotateX((float) (Math.PI/2));
	      parent.stroke(255);
	      parent.noFill();
	      parent.ellipse(0, 0, location.mag()*2, location.mag()*2);
	      parent.popMatrix();
	  }
}
