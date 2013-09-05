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



/*import suvitruf.nes.bomberman.model.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;*/
import com.badlogic.gdx.math.Vector2;


public class Brick extends BrickBase {
	public static String textureName = "images/BrickSprite.png";
	public static final float SPEED = 3f;
	//private  Texture texture;//  = new Texture(Gdx.files.internal("images/BombermanSprite.png"));
	//private TextureRegion[] textureFrames; 
	public static final int FRAME_COLS =4; 
	public static final int FRAME_ROWS = 2; 
	//public Animation       walkAnimation;
	//public float  curState = 0;
	public static String Name =  "Brick";
	//public  String name =  "Brick";
	public Brick(Vector2 pos){ 
		super(pos);
		/*
		Texture texture  = new Texture(textureName);
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		 int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];

		walkAnimation = new Animation(SPEED, textureFrames); */
		name =  "Brick";
		//curState = 0; 
		animationState = 0;
	}
	@Override
	public float getAnimationState(){
		switch(state){
		case NONE: 
			break;
		
		case DESTROYING:
			animationState+=0.5;
			if(animationState>16)
				state = State.DESTROYED;
			break;
		case DESTROYED: break;
		}
		
		return animationState;
	}
	/*@Override
	public TextureRegion getCurrentFrame(){
		//TextureRegion curFrame;
		//curState+=0.5;
		switch(state){
			case NONE: 
				break;
			
			case DESTROYING:
				animationState+=0.5;
				if(animationState>16)
					state = State.DESTROYED;
				break;
			case DESTROYED: break;
		}

		//curFrame = walkAnimation.getKeyFrame(curState, true);
		
		return null;//walkAnimation.getKeyFrame(curState, true);
		//return curFrame;
	}*/
	/*
	@Override
	public String getName(){
		return "Brick";
	}*/
}
