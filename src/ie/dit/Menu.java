package ie.dit;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class Menu 
{	
	ArrayList<Star> stars = new ArrayList<Star>();
	ArrayList<Star> comets = new ArrayList<Star>();
	Star star, comet, trail;
	PApplet parent;
	PShape planet, cloud;
	PImage img;
	float height, width, startposx = 0, startposy = 0;
;
	float rot;
	boolean starCheck = false, cometCheck = false;
	
	Menu(PApplet p, PImage img)
	{
		parent = p;
		img = this.img;
		width = p.width;
		height = p.height;
		rot = 0;
	}
	
	public void menu()
	{
		Planets();
		star();
		comets();
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
		
		parent.pushMatrix();
		parent.translate(width - width/5, height/2 - 150, 0);
		parent.rotateY((rot));
		parent.stroke(0, 100);
		parent.fill(140, 110, 60);
		parent.sphere(50);
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
	
	public void comets()
	{
		boolean tl = (startposx < width/2 && startposy < height/2);
		boolean bl = (startposx < width/2 && startposy > height/2);
		boolean tr = (startposx > width/2 && startposy < height/2);
		boolean br = (startposx > width/2 && startposy > height/2);
				
		if(cometCheck == false)
		{
			startposx = parent.random(-300, 1600);
			startposy = parent.random(-300, 1000);
					
					
			if((startposx < -200 || startposx > 1500) || (startposy < -200 || startposy > 900))
			{
					comet = new Star(startposx, startposy, -180, 15, parent);
					comets.add(comet);
					
					comet = new Star(startposx + parent.random(80, 110), startposy - 90, -180, 15, parent);
					comets.add(comet);
					
					comet = new Star(startposx - parent.random(120, 150), startposy - 125, -180, 15, parent);
					comets.add(comet);
					
					cometCheck = true;
			}
		}
		else
		{
			if(tl)
			{
				for(int i=0; i < comets.size(); i++)
				{
					Star c = comets.get(i);
					c.display();
					c.trail(1);
					c.x += 40;
					c.y += 2;
					
					if(c.x > 1500)
					{
						comets.remove(i);
					}
				}
				
			}
			else if(bl)
			{				
				for(int i=0; i < comets.size(); i++)
				{
					Star c = comets.get(i);
					c.display();
					c.trail(2);
					c.x += 40;
					c.y -= 20;
					
					if(c.x > 1500)
					{
						comets.remove(i);
					}
				}
			}
			else if(tr)
			{
				for(int i=0; i < comets.size(); i++)
				{
					Star c = comets.get(i);
					c.display();
					c.trail(3);
					c.x -= 40;
					c.y -= 2;
					
					if(c.x < -200)
					{
						comets.remove(i);
					}
				}
			}
			else if(br)
			{
				for(int i=0; i < comets.size(); i++)
				{
					Star c = comets.get(i);
					c.display();
					c.trail(4);
					c.x -= 40;
					c.y -= 20;
					
					if(c.x < -200)
					{
						comets.remove(i);
					}
				}
			}
						
			if(comets.size() == 0)
			{
				cometCheck = false;
			}
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