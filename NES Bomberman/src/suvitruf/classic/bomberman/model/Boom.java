package suvitruf.classic.bomberman.model;

import java.util.Timer;
import java.util.TimerTask;


import com.badlogic.gdx.utils.Array;

public class Boom {
	public static String textureName = "images/BoomSprite.png";
	
	public static final int FRAME_COLS =4; 
	public static final int FRAME_ROWS = 8;
	public static final float SPEED = 1f/7;
	public static String Name =  "Boom";
	protected  String name ;
	Array<BoomPart> boomParts = new Array<BoomPart>();
	TimerTask task;
	Timer timer;
	public static enum State {NONE,  BOOM}
	public State state;
	//World world;
	public Boom(Array<BoomPart> boomParts){ 
		
		this.boomParts = boomParts;
		name =  "Boom";
		state = State.NONE;
		createTimer();
	}
	
	private void createTimer(){
		//timeLeft = 0;
		task = new TimerTask(){
			public void run()
		      {
				state = State.BOOM;
				timer.cancel();
				timer.purge();
				timer = null;
		      }
		};
		timer=new Timer();
		
		timer.schedule(task,500);
	}
	public  String getName(){
		return name;
	}
	public State getState() {
		return state;
	}
	public Array<BoomPart> getParts(){
		return boomParts;
	}
}
