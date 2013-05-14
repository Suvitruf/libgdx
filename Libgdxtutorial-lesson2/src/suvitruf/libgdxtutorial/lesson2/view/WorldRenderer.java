package suvitruf.libgdxtutorial.lesson2.view;

import suvitruf.libgdxtutorial.lesson2.model.*;



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {
	public static float CAMERA_WIDTH = 8f;
	public static  float CAMERA_HEIGHT = 5f;
	
	private World world;
	public OrthographicCamera cam;
	ShapeRenderer renderer = new ShapeRenderer();

	
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
		
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);

	}
	public void render() {
		
		
	
		drawBricks();
		 drawPlayer() ;
		
	}
	private void drawBricks() {
		renderer.setProjectionMatrix(cam.combined);
		renderer.begin(ShapeType.FilledRectangle);
		for (Brick brick : world.getBricks()) {
			Rectangle rect =  brick.getBounds();
			float x1 =  brick.getPosition().x + rect.x;
			float y1 =  brick.getPosition().y + rect.y;
			renderer.setColor(new Color(0, 0, 0, 1));
			renderer.filledRect(x1, y1, rect.width, rect.height);
		}
		
		renderer.end();
	}
	private void drawPlayer() {
		renderer.setProjectionMatrix(cam.combined);
		Player player = world.getPlayer();
		renderer.begin(ShapeType.Rectangle);
		
		Rectangle rect = player.getBounds();
		float x1 = player.getPosition().x + rect.x;
		float y1 = player.getPosition().y + rect.y;
		renderer.setColor(new Color(1, 0, 0, 1));
		renderer.rect(x1, y1, rect.width, rect.height);
		renderer.end();
	}
	
}
