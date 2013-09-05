/*******************************************************************************
 * Copyright (c) [2013], [Apanasik Andrey]
 * http://suvitruf.ru/
 * https://github.com/Suvitruf/libgdx/tree/master/NES%20Bomberman
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *   Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 *   Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 * 
 *   Neither the name of the {organization} nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package suvitruf.classic.bomberman.model.npc;

import java.util.Random;

import suvitruf.classic.bomberman.model.Bomberman;
import suvitruf.classic.bomberman.model.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Minvo extends NpcBase {


	public static String Name =  "Minvo";
	public static final float SPEED = 3.5f;	// unit per second
	public static final float ANIMATIONSPEED = 1f/3;	
	
	/*private boolean seeBomberman(){
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
	}*/
	
	//boolean seeBomber = false;
	//int toX;
	//int toY;
	@Override
	public void changeDirection(float delta) {
		
		if(canKillBomber()	)
		//if(Math.abs(position.x - world.bomberman.position.x) < SIZE/2 && Math.abs(position.y - world.bomberman.position.y) < SIZE/2	)
		{
			 //level.bomberman.setDead(true);
			world.bomberman.setState(Bomberman.State.DYING); 
			this.resetVelocity();
			return;
		}
		counter++;
		if(seeBomberman()){ 
			//seeBomber = true;
			this.resetVelocity();
			this.goToBomber(delta, SPEED);
			/*int bX = Math.round(world.getBomberman().getPosition().x);
			int bY = Math.round(world.getBomberman().getPosition().y);
			int X = Math.round(getPosition().x);
			int Y = Math.round(getPosition().y);
			
			
			if(bX == X){
				if(getPosition().x - X>0 && getPosition().x - X>0.05F){
				
						direction = Direction.LEFT;
						getVelocity().x = -SPEED;//checkColision();	return;
																
				}
				if(getPosition().x - X<0 && getPosition().x - X<-0.05F){
					direction = Direction.RIGHT;
					getVelocity().x = SPEED;//checkColision();	return;
				}
				
				if(bY>Y) {
					direction = Direction.UP;
					getVelocity().y = SPEED;//checkColision();	return;
				} 
				else{
						direction = Direction.DOWN;
						getVelocity().y = -SPEED;//checkColision();	return;
					}
			}
			
			if(bY == Y ){
				
				if(getPosition().y - Y>0 && getPosition().y - Y>0.05F){
					
					direction = Direction.DOWN;
					getVelocity().y = -SPEED;//checkColision();	return;
																
				}
				if(getPosition().y - Y<0 && getPosition().y - Y<-0.05F){
					direction = Direction.UP;
					getVelocity().y = SPEED;//checkColision();	return;
				}
				
				if(bX>X) {
					direction = Direction.RIGHT;
					getVelocity().x = SPEED;//checkColision();	return;
				}
				else{
					direction = Direction.LEFT;
					getVelocity().x = -SPEED;//checkColision();	return;
				}
			}*/
			
		}
		else
			if(Math.abs(position.x - (int)position.x)/((int)position.x) <0.01 &&
					Math.abs(position.y - (int)position.y)/((int)position.x) <0.01 && counter>50)
			{
				newDirection(false);
				//return;
			}
			
		/*else
			if( seeBomber){
				 seeBomber = false;
				 setFirstDirection();
			}*/
		
		
		
		//if(counter>1000) counter = 0;
		
		 
		
		
		checkColision(delta);
	}

	private void checkColision(float delta){
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
	public Minvo(World world, Vector2 pos){ //constructor takes parameter vector2 which is a position (x,y)
		super(pos);
		this.world = world;
		//getVelocity().y =SPEED;	
		//state = State.NONE;
		points = 800;
		//setFirstDirection();
		
		
		name = "Minvo";
		animationState = 0;
	}
	
	private void setFirstDirection(){
		newDirection(false);
		/*direction = Direction.DOWN;
		getVelocity().y = -SPEED;*/	
	}

	@Override
	public float getAnimationState(){
		
		
		//Log.e("gg", Float.toString( animationState));
		switch(state){
			case NONE:
				animationState+=Gdx.graphics.getDeltaTime(); 
				if(direction == Direction.LEFT){
					
					if(animationState>1)
						animationState = 0;
				}
				else
					if(direction == Direction.RIGHT){
						if(animationState<1 || animationState>2)
							animationState = 1;
					}
					else
						if(animationState>2)
							animationState = 0;
				break;
			case DYING:
				animationState+=0.0025; 
				//Log.e("Dying", Float.toString(curState) );
				if(animationState<2)
					animationState= 2;
				if(animationState> 2.2)
					//animationState= 2;
					setState(State.DEAD);
			case DEAD:
				break;
		}
		
		return animationState;
	}
	@Override
	public void newDirection(boolean checkCur){
		if(!checkCur) counter = 0;
		Random generator = new Random();
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
			getVelocity().x =-SPEED;
			direction = Direction.LEFT;
		}
		//checkColision();
	}
	private int counter = 0;
	
	
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
