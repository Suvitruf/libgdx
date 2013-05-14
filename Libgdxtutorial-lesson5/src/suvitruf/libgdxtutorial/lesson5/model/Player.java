package suvitruf.libgdxtutorial.lesson5.model;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;



import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Actor {

	public enum State {
		NONE, WALKING, DEAD
	}
	public static enum Direction {
		LEFT, RIGHT, UP, DOWN, NONE
	}

	public static final float SPEED = 2f;	
	public static final float SIZE = 1f; 
	
	World world;
	Vector2 	position = new Vector2();
	Vector2 	velocity = new Vector2();
	//Rectangle 	bounds = new Rectangle();
	State		state = State.NONE;
	boolean		facingLeft = true;

	public Player(Vector2 position, World world) {
		
		this.world = world;
		this.position = position; 
		//this.bounds.height = SIZE*world.ppuY;
		//this.bounds.width = SIZE*world.ppuX;
		setHeight(SIZE*world.ppuY);
		setWidth(SIZE*world.ppuX);
		setX(position.x*world.ppuX);
		setY(position.y*world.ppuY);
		addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	                return true;
	        }
	        
	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        }
	});
	}
	/*
	public Rectangle getBounds() {
		return bounds;
	}*/
	
	public Vector2 getPosition() {
		
		return position;
	}
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void update(float delta) {
		position.add(velocity.tmp().mul(delta)); 
		setX(position.x*world.ppuX);
		setY(position.y*world.ppuY);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlfa) {
		
		if (this.equals(world.selectedActor)) {
			batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
		}
		
		batch.draw(world.textureRegions.get("player"), getX(), getY(),getWidth(), getHeight());
		batch.setColor(1, 1, 1, 1);
	}

	
	public Actor hit(float x, float y, boolean touchable) {
		//Процедура проверки. Если точка в прямоугольнике актёра, возвращаем актёра.
		 
		//	Log.e("hh", Float.toString(world.selectedActor.getX()));
		//Log.e("Player [", x+":"+y+"] --- ["+this.getX()+":"+this.getY()+"]");
		return x > 0 && x < getWidth() && y> 0 && y < getHeight()?this:null;
	}
	
	/*
	public void ChangeNavigation(float x, float y){
		//Log.e("Player ", "["+x+":"+y+"] --- ["+this.getX()+":"+this.getY()+"]");
		resetWay();
		if(y > getY())
			upPressed();
		
		if(y <  getY())
			downPressed();
		
		if ( x< getX()) 
			leftPressed();
			
		if (x> (getPosition().x +SIZE)* world.ppuX)
			rightPressed();
			
		processInput();
	}*/
	
	
	
	public void resetWay(){
		rightReleased();
		leftReleased();
		downReleased();
		upReleased();
		
		getVelocity().x = 0;
		getVelocity().y  = 0;
	}
	
	public void processInput() {
		if (direction.get(Keys.LEFT)) 
			getVelocity().x = -Player.SPEED;
			
		
		if (direction.get(Keys.RIGHT))
			getVelocity().x =Player.SPEED;	
		
		if (direction.get(Keys.UP)) 
			getVelocity().y = Player.SPEED;
		
		
		if (direction.get(Keys.DOWN))
			getVelocity().y = -Player.SPEED;
		if ((direction.get(Keys.LEFT) && direction.get(Keys.RIGHT)) ||
				(!direction.get(Keys.LEFT) && (!direction.get(Keys.RIGHT)))) 
			getVelocity().x = 0;			
		
		if ((direction.get(Keys.UP) && direction.get(Keys.DOWN)) ||
				(!direction.get(Keys.UP) && (!direction.get(Keys.DOWN)))) 
			getVelocity().y = 0;	
	}
	
	enum Keys {
		LEFT, RIGHT, UP, DOWN
	}
	
	static Map<Keys, Boolean> direction = new HashMap<Keys, Boolean>();
	static {
		direction.put(Keys.LEFT, false);
		direction.put(Keys.RIGHT, false);
		direction.put(Keys.UP, false);
		direction.put(Keys.DOWN, false);
	};
	
	
	
	public void leftPressed() {
		direction.get(direction.put(Keys.LEFT, true));
	}
	
	public void rightPressed() {
		direction.get(direction.put(Keys.RIGHT, true));
	}
	
	public void upPressed() {
		direction.get(direction.put(Keys.UP, true));
	}
	
	public void downPressed() {
		direction.get(direction.put(Keys.DOWN, true));
	}
	
	public void leftReleased() {
		direction.get(direction.put(Keys.LEFT, false));
	}
	
	public void rightReleased() {
		direction.get(direction.put(Keys.RIGHT, false));
	}
	
	public void upReleased() {
		direction.get(direction.put(Keys.UP, false));
	}
	
	public void downReleased() {
		direction.get(direction.put(Keys.DOWN, false));
	}
}