package suvitruf.libgdxtutorial.lesson6_1.model;
import java.util.Map;

import suvitruf.libgdxtutorial.lesson6_1.controller.MyContactListener;
import suvitruf.libgdxtutorial.lesson6_1.model.*;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import android.util.Log;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class MyWorld{

	
	//public float ppuX;	
	//public float ppuY;

	World world;
	Player player;
	Array<MovingPlatform> platforms = new Array<MovingPlatform>();
	//Array<Body> blocks = new Array<Body>();
	public MovingPlatform groundedPlatform = null;
	public static float CAMERA_WIDTH = 12f;
	public static  float CAMERA_HEIGHT = 8f;
	
	public int width;
	public int height;
	//обновление положения объектов
	public void update(float delta){
		
	}

	public Player getPlayer(){
		return player;
	}
	public World getWorld(){
		return world;
	}
	public Array<MovingPlatform> getPlatforms(){
		return platforms;
	}
	public MyWorld(){
		width = 30;
		height = 8;
		world = new World(new Vector2(0, -20), true);	
		world.setContactListener(new MyContactListener(world));
		//world.setAutoClearForces(true);
		createWorld();
	}
	
	public void setPP(float x, float y){
		//ppuX = x;
		//ppuY = y;
	}
	
	private void createWorld(){
		
		//player = new Player(world);//createPlayer(); 
		//BodyDef def = new BodyDef();
		//def.type = BodyType.DynamicBody;
		//this.createBody(def);
		//box = world.createBody(def);
		
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		Body boxP = world.createBody(def);
		player = new Player(boxP);
		
		player.getBody().setTransform(1.0f, 4.0f, 0);
		player.getBody().setFixedRotation(true);	
		
		//Body box = createBox(BodyType.StaticBody, 1, 1, 0);
		/*for(int i = 0; i < 10; i++) {
			box = createBox(BodyType.DynamicBody, (float)Math.random(), (float)Math.random(), 13);
			
			box.setTransform((float)Math.random() * 10f - (float)Math.random() * 10f, (float)Math.random() * 10 + 6, 0);
		}*/
		platforms.add(new MovingPlatform(world, 3F, 3, 1,0.25F, 2, 0, 2)); 
		
		for(int i=0;i<width; ++i){
			Body boxGround = createBox(BodyType.StaticBody, 0.5F, 0.5F, 2);
			boxGround.setTransform(i,0,0);
			boxGround.getFixtureList().get(0).setUserData("bd");
			boxGround = createBox(BodyType.StaticBody, 0.5F, 0.5F, 0);
			boxGround.setTransform(i,height-1,0);
			boxGround.getFixtureList().get(0).setUserData("b");
		}
		//platforms.add(new MovingPlatform(world, 2.3F, 1, 2, 0.5f, 2, 4, 8)); 
		//platforms.add(new MovingPlatform(world, 1, 3, 5, 0.5f, 0, 2, 5));		
		//platforms.add(new MovingPlatform(world, -7, 5, 2, 0.5f, -2, 2, 8));	
	}
	
	private Body createBox(BodyType type, float width, float height, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		Body box = world.createBody(def);
 
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width, height);
		
		box.createFixture(poly, density);
		poly.dispose();
 
		return box;
	}
	/*Fixture playerPhysicsFixture;
	Fixture playerSensorFixture;
	
	private Body createPlayer() {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		Body box = world.createBody(def);
 
		PolygonShape poly = new PolygonShape();		
		poly.setAsBox(0.45f, 1.4f);
		playerPhysicsFixture = box.createFixture(poly, 1);
		poly.dispose();			
 
		CircleShape circle = new CircleShape();		
		circle.setRadius(0.45f);
		circle.setPosition(new Vector2(0, -1.4f));
		playerSensorFixture = box.createFixture(circle, 0);	
		//трение
		//playerSensorFixture.setFriction(10);
		circle.dispose();		
 
		box.setBullet(true);
 
		return box;
	}*/

	public void dispose(){
		world.dispose();
	}
}
