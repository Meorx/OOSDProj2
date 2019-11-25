import java.util.ArrayList;
import java.util.Random;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
	/**
	 * TILE_SIZE is public because it is a constant that is defined in all
	 * classes -- everything in this game is made with 48 pixel spacing. 
	 * Moreover, since it is final and instantiated, its value cannot
	 * be changed, so there is no need to worry about privacy.
	 */
	public static final int TILE_SIZE = 48;
	private static int remainingLives1X = 24;
	private static int remainingLivesY = 744;
	private static int differenceBetweenLives = 32;
	private static boolean enteredHole1 = false;
	private static boolean enteredHole2 = false;
	private static boolean enteredHole3 = false;
	private static boolean enteredHole4 = false;
	private static boolean enteredHole5= false;
	private static int holeY= 48;
	private static int hole1X = 120;
	private static int hole2X = 312; 
	private static int hole3X = 504;
	private static int hole4X = 696;
	private static int hole5X = 888;
	private static int livesLeft = 3;
	// From here onwards, those are variables. Their value can change over time.
	private Image frog;
	private Image livesLeftPic;
	private int currentLevel = 0;
	
	// The reason why there is a division into tiles and objects is
	// they come with different amount of information -- the difference lays
	// in objects have also a boolean value that reflects the direction
	// in which they move. Tiles, in contrast are static and they stay where
	// they were initialized.
	private static ArrayList<String> tiles = new ArrayList<>();
	private static ArrayList<String> objects = new ArrayList<>();
	private ArrayList<Sprite> sprites = new ArrayList<>();
	
	public World() throws SlickException  {
		loadLevel(currentLevel);
		loadWorld();
		// Since frogs in the black holes and lives left are parts of game that dynamically 
		// disappear and appear, I have decided to simply render those pictures depending 
		// on their status (if the criteria for their existence is fulfilled, then they
		// appear). Centralizing their existence in the World class also makes them more accessible
		// from other classes. Moreover, they are not sprites, hence, there was no need to create objects
		// out of them.
		frog = new Image("assets/frog.png");
		livesLeftPic = new Image("assets/lives.png");
	}
	
	/**
	 * loadWorld() essentially reads the lists with data and creates
	 * sprites in accordance with the given data from the array lists, 
	 * specifically tiles and objects. It also creates a player at the end.
	 */
	public void loadWorld() {
        // Create tiles depending on their type.
        for(int i = 0; i <tiles.size(); i++){
            String line = tiles.get(i);
            String[] words = line.split(",");
            int tempX = Integer.parseInt(words[1]);
        	int tempY = Integer.parseInt(words[2]);
            if(words[0].contains("water")) {
            	sprites.add(Tile.createWaterTile(tempX, tempY));
            }
            if(words[0].contains("grass")) {
            	sprites.add(Tile.createGrassTile(tempX, tempY));
            }
            if(words[0].contains("tree")) {
            	sprites.add(Tile.createTreeTile(tempX, tempY));
            }
        }
		
		// Create moving objects.
		for(int i = 0; i <objects.size(); i++){
            String line = objects.get(i);
            String[] words = line.split(",");
            int tempX = Integer.parseInt(words[1]);
        	int tempY = Integer.parseInt(words[2]);
        	boolean objectBool = Boolean.parseBoolean(words[3]);
            if(words[0].contains("bus")) {
            	sprites.add(new Bus(tempX,tempY,objectBool));
            }
            if(words[0].contains("bulldozer")) {
            	sprites.add(new Bulldozer(tempX,tempY,objectBool));
            }
            // Add to logsMemLocation if it's a log or longlog and increases numberOfLogs
            // to count the number of logs.
            if(words[0].contains("log")) {
            	sprites.add(new Log(tempX,tempY,objectBool));
            }
            if(words[0].contains("longLog")) {
            	sprites.add(new Longlog(tempX,tempY,objectBool));
            }
            if(words[0].contains("racecar")) {
            	sprites.add(new Racecar(tempX,tempY,objectBool));
            }
            if(words[0].contains("turtle")) {
            	sprites.add(new Turtles(tempX,tempY,objectBool));
            }
            if(words[0].contains("bike")) {
            	sprites.add(new Bike(tempX,tempY,objectBool));
            }
        }
		
		// Player is created last so that the frog is always rendered on top of all
		// objects of the game.
		sprites.add(new Player((float)App.SCREEN_WIDTH / 2, (float)App.SCREEN_HEIGHT - TILE_SIZE));
	}
	
	/**
	 * update() updates all sprites in accordance with their changes. For
	 * instance, if the player has moved, update() will disregard the old 
	 * coordinates of the player and replace them with the new one.
	 */
	public void update(Input input, int delta) {
		for (Sprite sprite : sprites) {
			sprite.update(input, delta);
		}
		
		// loop over all pairs of sprites and test for intersection
		for (Sprite sprite1: sprites) {
			for (Sprite sprite2: sprites) {
				if (sprite1 != sprite2
						&& sprite1.collides(sprite2)) {
					sprite1.onCollision(sprite2);
				}
			}
		}	
		
		// Loads a new level once all entered holes are true. First what happens is
		// that the currentLevel is incremented, which means that the player  
		// has successfully completed a level. All ArrayLists with 
		// any data about the world are wiped, and all holes are set to false, since
		// the player has started the game anew. Finally, the lives left are
		// restarted to 3 and the new world is loaded.
		if((enteredHole1 == true) && (enteredHole2 == true) && (enteredHole3 == true)
				&& (enteredHole4 == true) && (enteredHole5 == true)) {
			currentLevel++;
			tiles.clear();
			objects.clear();
			sprites.clear();
		    loadLevel(currentLevel);
		    enteredHole1 = false;
		    enteredHole2 = false;
		    enteredHole3 = false;
		    enteredHole4 = false;
		    enteredHole5 = false;
		    livesLeft = 3;
		    loadWorld();
		}
		
	}
	
	/**
	 *  render() also controls if black slots are filled. If slot is flipped to true then the are rendered.
	 *  livesLeft are rendered with each frame because their number depends on user's ability to 
	 *  survive.
	 */
	public void render(Graphics g) {
		for (Sprite sprite : sprites) {
			if(sprite.toString().contains("Turtles")) {
				if(Sprite.getTurtleVisibility() == true) {
					sprite.render();
				}
			} else {
				sprite.render();
			}
		// The following if statements check if the player has reached 
		// a hole. If the player did, it will be filled out.
		}
		if(enteredHole1 == true) {
			frog.drawCentered(hole1X, holeY);
		}
		if(enteredHole2 == true) {
			frog.drawCentered(hole2X, holeY);
		}
		if(enteredHole3 == true) {
			frog.drawCentered(hole3X, holeY);
		}
		if(enteredHole4 == true) {
			frog.drawCentered(hole4X, holeY);
		}
		if(enteredHole5 == true) {
			frog.drawCentered(hole5X, holeY);
		}
		/**
		 * Creates lives depending on how many lives left there are.
		 */
		for(int i = 0; i < livesLeft; i++) {
			livesLeftPic.drawCentered(remainingLives1X + differenceBetweenLives * i,remainingLivesY);
		}
	}
	
	/**
	 * loadLevel() loads either level 0 or by level 1, depending on the level that
	 * is provided to the method. Then, loadLeve() reads .lvl file (0.lvl if the
	 * input level is 0, and 1.lvl when input level is 1). However, if any 
	 * the input is bigger than 1 (which means that the player passed level 1), 
	 * then the game ends.
	 * Disclaimer: this code is essentially copied and pasted from a YouTube video:
	 * https://www.youtube.com/watch?v=3_40oiUdLG8. 
	 * The credit for this code goes to the rightful owner. 
	 * The variables have been changed for the sake of readability. I have also 
	 * added a few changes to this method, such as division of the data from CSV files into 
	 * different types (e.g. I separated tiles from sprites).
	 */
	public static void loadLevel(int level){
		String levelName = " ";
		if(level == 0) {
			levelName = "assets/levels/0.lvl";
		}
		else if (level == 1){
			levelName = "assets/levels/1.lvl";
		}
		else if (level > 1) {
			System.exit(0);
		}
		
        File file = new File(levelName);
        try {
        	Scanner inputStream = new Scanner(file);
    		while(inputStream.hasNext()) {
    			String data = inputStream.next();
    			String[] line = data.split(",");
    	        if((line[0].contains("water")) || (line[0].contains("grass")) ||
    	           line[0].contains("tree")){
    	        	   tiles.add(data);
    	          } else{
    	        	  objects.add(data);
    	          }
    		}
        	inputStream.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
	}
	
	/**
	 * /setHole() allows other classes to see the set hole to true when the player successfully
	 * entered a hole. holeNum stands for the hole's number.
	 */
	public static void setHole(int holeNum) {
		if(holeNum == 1) {
			enteredHole1 = true;
		}
		if(holeNum == 2) {
			enteredHole2 = true;
		}
		if(holeNum == 3) {
			enteredHole3 = true;
		}
		if(holeNum == 4) {
			enteredHole4 = true;
		}
		if(holeNum == 5) {
			enteredHole5 = true;
		}
	}
	
	/**
	 * changeLives() allows other classes to see the decrease number of lives.
	 * The game ends when livesLeft reaches 0 (the first if-statement).
	 * However, if there is at least one life, then a change to the number of lives
	 * left can be made (the second and third if-statements).
	 */
	public static void changeLives(int change) {
		if ((livesLeft == 1) && (change == -1)) {
			System.exit(0);
		}
		if ((livesLeft > 1) && (change == -1)) {
			livesLeft = livesLeft - 1;
		}
		if ((livesLeft > 1) && (change == 1)) {
			livesLeft = livesLeft + 1;
		}
	}
	/**
	 * randomNumberInRange generates a random integer basing on the ranging given (referred as
	 * min and max). 
	 * Disclaimer: this code was purely copied and pasted here from this website:
	 * http://www.technicalkeeda.com/java-tutorials/java-generate-random-numbers-in-a-specific-range
	 * The credit for this code goes to the rightful owner. 
	 */
	public static int randomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
