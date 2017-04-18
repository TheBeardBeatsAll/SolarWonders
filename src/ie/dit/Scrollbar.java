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
	
	Scrollbar(PApplet p, int id)
	{
		parent = p;
		width = p.width;
		height = p.height;
		this.id = id;
		infoLocation = new PVector(0,0);
		infoSize = new PVector(0,0);
	}
	
	public void display(float iLocX, float iLocY, float iSizeX, float iSizeY)
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
		newSPos = sPos;
		sPosMin = barMinLoc;
		sPosMax = barMaxLoc;
		parent.stroke(0, 0, 0);
		parent.strokeWeight(1);
		parent.line(barMinLoc, barYLoc, barMaxLoc, barYLoc);
		parent.fill(255);
		parent.ellipse(sPos, barYLoc, 10, 10);
	}
	
	public void update()
	{
		
	}
	
	public boolean overEvent()
	{
		if (parent.mouseX > sPos - 10 && parent.mouseX < sPos + 10 &&
				parent.mouseY > barYLoc - 10 && parent.mouseY < barYLoc + 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
