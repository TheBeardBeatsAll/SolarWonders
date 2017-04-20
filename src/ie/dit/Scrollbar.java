package ie.dit;

import java.util.ArrayList;

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
	
	Scrollbar(PApplet p, int id, float iLocX, float iLocY, float iSizeX, float iSizeY)
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
		barYLoc = infoLocation.y + infoSize.y * .2f;
		barWidth = barMaxLoc - barMinLoc;
		sPosMin = barMinLoc;
		sPosMax = barMaxLoc;
		sPos = barMinLoc + barWidth / 2;
		newSPos = sPos;
		r = 10;
	}
	
	public void display()
	{
		parent.text("Size:", infoLocation.x + infoSize.x / 2 + -(parent.screenX(0, 0)),
				infoLocation.y + infoSize.y * .1f + -(parent.screenY(0, 0)));
		parent.stroke(0, 0, 0);
		parent.strokeWeight(1);
		parent.line(barMinLoc + -(parent.screenX(0, 0)), barYLoc + -(parent.screenY(0, 0)),
				barMaxLoc + -(parent.screenX(0, 0)), barYLoc + -(parent.screenY(0, 0)));
		
		if (over || locked)
		{
			parent.fill(0);
			parent.ellipse(sPos + -(parent.screenX(0, 0)), barYLoc + -(parent.screenY(0, 0)), r, r);
		}
		else
		{
			parent.fill(255);
			parent.ellipse(sPos + -(parent.screenX(0, 0)), barYLoc + -(parent.screenY(0, 0)), r, r);
		}
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
