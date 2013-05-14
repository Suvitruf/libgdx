package suvitruf.libgdxtutorial.lesson5.screens;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import suvitruf.libgdxtutorial.lesson5.controller.*;
import suvitruf.libgdxtutorial.lesson5.model.*;
import suvitruf.libgdxtutorial.lesson5.view.*;



import java.net.Socket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.io.DataOutputStream;
import java.io.DataInputStream;
public class GameScreen implements Screen, InputProcessor {
	//private World 			world;
	//private WorldRenderer 	renderer;
	//private WorldController	controller;

	
	
	//private World world;
	public OrthographicCamera cam;
	//ShapeRenderer renderer = new ShapeRenderer();
	public World world;
	private SpriteBatch spriteBatch;
	Texture texture;
	public  Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
	
	public int width;
	public int height;
	

	
	  
	@Override
	public void show() {
		
		World.CAMERA_WIDTH =  World.CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		this.cam = new OrthographicCamera(World.CAMERA_WIDTH, World.CAMERA_HEIGHT);
		SetCamera(World.CAMERA_WIDTH / 2f, World.CAMERA_HEIGHT / 2f);
		spriteBatch = new SpriteBatch();
		loadTextures();
		world = new World(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch, textureRegions);
		
		//Log.e("1","1");
		//world = new World();
		//renderer = new WorldRenderer(/*world*/);
		//controller = new WorldController(world);
		//Gdx.input.setInputProcessor(this);
		
		Gdx.input.setInputProcessor(world);
		 
		
		
	}
	
	private void loadTextures() {
		/*texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 2);
		textureRegions.put("player", tmp[0][0]);
		textureRegions.put("brick1", tmp[0][1]);
		textureRegions.put("brick2", tmp[1][0]);
		textureRegions.put("brick3", tmp[1][1]);*/
		
		
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth()/ 2, texture.getHeight()/2 );
		TextureRegion left2[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth()/2, tmpLeftRight[0][0].getRegionHeight());
		TextureRegion left[][] = left2[0][0].split(left2[0][0].getRegionWidth()/4, left2[0][0].getRegionHeight()/8);
		
		textureRegions.put("player",  left[0][0]);
		textureRegions.put("brick1",  left[0][1]);
		textureRegions.put("brick2",  left[1][0]);
		textureRegions.put("brick3",  left[1][1]);
		
		
		textureRegions.put("navigation-arrows", tmpLeftRight[0][1]);
		//TextureRegion right[][] = tmpLeftRight[0][1].split(tmpLeftRight[0][1].getRegionWidth(), tmpLeftRight[0][1].getRegionHeight()/2);
		
		TextureRegion rightbot[][] = tmpLeftRight[1][1].split(tmpLeftRight[1][1].getRegionWidth()/2,tmpLeftRight[1][1].getRegionHeight()/2);
		
		textureRegions.put("khob",   rightbot[0][1]); 
		
		/*arrowLeft = new  com.badlogic.gdx.graphics.g2d.Sprite(rightbot[0][0].split(rightbot[0][0].getRegionWidth()/2,rightbot[0][0].getRegionHeight()/2)[0][0]);
		arrowLeft.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowLeft.rotate90(true);
		arrowLeft.rotate90(true); 
		arrowLeft.rotate90(true);
		arrowRight = new  com.badlogic.gdx.graphics.g2d.Sprite(rightbot[0][0].split(rightbot[0][0].getRegionWidth()/2,rightbot[0][0].getRegionHeight()/2)[0][0]);
		arrowRight.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowRight.rotate90(true);
	
		arrowDown = new  com.badlogic.gdx.graphics.g2d.Sprite( rightbot[0][0].split(rightbot[0][0].getRegionWidth()/2,rightbot[0][0].getRegionHeight()/2)[0][0]);
		arrowDown.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowDown.rotate90(true);
		arrowDown.rotate90(true);
		
		arrowUp = new  com.badlogic.gdx.graphics.g2d.Sprite(rightbot[0][0].split(rightbot[0][0].getRegionWidth()/2,rightbot[0][0].getRegionHeight()/2)[0][0]);
		arrowUp.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		
		TextureRegion pause[][] = rightbot[1][0].split(rightbot[1][0].getRegionWidth(),  rightbot[1][0].getRegionHeight()/2);
		
		
		textureRegions.put("home",   (pause[0][0].split(pause[0][0].getRegionWidth()/2,  pause[0][0].getRegionHeight()))[0][0]);
		textureRegions.put("resume",   (pause[0][0].split(pause[0][0].getRegionWidth()/2,  pause[0][0].getRegionHeight()))[0][1]);
		textureRegions.put("fon",   pause[1][0]);*/
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		//ChangeNavigation(x,y);
		return false;
	}

	
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		return false; 
	}
	
	public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);	
		this.cam.update();
	}
	
	@Override
	public void resize(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		world.setViewport(width, height, true);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {		
		Gdx.input.setInputProcessor(null);
	}


	@Override
	public boolean keyDown(int keycode) {
		
		return true;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(232F/255, 232F/255, 232F/255, 232F/255);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		world.update(delta);
		world.draw();
		
	
	}
	@Override
	public boolean keyUp(int keycode) {

		return true;
	}
	
	/*private void ChangeNavigation(int x, int y){
		controller.resetWay();
		if(height-y >  controller.player.getPosition().y * renderer.ppuY)
			controller.upPressed();
		
		if(height-y <  controller.player.getPosition().y * srenderer.ppuY)
			controller.downPressed();
		
		if ( x< controller.player.getPosition().x * renderer.ppuX) 
			controller.leftPressed();
			
		if (x> (controller.player.getPosition().x +Player.SIZE)* renderer.ppuX)
			controller.rightPressed();
			
	}*/
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {

		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		//ChangeNavigation(x,y);
		return true;
	} 
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		//controller.resetWay();
		
		return true;
	}
	
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
