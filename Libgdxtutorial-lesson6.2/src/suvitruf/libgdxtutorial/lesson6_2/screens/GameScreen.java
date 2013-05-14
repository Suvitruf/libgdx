package suvitruf.libgdxtutorial.lesson6_2.screens;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import suvitruf.libgdxtutorial.lesson6_2.controller.*;
import suvitruf.libgdxtutorial.lesson6_2.model.*;
import suvitruf.libgdxtutorial.lesson6_2.view.*;





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

	
	private WorldRenderer 	renderer;
	//private World world;
	//public OrthographicCamera cam;
	//ShapeRenderer renderer = new ShapeRenderer();
	public MyWorld world;
	//private SpriteBatch spriteBatch;
	Texture texture;
	//public  Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
	
	public int width;
	public int height;
	

	private WorldController	controller;
	  
	@Override
	public void show() {
		
		MyWorld.CAMERA_WIDTH =  MyWorld.CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		//this.cam = new OrthographicCamera(MyWorld.CAMERA_WIDTH, MyWorld.CAMERA_HEIGHT);
		//SetCamera(MyWorld.CAMERA_WIDTH / 2f, MyWorld.CAMERA_HEIGHT / 2f);
		//spriteBatch = new SpriteBatch();
		loadTextures();
		world = new MyWorld();
	
		renderer = new WorldRenderer(world, MyWorld.CAMERA_WIDTH, MyWorld.CAMERA_HEIGHT,true);
		controller = new WorldController(world/*, renderer*/);
		Gdx.input.setInputProcessor(this);
		 
		
		
	}
	
	private void loadTextures() {
		
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
	
	/*public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);	
		this.cam.update();
	}*/
	
	@Override
	public void resize(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		
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
		renderer.dispose();
		controller.dispose();
		Gdx.input.setInputProcessor(null);
	}


	@Override
	public boolean keyDown(int keycode) {
		
		return true;
	}
 
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//world.update(delta);
		//world.draw();
		
		controller.update(delta);
		renderer.render(delta);
	}
	@Override
	public boolean keyUp(int keycode) {
		
		return true;
	}
	
	private void ChangeNavigation(int x, int y){
		controller.resetWay();
		if(height-y >  world.getPlayer().getPosition().y * renderer.ppuY)
			controller.upPressed();
		/*
		if(height-y <  controller.player.getPosition().y * srenderer.ppuY)
			controller.downPressed();*/
		
		//Log.e("pos",Float.toString(world.getPlayer().getPosition().x)+";"+Float.toString(world.getPlayer().getPosition().y));
		if ( x< world.getPlayer().getPosition().x * renderer.ppuX) 
			controller.leftPressed();
			
		if (x> (world.getPlayer().getPosition().x )* renderer.ppuX)
			controller.rightPressed();
			
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {

		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		ChangeNavigation(x,y);
		return true;
	} 
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
	
		controller.resetWay();
		return true;
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		ChangeNavigation(x,y);
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
