package ie.dit;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Scrollbar
{
	PApplet parent;
	int id;
	float width, height;
	PVector infoLocation, infoSize;
	float sPos, newSPos;			// x position of slider
	float sPosMin, sPosMax;
	boolean over;					// is the mouse over the slider
	boolean locked;
	float barWidth, barMinLoc, barMaxLoc, barYLoc;
	boolean start = true;
	
	Scrollbar(PApplet p, int id)
	{
		parent = p;
		width = p.width;
		height = p.height;
		this.id = id;
		infoLocation = new PVector(0,0);
		infoSize = new PVector(0,0);
	}
	
	public void setValues(float iLocX, float iLocY, float iSizeX, float iSizeY)
	{
		infoLocation.x = iLocX;
		infoLocation.y = iLocY;
		infoSize.x = iSizeX;
		infoSize.y = iSizeY;
		barMinLoc = infoLocation.x + infoSize.x * .25f + -(parent.screenX(0, 0));
		barMaxLoc = infoLocation.x + infoSize.x * .75f + -(parent.screenX(0, 0));
		barYLoc = infoLocation.y + infoSize.y * .2f + -(parent.screenY(0, 0));
		barWidth = barMaxLoc - barMinLoc;
		sPos = barMinLoc + barWidth / 2;
		if (start)
		{
			newSPos = sPos;
		}
		sPosMin = barMinLoc;
		sPosMax = barMaxLoc;
		parent.stroke(0, 0, 0);
		parent.strokeWeight(1);
		parent.line(barMinLoc, barYLoc, barMaxLoc, barYLoc);
	}
	
	public void display()
	{
		//if (start)
		//{
			if (over || locked)
			{
				parent.fill(0);
				parent.ellipse(newSPos, barYLoc, 10, 10);
			}
			else
			{
				parent.fill(255);
				parent.ellipse(newSPos, barYLoc, 10, 10);
			}
			System.out.println("newSPos = "+ newSPos);
		//}
		/*
		else
		{
			if (over || locked)
			{
				parent.fill(0);
				parent.ellipse(sPos + -(parent.screenX(0, 0)), barYLoc, 10, 10);
			}
			else
			{
				parent.fill(255);
				parent.ellipse(sPos + -(parent.screenX(0, 0)), barYLoc, 10, 10);
			}
		}*/
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
			start = false;
		}
		
		if (!parent.mousePressed)
		{
			locked = false;
		}
		
		if (locked) 
		{
			newSPos = parent.mouseX + -(parent.screenX(0, 0));
	    }
		sPos = newSPos;
	}
	
	public boolean overEvent()
	{
		if (parent.mouseX + -(parent.screenX(0, 0)) > newSPos - 10
					&& parent.mouseX + -(parent.screenX(0, 0)) < newSPos + 10
					&& parent.mouseY + -(parent.screenY(0, 0)) > barYLoc - 10
					&& parent.mouseY + -(parent.screenY(0, 0)) < barYLoc + 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
