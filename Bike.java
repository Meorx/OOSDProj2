import org.newdawn.slick.Input;

public class Bike extends Sprite {
	// Here are some constants -- the source code and the speed of the bike.
	private static final String ASSET_PATH = "assets/bike.png";
	private static final float SPEED = 0.2f;
	private boolean moveRight;
	
	/**
	 * This is the constructor of this class. Sends the parents class, Sprites, 
	 * the image source, x coordinate, y coordinate, and a tag indicating that 
	 * the bike is deadly for the player. 
	 */
	public Bike(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD });
		
		this.moveRight = moveRight;
	}
	
	/**
	 * update() updates the bike's coordinates. The first if statement 
	 * decides which direction the bike is going. Since the bike is supposed to 
	 * reverse its direction when it reaches the screen's sides, this if
	 * statements ensures that occurs. 
	 */
	@Override
	public void update(Input input, int delta) {
		if (((int)getX() == 24) || ((int)getX() == 1000)){
			if (moveRight == true) {
				moveRight = false;
			} else {
				moveRight = true;
			}
		}
		move(SPEED * delta * (moveRight ? 1 : -1), 0);
	}
}
