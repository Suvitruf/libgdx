package suvitruf.libgdxtutorial.lesson2.controller;

import java.util.HashMap;
import java.util.Map;

import suvitruf.libgdxtutorial.lesson2.model.*;



public class WorldController {
	enum Keys {
		LEFT, RIGHT, UP, DOWN
	}
	
	
	public Player player;
	
	
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	};

	public WorldController(World world) {

	
		this.player = world.getPlayer();
	}
	

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}
	
	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}
	
	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}
	
	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}
	
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}
	
	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}
	
	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
	}
	
	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
	}
	
	public void update(float delta) {

		processInput();
		player.update(delta);

	}
	
	public void resetWay(){
		rightReleased();
		leftReleased();
		downReleased();
		upReleased();
	}
	private void processInput() {
		if (keys.get(Keys.LEFT)) 
			player.getVelocity().x = -Player.SPEED;
			
		
		if (keys.get(Keys.RIGHT))
			player.getVelocity().x =Player.SPEED;	
		
		if (keys.get(Keys.UP)) 
			player.getVelocity().y = Player.SPEED;
		
		
		if (keys.get(Keys.DOWN))
			player.getVelocity().y = -Player.SPEED;
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
				(!keys.get(Keys.LEFT) && (!keys.get(Keys.RIGHT)))) 
			player.getVelocity().x = 0;			
		
		if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
				(!keys.get(Keys.UP) && (!keys.get(Keys.DOWN)))) 
			player.getVelocity().y = 0;			
		
	}
}
