package suvitruf.classic.bomberman.model;

//import suvitruf.nes.bomberman.model.Bomberman.Direction;
//import suvitruf.nes.bomberman.model.Bomberman.State;

import java.util.Timer;
import java.util.TimerTask;

import suvitruf.classic.bomberman.controller.WorldController;
//import suvitruf.classic.bomberman.view.WorldRenderer;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bomb {

	public static String textureName = "images/BombSprite.png";
	
	//private  Animation       walkAnimation;
	//private  Texture texture;
	//private TextureRegion[] textureFrames; 
	//private static final float HEIGHT = 1f; //half a unit
	//private static final float WIDTH = 1f;
	public static final int FRAME_COLS =2; 
	public static final int FRAME_ROWS = 2;
	public static  float SIZE = 1f;
	public static String Name =  "Bomb";
	public Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	//private  float  curState = 0;
	public int length;
	public boolean isAutoBoom = true;
	TimerTask task;
	Timer timer;
	public static enum State {NONE,  BOOM}
	public State state;
	private float  animationState;
	
	public Bomb( Vector2 pos, boolean auto, int length){ 
		this.length = length;
		state =  State.NONE;
		isAutoBoom = auto;
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	
		/*
		texture  = new Texture(Gdx.files.internal("images/BombSprite.png"));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
		textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		 int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];

		
		walkAnimation = new Animation(8F, textureFrames); */
		
		animationState = 0; 
		
		if(auto){
			createTimer();
		}
		
	}
	
	/*public void setAuto(boolean auto){
		this.isAutoBoom = auto;
	}*/
	
	public void setState(State state){
		/*if(!((state == State.BOOMING && (this.state == State.BOOMING || this.state == State.BOOM)) ||
				(state == State.BOOM &&  this.state == State.BOOM))
				)*/
			this.state = state;
	}
	int timeLeft;
	private void createTimer(){
		timeLeft = 0;
		task = new TimerTask(){
			public void run()
		      { 
				if(!WorldController.paused)++timeLeft;
				/*if(state ==State.BOOMING){
					setState(State.BOOM);
					
					timer.cancel();
				}*/
				
				if(state == State.NONE){
					if(timeLeft>3)
					{
						setState( State.BOOM);
					
						
						clearTimer();
					}
				}
				
		      }
		};
		timer=new Timer();
	
		timer.schedule(task, 1,1000);
	}
	
	private void clearTimer(){
		timer.cancel();
		timer.purge();
		timer = null;
	}
	public Vector2 getPosition() {
		return position;
	}
/*
	public TextureRegion getZeroFrame(){
		return walkAnimation.getKeyFrame(0, true);
	}*/
	public float getAnimationState(){
		animationState+=0.5;
		//animationState+=Gdx.graphics.getDeltaTime();  
		return animationState;
		
	}
	/*public TextureRegion getCurrentFrame(){
		TextureRegion curFrame;

		curState+=0.5;
	
	

		curFrame = walkAnimation.getKeyFrame(curState, true);
		
		
		return curFrame;
	}*/
	
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	
}
