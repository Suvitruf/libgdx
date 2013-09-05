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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameOverScreen implements Screen, InputProcessor{
	BomberMan game;
	private int width, height;
	//private Texture bgTexture;
	private SpriteBatch spriteBatch;
	public OrthographicCamera cam;
	public float ppuX;	// pixels per unit on the X axis
	public float ppuY;	// pixels per unit on the Y axis
	float CAMERA_WIDTH = 10F;
	float CAMERA_HEIGHT = 4F;
	Texture texture;
	public  Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
	Music  sound;
	public boolean soundOn;
	 /** Called when the activity is first created. */
	@Override
	public void show() {  
		soundOn = game.getPrefs().getInteger("sound-on") == 1 ? true : false;
		//Log.e("IntroScreen","show");
		//CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		 textureRegions = new HashMap<String, TextureRegion>();
		CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		//bgTexture = new Texture(Gdx.files.internal("images/bombermanmain.png"));
		spriteBatch = new SpriteBatch();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		loadSounds();
		loadTextures();
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
	}
	
	private void loadTextures(){
		texture  = new Texture(Gdx.files.internal("images/gameover.png"));
		
	}

	
	public void showText(){
		
		spriteBatch.draw(texture,(CAMERA_WIDTH/2-CAMERA_WIDTH/4)*ppuX, 2F*ppuY, CAMERA_WIDTH/2*ppuX, CAMERA_WIDTH/8*ppuY);		
		

		
	}
	private void loadSounds(){
		 sound = Gdx.audio.newMusic(Gdx.files.internal("audio/game-over.mp3"));
	}
	public GameOverScreen(BomberMan game){
		this.game = game;
	}
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK ){
			this.dispose();
			game.setScreen(game.intro);
			
		}
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
		if(!soundOn) return;
		if(!sound.isPlaying()){
			sound.setLooping(true);
			sound.play();
		}
	}
	@Override
	public void render(float delta) {
		if(texture == null) return;
		Gdx.gl.glClearColor(0,0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		showText();
		spriteBatch.end();
		
		if(soundOn) playMusic();
		//Log.e("IntroScreen", width+" - "+height);
		
		//controller.update(delta);
		//renderer.render();
	}
	
	@Override
	public void pause() {
		sound.stop();
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		playMusic();
		// TODO Auto-generated method stub
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void dispose() {
		
		Gdx.input.setInputProcessor(null);
		Gdx.input.setCatchBackKey(false);
		
		try{
			sound.stop();
			sound.dispose();
			this.spriteBatch.dispose();
			texture.dispose();
			textureRegions.clear();
			texture = null;
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
		//this.dispose();
		this.dispose();
		game.startFromBegining();
		//Log.e("intro","touchDown");
	
		return true;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		return true;
	}
}
