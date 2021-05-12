import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class EBullet {
	
	private int xPos, yPos, width, height;
	private ImageIcon imgPBullet;
	
	public EBullet() {

		xPos = 0;
		yPos = -100;
		imgPBullet = new ImageIcon("images\\EBullet.png");
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
	
	public void setPosition(int x, int y){
		
		xPos = x;
		yPos = y;
	}

	public void setWidth(int w){

		width = w;
	}

	public void setHeight(int h) {

		height = h;
	}
	
	public void moveDown(int yPix){
		
		yPos += yPix;
	}
	
	public void drawBullet(Graphics2D g2) {

		g2.drawImage(imgPBullet.getImage(), xPos, yPos, width, height, null);
	}

}
