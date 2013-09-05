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
package suvitruf.classic.bomberman.screens;

import java.util.HashMap;
import java.util.Map;

import suvitruf.classic.bomberman.BomberMan;
import suvitruf.classic.bomberman.model.Bomberman;

import suvitruf.classic.bomberman.model.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class WinScreen implements Screen, InputProcessor {
	BomberMan game;
	private int width, height;
	//private Texture bgTexture;
	private SpriteBatch spriteBatch;
	public OrthographicCamera cam;
	private World 			world;
	public float ppuX;	// pixels per unit on the X axis
	public float ppuY;	// pixels per unit on the Y axis
	float CAMERA_WIDTH = 10F;
	float CAMERA_HEIGHT = 12F;
	Texture bGexture;
	public boolean soundOn;
	Texture texture;
	public  Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
	public  Map<String,  Animation> animations;// = new HashMap<String,  Animation>();
	Music  sound;
	 /** Called when the activity is first created. */
	@Override
	public void show() {  
		soundOn = game.getPrefs().getInteger("sound-on") == 1 ? true : false;
		//Log.e("IntroScreen","show");
		//CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		textureRegions = new HashMap<String, TextureRegion>();
	   animations = new HashMap<String,  Animation>();
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		//bgTexture = new Texture(Gdx.files.internal("images/bombermanmain.png"));
		spriteBatch = new SpriteBatch();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		loadSounds();
		loadTextures();
		Gdx.input.setInputProcessor(this);
		world  = new World(game.getPrefs());
		world.setBomberman(new Bomberman(world, new Vector2(0, CAMERA_HEIGHT/6+1F)));
		
		world.getBomberman().setDirection(Bomberman.Direction.RIGHT, true);
		world.getBomberman().getVelocity().x = Bomberman.ANIMATIONSPEED;
		world.getBomberman().setState(Bomberman.State.WALKING);
	}
	
	private void loadTextures(){
		bGexture  = new Texture(Gdx.files.internal("images/congratilations.png"));
		
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth()/ 2, texture.getHeight() );
		
		TextureRegion tmpMain[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth(), tmpLeftRight[0][0].getRegionHeight()/2);
		TextureRegion tmpTop[][] =  tmpMain[0][0].split(tmpMain[0][0].getRegionWidth()/2, tmpMain[0][0].getRegionHeight());
		
		TextureRegion tmp[][] = tmpTop[0][0].split(tmpTop[0][0].getRegionWidth()/ 8, tmpTop[0][0].getRegionHeight() / 16);	
		
		textureRegions.put("brick", tmp[2][1]);
		
		TextureRegion tmpBomber[][] =  tmpTop[0][1].split(tmpTop[0][1].getRegionWidth(),tmpTop[0][1].getRegionHeight()/2);		
		loadBombermanAnimation(tmpBomber[0][0].split(tmpBomber[0][0].getRegionWidth()/8,tmpBomber[0][0].getRegionHeight()/8));
		
	}

	private void loadBombermanAnimation(TextureRegion tmp[][]){
		TextureRegion[] textureFrames = new TextureRegion[24];
		int index = 0;
		for (int i = 0; i <=2 ; i++)
			for (int j = 0; j <=7; j++)
				textureFrames[index++] = tmp[i][j];
		animations.put("bomberman", new Animation(Bomberman.ANIMATIONSPEED, textureFrames));
	}
	 
	public void showText(){
		
		
		spriteBatch.draw(bGexture,0, CAMERA_HEIGHT/2*ppuY, CAMERA_WIDTH*ppuX, CAMERA_WIDTH/6*ppuY);	
		
		for(int i=0;i<(int)CAMERA_WIDTH+1;++i)
		spriteBatch.draw(textureRegions.get("brick"),i*ppuX, 
				CAMERA_HEIGHT/6*ppuY, 1F*ppuX, 1F*ppuY);	
		
		
		
	}
	
	private void drawBomberman() {
		
		
		Bomberman bomberman = world.getBomberman();
		if(bomberman.getPosition().x>=CAMERA_WIDTH){
			//Log.e("tt","ttt");
			bomberman.getVelocity().x = 0;
			bomberman.setState(Bomberman.State.IDLE);
			bomberman.setDirection(Bomberman.Direction.RIGHT, false); 
		}
		//if (!bomberman.isDead) {
			//if (vedroid.isFacingLeft()){
			//	spriteBatch.draw(bobTexture, vedroid.getPosition().x * ppuX, vedroid.getPosition().y * ppuY, vedroid.WIDTH * ppuX, vedroid.HEIGHT * ppuY);
			//}else{
			//ShowPos();
			//cam.position.x - renderer.CAMERA_WIDTH/2
					
			//	spriteBatch.draw(bobTexture, (bomberman.getPosition().x- (bomberman.getPosition().x-CAMERA_WIDTH/2))* ppuX, bomberman.getPosition().y * ppuY, Bomberman.WIDTH * ppuX,Bomberman.HEIGHT * ppuY);			
			
			/*
			 //cool
			spriteBatch.draw(bomberman.texture, 
					(bomberman.getPosition().x- (bomberman.getPosition().x-CAMERA_WIDTH/2))* ppuX, bomberman.getPosition().y * ppuY, 
					Bomberman.WIDTH * ppuX,Bomberman.HEIGHT * ppuY);		*/	
			

				spriteBatch.draw(animations.get("bomberman").getKeyFrame(bomberman.getAnimationState(),true), 
						(bomberman.getPosition().x)* ppuX , bomberman.getPosition().y * ppuY, 
						Bomberman.SIZE * ppuX,Bomberman.SIZE * ppuY);
		
			//}
		//}
		//else {
		//	spriteBatch.draw(bobDeadTexture, bob.getPosition().x * ppuX, vedroid.getPosition().y * ppuY, Bob.WIDTH * ppuX, Bob.HEIGHT * ppuY);
		//}
	}

	private void loadSounds(){
		 sound = Gdx.audio.newMusic(Gdx.files.internal("audio/ending-theme.mp3"));
	}
	public WinScreen(BomberMan game){
		this.game = game;
	}
	@Override
	public boolean keyUp(int keycode) {
	
		return true;
	}
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		//sound.stop();
		//startedPlaying = false;
		//game.setScreen(game.game);
		return true;
	}
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		//this.world.setSize (w,h);
	}
	
	@Override
	public void resize(int width, int height) {
		setSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	/*
	private void stopMusic(){
		sound.stop();
	}*/
	
	private void playMusic(){
		if(!sound.isPlaying()){
			sound.setLooping(true);
			sound.play();
		}
	}
	
	@Override
	public void render(float delta) {
		//Log.e("delta",Float.toString(delta));
		world.getBomberman().update(delta);
		Gdx.gl.glClearColor(0,0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		showText();
		drawBomberman();
		spriteBatch.end();
		
		if(soundOn) playMusic();
		//Log.e("IntroScreen", width+" - "+height);
		
		//controller.update(delta);
		//renderer.render();
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean keyTyped(char character) { 
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void dispose() {
		sound.stop();
		sound.dispose();
		Gdx.input.setInputProcessor(null);
		
		try{
			spriteBatch.dispose();
			texture.dispose();
			textureRegions.clear();
			animations.clear();
		}
		catch(Exception e){
			
		}
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		
		// TODO Auto-generated method stub
		return false;
	}

	//@Override
	public boolean touchMoved(int x, int y) {
		
		//ChangeNavigation(x,y);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		//ChangeNavigation(x,y);
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		//Log.e("intro","touchDown");
		if(world.getBomberman().getState()==Bomberman.State.IDLE){
			this.dispose();
			game.startFromBegining();
		}
		return true;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		return true;
	}
}
