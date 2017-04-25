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
	  PVector location, velocity, acceleration, adjacent, trailPosition;
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
	  PVector infoLocation, infoSize, zoomPlusLoc, zoomMinusLoc;
	  ArrayList<Planet> sSystem;
	  ArrayList<Scrollbar> sBars;
	  int current;
	  int addPos, checkAddPos, addZoom, checkAddZoom;
	  PVector infoEscLoc, infoEscSize;
	  float zoom, zoomSize;
	  float notFocused = 1;
	  float topspeed, width, height;
	  
	  Planet(PApplet parent, float parent_mass, float x_coord, float z_coord,
			  float y_coord, float size, float mass, double eccentricity, PImage texture) 
	  {
		  super(parent, texture);
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
	  
	  Planet(PApplet p, ArrayList<Planet> sSystem, ArrayList<Scrollbar> sBars, PImage texture)
	  {
		super(p, texture);
		parent = p;
		width = p.width;
		height = p.height;
	    location = new PVector(width/2f,height/2f, 0f);
	    velocity = new PVector(0f,0f,0f);
	    acceleration = new PVector(0.01f,0.01f,0.01f);
	    topspeed = 4;
	    size = height / 10;
	    clicked = false;
	    infoLocation = new PVector(width*.01f, width*.1f);
	    infoSize = new PVector(width*.15f, height*.5f);
	    this.sSystem = sSystem;
	    this.sBars = sBars;
	    addPos = checkAddPos;
	    addZoom = checkAddZoom;
	    infoEscLoc = new PVector(infoLocation.x*1.1f, infoLocation.y*1.01f);
	    infoEscSize = new PVector(width*.03f, width*.03f);
	    zoom = .5f;
	    zoomMinusLoc = new PVector((width / 2f) * .85f, height * .875f);
	    zoomPlusLoc = new PVector((width / 2f) * 1.05f, height * .875f);
	    zoomSize = width * 0.05f;
	  }
	  
	  private void init_planet()
	  {
		  location = new PVector(x_coord, 0, z_coord);
	      velocity = new PVector(0f, 0f, 0f);
	      acceleration = new PVector(0f, 0f, 0f);
	      grav = 6.67384;
	      trailSize = 200;
	      rot = 0;
	      parent.noStroke();
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
	  }
	  
	  public void add_moon(float m_x, float m_z, float m_y, float m_size, float m_mass, double eccentricity, PImage texture)
	  {
		  Planet p = new Planet(parent, this.mass, m_x, m_z, m_y, m_size, m_mass, eccentricity, texture);
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
	  
	  public void check(int selected)
	  {
		  parent.pushMatrix();
		  parent.translate(location.x, location.y, location.z);
		  current = selected;
		  Planet pCurrent = sSystem.get(current);
		  if (parent.mouseX > 0 - size + (parent.screenX(0, 0))
				  && parent.mouseX < 0 + size + (parent.screenX(0, 0)) 
				  && parent.mouseY > 0 - size + (parent.screenY(0, 0))
				  && parent.mouseY < 0 + size + (parent.screenY(0, 0)))
		  {
			  if (pCurrent.clicked)
			  {
				  pCurrent.clicked = false;
			  }
			  else
			  {
				  for (int i = sSystem.size() - 1; i >= 0; i--)
				  {
					  Planet p = sSystem.get(i);
					  p.clicked = false;
				  }
				  pCurrent.clicked = true;
			  }
		  }
		  parent.popMatrix();
	  }
	  
	  public void focus()
	  {
		  Planet pCurrent = sSystem.get(current);
		  parent.camera(pCurrent.location.x, height / 2f, (float) ((height/2.0) / Math.tan(Math.PI / 6)) * zoom, 
				  pCurrent.location.x, height / 2f, 0, 0, 1, 0);
	  }
	  
	  public void adjustCamera()
	  {
		  Planet first = sSystem.get(0);
		  Planet last = sSystem.get(sSystem.size() - 1);
		  parent.camera((first.location.x + last.location.x) / 2, height / 2f, (float) ((height/2.0) / Math.tan(Math.PI / 6)) * notFocused, 
				  (first.location.x + last.location.x) / 2, height / 2f, 0, 0, 1, 0);
	  }
	  
	  public void zoomOut()
	  {
		  // minus button
		  parent.noStroke();
		  parent.fill(0, 157, 219);
		  parent.rect((zoomMinusLoc.x * .99f + -(parent.screenX(0, 0))) * notFocused,
				  (zoomMinusLoc.y * .99f + -(parent.screenY(0, 0))) * notFocused,
				  (zoomSize + zoomMinusLoc.x * 0.02f) * notFocused,
				  (zoomSize + zoomMinusLoc.y * 0.02f) * notFocused);
		  parent.strokeWeight(2);
		  parent.stroke(247, 255, 28);
		  parent.line((zoomMinusLoc.x + (zoomSize * .25f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomMinusLoc.y + (zoomSize * .5f) + -(parent.screenY(0, 0))) * notFocused,
				  (zoomMinusLoc.x + (zoomSize * .75f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomMinusLoc.y + (zoomSize * .5f) + -(parent.screenY(0, 0))) * notFocused);
		  if (parent.mouseX > zoomMinusLoc.x && parent.mouseX < zoomMinusLoc.x + zoomSize 
				  && parent.mouseY > zoomMinusLoc.y && parent.mouseY < zoomMinusLoc.y + zoomSize)
		  {
			  parent.fill(200);
			  if (parent.mousePressed)
			  {
				  notFocused = notFocused + 0.05f;
			  }
			  adjustCamera();
		  }
		  else
		  {
			  parent.fill(119, 112, 127);
		  }
		  parent.rect((zoomMinusLoc.x + -(parent.screenX(0, 0))) * notFocused,
				  (zoomMinusLoc.y + -(parent.screenY(0, 0))) * notFocused,
				  zoomSize * notFocused, zoomSize * notFocused);
	  }
	  
	  public void zoomIn()
	  {
		  // plus button
		  parent.noStroke();
		  parent.fill(0, 157, 219);
		  parent.rect((zoomPlusLoc.x * .99f + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y * .99f + -(parent.screenY(0, 0))) * notFocused,
				  (zoomSize + zoomPlusLoc.x * 0.02f) * notFocused,
				  (zoomSize + zoomPlusLoc.y * 0.02f) * notFocused);
		  parent.strokeWeight(2);
		  parent.stroke(247, 255, 28);
		  parent.line((zoomPlusLoc.x + (zoomSize * .25f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + (zoomSize * .5f) + -(parent.screenY(0, 0))) * notFocused,
				  (zoomPlusLoc.x + (zoomSize * .75f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + (zoomSize * .5f) + -(parent.screenY(0, 0))) * notFocused);
		  parent.line((zoomPlusLoc.x + (zoomSize * .5f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + (zoomSize * .25f) + -(parent.screenY(0, 0))) * notFocused,
				  (zoomPlusLoc.x + (zoomSize * .5f) + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + (zoomSize * .75f) + -(parent.screenY(0, 0))) * notFocused);
		  if (parent.mouseX > zoomPlusLoc.x && parent.mouseX < zoomPlusLoc.x + zoomSize 
				  && parent.mouseY > zoomPlusLoc.y && parent.mouseY < zoomPlusLoc.y + zoomSize)
		  {
			  if (parent.mousePressed && notFocused > 0.5)
			  {
				  notFocused = notFocused - 0.05f;
			  }
			  adjustCamera();
			  parent.fill(200);
		  }
		  else
		  {
			  parent.fill(119, 112, 127);
		  }
		  parent.rect((zoomPlusLoc.x + -(parent.screenX(0, 0))) * notFocused,
				  (zoomPlusLoc.y + -(parent.screenY(0, 0))) * notFocused,
				  zoomSize * notFocused, zoomSize * notFocused);
	  }
	  
	  public float getZoom()
	  {
		  return zoom;
	  }
	  
	  public float getFocus()
	  {
		  return notFocused;
	  }
	  
	  public void info()
	  {
		  focus();
		  parent.noStroke();
		  parent.fill(0, 157, 219);
		  parent.rect((infoLocation.x / 2 + -(parent.screenX(0, 0))) * zoom, (infoLocation.y * .95f + -(parent.screenY(0, 0))) * zoom,
					(infoSize.x + infoLocation.x) * zoom, (infoSize.y + infoLocation.y * .1f) * zoom);
		  parent.strokeWeight(2);
		  parent.stroke(247, 255, 28);
		  parent.fill(119, 112, 127);
		  parent.rect((infoLocation.x + -(parent.screenX(0, 0))) * zoom, (infoLocation.y + -(parent.screenY(0, 0))) * zoom,
				  infoSize.x * zoom, infoSize.y * zoom);
		  parent.fill(247, 255, 28);
		  parent.textAlign(PConstants.CENTER);
		  parent.textSize((height / 40) * zoom);
		  parent.text("Planet No. " + current, ((infoLocation.x + infoSize.x / 2) + -(parent.screenX(0, 0))) * zoom, 
				  ((infoLocation.y + infoSize.y * .75f) + -(parent.screenY(0, 0))) * zoom);
		  if (parent.mouseX > infoEscLoc.x && parent.mouseX < infoEscLoc.x + infoEscSize.x 
				  && parent.mouseY > infoEscLoc.y && parent.mouseY < infoEscLoc.y + infoEscSize.y)
		  {
			  parent.stroke(255, 0, 0);
			  parent.fill(255, 0, 0);
			  parent.rect((infoEscLoc.x + -(parent.screenX(0, 0))) * zoom, (infoEscLoc.y + -(parent.screenY(0, 0))) * zoom,
					  infoEscSize.x * zoom, infoEscSize.y * zoom);
			  parent.stroke(255);
			  parent.line((infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0))) * zoom,
					  (infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0))) * zoom,
					  (infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0))) * zoom, 
					  (infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0))) * zoom);
			  parent.line((infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0))) * zoom,
					  (infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0))) * zoom,
					  (infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0))) * zoom, 
					  (infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0))) * zoom);
			  if (parent.mousePressed)
			  {
				  clicked = false;
			  }
		  }
		  else
		  {
			  parent.stroke(255, 0, 0);
			  parent.fill(255);
			  parent.rect((infoEscLoc.x + -(parent.screenX(0, 0))) * zoom, (infoEscLoc.y + -(parent.screenY(0, 0))) * zoom,
					  infoEscSize.x * zoom, infoEscSize.y * zoom);
			  parent.stroke(255, 0, 0);
			  parent.line((infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0))) * zoom,
					  (infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0))) * zoom,
					  (infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0))) * zoom, 
					  (infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0))) * zoom);
			  parent.line((infoEscLoc.x + (infoEscSize.x * .25f) + -(parent.screenX(0, 0))) * zoom,
					  (infoEscLoc.y + (infoEscSize.y * .75f) + -(parent.screenY(0, 0))) * zoom,
					  (infoEscLoc.x + (infoEscSize.x * .75f) + -(parent.screenX(0, 0))) * zoom, 
					  (infoEscLoc.y + (infoEscSize.y * .25f) + -(parent.screenY(0, 0))) * zoom);
		  }
		  
		  for (int i = sBars.size() - 1; i >= 0; i--)
		  {
			  Scrollbar s = sBars.get(i);
			  if (s.id == current)
			  {
				  s.update(zoom);
				  s.display();
			  }
		  }
	  }
}
