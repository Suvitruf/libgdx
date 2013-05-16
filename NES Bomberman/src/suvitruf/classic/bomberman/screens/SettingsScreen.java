package suvitruf.classic.bomberman.screens;

import java.util.HashMap;
import java.util.Map;

import suvitruf.classic.bomberman.BomberMan;
//import suvitruf.classic.bomberman.controller.WalkingControl;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class SettingsScreen implements Screen, InputProcessor {
	BomberMan game;
	private int width, height;
	//private Texture bgTexture;
	private SpriteBatch spriteBatch;
	public OrthographicCamera cam;
	public float ppuX;	// pixels per unit on the X axis
	public float ppuY;	// pixels per unit on the Y axis
	float CAMERA_WIDTH = 16F;
	float CAMERA_HEIGHT = 8F;
	ShapeRenderer bgRenderer;// = new ShapeRenderer();
	Texture texture;
	public  Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
	
	Music  sound;  
	 /** Called when the activity is first created. */
	@Override
	public void show() {
		textureRegions = new HashMap<String, TextureRegion>();
		bgRenderer = new ShapeRenderer();
		//Log.e("IntroScreen","show");
		//CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		
		//bgTexture = new Texture(Gdx.files.internal("images/bombermanmain.png"));
		spriteBatch = new SpriteBatch();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		//loadSounds();
		loadTextures();
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		try{
			selected  = game.getPrefs().getInteger("controller-size");
			selectedSoundOn = (game.getPrefs().getInteger("sound-on") ==1) ? true : false;
			controllertype = game.getPrefs().getInteger("controller-type");
			
			opacity = game.getPrefs().getInteger("opacity");
			//Log.e("sound", Integer.toString( game.getPrefs().getInteger("sound-on")));
		}
		catch(Exception e){
			//Log.e("sound", Integer.toString( game.getPrefs().getInteger("sound-on")));
		}
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
		
		
		//TextureRegion tmpBomber[][] =  tmpTop[0][1].split(tmpTop[0][1].getRegionWidth(),tmpTop[0][1].getRegionHeight()/2);
		//TextureRegion fonts[][] = tmpBomber[1][0].split(tmpBomber[1][0].getRegionWidth()/4,tmpBomber[1][0].getRegionHeight()/8);
		
		//textureRegions.put("navigation-arrows",   tmpLeftRight[0][1].split(tmpLeftRight[0][1].getRegionWidth(), tmpLeftRight[0][1].getRegionHeight()/2)[0][0]);
		TextureRegion tmpBot[][] = tmpMain[1][0].split(tmpMain[1][0].getRegionWidth()/2, tmpMain[1][0].getRegionHeight());
		
		TextureRegion fonts[][] = tmpBot[0][1].split(tmpBot[0][1].getRegionWidth()/4,tmpBot[0][1].getRegionHeight()/8);
		
		textureRegions.put("wG",  fonts[0][0]);
		textureRegions.put("jG",  fonts[1][0]);
		textureRegions.put("yG",  fonts[2][0]);
		textureRegions.put("pG",  fonts[3][0]);
		
		textureRegions.put("+G",  fonts[4][0]);
		textureRegions.put("-G",  fonts[5][0]);
		
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
		textureRegions.put("lG",  tmpFont[4][2]);
		textureRegions.put("zG",  tmpFont[4][3]);
		
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
		textureRegions.put("rG",  tmpFont[7][3]);
		
		textureRegions.put("fG",  tmpFont[2][2]);
		textureRegions.put("dG",  tmpFont[2][3]);
		textureRegions.put("circle", tmpFont[6][3]);
		
	}
	public void showMenu(){
		
	}
	float offset = 0;
	float wSize = 0.5F;
	public void showBG(){
		
		offset = (CAMERA_WIDTH-15*wSize)/2;
		spriteBatch.draw(textureRegions.get("cG"),offset*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("oG"),(offset+wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("nG"),(offset+2*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("tG"),(offset+3*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("rG"),(offset+4*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("oG"),(offset+5*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("lG"),(offset+6*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("lG"),(offset+7*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 
		spriteBatch.draw(textureRegions.get("eG"),(offset+8*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("rG"),(offset+9*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 
		
		spriteBatch.draw(textureRegions.get("sG"),(offset+11*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("iG"),(offset+12*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("zG"),(offset+13*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("eG"),(offset+14*wSize)*ppuX, 7F*ppuY, wSize*ppuX, wSize*ppuY); 	

		
		
		
		spriteBatch.draw(textureRegions.get("oG"),offset*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("pG"),(offset+wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("aG"),(offset+2*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("cG"),(offset+3*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("iG"),(offset+4*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("tG"),(offset+5*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("yG"),(offset+6*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		
		spriteBatch.draw(textureRegions.get("-G"),(offset+9*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		if(opacity == 10){
			spriteBatch.draw(textureRegions.get("1"),(offset+10*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
			spriteBatch.draw(textureRegions.get("0"),(offset+11*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		}
		else
			spriteBatch.draw(textureRegions.get(Integer.toString(opacity)),(offset+11*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		
		spriteBatch.draw(textureRegions.get("0"),(offset+12*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("+G"),(offset+13*wSize)*ppuX, 5F*ppuY, wSize*ppuX, wSize*ppuY); 	
		/*spriteBatch.draw(textureRegions.get("1"),(offset+17*wSize)*ppuX, 6F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("2"),(offset+19*wSize)*ppuX, 6F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("3"),(offset+21*wSize)*ppuX, 6F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("4"),(offset+23*wSize)*ppuX, 6F*ppuY, wSize*ppuX, wSize*ppuY); 	
*/

		spriteBatch.draw(textureRegions.get("1"),(offset+wSize)*ppuX, 6F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("2"),(offset+3*wSize)*ppuX, 6F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("3"),(offset+5*wSize)*ppuX, 6F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("4"),(offset+7*wSize)*ppuX, 6F*ppuY, wSize*ppuX, wSize*ppuY); 	
		/*spriteBatch.draw(bgTexture, 
				0.125F*CAMERA_WIDTH*ppuX, 0.125F*CAMERA_HEIGHT*ppuY, CAMERA_WIDTH*0.75F*ppuX, CAMERA_HEIGHT*0.75F*ppuY);*/
		
		
		
		
		spriteBatch.draw(textureRegions.get("sG"),offset*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("oG"),(offset+wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("uG"),(offset+2*wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("nG"),(offset+3*wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("dG"),(offset+4*wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY); 	
		
		spriteBatch.draw(textureRegions.get("oG"),(offset+6*wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("nG"),(offset+7*wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY); 
		
		spriteBatch.draw(textureRegions.get("oG"),(offset+9*wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("fG"),(offset+10*wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY);
		spriteBatch.draw(textureRegions.get("fG"),(offset+11*wSize)*ppuX, 4F*ppuY, wSize*ppuX, wSize*ppuY);
		
		
		
		
		
		spriteBatch.draw(textureRegions.get("cG"),offset*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("oG"),(offset+wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("nG"),(offset+2*wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("tG"),(offset+3*wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("rG"),(offset+4*wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("oG"),(offset+5*wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("lG"),(offset+6*wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("lG"),(offset+7*wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 
		spriteBatch.draw(textureRegions.get("eG"),(offset+8*wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("rG"),(offset+9*wSize)*ppuX, 3F*ppuY, wSize*ppuX, wSize*ppuY); 
		
		
		
		spriteBatch.draw(textureRegions.get("jG"),(offset+wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("oG"),(offset+2*wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("yG"),(offset+3*wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 	
		
		
		spriteBatch.draw(textureRegions.get("aG"),(offset+5*wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("rG"),(offset+6*wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("rG"),(offset+7*wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 
		spriteBatch.draw(textureRegions.get("oG"),(offset+8*wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("wG"),(offset+9*wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 
		spriteBatch.draw(textureRegions.get("sG"),(offset+10*wSize)*ppuX, 2F*ppuY, wSize*ppuX, wSize*ppuY); 	

		
		
		
		
		spriteBatch.draw(textureRegions.get("sG"),(offset+wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("eG"),(offset+2*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("tG"),(offset+3*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 	
		
		
		spriteBatch.draw(textureRegions.get("pG"),(offset+5*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 		
		spriteBatch.draw(textureRegions.get("oG"),(offset+6*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("sG"),(offset+7*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 
		spriteBatch.draw(textureRegions.get("iG"),(offset+8*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 	
		spriteBatch.draw(textureRegions.get("tG"),(offset+9*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 
		spriteBatch.draw(textureRegions.get("iG"),(offset+10*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 
		spriteBatch.draw(textureRegions.get("oG"),(offset+11*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 
		spriteBatch.draw(textureRegions.get("nG"),(offset+12*wSize)*ppuX, 0.2F*ppuY, wSize*ppuX, wSize*ppuY); 
	}
	
	private int selected = 1;
	private boolean selectedSoundOn;
	private int controllertype= 1;
	
	private int opacity= 1;
	
	private void drawFrame(){
		//
		bgRenderer.setProjectionMatrix(cam.combined);
		bgRenderer.begin(ShapeType.Rectangle);
		bgRenderer.setColor(new Color(1, 1, 1, 1));
		//bgRenderer.rect(1.4F+ 1F*(selected-1), 2F, wSize, wSize);
		bgRenderer.rect(0-CAMERA_WIDTH / 2f+offset+wSize+1F*(selected-1), 0-CAMERA_HEIGHT / 2f+6f, wSize, wSize);
		
		
		bgRenderer.rect(0-CAMERA_WIDTH / 2f+offset+6*wSize+(selectedSoundOn ? 0 : 1.5F), 0-CAMERA_HEIGHT / 2f+4f, selectedSoundOn ? wSize*2 :wSize*3, wSize);
		
		
		
		bgRenderer.rect(0-CAMERA_WIDTH / 2f+offset+wSize+wSize*(controllertype==1 ? 0: 4), 0-CAMERA_HEIGHT / 2f+2f, controllertype==1 ? wSize*3 :wSize*6, wSize);
		
		
		bgRenderer.end();
	}
	
	/*private void loadSounds(){
		 sound = Gdx.audio.newMusic(Gdx.files.internal("audio/intro.mid"));
	}*/
	public SettingsScreen(BomberMan game){
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
	/*private void playMusic(){
		if(!sound.isPlaying()){
			sound.play();
			sound.setVolume(0.8F);
			sound.setLooping(true);
		}
	}*/
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		drawFrame();
		spriteBatch.begin();
		showBG();
		showMenu();
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
		float calcX = x/ppuX;
		float calcY = (height-y)/ppuY;
		//Log.e("x",Float.toString(calcX));
		if(calcY >=6F && calcY <=7F){
			if(calcX>= offset+wSize && calcX< offset+2*wSize)
				selected = 1;
			if(calcX>= offset+2*wSize && calcX< offset+4*wSize)
				selected =2;
			if(calcX>= offset+4*wSize && calcX< offset+6*wSize)
				selected = 3;
			if(calcX>= offset+6*wSize && calcX< offset+8*wSize)
				selected = 4;
			
			game.getPrefs().putInteger("controller-size",	selected );
			game.getPrefs().flush();
		}
		if(calcY >=4F && calcY <5F){
			if(calcX>= offset+6*wSize && calcX< offset+8*wSize)
				this.selectedSoundOn = true;
			if(calcX>= offset+9*wSize && calcX< offset+12*wSize)
				this.selectedSoundOn = false;
			game.getPrefs().putInteger("sound-on",	selectedSoundOn ?1:0 );
			game.getPrefs().flush();
		
		}
		
		if(calcY >=5F && calcY <=6F){
			if(calcX>= offset+9*wSize && calcX< offset+11*wSize)
				if(opacity >1)
					this.opacity--;
			if(calcX>= offset+13*wSize && calcX< offset+15*wSize)
				if(opacity <10)
				this.opacity++;
			game.getPrefs().putInteger("opacity",	opacity);
			game.getPrefs().flush();
		
		}
		
		if(calcY >=2F && calcY <=3F){
			if(calcX>= offset+wSize && calcX< offset+wSize+3*wSize)
				controllertype = 1;
			if(calcX>= offset+wSize+5*wSize && calcX< offset+11*wSize)
				controllertype = 0;
			
			game.getPrefs().putInteger("controller-type",controllertype );
			game.getPrefs().flush();
		}
		if(calcY >=0 && calcY <=1.2F){
			if(calcX>= offset+wSize && calcX< offset+wSize+11*wSize){
				this.dispose();
				game.setScreen(game.customArrowsScreen);
			}
		}
		//
		//Log.e("intro","touchDown");
	
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
		return true;
	}
}
