package suvitruf.classic.bomberman.model.hiddenobject;


import com.badlogic.gdx.math.Vector2;

public class BombPassPower extends HiddenObject {
	public static String Name =  "bombpass";
	 public  BombPassPower(Vector2 pos){ 
			super(pos);
			state = State.NONE;
			name = "bombpass";
	 }
}
