package suvitruf.classic.bomberman.model.npc;

import java.util.Random;


import suvitruf.classic.bomberman.model.Bomberman;

import suvitruf.classic.bomberman.model.World;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Doll extends NpcBase {

	public static String Name =  "Doll";
	public static final float SPEED = 2.5f;	// unit per second
	public static final float ANIMATIONSPEED = 1f/6;	
	//public static String textureName = "images/BalloomSprite.png";
	//public Animation       walkAnimation;
	//private  Texture texture;
	//private TextureRegion[] textureFrames; 
	//public static final int FRAME_COLS =4; 
	//public static final int FRAME_ROWS = 4;
	Random generator = new Random();
	
	

	
	
	
	public Doll(World world, Vector2 pos){ //constructor takes parameter vector2 which is a position (x,y)
		super(pos);
		this.world = world;
		//getVelocity().y =SPEED;	
		//state = State.NONE;
		points = 400;
		//setFirstDirection();
		
		/*texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
		textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		 int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];

		walkAnimation = new Animation(2*SPEED, textureFrames); 
		
		curState = 0; */
		name = "Doll";
		animationState = 0;
	}
	private void setFirstDirection(){
		direction = Direction.DOWN;
		getVelocity().y = -SPEED;	
	}
	
	@Override
	public float getAnimationState(){

		//Log.e("gg", Float.toString( animationState));
		switch(state){
			case NONE:
				animationState+=Gdx.graphics.getDeltaTime(); 
				if(direction == Direction.LEFT){
					
					if(animationState>0.5)
						animationState = 0;
				}
				else
					if(direction == Direction.RIGHT){
						if(animationState<0.5 || animationState>1)
							animationState = 0.5F;
					}
					else
						if(animationState>1)
							animationState = 0;
				break;
			case DYING:
				animationState+=0.00125; 
				//Log.e("Dying", Float.toString(curState) );
				if(animationState<1)
					animationState= 1;
				if(animationState> 1.1)
					//animationState= 2;
					setState(State.DEAD);
			case DEAD:
				break;
		}
		
		return animationState;
	}
	
	/*public TextureRegion getCurrentFrame(){
		TextureRegion curFrame;
		curState+=0.5;
		switch(state){
			case NONE:
				if(direction == Direction.LEFT){
					
					if(curState>10)
					curState = 0;
				}
				else
					if(direction == Direction.RIGHT){
						if(curState<12 || curState>22)
						curState = 13;
					}
					else
						if(curState>20)
							curState = 0;
				break;
			case DYING:
				//Log.e("Dying", Float.toString(curState) );
				if(curState<24)
					curState = 25;
				if(curState>42)
					setState(State.DEAD);
			case DEAD:
				break;
		}
		
		
		
		curFrame = walkAnimation.getKeyFrame(curState, true);
		
		
		return curFrame;
	}
	
	private void resetVelocity(){
		getVelocity().x =0;
		getVelocity().y =0;
	}*/
	@Override
	public void newDirection(boolean checkCur){
		if(!checkCur) counter = 0;
		
		int newDir =1+ generator.nextInt(4);
		resetVelocity();
		Direction oldDir = direction;
		if(newDir == 1 &&(direction != Direction.UP || !checkCur)){
			getVelocity().y =SPEED;	
			
			direction = Direction.UP;
		}
		
		
		if(oldDir == direction )
		if(newDir == 2 && (direction != Direction.DOWN || !checkCur)){
			getVelocity().y =-SPEED;	
			
			direction = Direction.DOWN;
		}
		
		if(oldDir == direction)
		if(newDir == 3 && (direction != Direction.RIGHT || !checkCur)){
		
			getVelocity().x =SPEED;
			direction = Direction.RIGHT;
		}
		
		
		
		if(oldDir == direction)
		if(newDir == 4 && (direction != Direction.LEFT || !checkCur)){
			//getVelocity().y = 0 ;	
			getVelocity().x =-SPEED;
			direction = Direction.LEFT;
		}
		/*if(!checkCur){
			boolean cool = true;
			int checkX = (int)position.x;
			int checkY = (int)position.y;
			if(direction == Direction.LEFT)
				checkX = (int)position.x-1;
			if(direction == Direction.RIGHT)
				checkX = (int)position.x+1;
			
			if(direction == Direction.UP)
				checkY = (int)position.y+1;
			if(direction == Direction.DOWN)
				checkY = (int)position.y-1;
			boolean exist = false;
			for (BrickBase block : level.getBlocks()) {
				if((int)block.position.x == checkX && (int)block.position.y == checkY ){
					 cool = false;
				}
				
			}
			if(cool) counter = 0;
			else newDirection(false);
		}*/
		
		//Log.e("Random", "newDir: "+newDir); 
	}
	
	
	private int counter = 0;
	public  void changeDirection(float delta){
		counter++;
		if(canKillBomber()	) 
			world.bomberman.setState(Bomberman.State.DYING); 
		
		 
		if(Math.abs(position.x - (int)position.x)/((int)position.x) <0.01 &&
				Math.abs(position.y - (int)position.y)/((int)position.x) <0.01 && counter>50)
		{
			newDirection(false);
			//return;
		}
		this.checkBombColision();
		this.checkBlockColision(false, delta, SPEED);
		/*for (Bomb bomb : world.getBombs()) {
			if(direction == Direction.UP)
				if(position.y+1.1*SIZE > bomb.position.y && position.y+1.1*SIZE <bomb.position.y + Bomb.SIZE
						&& position.x + 0.4*SIZE >bomb.position.x && position.x + 0.4F*SIZE < bomb.position.x+ Bomb.SIZE){
						newDirection(true);
				}
			
			if(direction == Direction.DOWN)
				if(position.y-0.2*SIZE> bomb.position.y && position.y-0.2*SIZE<bomb.position.y + Bomb.SIZE
				&&position.x + 0.4*SIZE > bomb.position.x && position.x + 0.4F*SIZE < bomb.position.x+ Bomb.SIZE){
						newDirection(true);
				}
			
			if(direction == Direction.RIGHT)
				if(position.y+0.4*SIZE > bomb.position.y && position.y+0.4*SIZE <bomb.position.y + Bomb.SIZE
				&& position.x + 1.1*SIZE > bomb.position.x && position.x + 1.1*SIZE < bomb.position.x+Bomb.SIZE){
					newDirection(true);
				}
			
			if(direction == Direction.LEFT)
				if(position.y+0.4*SIZE > bomb.position.y && position.y+0.4*SIZE <bomb.position.y + Bomb.SIZE
				&&position.x - 0.1*SIZE > bomb.position.x && position.x - 0.1*SIZE  < bomb.position.x+ Bomb.SIZE){
					newDirection(true);
				}
		}
		for (BrickBase block :world.getBlocks()) {
			
			if(direction == Direction.UP)
				if(position.y+1.1*SIZE > block.position.y && position.y+1.1*SIZE <block.position.y + BrickBase .SIZE
						&& position.x + 0.4*SIZE > block.position.x && position.x + 0.4F*SIZE < block.position.x+ BrickBase .SIZE){
						newDirection(true);
				}
			
			if(direction == Direction.DOWN)
				if(position.y-0.2*SIZE> block.position.y && position.y-0.2*SIZE<block.position.y + BrickBase .SIZE
				&&position.x + 0.4*SIZE > block.position.x && position.x + 0.4F*SIZE < block.position.x+ BrickBase .SIZE){
						newDirection(true);
				}
			
			if(direction == Direction.RIGHT)
				if(position.y+0.4*SIZE > block.position.y && position.y+0.4*SIZE <block.position.y + BrickBase .SIZE
				&& position.x + 1.1*SIZE > block.position.x && position.x + 1.1*SIZE < block.position.x+BrickBase .SIZE){
					newDirection(true);
				}
			
			if(direction == Direction.LEFT)
				if(position.y+0.4*SIZE > block.position.y && position.y+0.4*SIZE <block.position.y + BrickBase .SIZE
				&&position.x - 0.1*SIZE > block.position.x && position.x - 0.1*SIZE  < block.position.x+ BrickBase .SIZE){
					newDirection(true);
				}
			
			
		}*/
	}
	/*
	public String getName(){
		return "Balloom";
	}*/
	
	@Override
	public void update(float delta) {
		if(state == State.NONE)
			changeDirection(delta);
		else
			this.resetVelocity();

		position.add(velocity.tmp().mul(delta)); 
		
		bounds.setX(position.x);
		bounds.setY(position.y);
	}

}
