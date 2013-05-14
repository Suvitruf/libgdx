package suvitruf.libgdxtutorial.lesson6_2.controller;
import java.util.HashMap;
import java.util.Map;




import suvitruf.libgdxtutorial.lesson6_2.model.*;
import suvitruf.libgdxtutorial.lesson6_2.view.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.Contact;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import com.badlogic.gdx.utils.Array;

//import android.util.Log;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*import suvitruf.nes.runner.model.Bomb;
import suvitruf.nes.runner.model.BoomPart;
import suvitruf.nes.runner.model.BrickBase;
import suvitruf.nes.runner.model.Bomberman;
import suvitruf.nes.runner.model.NpcBase;
import suvitruf.nes.runner.model.World;
import suvitruf.nes.runner.model.Bomberman.State;*/
//import android.graphics.Bitmap;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
public class WorldController {   
	
	
	private MyWorld 	world;
	
	//направление движения
	  enum Keys {
	    LEFT, RIGHT, UP, DOWN
	  }
	 //куда движемся...игрок может двигаться одновременно по 2-м направлениям
	  static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();

	  //первоначально стоим
	  static {
	    keys.put(Keys.LEFT, false);
	    keys.put(Keys.RIGHT, false);
	    keys.put(Keys.UP, false);
	    keys.put(Keys.DOWN, false);
	  };
	  
	public WorldController(MyWorld world/*, WorldRenderer renderer*/) {

		this.world = world;
		
		
	}
	//флаг устанавливаем, что движемся влево
	  public void leftPressed() {
	
	    keys.get(keys.put(Keys.LEFT, true));
	  }

	  //флаг устанавливаем, что движемся вправо	
	  public void rightPressed() {
	    keys.get(keys.put(Keys.RIGHT, true));
	  }
	  
	  //освобождаем флаги
	  public void leftReleased() {
	    keys.get(keys.put(Keys.LEFT, false));
	  }	
	  public void rightReleased() {
	    keys.get(keys.put(Keys.RIGHT, false));
	  }
	  //флаг устанавливаем, что движемся вверх	
	  public void upPressed() {
	    keys.get(keys.put(Keys.UP, true));
	  }
	  
	  public void upReleased() {
		    keys.get(keys.put(Keys.UP, false));
		  }
	  
	  public void resetWay(){
		    rightReleased();
		    leftReleased();
		    //downReleased();
		    upReleased();
		    world.getPlayer().resetVelocity();
		  }
	  /*
	 public void resetImpulse(){
		   world.getPlayer().getBody().applyLinearImpulse(0, 0, world.getPlayer().getPosition().x, world.getPlayer().getPosition().y);
			
	 }*/
	public void dispose(){

		//world.dispose();
	}
	
	
	
	
	/**
	 *  Апдейтим логику 
	 * @param delta
	 * время, прошешее с прошлой прорисовки.
	 */
	public void update(float delta) {
		Array<MovingPlatform> platforms = world.getPlatforms();
		for(int i = 0; i < platforms.size; i++) {
			MovingPlatform platform = platforms.get(i);
			platform.update(Math.max(1/60.0f, delta));
		}
		grounded = isPlayerGrounded(Gdx.graphics.getDeltaTime());
		processInput();
		
	
		world.getPlayer().update(delta);
	}
	
	/**
	 * Опреелить, нахоится ли игрок на земле/платформе.
	 * @param deltaTime 
	 * время, прошешее с прошлой прорисовки.
	 * @return
	 */
	private boolean isPlayerGrounded(float deltaTime) {				
		world.groundedPlatform = null;
		List<Contact> contactList = world.getWorld().getContactList();
		for(int i = 0; i < contactList.size(); i++) {
			Contact contact = contactList.get(i);
			if(contact.isTouching() && (contact.getFixtureA() == world.getPlayer().playerSensorFixture ||
			   contact.getFixtureB() == world.getPlayer().playerSensorFixture)) {				
 
				Vector2 pos = world.getPlayer().getPosition();
				WorldManifold manifold = contact.getWorldManifold();
				boolean below = true;
				for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
					below &= (manifold.getPoints()[j].y < pos.y - 0.4f);
				}
 
				if(below) {
					if(contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("p")) {
						world.groundedPlatform = (MovingPlatform)contact.getFixtureA().getBody().getUserData();	
						if (!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) 	
							contact.setFriction(200F);
						else
							contact.setFriction(0F); 
					}
 
					if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("p")) {
						world.groundedPlatform = (MovingPlatform)contact.getFixtureB().getBody().getUserData();
						if (!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) 	
							contact.setFriction(200F);
						else
							contact.setFriction(0F); 
					}											
					return true;			
				}
 
				return false;
			}
		}
		return false;
	}
	
	public static boolean grounded ;
	 //в зависимости от выбранного направления движения выставляем новое направление движения для персонажа
	/**
	 * В зависимости от касаний по тачу задать направление движения игроку.
	 */
	private void processInput() {
		
		Player player = world.getPlayer();
	    if (keys.get(Keys.LEFT)) 
	    	player.getVelocity().x =- Player.SPEED;
	    
	    
	    
	   

	    if (keys.get(Keys.RIGHT))
	     	player.getVelocity().x = Player.SPEED;
	    	
	    
		
	    if(!grounded)
			player.setFriction(0F);	
		else{
			if (!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) 
				player.setFriction(200F);
			else
				player.setFriction(0.2F);
		}
	    
	    
		
	    if (keys.get(Keys.UP)) 	    	
			if(grounded) {
				 player.jump();
					this.upReleased();
				 //player.getBody().setGravityScale(-3);
				 //world.getWorld().setGravity(new Vector2(0, 20));
			}
	    
	    if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
				(!keys.get(Keys.LEFT) && (!keys.get(Keys.RIGHT)))) {
		
			player.getVelocity().x = 0;			
		}
	    
	 }

}
