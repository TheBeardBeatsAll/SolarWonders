package ie.dit;

import java.util.ArrayList;

import ddf.minim.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PShape;

public class SolarWonders extends PApplet
{
	Sun sun;

	ArrayList<Planet> planets = new ArrayList<Planet>();
	ArrayList<Star> stars = new ArrayList<Star>();
	ArrayList<Star> comets = new ArrayList<Star>();
	
	Minim minim;
	AudioPlayer[] songs = new AudioPlayer[7]; 
	
	PImage[] imgs = new PImage[13];
	PFont font;
	
	int music_menu, pause, timer;
	int play, ins, exit;
	int[] mouse_change = new int[7];

	String playing;
	String[] music = {"Out of Space - The Prodigy", "Life on Mars - David Bowie", 
			"Intergalactic - Beastie Boys", "Supernaut - Black Sabbath", 
			"Man on the Moon - R.E.M.", "Only of the Universe - F. Y.", "Nothing Selected"};
	String[] fnames = {"earth", "blue", "brown", "magma", "water", "gas", "ice", "red", "coarse", "sun", "moon", "moon2", "moon3"};
	
	Star star, comet, trail;
	Planet rPlanet, lPlanet;
	float startposx,  startposy;
	
	boolean starCheck, fade, cometCheck, musicCheck;
	PShape moon, bplanet, rplanet;
	
	public void setup()
	{
		smooth();
		sun = new Sun(this);
		loadData();
		rPlanet = new Planet(this, sun.mass, width - width/5, -width/7, 0, 200, 100, 0, imgs[8]);
		lPlanet = new Planet(this, sun.mass, width/27, width/6, 0, 450, 100, 0, imgs[4]);
		rPlanet.add_moon(0, width/7, 0, 50, 100, 0, imgs[11]);
		playing = music[6];
		music_menu = -1;
		pause = timer = 0;
		startposx = startposy = 0;
		starCheck = cometCheck = fade = false;
		musicCheck = true;
		play = ins = exit = 255;
		textFont(font);
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
		menu();
		
		
//		mouseOver();
//		pushMatrix();
//		translate(width/2f, height * 3f/4f, -width * 2f/3f);
//		sun.display();
//		test.update();
//		test.display();
//		popMatrix();
//		songMenu();
	}
	
	public void menu()
	{
		if(musicCheck)
		{
			playSound(songs[6]);
			musicCheck = false;
		}
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
		text();
	}
	
	public void star()
	{
		float size, x, y, z;
		
		ambientLight(255, 255, 255, width/2, height/2, 0);
		//parent.directionalLight(255, 255 , 255, width/2, 0, -200); //Deciding between the two
		
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

	
	public void text()
	{
		fill(255, 75);
		rect(width/2 - 200, height/2 - 150, 400, 275);
		
		stroke(255);
		line(width/2 - 190, height/2 - 95, width/2 + 190, height/2 - 95);
		
		fill(255, 225);
		textSize(45);
		text("SolarWonders", width/2-190, height/2-100);
		
		textSize(40);
		fill(255, play);
		text("Play", width/2-50, height/2-25);
		fill(255, ins);
		text("Instructions", width/2-130, height/2+25);
		fill(255, exit);
		text("Exit", width/2-42, height/2+75);
		
		if((mouseX > width/2-50 && mouseX < width/2+55) && (mouseY > height/2-55 && mouseY < height/2-20) )
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
		if((mouseX > width/2-130 && mouseX < width/2+140) && (mouseY > height/2-5 && mouseY < height/2+30))
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
		if((mouseX > width/2-42 && mouseX < width/2+48) && (mouseY > height/2+45 && mouseY < height/2+80) )
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
	
	public void mousePressed()
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
		translate(width * 0.8f, height * 0.025f);
		fill(200);
		stroke(255);
		rect(0, 0, (width * 0.2f) - (height * 0.025f), height * 0.03f);
		
		if(music_menu == 1)
		{
			for(int i = 0; i < 7; i++)
			{
				fill(mouse_change[i]);
				stroke(255);
				rect(0, (height * 0.03f) + (i * (height * 0.03f)), (width * 0.2f) - (height * 0.025f), height * 0.03f);
				fill(0);
				stroke(0);
				textSize(12);
				textAlign(CENTER, CENTER);
				text(music[i], ((width * 0.2f) - (height * 0.025f))/2f, (height * 0.045f) + (i * (height * 0.03f)));
			}
		}
		
		fill(100);
		stroke(255);
		rect(0, 0, width * 0.02f, height * 0.03f);
		
		fill(0);
		stroke(0);
		triangle(width * 0.001f, height * 0.001f, 
				width * 0.019f, height * 0.001f,
				width * 0.01f, height * 0.029f);
		
		fill(0);
		stroke(0);
		textSize(12);
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


