public class Tile extends Sprite {
	private static final String GRASS_PATH = "assets/grass.png";
	private static final String WATER_PATH = "assets/water.png";
	private static final String TREE_PATH = "assets/tree.png";
	
	/**
	 * createGrassTile() creates an object of a type Tile with x and y coordinates,
	 * and grass' image source, GRASS_PATH.
	 */
	public static Tile createGrassTile(float x, float y) {
		return new Tile(GRASS_PATH, x, y);
	}
	
	/**
	 * createWaterTile() creates an object of a type Tile with x and y coordinates,
	 * water's image source (WATER_PATH), and it also establishes that water tiles
	 * are a hazard.
	 */
	public static Tile createWaterTile(float x, float y) {
		return new Tile(WATER_PATH, x, y);
	}
	//, new String[] { Sprite.HAZARD}
	
	/**
	 * createTreeTile() creates an object of a type Tile with x and y coordinates,
	 * and grass' image source, TREE_PATH.
	 */
	public static Tile createTreeTile(float x, float y) {
		return new Tile(TREE_PATH, x, y);
	}
	
	/**
	 * This is the constructor of this class. It sends the parents class, Sprites, 
	 * the image source, x coordinate, y coordinate, and an image source.
	 */
	private Tile(String imageSrc, float x, float y) {		
		super(imageSrc, x, y);
	}
	
	/**
	 * This is another constructor of this class. It sends the parents class, Sprites, 
	 * the image source, x coordinate, y coordinate, an image source, and the tag
	 * if the object possess it.
	 */
	private Tile(String imageSrc, float x, float y, String[] tags) {		
		super(imageSrc, x, y, tags);
	}
}
