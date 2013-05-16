package suvitruf.classic.bomberman.model.npc;



import suvitruf.classic.bomberman.model.Bomb;

import suvitruf.classic.bomberman.model.*;

import android.util.FloatMath;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class NpcBase {
	protected int points = 0;
	protected World world;
	public static  float SIZE = 0.9f;
	//protected abstract  float SPEED = 1f;
	public enum Direction {
		LEFT, RIGHT, UP, DOWN, NONE
	}
	public Direction direction;
	
	public Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	public static enum State {NONE, DYING, DEAD }
	protected State state;
	//Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	public float  animationState = 0;
	protected  String name = "NpcBase" ;
	public NpcBase(Vector2 pos){ //constructor takes parameter vector2 which is a position (x,y)
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		state = State.NONE;
		direction = Direction.NONE;
	}
	public float getAnimationState(){
		return animationState;
	}
	public Vector2 getPosition() {
		return position;
	}

	public int getPoints(){
		return points;
	}
	
	public String getName(){
		return name;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	//public abstract  TextureRegion getCurrentFrame();
	public abstract void changeDirection(float delta);
	public abstract void update(float delta) ;
	

	public abstract void newDirection(boolean checkCur);
	public Vector2 getAcceleration() {
		return acceleration;
	}
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State newState) {
		this.state = newState;
	}
	
	protected void resetVelocity(){
		getVelocity().x =0;
		getVelocity().y =0;
	}
	
	private void checkBlockColisionSave(boolean wallpass, float delta, float speed){
		int[][]  around = new int[3][3];
		//int x1 = (int)(getPosition().x+SIZE/2);
		//int y1 = (int)(getPosition().y);
		int x1 = (int)(getPosition().x);
		int y1 = (int)(getPosition().y);
		
		
		for(int i=-1;i<=1;++i)
		for(int j=-1;j<=1;++j)
			around[i+1][j+1] = (world.map[y1-i][x1+j]==2) ? ( wallpass ?0 : 1): world.map[y1-i][x1+j];
		/*
		for(int i=0;i<3;++i)
			for(int j=0;j<3;++j)
				around[i][j] = 0;
		for (BrickBase block: world.getBlocks()){	
			if(block == null || (block instanceof Brick && wallpass)) continue;
			//if(wallPass && block.getName() == Brick.Name) continue;
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
		} 
		*/
		boolean cool = true;//false;
		//while(!cool){
			//cool = true;
			if(direction == Direction.NONE) 	newDirection(true);
			//Log.e("pos","["+Float.toString(this.getPosition().x)+";"+Float.toString(this.getPosition().y)+"]");
			//Log.e("direction",direction.toString());
			float offset = delta*speed*2;
			//float offset = 0.15F;
			if(direction == Direction.UP && this.getPosition().y-y1>offset)
				if((around[0][1] == 1 /*&& this.getPosition().x-x1<offset*/)|| (around[0][2] == 1 && this.getPosition().x-x1>offset)){
						newDirection(true);
						cool = false;
				}
			
			if(direction == Direction.DOWN && this.getPosition().y-y1<offset)
				if((around[2][1] == 1 /*&&this.getPosition().x-x1>offset */)||   (around[2][2] == 1 &&this.getPosition().x-x1>offset)){
						newDirection(true);
						cool = false;
				} 
			
			if(direction == Direction.RIGHT && this.getPosition().x-x1>offset)
				if((around[1][2] == 1/* &&this.getPosition().y-y1<offset*/) || (around[0][2] == 1&&this.getPosition().y-y1>offset)){
					newDirection(true);
					cool = false;
				}
			
			if(direction == Direction.LEFT && this.getPosition().x-x1<offset)
				if((around[1][0] == 1 /*&&this.getPosition().y-y1<offset */)|| (around[0][0] == 1&&this.getPosition().y-y1>offset )){
					newDirection(true);
					cool = false;
				}
			
			
			if(!cool ){
				cool = true;
				if(direction == Direction.UP && this.getPosition().y-y1>offset)
					if((around[0][1] == 1 /*&& this.getPosition().x-x1<offset*/)|| (around[0][2] == 1 && this.getPosition().x-x1>offset)){
							//newDirection(true);
							cool = false;
					}
				
				if(direction == Direction.DOWN && this.getPosition().y-y1<offset)
					if((around[2][1] == 1 /*&&this.getPosition().x-x1>offset */)||   (around[2][2] == 1 &&this.getPosition().x-x1>offset)){
							//newDirection(true);
							cool = false;
					} 
				
				if(direction == Direction.RIGHT && this.getPosition().x-x1>offset)
					if((around[1][2] == 1/* &&this.getPosition().y-y1<offset*/) || (around[0][2] == 1&&this.getPosition().y-y1>offset)){
						//newDirection(true);
						cool = false;
					}
				
				if(direction == Direction.LEFT && this.getPosition().x-x1<offset)
					if((around[1][0] == 1 /*&&this.getPosition().y-y1<offset */)|| (around[0][0] == 1&&this.getPosition().y-y1>offset )){
						//newDirection(true);
						cool = false;
					}
				
				//Log.e("direction2",direction.toString());
				if(!cool ){
				//	Log.e("direction","-");
				this.resetVelocity();
				direction = Direction.NONE;
				}
				
			}
				
		//}
		
			around = null;
	}
	
	public boolean checkX(float offset){
		return getPosition().x - (int)getPosition().x < offset;
	}
	
	public boolean checkY(float offset){
		return getPosition().y - (int)getPosition().y < offset;
	}
	
	public void goToBomber(float delta, float speed){
		
		int bX = Math.round(world.getBomberman().getPosition().x);
		int bY = Math.round(world.getBomberman().getPosition().y);
		int X = Math.round(getPosition().x);
		int Y = Math.round(getPosition().y);
		
		float offset = delta*speed*2;
		
		if(bX == X){
			if(checkX(offset)){
				if(bY>Y) {
					direction = Direction.UP;
					getVelocity().y = speed;
				} 
				else{
						direction = Direction.DOWN;
						getVelocity().y = -speed;
				}
			}
			else{
				if(getPosition().x - X>0){
					
					direction = Direction.LEFT;
					getVelocity().x = -speed;
															
				}
				if(getPosition().x - X<0){
					direction = Direction.RIGHT;
					getVelocity().x = speed;
				}
			}
			
			
			//if(bY>Y) {
			
		}
		
		if(bY == Y ){
			if(checkY(offset)){
				if(bX>X) {
					direction = Direction.RIGHT;
					getVelocity().x = speed;
				}
				else{
					direction = Direction.LEFT;
					getVelocity().x = -speed;
				}
			}
			else{
				if(getPosition().y - Y>0 ){
					
					direction = Direction.DOWN;
					getVelocity().y = -speed;
																
				}
				if(getPosition().y - Y<0){ 
					direction = Direction.UP;
					getVelocity().y = speed;
				}
			}
			
			
			
		}
	}
	/*
	private void checkBlockColisionSave2(boolean wallpass){
		for (BrickBase block :world.getBlocks()) {
			if(block == null || (block.getName() == Brick.Name && wallpass)) continue;
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
		}
	}*/
	protected void checkBlockColision(boolean wallpass, float delta, float speed){
		try{checkBlockColisionSave(wallpass, delta, speed);} catch (Exception e){}
	}
	protected void checkBombColision(){
		for (Bomb bomb : world.getBombs()) {
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
	}
	
	
	protected boolean seeBomberman(){
		boolean see = false;
		int bX = Math.round(world.getBomberman().getPosition().x);
		int bY = Math.round(world.getBomberman().getPosition().y);
		
		int X = Math.round(getPosition().x);
		int Y = Math.round(getPosition().y);
		
		if(bX == X || bY == Y){
			see = true; 
			//Log.e("gg", "true");
			for (BrickBase block :world.getBlocks()) {
				if(Math.round(block.getPosition().x) == X)
					if((block.getPosition().y > Y && block.getPosition().y < bY) ||
							(block.getPosition().y < Y && block.getPosition().y >bY)){
						see = false;
						break;
					}
				
				if(Math.round(block.getPosition().y) == Y)
					if((block.getPosition().x > X && block.getPosition().x < bX) ||
							(block.getPosition().x < X && block.getPosition().x >bX)){
						see = false;
						break;
					}
			}
			
			for (Bomb bomb :world.getBombs()) {
				if(Math.round(bomb.getPosition().x) == X)
					if((bomb.getPosition().y > Y && bomb.getPosition().y < bY) ||
							(bomb.getPosition().y < Y && bomb.getPosition().y >bY)){
						see = false;
						break;
					}
				
				if(Math.round(bomb.getPosition().y) == Y)
					if((bomb.getPosition().x > X && bomb.getPosition().x < bX) ||
							(bomb.getPosition().x < X && bomb.getPosition().x >bX)){
						see = false;
						break;
					}
			}
		}
		
		return see;
	}
	
	protected boolean canKillBomber(){ 
		
		
		return (bounds.overlaps(world.getBomberman().getBounds()) && !world.bonus && FloatMath.sqrt((world.getBomberman().getPosition().x-getPosition().x)*(world.getBomberman().getPosition().x-getPosition().x)
				+(world.getBomberman().getPosition().y-getPosition().y)*(world.getBomberman().getPosition().y-getPosition().y)) < SIZE/2) ? true : false;
			
	}
	
}
