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



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BoomPart {
	public static  float SIZE = 1f;
	protected Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	public static enum Type {MIDDLE, UP, DOWN, LEFT, RIGHT, HORIZONTAL, VERTICAL}
	Type type;

	public float  animationState;// = 0;
	
	public BoomPart(Vector2 pos, Type type){ 
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.type = type;
		animationState = 0;
		changeAnimationFrame();
		
	}
	
	private void changeAnimationFrame(){
		switch(type){
		case MIDDLE:
			animationState = Boom.SPEED*16;
			break;
		case HORIZONTAL:
			animationState =Boom.SPEED*20;
			break;
		case VERTICAL:
			animationState =Boom.SPEED*24;
			break;
		case RIGHT:
			animationState =Boom.SPEED*12;
			break;
		case LEFT:
			animationState =Boom.SPEED*4;
			break;
		case UP:
			animationState =0;
			break;
		case DOWN:
			animationState =Boom.SPEED*8;
			break;
		}
	}
	public void setType(Type type){
		this.type = type;
		changeAnimationFrame();
	}
	public Vector2 getPosition() {
		return position;
	}
	public float getAnimationState(){
		animationState+=Gdx.graphics.getDeltaTime();  
		return animationState;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
