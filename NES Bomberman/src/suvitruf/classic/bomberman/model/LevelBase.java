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
