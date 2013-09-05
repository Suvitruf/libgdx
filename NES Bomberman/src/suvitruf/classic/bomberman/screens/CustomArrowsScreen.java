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
import suvitruf.classic.bomberman.controller.WalkingControlArrows;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class CustomArrowsScreen implements Screen, InputProcessor {
	BomberMan game;
	private int width, height;
	//private Texture bgTexture;
	private SpriteBatch spriteBatch;
	public OrthographicCamera cam;
	public float ppuX;	// pixels per unit on the X axis
	public float ppuY;	// pixels per unit on the Y axis
	float CAMERA_WIDTH = 10f;
	float CAMERA_HEIGHT = 14f;
	ShapeRenderer bgRenderer;// = new ShapeRenderer();
	Texture texture;
	WalkingControlArrows controlArrows;
	public  Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
	
	Music  sound; 
	
	Sprite arrowLeft;
	Sprite arrowRight;
	Sprite arrowUp;
	Sprite arrowDown;
	boolean joy;
	 /** Called when the activity is first created. */
	@Override
	public void show() {
		joy = game.getPrefs().getInteger("controller-type") == 1 ? true : false; 
		textureRegions = new HashMap<String, TextureRegion>();
		bgRenderer = new ShapeRenderer();
		//Log.e("CustomArrowsScreen","show");
		//CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		
		//bgTexture = new Texture(Gdx.files.internal("images/bombermanmain.png"));
		spriteBatch = new SpriteBatch();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		//loadSounds();
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		controlArrows = new WalkingControlArrows(new Vector2(game.getPrefs().getFloat("xl"),game.getPrefs().getFloat("yl")), 
				new Vector2(game.getPrefs().getFloat("xr"),game.getPrefs().getFloat("yr")), 
				new Vector2(game.getPrefs().getFloat("xu"),game.getPrefs().getFloat("yu")), 
				new Vector2(game.getPrefs().getFloat("xd"),game.getPrefs().getFloat("yd")),
				
				new Vector2(game.getPrefs().getFloat("xbomb"),game.getPrefs().getFloat("ybomb")),
				new Vector2(game.getPrefs().getFloat("xdetonator"),game.getPrefs().getFloat("ydetonator"))
		);
		
		WalkingControlArrows.Coefficient =  1+0.3F*game.getPrefs().getInteger("controller-size");
		WalkingControlArrows.Opacity = game.getPrefs().getInteger("opacity");
		
		loadTextures();
	}
	
	private void loadTextures(){
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth()/ 2, texture.getHeight() );
		
		TextureRegion tmpMain[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth(), tmpLeftRight[0][0].getRegionHeight()/2);
		TextureRegion tmpTop[][] =  tmpMain[0][0].split(tmpMain[0][0].getRegionWidth()/2, tmpMain[0][0].getRegionHeight());
		
		TextureRegion tmp[][] = tmpTop[0][0].split(tmpTop[0][0].getRegionWidth()/ 8, tmpTop[0][0].getRegionHeight() / 16);	
	
	
	
		textureRegions.put("bomb", tmp[7][4]);
		textureRegions.put("detonator", tmp[1][2]);
		
		TextureRegion controls[][] = tmpLeftRight[0][1].split(tmpLeftRight[0][1].getRegionWidth(), tmpLeftRight[0][1].getRegionHeight()/2);
		
		TextureRegion controls2[][] = controls[1][0].split( controls[1][0].getRegionWidth()/2,  controls[1][0].getRegionHeight()/2);
		//textureRegions.put("khob",   controls2[0][1]);
		arrowLeft = new  com.badlogic.gdx.graphics.g2d.Sprite( controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
		arrowLeft.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowLeft.rotate90(true);
		arrowLeft.rotate90(true);
		arrowLeft.rotate90(true);
		arrowRight = new  com.badlogic.gdx.graphics.g2d.Sprite( controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
		arrowRight.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowRight.rotate90(true);
	
		arrowDown = new  com.badlogic.gdx.graphics.g2d.Sprite( controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
		arrowDown.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowDown.rotate90(true);
		arrowDown.rotate90(true);
		
		arrowUp = new  com.badlogic.gdx.graphics.g2d.Sprite( controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
		arrowUp.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		
	}
	
private void drawWalkingControlArrows(){
		
		//WalkingControlArrows control = world.getWalkingControlArrows();
		/*
		arrow.setPosition(control.positionLeft.x*ppuX, control.positionLeft.y*ppuY);
		arrow.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		arrow.rotate(90);*/
		
	
		
		arrowLeft.setPosition(controlArrows.positionLeft.x*ppuX, controlArrows.positionLeft.y*ppuY);
		arrowRight.setPosition(controlArrows.positionRight.x*ppuX, controlArrows.positionRight.y*ppuY);
		arrowUp.setPosition(controlArrows.positionUp.x*ppuX, controlArrows.positionUp.y*ppuY);
		arrowDown.setPosition(controlArrows.positionDown.x*ppuX, controlArrows.positionDown.y*ppuY);
		arrowLeft.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		arrowDown.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		arrowRight.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		arrowUp.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		
		
		if(!joy){
			arrowLeft.draw(spriteBatch);
			arrowRight.draw(spriteBatch);
			arrowUp.draw(spriteBatch);
			arrowDown.draw(spriteBatch);
		}
		spriteBatch.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		spriteBatch.draw(textureRegions.get("bomb"),controlArrows.bombPosition.x*ppuX, controlArrows.bombPosition.y*ppuY,
				WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX,  WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		spriteBatch.draw(textureRegions.get("detonator"),controlArrows.detonatorPosition.x*ppuX, controlArrows.detonatorPosition.y*ppuY,
				WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX,  WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		
	}
	public CustomArrowsScreen(BomberMan game){
		this.game = game;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK ){
			saveChanges();
			this.dispose();
			game.setScreen(game.settings);
			
		}
		return true;
	}
	
	private void saveChanges(){
		
		game.getPrefs().putFloat("xl", controlArrows.positionLeft.x);
		game.getPrefs().putFloat("yl", controlArrows.positionLeft.y);
		game.getPrefs().putFloat("xr", controlArrows.positionRight.x);
		game.getPrefs().putFloat("yr", controlArrows.positionRight.y);
		game.getPrefs().putFloat("xu", controlArrows.positionUp.x);
		game.getPrefs().putFloat("yu", controlArrows.positionUp.y); 
		game.getPrefs().putFloat("xd", controlArrows.positionDown.x);
		game.getPrefs().putFloat("yd", controlArrows.positionDown.y);
		
		
		game.getPrefs().putFloat("xbomb", controlArrows.bombPosition.x);
		game.getPrefs().putFloat("ybomb", controlArrows.bombPosition.y);
		
		
		game.getPrefs().putFloat("xdetonator", controlArrows.detonatorPosition.x);
		game.getPrefs().putFloat("ydetonator", controlArrows.detonatorPosition.y);
		game.getPrefs().flush();
	}
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
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
	
	private void drawFrame(){
		
	}
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(213/255F,213/255F, 213/255F, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		drawFrame();
		spriteBatch.begin();
		drawWalkingControlArrows();
		spriteBatch.end();
	
		//playMusic();
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
		try{
		Gdx.input.setCatchBackKey(false);
		Gdx.input.setInputProcessor(null);
		textureRegions.clear();
		texture.dispose();
		spriteBatch.dispose();
		bgRenderer.dispose();
		
		}
		catch(Exception e){
			
		}
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		withArrowControl(x, y, pointer);
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
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
	
		withArrowControl(x, y, pointer);
		return true;
	}
	
	private void withArrowControl(int x, int y, int pointer){
		if(!joy){
			if(x/ppuX >= controlArrows.positionLeft.x && x/ppuX <= controlArrows.positionLeft.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
					(height-y)/ppuY >= controlArrows.positionLeft.y && (height-y)/ppuY <= controlArrows.positionLeft.y+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient){
				//Log.e("left", "pressed");
				controlArrows.positionLeft.x = x/ppuX -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
				controlArrows.positionLeft.y = (height-y)/ppuY -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
				return;
			}
				
			
			
			if(x/ppuX >= controlArrows.positionRight.x && x/ppuX <= controlArrows.positionRight.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
					(height-y)/ppuY >= controlArrows.positionRight.y && (height-y)/ppuY <= controlArrows.positionRight.y+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient	){
				//Log.e("Right", "pressed");
				controlArrows.positionRight.x = x/ppuX -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
				controlArrows.positionRight.y = (height-y)/ppuY -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
				return;
			}
			
			if(x/ppuX >= controlArrows.positionUp.x && x/ppuX <= controlArrows.positionUp.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
					(height-y)/ppuY >= controlArrows.positionUp.y && (height-y)/ppuY <= controlArrows.positionUp.y+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient	){
				//Log.e("Up", "pressed");
				controlArrows.positionUp.x = x/ppuX -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
				controlArrows.positionUp.y = (height-y)/ppuY -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
				return;
			}
			
			if(x/ppuX >= controlArrows.positionDown.x && x/ppuX <= controlArrows.positionDown.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
					(height-y)/ppuY >= controlArrows.positionDown.y && (height-y)/ppuY <= controlArrows.positionDown.y+WalkingControlArrows.BSIZE* WalkingControlArrows .Coefficient	){
				//Log.e("Down", "pressed");
				controlArrows.positionDown.x = x/ppuX -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
				controlArrows.positionDown.y = (height-y)/ppuY -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
				return;
			}
		}
		
		
		
		if(x/ppuX >= controlArrows.bombPosition.x && x/ppuX <= controlArrows.bombPosition.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
				(height-y)/ppuY >= controlArrows.bombPosition.y && (height-y)/ppuY <= controlArrows.bombPosition.y+WalkingControlArrows.BSIZE* WalkingControlArrows .Coefficient	){
			//Log.e("Down", "pressed");
			controlArrows.bombPosition.x = x/ppuX -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
			controlArrows.bombPosition.y = (height-y)/ppuY -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
			return;
		}
		
		
		if(x/ppuX >= controlArrows.detonatorPosition.x && x/ppuX <= controlArrows.detonatorPosition.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
				(height-y)/ppuY >= controlArrows.detonatorPosition.y && (height-y)/ppuY <= controlArrows.detonatorPosition.y+WalkingControlArrows.BSIZE* WalkingControlArrows .Coefficient	){
			//Log.e("Down", "pressed");
			controlArrows.detonatorPosition.x = x/ppuX -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
			controlArrows.detonatorPosition.y = (height-y)/ppuY -WalkingControlArrows.BSIZE/2 * WalkingControlArrows.Coefficient;
			return;
		}
		
	}
	@Override
	public boolean keyDown(int keycode) {
		//sound.stop();
		//game.setScreen(game.stage);
		
		
		return true;
	}
	
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		return true;
	}
}
