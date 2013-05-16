package suvitruf.classic.bomberman.model;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BoomPart {
	public static  float SIZE = 1f;
	protected Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	public static enum Type {MIDDLE, UP, DOWN, LEFT, RIGHT, HORIZONTAL, VERTICAL}
	Type type;

	public float  animationState;// = 0;
	
	public BoomPart(Vector2 pos, Type type){ 
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.type = type;
		animationState = 0;
		changeAnimationFrame();
		
	}
	
	private void changeAnimationFrame(){
		switch(type){
		case MIDDLE:
			animationState = Boom.SPEED*16;
			break;
		case HORIZONTAL:
			animationState =Boom.SPEED*20;
			break;
		case VERTICAL:
			animationState =Boom.SPEED*24;
			break;
		case RIGHT:
			animationState =Boom.SPEED*12;
			break;
		case LEFT:
			animationState =Boom.SPEED*4;
			break;
		case UP:
			animationState =0;
			break;
		case DOWN:
			animationState =Boom.SPEED*8;
			break;
		}
	}
	public void setType(Type type){
		this.type = type;
		changeAnimationFrame();
	}
	public Vector2 getPosition() {
		return position;
	}
	public float getAnimationState(){
		animationState+=Gdx.graphics.getDeltaTime();  
		return animationState;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
