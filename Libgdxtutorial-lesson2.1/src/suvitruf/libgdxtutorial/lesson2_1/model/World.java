package suvitruf.libgdxtutorial.lesson2_1.model;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import android.util.Log;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class World extends Stage{

	
	public float ppuX;	
	public float ppuY;
	//выбранный актёр
	Actor selectedActor = null;
	//регионы
	Map<String, TextureRegion> textureRegions;
	public static float CAMERA_WIDTH = 8f;
	public static  float CAMERA_HEIGHT = 5f;
	//обновление положения объектов
	public void update(float delta){
		for(Actor actor: this.getActors())
			if(actor instanceof Player)
				((Player)actor).update(delta);
	}

	public World(int x, int y, boolean b, SpriteBatch spriteBatch, Map<String, TextureRegion> textureRegions){
		super(x,y, b, spriteBatch);
		this.textureRegions = textureRegions;
		ppuX = getWidth() / CAMERA_WIDTH;
		ppuY = getHeight() / CAMERA_HEIGHT;
		//добавим двух персонажей
		addActor(new Player(new Vector2(4,2), this));
		addActor(new Player(new Vector2(4,4), this));
	}
	
	public void setPP(float x, float y){
		ppuX = x;
		ppuY = y;
	}
	
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		super.touchDown(x, y, pointer, button);

		// передвигаем выбранного актёра
		moveSelected(x, y);

		return true;
	}
	
	/**
	 * Передвижение выбранного актёра
	 * @param x
	 * @param y
	 */
	private void moveSelected(float x, float y){
		if(selectedActor != null && selectedActor instanceof Player)
		{
			((Player)selectedActor).ChangeNavigation(x,this.getHeight() -y);
		}
	}
	
	/**
	 * Сбрасываем текущий вектор и направление движения
	 */
	private void resetSelected(){
		if(selectedActor != null && selectedActor instanceof Player)
		{
			((Player)selectedActor).resetWay();
		}
	}
	
	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		super.touchUp(x, y, pointer, button);
		 resetSelected();
    	return true;
    }
	
	public Actor hit(float x, float y, boolean touchable) {

		Actor  actor  = super.hit(x,y,touchable);
		//если выбрали актёра
		if(actor != null)
			//запоминаем
			selectedActor = actor;
		return actor;

	}

}
