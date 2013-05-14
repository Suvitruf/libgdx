package suvitruf.libgdxtutorial.lesson6;
import suvitruf.libgdxtutorial.lesson6.screens.*;

import android.content.Intent;

import com.badlogic.gdx.Game;
public class MyGame extends   Game  {
	//public GameScreen game;
	public GameScreen game;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		game = new GameScreen();
		setScreen(game);
	}
	MainActivity activity;
	public void goHome(){
		
	}
	@Override
	public void resize(int width, int height) {
		
	}
/*
	@Override
	public void render() {

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
	
	}
	*/
	
}
