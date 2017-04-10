package ie.dit;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class Menu 
{	
	ArrayList<Star> stars = new ArrayList<Star>();
	Star star;
	PApplet parent;
	PShape planet;
	PImage img;
	float height, width;
	float rot;
	boolean starCheck = false;
	
	Menu(PApplet p, PImage img)
	{
		parent = p;
		img = this.img;
		width = p.width;
		height = p.height;
		rot = 0;
	}
	public void Menu()
	{
		Planets();
		star();
		//text();
	}
	
	public void Planets()
	{
		parent.directionalLight(255, 255 , 255, 0, 5, -10);
		
		parent.pushMatrix();
		parent.translate(width - width/5, height/2 - 150, -200);
		parent.rotateY(rot);
		parent.noStroke();
		parent.stroke(0, 100);
		parent.fill(200, 100, 0);
		parent.sphere(200);
		parent.popMatrix();
		
		parent.pushMatrix();
		parent.translate(50, height, 200);
		parent.rotateY((rot));
		parent.stroke(0, 100);
		parent.fill(0, 255, 255);
		parent.sphere(450);
		parent.popMatrix();
		
		rot += .0005;
	}
	
	public void star()
	{
		float size, x, y, z;
		
		parent.ambientLight(255, 255, 255, width/2, height/2, 0);
		//parent.directionalLight(255, 255 , 255, width/2, 0, -200); //Deciding between the two
		
		//Create stars 
		if( starCheck == false)
		{
			for(int i=-350; i < width+350; i += 15)
			{	
				x = i;
				y = parent.random(-150, height+150);
				z = -300;
				size = parent.random(0, 5);
				
				star = new Star(x, y ,z, size, parent);
				stars.add(star);
			}
			
			starCheck = true;
		}
		
		//Displays stars
		for(int i=0; i < stars.size(); i++)
		{
			Star s = stars.get(i);
			s.display();
		}
	}
	
	public void text()
	{
		parent.fill(255, 75);
		parent.rect(width/2 - 200, height/2 - 250, 400, 500);
		
		parent.fill(255, 225);
		parent.textSize(55);
		parent.text("SolarWonders", width/2-180, height/2-200);
		
		parent.text("Play", width/2-180, height/2-50);
		parent.text("Instructions", width/2-180, height/2+50);
		parent.text("Exit", width/2-180, height/2+150);
	}
}