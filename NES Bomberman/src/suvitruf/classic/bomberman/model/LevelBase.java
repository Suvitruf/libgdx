package suvitruf.classic.bomberman.model;


import suvitruf.classic.bomberman.model.hiddenobject.HiddenObject;
import suvitruf.classic.bomberman.model.npc.NpcBase;

import com.badlogic.gdx.utils.Array;

public abstract class LevelBase {
	 Array<BrickBase> blocks = new Array<BrickBase>();
	 public Bomberman  bomberman;
	 
	 Array<NpcBase> npcs = new Array<NpcBase>();
	 Array<Bomb> bombs = new Array<Bomb>();
	 Array<Boom> booms = new Array<Boom>();
	 Array<HiddenObject> hiddenObjects = new Array<HiddenObject>();
	 
	 protected int width;
	 protected int height;
		
	public LevelBase(int w, int h) {
		width = w;
		height=h;
		
		
	}
	public void putBoom(Boom boom){
		booms.add(boom);
	}
	

	public Array<HiddenObject> getHiddenObjects() {
		return hiddenObjects ;
	}
	
	
	public Array<BrickBase> getBlocks() {
		return blocks;
	}
	
	public Array<NpcBase> getNpcs() {
		return npcs;
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
	
	public abstract void putBomb(int x, int y , boolean auto, int length);
	public Bomberman getBomberman() {
		return bomberman;
	}
	
	public abstract void createLevel();
	
	
}
