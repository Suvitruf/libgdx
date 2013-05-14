package suvitruf.libgdxtutorial.lesson8.screens;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;


import suvitruf.libgdxtutorial.lesson8.MyGame;
//import suvitruf.nes.bomberman.controller.WorldController;
//import suvitruf.nes.bomberman.model.NpcBase;
//import suvitruf.nes.bomberman.model.World;
//import suvitruf.nes.bomberman.view.WorldRenderer;
//import android.os.Bundle;
//import android.content.Intent;

//import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.backends.android.AndroidApplication;
//import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IntroScreen implements Screen, InputProcessor {
	MyGame game;
	private int width, height;
	private Texture bgTexture;
	private SpriteBatch spriteBatch;
	public OrthographicCamera cam;
	public float ppuX;	// pixels per unit on the X axis
	public float ppuY;	// pixels per unit on the Y axis
	float CAMERA_WIDTH = 800F;
	float CAMERA_HEIGHT = 480F;
	public  Map<String, Texture> textures;
	boolean downBtn;
	 /** Called when the activity is first created. */ 
	@Override
	public void show() {
		
		//CAMERA_WIDTH =  Math.max(CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight(),CAMERA_WIDTH);
		textures = new HashMap<String, Texture>();
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		downBtn = false;
		spriteBatch = new SpriteBatch();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		//SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
		loadTextures();
		Gdx.input.setInputProcessor(this);
	}
	//установка камеры
	  public void SetCamera(float x, float y){
		//	Log.e("size",Float.toString(x)+"-"+Float.toString(y));
	    this.cam.position.set(x, y,0);	
	    this.cam.update();
	  }
	private void loadTextures(){
		
		bgTexture = new Texture(Gdx.files.internal("images/bg.png"));
		textures.put("cover_button_start_up", new Texture(Gdx.files.internal("images/cover_button_start_up.png")));
		textures.put("cover_button_start_down", new Texture(Gdx.files.internal("images/cover_button_start_down.png")));
	}
	public void showMenu(){
		if(downBtn)
			spriteBatch.draw(textures.get("cover_button_start_down"),653, 183, 256 , 128);
		else
			spriteBatch.draw(textures.get("cover_button_start_up"),653, 183, 256 , 128);
		
	}
	
	public void showBG(){
		spriteBatch.draw(bgTexture,0, -32, 1024 , 512);
		
	}
	
	public IntroScreen(MyGame game){
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
	
	
	@Override
	public void render(float delta) {
		//this.cam.update();
		SetCamera(CAMERA_WIDTH/2, CAMERA_HEIGHT / 2f);
		spriteBatch.setProjectionMatrix(this.cam.combined); 
		 //timeLeft+=delta;
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//Gdx.gl.glViewport(0, 0,1200,480);
		
		
		spriteBatch.begin();
		showBG();
		showMenu();
		spriteBatch.end();
		
		
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
		
		Gdx.input.setInputProcessor(null);
		
		
		try{
			
			spriteBatch.dispose();		
			
	
			bgTexture.dispose();
			textures.clear();
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
		
		if((height-y)/ppuY >= 213 && (height-y)/ppuY <= 283 && x/ppuX>=660 && x/ppuX<=780)
			downBtn = true;
		
		return true;
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
		if(downBtn){
			dispose();
			game.setScreen(game.game);
		}
			
		downBtn = false;
		return true;
	}
}
