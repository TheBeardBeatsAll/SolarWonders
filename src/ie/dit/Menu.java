package ie.dit;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Menu {
	
	ArrayList<Menu> Star = new ArrayList<Menu>();
	PApplet parent;
	PImage img;
	float height, width;
	float rot, g;
	boolean starCheck = false;
	
	Menu(PApplet p, PImage img)
	{
		parent = p;
		img = this.img;
		width = p.width;
		height = p.height;
		rot = 0;
		g = 0;
	}
	
	void MenPlanets()
	{
		parent.pushMatrix();
		parent.translate(width/2 + 250, height/2 - 125, -150);
		parent.rotateY(rot);
		parent.noStroke();
		parent.stroke(0, 100);
		parent.fill(200, 100, 0);
		parent.sphere(100);
		parent.popMatrix();
		
		parent.pushMatrix();
		parent.translate(0, height, 200);
		parent.rotateY((rot));
		parent.stroke(0, 100);
		parent.fill(0, 255, 255);
		parent.beginShape();
		parent.sphere(300);
		parent.endShape();
		parent.popMatrix();
		
		rot += .0005;
		
		star();
	}
	
	void star()
	{
		parent.ambientLight(255, 255, 255, width/2, height/2, 0);

		if( starCheck == false)
		{
			for(int i=-250; i < width+250; i += 15)
			{
				parent.pushMatrix();
				parent.translate(i, parent.random(-150, height+150), -250);
				parent.noStroke();
				parent.fill(255);
				parent.sphere(parent.random(0, 5));
				parent.popMatrix();
			}
			
			//starCheck = true;
		}
	}
}
