package ie.dit;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PShape;

public class Textures 
{

	PApplet parent;
	PFont font;
	PImage[] imgs;
	PShape planet;
	PShape[] textures = new PShape[12];
	int check = 0;
	float rot = 0;
	boolean create_chk = false;
	
	Textures(PApplet p, PImage[] images)
	{
		this.parent = p;
		this.imgs = images;
	}
	
	public void textures()
	{
		int rec = 0, j =0;
		
		parent.fill(255, 255);
		
		parent.noStroke();
		planet = parent.createShape(parent.SPHERE, 200);
		planet.setTexture(imgs[check]);

		
		if(create_chk == false)
		{
			for(int i=0; i < 9; i++)
			{
				textures[i] = parent.createShape(parent.RECT, 58 + j, 65 + rec, 75, 50);
				textures[i].setTexture(imgs[i]);
			
				j += 80;
			
				if(j > 200)
				{
					j = 0;
					rec += 60;
				}
			}
		
			create_chk = true;
		}
	}
	
	public void planet()
	{
		int rec = 0, j = 0;

		textures();
		
		parent.pushMatrix();
		parent.translate(parent.width/2, parent.height/2);
		parent.rotateY(rot);
		parent.shape(planet);
		parent.popMatrix();
		
		rot += .002;
		
		parent.fill(255, 100);
		parent.rect(50, 50, 250, 200);
		
		parent.fill(255, 0, 0);
				
		for(int i=0; i < 9; i++)
		{
			parent.shape(textures[i]);
		}
		
		if( parent.mousePressed && ( (parent.mouseX > 50 && parent.mouseX < 300) && (parent.mouseY > 50 && parent.mouseY < 250) ) )
		{
			for(int i=0; i < 9; i++)
			{	
				if(((parent.mouseX > 58 + j && parent.mouseX < (58+j) + 75) && (parent.mouseY > 65+rec && parent.mouseY < (65+rec) + 50 ) ) )
				{
					check = i;
					break;
				}
				else
				{
					j += 80;
				
					if(j > 200)
					{
						j = 0;
						rec += 60;
					}
				}
			}//end for
		}//end mousePressed if	
	}
}