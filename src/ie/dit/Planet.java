package ie.dit;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Planet 
{
	  PApplet parent;
	  PVector location;
	  PVector velocity;
	  // Acceleration is the key!
	  PVector acceleration;
	  // The variable, topspeed, will limit the magnitude of velocity.
	  float topspeed, width, height;
	  float size_w;
	  boolean clicked;
	  PVector infoLocation, infoSize;
	  ArrayList<Planet> sSystem;
	  int current;

	  Planet(PApplet p, ArrayList<Planet> sSystem)
	  {
		parent = p;
		width = p.width;
		height = p.height;
	    location = new PVector(width/2f,height/2f, 0f);
	    velocity = new PVector(0f,0f,0f);
	    acceleration = new PVector(0.01f,0.01f,0.01f);
	    topspeed = 4;
	    size_w = 50;
	    clicked = false;
	    infoLocation = new PVector(width*.01f, width*.2f);
	    infoSize = new PVector(width*.15f, height*.5f);
	    this.sSystem = sSystem;
	  }

	  public void update() 
	  {
	    // Velocity change by acceleration and is limited by topspeed.
	    velocity.add(acceleration);
	    velocity.limit(topspeed);
	    location.add(velocity);
	  }

	  public void display() 
	  {
	    parent.pushMatrix();
	    parent.translate(location.x, location.y, location.z);
	    parent.noFill();
	    parent.stroke(255);
	    parent.sphere(size_w);
	    parent.popMatrix();
	  }
	  
	  public void check(int selected)
	  {
		  if (parent.mouseX > location.x - size_w + (parent.screenX(0, 0))
				  && parent.mouseX < location.x + size_w + (parent.screenX(0, 0)) 
				  && parent.mouseY > location.y - size_w + (parent.screenY(0, 0))
				  && parent.mouseY < location.y + size_w + (parent.screenY(0, 0)))
		  {
			  if (clicked)
			  {
				  clicked = false;
			  }
			  else
			  {
				  clicked = true;
				  current = selected;
			  }
		  }
	  }
	  
	  public void info()
	  {
		  if (clicked)
		  {
			  parent.stroke(0, 0, 255);
			  parent.fill(255);
			  parent.rect(infoLocation.x + -(parent.screenX(0, 0)), infoLocation.y + -(parent.screenY(0, 0)), infoSize.x, infoSize.y);
			  parent.fill(0);
			  parent.textSize(10);
			  parent.text("Planet No. " + current, (infoLocation.x + infoSize.x / 3) + -(parent.screenX(0, 0)), 
					  (infoLocation.y + infoSize.y / 2) + -(parent.screenY(0, 0)));
		  }
	  }
	  
	  public void checkEdges() 
	  {

	    if (location.x + size_w >width || location.x  - size_w< 0) {
	      velocity.x = -velocity.x;
	      acceleration.x = -acceleration.x;
	    }
	    
	    if (location.y  + size_w > height || location.y - size_w < 0) {
	      velocity.y = -velocity.y;
	      acceleration.y = -acceleration.y;
	    }

	    if (location.z  + size_w > 50 || location.z - size_w < -50) {
		      velocity.z = -velocity.z;
		      acceleration.z = -acceleration.z;
		    }
	  }
}
