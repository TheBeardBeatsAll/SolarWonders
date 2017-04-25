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
	  int x_start, z_start, x_accel, z_accel, trailSize;
	  float r_c, g_c, b_c;
	  float x_coord, z_coord, y_coord;
	  float period_acc, parent_mass, period_vel, rot;
	  double major, acc, eccentricity;
	  double theta, grav, vel, sigma;
	  ArrayList<Planet> moons = new ArrayList<Planet>();
	  ArrayList<PVector> trail = new ArrayList<PVector>();

	  boolean clicked;
	  PVector location, infoLocation, infoSize, infoEscLoc, infoEscSize;
	  ArrayList<Scrollbar> sBars;
	  int current;
	  int addPos, checkAddPos, add , checkAdd ;
	  
	  
	  Planet(PApplet parent, float parent_mass, float x_coord, float z_coord,
			  float y_coord, float size, float mass, double eccentricity, PImage texture, ArrayList<Planet> sSystem) 
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
	      rot = 0;
	      clicked = false;
	      
	      parent.noStroke();
	      parent.noFill();
	      planet = parent.createShape(PConstants.SPHERE, size);
		  planet.setTexture(texture);
		  
		  period_acc = 3f;
		  period_vel = (float) Math.sqrt(period_acc);//you multiply acc by x then you multiply vel by sqrt(x)
		  
		  adjacent = new PVector(0, y_coord, 0);
		  sigma = Math.asin(adjacent.mag()/location.mag());
		  if(y_coord > 0)
		  {
			  sigma *= -1;
		  }
		  r_c = parent.random(100, 255);
		  g_c = parent.random(100, 255);
		  b_c = parent.random(100, 255);
		  
		  size = height / 10;
		  infoLocation = new PVector(width*.01f, width*.1f);
		  infoSize = new PVector(width*.15f, height*.5f);
		  addPos = checkAddPos;
		  add  = checkAdd ;
		  infoEscLoc = new PVector(infoLocation.x*1.1f, infoLocation.y*1.01f);
		  infoEscSize = new PVector(width*.03f, width*.03f);
	  }
	  
	  public void add_moon(float m_x, float m_z, float m_y, float m_size, float m_mass, 
			  double eccentricity, PImage texture, ArrayList<Planet> sSystem)
	  {
		  Planet p = new Planet(parent, this.mass, m_x, m_z, m_y, m_size, m_mass, eccentricity, texture, sSystem);
		  moons.add(p);
	  }
	  
	  private void calculate_theta()
	  {
		  adjacent = new PVector(location.x, 0, 0);
		  theta = Math.acos(adjacent.mag()/location.mag());
	  }
	  
	  private void init_vel()
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
		  trailPosition = new PVector(location.x, 0, location.z);
	      trail.add(trailPosition);
	      int trailLength = trail.size() - 2;

	      for (int i = 0; i < trailLength; i++) 
	      {
	        trailPosition = trail.get(i);
	        adjacent = trail.get(i + 1);

	        parent.stroke(r_c, g_c, b_c,255*i/trailLength);
	        parent.line(trailPosition.x, 0, trailPosition.z,
	        		adjacent.x, 0, adjacent.z);
	      }
	      if (trailLength >= trailSize)
	      {
	        trail.remove(0);
	      }
	  }
	  
	  public void display() 
	  {
	      parent.pushMatrix();
	      parent.rotateX((float) sigma);
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
	    parent.translate(location.x, location.y, location.z);
	    parent.noFill();
	    parent.stroke(255);
	    // exception must be made for second planet in the ArrayList
	    if (current == 1)
    	{
	    	Scrollbar s = sBars.get(current + 1);
	    	parent.strokeWeight(1);
		    parent.sphere(size + (s.sPos - s.sliderStart()));
    	}
	    else
	    {
	    	Scrollbar s = sBars.get(current * 2);
	    	parent.strokeWeight(1);
		    parent.sphere(size + (s.sPos - s.sliderStart()));
	    }
	    // to change distances between planets
	    Scrollbar s2 = sBars.get(current * 2 + 1);
	    Planet p = sSystem.get(current);
	    if (s2.locked)
	    {
	    	addPos = 1;
	    }
	    else
	    {
	    	addPos = 0;
	    	checkAddPos = addPos;
	    }
	    
	    if (addPos == 1 && addPos != checkAddPos)
		{
			p.location.x = p.location.x + (s2.sPos - s2.sliderStart()) / 10;
		}
	    parent.popMatrix();
	  }
	  
	  public void bars()
	  {
//			Scrollbar scrollbar = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y, 
//			infoLocation.y + infoSize.y * .2f);
//	Scrollbar scrollbar2 = new Scrollbar(parent, sSystem.size() - 1, infoLocation.x, infoLocation.y, infoSize.x, infoSize.y, 
//			infoLocation.y + infoSize.y * .4f);
//	sBars.add(scrollbar);
//	sBars.add(scrollbar2);
	  }

	  public void check()
	  {
		  parent.pushMatrix();
		  parent.translate(location.x, location.y, location.z);
		  if (parent.mouseX > 0 - size + (parent.screenX(0, 0))
				  && parent.mouseX < 0 + size + (parent.screenX(0, 0)) 
				  && parent.mouseY > 0 - size + (parent.screenY(0, 0))
				  && parent.mouseY < 0 + size + (parent.screenY(0, 0)))
		  {
			  if (clicked)
			  {
				  clicked = false;
			  }
			  else
			  {
				  for (int i = sSystem.size() - 1; i >= 0; i--)
				  {
					  Planet p = sSystem.get(i);
					  p.clicked = false;
				  }
				  clicked = true;
			  }
		  }
		  parent.popMatrix();
	  }
	  
	  public void info()
	  {
		  parent.noStroke();
		  parent.fill(0, 157, 219);
		  parent.rect(infoLocation.x / 2, infoLocation.y * .95f,
					infoSize.x + infoLocation.x, infoSize.y + infoLocation.y * .1f);
		  parent.strokeWeight(2);
		  parent.stroke(247, 255, 28);
		  parent.fill(119, 112, 127);
		  parent.rect(infoLocation.x, infoLocation.y,
				  infoSize.x, infoSize.y);
		  parent.fill(247, 255, 28);
		  parent.textAlign(PConstants.CENTER);
		  parent.textSize((height / 40));
		  parent.text("Planet No. " + current, infoLocation.x + infoSize.x / 2, 
				  infoLocation.y + infoSize.y * .75f);
		  if (parent.mouseX > infoEscLoc.x && parent.mouseX < infoEscLoc.x + infoEscSize.x 
				  && parent.mouseY > infoEscLoc.y && parent.mouseY < infoEscLoc.y + infoEscSize.y)
		  {
			  parent.stroke(255, 0, 0);
			  parent.fill(255, 0, 0);
			  parent.rect(infoEscLoc.x, infoEscLoc.y,
					  infoEscSize.x, infoEscSize.y);
			  parent.stroke(255);
			  parent.line(infoEscLoc.x + (infoEscSize.x * .25f),
					  infoEscLoc.y + (infoEscSize.y * .25f),
					  infoEscLoc.x + (infoEscSize.x * .75f), 
					  infoEscLoc.y + (infoEscSize.y * .75f));
			  parent.line(infoEscLoc.x + (infoEscSize.x * .25f),
					  infoEscLoc.y + (infoEscSize.y * .75f),
					  infoEscLoc.x + (infoEscSize.x * .75f), 
					  infoEscLoc.y + (infoEscSize.y * .25f));
			  if (parent.mousePressed)
			  {
				  clicked = false;
			  }
		  }
		  else
		  {
			  parent.stroke(255, 0, 0);
			  parent.fill(255, 0, 0);
			  parent.rect(infoEscLoc.x, infoEscLoc.y,
					  infoEscSize.x, infoEscSize.y);
			  parent.stroke(255, 0, 0);
			  parent.line(infoEscLoc.x + (infoEscSize.x * .25f),
					  infoEscLoc.y + (infoEscSize.y * .25f),
					  infoEscLoc.x + (infoEscSize.x * .75f), 
					  infoEscLoc.y + (infoEscSize.y * .75f));
			  parent.line(infoEscLoc.x + (infoEscSize.x * .25f),
					  infoEscLoc.y + (infoEscSize.y * .75f),
					  infoEscLoc.x + (infoEscSize.x * .75f), 
					  infoEscLoc.y + (infoEscSize.y * .25f));
		  }
		  
		  for (int i = sBars.size() - 1; i >= 0; i--)
		  {
			  Scrollbar s = sBars.get(i);
			  if (s.id == current)
			  {
				  s.update();
				  s.display();
			  }
		  }
	  }
}
