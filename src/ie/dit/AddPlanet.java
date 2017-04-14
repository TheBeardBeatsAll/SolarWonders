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
	
	AddPlanet(PApplet p, ArrayList<Planet> sSystem)
	{
		parent = p;
		width = p.width;
		height = p.height;
		location = new PVector(width*.01f, width*.01f, 0f);
		size = new PVector(width*.1f, height*.075f);
		clicked = clickOnce;
		this.sSystem = sSystem; 
	}
	
	public void display()
	{
		parent.stroke(0, 0, 255);
		parent.fill(255);
		parent.rect(location.x, location.y, size.x, size.y);
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
			Planet planet = new Planet(parent);
			sSystem.add(planet);
		}
	}
}
