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
	  float size, mass, period;
	  double theta, grav;
	  Sun sun;
	  PVector bug;

	  Planet(PApplet parent, Sun sun) 
	  {
		  this.sun = sun;
		  this.parent = parent;
		  width = parent.width;
		  height = parent.height;
	      location = new PVector(width/6f, width/6f, 0f);
	      velocity = new PVector(0f,0f,0f);
	      acceleration = new PVector(0f,0f,0f);
	      size = 30f;
	      grav = 6.67384 * Math.pow(10, -11);
	      mass = 100f;
	      planet = parent.createShape(PConstants.SPHERE, size);
		  planet.setStroke(255);
		  planet.setFill(parent.color(125, 125, 125));
		  period = 0.05f;
		  bug = new PVector(width/6f, width/6f, 0f);
	  }

	  public void acceleration_cal(int x, int y)
	  {
		  //if((location.x <= 0 && location.y > 0) || (location.x >= 0 && location.y < 0))
		  //{
			  adjacent = new PVector(location.x, 0, 0);
			  theta = Math.acos(adjacent.mag()/location.mag());
			  acceleration.x = (float) (x * grav * sun.mass * Math.cos(theta) / Math.pow(location.mag(), 2));
			  acceleration.y = (float) (y * grav * sun.mass * Math.sin(theta) / Math.pow(location.mag(), 2));
		  /*}
		  else if((location.x < 0 && location.y <= 0) || (location.x > 0 && location.y >= 0))
		  {
			  adjacent = new PVector(0, location.y, 0);
			  theta = Math.acos(adjacent.mag()/location.mag());
			  acceleration.y = (float) (y * grav * sun.mass * Math.cos(theta) / Math.pow(location.mag(), 2));
			  acceleration.x = (float) (x * grav * sun.mass * Math.sin(theta) / Math.pow(location.mag(), 2));
		  }*/
		  System.out.println("sin(theta): " + Math.sin(theta));
		  System.out.println("cos(theta): " + Math.cos(theta));
		  System.out.println("acc(x): " + acceleration.x);
		  System.out.println("acc(y): " + acceleration.y);
		  System.out.println("theta: " + theta);
		  System.out.println("mag: " + acceleration.mag());
		  acceleration.normalize();
		  acceleration.mult(period); 
	  }
	  
	  public void update() 
	  {
		  if(location.x <= 0 && location.y > 0)
		  {
			  acceleration_cal(1, -1);
		  }
		  else if(location.x < 0 && location.y <= 0)
		  {
			  acceleration_cal(1, -1);
		  }
		  else if(location.x >= 0 && location.y < 0)
		  {
			  acceleration_cal(1, -1);
		  }
		  else if(location.x > 0 && location.y >= 0)
		  {
			  acceleration_cal(1, -1);
		  }
	      velocity.add(acceleration);
	      location.add(velocity);
	  }

	  public void display() 
	  {
	      parent.pushMatrix();
	      parent.translate(location.x, location.y, location.z);
	      //System.out.println("X: " + location.x + ", Y: " + location.y + ", Z:" + location.z);
	      parent.shape(planet);
	      parent.popMatrix();
	      parent.stroke(255);
	      parent.noFill();
	      parent.ellipse(0, 0, bug.mag()*2, bug.mag()*2);
	  }
}
