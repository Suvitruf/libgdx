package suvitruf.classic.bomberman.model;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class HardBrick extends BrickBase {
	public static String textureName = "images/HardBrick.png";
	

	public static String Name =  "HardBrick";
	//public static Texture texture  = new Texture(Gdx.files.internal("images/HardBrick.png"));
	
	public HardBrick(Vector2 pos){ 
		super(pos);
		name = "HardBrick";
	}
	/*
	@Override
	public  String getName(){
		return name;
	}*/
}
