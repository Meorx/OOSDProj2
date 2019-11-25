import org.newdawn.slick.Input;

public class Bus extends Sprite {
	private static final String ASSET_PATH = "assets/bus.png";
	private static final float SPEED = 0.15f;
	private boolean moveRight;
	
	/**
	 * getInitialX() is a methods that provides the position where the bus 
	 * will reappear, depending on its direction. If it is going to the right and 
	 * has reached the right edge of the screen, it will reappear on the opposite
	 * side -- the left edge of the screen.
	 */
	private final float getInitialX() {
		return moveRight ? -World.TILE_SIZE / 2
						 : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
	}
	
	/**
	 * This is the constructor of this class. It sends the parents class, Sprites, 
	 * the image source, x coordinate, y coordinate, a tag indicating that 
	 * the bus is a hazard to the player, and the speed of the bus.
	 */
	public Bus(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD });
		this.moveRight = moveRight;
	}
	
	/**
	 * update() updates the bus' coordinates and ensures that if the bus is on 
	 * a trajectory to go beyond the screen, it reappears  on the other side by 
	 * calling setX(getInitialX()) which sets X coordinate to its offset value.
	 */
	@Override
	public void update(Input input, int delta) {
		move(SPEED * delta * (moveRight ? 1 : -1), 0);
		
		// Checks if the vehicle has moved off the screen. If it di, then it
		// reappears on the other side.
		if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
		 || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
			setX(getInitialX());
		}
	}
}
