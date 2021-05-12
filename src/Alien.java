import java.awt.Graphics2D; //used to draw images
import java.awt.Rectangle; //getRect method requires this

import javax.swing.ImageIcon; //images are needed for this

/**
 * NAME: Nelson Correia
 * DATE: June 12, 2014
 * COURSE CODE: ICS 3U1
 * PROGRAM: Space Invaders CPT (Alien class)
 */

public class Alien {

	private int xPos, yPos, width, height;
	private ImageIcon imgAlien;
	private boolean dead;

	public Alien(int x, int y, int w, int h, ImageIcon i) { //quite the unique constructor... I simply eased things by adding the Image as an argument

		xPos = x;
		yPos = y;
		width = w;
		height = h;
		imgAlien = i;
		dead = false;
	}

	public void drawAlien(Graphics2D g2) { //draws alien

		g2.drawImage(imgAlien.getImage(), xPos, yPos, width, height, null);
	}

	public int getX() { //returns the x position of the alien

		return xPos;
	}

	public void setX(int x) { //sets the x position of the alien

		xPos = x;
	}

	public int getY() { //returns the y position of the alien

		return yPos;
	}

	public void setY(int y) { //sets the y position of the alien

		yPos = y;
	}

	public int getWidth() { //returns the width of the alien

		return width;
	}

	public void setWidth(int w) { //sets the width of the alien

		width = w;
	}

	public int getHeight() { //returns the height of the alien

		return height;
	}
	
	public void setHeight(int h) { //sets the height of the alien

		height = h;
	}

	public boolean isDead() { //returns whether the alien is dead

		return dead;
	}

	public void setImage (ImageIcon img){ //sets the image to be used by the alien

		imgAlien = img;
	}

	public void move(int xPix) { //changes the x position of the alien

		xPos += xPix;
	}

	public Rectangle getRect() { //creates a rectangle, used to check for intersections between other objects

		return new Rectangle(xPos, yPos, width, height);
	}

	public void kill() { //removes the image for the alien, and makes a boolean of it being dead true

		imgAlien = new ImageIcon("");
		dead = true;
	}

	public void moveDown(int y) { //adds to the Y position

		yPos += y;
	}

	public void respawn() { //simply sets dead to false

		dead = false;
	}

	public void setPosition(int x, int y){ //modifies the position

		xPos = x;
		yPos = y;
	}

}
