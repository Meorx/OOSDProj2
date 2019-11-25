import org.newdawn.slick.Input;

public class Extralife extends Sprite {
	private static final String ASSET_PATH = "assets/extralife.png";
	private static float SPEED;
	// The extra life never moves vertically, so a constnant was created.
	private static final int dy = 0;
	// timePassed checks how long the extra life was visible.
	private int timePassed;
	// timeUntilAppearance checks how long has it been since the 
	// extra life was invisible
	private int timeUntilAppearance;
	// timeToMove checks how long has it been since the extra life has moved.
	private int timeToMove = 0;
	private boolean isExtraLiveVisible = false;
	private int delta;
	private int fourteenSeconds = 14000;
	private int twoSeconds = 2000;
	
	
	private boolean moveRight;
	
	private final float getInitialX() {
		return moveRight ? -World.TILE_SIZE / 2
						 : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
	}

	public Extralife(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y);
		this.moveRight = moveRight;
	}
	
	@Override
	public void update(Input input, int delta) {
		int dx = 0;
		this.delta = delta;
		updatedTimeAndStatus();
		
		timeToDisappear();
		isTimeToMove();
		
		if(isTimeToMove() == true) {
			changeDirection();
			timeToMove = 0;
			move(dx + World.TILE_SIZE,dy);
		}
		move(SPEED * delta * (moveRight ? 1 : -1), 0);
		this.delta = delta;
		if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
		 || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
			setX(getInitialX());
		}
	}
	
	public void timeToDisappear() {
		if(((int)timePassed > (fourteenSeconds - 3)) && 
				((int)timePassed < (fourteenSeconds + 3))) {
			setExtraLifeVisibility();
			timeUntilAppearance = World.randomNumberInRange(25,35);
		}
	}
	
	//isTimeToMove() : boolean
	public boolean isTimeToMove() {
		if(((int)timeToMove > (twoSeconds - 3)) && 
				((int)timeToMove < (twoSeconds + 3))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * updatedTimeAndStatus() gets the time elapsed from delta and updates timePassed
	 * and timeLeftToMove. This is to ensure that the turtles appear and disappear. 
	 */
	public void updatedTimeAndStatus() {
		timeToMove = timeToMove + delta; 
		timePassed = timePassed + delta;
		timeUntilAppearance = timeUntilAppearance + delta;
	}
	
	/**
	 * changeDirection() changes the current direction of the extra life to 
	 * the opposite one.
	 */
	public void changeDirection(){
		if(moveRight == true){
			moveRight = false;
		} else {
			moveRight = true;
		}
	}
	
}
