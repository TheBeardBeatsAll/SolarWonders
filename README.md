# SolarWonders
OOP Assignment 3 by Max MacDonald, Finn McLaughlin and Lorcan Nolan.

## Concept
After reading through the assignment brief, all of us were excited at the opportunity to work on a programming project within a group environment. We quickly came together as to form a team and began brainstorming ideas and the possible functionalities of each idea.
After much discussion we decided to make a soloar system simulator. However, we wanted this to project to be unique and did not want to simply recreate our current solar system.

Our solar system simulator is customisable to the users' preferences and imaginations. With features that range from changing the planet's size to more complex details such as the eccentricity of a planet's orbit, the user has many possibilities to create their own unique system of worlds.

## Screens
This assignment has three seperate screens:

#### Main Menu:
* This screen allows you to navigate your way through the program.

#### Instructions:
* Contains information on how to use the simulator itself.

#### Play:
* Displays the solar system you have created or are creating.

## Features:
#### Main Menu:
Navigation through the home page is determined by the mouse. If the mouse's location is hovering over a menu option and the mouse is clicked, you will be taken to the screen of whichever menu option you chose. If you chose exit, the program will close. Music will also begin to play in the background.

#### Instructions:
Outlines how to use and control the simultor. Contains the same background as the main menu to give the impression of still being in a menu environment.

#### Play:
When the play button is first selected, the user will be met by the solar system's sun. The sun is located at the centre of the screen and the user is given the option to add a planet in the top left corner or to change the music in the top right.

* Add Planet:
  * To add a planet, the user must hover the mouse over the box and click the mouse. By doing this a default planet will appear orbiting the sun and it will be added to an array list of planets.
  
* Change Music:
  * To change the current song being played in the background, hover the mouse over the down arrow in the top right corner and click the mouse button. This will display a drop-down menu of songs to choose from. To choose a song, point and click with the mouse.

* Customise Planets:
  * After a planet has been added the user can customise the planets by pressing either the W or S keys. By doing this it will bring a planet into focus and display an info box associated with that planet.
  * By pressing the S key, you focus the display on the first planet in the array list. Pressing W will focus the display on the last planet. Once a planet is in focus, press W to scroll down through the planets or S to scroll up through planets.
  * Within a planet's info box, the user can make customisations. The user can customise the planet's size, the planet's x, y and z coordinates, the eccentricity of a planet's orbit, the texture of the planet, and an option to add a moon to the planet.
  * When changing the size, x, y and z coordinates and the eccentricity a scroll bar is used. The user must click on the circular slider and drag it across the line to make changes
  * To add a moon, point and click the mouse on the Add Moon text.
  * To change the texture of the planet, click and point the mouse where it says Textures. This will display a box of 9 different textures to choose from.
* Change Camera Angle:
  * There are 3 different angles available to view the solar system. To change the angle the camera is looking at the planets, press the buttons 1, 2 or 3.
  
## Forces:
A key part of this program is the gravitational forces that were implemented into the code. Any planet that is added has a gravitational pull towards the sun and any moon added has a gravitational pull towards its parent planet. This is done by making the acceleration of an orbiting planet/moon constant but its direction is always towards the centre whatever is being orbited (sun/planet).
Another key element to this simulation that makes it accurate is the eccentricity value. This determines how elliptical the orbits are so every planet can have a unique orbit.
