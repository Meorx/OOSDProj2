import org.newdawn.slick.Input;

public class Turtles extends Sprite {
	
	private static final String ASSET_PATH = "assets/turtles.png";
	private static final float SPEED = 0.15f;
	// Those seconds are constants. However, since Slick counts in miliseconds, 
	// the seconds have been converted.
	private final int sevenSeconds = 7000;
	private final int twoSeconds = 2000;
	// timeUnderWater is set to 3, so that it does not start before timeAboveWater.
	private float timeUnderWater = 0;
	private float timeAboveWater = 7; 
	private int delta;
	private boolean moveRight;
	
	/**
	 * getInitialX() is a methods that provides the position where the turtles 
	 * will reappear, depending on its direction. If it is going to the right and 
	 * has reached the right edge of the screen, it will reappear on the opposite
	 * side -- the left edge of the screen.
	 */
	private final float getInitialX() {
		return moveRight ? -World.TILE_SIZE / 2
						 : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
	}
	
	/**
	 * The constructor of this class. Sends the parents class, Sprites, 
	 * the image source, x coordinate, y coordinate, tag indicating that the
	 * player can ride turtles, and the turtles' speed.
	 */
	public Turtles(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y, new String[] { Sprite.RIDEABLE }, moveRight, SPEED);
		this.moveRight = moveRight;
		
	}
	
	/**
	 *  update() checks whether it is time for the turtles
	 *  to go back under the water or to resurface.
	 *  If the time has come, the method flips the turtles visibility by 
	 *  calling setTurtleVisibility() located in the parent class, Sprite.
	 *  If the turtle were visible, then the methods sets timeUnderWater to zero, 
	 *  because the turtles have freshly dove under water. Opposite happens
	 *  when turtles were under water.
 	 *  Since the updated times do not always equal to round  7 seconds (7000 
 	 *  miliseconds), there I have created a range of acceptable values that 
 	 *  approximate 7 seconds. The resulting values have an accuracy of
	 *  +/- 0.003 seconds is accepted. 
	 *  Moreover, timeAboveWater and timeUnderWater are mutually exclusive. 
	 *  That means that only one of them reaches their set time (7 seconds 
	 *  for timeAboveWater and 2 seconds timeUnderWater) at a time. 
	 */
	@Override
	public void update(Input input, int delta) {
		this.delta = delta;
		updatedTimeAndStatus();
		
		if(((int)timeAboveWater > (sevenSeconds - 3)) && 
				((int)timeAboveWater < (sevenSeconds + 3))) {
			setTurtleVisibility();
			timeUnderWater = 0;
			System.out.println("time above water: " + timeAboveWater);
			
		}
		if(((int)timeUnderWater > (twoSeconds - 3)) && 
				((int)timeUnderWater < (twoSeconds + 3))) {
			setTurtleVisibility();
			timeAboveWater = 0;
		}
		
		//Only if turtles are visible, the turtles are moved.
		if(getTurtleVisibility() == true ) {
			move(SPEED * delta * (moveRight ? 1 : -1), 0);
			this.delta = delta;

			if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
			 || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
				setX(getInitialX());
			}
		}
	}
	
	/**
	 * updatedTimeAndStatus() gets the time elapsed from delta and updates timeAboveWater
	 * and timeUnderWater. This is to ensure that the turtles appear and disappear. 
	 */
	public void updatedTimeAndStatus() {
		timeAboveWater = timeAboveWater + delta; 
		timeUnderWater = timeUnderWater + delta;
	}
}
