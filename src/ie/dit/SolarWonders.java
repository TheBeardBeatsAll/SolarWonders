package ie.dit;

import java.util.ArrayList;

import ddf.minim.*;
import processing.core.PApplet;

public class SolarWonders extends PApplet
{
	Planet test;
	Sun sun;
	Boolean moon;
	ArrayList<Planet> planets = new ArrayList<Planet>();
	Minim minim;
	AudioPlayer life, inter, supernaut, man, space, only, dynamite;
	String[] music = new String[7];
	String playing;
	
	public void loadSounds()
	{
	  minim = new Minim(this);       
	  
	  life = minim.loadFile("LifeOnMars.mp3");
	  inter = minim.loadFile("Intergalactic.mp3");
	  supernaut = minim.loadFile("Supernaut.mp3");
	  man = minim.loadFile("ManOnTheMoon.mp3");
	  space = minim.loadFile("OutofSpace.mp3");
	  //only = minim.loadFile("OnlyOfTheUniverse.mp3");
	  //dynamite = minim.loadFile("DeloreanDynamite.mp3");
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
		playing = music[0] = "Nothing Selected";
		music[1] = "Life on Mars - David Bowie";
		music[2] = "Intergalactic - Beastie Boys";
		music[3] = "Supernaut - Black Sabbath";
		music[4] = "Man on the Moon - R.E.M.";
		music[5] = "Only of the Universe - Fatima Yamaha";
		music[6] = "Out of Space - The Prodigy";
	}
	
	public void draw()
	{
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
	
	public void mouseClicked()
	{
		
	}
	
	public void songMenu()
	{
		hint(DISABLE_DEPTH_TEST);
		translate(width * 0.8f, height * 0.025f);
		fill(200);
		stroke(255);
		rect(0, 0, (width * 0.2f) - (height * 0.025f), height * 0.03f);
		
		fill(100);
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
		text("Music: " + playing, ((width * 0.2f) - (height * 0.025f))/2f, height * 0.015f);
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


