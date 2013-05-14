package suvitruf.libgdxtutorial.lesson2.model;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	Array<Brick> bricks = new Array<Brick>();
	public Player player;
	
	public int width;
	public int height;
	
	public Array<Brick> getBricks() {
		return bricks;
	}

	public Player getPlayer() {
		return player;
	}
	
	public World() {
		
		width = 8;
		height = 5;

		createWorld();
		
	}
	
	public void createWorld() {
		player = new Player(new Vector2(6,2));
		
		bricks.add(new Brick(new Vector2(0, 0)));
		bricks.add(new Brick(new Vector2(1, 0)));
		bricks.add(new Brick(new Vector2(2, 0)));
		bricks.add(new Brick(new Vector2(3, 0)));
		bricks.add(new Brick(new Vector2(4, 0)));
		bricks.add(new Brick(new Vector2(5, 0)));
		bricks.add(new Brick(new Vector2(6, 0)));
		bricks.add(new Brick(new Vector2(7, 0)));
	
		
	}
}
