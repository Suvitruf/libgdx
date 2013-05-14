package suvitruf.libgdxtutorial.lesson5.controller;

import suvitruf.libgdxtutorial.lesson5.model.Player;
import suvitruf.libgdxtutorial.lesson5.model.World;


import android.util.Log;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class WalkingControl extends Actor{

	//размер джоя
	public static  float SIZE = 4f;
	//размер движущейся части (khob)
	public static  float CSIZE = 3f; 
	
	public static  float CIRCLERADIUS = 1.5f;
	public static float  CONTRLRADIUS = 3F;
	//public static float Coefficient = 1F;

	//угол для определения направления
	float angle;
	//public static int Opacity = 1;
	 World world;
	
	//координаты отклонения khob
	protected Vector2 offsetPosition = new Vector2(); 
	
	protected Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	
	public WalkingControl(Vector2 pos, World world){ 
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.world = world;
		
		getOffsetPosition().x = 0;
		getOffsetPosition().y = 0;
		
		setHeight(SIZE*world.ppuY);
		setWidth(SIZE*world.ppuX);
		setX(position.x*world.ppuX);
		setY(position.y*world.ppuY);
	
		addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	                return true;
	        }
	        
	        //при перетаскивании
	        public void touchDragged(InputEvent event, float x, float y, int pointer){
	        	
	        	withControl(x,y);
	        }
	        
	        //убираем палец с экрана
	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        	
				getOffsetPosition().x = 0;
				getOffsetPosition().y = 0;
	        }
	        
		});
	}
	

	 
	//отрисовка
	@Override
	public void draw(SpriteBatch batch, float parentAlfa) {
		
		
		
		batch.draw(world.textureRegions.get("navigation-arrows"), getX(), getY(),getWidth(), getHeight());
		batch.draw(world.textureRegions.get("khob"), 
				(float)(position.x+WalkingControl.SIZE/2-WalkingControl.CSIZE/2+getOffsetPosition().x)*world.ppuX, 
				(float)(position.y+WalkingControl.SIZE/2-WalkingControl.CSIZE/2+getOffsetPosition().y)*world.ppuY, 
				WalkingControl.CSIZE * world.ppuX, WalkingControl.CSIZE * world.ppuY);
		
	}

	
	public Actor hit(float x, float y, boolean touchable) {
		//Процедура проверки. Если точка в прямоугольнике актёра, возвращаем актёра.
		return x > 0 && x < getWidth() && y> 0 && y < getHeight()?this:null;
	}
	
	
	public Vector2 getPosition() {
		return position;
	}
	public Vector2 getOffsetPosition() {
		return offsetPosition;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	
	
	
	public void withControl(float x, float y){

		//точка касания относительно центра джойстика
		float calcX = x/world.ppuX -SIZE/2;
		float calcY = y/world.ppuY -SIZE/2;
		
		//определяем лежит ли точка касания в окружности джойстика
		if(((calcX*calcX + calcY* calcY)<=WalkingControl.CONTRLRADIUS*WalkingControl.CONTRLRADIUS)
				){	
			
			world.resetSelected();
			
			//пределяем угол касания
			double angle = Math.atan(calcY/calcX)*180/Math.PI;
			
			//угол будет в диапозоне [-90;90]. Удобнее работать, если он в диапозоне [0;360]
			//поэтому пошаманим немного
			if(angle>0 &&calcY<0)
					angle+=180;
			if(angle <0)
				if(calcX<0)
					angle=180+angle;
				else
					angle+=360;
			
			//в зависимости от угла, указыванием направление, куда двигать игрока
			if(angle>40 && angle<140)
				((Player)world.selectedActor).upPressed();
				
			if(angle>220 && angle<320)
				((Player)world.selectedActor).downPressed();
			
			
			if(angle>130 && angle<230)
				((Player)world.selectedActor).leftPressed();
			
			if(angle<50 || angle>310)
				((Player)world.selectedActor).rightPressed();
				
			
			//двигаем игрока
			((Player)world.selectedActor).processInput();

			
			angle = (float)(angle*Math.PI/180);
			getOffsetPosition().x = (float)((calcX*calcX + calcY* calcY>1F)? Math.cos(angle)*0.75F: calcX);
			getOffsetPosition().y = (float)((calcX*calcX + calcY* calcY>1F)? Math.sin(angle)*0.75F: calcY);
			
		}	
		else{
			
			world.resetSelected();
			getOffsetPosition().x = 0;
			getOffsetPosition().y = 0;
		}

	}
}
