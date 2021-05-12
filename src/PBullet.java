import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class PBullet {
	
	private int xPos, yPos, width, height;
	private ImageIcon imgPBullet;
	
	public PBullet() {

		xPos = 0;
		yPos = -100;
		imgPBullet = new ImageIcon("images\\PBullet.png");
		width = 3;
		height = 10;
	}
	
	public Rectangle getRect() {
		
		return new Rectangle(xPos, yPos, width, height);
	}
	
	public int getX() {

		return xPos;
	}

	public int getY() {

		return yPos;
	}

	public int getWidth() {

		return width;
	}

	public int getHeight() {

		return height;
	}

	public void setX(int x) {

		xPos = x;
	}

	public void setY(int y) {

		yPos = y;
	}

	public void setWidth(int w){

		width = w;
	}

	public void setHeight(int h) {

		height = h;
	}
	
	public void moveUp(int yPix){
		
		yPos -= yPix;
	}
	
	public void drawBullet(Graphics2D g2) {

		g2.drawImage(imgPBullet.getImage(), xPos, yPos, width, height, null);
	}

}
