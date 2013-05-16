package suvitruf.classic.bomberman.model.hiddenobject;



import com.badlogic.gdx.math.Vector2;

public class DetonatorPower extends HiddenObject {
	public static String Name =  "detonator";
	 public  DetonatorPower  (Vector2 pos){ 
			super(pos);
			state = State.NONE;
			name = "detonator";
	 }
}
