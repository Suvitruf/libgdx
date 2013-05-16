package suvitruf.classic.bomberman.model.hiddenobject;





import com.badlogic.gdx.math.Vector2;

public class BombsPower extends HiddenObject{
	public static String Name =  "bombs";
	 public  BombsPower  (Vector2 pos){ 
			super(pos);
			state = State.NONE;
			name = "bombs";
	 }
}
