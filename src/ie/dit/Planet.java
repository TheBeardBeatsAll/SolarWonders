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
	  PVector infoLocation, infoSize, zoomPlusLoc, zoomMinusLoc;
	  ArrayList<Planet> sSystem;
	  ArrayList<Scrollbar> sBars;
	  int current;
	  int addZoom, checkAddZoom;
	  PVector infoEscLoc, infoEscSize;
	  float zoom, zoomSize;
	  float notFocused = 1;

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
	    addZoom = checkAddZoom;
	    infoEscLoc = new PVector(infoLocation.x*1.1f, infoLocation.y*1.01f);
	    infoEscSize = new PVector(width*.03f, width*.03f);
	    zoom = .5f;
	    zoomMinusLoc = new PVector((width / 2f) * .85f, height * .875f);
	    zoomPlusLoc = new PVector((width / 2f) * 1.05f, height * .875f);
	    zoomSize = width * 0.05f;
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
	    
	    // to determine the size of the planets
	    
	    /* accessing the first scroll bar of the currently selected planet
	       by multiplying by 2 as there are 2 scroll bars per planet */
    	Scrollbar s = sBars.get(current * 2);
    	
    	parent.strokeWeight(1);
    	
    	// drawing the sphere which can be changed in size
    	// size_w is set to an initial value in constructor
    	/* adding to size_w is the current slider position of the first scroll bar 
    	   minus the initial starting position of the slider, i.e. the mid-point of the scroll bar */
	    parent.sphere(size_w + (s.sPos - s.sliderStart()));
	    
	    
	    // to change distances between planets, i.e. location of current planet
	    
	    /* accessing the second scroll bar of the currently selected planet
	       by multiplying by 2 and adding 1 as there are 2 scroll bars per planet */
	    Scrollbar s2 = sBars.get(current * 2 + 1);
	    
	    // accessing current planet selected
	    Planet p = sSystem.get(current);
	    
	    /* because location couldn't be changed as easily as size, the following mechanism isn't
	       slows down the rate it is incremented */
	    // the planet's location value will only be incremented when the slider is selected
	    if (s2.locked)
	    {
	    	/* s2.sPos = slider position of second scroll bar, i.e. distance scroll bar
	    	 * s2.sliderStart() = mid-point of second scroll bar
	    	 * s2.sPos - s2.sliderStart() is divided by 10 to slow down increment rate*/
			p.location.x = p.location.x + (s2.sPos - s2.sliderStart()) / 10;
	    }
	    parent.popMatrix();
	  }
	  
	  // only called when the mouse is pressed
	  public void check(int selected)
	  {
		  parent.pushMatrix();
		  parent.translate(location.x, location.y, location.z);
		  
		  // current = position of planet in array list that is being checked 
		  current = selected;
		  // accessing current planet
		  Planet pCurrent = sSystem.get(current);
		  
		  // not really sure how this is working
		  // basically it checks where the mouse was when you clicked and if it is within the radius of a planet
		  // seems too vague to work accurately but it does somehow...
		  if (parent.mouseX > 0 - size_w + (parent.screenX(0, 0))
				  && parent.mouseX < 0 + size_w + (parent.screenX(0, 0)) 
				  && parent.mouseY > 0 - size_w + (parent.screenY(0, 0))
				  && parent.mouseY < 0 + size_w + (parent.screenY(0, 0)))
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
		  parent.popMatrix();
	  }
	  
	  public void focus()
	  {
		  Planet pCurrent = sSystem.get(current);
		  parent.camera(pCurrent.location.x, height / 2f, (float) ((height/2.0) / Math.tan(Math.PI / 6)) * zoom, 
				  pCurrent.location.x, height / 2f, 0, 0, 1, 0);
	  }
	  
	  public void adjustCamera()
	  {
		  Planet first = sSystem.get(0);
		  Planet last = sSystem.get(sSystem.size() - 1);
		  parent.camera((first.location.x + last.location.x) / 2, height / 2f, (float) ((height/2.0) / Math.tan(Math.PI / 6)) * notFocused, 
				  (first.location.x + last.location.x) / 2, height / 2f, 0, 0, 1, 0);
	  }
	  
	  public void zoomOut()
	  {
		  // minus button
		  parent.noStroke();
		  parent.fill(0, 157, 219);
		  parent.rect((zoomMinusLoc.x * .99f + -(parent.screenX(0, 0))) * notFocused,
				  (zoomMinusLoc.y * .99f + -(parent.screenY(0, 0))) * notFocused,
				  (zoomSize + zoomMinusLoc.x * 0.02f) * notFocused,
				  (zoomSize + zoomMinusLoc.y * 0.02f) * notFocused);
		  parent.strokeWeight(2);
		  parent.stroke(247, 255, 28);
		  parent.line((zoomMinusLoc.x + (zoomSize * .25f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomMinusLoc.y + (zoomSize * .5f) + -(parent.screenY(0, 0))) * notFocused,
				  (zoomMinusLoc.x + (zoomSize * .75f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomMinusLoc.y + (zoomSize * .5f) + -(parent.screenY(0, 0))) * notFocused);
		  if (parent.mouseX > zoomMinusLoc.x && parent.mouseX < zoomMinusLoc.x + zoomSize 
				  && parent.mouseY > zoomMinusLoc.y && parent.mouseY < zoomMinusLoc.y + zoomSize)
		  {
			  parent.fill(200);
			  if (parent.mousePressed)
			  {
				  notFocused = notFocused + 0.05f;
			  }
			  adjustCamera();
		  }
		  else
		  {
			  parent.fill(119, 112, 127);
		  }
		  parent.rect((zoomMinusLoc.x + -(parent.screenX(0, 0))) * notFocused,
				  (zoomMinusLoc.y + -(parent.screenY(0, 0))) * notFocused,
				  zoomSize * notFocused, zoomSize * notFocused);
	  }
	  
	  public void zoomIn()
	  {
		  // plus button
		  parent.noStroke();
		  parent.fill(0, 157, 219);
		  parent.rect((zoomPlusLoc.x * .99f + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y * .99f + -(parent.screenY(0, 0))) * notFocused,
				  (zoomSize + zoomPlusLoc.x * 0.02f) * notFocused,
				  (zoomSize + zoomPlusLoc.y * 0.02f) * notFocused);
		  parent.strokeWeight(2);
		  parent.stroke(247, 255, 28);
		  parent.line((zoomPlusLoc.x + (zoomSize * .25f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + (zoomSize * .5f) + -(parent.screenY(0, 0))) * notFocused,
				  (zoomPlusLoc.x + (zoomSize * .75f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + (zoomSize * .5f) + -(parent.screenY(0, 0))) * notFocused);
		  parent.line((zoomPlusLoc.x + (zoomSize * .5f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + (zoomSize * .25f) + -(parent.screenY(0, 0))) * notFocused,
				  (zoomPlusLoc.x + (zoomSize * .5f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + (zoomSize * .75f) + -(parent.screenY(0, 0))) * notFocused);
		  if (parent.mouseX > zoomPlusLoc.x && parent.mouseX < zoomPlusLoc.x + zoomSize 
				  && parent.mouseY > zoomPlusLoc.y && parent.mouseY < zoomPlusLoc.y + zoomSize)
		  {
			  if (parent.mousePressed && notFocused > 0.5)
			  {
				  notFocused = notFocused - 0.05f;
			  }
			  adjustCamera();
			  parent.fill(200);
		  }
		  else
		  {
			  parent.fill(119, 112, 127);
		  }
		  parent.rect((zoomPlusLoc.x + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + -(parent.screenY(0, 0))) * notFocused,
				  zoomSize * notFocused, zoomSize * notFocused);
	  }
	  
	  public float getZoom()
	  {
		  return zoom;
	  }
	  
	  public float getFocus()
	  {
		  return notFocused;
	  }
	  
	  public void info()
	  {
		  focus();
		  parent.noStroke();
		  parent.fill(0, 157, 219);
		  parent.rect((infoLocation.x / 2 + -(parent.screenX(0, 0))) * zoom, (infoLocation.y * .95f + -(parent.screenY(0, 0))) * zoom,
					(infoSize.x + infoLocation.x) * zoom, (infoSize.y + infoLocation.y * .1f) * zoom);
		  parent.strokeWeight(2);
		  parent.stroke(247, 255, 28);
		  parent.fill(119, 112, 127);
		  parent.rect((infoLocation.x + -(parent.screenX(0, 0))) * zoom, (infoLocation.y + -(parent.screenY(0, 0))) * zoom,
				  infoSize.x * zoom, infoSize.y * zoom);
		  parent.fill(247, 255, 28);
		  parent.textAlign(PConstants.CENTER);
		  parent.textSize((height / 40) * zoom);
		  parent.text("Planet No. " + current, ((infoLocation.x + infoSize.x / 2) + -(parent.screenX(0, 0))) * zoom, 
				  ((infoLocation.y + infoSize.y * .75f) + -(parent.screenY(0, 0))) * zoom);
		  if (parent.mouseX > infoEscLoc.x && parent.mouseX < infoEscLoc.x + infoEscSize.x 
				  && parent.mouseY > infoEscLoc.y && parent.mouseY < infoEscLoc.y + infoEscSize.y)
		  {
			  parent.stroke(255, 0, 0);
			  parent.fill(255, 0, 0);
			  parent.rect((infoEscLoc.x + -(parent.screenX(0, 0))) * zoom, (infoEscLoc.y + -(parent.screenY(0, 0))) * zoom,
					  infoEscSize.x * zoom, infoEscSize.y * zoom);
			  parent.stroke(255);
			  parent.line((infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0))) * zoom,
					  (infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0))) * zoom,
					  (infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0))) * zoom, 
					  (infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0))) * zoom);
			  parent.line((infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0))) * zoom,
					  (infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0))) * zoom,
					  (infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0))) * zoom, 
					  (infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0))) * zoom);
			  if (parent.mousePressed)
			  {
				  clicked = false;
			  }
		  }
		  else
		  {
			  parent.stroke(255, 0, 0);
			  parent.fill(255);
			  parent.rect((infoEscLoc.x + -(parent.screenX(0, 0))) * zoom, (infoEscLoc.y + -(parent.screenY(0, 0))) * zoom,
					  infoEscSize.x * zoom, infoEscSize.y * zoom);
			  parent.stroke(255, 0, 0);
			  parent.line((infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0))) * zoom,
					  (infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0))) * zoom,
					  (infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0))) * zoom, 
					  (infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0))) * zoom);
			  parent.line((infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0))) * zoom,
					  (infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0))) * zoom,
					  (infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0))) * zoom, 
					  (infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0))) * zoom);
		  }
		  
		  for (int i = sBars.size() - 1; i >= 0; i--)
		  {
			  Scrollbar s = sBars.get(i);
			  if (s.id == current)
			  {
				  s.update(zoom);
				  s.display();
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
