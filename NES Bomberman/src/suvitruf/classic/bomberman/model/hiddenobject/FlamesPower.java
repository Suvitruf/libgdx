package suvitruf.classic.bomberman.model.hiddenobject;


import com.badlogic.gdx.math.Vector2;


public class FlamesPower extends HiddenObject {
	
	public static String Name =  "flames";
	 public FlamesPower (Vector2 pos){ 
			super(pos);
			state = State.NONE;
			name = "flames";
	 }
}
