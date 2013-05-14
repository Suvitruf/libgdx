package suvitruf.libgdxtutorial.lesson6_2.view;

import suvitruf.libgdxtutorial.lesson6_2.controller.WorldController;
import suvitruf.libgdxtutorial.lesson6_2.model.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WorldRenderer {
	Box2DDebugRenderer renderer;
	public static float CAMERA_WIDTH = 10f;
	public static  float CAMERA_HEIGHT = 15f;
	public float ppuX;	// pixels per unit on the X axis
	public float ppuY;	// pixels per unit on the Y axis
	//private SpriteBatch spriteBatch;
	MyWorld world;
	public OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	Texture texture;
	public  Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
	
	public WorldRenderer(MyWorld world, float w, float h, boolean debug) {
		renderer = new Box2DDebugRenderer();font = new BitmapFont();
		this.world = world;
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		//spriteBatch = new SpriteBatch();
		//this.cam = new OrthographicCamera(30, 30);
		//SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
		ppuX = (float)Gdx.graphics.getWidth() / CAMERA_WIDTH;
		ppuY = (float)Gdx.graphics.getHeight() / CAMERA_HEIGHT;
		spriteBatch = new SpriteBatch();
		 textureRegions = new HashMap<String, TextureRegion>();
		loadTextures();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
	}
	
	/**
	 * Загрузка атласа и выделение регионов на нём.
	 */
	private void loadTextures() {
		
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth()/ 2, texture.getHeight()/2 );
		TextureRegion left2[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth()/2, tmpLeftRight[0][0].getRegionHeight());
		TextureRegion left[][] = left2[0][0].split(left2[0][0].getRegionWidth()/4, left2[0][0].getRegionHeight()/8);
		
		textureRegions.put("player",  left[0][0]);
		textureRegions.put("brick1",  left[0][1]);
		textureRegions.put("brick2",  left[1][0]);
		textureRegions.put("brick3",  left[1][1]);
		textureRegions.put("balloom",  left[0][2]);
		textureRegions.put("runner",  left[1][2]);
		
		
		textureRegions.put("navigation-arrows", tmpLeftRight[0][1]);
		
		TextureRegion rightbot[][] = tmpLeftRight[1][1].split(tmpLeftRight[1][1].getRegionWidth()/2,tmpLeftRight[1][1].getRegionHeight()/2);
		
		textureRegions.put("khob",   rightbot[0][1]); 
		
	}
	
	/**
	 * Установка камеры.
	 * @param x
	 * @param y
	 */
	public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);
	
		this.cam.update();
	}  
	public void dispose(){

		world.dispose();
	}
	BitmapFont font;
	
	/**
	 * Отрендерить всё.
	 * @param delta
	 * время, прошешее с прошлой прорисовки.
	 */
	public void render(float delta) {
		renderer.render(world.getWorld(), cam.combined);
		spriteBatch.begin();
		drawPlayer();
		drawBlocks();
		drawPlatforms();
		drawNpc();
		/*font.drawMultiLine(spriteBatch, 
				"friction: " + world.getPlayer().getFriction() + "\ngrounded: " +
		WorldController.grounded+"\nvelocityX:"+world.getPlayer().getVelocity().x, 
		world.getPlayer().getPosition().x*ppuX+20, world.getPlayer().getPosition().y*ppuY);*/
		spriteBatch.end();
		world.getWorld().step(delta, 4, 4);
	}
	
	/**
	 * Отрисовать платформы.
	 */
	private void drawPlatforms(){
		for(MovingPlatform platform : world.getPlatforms()){
			spriteBatch.draw(textureRegions.get("brick2"), (platform.getBody().getPosition().x - platform.width/2)*ppuX,
					(platform.getBody().getPosition().y- platform.height/2)*ppuY, platform.width*ppuX,platform.height*ppuY );
			
		}
	}
	
	/**
	 * Отрисовать блоки.
	 */
	private void drawBlocks(){
		 for (Iterator<Body> iter = world.getWorld().getBodies(); iter.hasNext();) {
			 Body body = iter.next();
			 if( body != null &&  body.getFixtureList().get(0).getUserData() != null &&  body.getFixtureList().get(0).getUserData().equals("b"))
			 //((PolygonShape)body.getFixtureList().get(0).getShape()).
			 spriteBatch.draw(textureRegions.get("brick1"), (body.getPosition().x - 0.5F)*ppuX,(body.getPosition().y- 0.5F)*ppuY, 1F*ppuX,1F*ppuY );
			
			 if( body != null &&  body.getFixtureList().get(0).getUserData() != null &&  body.getFixtureList().get(0).getUserData().equals("bd"))
				 //((PolygonShape)body.getFixtureList().get(0).getShape()).
				 spriteBatch.draw(textureRegions.get("brick2"), (body.getPosition().x - 0.5F)*ppuX,(body.getPosition().y- 0.5F)*ppuY, 1F*ppuX,1F*ppuY );
				
		 }
		
		
		
	}
	
	private void drawNpc(){
		for (Iterator<Body> iter = world.getWorld().getBodies(); iter.hasNext();) {
			 Body body = iter.next();
			 if( body != null &&  body.getFixtureList().get(0).getUserData() != null &&  body.getFixtureList().get(0).getUserData().equals("balloom"))
			 
			 spriteBatch.draw(textureRegions.get("balloom"), (body.getPosition().x - 0.5F)*ppuX,(body.getPosition().y- 0.5F)*ppuY, 1F*ppuX,1F*ppuY );
		
			 if( body != null &&  body.getFixtureList().get(0).getUserData() != null &&  body.getFixtureList().get(0).getUserData().equals("runner"))
				 //((PolygonShape)body.getFixtureList().get(0).getShape()).
				 spriteBatch.draw(textureRegions.get("runner"), (body.getPosition().x - 0.5F)*ppuX,(body.getPosition().y- 0.5F)*ppuY, 1F*ppuX,1F*ppuY );
				
		 }
	}
	
	/**
	 * Отрисовать пперса.
	 */
	private void drawPlayer(){
		spriteBatch.draw(textureRegions.get("player"), (world.getPlayer().getPosition().x-Player.SIZE/2)*ppuX,(world.getPlayer().getPosition().y-Player.SIZE/2)*ppuY, Player.SIZE*ppuX,Player.SIZE*ppuY );
		
		spriteBatch.draw(textureRegions.get("player"), (world.getPlayer2().getPosition().x-Player.SIZE/2)*ppuX,(world.getPlayer2().getPosition().y-Player.SIZE/2)*ppuY, Player.SIZE*ppuX,Player.SIZE*ppuY );
		
	}
}
