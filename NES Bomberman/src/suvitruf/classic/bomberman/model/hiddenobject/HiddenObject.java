package suvitruf.classic.bomberman.model.hiddenobject;



import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class HiddenObject {
	public enum State {NONE, TAKEN, DESTROYED };
	public State state;
	public static  float SIZE = 1f;
	boolean isHidden;
	protected  String name ;
	protected Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	public HiddenObject  (Vector2 pos){ 
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		//isHidden = true;
		name = "HiddenObject";
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public String getName(){
		return name;
	}
	/*public void setHidden(boolean hidden){
		this.isHidden = hidden;
	}
	
	public boolean isHidden(){
		return this.isHidden ;
	}*/
	
	public Rectangle getBounds() {
		return bounds;
		
	}
}
