package ie.dit;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
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
	  ArrayList<Scrollbar> sBars;
	  int current;
	  int addPos, checkAddPos;
	  PVector infoEscLoc, infoEscSize;

	  Planet(PApplet p, ArrayList<Planet> sSystem, ArrayList<Scrollbar> sBars)
	  {
		parent = p;
		width = p.width;
		height = p.height;
	    location = new PVector(width/2f,height/2f, 0f);
	    velocity = new PVector(0f,0f,0f);
	    acceleration = new PVector(0.01f,0.01f,0.01f);
	    topspeed = 4;
	    size_w = height / 10;
	    clicked = false;
	    infoLocation = new PVector(width*.01f, width*.1f);
	    infoSize = new PVector(width*.15f, height*.5f);
	    this.sSystem = sSystem;
	    this.sBars = sBars;
	    addPos = checkAddPos;
	    infoEscLoc = new PVector(infoLocation.x*1.1f, infoLocation.y*1.01f);
	    infoEscSize = new PVector(width*.03f, width*.03f);
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
	    // exception must be made for second planet in the ArrayList
	    if (current == 1)
    	{
	    	Scrollbar s = sBars.get(current + 1);
	    	parent.strokeWeight(1);
		    parent.sphere(size_w + (s.sPos - s.sliderStart()));
    	}
	    else
	    {
	    	Scrollbar s = sBars.get(current * 2);
	    	parent.strokeWeight(1);
		    parent.sphere(size_w + (s.sPos - s.sliderStart()));
	    }
	    // to change distances between planets
	    Scrollbar s2 = sBars.get(current * 2 + 1);
	    Planet p = sSystem.get(current);
	    if (s2.locked)
	    {
	    	addPos = 1;
	    }
	    else
	    {
	    	addPos = 0;
	    	checkAddPos = addPos;
	    }
	    
	    if (addPos == 1 && addPos != checkAddPos)
		{
			p.location.x = p.location.x + (s2.sPos - s2.sliderStart()) / 10;
		}
	    parent.popMatrix();
	  }
	  
	  public void check(int selected)
	  {
		  current = selected;
		  Planet pCurrent = sSystem.get(current);
		  if (parent.mouseX > location.x - size_w + (parent.screenX(0, 0))
				  && parent.mouseX < location.x + size_w + (parent.screenX(0, 0)) 
				  && parent.mouseY > location.y - size_w + (parent.screenY(0, 0))
				  && parent.mouseY < location.y + size_w + (parent.screenY(0, 0)))
		  {
			  if (pCurrent.clicked)
			  {
				  pCurrent.clicked = false;
			  }
			  else
			  {
				  for (int i = sSystem.size() - 1; i >= 0; i--)
				  {
					  Planet p = sSystem.get(i);
					  p.clicked = false;
				  }
				  pCurrent.clicked = true;
			  }
		  }
	  }
	  
	  public void info()
	  {
		  if (clicked)
		  {
			  parent.noStroke();
			  parent.fill(0, 157, 219);
			  parent.rect(infoLocation.x / 2 + -(parent.screenX(0, 0)), infoLocation.y * .95f + -(parent.screenY(0, 0)),
						infoSize.x + infoLocation.x, infoSize.y + infoLocation.y * .1f);
			  parent.strokeWeight(2);
			  parent.stroke(247, 255, 28);
			  parent.fill(119, 112, 127);
			  parent.rect(infoLocation.x + -(parent.screenX(0, 0)), infoLocation.y + -(parent.screenY(0, 0)), infoSize.x, infoSize.y);
			  parent.fill(247, 255, 28);
			  parent.textAlign(PConstants.CENTER);
			  parent.textSize(height / 40);
			  parent.text("Planet No. " + current, (infoLocation.x + infoSize.x / 2) + -(parent.screenX(0, 0)), 
					  (infoLocation.y + infoSize.y * .75f) + -(parent.screenY(0, 0)));
			  if (parent.mouseX > infoEscLoc.x && parent.mouseX < infoEscLoc.x + infoEscSize.x 
					  && parent.mouseY > infoEscLoc.y && parent.mouseY < infoEscLoc.y + infoEscSize.y)
			  {
				  parent.stroke(255, 0, 0);
				  parent.fill(255, 0, 0);
				  parent.rect(infoEscLoc.x + -(parent.screenX(0, 0)), infoEscLoc.y + -(parent.screenY(0, 0)), infoEscSize.x, infoEscSize.y);
				  parent.stroke(255);
				  parent.line(infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0)),
						  infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0)),
						  infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0)), 
						  infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0)));
				  parent.line(infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0)),
						  infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0)),
						  infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0)), 
						  infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0)));
				  if (parent.mousePressed)
				  {
					  clicked = false;
				  }
			  }
			  else
			  {
				  parent.stroke(255, 0, 0);
				  parent.fill(255);
				  parent.rect(infoEscLoc.x + -(parent.screenX(0, 0)), infoEscLoc.y + -(parent.screenY(0, 0)), infoEscSize.x, infoEscSize.y);
				  parent.stroke(255, 0, 0);
				  parent.line(infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0)),
						  infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0)),
						  infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0)), 
						  infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0)));
				  parent.line(infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0)),
						  infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0)),
						  infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0)), 
						  infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0)));
			  }
			  
			  for (int i = sBars.size() - 1; i >= 0; i--)
			  {
				  Scrollbar s = sBars.get(i);
				  if (s.id == current)
				  {
					  s.update();
					  s.display();
				  }
			  }
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
