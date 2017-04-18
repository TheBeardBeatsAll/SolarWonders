package ie.dit;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Planet extends Sun
{
	  PApplet parent;
	  PVector location, velocity, acceleration, adjacent;
	  PShape planet;
	  int x_start, z_start, x_accel, z_accel;
	  float x_coord, z_coord;
	  float period, parent_mass;
	  double major, minor;
	  double major_x, major_z, alpha;
	  double theta, grav, vel, eccentricity;
	  ArrayList<Planet> moons = new ArrayList<Planet>();

	  Planet(PApplet parent, float parent_mass, float x_coord, float z_coord,
			  float size, float mass, double eccentricity) 
	  {
		  super(parent);
		  this.parent = parent;
		  this.x_coord = x_coord;
		  this.z_coord = z_coord;
		  this.size = size;
		  this.mass = mass;
		  this.eccentricity = eccentricity;
		  this.parent_mass = parent_mass;
		  init_planet();
		  init_vel();
		  init_ellip();
	  }
	  
	  private void init_ellip()
	  {
		  major = location.mag() / (1 + eccentricity);
		  minor = major * Math.sqrt(1 - Math.pow(eccentricity, 2));
		  major_x = major * Math.cos(theta) * eccentricity;
		  major_z = major * Math.sin(theta) * eccentricity;
		  alpha = theta;
	  }
	  
	  private void init_planet()
	  {
		  location = new PVector(x_coord, 0, z_coord);
	      velocity = new PVector(0f, 0f, 0f);
	      acceleration = new PVector(0f, 0f, 0f);
	      grav = 6.67384 * Math.pow(10, -11);
	      planet = parent.createShape(PConstants.SPHERE, size);
		  planet.setStroke(255);
		  planet.setFill(parent.color(125, 125, 125));
		  period = 0.1f;
	  }
	  
	  public void add_moon(float m_x, float m_z, float m_size, float m_mass, double eccentricity)
	  {
		  Planet p = new Planet(parent, this.mass, m_x, m_z, m_size, m_mass, eccentricity);
		  moons.add(p);
	  }
	  
	  private void init_vel()
	  {
		  acceleration_cal();
		  vel = Math.sqrt(acceleration.mag() * location.mag()); //a = v*v/d --> v = sqrt(a*r)
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
		  velocity.x = (float) (x_start * vel * Math.sin(theta));
		  velocity.z = (float) (z_start * vel * Math.cos(theta));
	  }
	  
	  private void acceleration_cal()
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
		  //F = ma, F = (G * M1 * M2)/(d * d) --> a = (G * M1)/(d * d) 
		  acceleration.x = (float) (x_accel * grav * parent_mass * Math.cos(theta) / Math.pow(location.mag(), 2));
		  acceleration.z = (float) (z_accel * grav * parent_mass * Math.sin(theta) / Math.pow(location.mag(), 2));
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
	      for(Planet p : moons)
		  {
	    	  parent.rotateX((float) (Math.PI/4));
	    	  p.update();
			  p.display();
		  }
	      parent.popMatrix();
	      parent.pushMatrix();
	      parent.translate((float)(major_x), 0, (float)(major_z));
	      parent.rotateY((float) (-alpha));
	      parent.rotateX((float) (Math.PI/2));
	      parent.stroke(255);
	      parent.noFill();
	      parent.line(-(float) (major), 0, (float) (major), 0);
	      parent.stroke(255, 0, 0);;
	      parent.line(0,-(float) (minor),0 ,(float) (minor));
	      parent.ellipse(0, 0, (float) (major * 2f), (float) (minor * 2f));
	      parent.popMatrix();
	      System.out.println("loc(mag) : " + location.mag());
	      System.out.println("major : " + major);
	      System.out.println("minor : " + minor);
	      System.out.println("major_x : " + major_x);
	      System.out.println("major_z : " + major_z);
	      System.out.println("location(x) : " + location.x);
	      System.out.println("location(z) : " + location.z);
	      System.out.println("alpha : " + alpha);
	  }
}
