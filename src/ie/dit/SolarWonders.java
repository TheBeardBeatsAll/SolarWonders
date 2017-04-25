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
		//mover.check();
		button.check();
		if (solarSystem.size() > 0)
		{
			for (int i = solarSystem.size() - 1; i >= 0; i--)
		    {
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
		//mover.update();
		//mover.checkEdges();
		//mover.display();
		//mover.info();
		button.display();
		for (int i = solarSystem.size() - 1; i >= 0; i--)
	    {
			Planet p = solarSystem.get(i);
			p.display();
			//p.info();
	    }
		for (int i = solarSystem.size() - 1; i >= 0; i--)
	    {
			Planet p = solarSystem.get(i);
			if (p.clicked)
			{
				p.info();
				System.out.println("Planet " + i + " is clicked");
			}
			else
			{
				count++;
			}
			
			if (count == solarSystem.size())
			{
				p.adjustCamera();
			}
	    }
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