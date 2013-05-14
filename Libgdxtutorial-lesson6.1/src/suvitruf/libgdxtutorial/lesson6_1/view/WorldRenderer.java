package suvitruf.libgdxtutorial.lesson6_1.view;

import suvitruf.libgdxtutorial.lesson6_1.controller.WorldController;
import suvitruf.libgdxtutorial.lesson6_1.model.*;

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
	private void loadTextures() {
		/*texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 2);
		textureRegions.put("player", tmp[0][0]);
		textureRegions.put("brick1", tmp[0][1]);
		textureRegions.put("brick2", tmp[1][0]);
		textureRegions.put("brick3", tmp[1][1]);*/
		
		
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth()/ 2, texture.getHeight()/2 );
		TextureRegion left2[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth()/2, tmpLeftRight[0][0].getRegionHeight());
		TextureRegion left[][] = left2[0][0].split(left2[0][0].getRegionWidth()/4, left2[0][0].getRegionHeight()/8);
		
		textureRegions.put("player",  left[0][0]);
		textureRegions.put("brick1",  left[0][1]);
		textureRegions.put("brick2",  left[1][0]);
		textureRegions.put("brick3",  left[1][1]);
		
		
		textureRegions.put("navigation-arrows", tmpLeftRight[0][1]);
		//TextureRegion right[][] = tmpLeftRight[0][1].split(tmpLeftRight[0][1].getRegionWidth(), tmpLeftRight[0][1].getRegionHeight()/2);
		
		TextureRegion rightbot[][] = tmpLeftRight[1][1].split(tmpLeftRight[1][1].getRegionWidth()/2,tmpLeftRight[1][1].getRegionHeight()/2);
		
		textureRegions.put("khob",   rightbot[0][1]); 
		
		/*arrowLeft = new  com.badlogic.gdx.graphics.g2d.Sprite(rightbot[0][0].split(rightbot[0][0].getRegionWidth()/2,rightbot[0][0].getRegionHeight()/2)[0][0]);
		arrowLeft.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowLeft.rotate90(true);
		arrowLeft.rotate90(true); 
		arrowLeft.rotate90(true);
		arrowRight = new  com.badlogic.gdx.graphics.g2d.Sprite(rightbot[0][0].split(rightbot[0][0].getRegionWidth()/2,rightbot[0][0].getRegionHeight()/2)[0][0]);
		arrowRight.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowRight.rotate90(true);
	
		arrowDown = new  com.badlogic.gdx.graphics.g2d.Sprite( rightbot[0][0].split(rightbot[0][0].getRegionWidth()/2,rightbot[0][0].getRegionHeight()/2)[0][0]);
		arrowDown.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowDown.rotate90(true);
		arrowDown.rotate90(true);
		
		arrowUp = new  com.badlogic.gdx.graphics.g2d.Sprite(rightbot[0][0].split(rightbot[0][0].getRegionWidth()/2,rightbot[0][0].getRegionHeight()/2)[0][0]);
		arrowUp.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		
		TextureRegion pause[][] = rightbot[1][0].split(rightbot[1][0].getRegionWidth(),  rightbot[1][0].getRegionHeight()/2);
		
		
		textureRegions.put("home",   (pause[0][0].split(pause[0][0].getRegionWidth()/2,  pause[0][0].getRegionHeight()))[0][0]);
		textureRegions.put("resume",   (pause[0][0].split(pause[0][0].getRegionWidth()/2,  pause[0][0].getRegionHeight()))[0][1]);
		textureRegions.put("fon",   pause[1][0]);*/
	}
	public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);
	
		this.cam.update();
	}  
	public void dispose(){

		world.dispose();
	}
	BitmapFont font;
	public void render(float delta) {
		renderer.render(world.getWorld(), cam.combined);
		spriteBatch.begin();
		drawPlayer();
		drawBlocks();
		drawPlatfowms();
		font.drawMultiLine(spriteBatch, 
				"friction: " + world.getPlayer().getFriction() + "\ngrounded: " +
		WorldController.grounded+"\nvelocityX:"+world.getPlayer().getVelocity().x, 
		world.getPlayer().getPosition().x*ppuX+20, world.getPlayer().getPosition().y*ppuY);
		spriteBatch.end();
		world.getWorld().step(delta, 4, 4);
	}
	private void drawPlatfowms(){
		for(MovingPlatform platform : world.getPlatforms()){
			spriteBatch.draw(textureRegions.get("brick2"), (platform.getBody().getPosition().x - platform.width/2)*ppuX,
					(platform.getBody().getPosition().y- platform.height/2)*ppuY, platform.width*ppuX,platform.height*ppuY );
			
		}
	}
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
	private void drawPlayer(){
		spriteBatch.draw(textureRegions.get("player"), (world.getPlayer().getPosition().x-Player.SIZE/2)*ppuX,(world.getPlayer().getPosition().y-Player.SIZE/2)*ppuY, Player.SIZE*ppuX,Player.SIZE*ppuY );
		
	}
}
