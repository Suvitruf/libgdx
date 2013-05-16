package suvitruf.classic.bomberman.model;



/*import suvitruf.nes.bomberman.model.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;*/
import com.badlogic.gdx.math.Vector2;


public class Brick extends BrickBase {
	public static String textureName = "images/BrickSprite.png";
	public static final float SPEED = 3f;
	//private  Texture texture;//  = new Texture(Gdx.files.internal("images/BombermanSprite.png"));
	//private TextureRegion[] textureFrames; 
	public static final int FRAME_COLS =4; 
	public static final int FRAME_ROWS = 2; 
	//public Animation       walkAnimation;
	//public float  curState = 0;
	public static String Name =  "Brick";
	//public  String name =  "Brick";
	public Brick(Vector2 pos){ 
		super(pos);
		/*
		Texture texture  = new Texture(textureName);
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		 int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];

		walkAnimation = new Animation(SPEED, textureFrames); */
		name =  "Brick";
		//curState = 0; 
		animationState = 0;
	}
	@Override
	public float getAnimationState(){
		switch(state){
		case NONE: 
			break;
		
		case DESTROYING:
			animationState+=0.5;
			if(animationState>16)
				state = State.DESTROYED;
			break;
		case DESTROYED: break;
		}
		
		return animationState;
	}
	/*@Override
	public TextureRegion getCurrentFrame(){
		//TextureRegion curFrame;
		//curState+=0.5;
		switch(state){
			case NONE: 
				break;
			
			case DESTROYING:
				animationState+=0.5;
				if(animationState>16)
					state = State.DESTROYED;
				break;
			case DESTROYED: break;
		}

		//curFrame = walkAnimation.getKeyFrame(curState, true);
		
		return null;//walkAnimation.getKeyFrame(curState, true);
		//return curFrame;
	}*/
	/*
	@Override
	public String getName(){
		return "Brick";
	}*/
}
