package ie.dit;

import java.util.ArrayList;
import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet mover;
	AddPlanet button;
	public ArrayList<Planet> solarSystem;
	public void setup()
	{
		smooth();
		// Make Mover object
		mover = new Planet(this);
		solarSystem = new ArrayList<Planet>();
		button = new AddPlanet(this, solarSystem);
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
		System.out.println(solarSystem.size());
		if (solarSystem.size() > 0)
		{
			for (int i = solarSystem.size() - 1; i >= 0; i --)
		    {
				Planet p = solarSystem.get(i);
				p.check();
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
			p.info();
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


