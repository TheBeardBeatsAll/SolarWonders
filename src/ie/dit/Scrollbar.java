package ie.dit;

import processing.core.PApplet;
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
	
	Scrollbar(PApplet parent, int id, float iLocX, float iLocY, float iSizeX, 
			float iSizeY, float barYLoc)
	{
		this.parent = parent;
		width = parent.width;
		height = parent.height;
		this.id = id;
		infoLocation = new PVector(iLocX, iLocY);
		infoSize = new PVector(iSizeX, iSizeY);
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
	
	public void display()
	{		
		parent.fill(247, 255, 28);
		// displaying line
		parent.stroke(0, 157, 219);
		parent.strokeWeight(2);
		parent.line(barMinLoc, barYLoc,
				barMaxLoc, barYLoc);
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
		parent.ellipse(sPos, barYLoc, r, r);
	}
	
	public void update()
	{
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
			newSPos = parent.mouseX;
	    }
		sPos = newSPos;
		
		if (sPos > barMaxLoc)
		{
			sPos = barMaxLoc;
		}
		
		if (sPos < barMinLoc)
		{
			sPos = barMinLoc;
		}
	}
	
	public float sliderStart()
	{
		return barMinLoc + barWidth / 2;
	}
	
	public boolean overEvent()
	{
		if (parent.mouseX > sPos- r
					&& parent.mouseX < sPos + r
					&& parent.mouseY > barYLoc - r
					&& parent.mouseY < barYLoc + r)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}