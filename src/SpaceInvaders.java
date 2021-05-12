import java.awt.*; //was also needed for the cursor
import java.awt.event.*; //check for mouse motion
import java.util.*; //used to randomly generate the alien that will shoot at the player

import javax.swing.*; //Used to draw the JPanel
import javax.swing.Timer; //Used to have events occur at regular intervals

/**
 * NAME: Nelson Correia
 * DATE: June 12, 2014
 * COURSE CODE: ICS 3U1
 * PROGRAM: Space Invaders CPT
 */



public class SpaceInvaders extends JPanel implements ActionListener, MouseMotionListener, KeyListener, MouseListener {

	private static final long serialVersionUID = 6036138378345051010L; //I am unsure of what this does, but I added it to remove Eclipse's warning
	
	private Timer flyTimer, pBulletTimer, eBulletTimer; //Declaring the three Timer objects
	private Color neonG = new Color(32, 255, 32); // Classic Space Invaders neon green, used to draw the line at the bottom of the screen
	private Ship ship; //declaring Ship object
	private int alienSpeed = 5, score = 0, lives = 3, playerSpeed = 15, alienBSpeed = 15, amountOfAliens = 60, aliensShot = 0; //alienSpeed is how much pixels the alien moves at the Timer's interval
	private Cursor cursor; //declaring the Cursor object
	private ImageIcon imgCursor, ALIEN1 = new ImageIcon("images\\Alien1.png"), ALIEN2 = new ImageIcon("images\\Alien2.png"), ALIEN3 = new ImageIcon("images\\Alien3.png"), ALIEN4 = new ImageIcon("images\\Alien4.png"), ALIEN5 = new ImageIcon("images\\Alien5.png"); //delcaring constant ImageIcon variables, to make the program a bit easier for comprehension
	private ArrayList<Alien> aliens = new ArrayList<Alien>(); //an array list allows the easy removal of alien objects... in other words they go when dead
	private Ship[] lifeShip = new Ship[2]; //An array list to display the amount of ships, indicating lives. These really have nothing but aesthetic value
	private Cover[] cover = new Cover[4]; //declaring the array of Cover objects
	private boolean gameOver = false, canShoot = true, start = false, controls = false, pause = false, victory = false; //booleans are very convenient for use
	private PBullet pb; //Declaring the Player Bullet object
	private EBullet eb; //Declaring the Enemy Bullet object
	private Font font; //Declaring Font object
	private FontMetrics fm; //Declaring FontMetrics object
	private Random rnd; //Declaring Random object

	public static void main(String[] args) {

		new SpaceInvaders();

	}

	public SpaceInvaders() {

		cover[0] = new Cover(); //initializing all 4 cover objects
		cover[1] = new Cover();
		cover[2] = new Cover();
		cover[3] = new Cover();

		font = new Font("Space Invaders", Font.PLAIN, 12); //initializing the Font object, note the custom font used in the program
		fm = getFontMetrics(font); //initializing the FontMetrics object, to be used with the Font object "font"
		flyTimer = new Timer(800, this); //initializing the alien's fly Timer. Note that 800 is the starting value, the delay of this Timer will decrease as the player progresses, causing the aliens to move faster
		pBulletTimer = new Timer(35, this); //initializing the Timer for the player's bullet
		eBulletTimer = new Timer(20, this); //initializing the Timer for the alien's bullet
		rnd = new Random(); //Initializing the Random object

		int xPos = 30, yPos = 10, counter = 0; //temporary int variables...

		for (int i = 1; i <= amountOfAliens; i++) { //Adding all the aliens to the game

			counter++; //for every alien added, increase the counter
			xPos += 27; //move the next alien generated 27 pixels to the right, allowing them to be spaced out.

			if (counter <= 12) //The counter starts from the top, therefore, we start with the Alien5 sprite, which is the aliens that live at the top row
			{
				aliens.add(new Alien(xPos, yPos, 20, 20, ALIEN5)); //we start from the top, therefore the image is ALIEN5, which is found at the top of the alien swarm
			}

			else if (counter <= 24)
			{
				aliens.add(new Alien(xPos, yPos, 20, 20, ALIEN4)); //Below the row of ALIEN5 aliens
			}

			else if (counter <= 36)
			{
				aliens.add(new Alien(xPos, yPos, 20, 20, ALIEN3)); //below ALIEN4
			}

			else if (counter <= 48)
			{
				aliens.add(new Alien(xPos, yPos, 20, 20, ALIEN2)); //Below ALIEN3
			}

			else if (counter <= 60)
			{
				aliens.add(new Alien(xPos, yPos, 20, 20, ALIEN1)); //First row of the alien swarm
			}

			if (counter % 12 == 0) //12 aliens per row, therefore we want to continue with the next
			{
				xPos = 30; //reset the position of the first alien in the second row to 30 
				yPos += 35; //move the row down 35 pixels, so it doesn't overlap another row
			}

		}
		
		ship = new Ship(); //initialzing the Ship object
		pb = new PBullet(); //initializing the PBullet object
		eb = new EBullet(); //initializing the EBullet object

		for (int i = 0; i < lifeShip.length; i++) //because individually declaring all 2 ships is too much work
		{
			lifeShip[i] = new Ship();
		}

		imgCursor = new ImageIcon("images\\cursor.png"); // this image does not exist, and must be this way because I don't want a cursor in my program. If for whatever reason I want a custom cursor, I can add it easily
		cursor = Toolkit.getDefaultToolkit().createCustomCursor(imgCursor.getImage(), new Point(imgCursor.getIconWidth() / 2, imgCursor.getIconHeight() / 2), ""); //initializing the cursor object using the non-existant object so the cursor doesn't show

		setLayout(null);
		setBackground(Color.BLACK); //makes the background of the panel to black
		setFocusable(true);
		addMouseMotionListener(this); //allows the panel to listen for mouse motion
		addMouseListener(this); //allows the panel to listen for what the mouse does
		addKeyListener(this); //allows the panel to listen for any keys pressed
		requestFocus();
		setCursor(cursor); //sets the cursor to the declared Cursor object

		JFrame frame = new JFrame(); //initializing the JFrame object
		frame.setContentPane(this);
		frame.setSize(500, 575); //sets the size of the frame to a nice resolution that is slighly higher than wide
		frame.setLocationRelativeTo(null); //centers the JFrame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes the program stop when the user closes the JFrame
		frame.setResizable(false); //makes the JFrame so that it can not be resized
		frame.setTitle("Space Invaders"); //The title. Straight to the point
		frame.setVisible(true); //Allows the frame to be visible.

		ship.setX(getWidth() / 2 - ship.getWidth() / 2); //centers the ship in the middle of the screen
		ship.setY(getHeight() - 70); //centers the ship in the Y position

		cover[0].setPosition(60, getHeight() - 120); //positioning the cover/shields/barriers
		cover[1].setPosition(160, getHeight() - 120);
		cover[2].setPosition(270, getHeight() - 120);
		cover[3].setPosition(370, getHeight() - 120);

		lifeShip[0].setPosition(70, getHeight() - 30); //just moves the ship, as a visual indication of how many lives the player has
		lifeShip[1].setPosition(105, getHeight() - 30);

		flyTimer.start(); //start those timers
		pBulletTimer.start();
		eBulletTimer.start();
	}

	public void mousePressed(MouseEvent e) { //when the mouse is pressed

		if (canShoot == true && start == true) //if the game is started and the player can shoot
		{
			pb.setX(ship.getX() + (ship.getWidth() / 2) - (pb.getWidth() / 2)); //sets the player bullet's X position to the middle of the ship, where the little cannon is located
			pb.setY(ship.getY() - pb.getHeight()); //sets the Y position of the bullet to the top of the ship
			canShoot = false; //once the bullet is shot the boolean is set to false again, so the player doesn't keep re-positioning the bullet before it can hit anything
		}

		repaint();
	}

	public void mouseMoved(MouseEvent e) {

		if (pause == false) //the ship should only move when the game is not paused, to prevent cheating
		{
			ship.setX(e.getX() - (ship.getWidth() / 2)); //Allows the player to control the ship by moving their mouse (center of the ship, precisely)

			if (ship.getX() + ship.getWidth() >= getWidth()) //checks to see if the ship has passed the X limit (stopping it from going off screen)
			{
				ship.setX(getWidth() - ship.getWidth()); //puts the ship back on the screen if it it brought off.
			}

			if (ship.getX() <= 0) //Prevents the ship from crossing the left boundary
			{
				ship.setX(0); //puts the ship back on the screen if it it brought off.
			}
			repaint(); //super important thing that must never be forgotten
		}
	}

	public void actionPerformed(ActionEvent e) {

		boolean hitWall = false; //it needs to be declared here to avoid error...

		if (e.getSource() == flyTimer) //everything in the timer here is simply controlling the flight of aliens
		{
			for (int i = 0; i < aliens.size(); i++)
			{
				aliens.get(i).move(alienSpeed); //collectively moving all the aliens at the same speed

				if (aliens.get(i).getX() <= 0 || aliens.get(i).getX() + aliens.get(i).getWidth() >= getWidth() && aliens.get(i).isDead() == false) //if one of the aliens hits one of the walls... and mentioned alien is not dead...
				{
					hitWall = true;
					alienSpeed = -alienSpeed; //negates the direction of ALL aliens.
					i = aliens.size(); //breaks the loop once one of the aliens hits the wall
				}
			}


			if (hitWall == true)
			{
				for (int i = 0; i < aliens.size(); i++)
				{
					aliens.get(i).moveDown(20); //moves the aliens down a tad bit, causing them to progress slightly closer to the player
				}
				flyTimer.setDelay(flyTimer.getDelay() - (flyTimer.getDelay() / 5)); //reduces the time taken for the timer, eventually making the aliens faster as they move down, by a factor of 1/7
			}

			for (int i = 0; i < aliens.size(); i++)
			{
				if (aliens.get(i).getY() >= getHeight() - 80 && aliens.get(i).isDead() == false)//deadPoint refers to the position on the Y-axis where if the aliens reach the location, the player fails the game (dead)
				{
					gameOver = true; //also, the aliens can't be all dead in order to kill the player
				}
			}
		}

		else if (e.getSource() == pBulletTimer && start == true && gameOver == false && victory == false) //controlling the timer for the Player's bullets
		{
			pb.moveUp(playerSpeed); //moves the bullet up the screen

			if (pb.getY() - pb.getHeight() <= 0) //if the bullet goes off the screen, the player can shoot again
			{
				canShoot = true;
			}

			for (int i = 0; i < aliens.size(); i++)
			{
				if (pb.getRect().intersects(aliens.get(i).getRect()) && aliens.get(i).isDead() == false)
				{
					aliens.get(i).kill(); //removes the alien that was shot from the arraylist... or sets it invisible
					score += 100; //100 points per alien shot
					aliensShot++; //add another alien as shot
					pb.setY(-100); //Moves the bullet far away so it doesn't go through multiple aliens
				}
			}

			for (int i = 0; i < cover.length; i++)
			{
				if (pb.getRect().intersects(cover[i].getRect()) && cover[i].isBroken() == false)
				{
					cover[i].smash(); //damages the cover by 1 damage (check Cover class to see what I mean)
					pb.setY(-100); //sets the player's bullet off the screen
					repaint();
				}
			}
			
			if (aliensShot >= amountOfAliens) //if the amount of aliens the player has shot equals the total amount of aliens, they killed them all off
			{
				victory = true; //the player won!!!
				score += 1000; //There is a 1000 point bonus for shooting all aliens(winning) rather than shooting some but dying
				score = score + (lives * 500);//the player gains a 500 point bonus for every extra life they have
				pBulletTimer.stop(); //stop the timer so the score doesn't go up too much
			}

			repaint();
		}

		else if (e.getSource() == eBulletTimer && start == true && gameOver == false) //checking for things that happen in the enemy bullet timer
		{
			int r = rnd.nextInt(amountOfAliens - 1); //randomly selects a spot in the ArrayList of aliens

			eb.moveDown(alienBSpeed); //makes the Alien Bullet, eb, move down the screen

			if (aliens.get(r).isDead() == false && eb.getY() >= getHeight()) //if the alien that was selected by int r is not dead, and the bullet has flown off the screen...
			{
				eb.setPosition(aliens.get(r).getX() + (aliens.get(r).getWidth() / 2), aliens.get(r).getY()); //set the position of eb to the selected alien
			}

			if (eb.getRect().intersects(ship.getRect())) //if the alien bullet hits the player's ship
			{
				lives --; //the player loses a life
				eb.setY(1000); //moves the bullet off the screen, prevents the player from losing all their lives from one shot
				if (lives <= 0)
				{
					gameOver = true; //You lose if all lives are gone
				}
			}

			for (int i = 0; i < cover.length; i++)
			{
				if (eb.getRect().intersects(cover[i].getRect()) && cover[i].isBroken() == false) //if the cover isn't broken and the alien's bullet hits it...
				{
					cover[i].smash(); //damage the cover
					eb.setY(1000); //moves the bullet off the screen so it can be shot again and also prevents the bullet from damaging the barrier multiple times with a single shot
				}
			}
		}

		repaint(); //important!

	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_G) //temporary debugging
		{
			gameOver = true;
		}

		else if (e.getKeyCode() == KeyEvent.VK_SPACE) //checks to see if the player wants to start the game
		{
			start = true;
		}

		else if (e.getKeyCode() == KeyEvent.VK_C) //checks to see if the player wants to check the controls
		{
			controls = true;
		}

		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) //if the player presses escape to pause
		{
			
			if (pause == false) //if the game isn't paused, pause it
			{
				pause = true;
				flyTimer.stop(); //stops all the timers so things don't happen when the game is paused
				eBulletTimer.stop();
				pBulletTimer.stop();
				repaint();
			}
			
			else if (pause == true) //if the game is paused, unpause it
			{
				pause = false;
				flyTimer.start(); //starts the timers again so the game doesn't stay frozen
				eBulletTimer.start();
				pBulletTimer.start();
			}
			
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver == true || victory == true) //when the player is asked to press space when they lose or win the game
		{
			gameOver = false; //resets the game's stats...
			victory = false;
			lives = 3; //they must regain all their lives
			aliensShot = 0; //aliens that were shot are all reset
			score = 0; //prevents accumulating score from previous games
			flyTimer.setDelay(800); //resets the delay to the initial value
			flyTimer.start(); //resets all the timers, because they stop when the game is lost or beat
			pBulletTimer.start();
			eBulletTimer.start();
			
			eb.setY(1000); //sets enemy bullet off the screen, preventing it from hitting the player when the game restarts
			
			for (int i = 0; i < 4; i++) 
			{
				cover[i].respawn(); //individually repairing all cover is far too redundant
				cover[i].setImage(new ImageIcon("images\\Cover.png")); //resets the images of all the covers
			}

			int xPos = 30, yPos = 10, counter = 0;

			for (int i = 0; i <= amountOfAliens; i++) //respawning the aliens, this sequence is very similar to the one used to create all the aliens at the beginning of the program
			{ 
				counter++;
				xPos += 27;

				if (counter <= 12) 
				{
					aliens.get(i).setImage(ALIEN5);
					aliens.get(i).respawn();
					aliens.get(i).setPosition(xPos, yPos);
				}

				else if (counter <= 24)
				{
					aliens.get(i).setImage(ALIEN4);
					aliens.get(i).respawn();
					aliens.get(i).setPosition(xPos, yPos);
				}

				else if (counter <= 36)
				{
					aliens.get(i).setImage(ALIEN3);	
					aliens.get(i).respawn();
					aliens.get(i).setPosition(xPos, yPos);
				}

				else if (counter <= 48)
				{
					aliens.get(i).setImage(ALIEN2);
					aliens.get(i).respawn();
					aliens.get(i).setPosition(xPos, yPos);
				}

				else if (counter <= 60)
				{
					aliens.get(i).setImage(ALIEN1);
					aliens.get(i).respawn();
					aliens.get(i).setPosition(xPos, yPos);
				}

				if (counter % 12 == 0) //12 aliens per row, therefore we want to continue with the next
				{
					xPos = 30;
					yPos += 35;
				}
				
				repaint();

			}
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g); //prevents transparent frame error
		Graphics2D g2 = (Graphics2D) g; //casting Graphics object into a Graphics2D object
		g2.setFont(font); //sets the font used to draw text to the custom font

		if (start == false && controls == false) //if the player didn't start and didn't request to see the controls
		{
			g2.setColor(Color.WHITE);
			g2.drawString("SPACE INVADERS", getWidth() / 2 - fm.stringWidth("SPACE INVADERS") / 2, 80);
			g2.drawString("PRESS SPACE TO PLAY", getWidth() / 2 - fm.stringWidth("PRESS SPACE TO PLAY") / 2, 180);
			g2.drawString("PRESS C FOR CONTROLS", getWidth() / 2 - fm.stringWidth("PRESS C FOR CONTROLS") / 2, 240);
		}

		else if (controls == true && start == false) //if the player didn't start and requested to see controls
		{
			g2.setColor(Color.WHITE);
			g2.drawString("CLICK TO SHOOT", getWidth() / 2 - fm.stringWidth("CLICK TO SHOOT") / 2, 80);
			g2.drawString("MOUSE TO MOVE", getWidth() / 2 - fm.stringWidth("MOUSE TO MOVE") / 2, 120);
			g2.drawString("SPACE TO START", getWidth() / 2 - fm.stringWidth("SPACE TO START") / 2, 160);
			g2.drawString("ESC TO PAUSE", getWidth() / 2 - fm.stringWidth("ESC TO PAUSE") / 2, 200);

			g2.drawString("YOUR OBJECTIVE IS TO DESTROY ALL ALIENS BEFORE THEY GET TO", 20, 240);
			g2.drawString("YOU. USE THE GREEN SHIELDS FOR COVER. ONLY ONE PLAYER ", 20, 260);
			g2.drawString("PROJECTILE CAN BE ON THE SCREEN AT A TIME, SO BE CAREFUL ", 20, 280);
			g2.drawString("BEFORE YOU SHOOT.", 20, 300);
		}

		else if (start == true) //if the player started the game
		{
			controls = false; //must be set to false, preventing the controls from being displayed on the screen

			if (pause == true) //if the game is paused
			{
				g2.setColor(Color.WHITE);
				g2.drawString("PAUSED", getWidth() / 2 - fm.stringWidth("PAUSED") / 2, getHeight() / 2 - 10); //literally just says paused in the middle of the screen
			}

			if (gameOver == false) //if the game didn't end
			{
				g2.setColor(neonG); //only used to draw the line. The rest of the game is images, and is unaffected by this
				ship.drawShip(g2);
				g2.drawLine(0, getHeight() - 35, getWidth(), getHeight() - 35);

				for (int i = 0; i < aliens.size(); i++) 
				{
					aliens.get(i).drawAlien(g2);
				}

				cover[0].drawCover(g2); //draw all the cover
				cover[1].drawCover(g2);
				cover[2].drawCover(g2);
				cover[3].drawCover(g2);

				g2.setColor(Color.WHITE);
				g2.drawString("SCORE:" + score, getWidth() - 120, getHeight() - 15); //output the user's score
				g2.drawString("LIVES:" + lives, 10, getHeight() - 15); //output the users lives, as an int

				if (lives >= 2) //if they have 2 or more lives, draw the first ship in the corner
				{
					lifeShip[0].drawShip(g2);
				}

				if (lives >= 3) //if they have 3 or more lives, draw both ships in the corner
				{
					lifeShip[1].drawShip(g2);
				}

				pb.drawBullet(g2); //drawing the bullets
				eb.drawBullet(g2);
			}

			if (gameOver == true) //display the game over message, indicating the player lost
			{
				g2.setColor(Color.WHITE);
				g2.drawString("YOU HAVE LOST AGAINST THE ALIENS", getWidth() / 2 - fm.stringWidth("YOU HAVE LOST AGAINST THE ALIENS") / 2, 160);
				g2.drawString("GAME OVER!", getWidth() / 2 - fm.stringWidth("GAME OVER!") / 2, 200);
				g2.drawString("YOUR SCORE: " + score, getWidth() / 2 - fm.stringWidth("YOUR SCORE: " + score) / 2, 250);
				g2.drawString("PLAY AGAIN?", getWidth() / 2 - fm.stringWidth("PLAY AGAIN?") / 2, 300);
				g2.drawString("PRESS SPACE", getWidth() / 2 - fm.stringWidth("PRESS SPACE") / 2, 330); //prompt to play again
				repaint();
			}
			
			else if (victory == true) //indicating the victory message when the player kills all the aliens
			{
				g2.setColor(Color.WHITE);
				g2.drawString("CONGRATULATIONS!", getWidth() / 2 - fm.stringWidth("CONGRATULATIONS!") / 2, 160);
				g2.drawString("YOU HAVE DEFEATED THE ALIENS!", getWidth() / 2 - fm.stringWidth("YOU HAVE DEFEATED THE ALIENS!") / 2, 200);
				eBulletTimer.stop();
				pBulletTimer.stop();
				flyTimer.stop();
				g2.drawString("YOUR SCORE: " + score, getWidth() / 2 - fm.stringWidth("YOUR SCORE: " + score) / 2, 250);
				g2.drawString("PLAY AGAIN?", getWidth() / 2 - fm.stringWidth("PLAY AGAIN?") / 2, 300);
				g2.drawString("PRESS SPACE", getWidth() / 2 - fm.stringWidth("PRESS SPACE") / 2, 330); //prompt to play again
				repaint();
			}
		}
	}


	//the barren wasteland of unused methods below
	public void mouseReleased(MouseEvent arg0) {}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

	public void mouseDragged(MouseEvent arg0) {}

	public void mouseClicked(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

}