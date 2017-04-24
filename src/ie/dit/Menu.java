package ie.dit;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PShape;

public class Menu 
{	
	ArrayList<Star> stars = new ArrayList<Star>();
	ArrayList<Star> comets = new ArrayList<Star>();
	Star star, comet, trail;
	PApplet parent;
	PShape rplanet, bplanet, moon;
	PImage[] textures = new PImage[12];
	PFont font;
	float height, width, startposx = 0, startposy = 0;
	int timer = 0;
	
	float rot;
	boolean starCheck = false, cometCheck = false;
	
	Menu(PApplet p, PImage[] textures, PFont font)
	{
		parent = p;
		this.textures = textures;
		this.font = font;
		width = p.width;
		height = p.height;
		rot = 0;
		
		parent.noStroke();
		rplanet = parent.createShape(parent.SPHERE, 200);
		rplanet.setTexture(textures[6]);
		
		parent.noStroke();
		bplanet = parent.createShape(parent.SPHERE, 450);
		bplanet.setTexture(textures[4]);
		
		parent.noStroke();
		moon = parent.createShape(parent.SPHERE, 50);
		moon.setTexture(textures[8]);
	}
	
	public void menu()
	{
		Planets();
		star();
		comets();
		text();
	}
	
	public void Planets()
	{
		parent.directionalLight(255, 255 , 255, 0, 5, -10);
		
		parent.pushMatrix();
		parent.translate(width - width/5, height/2 - 150, -200);
		parent.rotateY(rot);
		parent.shape(rplanet);
		parent.popMatrix();
		
		parent.pushMatrix();
		parent.translate(50, height, 200);
		parent.rotateY((rot));
		parent.shape(bplanet);
		parent.popMatrix();
		
		parent.pushMatrix();
		parent.translate(width - width/5, height/2 - 150, 0);
		parent.rotateY((rot));
		parent.shape(moon);
		parent.popMatrix();
		
		rot += .0006;
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
			if(parent.millis() - timer >= 4000)
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
					
					if(c.x > 1600)
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
					
					if(c.x > 1600)
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
					
					if(c.x < -300)
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
					
					if(c.x < -300)
					{
						comets.remove(i);
					}
				}
			}
						
			if(comets.size() == 0)
			{
				timer = parent.millis();
				cometCheck = false;
			}
		}
	}

	
	public void text()
	{
		int play = 255, ins = 255, exit = 255;
		
		parent.textFont(font);
		parent.fill(255, 75);
		parent.rect(width/2 - 200, height/2 - 150, 400, 275);
		
		parent.stroke(255);
		parent.line(width/2 - 190, height/2 - 95, width/2 + 190, height/2 - 95);
		
		parent.fill(255, 225);
		parent.textSize(45);
		parent.text("SolarWonders", width/2-190, height/2-100);
		
		parent.textSize(40);
		parent.fill(255, play);
		parent.text("Play", width/2-50, height/2-25);
		parent.fill(255, ins);
		parent.text("Instructions", width/2-130, height/2+25);
		parent.fill(255, exit);
		parent.text("Exit", width/2-42, height/2+75);
		
		if((parent.mouseX > width/2-50 && parent.mouseX < width/2+55) && (parent.mouseY > height/2-55 && parent.mouseY < height/2-20) )
		{
			parent.noFill();
			parent.rect(width/2-50, height/2-55, 105, 35);
		}
		if((parent.mouseX > width/2-130 && parent.mouseX < width/2+140) && (parent.mouseY > height/2-5 && parent.mouseY < height/2+30))
		{
			parent.noFill();
			parent.rect(width/2-130, height/2-5, 270, 35);
		}
		if((parent.mouseX > width/2-42 && parent.mouseX < width/2+48) && (parent.mouseY > height/2+45 && parent.mouseY < height/2+80) )
		{
			parent.noFill();
			parent.rect(width/2-42, height/2+45, 90, 35);
		}
	}
}