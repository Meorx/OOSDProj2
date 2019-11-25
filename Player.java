import org.newdawn.slick.Input;


public class Player extends Sprite {
	private static final String ASSET_PATH = "assets/frog.png";
	private static int dy = 0;
	private static final int Slot1Start = 72;
	private static final int Slot1End = 168;
	private static final int Slot2Start = 264;
	private static final int Slot2End = 360;
	private static final int Slot3Start = 456;
	private static final int Slot3End = 552;
	private static final int Slot4Start = 648;
	private static final int Slot4End = 744;
	private static final int Slot5Start = 840;
	private static final int Slot5End = 936;
	private int delta;
	
	
	/**
	 * Player() is a constructor of this class. It calls its superclass, Sprites, to create the object
	 * with the ASSET_PATH being the image source, and x and y being the coordinates. 
	 */
	public Player(float x, float y) {
		super(ASSET_PATH, x, y);
	}
	
	/**
	 * update() updates the player's position and decides on the player's fate depending where they are.
	 * There are set bounds, which ensure that the player cannot move the frog (via keyboard input)
	 * outside the screen. 
	 * update() also checks for player's position changed by other factors such as push-able sprites
	 * like logs and bulldozers. In such cases, there is an if-statement that decreases player's
	 * lives if they get pushed outside screen's bounds.
	 */
	@Override
	public void update(Input input, int delta) {
		int dx = 0,
			dy = 0;
		// Make user move by one tile. 
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dx -= World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dx += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			dy += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			dy -= World.TILE_SIZE;
		}
		
		// This ensures  the frog stays on screen
		if (getX() + dx - World.TILE_SIZE / 2 < 0 || getX() + dx + World.TILE_SIZE / 2 	> App.SCREEN_WIDTH) {
			dx = 0;
		}
		if (getY() + dy - World.TILE_SIZE / 2 < 0 || getY() + dy + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT) {
			dy = 0;
		}
		
		// The only time when a player can get off the screen is when the player is being pushed. 
		// Hence, when the player gets off screen (regardless by what they are being pushed by),
		// they loose a life.
		if(getX() > App.SCREEN_WIDTH) {
			World.changeLives(-1);
			resetToOffsetPosition();
		}
		
		// Fill out the black holes when the user gets into that hole's specific X coordinate range and
		// reset the user's position to the offset position.
		// Add else where dy = 0 if getX() is not matching slots??!?!??!?!??!?!?
		if(getY() == 48) {
			if((getX() > Slot1Start) && (getX() < Slot1End)) {
				World.setHole(1);
				resetToOffsetPosition();
			}
			else if((getX() > Slot2Start) && (getX() < Slot2End)) {
				World.setHole(2);
				resetToOffsetPosition();
			}
			else if((getX() > Slot3Start) && (getX() < Slot3End)) {
				World.setHole(3);
				resetToOffsetPosition();
			}
			else if((getX() > Slot4Start) && (getX() < Slot4End)) {
				World.setHole(4);
				resetToOffsetPosition();
			}
			else if((getX() > Slot5Start) && (getX() < Slot5End)) {
				World.setHole(5);
				resetToOffsetPosition();
			}
			// This else ensures that the player doesn't get into the trees.
			else {
				dy += World.TILE_SIZE;
			}
		}
		
		this.delta = delta;
		move(dx, dy);
	}
	
	/**
	 * onCollision() detects collisions between the player and the rest of the world. However, 
	 * such interactions depend on with what the player is intersecting with. If it's a deadly
	 * object with a HAZARD tag, then the player loses a life. However, if it's a push-able
	 * object such as bulldozer or log, then the player is just pushed instead.
	 */
	@Override
	//move(Sprite.getX(), dy);
	public void onCollision(Sprite other) {
		if((other.hasTag(Sprite.RIDEABLE))) {
	    	float speed = other.getSpeed();
	    	boolean isMoveingRight = other.getMoveRight();
	    	int direction;
	    	
	    	if(isMoveingRight == true) {
	    		direction = 1;
	    	} else {
	    		direction = -1;
	    	}
	    	move(speed * delta * direction, dy);	
	    } 
	 //   if (other.hasTag(Sprite.HAZARD)) {
	//				World.changeLives(-1);
	//				resetToOffsetPosition();
	//	}
	}
}
