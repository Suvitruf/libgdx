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

import java.util.Timer;
import java.util.TimerTask;


import com.badlogic.gdx.utils.Array;

public class Boom {
	public static String textureName = "images/BoomSprite.png";
	
	public static final int FRAME_COLS =4; 
	public static final int FRAME_ROWS = 8;
	public static final float SPEED = 1f/7;
	public static String Name =  "Boom";
	protected  String name ;
	Array<BoomPart> boomParts = new Array<BoomPart>();
	TimerTask task;
	Timer timer;
	public static enum State {NONE,  BOOM}
	public State state;
	//World world;
	public Boom(Array<BoomPart> boomParts){ 
		
		this.boomParts = boomParts;
		name =  "Boom";
		state = State.NONE;
		createTimer();
	}
	
	private void createTimer(){
		//timeLeft = 0;
		task = new TimerTask(){
			public void run()
		      {
				state = State.BOOM;
				timer.cancel();
				timer.purge();
				timer = null;
		      }
		};
		timer=new Timer();
		
		timer.schedule(task,500);
	}
	public  String getName(){
		return name;
	}
	public State getState() {
		return state;
	}
	public Array<BoomPart> getParts(){
		return boomParts;
	}
}
