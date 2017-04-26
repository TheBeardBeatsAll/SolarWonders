package ie.dit;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;

public class Planet extends Sun
{
	  PApplet parent;
	  PVector velocity, acceleration, adjacent, trailPosition;
	  PShape planet;
	  PImage texture;
	  int addPos, checkAddPos, add , checkAdd, current, max_moons;
	  int x_start, z_start, x_accel, z_accel, trailSize;
	  int textures, add_m, j, rec, lock_t2;
	  float r_c, g_c, b_c;
	  float x_coord, z_coord, y_coord, size;
	  float period_acc, parent_mass, period_vel, rot, rot2;
	  double major, acc, eccentricity;
	  double theta, grav, vel;
	  boolean fade, lock_t, lock_m;
	  ArrayList<Planet> moons = new ArrayList<Planet>();
	  ArrayList<PVector> trail = new ArrayList<PVector>();
	  ArrayList<Scrollbar> sBars = new ArrayList<Scrollbar>();
	  PImage[] imgs = new PImage[13];
	  PShape[] surfaces = new PShape[12];
	  PVector location, infoLocation, infoSize, infoEscLoc, infoEscSize;
	  
	  Planet(PApplet parent, float parent_mass, float x_coord, float z_coord,
			  float y_coord, float size, float mass, double eccentricity, PImage texture, PImage[] imgs, ArrayList<Planet> sSystem) 
	  {
		  super(parent, texture, sSystem);
		  this.parent = parent;
		  this.x_coord = x_coord;
		  this.z_coord = z_coord;
		  this.y_coord = y_coord;
		  this.size = size;
		  this.mass = mass;
		  this.eccentricity = eccentricity;
		  this.parent_mass = parent_mass;
		  this.texture = texture;
		  this.imgs = imgs;
		  init_planet();
		  calculate_theta();
		  init_vel();  
	  }
	  
	  private void init_planet()
	  {
		  location = new PVector(x_coord, 0, z_coord);
	      velocity = new PVector(0f, 0f, 0f);
	      acceleration = new PVector(0f, 0f, 0f);
	      grav = 6.67384;
	      trailSize = 200;
	      rot = rot2 = 0;
	      add_m = textures = 255;
	      parent.noStroke();
	      planet = parent.createShape(PConstants.SPHERE, size);
		  planet.setTexture(texture);
		  fade = false;
		  lock_t2 = -1;
		  lock_t = lock_m = true;
		  max_moons = 5;
		  period_acc = 3f;
		  period_vel = (float) Math.sqrt(period_acc);//you multiply acc by x then you multiply vel by sqrt(x)
		  
		  r_c = parent.random(100, 255);
		  g_c = parent.random(100, 255);
		  b_c = parent.random(100, 255);
		  
		  infoLocation = new PVector(width*.01f, width*.1f);
		  infoSize = new PVector(width*.15f, height*.5f);
		  addPos = checkAddPos;
		  add  = checkAdd ;
		  infoEscLoc = new PVector(infoLocation.x*1.1f, infoLocation.y*1.01f);
		  infoEscSize = new PVector(width*.03f, width*.03f); 
		  
		  rec = j = 0;
		  for(int i=0; i < 9; i++)
	      {
			  parent.noStroke();
			  surfaces[i] = parent.createShape(PConstants.RECT, width * 0.0235f + j, height * 0.715f + rec, 75, 50);
			  surfaces[i].setTexture(imgs[i]);
			
		      j += 80;
			
			  if(j > 200)
			  {
				  j = 0;
				  rec += 60;
			  }
		  }
	  }
	  
	  private void calculate_theta()
	  {
		  adjacent = new PVector(location.x, 0, 0);
		  theta = Math.acos(adjacent.mag()/location.mag());
	  }
	  
	  public void init_vel()
	  {
		  major = location.mag() / (1 + eccentricity);
		  //a = v*v/d --> v = sqrt(a*d)
		  vel = Math.sqrt((parent_mass * grav) * ((2f / location.mag()) -  (1 / major)));
		  if(location.x <= 0 && location.z > 0)
		  {
			  x_start = z_start = 1;
		  }
		  else if(location.x >= 0 && location.z < 0)
		  {
			  x_start = z_start = -1;
		  }
		  else if(location.x < 0 && location.z <= 0)
		  {
			  x_start = -1;
			  z_start = 1;
		  }
		  else if(location.x > 0 && location.z >= 0)
		  {
			  x_start = 1;
			  z_start = -1;
		  }
		  velocity.x = (float) (x_start * vel * Math.sin(theta));
		  velocity.z = (float) (z_start * vel * Math.cos(theta));
		  velocity.mult(period_vel);
	  }
	  
	  private void acceleration_cal()
	  {
		  acc = grav * parent_mass / Math.pow(location.mag(), 2);
		  if(location.x <= 0 && location.z > 0)
		  {
			  x_accel = 1;
			  z_accel = -1;
		  }
		  else if(location.x >= 0 && location.z < 0)
		  {
			  x_accel = -1;
			  z_accel = 1;
		  }
		  else if(location.x < 0 && location.z <= 0)
		  {
			  x_accel = z_accel = 1;
		  }
		  else if(location.x > 0 && location.z >= 0)
		  {
			  x_accel = z_accel = -1;
		  }
		  calculate_theta();
		  //F = ma, F = (G * M1 * M2)/(d * d) --> a = (G * M1)/(d * d) 
		  acceleration.x = (float) (x_accel * acc * Math.cos(theta));
		  acceleration.z = (float) (z_accel * acc * Math.sin(theta));
		  acceleration.mult(period_acc);
	  }
	  
	  public void update() 
	  {
		  acceleration_cal();
	      velocity.add(acceleration);
	      location.add(velocity);
	  }
	  
	  private void trails()
	  {
		  trailPosition = new PVector(location.x, location.y, location.z);
	      trail.add(trailPosition);
	      int trailLength = trail.size() - 2;

	      for (int i = 0; i < trailLength; i++) 
	      {
	        trailPosition = trail.get(i);
	        adjacent = trail.get(i + 1);

	        parent.noFill();
	        parent.stroke(r_c, g_c, b_c,255*i/trailLength);
	        parent.line(trailPosition.x, trailPosition.y, trailPosition.z,
	        		adjacent.x, adjacent.y, adjacent.z);
	      }
	      if (trailLength >= trailSize)
	      {
	        trail.remove(0);
	      }
	  }
	  
	  public void add_moon(float m_x, float m_z, float m_y, float m_size, float m_mass, 
			  double eccentricity, PImage texture, PImage[] imgs, ArrayList<Planet> sSystem)
	  {
		  Planet p = new Planet(parent, this.mass, m_x, m_z, m_y, m_size, m_mass, eccentricity, texture, imgs, sSystem);
		  moons.add(p);
	  }
	  public void display() 
	  {
	      parent.pushMatrix();
	      trails();
	      parent.translate(location.x, location.y, location.z);
	      parent.pushMatrix();
	      parent.rotateY(rot);

	      parent.shape(planet);
	      parent.popMatrix();
	      for(Planet p : moons)
		  {
	    	  p.update();
	    	  parent.rotateX(PConstants.PI/4);
			  p.display();
		  }  
	      parent.popMatrix();
	      rot += .001;
	  }
	  
	  public void display2() 
	  {
	      parent.pushMatrix();
	      parent.translate(width/2, height/2, 600);
	      parent.rotateY(rot2);
	      parent.shape(planet);
	      parent.popMatrix();
	      update_bars();
	      info();
	      rot2 += .001f;
	  }
	  
	  public void update_bars()
	  {
		  for(int i = 0; i < sBars.size(); i++)
		  {
			  Scrollbar s = sBars.get(i);
			  switch(i)
			  {
			  	case 0:
			  	{
			  		if(size + (s.sPos - s.sliderStart()) > 10 && size + (s.sPos - s.sliderStart()) < 100)
			  		{
				  		parent.noStroke();
					    planet = parent.createShape(PConstants.SPHERE, size + (s.sPos - s.sliderStart()));
						planet.setTexture(texture);
			  		}
			  		break;
			  	}
			  	case 1:
			  	{
			  		if(location.x + (s.sPos - s.sliderStart()) > 150 && location.x + (s.sPos - s.sliderStart()) < 10000)
			  		{
			  			location.x = (float) (location.x + (s.sPos - s.sliderStart()) * 0.1);
			  		}
			  		break;
			  	}
			  	case 2:
			  	{
			  		if(location.z + (s.sPos - s.sliderStart()) > 150 && location.z + (s.sPos - s.sliderStart()) < 10000)
			  		{
			  			location.z = (float) (location.z + (s.sPos - s.sliderStart()) * 0.1);
			  		}
			  		break;
			  	}
			  	case 3:
			  	{
			  		if(eccentricity + ((s.sPos - s.sliderStart()) * 0.01) > 0.000000001 && eccentricity + ((s.sPos - s.sliderStart()) * 0.01) < 0.4)
			  		{
			  			eccentricity = eccentricity + ((s.sPos - s.sliderStart()) * 0.01);
			  		}
			  		break;
			  	}
			  }
		  }
		  init_vel();
	  }
	  
	  public void bars()
	  {
			Scrollbar scrollbar = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y, 
			infoLocation.y + infoSize.y * .175f);
			sBars.add(scrollbar);
			scrollbar = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y, 
			infoLocation.y + infoSize.y * .325f);
			sBars.add(scrollbar);
			scrollbar = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y, 
			infoLocation.y + infoSize.y * .475f);
			sBars.add(scrollbar);
			scrollbar = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y, 
			infoLocation.y + infoSize.y * .625f);
			sBars.add(scrollbar);
	  }
	  
      private void info()
	  {
		  parent.noStroke();
		  parent.fill(0, 157, 219);
		  parent.rect(infoLocation.x / 2, infoLocation.y * .95f,
					infoSize.x + infoLocation.x, infoSize.y + infoLocation.y * .1f);
		  parent.stroke(247, 255, 28);
		  parent.fill(119, 112, 127);
		  parent.rect(infoLocation.x, infoLocation.y,
				  infoSize.x, infoSize.y);
		  parent.fill(247, 255, 28);
		  parent.textAlign(PConstants.CENTER);
		  parent.textSize((height / 50));  
		  parent.text("SIZE:", infoLocation.x + infoSize.x / 2,
					infoLocation.y + infoSize.y * .1f);
		  parent.text("X_Coord:", infoLocation.x + infoSize.x / 2,
					infoLocation.y + infoSize.y * .25f);
		  parent.text("Z_Coord:", infoLocation.x + infoSize.x / 2,
					infoLocation.y + infoSize.y * .4f);
		  parent.text("Eccentricity:", infoLocation.x + infoSize.x / 2,
					infoLocation.y + infoSize.y * .55f);
		  parent.fill(247, 255, 28, add_m);
		  parent.text("Add Moon", infoLocation.x + infoSize.x / 2,
					infoLocation.y + infoSize.y * .875f);
		  parent.fill(247, 255, 28, textures);
		  parent.text("Textures", infoLocation.x + infoSize.x / 2,
					infoLocation.y + infoSize.y * .95f);
		  
		  if(mouseCheck(infoLocation.x + infoSize.x / 2 - 70, 135, infoLocation.y + infoSize.y * .875f - 20, 27))
		  {
			  if(parent.mousePressed)
			  {
				  if(moons.size() < max_moons)
				  {
					  if(lock_m)
					  {
						  add_moon(width * 0.08f, 0, 0, 10, 40, 
							  parent.random(0f, 0.2f), imgs[(int)parent.random(10,12)], this.imgs, sSystem);
						  lock_m = false;
					  }
			  	  }
			  }
			  
			  if(fade == false)
			  {
		 		  add_m = add_m - 4;
					
				  if(add_m < 50)
				  {
				   	fade = true;
					}
				  }
				  else
				  {
				      add_m = add_m + 4;
					
					  if(add_m > 250)
					  {
						  fade = false;
						  lock_m = true;
					  }
				  }
			  }
			  else
			  {
				  add_m = 255;
			  }
			  if(mouseCheck(infoLocation.x + infoSize.x / 2 - 60, 110, infoLocation.y + infoSize.y * .95f - 20, 27))
			  {
				  if(parent.mousePressed)
				  {
					  if(lock_t)
					  { 
						  lock_t2 = -lock_t2;
						  lock_t = false;
					  }
				  }
				  
				  if(fade == false)
				  {
					  textures = textures - 4;
					
					  if(textures < 50)
					  {
						  fade = true;
					  }
				  }
				  else
				  {
					  textures = textures + 4;
					
					  if(textures > 250)
					  {
						  fade = false;
						  lock_t = true;
					  }
				  }
			  }
			  else
			  {
				  textures = 255;
			  }
			  if(lock_t2 == 1)
			  {
				  parent.fill(119, 112, 127);
				  parent.rect(width * 0.02f, height * 0.7f, 250, 200);
							
				  for(int i=0; i < 9; i++)
				  {
					  parent.shape(surfaces[i]);
				  }
				  //width * 0.0235f + j, height * 0.715f + rec
				  rec = j = 0;
				  if(parent.mousePressed && ( (parent.mouseX > width * 0.02f && parent.mouseX < width * 0.02f + 250) && (parent.mouseY > height * 0.7f && parent.mouseY < height * 0.7f + 200) ) )
					{
						for(int i=0; i < 9; i++)
						{	
							if(((parent.mouseX > width * 0.0235f + j && parent.mouseX < (width * 0.0235f+j) + 75) && (parent.mouseY > height * 0.715f+rec && parent.mouseY < (height * 0.715f+rec) + 50 ) ) )
							{
								texture = imgs[i];
								parent.noStroke();
								planet = parent.createShape(PConstants.SPHERE, size);
								planet.setTexture(texture);
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
		  
		  for(Scrollbar s: sBars)
		  {
				  s.update();
				  s.display();
		  }
	  }
      
      boolean mouseCheck(float x1, float x2, float y1, float y2)
  	  {
  	      if(parent.mouseX > x1 && parent.mouseX < (x1 + x2))
  	      {
  		      if(parent.mouseY > y1 && parent.mouseY < (y1 + y2))
  		      {
  		        return true;
  		      }//end if
  		      else 
  		      {
  		        return false;
  		      }//end else
  	      }//end if
  	      else 
  	      {
  	        return false;
  	      }//end else
  	  }//end mouseCheck
}
