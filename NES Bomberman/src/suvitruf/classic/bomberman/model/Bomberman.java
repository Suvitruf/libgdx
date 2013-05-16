package suvitruf.classic.bomberman.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import suvitruf.classic.bomberman.model.hiddenobject.*;


//import android.util.Log;




import android.util.Log;

//mport com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.g2d.Animation;

public class Bomberman{
	//private  Texture texture;//  = new Texture(Gdx.files.internal("images/BombermanSprite.png"));
	//private TextureRegion[] textureFrames; 
	//private static final int FRAME_COLS =8; 
	//private static final int FRAME_ROWS = 3; 
	//public Animation       walkAnimation;
	 
	public enum State {
		IDLE, WALKING, JUMPING,DYING2, DYING, DEAD, WON
	}
	public static enum Direction {
		LEFT, RIGHT, UP, DOWN, NONE
	}
	
	World world;
	
	//private Direction  direction = Direction.NONE;
	Map<Direction, Boolean> direction = new HashMap<Direction, Boolean>();
	//Direction direction;// = Direction.NONE;
			
	//public boolean isDead= false;
	private float speed = 3f;	// unit per second
	public static final float ANIMATIONSPEED = 3f;
	private boolean wallPass = false;
	private boolean bombPass = false;
	public boolean flamePass = false;
	//public static final float JUMP_VELOCITY = 4f;
	//public static final float SIZE = 0.5f; // half a unit
	//public boolean falling = false;
	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	Circle boundss = new Circle(1,1,1);
	State		state = State.IDLE;
	//boolean		facingLeft = true;
	boolean doubleDirection;
	public float  curState = 0;
	public static final float HEIGHT = 1f; 
	public static final float WIDTH = 1f;
	public static final float SIZE = 1f;
	public Bomberman(World world , Vector2 position) {
		this.world = world;
		 resetDirection();
		//direction = Direction.NONE;
		/*texture  = new Texture(Gdx.files.internal("images/BombermanSprite.png"));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
		textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		 int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];

		walkAnimation = new Animation(SPEED, textureFrames); */
		
		curState = 0; 
		
		this.position = position;
		this.bounds.height = HEIGHT;
		
		this.bounds.width = WIDTH;
		
		//this.state = State.WON;
	}
	
	public float getSpeed(){
		return speed;
	}
/*	public boolean isFacingLeft() {
		return facingLeft;
	}*/

	public float getAnimationState(){
		//TextureRegion curFrame;
		if(state == State.DYING)
		{
			//walkAnimation = new Animation(SPEED*2, textureFrames); 
			curState+=0.5;
			if(curState<35)
				curState = 36;
			if(curState>=66)
				curState = 66;
		}
		
		if(state == State.WALKING)
		{
			//if(direction != Direction.NONE)
				curState+=0.5;
				
			if(direction.get(Direction.UP)){
				if(curState<26 || curState>34)
				curState = 27;
			}
			else
				if(direction.get(Direction.DOWN)){
					if(curState<17 || curState>25)
						curState = 18;
				}
				else
					if(direction.get(Direction.LEFT)){
						if(curState>7)
						curState = 0;
					}
					else
						if(direction.get(Direction.RIGHT))
							if(curState<8 || curState>16)
							curState = 9;
					
					
					
			/*switch(direction){
				case LEFT:
					if(curState>7)
						curState = 0;
					break;
				 default: break;
				case RIGHT: 
					if(curState<8 || curState>16)
						curState = 9;
					break;
				case DOWN: 
					if(curState<17 || curState>25)
						curState = 18;
					break;
				case UP: 
					if(curState<26 || curState>34)
						curState = 27;
					
				
			}*/
			
			/*if(direction.get(Direction.LEFT)){
				
					if(curState>8)
						curState = 0;
			}
			if(direction.get(Direction.RIGHT)){
				if(curState>17 || curState <8)
					curState = 9;
			}*/
		}
		//if(curState>8) curState = 0;
		//curFrame = 
		//return walkAnimation.getKeyFrame(curState, true);
		return curState;
		
		//return curFrame;
	}
	/*public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}*/

	public Vector2 getPosition() {
		
		return position;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public State getState() {
		return state;
	}
	
	TimerTask task1;
	TimerTask task2;
	Timer timer;
	/*
	public boolean isDead(){
		return (this.state == State.DYING || this.state == State.DYING2|| this.state == State.DEAD);
	}*/
	public void setState(State newState) {
		if(newState == State.DYING && (this.state == State.DYING || this.state == State.DYING2|| this.state == State.DEAD || world.invincibility))
			return;
		if(newState == State.DYING && !(this.state == State.DYING || this.state == State.DYING2|| this.state == State.DEAD))
		{
		//if(!(newState == State.DYING 
			timer=new Timer();
			task1 = new TimerTask(){
				public void run()
			      {
					
					state = State.DYING2;
					timer.cancel();
					timer.purge();
					timer=new Timer();
					timer.schedule(task2, 3000);
			      }
			};
			
			task2 = new TimerTask(){
				public void run()
			      {
					
					state = State.DEAD;
					timer.cancel();
					timer.purge();
					timer = null;
			      }
			};
			
			timer.schedule(task1, 1000);
		
		}
		this.state = newState;
	}
	/*
	public Map<Direction, Boolean> getDirection() {
		return this.direction;
	}*/
	public void resetDirection(){
		direction.put(Direction.LEFT, false);
		direction.put(Direction.RIGHT, false);
		direction.put(Direction.UP, false);
		direction.put(Direction.DOWN, false);
	}
	public void setDirection(Direction dir, boolean value) {
		this.direction.put(dir, value);
	}
	public boolean getDirection(Direction dir) {
		return direction.get(dir);
	}
/*
	public void setDead(boolean dead){
		isDead = dead;
	}*/
	/*public boolean isDoubleDirection() {
		return true;
		//return this.doubleDirection;// = newDirection;
	}*/
	
	public void update(float delta) {
		if(isDead() || state == State.WON) return;
		collisionDetection();
		//if(!bombPass) collisionDetectionBombs();
		checkHiddenObects();
		
		position.add(velocity.tmp().mul(delta)); 
		bounds.setX(position.x);
		bounds.setY(position.y);
	}
	
	private void checkHiddenObects(){
		float x1 = Math.round(getPosition().x);
		float y1 = Math.round(getPosition().y);
		boolean can = true;
		for(HiddenObject object :world.getHiddenObjects()){
			if(world.map[(int)y1][(int)x1] != 0) can = false;
			//if(world.map[(int)getPosition().y][(int)getPosition().x] != 0) can = false;
			/*for (BrickBase block : world.getBlocks()) {
				if(block.position.x == x1 && block.position.y == y1){
					can = false; break;
				}
			}*/
			
			if(!can) continue;
			if(/*object.getName() == Door.Name*/object instanceof Door && object.getPosition().x == x1 && object.getPosition().y == y1){
				if(world.getNpcCount() == 0 ){
					setState(State.WON);
					this.resetDirection();
					this.resetVelocity();
				}
				break;
			}
			if(/*object.getName() == FlamesPower.Name*/object instanceof FlamesPower && object.getPosition().x == x1 && object.getPosition().y == y1){
				world.inreasePowerUp(FlamesPower.Name);
				object.state = HiddenObject.State.TAKEN;
			}
			
			if(/*object.getName() == BombsPower.Name*/ object instanceof BombsPower&& object.getPosition().x == x1 && object.getPosition().y == y1){
				world.inreasePowerUp(BombsPower.Name);
				object.state = HiddenObject.State.TAKEN;
			}
			
			if(/*object.getName() == SpeedPower.Name */object instanceof SpeedPower&& object.getPosition().x == x1 && object.getPosition().y == y1){
				world.inreasePowerUp(SpeedPower.Name);
				object.state = HiddenObject.State.TAKEN;
			}
			
			if(/*object.getName() == WallPassPower.Name*/ object instanceof WallPassPower&& object.getPosition().x == x1 && object.getPosition().y == y1){
				world.inreasePowerUp(WallPassPower.Name);
				object.state = HiddenObject.State.TAKEN;
			}
			if(/*object.getName() == BombPassPower.Name*/ object instanceof BombPassPower&& object.getPosition().x == x1 && object.getPosition().y == y1){
				world.inreasePowerUp(BombPassPower.Name);
				object.state = HiddenObject.State.TAKEN;
			}
			
			if(/*object.getName() == DetonatorPower.Name*/ object instanceof DetonatorPower&& object.getPosition().x == x1 && object.getPosition().y == y1){
				world.inreasePowerUp(DetonatorPower.Name);
				object.state = HiddenObject.State.TAKEN;
			}
			
			if(/*object.getName() == FlamePassPower.Name*/ object instanceof FlamePassPower&& object.getPosition().x == x1 && object.getPosition().y == y1){
				world.inreasePowerUp(FlamePassPower.Name);
				object.state = HiddenObject.State.TAKEN;
			}
			
			if(/*object.getName() == MysteryPower.Name*/ object instanceof MysteryPower&& object.getPosition().x == x1 && object.getPosition().y == y1){
				
				//world.inreasePowerUp(FlamePassPower.Name);
				object.state = HiddenObject.State.TAKEN;
			}
		}
	}
	
	public boolean isDead(){
		return state == State.DYING || state == State.DYING2 || state == State.DEAD;
	}
	public boolean isInsideBomb = false;
	/*private void collisionDetectionBombs() {
		float x = getVelocity().x;
		float y = getVelocity().y;
		for (Bomb bomb: world.getBombs()){	
			
			if (direction.get(Direction.LEFT)){
				if (getPosition().x - Bomberman.WIDTH/25>= bomb.getPosition().x && getPosition().x-Bomberman.WIDTH/10 <= (bomb.getPosition().x + Bomb.SIZE) && 
						((getPosition().y+Bomberman.HEIGHT*0.1  >= (bomb.getPosition().y) && getPosition().y+Bomberman.HEIGHT*0.1<= (bomb.getPosition().y + Bomb.SIZE))
								|| (getPosition().y +Bomberman.HEIGHT*0.9 >= (bomb.getPosition().y) &&getPosition().y+ Bomberman.HEIGHT*0.9<= (bomb.getPosition().y + Bomb.SIZE))
						// bomberman.getPosition().y+Bomberman.HEIGHT/2 >= (block.getPosition().y) && bomberman.getPosition().y+Bomberman.HEIGHT/2 <= (block.getPosition().y + 1
								)) {				
					//bomberman.getPosition().x = block.getPosition().x+BrickBase.SIZE;
					
					getVelocity().x = 0; 	
					
				}
				
			}
			
			if (direction.get(Direction.RIGHT)){
				if (((getPosition().x+Bomberman.WIDTH+Bomberman.WIDTH/10) >= bomb.getPosition().x && (getPosition().x+Bomberman.WIDTH/10) <= (bomb.getPosition().x + Bomb.SIZE))
						&& 
						((getPosition().y+Bomberman.HEIGHT*0.1  >= (bomb.getPosition().y) && getPosition().y+Bomberman.HEIGHT*0.1<= (bomb.getPosition().y + Bomb.SIZE))
						|| (getPosition().y +Bomberman.HEIGHT*0.9 >= (bomb.getPosition().y) && getPosition().y+ Bomberman.HEIGHT*0.9<= (bomb.getPosition().y + Bomb.SIZE))
						))
						 {				
					//bomberman.getPosition().x = block.getPosition().x-Bomberman.WIDTH;
				getVelocity().x = 0;					
				}
				
			}
			
			
			if (direction.get(Direction.DOWN)){
				if ((getPosition().x+Bomberman.WIDTH*0.1> bomb.getPosition().x && getPosition().x+Bomberman.WIDTH*0.1 < (bomb.getPosition().x + Bomb.SIZE)
						|| getPosition().x +  Bomberman.WIDTH> bomb.getPosition().x && getPosition().x+Bomberman.WIDTH < (bomb.getPosition().x + Bomb.SIZE))
						&& getPosition().y-Bomberman.HEIGHT*0.1 >= (bomb.getPosition().y) && getPosition().y-Bomberman.HEIGHT*0.1 <= (bomb.getPosition().y + Bomb.SIZE)) {				
					//bomberman.getPosition().y = block.getPosition().y+BrickBase.SIZE;
					getVelocity().y = 0;					
				}
				
					
			}
			
			if (direction.get(Direction.UP)){
				if ((getPosition().x+Bomberman.WIDTH*0.1> bomb.getPosition().x && getPosition().x+Bomberman.WIDTH*0.1 < (bomb.getPosition().x + Bomb.SIZE)
						|| getPosition().x +  Bomberman.WIDTH>bomb.getPosition().x && getPosition().x+Bomberman.WIDTH < (bomb.getPosition().x + Bomb.SIZE))
						&& getPosition().y+Bomberman.HEIGHT*1.1 >= (bomb.getPosition().y) && getPosition().y+Bomberman.HEIGHT*1.1 <= (bomb.getPosition().y + Bomb.SIZE)) {				
					//bomberman.getPosition().y = block.getPosition().y-Bomberman.HEIGHT;
					getVelocity().y = 0;	  				
				}
				
			}
			 
			//vertical-ceiling
			
		}	
		
		if(isInsideBomb)
		{
			if(getVelocity().x ==0 && getVelocity().y==0)
			{
				getVelocity().x = x;
				getVelocity().y = y;
			}
			if(!checkInsideBomb())
				isInsideBomb = false;
			//else
			//	isInsideBomb = false;
		}
	}
	
	private boolean checkInsideBomb(){
		boolean inside = false;
		float x = position.x;//Math.round(position.x);
		float y = position.y;//Math.round(position.y);
		for (Bomb bomb: world.getBombs()){	
			if( Math.abs(x - bomb.position.x)<WIDTH*1.1F && Math.abs(y -bomb.position.y)<WIDTH*1.1F ){
				inside =true;
				break;
			}
		}
		return inside;
	}*/
	private void findAroundBlocks(){
		int[][]  around = new int[3][3];
		int x1 = (int)(getPosition().x+SIZE/2);
		int y1 = (int)(getPosition().y+SIZE/2);
		
		for(int i=-1;i<=1;++i)
			for(int j=-1;j<=1;++j)
				around[i+1][j+1] = (world.map[y1-i][x1+j]==2) ? ( wallPass ?0 : 1): world.map[y1-i][x1+j];
				
		/*for(int i=0;i<3;++i)
			for(int j=0;j<3;++j)
				around[i][j] = 0;
		
		for (BrickBase block: world.getBlocks()){	
			if(wallPass && block.getName() == Brick.Name) continue;
			if((int)block.getPosition().x == x1 ){
				if((int)block.getPosition().y == y1+1)
					around[0][1] = 1;
				if((int)block.getPosition().y == y1)
					around[1][1] = 1;
				if((int)block.getPosition().y == y1-1)
					around[2][1] = 1;
			}
			
			if((int)block.getPosition().x == x1 +1){
				if((int)block.getPosition().y == y1)
					around[1][2] = 1;
				if((int)block.getPosition().y == y1-1)
					around[2][2] = 1;
				if((int)block.getPosition().y == y1+1)
					around[0][2] = 1;
			}
			
			if((int)block.getPosition().x == x1 -1){
				if((int)block.getPosition().y == y1)
					around[1][0] = 1;
				if((int)block.getPosition().y == y1-1)
					around[2][0] = 1;
				if((int)block.getPosition().y == y1+1)
					around[0][0] = 1;
			}
		}*/
		
		for (Bomb bomb: world.getBombs()){	
			if(bombPass) continue;
			if((int)bomb.getPosition().x == x1 ){
				if((int)bomb.getPosition().y == y1+1)
					around[0][1] = 1;
				if((int)bomb.getPosition().y == y1)
					around[1][1] = 1;
				if((int)bomb.getPosition().y == y1-1)
					around[2][1] = 1;
			}
			
			if((int)bomb.getPosition().x == x1 +1){
				if((int)bomb.getPosition().y == y1)
					around[1][2] = 1;
				if((int)bomb.getPosition().y == y1-1)
					around[2][2] = 1;
				if((int)bomb.getPosition().y == y1+1)
					around[0][2] = 1;
			}
			
			if((int)bomb.getPosition().x == x1 -1){
				if((int)bomb.getPosition().y == y1)
					around[1][0] = 1;
				if((int)bomb.getPosition().y == y1-1)
					around[2][0] = 1;
				if((int)bomb.getPosition().y == y1+1)
					around[0][0] = 1;
			}
		}
		
		if (direction.get(Direction.LEFT)){
			if(around[1][0] ==1 && Math.abs(getPosition().x-x1)<=0.1F)
				getVelocity().x = 0; 
		
			
			if(around[1][0] ==0 && Math.abs(getPosition().y-y1)<SIZE/2
					&& Math.abs(getPosition().y-y1)>0.05){
				if(getPosition().y-y1<0 && !direction.get(Direction.DOWN)){
					getVelocity().x = -speed; 
					getVelocity().y = +speed; //return;
				}
				
				if(getPosition().y-y1>0 && !direction.get(Direction.UP)){
					getVelocity().x = -speed; 
					getVelocity().y = -speed; //return;
				}
			}
			/*
			if ((direction.get(Direction.UP) || direction.get(Direction.DOWN))&&
					getPosition().x-x1<0) return;*/
			
		}
		if (direction.get(Direction.RIGHT) ){
			if(around[1][2] ==1 && Math.abs(getPosition().x-x1)<=0.1F)
				getVelocity().x = 0; 	
		
			if(around[1][2] ==0 && Math.abs(getPosition().y-y1)<SIZE/2 && Math.abs(getPosition().y-y1)>0.05){
				if(getPosition().y-y1<0 && !direction.get(Direction.DOWN)){
					getVelocity().x = +speed; 
					getVelocity().y = +speed; //return;
				}
				if(getPosition().y-y1>0 && !direction.get(Direction.UP)){
					getVelocity().x = +speed; 
					getVelocity().y = -speed; //return;
				}
			}	
			/*
			if ((direction.get(Direction.UP) || direction.get(Direction.DOWN))&&
					getPosition().x-x1>0) return;*/
		}
		if (direction.get(Direction.UP)){
			if(around[0][1] ==1 && Math.abs(getPosition().y-y1)<=0.1F)
				getVelocity().y = 0; 
			
			if(around[0][1] ==0 && Math.abs(getPosition().x-x1)<SIZE/2 && Math.abs(getPosition().x-x1)>0.05){
				if(getPosition().x-x1<0){
					getVelocity().x = +speed; 
					getVelocity().y = +speed; //return;
				}
				if(getPosition().x-x1>0){
					getVelocity().x = -speed; 
					getVelocity().y = +speed; //return;
				}
			}	
		}
		if (direction.get(Direction.DOWN)){
			if(around[2][1] ==1 && Math.abs(getPosition().y-y1)<=0.1F)
				getVelocity().y = 0; 
			
			if(around[2][1] ==0 && Math.abs(getPosition().x-x1)<SIZE/2 && Math.abs(getPosition().x-x1)>0.05){
				if(getPosition().x-x1<0){
					getVelocity().x = +speed; 
					getVelocity().y = -speed; //return;
				}
				if(getPosition().x-x1>0){
					getVelocity().x = -speed; 
					getVelocity().y = -speed; //return; 
				}
			}	
		}
		
		/*if (direction.get(Direction.LEFT)){
			if(direction.get(Direction.UP)){
				if(Math.abs(getPosition().y-y1)<=0.05F){
					if(getPosition().x-x1 <0){
						getVelocity().x = -speed; 
						getVelocity().y = 0; 
					}
					else
					{
						getVelocity().x = 0; 
						getVelocity().y = speed; 
					}
				}
					
			}
			
			if(direction.get(Direction.DOWN)){
				if(Math.abs(getPosition().y-y1)<=0.05F){
					if(getPosition().x-x1 <0){
						getVelocity().x = -speed; 
						getVelocity().y = 0; 
					}
					else
					{
						getVelocity().x = 0; 
						getVelocity().y = -speed; 
					}
				}
					
			}
		}*/
			
		/*int count = 0;
		for(int i=0;i<3;++i)
			for(int j=0;j<3;++j)
				if(around[i][j] !=0) ++count;
		
		Log.e("count",Integer.toString(count));*/
		
		around = null;
	}
	protected void resetVelocity(){
		getVelocity().x =0;
		getVelocity().y =0;
	}
	private void collisionDetectionSave(){
		findAroundBlocks();
		/*for (BrickBase block: world.getBlocks()){	
			if(wallPass && block.getName() == Brick.Name) continue;
			if (direction.get(Direction.LEFT)){
				
				if (getPosition().x - Bomberman.WIDTH/25>= block.getPosition().x && getPosition().x-Bomberman.WIDTH/25 <= (block.getPosition().x + BrickBase.SIZE) && 
						((getPosition().y+Bomberman.HEIGHT*0.1 >= (block.getPosition().y) && getPosition().y+Bomberman.HEIGHT*0.1<= (block.getPosition().y + BrickBase.SIZE))
								|| (getPosition().y +Bomberman.HEIGHT >= (block.getPosition().y) &&getPosition().y+ Bomberman.HEIGHT<= (block.getPosition().y + BrickBase.SIZE))
								)) {				

						getVelocity().x = 0; 					
				}
				
			}
			
			if (direction.get(Direction.RIGHT)){
				if (((getPosition().x+Bomberman.WIDTH+Bomberman.WIDTH/10) >= block.getPosition().x && (getPosition().x+Bomberman.WIDTH/10) <= (block.getPosition().x + BrickBase.SIZE))
						&& 
						((getPosition().y+Bomberman.HEIGHT*0.1 >= (block.getPosition().y) && getPosition().y+Bomberman.HEIGHT*0.1<= (block.getPosition().y + BrickBase.SIZE))
						|| (getPosition().y +Bomberman.HEIGHT>= (block.getPosition().y) && getPosition().y+ Bomberman.HEIGHT<= (block.getPosition().y + BrickBase.SIZE))
						))
						 {				
			
				getVelocity().x = 0;					
				}
				
			}
			
			
			if (direction.get(Direction.DOWN)){
			
				if ((getPosition().x+Bomberman.WIDTH*0.1> block.getPosition().x && getPosition().x+Bomberman.WIDTH*0.1 < (block.getPosition().x + BrickBase.SIZE)
						|| getPosition().x +  Bomberman.WIDTH> block.getPosition().x && getPosition().x+Bomberman.WIDTH < (block.getPosition().x + BrickBase.SIZE))
						&& getPosition().y-Bomberman.HEIGHT*0.05 >= (block.getPosition().y) && getPosition().y-Bomberman.HEIGHT*0.05 <= (block.getPosition().y + BrickBase.SIZE)) 
					getVelocity().y = 0;					
				
				
					
			}
			
			if (direction.get(Direction.UP)){
				if ((getPosition().x+Bomberman.WIDTH*0.1> block.getPosition().x && getPosition().x+Bomberman.WIDTH*0.1 < (block.getPosition().x + BrickBase.SIZE)
						|| getPosition().x +  Bomberman.WIDTH> block.getPosition().x && getPosition().x+Bomberman.WIDTH < (block.getPosition().x + BrickBase.SIZE))
						&& getPosition().y+Bomberman.HEIGHT*1.1 >= (block.getPosition().y) && getPosition().y+Bomberman.HEIGHT*1.1 <= (block.getPosition().y + BrickBase.SIZE)) {				
					//bomberman.getPosition().y = block.getPosition().y-Bomberman.HEIGHT;
					getVelocity().y = 0;					
				}
				
			}
		
		}	*/
	}
	private void collisionDetection() {
		
		try{ collisionDetectionSave();} catch (Exception e){Log.e("err",e.getMessage());}
		//bomberman.setDirection(Bomberman.Direction.NONE);
		
		
		
		
		/*
		if(bomberman.getVelocity().x<0)
			bomberman.setDirection(Bomberman.Direction.LEFT);
		
		if(bomberman.getVelocity().x>0)
			bomberman.setDirection(Bomberman.Direction.RIGHT);
		if(bomberman.getVelocity().y>0)
			bomberman.setDirection(Bomberman.Direction.UP);
		if(bomberman.getVelocity().y<0)
			bomberman.setDirection(Bomberman.Direction.DOWN);*/
	}

	public void setSpeed(int count){
		this.speed = 3F;
		for(int i=0;i<count;++i)
			this.speed*=1.2;
	}
	
	public void setWallPass(boolean can){
		this.wallPass = can;	
	}
	public void setBombPass(boolean can){
		this.bombPass = can;	
	}
	
	public void setFlamePass(boolean can){
		this.flamePass = can;	
	}
	/*
	public void setDoubleDirection(boolean isD) {
		//this.doubleDirection =  isD;
	}*/

}
