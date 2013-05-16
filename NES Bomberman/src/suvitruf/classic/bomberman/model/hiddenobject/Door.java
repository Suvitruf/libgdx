package suvitruf.classic.bomberman.model.hiddenobject;


import com.badlogic.gdx.math.Vector2;

public class Door extends HiddenObject {
	//public static String textureName = "images/HardBrick.png";
	public static String Name =  "Door";
	//public static Texture texture  = new Texture(Gdx.files.internal("images/HardBrick.png"));
	
	
	public Door (Vector2 pos){ 
		super(pos);
		state = State.NONE;
		name = "Door";
	}
	
	
}
