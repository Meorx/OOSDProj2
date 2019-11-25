//import utilities.BoundingBox;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class Sprite {
	// This is a defined constant to avoid typos
	public final static String HAZARD = "hazard";
	public final static String RIDEABLE = "rideable";
	public final static String WATER = "water";
	
	public static float SPEED;
	private static boolean seeTurtles = false;
	private BoundingBox bounds;
	private Image image;
	private float x;
	private float y;
	private boolean moveRight;
	private String[] tags;
	
	/**
	 * This is one type of a constructor, primarily for the objects that 
	 * do no affect the player in any way such as grass tiles.
	 */
	public Sprite(String imageSrc, float x, float y) {
		setupSprite(imageSrc, x, y);
	}
	
	/**
	 * This constructor, primarily for the objects that are deadly to 
	 * the player and that's all they do such as buses and bikes.
	 */
	public Sprite(String imageSrc, float x, float y, String[] tags) {
		setupSprite(imageSrc, x, y);
		this.tags = tags;
		
	/**
	 * This constructor, primarily for the objects that are not deadly to 
	 * the user, but they affect the player's position. That is why this 
	 * constructor also takes in the speed. 
	 */
	}
	public Sprite(String imageSrc, float x, float y, String[] tags, boolean moveRight, float speed) {
		setupSprite(imageSrc, x, y);
		this.tags = tags;
		this.moveRight = moveRight;
		this.SPEED = speed;
	}
	
	/**
	 * setupSprite() sets up the sprites by presenting them according to their
	 * images and then frames them in a bounding box, so that they take an affect
	 * on the user (e.g. if they are hazardous, they deprive the player of a life).
	 */
	private void setupSprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		
		bounds = new BoundingBox(image, (int)x, (int)y);
		
		tags = new String[0];		
	}

	/**
	 * The following couple methods are simple setters and getters.
	 * This method sets the x position of the sprite.
	 * @param x	 the target x position
	 */
	public final void setX(float x) { 
		this.x = x; bounds.setX((int)x); 
    }
	/**
	 * Sets the y position of the sprite.
	 * @param y	 the target y position
	 */
	public final void setY(float y) { this.y = y; bounds.setY((int)y); }
	/**
	 * Accesses the x position of the sprite.
	 * @return	the x position of the sprite
	 */
	public final float getX() { 
		return x; 
    }
	/**
	 * Accesses the y position of the sprite.
	 * @return	the y position of the sprite
	 */
	public final float getY() { 
		return y; 
	}
	
	/**
	 * Accesses the moveRight() to indicate the direction of the sprite and
	 * returns the direction in form of a boolean.
	 */
	public final boolean getMoveRight() {
		return moveRight;
	}
	
	/**
	 * Accesses the getSpeed() to indicate the speed of the sprite (that 
	 * obviously moves at speed) and returns the speed.
	 */
	public final float getSpeed() {
		return SPEED;
	}
	
	/**
	 * Sets the turtles' visibility to the opposite of the current one.
	 */
	public static void setTurtleVisibility() {
		if(seeTurtles == true) {
			seeTurtles = false;
		} else {
			seeTurtles = true;
		}
	}
	
	/**
	 * Accesses the visibility of the turtles and returns their current
	 * visibility.
	 */
	public static boolean getTurtleVisibility() {
		return seeTurtles;
	}
	
	/**
	 * move() moves the object in accordance to the difference in their
	 * position, indicated by dx (horizontal change) and dy (veritcal change).
	 */
	public final void move(float dx, float dy) {
		setX(x + dx);
		setY(y + dy);
	}
	
	/**
	 * onScreen() returns whether an object would be visible on the screen given
	 * the provided coordinates x and y.
	 */
	public final boolean onScreen(float x, float y) {
		return !(x + World.TILE_SIZE / 2 > App.SCREEN_WIDTH || x - World.TILE_SIZE / 2 < 0
			 || y + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT || y - World.TILE_SIZE / 2 < 0);
	}
	
	/**
	 * onScreen() returns whether an object is be visible on the screen 
	 * in this moment, given its current x and y coordinates.
	 */
	public final boolean onScreen() {
		return onScreen(getX(), getY());
	}
	
	/**
	 * collides() returns a boolean which indicates if two objects
	 * have collided, specifically the player and other sprites.
	 */
	public final boolean collides(Sprite other) {
		return bounds.intersects(other.bounds);
	}
	
	/**
	 * updates() updates the sprites, and it's actually just a template
	 * that is meant to be overriden since it has no content inside.
	 */
	public void update(Input input, int delta) { }
	
	/**
	 * onCollision() updates the sprites, and it's actually just a template
	 * that is meant to be overriden since it has no content inside.
	 */
	public void onCollision(Sprite other) { }
	
	/**
	 * render() takes in the coordinates of the object, x and y. Then it
	 * draws it centered on the screen.
	 */
	public void render() {
		image.drawCentered(x, y);
	}
	
	/**
	 * hasTag() returns a boolean that indicates if that specific sprite
	 * has a tag and if it does, what kind of tag is it.
	 */
	public boolean hasTag(String tag) {
		for (String test : tags) {
			if (tag.equals(test)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * resetToOffsetPosition() is specifically for the player. It resets 
	 * the player's position to the original, starting one. This method is 
	 * called whenever a player either reaches a black hole or dies. 
	 */
	public void resetToOffsetPosition() {
		this.x = App.SCREEN_WIDTH / 2;
		this.y = App.SCREEN_HEIGHT - World.TILE_SIZE;
	}
	
}
