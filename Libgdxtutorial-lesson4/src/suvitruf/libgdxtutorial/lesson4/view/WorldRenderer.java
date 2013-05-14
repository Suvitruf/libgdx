package suvitruf.libgdxtutorial.lesson4.view;

import java.util.HashMap;
import java.util.Map;

import suvitruf.libgdxtutorial.lesson4.model.*;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {
	public static float CAMERA_WIDTH = 8f;
	public static  float CAMERA_HEIGHT = 5f;
	
	private World world;
	public OrthographicCamera cam;
	ShapeRenderer renderer = new ShapeRenderer();
	
	private SpriteBatch spriteBatch;
	Texture texture;
	public  Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
	
	public int width;
	public int height;
	public float ppuX;	// пикселей на точку мира по X 
	public float ppuY;	// пикселей на точку мира по Y 
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;  
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
	}
	
	public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);	
		this.cam.update();
	}
	
	public WorldRenderer(World world) {
		spriteBatch = new SpriteBatch();
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
		
		loadTextures();

	}
	private void loadTextures() {
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 2);
		textureRegions.put("player", tmp[0][0]);
		textureRegions.put("brick1", tmp[0][1]);
		textureRegions.put("brick2", tmp[1][0]);
		textureRegions.put("brick3", tmp[1][1]);
	}
	public void render() {				
		spriteBatch.begin();
		drawBricks();
		drawPlayer() ;
		spriteBatch.end();			 
	}
	private void drawBricks() {
		//renderer.setProjectionMatrix(cam.combined);
		//renderer.begin(ShapeType.FilledRectangle);
		int i=0;
		for (Brick brick : world.getBricks()) {
			/*Rectangle rect =  brick.getBounds();
			float x1 =  brick.getPosition().x + rect.x;
			float y1 =  brick.getPosition().y + rect.y;
			renderer.setColor(new Color(0, 0, 0, 1));
			renderer.filledRect(x1, y1, rect.width, rect.height);*/
			spriteBatch.draw(textureRegions.get("brick"+(i%3+1)), brick.getPosition().x* ppuX, brick.getPosition().y * ppuY,Brick.SIZE * ppuX, Brick.SIZE * ppuY);
			++i;
		}
		
		//renderer.end();
	}
	private void drawPlayer() {
		spriteBatch.draw(textureRegions.get("player"), world.getPlayer().getPosition().x* ppuX, world.getPlayer().getPosition().y * ppuY,Player.SIZE * ppuX, Player.SIZE * ppuY);
		
		/*renderer.setProjectionMatrix(cam.combined);
		Player player = world.getPlayer();
		renderer.begin(ShapeType.Rectangle);
		
		Rectangle rect = player.getBounds();
		float x1 = player.getPosition().x + rect.x;
		float y1 = player.getPosition().y + rect.y;
		renderer.setColor(new Color(1, 0, 0, 1));
		renderer.rect(x1, y1, rect.width, rect.height);
		renderer.end();*/
	}
	
}
