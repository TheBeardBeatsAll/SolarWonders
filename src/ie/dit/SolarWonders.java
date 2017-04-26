package ie.dit;

import java.util.ArrayList;

import ddf.minim.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

public class SolarWonders extends PApplet
{
	Sun sun;
	Star star, comet, trail;
	Planet rPlanet, lPlanet;
	ArrayList<Planet> sSystem = new ArrayList<Planet>();
	ArrayList<Star> stars = new ArrayList<Star>();
	ArrayList<Star> comets = new ArrayList<Star>();
	
	Minim minim;
	AudioPlayer[] songs = new AudioPlayer[7]; 
	
	PImage[] imgs = new PImage[13];
	PFont font;
	
	int music_menu, pause, timer;
	int play, ins, exit, menu_choice;
	int[] mouse_change = new int[7];

	int planet_choice;
	
	String playing;
	String[] music = {"Out of Space - The Prodigy", "Life on Mars - David Bowie", 
			"Intergalactic - Beastie Boys", "Supernaut - Black Sabbath", 
			"Man on the Moon - R.E.M.", "Only of the Universe - F. Y.", "Nothing Selected"};
	String[] fnames = {"earth", "blue", "brown", "magma", "water", "gas", "ice", "red", "coarse", "sun", "moon", "moon2", "moon3"};
	
	float startposx, startposy, tSize, interval;
	double alpha, beta;
	boolean starCheck, fade, cometCheck, musicCheck;
	
	PVector infoLocation, infoSize, location, size;
	
	public void setup()
	{
		smooth();
		loadData();
		sun = new Sun(this, imgs[9], sSystem);
		rPlanet = new Planet(this, sun.mass, width - width/5, -width/7, 0, 200, 100, 0, imgs[8], imgs, sSystem);
		lPlanet = new Planet(this, sun.mass, width/27, width/6, 0, 450, 100, 0, imgs[4], imgs, sSystem);
		rPlanet.add_moon(0, width/5, 0, 50, 100, 0, imgs[11], imgs, sSystem);
		playing = music[6];
		music_menu = -1;
		pause = timer = 0;
		startposx = startposy = 0;
		starCheck = cometCheck = fade = false;
		musicCheck = true;
		menu_choice = 1;
		play = ins = exit = 255;
		textFont(font);
		planet_choice = 0;
		location = new PVector(width*.01f, width*.01f, 0f);
		size = new PVector(width*.1f, height*.075f);
		infoLocation = new PVector(width*.01f, width*.1f);
	    infoSize = new PVector(width*.15f, height*.5f);
	    tSize = height / 45;
	    alpha = beta = 0;
	    interval = 0;
	}
	
	public void loadData()
	{
	  minim = new Minim(this);       
	  
	  for(int i = 0; i < 7; i++)
	  {
		  songs[i] = minim.loadFile("song" + i + ".mp3");
	  }
	  for(int i = 0; i < imgs.length; i++)
	  {
		  imgs[i] = loadImage(fnames[i] + ".png");
	  }
		
	  font = createFont("font.ttf", 25);
	}//end loadSounds
	
	public void draw()
	{
		println(width + "   " + height);
		switch(menu_choice)
		{
			case 1:
			{
				if(musicCheck)
				{
					playSound(songs[6]);
					musicCheck = false;
				}
				menu_background();
				text_menu();
				mouseOver();
				break;
			}
			case 2:
			{
				menu_background();
				textAlign(LEFT, BOTTOM);
				fill(255, 125);
				pushMatrix();
				translate(0, 0, 480);
				rect(width/2-150, height/2-50, 300, 115);
				
				textSize(15);
				fill(247, 255, 28);
				text("Instructions", width/2-145, height/2-35);
				textSize(10);
				fill(247, 255, 28, ins);
				text("Return to Menu", width/2+45, height/2+60);			
				textSize(5);
				textAlign(LEFT, TOP);
				fill(247, 255, 28);
				text("* Add Planet : To add a planet, press the Add Planet button in the top left corner"
						+ "\n* Customise Planets :\t - Press W or S to cycle through the planets. "
						+ "\n                  \t- Within a planet's info box, you can customise the planet's size,\nthe planet's x, y and z coordinates, the eccentricity of a planet's orbit, the texture\nof the planet, and an option to add a moon to the planet. "
						+ "\n                  \t- To add a moon, click the Add Moon button. "
						+ "\n                  \t- To change the texture of the planet, click the Textures button."
						+ "\n* Change Camera Angle : Press 1, 2, or 3 to cycle through the camera angles"
						+ "\n* Change Music : To change the song, click the drop down menu in the top right\ncorner and select a song ", width/2-145, height/2-25);
				popMatrix();
				
				if((mouseX > width/2+155 && mouseX < width/2+520) && (mouseY > height/2+185 && mouseY < height/2+220))
				{
					if(fade == false)
					{
						ins = ins - 4;
						
						if(ins < 50)
						{
							fade = true;
						}
					}
					else
					{
						ins = ins + 4;
						
						if(ins > 250)
						{
							fade = false;
						}
					}
				}
				else
				{
					ins = 255;
				}
				break;
			}
			case 3:
			{
				songs[6].close();
				if(planet_choice == 0)
				{
					background(0);
					pushMatrix();
					translate(width/2f, height * 3f/4f, -width);
					rotateX((float) alpha);
					rotateY((float) beta);
					sun.display();
					for(Planet p: sSystem)
					{
						p.update();
						p.display();
					}
					popMatrix();
				}
				else
				{
					background(0);
					Planet p = sSystem.get(planet_choice - 1);
					p.display2();
				}
				add_planet_b();
				songMenu();
				mouseOver();
				break;
			}
		}
	}
	
	public void keyPressed()
	{
		if(menu_choice == 3)
		{
			if(key == 'w')
			{
				if(planet_choice > -1)
				{
					planet_choice--;
				}
				if(planet_choice == -1)
				{
					planet_choice = sSystem.size();
				}

			}
			if(key == 's')
			{
				if(planet_choice < sSystem.size() + 1)
				{
					planet_choice++;
				}
				if(planet_choice == sSystem.size() + 1)
				{
					planet_choice = 0;
				}
			}
			if(key == '1')
			{
				alpha = 0;
				beta = 0;
			}
			if(key == '2')
			{
				alpha = -0.785;
				beta = 3.927;
			}
			if(key == '3')
			{
				alpha = 0.785;
				beta = 2.356;
			}
		}
		if(key == 'b')
		{
			menu_choice = 1;
		}
	}
	
	public void add_planet_b()
	{
		noStroke();
		fill(0, 157, 219);
		rect(location.x/2f, location.y/2f,
				(size.x + location.x), (size.y + location.y));
		strokeWeight(2);
		stroke(247, 255, 28);

		if(mouseCheck(location.x, size.x, location.y, size.y))
		{
			fill(200);
		}
		else
		{
			fill(119, 112, 127);
		}
		rect((location.x), (location.y),
				size.x, size.y);
		textAlign(PConstants.CENTER, PConstants.CENTER);
		textSize(tSize);
		fill(247, 255, 28);
		text("Add Planet", location.x + size.x /2,
				location.y + size.y / 2);
	}
	
	public void menu_background()
	{
		background(0);
		directionalLight(255, 255 , 255, 0, 5, -10);
		
		pushMatrix();
		translate(0, height/2 - 150, 0);
		rPlanet.display();
		popMatrix();
		
		pushMatrix();
		translate(0, height, 0);
		lPlanet.display();
		popMatrix();
		
		star();
		comets();
	}
	
	public void star()
	{
		float size, x, y, z;
		
		ambientLight(255, 255, 255, width/2, height/2, 0);
		
		//Create stars 
		if( starCheck == false)
		{
			for(int i=-350; i < width+350; i += 15)
			{	
				x = i;
				y = random(-150, height+150);
				z = -300;
				size = random(0, 5);
				
				star = new Star(x, y ,z, size, this);
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
			if(millis() - timer >= 4000)
			{
				startposx = random(-300, 1600);
				startposy = random(-300, 1000);
						
						
				if((startposx < -200 || startposx > 1500) || (startposy < -200 || startposy > 900))
				{
						comet = new Star(startposx, startposy, -180, 15, this);
						comets.add(comet);
						
						comet = new Star(startposx + random(80, 110), startposy - 90, -180, 15, this);
						comets.add(comet);
						
						comet = new Star(startposx - random(120, 150), startposy - 125, -180, 15, this);
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
				timer = millis();
				cometCheck = false;
			}
		}
	}

	
	public void text_menu()
	{
		textAlign(LEFT, BOTTOM);
		fill(255, 75);
		rect(width/2 - 200, height/2 - 150, 400, 275);
		
		stroke(247, 255, 28);
		line(width/2 - 190, height/2 - 95, width/2 + 190, height/2 - 95);
		
		fill(247, 255, 28);
		textSize(45);
		text("SolarWonders", width/2-190, height/2-100);
		
		textSize(40);
		fill(247, 255, 28, play);
		text("Play", width/2-50, height/2-25);
		fill(247, 255, 28, ins);
		text("Instructions", width/2-130, height/2+25);
		fill(247, 255, 28, exit);
		text("Exit", width/2-42, height/2+75);
	}
	
	//method to play the sounds
	public void playSound(AudioPlayer sound)
	{
	  if(sound == null)
	  {
	    return;
	  }
	  sound.rewind();
	  sound.loop(); 
	}//end playSound
	
	public void mouseOver()
	{
		if(menu_choice == 3)
		{
			for(int i = 0; i < 7; i++)
			{
				if(mouseCheck(width * 0.8f, (width * 0.2f) - (height * 0.025f), (height * 0.055f) + (i * (height * 0.03f)), (height * 0.03f)))
				{
					mouse_change[i] = 100;
				}
				else
				{
					mouse_change[i] = 150;
				}
			}
		}
		
		if(menu_choice == 1)
		{
			if(mouseCheck(width/2-50, 105, height/2-55, 35))
			{
				if(fade == false)
				{
					play = play - 4;
					
					if(play < 50)
					{
						fade = true;
					}
				}
				else
				{
					play = play + 4;
					
					if(play > 250)
					{
						fade = false;
					}
				}
			}
			else
			{
				play = 255;
			}
			if(mouseCheck(width/2-130, 270, height/2-5, 35))
			{
				if(fade == false)
				{
					ins = ins - 4;
					
					if(ins < 50)
					{
						fade = true;
					}
				}
				else
				{
					ins = ins + 4;
					
					if(ins > 250)
					{
						fade = false;
					}
				}
			}
			else
			{
				ins = 255;
			}
			if(mouseCheck(width/2-42, 90, height/2+45, 35))
			{
				if(fade == false)
				{
					exit = exit - 4;
					
					if(exit < 50)
					{
						fade = true;
					}
				}
				else
				{
					exit = exit + 4;
					
					if(exit > 250)
					{
						fade = false;
					}
				}
			}
			else
			{
				exit = 255;
			}
		}
	}
	
	public void mousePressed()
	{
		if(menu_choice == 3)
		{
			if(mouseCheck(width * 0.8f, width * 0.02f, height * 0.025f, height * 0.03f))
			{
				music_menu = -music_menu;
			}
			else if(music_menu == 1)
			{
				music_menu = -music_menu;
				for(int i = 0; i < 7; i++)
				{
					if(mouseCheck(width * 0.8f, (width * 0.2f) - (height * 0.025f), (height * 0.055f) + (i * (height * 0.03f)), (height * 0.03f)))
					{
						songs[pause].pause();
						playSound(songs[i]);
						pause = i;
						if(i == 6)
						{
							songs[6].pause();
						}
						playing = music[i];
					}
				}
			}
			
			if(mouseCheck(location.x, size.x, location.y, size.y) && sSystem.size() <= 10)
			{
				Planet planet = new Planet(this, sun.mass, width/4 + interval, 0, 0, 50, 100, 0.2, imgs[0], imgs, sSystem);
				planet.bars();
				sSystem.add(planet);
				planet_choice = sSystem.size();
				interval += width/11;
			}
		}
		if((mouseX > width/2+155 && mouseX < width/2+520) && (mouseY > height/2+185 && mouseY < height/2+220) && menu_choice == 2)
		{
			menu_choice = 1;
		}


		if(menu_choice == 1)
		{
			if(mouseCheck(width/2-50, 105, height/2-55, 35))
			{
				menu_choice = 3;
			}
			else if(mouseCheck(width/2-130, 270, height/2-5, 35))
			{
				menu_choice = 2;
			}
			else if(mouseCheck(width/2-42, 90, height/2+45, 35))
			{
				exit();
			}
		}
	}
	
	boolean mouseCheck(float x1, float x2, float y1, float y2)
	{
	    if(mouseX > x1 && mouseX < (x1 + x2))
	    {
		    if(mouseY > y1 && mouseY < (y1 + y2))
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
	
	public void songMenu()
	{
		hint(DISABLE_DEPTH_TEST);
		pushMatrix();
		noStroke();
		translate(width * 0.8f, height * 0.025f);
		fill(0, 157, 219);
		rect(-(height * 0.005f), -(height * 0.005f), (width * 0.2f) - (height * 0.015f), height * 0.04f);
		fill(119, 112, 127);
		stroke(247, 255, 28);
		rect(0, 0, (width * 0.2f) - (height * 0.025f), height * 0.03f);
		
		textSize(14);
		if(music_menu == 1)
		{
			for(int i = 0; i < 7; i++)
			{
				fill(mouse_change[i]);
				stroke(0);
				rect(0, (height * 0.03f) + (i * (height * 0.03f)), (width * 0.2f) - (height * 0.025f), height * 0.03f);
				fill(247, 255, 28);
				
				textAlign(CENTER, CENTER);
				text(music[i], ((width * 0.2f) - (height * 0.025f))/2f, (height * 0.045f) + (i * (height * 0.03f)));
			}
		}
		
		fill(100);
		stroke(247, 255, 28);
		rect(0, 0, width * 0.02f, height * 0.03f);
		
		fill(0);
		stroke(0);
		triangle(width * 0.001f, height * 0.001f, 
				width * 0.019f, height * 0.001f,
				width * 0.01f, height * 0.029f);
		
		fill(247, 255, 28);
		textAlign(CENTER, CENTER);
		text("Music: " + playing, (((width * 0.2f) - (height * 0.025f) - (width * 0.02f))/2f) + (width * 0.02f), height * 0.015f);
		popMatrix();
		hint(ENABLE_DEPTH_TEST);
	}
	
	public void settings()
	{
		fullScreen(P3D);
	}
	
    public static void main(String[] args)
    {
        PApplet.main("ie.dit.SolarWonders");
    }
}


