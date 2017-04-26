package ie.dit;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

public class AddPlanet 
{
	PApplet parent;
	float width, height;
	PVector location;
	PVector size;
	ArrayList<Planet> sSystem;
	ArrayList<Scrollbar> sBars;
	PVector infoLocation, infoSize;
	float tSize, zoom, focus, cam;
	PFont font;
	
	AddPlanet(PApplet p, ArrayList<Planet> sSystem, ArrayList<Scrollbar> sBars)
	{
		parent = p;
		width = p.width;
		height = p.height;
		location = new PVector(width*.01f, width*.01f, 0f);
		size = new PVector(width*.1f, height*.075f);
		this.sSystem = sSystem;
		this.sBars = sBars;
		infoLocation = new PVector(width*.01f, width*.1f);
	    infoSize = new PVector(width*.15f, height*.5f);
	    tSize = height / 41;
	    font = p.createFont("Individigital.ttf", 25);
	}
	
	public void display()
	{
		// boolean to check if a planet has been clicked on
		boolean focused = false;
		
		// focus is equivalent to notFocused in Planet class
		// if there are no planet objects, focus is set to 1
		if (sSystem.size() == 0)
		{
			focus = 1;
		}
		// zoom is equivalent to zoom in Planet class
		// focus value is updated as often as notFocused value is changed with Planet class
		else
		{
			for (int i = sSystem.size() - 1; i >= 0; i--)
		    {
				Planet p = sSystem.get(i);
				zoom = p.getZoom();
				focus = p.getFocus();
				// if a planet is clicked, focused boolean is set to true to update add planet button and position
				if (p.clicked)
				{
					focused = true;
				}
		    }
		}
		
		// if a planet is clicked, adjust cam to zoom value from Planet class
		if (focused)
		{
			cam = zoom;
		}
		// if not clicked, adjust cam to notFocused value from Planet class
		else
		{
			cam = focus;
		}
		parent.textFont(font);
		parent.noStroke();
		parent.fill(0, 157, 219);
		// outer blue box
		parent.rect((location.x / 2 + -(parent.screenX(0, 0))) * cam, (location.y / 2 + -(parent.screenY(0, 0))) * cam,
				(size.x + location.x) * cam, (size.y + location.y) * cam);
		parent.strokeWeight(2);
		parent.stroke(247, 255, 28);
		if (parent.mouseX >= location.x && parent.mouseX <= location.x + size.x &&
				parent.mouseY >= location.y && parent.mouseY <= location.y + size.y)
		{
			parent.fill(200);
		}
		else
		{
			parent.fill(119, 112, 127);
		}
		// inner grey box
		parent.rect((location.x + -(parent.screenX(0, 0))) * cam, (location.y + -(parent.screenY(0, 0))) * cam,
				size.x * cam, size.y * cam);
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
		parent.textSize(tSize * cam);
		parent.fill(247, 255, 28);
		parent.text("Add Planet", (location.x + size.x / 2 + -(parent.screenX(0, 0))) * cam,
				(location.y + size.y / 2 + -(parent.screenY(0, 0))) * cam);
	}
	
	// gets called every time the mouse is pressed
	public void check()
	{
		// if mouse is within the box when clicked
		if (parent.mouseX >= location.x && parent.mouseX <= location.x + size.x &&
				parent.mouseY >= location.y && parent.mouseY <= location.y + size.y)
		{
			// create a new planet and add to sSystem/solarSystem arraylist
			Planet planet = new Planet(parent, sSystem, sBars);
			sSystem.add(planet);
			
			// create 2 new scroll bar objects and add to sBars/scrollbars arraylist
			// second parameter is its associated planet
			// final parameter is the y position of the scrollbar
			Scrollbar scrollbar = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y, 
					infoLocation.y + infoSize.y * .2f);
			Scrollbar scrollbar2 = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y, 
					infoLocation.y + infoSize.y * .4f);
			sBars.add(scrollbar);
			sBars.add(scrollbar2);
			// if there is more than 1 planet, change the x position of the most recently added planet
			if (sSystem.size() >= 2)
			{
				Planet p = sSystem.get(sSystem.size() - 2);
				planet.location.x = p.location.x + height * .5f;
				// instantly adjust camera, reduces screen flickering
				adjustCamera();
			}
		}
	}
	
	public void adjustCamera()
    {
    	if (sSystem.size() >= 2)
		{
	    	Planet first = sSystem.get(0);
			Planet last = sSystem.get(sSystem.size() - 1);
			parent.camera((first.location.x + last.location.x) / 2, height / 2f, (float) ((height/2.0) / Math.tan(Math.PI / 6)), 
					(first.location.x + last.location.x) / 2, height / 2f, 0, 0, 1, 0);
		}
    }
}