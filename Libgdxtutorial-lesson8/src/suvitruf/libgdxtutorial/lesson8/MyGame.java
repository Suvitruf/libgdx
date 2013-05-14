package suvitruf.libgdxtutorial.lesson8;
import suvitruf.libgdxtutorial.lesson8.screens.*;

import com.badlogic.gdx.Game;
public class MyGame extends   Game  {
	//public GameScreen game;
	public GameScreen game;
	public IntroScreen intro;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		game = new GameScreen(this);
		intro = new IntroScreen(this);
		setScreen(intro);
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
