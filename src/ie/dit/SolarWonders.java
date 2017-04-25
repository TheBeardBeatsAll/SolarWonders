/* 
Things to do:
	- camera:
		-> when you click on a planet, focus on that planet
		-> ability to zoom in and out when not focused on a planet
 	- make it look nicer....done
 	- add names
*/
package ie.dit;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;

public class SolarWonders extends PApplet
{
	Planet mover;
	AddPlanet button;
	public ArrayList<Planet> solarSystem;
	public ArrayList<Scrollbar> scrollers;
	PFont font;
	public void setup()
	{
		smooth();
		// Make Mover object
		//mover = new Planet(this);
		solarSystem = new ArrayList<Planet>();
		scrollers = new ArrayList<Scrollbar>();
		button = new AddPlanet(this, solarSystem, scrollers);
		font = createFont("Calibri", 60);
	}
	
	public void draw()
	{
		//Max();
		Lorcan();
		//Finn();
	}
	
	public void mousePressed()
	{
		// checks if the add planet button is pressed
		button.check();
		
		// when there is at least 1 planet created
		if (solarSystem.size() > 0)
		{
			for (int i = solarSystem.size() - 1; i >= 0; i--)
		    {
				// continuously checking if a planet has been clicked
				Planet p = solarSystem.get(i);
				p.check(i);
		    }
		}
	}
	
	public void Max()
	{
		background(0);
		mover.update();
		mover.checkEdges();
		mover.display();
	}
	
	public void Lorcan()
	{
		int count = 0;
		background(0);
		
		// display add planet button
		button.display();
		
		// display created planets
		for (int i = solarSystem.size() - 1; i >= 0; i--)
	    {
			Planet p = solarSystem.get(i);
			p.display();
	    }
		
		// checks which planet has been clicked
		for (int i = solarSystem.size() - 1; i >= 0; i--)
	    {
			Planet p = solarSystem.get(i);
			if (p.clicked)
			{
				// the info box of the clicked planet is displayed
				p.info();
				System.out.println("Planet " + i + " is clicked");
			}
			else
			{
				// counter increments if a planet's clicked value is false
				count++;
			}
			
			// if all planets' clicked value is false, reset the camera to the way it was before a planet was clicked
			if (count == solarSystem.size())
			{
				p.adjustCamera();
			}
	    }
		
		// if there is at least 1 planet created, allow the abilty to zoom in and out on planets
		if (solarSystem.size() > 0)
		{
			Planet p = solarSystem.get(0);
			p.zoomIn();
			p.zoomOut();
		}
	}
	
	public void Finn()
	{
		
	}
	
	public void settings()
	{
		fullScreen(P3D);
		//size(750, 500, P3D);
	}
	
    public static void main(String[] args)
    {
        PApplet.main("ie.dit.SolarWonders");
    }
}