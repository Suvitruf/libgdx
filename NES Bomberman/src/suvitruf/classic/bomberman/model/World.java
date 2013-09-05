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
package suvitruf.classic.bomberman.model;
import suvitruf.classic.bomberman.controller.WalkingControl;
import suvitruf.classic.bomberman.controller.WalkingControlArrows;
import suvitruf.classic.bomberman.model.hiddenobject.*;
import suvitruf.classic.bomberman.model.npc.*;

import android.util.Log;

//import android.util.Log;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
//import suvitruf.nes.bomberman.model.Level1;
import java.util.Random;
//import java.util.Timer;
//import java.util.TimerTask;

public class World {

	/** The blocks making up the world **/
	//Array<BrickBase> blocks = new Array<BrickBase>();
	/** Our player controlled hero **/
	//int stage = 1;
	//LevelBase level;
	public boolean invincibility = false;

	 public Bomberman  bomberman;
	 private  Preferences prefs;
	 public int Left;
	 private int offset = 4;
	 String powerName;
	 Array<NpcBase> npcs;// = new Array<NpcBase>();
	 Array<Bomb> bombs;// = new Array<Bomb>();
	 Array<Boom> booms;// = new Array<Boom>();
	 Array<HiddenObject> hiddenObjects;// = new Array<HiddenObject>();
	Array<BrickBase> blocks;// = new Array<BrickBase>();
	 WalkingControl   walkingControl; 
	 WalkingControlArrows controlArrows;
    //public Bomberman  bomberman;
	public int width;
	public int height;
	public int points;
	public void putBoom(Boom boom){
		booms.add(boom);
	}
	

	public Array<HiddenObject> getHiddenObjects() {
		return hiddenObjects ;
	}
	
	
	public Array<BrickBase> getBlocks() {
		return blocks;
	}
	
	public int getNpcCount(){
		return npcs.size;
	}
	
	public void removeBrick(BrickBase brick){
		blocks.removeValue(brick, true);
		map[(int)brick.getPosition().y][(int)brick.getPosition().x] = 0;
	}
	public Array<NpcBase> getNpcs() {
	
		return npcs;
	}
	
	public void boomLastBomb(){
		if(bombs.size>0 && (getPower("detonator")>=1 || inreasedPowerUp == DetonatorPower.Name)) bombs.get(0).setState(Bomb.State.BOOM);
	}
	
	/*public Array<Bomb> getBombsTop() {
		return bombsTop;
	}*/
	public Array<Boom> getBooms() {
		return booms;
	}
	public Array<Bomb> getBombs() {
		return bombs;
	}
	
	public void setBomberman(Bomberman bomber){
		bomberman = bomber;
	}
	
	public Bomberman getBomberman() {
		return bomberman;
	}
	
	public WalkingControl getWalkingControl() {
		return walkingControl;
	}
	public WalkingControlArrows getWalkingControlArrows(){
		return this.controlArrows;
	}
	public void generateNpcOnPosition(int count, int type, Vector2 position){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 10+generator.nextInt(width-15);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			switch(type){
			case 1:
				npcs.add(new Balloom(this, new Vector2(position.x,position.y)));
				break;
			case 2:
				npcs.add(new Oneal(this,new Vector2(position.x,position.y)));
				break;
			case 3:
				npcs.add(new Doll(this, new Vector2(position.x,position.y)));
				break;
			case 4:
				npcs.add(new Minvo(this,new Vector2(position.x,position.y)));
				break;
			case 5:
				npcs.add(new Kondoria(this, new Vector2(position.x,position.y)));
				break;
			case 6:
				npcs.add(new Ovapi(this,new Vector2(position.x,position.y)));
			case 7:
				npcs.add(new Pass(this, new Vector2(position.x,position.y)));
				
				break;
			case 8:
				npcs.add(new Pontan(this,new Vector2(position.x,position.y)));
				break;
			}
			
		}
	}
	
	public void generateNpcAfterDestroyingObject(Vector2 position){
		String s = getStage();
		//Log.e("g", s);
		int sI =  Integer.parseInt(s);
		if(npcs.size<8)
			generateNpcOnPosition(8-npcs.size,sI<10 ? 1 : (sI<20 ? 2 : (sI<30 ? 3 : (sI <40 ? 4 : 5))), position);
	}
	public boolean putBomb(int x, int y , boolean auto){
		boolean exist = false;
		boolean coolCount = true;
		for (Bomb bomb : bombs) {
			if((int)bomb.position.x == x && (int)bomb.position.y == y){
				exist = true; break;
			}
		}
		for (BrickBase block : blocks) {
			if((int)block.position.x == x && (int)block.position.y == y){
				exist = true;break;
			}
		}
		
		if(!exist){
			if(bombs.size < prefs.getInteger(BombsPower.Name)+(inreasedPowerUp ==BombsPower.Name ?2 : 1)){
				//bombs.add(new Bomb(new Vector2(x, y),auto,1+prefs.getInteger(FlamesPower.Name))); 
				bombs.add(new Bomb(new Vector2(x, y),getPower("detonator")>=1 || inreasedPowerUp == DetonatorPower.Name ? false : true,  (inreasedPowerUp ==FlamesPower.Name ?2 : 1)+prefs.getInteger(FlamesPower.Name))); 
				bomberman.isInsideBomb = true;
			}
			else
				coolCount = false;
		}
		
		return !exist && coolCount;
	}
	
	public String inreasedPowerUp;
	public void inreasePowerUp(String name){
	
		inreasedPowerUp = name;
		/*prefs.putInteger(name, 	prefs.getInteger(name)+1);
		prefs.flush();*/
		if(name.equals(SpeedPower.Name)) getBomberman().setSpeed(getPower(name)+1);
		if(name.equals(BombPassPower.Name)) getBomberman().setBombPass(true);
		if(name.equals(WallPassPower.Name)) getBomberman().setWallPass(true);
		if(name.equals(FlamePassPower.Name)) getBomberman().setFlamePass(true);
	}
	public boolean bonus = false;
	
	public int getPower(String name){
		return prefs.getInteger(name);
	}
	public void generateBonusNpc(int count){
		 try{generateBonusNpcSave(count);}
		 catch(Exception e){
				//Log.e("ex in generateBonusNpc local msg:", e.getLocalizedMessage());
				//Log.e("ex in generateBonusNpc", "--");
		 }
		
	}
	
	private void generateBonusNpcSave(int count){
		 String s = getStage();
		 //s = "A";
		 if(s.equals("A")) generateNpc(count,1);
		 if(s.equals("B")) generateNpc(count,2);
		 if(s.equals("C")) generateNpc(count,3);
		 if(s.equals("D")) generateNpc(count,4);
		 if(s.equals("E")) generateNpc(count,6);
		 if(s.equals("F")) generateNpc(count,5); 
		 if(s.equals("G")) generateNpc(count,7);
		 if(s.equals("H")) generateNpc(count,8);
		 if(s.equals("I")) generateNpc(count,8);
		 if(s.equals("J")) generateNpc(count,8);
	}
	
	private void generateAllNpc(int sI){
		switch(sI){
		
		case 1:
			generateNpc(6,1);
			break;
		case 2:
			generateNpc(3,1);
			generateNpc(3,2); 
			break;
		case 3:
			generateNpc(2,1);
			generateNpc(2,2);
			generateNpc(2,3);
			break;
		case 4:
			generateNpc(1,1);
			generateNpc(1,2);
			generateNpc(2,3);
			generateNpc(2,4);
			break;
		case 5:
			generateNpc(4,2);
			generateNpc(3,3);
			break;
		case 6:
			generateNpc(2,2);
			generateNpc(3,3);
			generateNpc(2,4);
			break;
		case 7:
			generateNpc(2,2);
			generateNpc(3,3);
			generateNpc(2,6);
			break;
		case 8:
			generateNpc(1,2);
			generateNpc(2,3);
			generateNpc(4,4);
			break;
		case 9:
			generateNpc(1,2);
			generateNpc(1,3);
			generateNpc(4,4);
			generateNpc(1,5);
			break;
		case 10:
			generateNpc(1,2);
			generateNpc(1,3);
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(3,5);
			break;
			
		case 11:
			generateNpc(1,2);
			generateNpc(2,3);
			generateNpc(3,4);
			generateNpc(1,6);
			generateNpc(1,5);
			break;
		case 12:
			generateNpc(1,2);
			generateNpc(1,3);
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(4,5);
			break;
		case 13:
			generateNpc(3,3);
			generateNpc(3,4);
			generateNpc(3,5);
			break;
		case 14:
			generateNpc(7,6);
			generateNpc(1,7);
		
			break;
		case 15:
			generateNpc(1,3);
			generateNpc(3,4);
			generateNpc(3,5);
			generateNpc(1,7);
			break;
		case 16:
			generateNpc(3,4);
			generateNpc(4,5);
			generateNpc(1,7);
			break;
		case 17:
			generateNpc(5,3);
			generateNpc(2,5);
			generateNpc(1,7);
			break;
		case 18:
			generateNpc(3,1);
			generateNpc(3,2);
			generateNpc(2,7);
			break;
		case 19:
			generateNpc(1,2);
			generateNpc(1,3);
			generateNpc(4,4);
			generateNpc(1,5);
			break;
		case 20:
			generateNpc(1,1);
			generateNpc(1,2);
			generateNpc(3,3);
			generateNpc(1,6);
			generateNpc(2,7);
			break;
			
		case 21:
			generateNpc(3,6);
			generateNpc(4,5);
			generateNpc(2,7);
			break;
		case 22:
			generateNpc(4,3);
			generateNpc(3,4);
			generateNpc(1,5);
			generateNpc(1,7);
			break;
		case 23:
			generateNpc(2,3);
			generateNpc(2,4);
			generateNpc(2,5);
			generateNpc(2,6);
			generateNpc(1,7);
			break;
		case 24:
			generateNpc(1,2);
			generateNpc(1,4);
			generateNpc(2,6);
			generateNpc(4,5);
			generateNpc(1,7);
			break;
		case 25:
			generateNpc(2,2);
			generateNpc(1,3);
			generateNpc(1,4);
			generateNpc(2,5);
			generateNpc(2,6);
			generateNpc(1,7);
			break;
		case 26:
			generateNpc(1,1);
			generateNpc(1,2);
			generateNpc(1,3);
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(2,5);
			generateNpc(1,7);
			break;
		case 27:
			generateNpc(1,1);
			generateNpc(1,2);
			generateNpc(1,6);
			generateNpc(5,5);
			generateNpc(1,7);
			break;
		case 28:
			generateNpc(1,2);
			generateNpc(3,3);
			generateNpc(3,4);
			generateNpc(1,5);
			generateNpc(1,7);
			break;
		case 29:
			generateNpc(4,6);
			generateNpc(2,5);
			generateNpc(2,7);
			break;
		case 30:
			generateNpc(3,3);
			generateNpc(2,4);
			generateNpc(2,6);
			generateNpc(1,5);
			generateNpc(1,7);
			break;
			
			
			
			
		case 31:
			generateNpc(2,2);
			generateNpc(2,3);
			generateNpc(2,4);
			generateNpc(2,5);
			generateNpc(2,6);
			break;
		case 32:
			generateNpc(1,2);
			generateNpc(1,3);
			generateNpc(3,4);
			generateNpc(4,5);
			generateNpc(1,7);
			break;
		case 33:
			generateNpc(2,3);
			generateNpc(2,4);
			generateNpc(1,6);
			generateNpc(3,5);
			generateNpc(2,7);
			break;
		case 34:
			generateNpc(2,3);
			generateNpc(3,4);
			generateNpc(3,5);
			generateNpc(2,7);
			break;
		case 35:
			generateNpc(2,3);
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(3,5);
			generateNpc(2,7);
			break;
		case 36:
			generateNpc(2,3);
			generateNpc(2,4);
			generateNpc(3,5);
			generateNpc(3,7);

			break;
		case 37:
			generateNpc(2,3);
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(3,5);
			generateNpc(3,7);
			break;
		case 38:
			generateNpc(2,3);
			generateNpc(2,4);
			generateNpc(3,5);
			generateNpc(3,7);
			break;
		case 39:
			generateNpc(1,3);
			generateNpc(1,4);
			generateNpc(2,5);
			generateNpc(2,6);
			generateNpc(4,7);
			break;
		case 40:
			generateNpc(1,3);
			generateNpc(2,4);
			generateNpc(3,5);
			generateNpc(4,7);
			break;
			
			
		case 41:
			generateNpc(1,3);
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(3,5);
			generateNpc(4,7);
			break;
		case 42:
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(3,5);
			generateNpc(5,7);
			break;
		case 43:
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(2,5);
			generateNpc(6,7);
			break;
		case 44:
			generateNpc(1,4);
			generateNpc(1,6);
			generateNpc(2,5);
			generateNpc(6,7);
			break;
		case 45:
			generateNpc(2,6);
			generateNpc(2,5);
			generateNpc(6,7);
			break;
		case 46:
			generateNpc(2,6);
			generateNpc(2,5);
			generateNpc(6,7);

			break;
		case 47:
			generateNpc(2,6);
			generateNpc(2,5);
			generateNpc(6,7);
			break;
		case 48:
			generateNpc(1,6);
			generateNpc(2,5);
			generateNpc(6,7);
			generateNpc(1,8);
			break;
		case 49:
			generateNpc(2,6);
			generateNpc(1,5);
			generateNpc(6,7);
			generateNpc(1,8);
			break;
		case 50:
			generateNpc(2,6);
			generateNpc(1,5);
			generateNpc(5,7);
			generateNpc(2,8);
			break;
		default:
			
			generateNpc(6,8);
			break;
		}
	}
	
	public int[][]  map;
	public void createLevel() {
		
		
		bomberman = new Bomberman(this, new Vector2(5.01F, 1.01F));
		bomberman.setSpeed(getPower("speed"));
		bomberman.setWallPass(getPower("wallpass")>=1 ? true : false);
		bomberman.setBombPass(getPower("bombpass")>=1 ? true : false);
		bomberman.setFlamePass(getPower("flamepass")>=1 ? true : false);
		//bomberman = new Bomberman(this, new Vector2(1.1F, 1.1F));

		
		for (int i = offset; i <= width-offset; i++) {			
			blocks.add(new HardBrick(new Vector2(i, 0)));
			blocks.add(new HardBrick(new Vector2(i, height-2)));
		}
		for (int i = 0; i < height-1; i++) {
			
			blocks.add(new HardBrick(new Vector2(offset, i)));
			blocks.add(new HardBrick(new Vector2(width-offset, i)));
		}
		
		for (int i = offset; i < width-offset; i++) {
			for (int j = 1; j< height-1; j++) {
				if(i%2==0 && j%2 == 0)
					blocks.add(new HardBrick(new Vector2(i, j)));
			}
		}
		int bricksCount = (int)((width-2)*(height-2)/6);
		String s = getStage();
		//Log.e("g", s);
		int sI = -1;
		try{sI = Integer.parseInt(s); /*sI=1;*/}
		catch(Exception e){bonus = true; }
		if(bonus)
			//Log.e("stage", s);
			generateBonusNpc(6);  

		else
		{
			if(sI == 1 || sI == 7|| sI == 15 || sI == 27 || sI == 38)
				powerName = FlamesPower.Name;
			if(sI == 2 || sI == 5|| sI == 6 || sI == 11 || sI == 12 || sI == 17 || sI == 19 || sI == 23 || sI == 28 || sI == 32)
				powerName = BombsPower.Name;
			if(sI == 3 || sI == 8 || sI == 13 || sI == 20 || sI == 22 || sI == 24 || sI == 29 || sI == 33 || sI == 37 || sI == 41 || sI == 44 || sI == 48)
				powerName = DetonatorPower.Name;
			if(sI == 4)
				powerName = SpeedPower.Name;
			if(sI == 9 || sI == 14 || sI == 18 || sI == 21 || sI == 25 || sI == 35 || sI == 43 || sI == 47)
				powerName = BombPassPower.Name;
			if(sI == 10  || sI == 16 || sI == 31 || sI == 39 || sI == 42 || sI == 46)
				powerName = WallPassPower.Name;
			if(sI == 30  || sI == 36 || sI == 49)
				powerName = FlamePassPower.Name;
			if(sI == 26  || sI == 34 || sI == 40|| sI == 45|| sI == 50)
				powerName = MysteryPower.Name;
			
			
			//powerName = BombPassPower.Name;
			generateBricks(bricksCount);
			generateAllNpc(sI);
			
			
			/*
			if(sI == 1 || sI == 2 || sI == 3|| sI == 4|| sI == 18|| sI == 19 || sI == 26 || sI == 27)
				generateNpc(1,1);
			if(sI == 1 || sI == 2 || sI == 3|| sI == 18)
				generateNpc(1,1);
			if(sI == 1 || sI == 2 || sI == 18)
				generateNpc(1,1);
			if(sI == 1 )
				generateNpc(3,1);
			
			if((sI >= 2 && sI <=12) || sI == 19  || sI == 20 || (sI >=25 && sI<=28) || sI == 31 || sI == 32)
				generateNpc(1,2);		
			if(sI==2 || sI==3 || sI==5 || sI==6|| sI==7 || sI==31)
				generateNpc(1,2);		
			if(sI==2 || sI==5 )
				generateNpc(1,2);
			if(sI==5) generateNpc(1,2);
			
			if((sI>=3 && sI<= 13) || sI==15  )
				generateNpc(1,3);
			
			if(sI==3 || sI==4 || sI==5 || sI==6|| sI==7 || sI==8 || sI==11 || sI==13)
				generateNpc(1,3);
			
			if(sI==5 || sI==6 || sI==7 || sI==13)
				generateNpc(1,3);*/
			
		}
		/*switch(sI){
		
			case 1:
				powerName = FlamesPower.Name;
				//powerName = DetonatorPower.Name;
				generateBricks(bricksCount);
				//generateNpc(8,7);
				generateNpc(6,1);
				break;
			case 2:
				powerName = BombsPower.Name;	
				generateBricks(bricksCount);
				generateNpc(3,1);
				generateNpc(3,2);
				break;
			case 3:
				
				powerName = DetonatorPower.Name;
				generateBricks(bricksCount);
				generateNpc(2,1);
				generateNpc(2,2);
				generateNpc(2,3);
				break;
			case 4:
				
				powerName = SpeedPower.Name;
				generateBricks(bricksCount);
				generateNpc(1,1);
				generateNpc(1,2);
				generateNpc(2,3);
				generateNpc(2,4);
				break;
			case 5:
				
				powerName = BombsPower.Name;
				generateBricks(bricksCount);
				generateNpc(4,2);
				generateNpc(3,3);
				break;
			case 6:
				
				powerName = BombsPower.Name;
				generateBricks(bricksCount);
				generateNpc(2,2);
				generateNpc(3,3);
				generateNpc(2,4);
				break;
			case 7:
				
				powerName = FlamesPower.Name;
				generateBricks(bricksCount);
				generateNpc(2,2);
				generateNpc(3,3);
				generateNpc(2,6);
				break;
			case 8:
				
				powerName = DetonatorPower.Name;
				generateBricks(bricksCount);
				generateNpc(1,2);
				generateNpc(2,3);
				generateNpc(4,4);
				break;
			case 9:
				
				powerName = BombsPower.Name;
				generateBricks(bricksCount);
				generateNpc(1,2);
				generateNpc(1,3);
				generateNpc(4,4);
				generateNpc(1,5);
				break;
			case 10:
				
				powerName = WallPassPower.Name;
				generateBricks(bricksCount);
				generateNpc(1,2);
				generateNpc(1,3);
				generateNpc(1,4);
				generateNpc(1,6);
				generateNpc(3,5);
				break;
			default:
				
				powerName = BombsPower.Name;
				generateBricks(bricksCount);
				generateNpc(6,8);
				break;
		}*/
		
		
		
	}
	
	
	
	private String getStage(){
		return  prefs.getString("stage");
	}
	
	private void generateBricks(int bricksCount){
		Door door = null;
		HiddenObject power = null;
		Random generator = new Random();
		int x,y;
		for(int i=0; i<bricksCount;++i){
			x = offset+generator.nextInt(width-2*offset);
			y = 2+generator.nextInt(height-3);
			if(x%2==0 && y%2==0)
				x++;
			
		
			
			boolean exist = false;
			for (BrickBase block : blocks) {
				if(block.position.x == x && block.position.y == y)
				{
					exist = true;
				}
			}
			if(!exist)
			{
				blocks.add(new Brick(new Vector2(x, y)));
				if(door == null) door = new Door(new Vector2(x,y));
				else
					
					if(power == null)
					{
						if(powerName.equals(FlamesPower.Name))
							power = new FlamesPower(new Vector2(x,y));
						if(powerName.equals(BombsPower.Name))
							power = new BombsPower(new Vector2(x,y));
						if(powerName.equals(SpeedPower.Name))
							power = new SpeedPower(new Vector2(x,y));
						if(powerName.equals(BombPassPower.Name))
							power = new BombPassPower(new Vector2(x,y));
						if(powerName.equals(WallPassPower.Name))
							power = new WallPassPower(new Vector2(x,y));
						if(powerName.equals(DetonatorPower.Name))
							power = new DetonatorPower(new Vector2(x,y));
						if(powerName.equals(FlamePassPower.Name))
							//Log.e("flamepss", "true");
							power = new FlamePassPower(new Vector2(x,y));
							//Log.e("flamepss", power.getName());
						if(powerName.equals(MysteryPower.Name))
							power = new MysteryPower(new Vector2(x,y));
						
					}
					
			}
		}
		
		hiddenObjects.add(door);
		hiddenObjects.add(power);
		
	}
	/*
	private void generateOneals(int count){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 10+generator.nextInt(width-15);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			npcs.add(new Oneal(this, new Vector2(x, y)));
		}
	}
	private void generateBallooms(int count){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 10+generator.nextInt(width-15);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			npcs.add(new Balloom(this, new Vector2(x, y)));
		}
	}
	

	private void generateDolls(int count){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 10+generator.nextInt(width-15);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			npcs.add(new Doll(this, new Vector2(x, y)));
		}
	}
	

	private void generateMinvos(int count){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 10+generator.nextInt(width-15);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			npcs.add(new Minvo(this, new Vector2(x, y)));
		}
	}
	
	public void generatePontans(int count){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 5+generator.nextInt(width-10);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			npcs.add(new Pontan(this, new Vector2(x, y)));
		}
	}
	

	public void generateOvapi(int count){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 5+generator.nextInt(width-10);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			npcs.add(new Ovapi(this, new Vector2(x, y)));
		}
	}*/
	
	public void generateNpcSave(int count, int type){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 10+generator.nextInt(width-15);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			switch(type){
			case 1:
				npcs.add(new Balloom(this, new Vector2(x, y)));
				break;
			case 2:
				npcs.add(new Oneal(this, new Vector2(x, y)));
				break;
			case 3:
				npcs.add(new Doll(this, new Vector2(x, y)));
				break;
			case 4:
				npcs.add(new Minvo(this, new Vector2(x, y)));
				break;
			case 5:
				npcs.add(new Kondoria(this, new Vector2(x, y)));
				break;
			case 6:
				npcs.add(new Ovapi(this, new Vector2(x, y)));
			case 7:
				npcs.add(new Pass(this, new Vector2(x, y)));
				
				break;
			case 8:
				npcs.add(new Pontan(this, new Vector2(x, y)));
				break;
			}
			
		}
	}
	public void generateNpc(int count, int type){
		try{generateNpcSave(count,  type);}
		catch(Exception ee){
			//Log.e("generateNpc","er");
		}
		
	}

	/*public void generateKondoria(int count){
		Random generator = new Random();
		int x = 0,y =0;
		
		for(int i=0;i<count;++i)
		{
			boolean exist = true;
			
			
			while(exist)
			{
				exist = false;
				x = 5+generator.nextInt(width-10);
				y = 2+generator.nextInt(height-3);
				for (BrickBase block : blocks) {
					if(block.position.x == x && block.position.y == y)
					{
						exist = true;
					}
				}
				
			}
			npcs.add(new Kondoria(this, new Vector2(x, y)));
		}
	}*/
	
	/*public Array<Bomb> getBombsTop() {
		return level.getBombsTop();
	}*/
	public World(/*int w, int h,int stage*/Preferences  prefs) {
		 inreasedPowerUp = "";
		//width = w;
		//height=h;
		width = 32;
		walkingControl = new WalkingControl (new Vector2(0F,0F), new Vector2(prefs.getFloat("xbomb"),prefs.getFloat("ybomb")),
				new Vector2(prefs.getFloat("xdetonator"),prefs.getFloat("ydetonator")));
		//controlArrows = new WalkingControlArrows(new Vector2(3F,3F), new Vector2(7F,3F), new Vector2(5F,5F), new Vector2(5F,1F));
		controlArrows = new WalkingControlArrows(new Vector2(prefs.getFloat("xl"),prefs.getFloat("yl")), 
				new Vector2(prefs.getFloat("xr"),prefs.getFloat("yr")), 
				new Vector2(prefs.getFloat("xu"),prefs.getFloat("yu")), 
				new Vector2(prefs.getFloat("xd"),prefs.getFloat("yd")),
				new Vector2(prefs.getFloat("xbomb"),prefs.getFloat("ybomb")),
				new Vector2(prefs.getFloat("xdetonator"),prefs.getFloat("ydetonator"))
		);
		height=14;
		//this.stage = stage;
		this.prefs =  prefs;
		points = this.prefs.getInteger("points");
		Left = prefs.getInteger("left");
		
		npcs = new Array<NpcBase>();
		 bombs = new Array<Bomb>();
		 booms = new Array<Boom>(); 
		 hiddenObjects = new Array<HiddenObject>();
		blocks = new Array<BrickBase>();
		
		if(!prefs.getString("stage").equals("51")) createLevel();
		
		map = new int[height][width];
		map = new int[height][width];
		
		for(int i=0;i<height;++i)
			for(int j=0;j<width;++j)
				map[i][j]= 0;
		setMap();
		//else bomberman = new Bomberman(this, new Vector2(0, 2F)); 
			
		//createDemoWorld();
		
	}
	private void setMap(){
		for (BrickBase block : blocks)
			if(block instanceof Brick)
				map[(int)block.position.y][(int)block.position.x] = 2;
			else
				map[(int)block.position.y][(int)block.position.x] = 1;
	}
	/*public void setSize (int w, int h) {
		width = w;
		height=h;
	}*/
	
	public void dispose(){
		booms.clear();
		bombs.clear();
		blocks.clear();
		hiddenObjects.clear();
		npcs.clear();
		
	}
	
}
