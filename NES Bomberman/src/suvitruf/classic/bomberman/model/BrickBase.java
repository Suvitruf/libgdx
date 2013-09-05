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

//import com.badlogic.gdx.graphics.g2d.TextureRegion;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BrickBase {

	//3.0d is a double precision floating-point number constant. 
		//3.0, or alternatively 3.0f is a single precision floating-point number constant.
		public static  float SIZE = 1f;
		//public static String Name =  "BrickBase";
		protected  String name ;
		public Vector2 position = new Vector2();
		protected Rectangle bounds = new Rectangle();
		
		public static enum State {NONE, DESTROYING, DESTROYED}
		protected State state;
		public float  animationState = 0;
		
		public BrickBase(Vector2 pos){ //constructor takes parameter vector2 which is a position (x,y)
			this.position = pos;
			this.bounds.width = SIZE;
			this.bounds.height = SIZE;
			state = State.NONE;
			name =  "BrickBase";
		}
		
		public Vector2 getPosition() {
			return position;
		}
		public void setState(State state){
			/*if(!((state == State.DESTROYING && (this.state == State.DESTROYING || this.state == State.DESTROYED)) ||
					(state == State.DESTROYED &&  this.state == State.DESTROYED))
					)*/
				this.state = state;
			
		}
		public State getState(){
			
			return state;
		}
		/*public TextureRegion getCurrentFrame()
		{
			return null;
		}*/
		public float getAnimationState(){
			return animationState;
		}
		public  String getName(){
			return name;
		}
		public Rectangle getBounds() {
			return bounds;
		}

}
