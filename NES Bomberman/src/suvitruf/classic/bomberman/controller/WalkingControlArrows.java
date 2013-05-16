package suvitruf.classic.bomberman.controller;

import com.badlogic.gdx.math.Vector2;

public class WalkingControlArrows {
	public static  float BSIZE = 2f;
	public static float Coefficient = 1F;
	public static int Opacity = 1;
	
	public Vector2 bombPosition = new Vector2();
	public Vector2 detonatorPosition = new Vector2();
	
	public Vector2 positionLeft = new Vector2();
	public Vector2 positionRight = new Vector2();
	public Vector2 positionUp = new Vector2();
	public Vector2 positionDown = new Vector2();
	
	public WalkingControlArrows(Vector2 posLeft, Vector2 posRight, Vector2 posUp, Vector2 posDown, Vector2 posBomb, Vector2 posDetonator){ 
		positionLeft =posLeft;
		positionRight = posRight;
		positionUp = posUp;
		positionDown = posDown;
		
		bombPosition = posBomb;
		detonatorPosition = posDetonator;
		
	}
}
