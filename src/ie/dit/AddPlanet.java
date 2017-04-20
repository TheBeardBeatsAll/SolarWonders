package ie.dit;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

public class AddPlanet 
{
	PApplet parent;
	float width, height;
	PVector location;
	PVector size;
	int clicked, clickOnce;
	ArrayList<Planet> sSystem;
	ArrayList<Scrollbar> sBars;
	PVector infoLocation, infoSize;
	
	AddPlanet(PApplet p, ArrayList<Planet> sSystem, ArrayList<Scrollbar> sBars)
	{
		parent = p;
		width = p.width;
		height = p.height;
		location = new PVector(width*.01f, width*.01f, 0f);
		size = new PVector(width*.1f, height*.075f);
		clicked = clickOnce;
		this.sSystem = sSystem;
		this.sBars = sBars;
		infoLocation = new PVector(width*.01f, width*.2f);
	    infoSize = new PVector(width*.15f, height*.5f);
	}
	
	public void display()
	{
		parent.stroke(0, 0, 255);
		parent.fill(255);
		parent.rect(location.x + -(parent.screenX(0, 0)), location.y + -(parent.screenY(0, 0)), size.x, size.y);
	}
	
	public void check()
	{
		if (parent.mouseX >= location.x && parent.mouseX <= location.x + size.x &&
				parent.mouseY >= location.y && parent.mouseY <= location.y + size.y)
		{
			clicked = 1;
		}
		else
		{
			clicked = 0;
			clickOnce = clicked;
		}
		
		if (clicked == 1 && clicked != clickOnce)
		{
			Planet planet = new Planet(parent, sSystem, sBars);
			sSystem.add(planet);
			Scrollbar scrollbar = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y);
			sBars.add(scrollbar);
			if (sSystem.size() >= 2)
			{
				Planet p = sSystem.get(sSystem.size() - 2);
				planet.location.x = p.location.x + height * .3f;
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