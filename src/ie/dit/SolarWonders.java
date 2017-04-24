package ie.dit;

import java.util.ArrayList;

import ddf.minim.*;
import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet test;
	Sun sun;
	Boolean moon;
	int music_menu, pause;
	ArrayList<Planet> planets = new ArrayList<Planet>();
	Minim minim;
	AudioPlayer[] songs = new AudioPlayer[7]; 
	String[] music = new String[7];
	String playing;
	int[] mouse_change = new int[7];

	public void loadSounds()
	{
	  minim = new Minim(this);       
	  
	  for(int i = 0; i < 7; i++)
	  {
		  songs[i] = minim.loadFile("song" + i + ".mp3");
	  }
	}//end loadSounds

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
	
	public void setup()
	{
		smooth();
		sun = new Sun(this);
		test = new Planet(this, sun.mass, width/3f, width/3f, width/3f, 30, 100, 0);
		planets.add(test);
		moon = true;
		loadSounds();
		playing = music[6] = "Nothing Selected";
		music[1] = "Life on Mars - David Bowie";
		music[2] = "Intergalactic - Beastie Boys";
		music[3] = "Supernaut - Black Sabbath";
		music[4] = "Man on the Moon - R.E.M.";
		music[5] = "Only of the Universe - F. Y.";
		music[0] = "Out of Space - The Prodigy";
		music_menu = -1;
		pause = 0;
	}
	
	public void draw()
	{
		mouseOver();
		background(0);
		pushMatrix();
		translate(width/2f, height * 3f/4f, -width * 2f/3f);
		sun.display();
		test.update();
		if(moon)
		{
			test.add_moon(width/12f, width/12f, 0, 10, 10, 0);
			moon = false;
		}
		test.display();
		popMatrix();
		songMenu();
	}
	
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


