package suvitruf.classic.bomberman.controller;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WalkingControl {

	public static  float SIZE = 4f;
	public static  float CSIZE = 3f;
	public static  float CIRCLERADIUS = 1.5f;
	public static float  CONTRLRADIUS = 3F;
	public static float Coefficient = 1F;
	
	public static int Opacity = 1;
	public static  float BSIZE = 2f;
	public Vector2 bombPosition = new Vector2();
	public Vector2 detonatorPosition = new Vector2();
	
	protected Vector2 offsetPosition = new Vector2(); 
	
	protected Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	public WalkingControl(Vector2 pos, Vector2 posBomb, Vector2 posDetonator){ 
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		
		bombPosition = posBomb;
		detonatorPosition = posDetonator;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public Vector2 getOffsetPosition() {
		return offsetPosition;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
