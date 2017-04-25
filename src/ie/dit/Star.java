package ie.dit;

import processing.core.PApplet;

public class Star 
{
	
	PApplet parent;
	float size, x, y, z, rot = 0;
	
	Star(float x, float y, float z, float size, PApplet p)
	{
		this.parent = p;
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;
	}
	
	public void display()
	{
		parent.pushMatrix();
		parent.translate(x, y, z);
		parent.noStroke();
		parent.fill(255);
		parent.sphere(size);
		parent.popMatrix();
	}
	
	public void trail(int check)
	{	
		//x = parent.width/2;
		//y = parent.height/2;
		
		if(check == 1)
		{
			parent.pushMatrix();
			parent.translate(0, 0, 0);
			parent.fill(0, 100, 255, 100);
			parent.beginShape();
			parent.vertex(x, y-12, z-12);
			parent.vertex(x, y-12, z+12);
			parent.vertex(x-100, y, z);
			
			parent.vertex(x, y-12, z+12);
			parent.vertex(x, y+12, z+12);
			parent.vertex(x-100, y, z);
			
			//parent.vertex(x, y+12, z+12);
			parent.vertex(x, y+12, z-12);
			//parent.vertex(x-100, y, z);
			
			parent.vertex(x, y+12, z-15);
			parent.vertex(x, y-12, z-15);
			parent.vertex(x-100, y, z);
			parent.endShape();
			parent.popMatrix();
		}
		else if(check == 2)
		{
			parent.pushMatrix();
			parent.translate(0, 0, 0);
			parent.fill(0, 100, 255, 100);
			parent.beginShape();
			parent.vertex(x, y-12, z-12);
			parent.vertex(x, y-12, z+12);
			parent.vertex(x-100, y+40, z);
			
			parent.vertex(x, y-12, z+12);
			parent.vertex(x, y+12, z+12);
			parent.vertex(x-100, y+40, z);
			
			parent.vertex(x, y+12, z-12);
			
			parent.vertex(x, y+12, z-15);
			parent.vertex(x, y-12, z-15);
			parent.vertex(x-100, y+40, z);
			parent.endShape();
			parent.popMatrix();
		}
		else if(check == 3)
		{
			parent.pushMatrix();
			parent.translate(0, 0, 0);
			parent.fill(0, 100, 255, 100);
			parent.beginShape();
			parent.vertex(x, y-12, z-12);
			parent.vertex(x, y-12, z+12);
			parent.vertex(x+100, y, z);
			
			parent.vertex(x, y-12, z+12);
			parent.vertex(x, y+12, z+12);
			parent.vertex(x+100, y, z);
			
			parent.vertex(x, y+12, z-12);
			
			parent.vertex(x, y+12, z-15);
			parent.vertex(x, y-12, z-15);
			parent.vertex(x+100, y, z);
			parent.endShape();
			parent.popMatrix();
		}
		else if(check == 4)
		{
			parent.pushMatrix();
			parent.translate(0, 0, 0);
			parent.fill(0, 100, 255, 100);
			parent.beginShape();
			parent.vertex(x, y-12, z-12);
			parent.vertex(x, y-12, z+12);
			parent.vertex(x+100, y+40, z);
			
			parent.vertex(x, y-12, z+12);
			parent.vertex(x, y+12, z+12);
			parent.vertex(x+100, y+40, z);
			
			parent.vertex(x, y+12, z-12);
			
			parent.vertex(x, y+12, z-15);
			parent.vertex(x, y-12, z-15);
			parent.vertex(x+100, y+40, z);
			parent.endShape();
			parent.popMatrix();
		}
	}
}