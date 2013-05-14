package suvitruf.libgdxtutorial.lesson6_2.model;
import java.util.Map;

import suvitruf.libgdxtutorial.lesson6_2.controller.MyContactListener;
import suvitruf.libgdxtutorial.lesson6_2.controller.MyFilter;
import suvitruf.libgdxtutorial.lesson6_2.model.*;

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
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class MyWorld{

	final public static short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
	final public static short CATEGORY_BALOOM = 0x0002;  // 0000000000000010 in binary
	final public static short CATEGORY_RUNNER = 0x0004;  // 0000000000000100 in binary
	final public static short CATEGORY_SCENERY = 0x0008; // 0000000000001000 in binary
	
	final public static short MASK_PLAYER = CATEGORY_RUNNER | CATEGORY_SCENERY;
	final public static short MASK_BALOOM =  CATEGORY_SCENERY ; 
	final public static short MASK_RUNNER = CATEGORY_PLAYER | CATEGORY_SCENERY ; 
	final public static short MASK_SCENERY = -1;
	//final public static short MASK_SCENERY = -1;
	
	//public float ppuX;	
	//public float ppuY;

	World world;
	Player player2;
	Player player;
	Array<MovingPlatform> platforms;// = new Array<MovingPlatform>();
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
	public Player getPlayer2(){
		return player2;
	}
	
	public World getWorld(){
		return world;
	}
	public Array<MovingPlatform> getPlatforms(){
		return platforms;
	}
	public MyWorld(){
		platforms = new Array<MovingPlatform>();
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
	
	public Body baloom;
	public Body runner;
	
	/**
	 * Создание объектов мира
	 */
	private void createWorld(){
		
		//player = new Player(world);//createPlayer(); 
		//BodyDef def = new BodyDef();
		//def.type = BodyType.DynamicBody;
		//this.createBody(def);
		//box = world.createBody(def);
		
		//создание игрока
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		Body boxP = world.createBody(def);
		player = new Player(boxP);		
		player.getBody().setTransform(1.0f, 4.0f, 0);
		player.getBody().setFixedRotation(true);	
		
		
		BodyDef def2 = new BodyDef();
		def.type = BodyType.DynamicBody;
		Body boxP2 = world.createBody(def2);
		player2 = new Player(boxP2);		
		player2.getBody().setTransform(5.0f, 1.0f, 0);
		player2.getBody().setFixedRotation(true);
		//Body box = createBox(BodyType.StaticBody, 1, 1, 0);
		/*for(int i = 0; i < 10; i++) {
			box = createBox(BodyType.DynamicBody, (float)Math.random(), (float)Math.random(), 13);
			
			box.setTransform((float)Math.random() * 10f - (float)Math.random() * 10f, (float)Math.random() * 10 + 6, 0);
		}*/
		
		//создание платформы
		platforms.add(new MovingPlatform(world, 3F, 3, 1,0.25F, 2, 0, 2)); 
		
		//создание блоков
		for(int i=0;i<width; ++i){
			Body boxGround = createBox(BodyType.StaticBody, 0.5F, 0.5F, 2);
			boxGround.setTransform(i,0,0);
			boxGround.getFixtureList().get(0).setUserData("bd");
			boxGround = createBox(BodyType.StaticBody, 0.5F, 0.5F, 0);
			boxGround.setTransform(i,height-1,0);
			boxGround.getFixtureList().get(0).setUserData("b");
		}
		
		
		createBaloom(BodyType.DynamicBody, 0.5F, 0.5F, 7F, 1F, 2);
		createRunner(BodyType.DynamicBody, 0.5F, 0.5F, 9F, 1F, 2);
		
		setFilter();
		//platforms.add(new MovingPlatform(world, 2.3F, 1, 2, 0.5f, 2, 4, 8)); 
		//platforms.add(new MovingPlatform(world, 1, 3, 5, 0.5f, 0, 2, 5));		
		//platforms.add(new MovingPlatform(world, -7, 5, 2, 0.5f, -2, 2, 8));	
	}
	
	private void createRunner(BodyType type, float width, float height, float x, float y, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		Body box = world.createBody(def);
 
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width, height);
		
		Fixture fix = box.createFixture(poly, density);
		
		Filter f = new Filter();
		//f.groupIndex = 2;
		f.categoryBits = this.CATEGORY_RUNNER;
		f.maskBits = this.MASK_RUNNER;
		fix.setFilterData(f);
		
		poly.dispose();
		box.setTransform(x,y,0);
		box.getFixtureList().get(0).setUserData("runner");
	}
	
	private void createBaloom(BodyType type, float width, float height,float x, float y, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		Body box = world.createBody(def);
 
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width, height);
		
		Fixture fix = box.createFixture(poly, density);
		
		Filter f = new Filter();
		//f.groupIndex = 2;
		f.categoryBits = this.CATEGORY_BALOOM;
		f.maskBits = this.MASK_BALOOM;
		fix.setFilterData(f);
		
		poly.dispose();
		box.setTransform(x,y,0);
		box.getFixtureList().get(0).setUserData("balloom");
	}
	
	private void setFilter(){
		//world.setContactFilter(new MyFilter());
	}
	/**
	 * Создание блока.
	 * @param type тип
	 * @param width ширина
	 * @param height высота
	 * @param density плотность
	 * @return
	 */
	private Body createBox(BodyType type, float width, float height, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		Body box = world.createBody(def);
 
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width, height);
		
		Fixture fix = box.createFixture(poly, density);
		
		Filter f = new Filter();
		//f.groupIndex = 2;
		f.maskBits = MASK_SCENERY;
		f.categoryBits = CATEGORY_SCENERY;
		fix.setFilterData(f);
		
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
