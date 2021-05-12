import java.awt.*; //Importing Graphics2D and Rectangle

import javax.swing.ImageIcon; //importing ImageIcon

/**
 * NAME: Nelson Correia
 * DATE: June 12, 2014
 * COURSE CODE: ICS 3U1
 * PROGRAM: Space Invaders CPT (Cover class)
 */

public class Cover {

	private int xPos, yPos, width, height, damage;
	private boolean broken = false;
	private ImageIcon imgCover;
	private final ImageIcon DAMAGE1 = new ImageIcon("images\\Dam1.png"), DAMAGE2 = new ImageIcon("images\\Dam2.png"), DAMAGE3 = new ImageIcon("images\\Dam3.png");
	
	public Cover() {

		xPos = 40;
		yPos = 0;
		damage = 0;
		imgCover = new ImageIcon("images\\Cover.png"); //default image, undamaged
		width = imgCover.getIconWidth();
		height = imgCover.getIconHeight();
		broken = false;
	}
	
	public void setPosition(int x, int y) { //modifies the position
		
		xPos = x;
		yPos = y;
	}
	
	public Rectangle getRect() { //used to check for intersections

		return new Rectangle(xPos, yPos, width, height);
	}

	public int getX() { //returns the x position

		return xPos;
	}

	public int getY() { //returns y position

		return yPos;
	}

	public int getWidth() { //returns width

		return width;
	}

	public int getHeight() { //returns height

		return height;
	}
	
	public int getDamage() {
		
		return damage;
	}
	
	public boolean isBroken() { //returns whether the cover is broken
		
		return broken;
	}

	public void setX(int x) { //modifies the x position

		xPos = x;
	}

	public void setY(int y) { //modifies the y position

		yPos = y;
	}

	public void setWidth(int w){ //modifies width

		width = w;
	}

	public void setHeight(int h) { //modifies height

		height = h;
	}
	
	public void setBroken(boolean b){ //sets the cover as broken or not
		
		broken = b;
		if (broken == false)
		{
			damage = 0;
			imgCover = new ImageIcon("images\\Cover.png"); //I'm under the assumption that the user wants to fully repair the barrier if they don't want it broken
		}
	}
	
	public void setImage(ImageIcon img){ //changes the image of the cover
		
		imgCover = img;
	}
	
	public void respawn() { //repairs the barrier, setting damage to 0 and broken as false
		
		broken = false;
		damage = 0;
	}
	
	public void smash() { //damages the cover by a factor of 1 damage
		
		damage++;
		if (damage == 1 || damage == 2) //the image of the barrier depends on how much damage it recieved 
		{
			imgCover = DAMAGE1;
		}
		
		else if (damage == 3 || damage == 4)
		{
			imgCover = DAMAGE2;
		}
		
		else if (damage == 5 || damage == 6)
		{
			imgCover = DAMAGE3;
		}
		
		else if (damage == 7 || damage >= 8)
		{
			imgCover = new ImageIcon("lollololololthisdoesntexist.png"); //makes the barrier invisible, by setting its image to one that doesn't exist
			broken = true;
		}
	}
	
	public void drawCover(Graphics2D g2) { //just draws the barrier
		
		g2.drawImage(imgCover.getImage(), xPos, yPos, null);
	}

}