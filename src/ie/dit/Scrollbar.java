package ie.dit;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Scrollbar
{
	PApplet parent;
	int id, r;
	float width, height;
	PVector infoLocation, infoSize;
	float sPos, newSPos;			// x position of slider
	float sPosMin, sPosMax;
	boolean over, locked;
	float barWidth, barMinLoc, barMaxLoc, barYLoc;
	float zoom;
	
	Scrollbar(PApplet p, int id, float iLocX, float iLocY, float iSizeX, float iSizeY, float barYLoc)
	{
		parent = p;
		width = p.width;
		height = p.height;
		this.id = id;
		infoLocation = new PVector(iLocX, iLocY);
		infoSize = new PVector(iSizeX, iSizeY);
		infoLocation.x = iLocX;
		infoLocation.y = iLocY;
		infoSize.x = iSizeX;
		infoSize.y = iSizeY;
		barMinLoc = infoLocation.x + infoSize.x * .25f;
		barMaxLoc = infoLocation.x + infoSize.x * .75f;
		this.barYLoc = barYLoc;
		barWidth = barMaxLoc - barMinLoc;
		sPosMin = barMinLoc;
		sPosMax = barMaxLoc;
		sPos = barMinLoc + barWidth / 2;
		newSPos = sPos;
		r = 10;
	}
	
	// displaying scroll bars
	// barMinLoc = x position of the left most point of the line
	// barMaxLoc = x position of the right most point of the line
	// barYLoc = y position of the line
	// infoLocation and infoSize PVectors are equal to those of the same name in Planet ( info() )
	// sPos = x position if slider
	public void display()
	{
		parent.fill(247, 255, 28);
		parent.textAlign(PConstants.CENTER);
		parent.text("SIZE:", (infoLocation.x + infoSize.x / 2 + -(parent.screenX(0, 0))) * zoom,
				(infoLocation.y + infoSize.y * .1f + -(parent.screenY(0, 0))) * zoom);
		parent.text("DIST:", (infoLocation.x + infoSize.x / 2 + -(parent.screenX(0, 0))) * zoom,
				(infoLocation.y + infoSize.y * .3f + -(parent.screenY(0, 0))) * zoom);
		
		// displaying line
		parent.stroke(0, 157, 219);
		parent.strokeWeight(2);
		parent.line((barMinLoc + -(parent.screenX(0, 0))) * zoom, (barYLoc + -(parent.screenY(0, 0))) * zoom,
				(barMaxLoc + -(parent.screenX(0, 0))) * zoom, (barYLoc + -(parent.screenY(0, 0))) * zoom);
		parent.strokeWeight(2);
		parent.stroke(247, 255, 28);
		// if hovering over slider or slider is clicked, change ellipse fill
		if (over || locked)
		{
			parent.fill(247, 255, 28);
		}
		else
		{
			parent.fill(119, 112, 127);
		}
		// draw slider
		parent.ellipse((sPos + -(parent.screenX(0, 0))) * zoom, (barYLoc + -(parent.screenY(0, 0))) * zoom, r * zoom, r * zoom);
	}
	
	public void update(float focus)
	{
		// zoom = equivalent to zoom in Planet class (bottom of planet class)
		zoom = focus;
		if (overEvent())
		{
			over = true;
		}
		else
		{
			over = false;
		}
		
		if (parent.mousePressed && over)
		{
			locked = true;
		}
		
		if (!parent.mousePressed)
		{
			locked = false;
		}
		
		if (locked) 
		{
			// moving slider
			newSPos = parent.mouseX;
	    }
		// permanently changing slider position
		sPos = newSPos;
		
		// slider cannot extend past right most point of the line
		if (sPos > barMaxLoc)
		{
			sPos = barMaxLoc;
		}
		
		// slider cannot extend past left most point of the line
		if (sPos < barMinLoc)
		{
			sPos = barMinLoc;
		}
	}
	
	// used to draw sphere
	public float sliderStart()
	{
		return barMinLoc + barWidth / 2;
	}
	
	// if the mouse is over the slider ellipse
	public boolean overEvent()
	{
		if (parent.mouseX + -(parent.screenX(0, 0)) > sPos + -(parent.screenX(0, 0)) - r
					&& parent.mouseX + -(parent.screenX(0, 0)) < sPos + -(parent.screenX(0, 0)) + r
					&& parent.mouseY + -(parent.screenY(0, 0)) > barYLoc + -(parent.screenY(0, 0)) - r
					&& parent.mouseY + -(parent.screenY(0, 0)) < barYLoc + -(parent.screenY(0, 0)) + r)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
