import java.awt.Graphics2D; //importing Graphics2D and rectangle
import java.awt.Rectangle;

import javax.swing.ImageIcon; //importing ImageIcon

/**
 * NAME: Nelson Correia
 * DATE: June 12, 2014
 * COURSE CODE: ICS 3U1
 * PROGRAM: Space Invaders CPT (Ship class)
 */

public class Ship { //the name of this class is a mistake, since the player controls a laser cannon, not a ship...

	public int xPos, yPos, width, height, bulletSpeed;
	private ImageIcon imgShip;
	public PBullet pb;

	public Ship() { //default constructor

		xPos = getWidth() / 2 + width / 2;
		yPos = getHeight() - 60;
		width = 27;
		height = 20;
		bulletSpeed = 10;
		imgShip = new ImageIcon("images\\Ship.png");
	}

	public Rectangle getRect() { //used for intersections

		return new Rectangle(xPos, yPos, width, height);
	}

	public int getX() { //returns the x position

		return xPos;
	}

	public int getY() { //returns y postion

		return yPos;
	}

	public int getWidth() { //returns width

		return width;
	}

	public int getHeight() { //returns height

		return height;
	}

	public void setX(int x) { //changes x positon

		xPos = x;
	}

	public void setY(int y) { //changes y postion

		yPos = y;
	}

	public void setPosition(int x, int y) { //changes position

		xPos = x;
		yPos = y;
	}

	public void setWidth(int w){ //modifies width

		width = w;
	}

	public void setHeight(int h) { //modifies height

		height = h;
	}

	public void drawShip(Graphics2D g2) { //draws the ship

		g2.drawImage(imgShip.getImage(), xPos, yPos, width, height, null);
	}


}
