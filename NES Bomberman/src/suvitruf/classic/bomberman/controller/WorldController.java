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
package suvitruf.classic.bomberman.controller;
import suvitruf.classic.bomberman.model.*;
import suvitruf.classic.bomberman.model.hiddenobject.Door;
import suvitruf.classic.bomberman.model.hiddenobject.HiddenObject;
import suvitruf.classic.bomberman.model.hiddenobject.MysteryPower;
import suvitruf.classic.bomberman.model.npc.*;
//import suvitruf.classic.bomberman.model.npc.NpcBase.Direction;
import suvitruf.classic.bomberman.view.WorldRenderer;

import com.badlogic.gdx.math.Vector2;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

//import android.util.Log;
import com.badlogic.gdx.utils.Array;

//import android.util.Log;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*import suvitruf.nes.bomberman.model.Bomb;
import suvitruf.nes.bomberman.model.BoomPart;
import suvitruf.nes.bomberman.model.BrickBase;
import suvitruf.nes.bomberman.model.Bomberman;
import suvitruf.nes.bomberman.model.NpcBase;
import suvitruf.nes.bomberman.model.World;
import suvitruf.nes.bomberman.model.Bomberman.State;*/
//import android.graphics.Bitmap;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
public class WorldController {
	private WorldRenderer 	renderer;
	enum Keys {
		LEFT, RIGHT, UP, DOWN
	}
	
	private World 	world;
	public Bomberman 	bomberman;
	public float toX;
	public float toY;
	public boolean hasKeyBoard = false;
	public static boolean paused = false;
	public static boolean soundOn;
	public static boolean joy;
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	};

	public WorldController(World world, WorldRenderer renderer) {
		
		this.renderer = renderer;
		this.world = world;
		this.bomberman = world.getBomberman();
		paused = false;
		task = null;
		timer = null;
		
		//this.bomberman = world.getBomberman();
	}

	// ** Key presses and touches **************** //
	
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}
	public void boomBombPressed() {
		world.boomLastBomb();
	}
	public void bombPressed(int x, int y) {
		if(!bomberman.isDead())
			if(world.putBomb(x, y, true))
				renderer.playPutingBombSound();
		//Log.e("bomb",	"booom");
	}
	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}
	
	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}
	
	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}
	
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}
	
	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}
	
	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
	}
	
	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
	}
	
	TimerTask task;
	Timer timer;
	public void dispose(){
		clearTimer();
		//world.dispose();
	}
	
	
	int timeLeft;
	private void createMysteryTimer(){
		timeLeft = 0;
		world.invincibility = true;
		task = new TimerTask(){
			public void run()
		      {
				if(!WorldController.paused)
					++timeLeft;
		
				if(timeLeft >= 20){
					//Log.e("cleartimer","clear");
					world.invincibility = false;
					clearTimer();
				}

		      }
		};
		timer=new Timer();
		
		timer.schedule(task, 1, 1000);
	}
	
	public void clearTimer(){
		if(timer != null){
			timer.cancel();
			timer.purge();
			timer = null;
		}
	
		
	}
	
	/** The main update method **/ 
	public void update(float delta) {
	
		//Log.e("delta",	Float.toString(delta));
		/*if(world.getBomberman().isDead)
			world = new World(world.width, world.height);*/
		processInput();
		checkBombsTimer();
		if(!world.bonus) destroyBricks();
		removeDeadNpc();
		killByBoom();
		removeHiddenObjects();
		
		//destroyHiddenObjects();
		//killBombermanByBoom();
		//gravityDetection();
		//collisionDetectionBlocks();	
		bomberman.update(delta);
		for(NpcBase npc : world.getNpcs()){
			npc.update(delta);
		}
		removeBooms();
		//ShowPos();
	}
	
	private void removeHiddenObjects(){
		for(HiddenObject object : world.getHiddenObjects()){
			if(object.state == HiddenObject.State.TAKEN){
			
				if(object.getName() == MysteryPower.Name)
					createMysteryTimer();
				world.getHiddenObjects().removeValue(object, true);
				renderer.playFindDoorMusic();
			}
			if(object.state == HiddenObject.State.DESTROYED){
				//world.generateNpcAfterDestroyingObject(object.getPosition());
				if(!(object.getName() == Door.Name))
					world.getHiddenObjects().removeValue(object, true);
			}
		}
	}
	private void removeBooms(){
		for(Boom boom : world.getBooms()){
			if(boom.getState() == Boom.State.BOOM){
				world.getBooms().removeValue(boom, true);
				for(BoomPart part : boom.getParts()){
					for(HiddenObject object : world.getHiddenObjects()){
						if( object.getPosition().x == part.getPosition().x && 
								object.getPosition().y==part.getPosition().y){
							object.state = HiddenObject.State.DESTROYED;
							world.generateNpcAfterDestroyingObject(object.getPosition());
						}
				
					}
				}
				
			}
				
		}
	}
	
	private void destroyBricks(){
		for (BrickBase block : world.getBlocks()) {
			if(block.getState() == BrickBase.State.DESTROYED)
				//world.getBlocks().removeValue(block,true);
				world.removeBrick(block);
		}
	}
	private void removeDeadNpc(){
		for (NpcBase npc : world.getNpcs()) {
			if(npc.getState() == NpcBase.State.DEAD)
				world.getNpcs().removeValue(npc, true);
		}
	}
	
	private void killByBoom(){
		for(Boom boom: world.getBooms())
			for(BoomPart part: boom.getParts()){
				for(NpcBase npc : world.getNpcs()){
					float x1 = Math.round(npc.getPosition().x);
					float y1 = Math.round(npc.getPosition().y);
					if(x1 == part.getPosition().x && y1 == part.getPosition().y)
					{
						if(npc.getState() != NpcBase.State.DYING && npc.getState() != NpcBase.State.DEAD){
							world.points += npc.getPoints();
							npc.setState(NpcBase.State.DYING);
						}
					}
				}
				
				float x1 = (int)(world.getBomberman().getPosition().x+Bomberman.SIZE/2);
				float y1 =  (int)(world.getBomberman().getPosition().y+Bomberman.SIZE/2);
				if(x1 == part.getPosition().x && y1 == part.getPosition().y && !world.invincibility && !world.bonus && !world.getBomberman().flamePass)
					world.getBomberman().setState(Bomberman.State.DYING);
			}
				

	}
	private void killNearNpc(Bomb bombCheck){
		 Array<NpcBase> npcToKill = new Array<NpcBase>();
		 float x = bombCheck.getPosition().x;
		 float y = bombCheck.getPosition().y;
		 
		 for(int i=1;i<=bombCheck.length;++i){
				for(NpcBase npc : world.getNpcs()){
					float x1 = Math.round(npc.getPosition().x);
					float y1 = Math.round(npc.getPosition().y);
					if((x1 == x+i && y1==y)|| (x1 == x-i && y1==y)|| (x1 == x && y1==y+i)|| (x1 == x && y1==y-i))
						npcToKill.add(npc);
						//bomb.setState(Bomb.State.BOOM);
				}
			}
		 
		 for (BrickBase block : world.getBlocks()) {
				for(NpcBase npc :npcToKill){
					float x1 = Math.round(npc.getPosition().x);
					float y1 = Math.round(npc.getPosition().y);
					
					if(block.getPosition().x == x1 && 
							((block.getPosition().y> y1&& block.getPosition().y < bombCheck.getPosition().y)
									||
							(block.getPosition().y< y1&& block.getPosition().y > bombCheck.getPosition().y)
							)
							)
						npcToKill.removeValue(npc, true);
					
					if(block.getPosition().y == y1 && 
							((block.getPosition().x > x1 && block.getPosition().x < bombCheck.getPosition().x)
									||
									(block.getPosition().x <  x1 && block.getPosition().x > bombCheck.getPosition().x))
							)
						npcToKill.removeValue(npc, true);
				}
				
			}
		 
		 for(NpcBase npc : npcToKill)
			 if(npc.getState() != NpcBase.State.DYING && npc.getState() != NpcBase.State.DEAD){
					world.points += npc.getPoints();
					npc.setState(NpcBase.State.DYING);
				}
		/*for(int i=1;i<=bomb.length;++i)
			for (NpcBase npc : world.getNpcs()) {
				if(Math.abs(npc.getPosition().y-bomb.getPosition().y) <i  &&
						Math.abs(npc.getPosition().x - bomb.getPosition().x)<0.2F)
					npc.setState(NpcBase.State.DYING);
				
				if(npc.getPosition().y+i*1F == bomb.getPosition().y &&
						npc.getPosition().x == bomb.getPosition().x)
					npc.setState(NpcBase.State.DYING);
				
				if(Math.abs(npc.getPosition().y - bomb.getPosition().y)<0.2F &&
						Math.abs(npc.getPosition().x - bomb.getPosition().x)<i)
					npc.setState(NpcBase.State.DYING);
				
				if(npc.getPosition().y == bomb.getPosition().y &&
						npc.getPosition().x == bomb.getPosition().x-i*1F)
					npc.setState(NpcBase.State.DYING);
			}*/
		
		
	}
	
	
	private void killBombermanInsideBrick(int x,int y){
		
		//Log.e("killBombermanInsideBrick", "+"); 
		if(x == Math.round(world.getBomberman().getPosition().x) && y == Math.round(world.getBomberman().getPosition().y) && !world.invincibility && !world.bonus && !world.getBomberman().flamePass)
			world.getBomberman().setState(Bomberman.State.DYING);
	}
	
	private void destroyNearObjects(Bomb bomb){
		
		Array<BoomPart> boomParts = new Array<BoomPart>();
		boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x,bomb.getPosition().y), BoomPart.Type.MIDDLE));
		boolean topEnd = false;
		boolean botEnd = false;
		
		boolean leftEnd = false;
		boolean rightEnd = false;
		
		Array<BrickBase> bricks = new Array<BrickBase>();
		
		for(int i=1;i<=bomb.length;++i){
			for (BrickBase block : world.getBlocks()) 
				if((block.position.x == bomb.getPosition().x+i && 
						block.position.y == bomb.getPosition().y) || 
						(block.position.x == bomb.getPosition().x-i && 
						block.position.y == bomb.getPosition().y)||
						(block.position.y == bomb.getPosition().y-i && 
						block.position.x == bomb.getPosition().x)
						||
						(block.position.y == bomb.getPosition().y+i && 
						block.position.x == bomb.getPosition().x))
					bricks.add(block);
			
		}
		
		for(int i=1;i<=bomb.length;++i){
			for (BrickBase block : bricks) {
				if(!botEnd){
					if(block.getName()=="HardBrick" && block.getPosition().y-i*1F == bomb.getPosition().y &&
							block.getPosition().x == bomb.getPosition().x){
						
						//Log.e("HardBrick", "true");
						botEnd = true;
					}
					if(block.getName()=="Brick" && block.getPosition().y-i*1F == bomb.getPosition().y&&
							block.getPosition().x == bomb.getPosition().x){
						//Log.e("Brick", "true");
						killBombermanInsideBrick((int)block.getPosition().x, (int)block.getPosition().y);
						block.setState(BrickBase.State.DESTROYING);
						//world.getBlocks().removeValue(block,true);
						botEnd = true;
					}
					/*
					for(HiddenObject object : world.getHiddenObjects()){
						if( object.getPosition().x == bomb.getPosition().x && 
								object.getPosition().y< bomb.getPosition().y&& 
								object.getPosition().y>bomb.getPosition().y-i*1F)
							object.state = HiddenObject.State.DESTROYED;
				
					}*/
					
				}
				
				
				if(!topEnd){
					if(block.getName()=="HardBrick" && block.getPosition().y+i*1F == bomb.getPosition().y&&
							block.getPosition().x == bomb.getPosition().x){
						topEnd = true;
					}
					if(block.getName()=="Brick" && block.getPosition().y+i*1F == bomb.getPosition().y&&
							block.getPosition().x == bomb.getPosition().x){
						killBombermanInsideBrick((int)block.getPosition().x, (int)block.getPosition().y);
						block.setState(BrickBase.State.DESTROYING);
						//world.getBlocks().removeValue(block,true);
						topEnd = true;
					}
					
					/*
					for(HiddenObject object : world.getHiddenObjects()){
						if( object.getPosition().x == bomb.getPosition().x && 
								object.getPosition().y> bomb.getPosition().y&& 
								object.getPosition().y<bomb.getPosition().y+i*1F)
							object.state = HiddenObject.State.DESTROYED;
				
					}*/
				}
				
				if(!leftEnd){
					if(block.getName()=="HardBrick" && block.getPosition().y == bomb.getPosition().y&&
							block.getPosition().x == bomb.getPosition().x-i*1F){
						leftEnd = true;
					}
					if(block.getName()=="Brick" && block.getPosition().y == bomb.getPosition().y&&
							block.getPosition().x == bomb.getPosition().x-i*1F){
						killBombermanInsideBrick((int)block.getPosition().x, (int)block.getPosition().y);
						block.setState(BrickBase.State.DESTROYING);
						//world.getBlocks().removeValue(block,true);
						leftEnd = true;
					}
					/*
					for(HiddenObject object : world.getHiddenObjects()){
						if( object.getPosition().x > bomb.getPosition().x-i*1F && 
								 object.getPosition().x < bomb.getPosition().x && 
								object.getPosition().y==bomb.getPosition().y)
							object.state = HiddenObject.State.DESTROYED;
				
					}*/
				}
				if(!rightEnd){
					if(block.getName()=="HardBrick" && block.getPosition().y == bomb.getPosition().y&&
							block.getPosition().x == bomb.getPosition().x+i*1F){
						rightEnd= true;
					}
					if(block.getName()=="Brick" && block.getPosition().y == bomb.getPosition().y&&
							block.getPosition().x == bomb.getPosition().x+i*1F){
						killBombermanInsideBrick((int)block.getPosition().x, (int)block.getPosition().y);
						block.setState(BrickBase.State.DESTROYING);
						//world.getBlocks().removeValue(block,true);
						rightEnd = true;
					}
					/*
					for(HiddenObject object : world.getHiddenObjects()){
						if( object.getPosition().x < bomb.getPosition().x+i*1F && 
								 object.getPosition().x > bomb.getPosition().x && 
								object.getPosition().y==bomb.getPosition().y)
							object.state = HiddenObject.State.DESTROYED;
				
					}*/
				}
			}
			if(!rightEnd)
				boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x+i*1F,bomb.getPosition().y), BoomPart.Type.HORIZONTAL));
			/*else
				if(i>100)
					for(BoomPart boomPart: boomParts)
						if(boomPart.getPosition().x == bomb.getPosition().x+(i-1)*1F && boomPart.getPosition().y == bomb.getPosition().y)
							boomPart.setType(BoomPart.Type.RIGHT);*/

				
			
			if(!leftEnd)
				boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x-i*1F,bomb.getPosition().y), BoomPart.Type.HORIZONTAL));
			/*else
				if(i>100)
					for(BoomPart boomPart: boomParts)
						if(boomPart.getPosition().x == bomb.getPosition().x-(i-1)*1F && boomPart.getPosition().y == bomb.getPosition().y)
							boomPart.setType(BoomPart.Type.LEFT);*/
			if(!botEnd)
				boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x,bomb.getPosition().y+i*1F), BoomPart.Type.VERTICAL));
			/*else
				if(i>100)
					for(BoomPart boomPart: boomParts)
						if(boomPart.getPosition().x == bomb.getPosition().x && boomPart.getPosition().y-(i-1)*1F == bomb.getPosition().y)
							boomPart.setType(BoomPart.Type.UP);*/
			if(!topEnd)
				boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x,bomb.getPosition().y-i*1F), BoomPart.Type.VERTICAL));
			/*else
				if(i>100)
					for(BoomPart boomPart: boomParts)
						if(boomPart.getPosition().x == bomb.getPosition().x && boomPart.getPosition().y+(i-1)*1F == bomb.getPosition().y)
							boomPart.setType(BoomPart.Type.DOWN);*/
			
		}
		
		if(!leftEnd)
			for(BoomPart boomPart: boomParts)
				if(boomPart.getPosition().x == bomb.getPosition().x-bomb.length*1F && boomPart.getPosition().y == bomb.getPosition().y)
					boomPart.setType(BoomPart.Type.LEFT);
		
		if(!rightEnd)
			for(BoomPart boomPart: boomParts)
				if(boomPart.getPosition().x == bomb.getPosition().x+bomb.length*1F && boomPart.getPosition().y == bomb.getPosition().y)
					boomPart.setType(BoomPart.Type.RIGHT);
		if(!botEnd)
			for(BoomPart boomPart: boomParts)
				if(boomPart.getPosition().x == bomb.getPosition().x && boomPart.getPosition().y-bomb.length*1F == bomb.getPosition().y)
					boomPart.setType(BoomPart.Type.UP);
		
		if(!topEnd)
			for(BoomPart boomPart: boomParts)
				if(boomPart.getPosition().x == bomb.getPosition().x && boomPart.getPosition().y+bomb.length*1F == bomb.getPosition().y)
					boomPart.setType(BoomPart.Type.DOWN);
		world.putBoom(new Boom(boomParts));
	}
	private void killBomberman(Bomb bombCheck){
		 boolean kill = false;
		 float x = bombCheck.getPosition().x;
		 float y = bombCheck.getPosition().y;
		 float x1 = (int)world.getBomberman().getPosition().x+0.03F;
		 float y1 =  (int)world.getBomberman().getPosition().y+0.03F;
		 
		 for(int i=0;i<=bombCheck.length;++i)
			 if((x1 == x+i && y1==y)|| (x1 == x-i && y1==y)|| (x1 == x && y1==y+i)|| (x1 == x && y1==y-i))
				 kill = true;
			 
		 
		 for (BrickBase block : world.getBlocks()) {
			 if(block.getPosition().x == x1 && 
			 ((block.getPosition().y> y1&& block.getPosition().y < bombCheck.getPosition().y)||
				(block.getPosition().y< y1&& block.getPosition().y > bombCheck.getPosition().y)))
				 kill = false;
					
			if(block.getPosition().y == y1 &&  ((block.getPosition().x > x1 && block.getPosition().x < bombCheck.getPosition().x)
									||
				(block.getPosition().x <  x1 && block.getPosition().x > bombCheck.getPosition().x)))
				kill = false;

			}
		 
		 if(kill  && !world.bonus && !world.invincibility && !world.getBomberman().flamePass){
			 //bomberman.setDead(true);
			 bomberman.setState(Bomberman.State.DYING); 
		 }
			 
		 
	}
	private void checkBombsTimer(){
		for(Bomb bomb : world.getBombs()){
			if(bomb.state == Bomb.State.BOOM)
			{
				killNearNpc(bomb);
				if(!world.bonus) killBomberman(bomb);
				checkAroundBombs(bomb);
				destroyNearObjects(bomb);
				renderer.playBoomSound();
				world.getBombs().removeValue(bomb, true);
			}
			/*if(bomb.state == Bomb.State.BOOM)
				world.getBombs().removeValue(bomb, true);*/
		}
	}
	private void checkAroundBombs(Bomb bombCheck){
		float x = bombCheck.getPosition().x;
		float y = bombCheck.getPosition().y;
		 Array<Bomb> bombsToRemove = new Array<Bomb>();
		for(int i=1;i<=bombCheck.length;++i){
			for(Bomb bomb : world.getBombs()){
				float x1 = bomb.getPosition().x;
				float y1 = bomb.getPosition().y;
				if((x1 == x+i && y1==y)|| (x1 == x-i && y1==y)|| (x1 == x && y1==y+i)|| (x1 == x && y1==y-i))
					bombsToRemove.add(bomb);
					//bomb.setState(Bomb.State.BOOM);
			}
		}
		//Log.e("countbefore",Integer.toString(bombsToRemove.size));
		for (BrickBase block : world.getBlocks()) {
			for(Bomb bomb : bombsToRemove){
				if(block.getPosition().x == bomb.getPosition().x && 
						((block.getPosition().y> bomb.getPosition().y && block.getPosition().y < bombCheck.getPosition().y)
								||
						(block.getPosition().y< bomb.getPosition().y && block.getPosition().y > bombCheck.getPosition().y)
						)
						)
					bombsToRemove.removeValue(bomb, true);
				
				if(block.getPosition().y == bomb.getPosition().y && 
						((block.getPosition().x > bomb.getPosition().x && block.getPosition().x < bombCheck.getPosition().x)
								||
								(block.getPosition().x <  bomb.getPosition().x && block.getPosition().x > bombCheck.getPosition().x))
						)
					bombsToRemove.removeValue(bomb, true);
			}
			
		}
		for(Bomb bomb : bombsToRemove){
			
			//renderer.playBoomSound();
			bomb.setState(Bomb.State.BOOM);
		}
		/*float x = bombCheck.getPosition().x;
		float y = bombCheck.getPosition().y;
		 Array<Integer> bombsToRemove = new Array<Integer>();
		for(int i=1;i<=bombCheck.length;++i){
			for(Bomb bomb : world.getBombs()){
				float x1 = bomb.getPosition().x;
				float y1 = bomb.getPosition().y;
				if((x1 == x+i && y1==y)|| (x1 == x-i && y1==y)|| (x1 == x && y1==y+i)|| (x1 == x && y1==y-i))
					bombsToRemove.add(world.getBombs().indexOf(bomb, true));
					//bomb.setState(Bomb.State.BOOM);
			}
		}
		 Array<Integer> bombsToRemove2 = new Array<Integer>();
		//Log.e("countbefore",Integer.toString(bombsToRemove.size));
		for (BrickBase block : world.getBlocks()) {
			for(int i =0; i< bombsToRemove.size;++i){
				Bomb bomb = world.getBombs().get(bombsToRemove.get(i));
				if(block.getPosition().x == bomb.getPosition().x && 
						((block.getPosition().y> bomb.getPosition().y && block.getPosition().y < bombCheck.getPosition().y)
								||
						(block.getPosition().y< bomb.getPosition().y && block.getPosition().y > bombCheck.getPosition().y)
						)
						)
					bombsToRemove2.add(bombsToRemove.get(i));
				
				if(block.getPosition().y == bomb.getPosition().y && 
						((block.getPosition().x > bomb.getPosition().x && block.getPosition().x < bombCheck.getPosition().x)
								||
								(block.getPosition().x <  bomb.getPosition().x && block.getPosition().x > bombCheck.getPosition().x))
						)
					bombsToRemove2.add(bombsToRemove.get(i));
			}
			
		}
		for(int i =0; i< bombsToRemove.size;++i){
			if(!bombsToRemove2.contains(bombsToRemove.get(i), true)){
				renderer.playBoomSound();
				world.getBombs().get(bombsToRemove.get(i)).setState(Bomb.State.BOOM);
			}
		}*/
		//Log.e("countafter",Integer.toString(bombsToRemove.size));
	}
	/*
	private void gravityDetection() {	
	
		vedroid.falling = true;
		for (Block block: world.getBlocks()){
			if (vedroid.getPosition().y  >= block.getPosition().y && vedroid.getPosition().y <= block.getPosition().y+1 &&
					vedroid.getPosition().x+vedroid.WIDTH/2 >= block.getPosition().x && vedroid.getPosition().x+vedroid.WIDTH/2 < block.getPosition().x+1) {				
				vedroid.falling= false;
				break;				
			}		
		}		
		if (keys.get(Keys.JUMP)){
			if (vedroid.isdead == false){
				vedroid.getVelocity().y = vedroid.SPEED;
			}		
		}
		else{
			if (vedroid.falling == true){
				vedroid.getVelocity().y = -vedroid.SPEED;
				
			}else{
				vedroid.getVelocity().y = 0;
			}
		}
	}	*/

	/*private void collisionDetectionBlocks() {
		for (BrickBase block: world.getBlocks()){	
			for(NpcBase npc: world.getNpcs()){
				if(!(npc instanceof Kondoria || npc instanceof Ovapi || npc instanceof Pontan) ||
						((npc instanceof Kondoria || npc instanceof Ovapi || npc instanceof Pontan) && block.getName() == HardBrick.Name)){
					if(npc.direction == NpcBase.Direction.UP)
						if(npc.position.y+1.1*npc.SIZE > block.position.y && npc.position.y+1.1*npc.SIZE <block.position.y + BrickBase .SIZE
								&& npc.position.x + 0.4*npc.SIZE > block.position.x && npc.position.x + 0.4F*npc.SIZE < block.position.x+ BrickBase .SIZE){
							npc.newDirection(true);
						}
					
					if(npc.direction == NpcBase.Direction.DOWN)
						if(npc.position.y-0.2*npc.SIZE> block.position.y && npc.position.y-0.2*npc.SIZE<block.position.y + BrickBase .SIZE
						&&npc.position.x + 0.4*npc.SIZE > block.position.x && npc.position.x + 0.4F*npc.SIZE < block.position.x+ BrickBase .SIZE){
								npc.newDirection(true);
						}
					
					if(npc.direction == NpcBase.Direction.RIGHT)
						if(npc.position.y+0.4*npc.SIZE > block.position.y && npc.position.y+0.4*npc.SIZE <block.position.y + BrickBase .SIZE
						&& npc.position.x + 1.1*npc.SIZE > block.position.x && npc.position.x + 1.1*npc.SIZE < block.position.x+BrickBase .SIZE){
							npc.newDirection(true);
						}
					
					if(npc.direction == NpcBase.Direction.LEFT)
						if(npc.position.y+0.4*npc.SIZE > block.position.y && npc.position.y+0.4*npc.SIZE <block.position.y + BrickBase .SIZE
						&&npc.position.x - 0.1*npc.SIZE > block.position.x && npc.position.x - 0.1*npc.SIZE  < block.position.x+ BrickBase .SIZE){
							npc.newDirection(true);
						}
				}
				
			
					
				
				
			}
		}
	}*/
	/*private void collisionDetectionBlocks() {
		
		
		//bomberman.setDirection(Bomberman.Direction.NONE);
		
		for (BrickBase block: world.getBlocks()){	
			
			 
			// cool
			if (keys.get(Keys.LEFT)){
				if (bomberman.getPosition().x - Bomberman.WIDTH/10>= block.getPosition().x && bomberman.getPosition().x-Bomberman.WIDTH/10 <= (block.getPosition().x + BrickBase.SIZE) && 
						((bomberman.getPosition().y >= (block.getPosition().y) && bomberman.getPosition().y<= (block.getPosition().y + BrickBase.SIZE))
								|| (bomberman.getPosition().y +Bomberman.HEIGHT*0.9 >= (block.getPosition().y) && bomberman.getPosition().y+ Bomberman.HEIGHT*0.9<= (block.getPosition().y + BrickBase.SIZE))
						// bomberman.getPosition().y+Bomberman.HEIGHT/2 >= (block.getPosition().y) && bomberman.getPosition().y+Bomberman.HEIGHT/2 <= (block.getPosition().y + 1
								)) {				
					//bomberman.getPosition().x = block.getPosition().x+BrickBase.SIZE;
					bomberman.getVelocity().x = 0; 					
				}
				
			}
			
			if (keys.get(Keys.RIGHT)){
				if (((bomberman.getPosition().x+Bomberman.WIDTH+Bomberman.WIDTH/10) >= block.getPosition().x && (bomberman.getPosition().x+Bomberman.WIDTH/10) <= (block.getPosition().x + BrickBase.SIZE))
						&& 
						((bomberman.getPosition().y >= (block.getPosition().y) && bomberman.getPosition().y<= (block.getPosition().y + BrickBase.SIZE))
						|| (bomberman.getPosition().y +Bomberman.HEIGHT*0.9 >= (block.getPosition().y) && bomberman.getPosition().y+ Bomberman.HEIGHT*0.9<= (block.getPosition().y + BrickBase.SIZE))
						))
						 {				
					//bomberman.getPosition().x = block.getPosition().x-Bomberman.WIDTH;
					bomberman.getVelocity().x = 0;					
				}
				
			}
			
			
			if (keys.get(Keys.DOWN)){
				if ((bomberman.getPosition().x+Bomberman.WIDTH*0.1> block.getPosition().x && bomberman.getPosition().x+Bomberman.WIDTH*0.1 < (block.getPosition().x + BrickBase.SIZE)
						|| bomberman.getPosition().x +  Bomberman.WIDTH> block.getPosition().x && bomberman.getPosition().x+Bomberman.WIDTH < (block.getPosition().x + BrickBase.SIZE))
						&& bomberman.getPosition().y-Bomberman.HEIGHT*0.1 >= (block.getPosition().y) && bomberman.getPosition().y-Bomberman.HEIGHT*0.1 <= (block.getPosition().y + BrickBase.SIZE)) {				
					//bomberman.getPosition().y = block.getPosition().y+BrickBase.SIZE;
					bomberman.getVelocity().y = 0;					
				}
				
					
			}
			
			if (keys.get(Keys.UP)){
				if ((bomberman.getPosition().x+Bomberman.WIDTH*0.1> block.getPosition().x && bomberman.getPosition().x+Bomberman.WIDTH*0.1 < (block.getPosition().x + BrickBase.SIZE)
						|| bomberman.getPosition().x +  Bomberman.WIDTH> block.getPosition().x && bomberman.getPosition().x+Bomberman.WIDTH < (block.getPosition().x + BrickBase.SIZE))
						&& bomberman.getPosition().y+Bomberman.HEIGHT*1.1 >= (block.getPosition().y) && bomberman.getPosition().y+Bomberman.HEIGHT*1.1 <= (block.getPosition().y + BrickBase.SIZE)) {				
					//bomberman.getPosition().y = block.getPosition().y-Bomberman.HEIGHT;
					bomberman.getVelocity().y = 0;					
				}
				
			}
			//vertical-ceiling
		
		}	
		
		
	}*/
	
	public void resetWay(){
		rightReleased();
		leftReleased();
		downReleased();
		upReleased();
	}
	
	/** Change Bob's state and parameters based on input controls **/
	private void processInput() {
		bomberman.resetDirection();
		/*if(!hasKeyBoard)
		if((bomberman.getPosition().x<toX && toX -bomberman.getPosition().x <Bomberman.WIDTH) ||
				(bomberman.getPosition().x>toX && bomberman.getPosition().x-toX  <0)
				){
			rightReleased();
			leftReleased();
		}
		if(!hasKeyBoard)
		if((bomberman.getPosition().y > toY &&bomberman.getPosition().y- toY<Bomberman.HEIGHT/2)||
				(bomberman.getPosition().y < toY && toY - (bomberman.getPosition().y)  <Bomberman.HEIGHT)
				){
			downReleased();
			upReleased();
		}*/
		
		if (!bomberman.isDead()){
			if (keys.get(Keys.LEFT)) {
				
				// left is pressed
				//vedroid.setFacingLeft(true);
				bomberman.setState(Bomberman.State.WALKING);
				bomberman.setDirection(Bomberman.Direction.LEFT, true);
				//bomberman.getDirection().put(Bomberman.Direction.LEFT, true);
				bomberman.getVelocity().x = -bomberman.getSpeed();
				
			}
			
			if (keys.get(Keys.RIGHT)) {
				bomberman.setDirection(Bomberman.Direction.RIGHT, true);
				// right is pressed
				//vedroid.setFacingLeft(false);
				bomberman.setState(Bomberman.State.WALKING);
				//bomberman.getDirection().put(Bomberman.Direction.RIGHT, true);
				//if(Math.abs(toX-vedroid.getPosition().x)< Math.abs(toY-vedroid.getPosition().y))
				//	vedroid.getVelocity().x = (float)(vedroid.SPEED/45 * Math.atan(Math.abs((toX-vedroid.getPosition().x)/(toY-vedroid.getPosition().y))));	
				//else
				bomberman.getVelocity().x =bomberman.getSpeed();	
			}
			
			if (keys.get(Keys.UP)) {
		
				//vedroid.setFacingLeft(true);
				bomberman.setDirection(Bomberman.Direction.UP, true);
				bomberman.setState(Bomberman.State.WALKING);
				//bomberman.getDirection().put(Bomberman.Direction.UP, true);
				bomberman.getVelocity().y = bomberman.getSpeed();
			}
			
			if (keys.get(Keys.DOWN)) {
				
				//vedroid.setFacingLeft(true);
				bomberman.setDirection(Bomberman.Direction.DOWN, true);
				//bomberman.getDirection().put(Bomberman.Direction.DOWN, true);
				bomberman.setState(Bomberman.State.WALKING);
				bomberman.getVelocity().y = -bomberman.getSpeed();
			}
			
			
			// need to check if both or none direction are pressed, then Bob is idle
			if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
					(!keys.get(Keys.LEFT) && (!keys.get(Keys.RIGHT)))) {
				//bomberman.setState(State.IDLE);
				// acceleration is 0 on the x
				bomberman.getAcceleration().x = 0;
				// horizontal speed is 0
				bomberman.getVelocity().x = 0;			
			}
			if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
					(!keys.get(Keys.UP) && (!keys.get(Keys.DOWN)))) {
				//bomberman.setState(State.IDLE);
				// acceleration is 0 on the x
				bomberman.getAcceleration().y = 0;
				// horizontal speed is 0
				bomberman.getVelocity().y = 0;			
			}
			if(!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)&& !keys.get(Keys.UP) && !keys.get(Keys.DOWN)){
				bomberman.getVelocity().x = 0;
				bomberman.getVelocity().y = 0;
				bomberman.setState(Bomberman.State.IDLE);
			}
			/*if (keys.get(Keys.UP)){
				vedroid.setState(State.JUMPING);			
			}*/
		}
		else {
			bomberman.getVelocity().x = 0;
			bomberman.getVelocity().y = 0;
		}
	

		
		
		
		
	}

}
