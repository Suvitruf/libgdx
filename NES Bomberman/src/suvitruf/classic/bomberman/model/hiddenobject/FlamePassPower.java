package suvitruf.classic.bomberman.model.hiddenobject;



import com.badlogic.gdx.math.Vector2;

public class FlamePassPower extends HiddenObject {
	public static String Name =  "flamepass";
	 public  FlamePassPower  (Vector2 pos){ 
			super(pos);
			state = State.NONE;
			name = "flamepass";
	 }
}
