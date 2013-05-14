package suvitruf.libgdxtutorial.lesson8.screens;
import suvitruf.libgdxtutorial.lesson8.MyGame;
import suvitruf.libgdxtutorial.lesson8.controller.*;
import suvitruf.libgdxtutorial.lesson8.model.*;
import suvitruf.libgdxtutorial.lesson8.view.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;

import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen, InputProcessor {
	private World 			world;
	private WorldRenderer 	renderer;
	private WorldController	controller;

	private int width, height;
	MyGame game;
	public GameScreen(MyGame game){
		this.game = game;
	}
	@Override
	public void show() {

		world = new World();
		renderer = new WorldRenderer(world);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		ChangeNavigation(x,y);
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
	
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
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
		Gdx.input.setInputProcessor(null);
		Gdx.input.setCatchBackKey(false);
		renderer.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		
		return true;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		renderer.render();
	}
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK ){
			
			this.dispose();
			game.setScreen(game.intro);
			
		}
		return true;
	}
	
	private void ChangeNavigation(int x, int y){
		controller.resetWay();
		if(height-y >  controller.player.getPosition().y * renderer.ppuY)
			controller.upPressed();
		
		if(height-y <  controller.player.getPosition().y * renderer.ppuY)
			controller.downPressed();
		
		if ( x< controller.player.getPosition().x * renderer.ppuX) 
			controller.leftPressed();
			
		if (x> (controller.player.getPosition().x +Player.SIZE)* renderer.ppuX)
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
	public boolean scrolled(int amount) {
		return false;
	}
}
