package suvitruf.classic.bomberman.screens;

//import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import suvitruf.classic.bomberman.BomberMan;

/*import suvitruf.nes.bomberman.controller.WorldController;
import suvitruf.nes.bomberman.model.NpcBase;
import suvitruf.nes.bomberman.model.World;
import suvitruf.nes.bomberman.view.WorldRenderer;
import android.os.Bundle;
import android.content.Intent;*/

//import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.backends.android.AndroidApplication;
//import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StageScreen implements Screen, InputProcessor {
	BomberMan game;
	private int width, height;
	//private Texture bgTexture;
	private SpriteBatch spriteBatch;
	public OrthographicCamera cam;
	public float ppuX;	// pixels per unit on the X axis
	public float ppuY;	// pixels per unit on the Y axis
	float CAMERA_WIDTH = 10F;
	float CAMERA_HEIGHT = 10F;
	//public boolean paused;
	public boolean soundOn;
	Texture texture;
	public  Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
	Sound  sound;
	 /** Called when the activity is first created. */
	
	TimerTask task;
	Timer timer;
	
	@Override
	public void show() {  
		startedPlaying = false;
		timeLeft = 0;
		//Log.e("IntroScreen","show");
		//CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		textureRegions = new HashMap<String, TextureRegion>();
		soundOn = game.getPrefs().getInteger("sound-on") == 1 ? true : false;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		//bgTexture = new Texture(Gdx.files.internal("images/bombermanmain.png"));
		spriteBatch = new SpriteBatch();
		task = null;
		timer= null;
		// paused = false;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		loadSounds();
		loadTextures();
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		//createTimer();
	}
	
	private void loadTextures(){
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth()/ 2, texture.getHeight() );
		
		TextureRegion tmpMain[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth(), tmpLeftRight[0][0].getRegionHeight()/2);
		TextureRegion tmpTop[][] =  tmpMain[0][0].split(tmpMain[0][0].getRegionWidth()/2, tmpMain[0][0].getRegionHeight());
		
		TextureRegion tmp[][] = tmpTop[0][0].split(tmpTop[0][0].getRegionWidth()/ 8, tmpTop[0][0].getRegionHeight() / 16);	
	
		textureRegions.put("Door", tmp[0][0]);
		textureRegions.put("HardBrick", tmp[0][1]);
		textureRegions.put("wallpass", tmp[0][2]);
		textureRegions.put("flames", tmp[0][3]);
		textureRegions.put("bombs", tmp[1][0]);
		textureRegions.put("speed", tmp[1][1]);
		textureRegions.put("detonator", tmp[1][2]);
		textureRegions.put("bombpass", tmp[1][3]);
		
		//textureRegions.put("navigation-arrows",   tmpLeftRight[0][1].split(tmpLeftRight[0][1].getRegionWidth(), tmpLeftRight[0][1].getRegionHeight()/2)[0][0]);
		TextureRegion tmpBot[][] = tmpMain[1][0].split(tmpMain[1][0].getRegionWidth()/2, tmpMain[1][0].getRegionHeight());
		
		TextureRegion tmpFont[][] =  tmpBot[0][0].split(tmpBot[0][0].getRegionWidth()/ 4, tmpBot[0][0].getRegionHeight() / 8);	
		textureRegions.put("1",  tmpFont[0][0]);
		textureRegions.put("2",  tmpFont[0][1]);
		textureRegions.put("3",  tmpFont[0][2]);
		textureRegions.put("4",  tmpFont[0][3]);
		textureRegions.put("5",  tmpFont[1][0]);
		textureRegions.put("6",  tmpFont[1][1]);
		textureRegions.put("7",  tmpFont[1][2]);
		textureRegions.put("8",  tmpFont[1][3]);
		textureRegions.put("9",  tmpFont[2][0]);
		textureRegions.put("0",  tmpFont[2][1]);
		
		textureRegions.put("t",  tmpFont[3][0]);
		textureRegions.put("i",  tmpFont[3][1]);
		textureRegions.put("m",  tmpFont[3][2]);
		textureRegions.put("e",  tmpFont[3][3]);
		
		textureRegions.put("l",  tmpFont[4][0]);
		textureRegions.put("f",  tmpFont[4][1]);
		
		textureRegions.put("cG",  tmpFont[7][2]);
		textureRegions.put("iG",  tmpFont[7][1]);
		textureRegions.put("sG",  tmpFont[7][0]);
		textureRegions.put("tG",  tmpFont[5][0]);
		textureRegions.put("aG",  tmpFont[5][1]);
		textureRegions.put("gG",  tmpFont[5][2]);
		textureRegions.put("eG",  tmpFont[5][3]);
		
		textureRegions.put("bG",  tmpFont[6][0]);
		textureRegions.put("oG",  tmpFont[6][1]);
		textureRegions.put("nG",  tmpFont[6][2]);
		textureRegions.put("uG",  tmpFont[6][3]);
		
		textureRegions.put("circle", tmpFont[6][3]);
		
	}

	
	public void showStage(){
		
				
		
		try{
			String st = game.getStage();
			int length =  st.length();
			for(int i=0;i<length;++i)
				spriteBatch.draw(textureRegions.get(st.substring(i,i+1)),(CAMERA_WIDTH*0.4F+3.8F+0.7F*i)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY);
			
		//spriteBatch.draw(textureRegions.get(game.getStage()),(CAMERA_WIDTH*0.4F+3.8F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY);
		
		spriteBatch.draw(textureRegions.get("sG"),CAMERA_WIDTH*0.4F*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("tG"),(CAMERA_WIDTH*0.4F+0.7F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("aG"),(CAMERA_WIDTH*0.4F+1.4F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("gG"),(CAMERA_WIDTH*0.4F+2.1F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 		
		spriteBatch.draw(textureRegions.get("eG"),(CAMERA_WIDTH*0.4F+2.8F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 
		}
		catch(Exception e){
			try{
				
				spriteBatch.draw(textureRegions.get("bG"),CAMERA_WIDTH*0.2F*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
				spriteBatch.draw(textureRegions.get("oG"),(CAMERA_WIDTH*0.2F+0.7F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
				spriteBatch.draw(textureRegions.get("nG"),(CAMERA_WIDTH*0.2F+1.4F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
				spriteBatch.draw(textureRegions.get("uG"),(CAMERA_WIDTH*0.2F+2.1F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 		
				spriteBatch.draw(textureRegions.get("sG"),(CAMERA_WIDTH*0.2F+2.8F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 
				
				spriteBatch.draw(textureRegions.get("sG"),(CAMERA_WIDTH*0.4F+3.8F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
				spriteBatch.draw(textureRegions.get("tG"),(CAMERA_WIDTH*0.4F+4.5F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
				spriteBatch.draw(textureRegions.get("aG"),(CAMERA_WIDTH*0.4F+5.2F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 
				spriteBatch.draw(textureRegions.get("gG"),(CAMERA_WIDTH*0.4F+5.9F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 		 		
				spriteBatch.draw(textureRegions.get("eG"),(CAMERA_WIDTH*0.4F+6.5F)*ppuX, CAMERA_HEIGHT / 2f*ppuY, 1F*ppuX, 1F*ppuY); 
				
			}catch(Exception e2){}
		}
		/*BitmapFont font = new BitmapFont();
		 CharSequence stage = "Stage "+Integer.toString(game.getStage());
		 font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		 font.setScale(3,3);
		 font.draw(spriteBatch, stage,CAMERA_WIDTH * 0.4f*ppuX, CAMERA_HEIGHT / 2f*ppuY);*/

		
	}
	private void loadSounds(){
		 sound = Gdx.audio.newSound(Gdx.files.internal("audio/level-start.mp3"));
	}
	public StageScreen(BomberMan game){
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
	
	boolean startedPlaying;
	private void playMusic(){
		//Log.e("playMusic","1");
		//if(!sound.isPlaying()){
			if(!startedPlaying && soundOn){
				//Log.e("!startedPlaying","1");
				//sound.setVolume(0.5F);
				//sound.play();
				sound.play(1F);
				startedPlaying = true;
			}
			//else
			//{
				//startedPlaying = false;
				//this.dispose();
				//game.setScreen(game.game);
			//}
		//}
	}
	
	float timeLeft;
	@Override
	public void render(float delta) {
		 timeLeft+=delta;
		 if(timeLeft>=4){
			 dispose();
			 game.setScreen(game.game);
		 }
		//Log.e("SS","1");
		Gdx.gl.glClearColor(0,0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//if(!paused ){
			spriteBatch.begin();
			showStage();
			spriteBatch.end();
			
		if(timeLeft>=0.6 && soundOn)
			playMusic();
		//}
		
		//Log.e("IntroScreen", width+" - "+height);
		
		//controller.update(delta);
		//renderer.render();
	}
	
	@Override
	public void pause() {
		sound.stop();
		this.startedPlaying = false;
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
		clear();
		Gdx.input.setInputProcessor(null);
		Gdx.input.setCatchBackKey(false);
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
		 dispose();
		
		game.setScreen(game.game);
		return true;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		
		
		return true;
	}
	private void clear(){
		//paused = true;
		
		try{
			sound.stop();
			sound.dispose();
			startedPlaying = false;
		this.spriteBatch.dispose();
		texture.dispose();
		textureRegions.clear();
		}
		catch(Exception e){
			
		}
		//clearTimer();
	}
	/*
	public int timeLeft;
	public void clearTimer(){
		timer.cancel();
		timer.purge();
		timer = null;
	}
	
	private void createTimer(){
		Log.e("createTimer","+");
		timeLeft = 0;
		task = new TimerTask(){
			public void run()
		      {

				++timeLeft;
		
					if(timeLeft >= 3){
						
						clear();
						game.setScreen(game.game);
					}
				}
				
		      
		};
		timer=new Timer();
		
		timer.schedule(task, 1,1000);
	}*/
}
