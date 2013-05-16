package suvitruf.classic.bomberman.model;

//import com.badlogic.gdx.graphics.g2d.TextureRegion;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BrickBase {

	//3.0d is a double precision floating-point number constant. 
		//3.0, or alternatively 3.0f is a single precision floating-point number constant.
		public static  float SIZE = 1f;
		//public static String Name =  "BrickBase";
		protected  String name ;
		public Vector2 position = new Vector2();
		protected Rectangle bounds = new Rectangle();
		
		public static enum State {NONE, DESTROYING, DESTROYED}
		protected State state;
		public float  animationState = 0;
		
		public BrickBase(Vector2 pos){ //constructor takes parameter vector2 which is a position (x,y)
			this.position = pos;
			this.bounds.width = SIZE;
			this.bounds.height = SIZE;
			state = State.NONE;
			name =  "BrickBase";
		}
		
		public Vector2 getPosition() {
			return position;
		}
		public void setState(State state){
			/*if(!((state == State.DESTROYING && (this.state == State.DESTROYING || this.state == State.DESTROYED)) ||
					(state == State.DESTROYED &&  this.state == State.DESTROYED))
					)*/
				this.state = state;
			
		}
		public State getState(){
			
			return state;
		}
		/*public TextureRegion getCurrentFrame()
		{
			return null;
		}*/
		public float getAnimationState(){
			return animationState;
		}
		public  String getName(){
			return name;
		}
		public Rectangle getBounds() {
			return bounds;
		}

}