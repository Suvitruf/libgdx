package suvitruf.classic.bomberman.view;


import suvitruf.classic.bomberman.controller.*;
import suvitruf.classic.bomberman.model.*;
import suvitruf.classic.bomberman.model.hiddenobject.DetonatorPower;
import suvitruf.classic.bomberman.model.hiddenobject.HiddenObject;
import suvitruf.classic.bomberman.model.npc.*;
//import suvitruf.nes.bomberman.controller.WorldController.Keys;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
//import com.badlogic.gdx.graphics.Pixmap.Format;
import java.util.TimerTask;
//import com.badlogic.gdx.graphics.Pixmap;
import android.util.Log; 
/*import suvitruf.nes.bomberman.model.NpcBase;
import suvitruf.nes.bomberman.model.Brick;
import suvitruf.nes.bomberman.model.BrickBase;
import suvitruf.nes.bomberman.model.Bomberman;
import suvitruf.nes.bomberman.model.HardBrick;
import suvitruf.nes.bomberman.model.World;*/
//import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.Pixmap.Format;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.graphics.Pixmap;
public class WorldRenderer {

	public static float CAMERA_WIDTH = 10f;
	public static  float CAMERA_HEIGHT = 14f;
	
	private World world;
	public OrthographicCamera cam;
	//public Sound sound;
	
	//public  Map<String, Music > musics = new HashMap<String, Music>();
	public  Map<String, Sound > sounds = new HashMap<String, Sound>();
	
	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	ShapeRenderer bgRenderer = new ShapeRenderer();
	//ShapeRenderer controlRenderer = new ShapeRenderer();
	//ShapeRenderer textRenderer = new ShapeRenderer();
	/** Textures **/
	//private Texture bobTexture; 
	//private Texture blockTexture;
	public double angle;
	private SpriteBatch spriteBatch;
	private boolean debug = false;
	public int width;
	public int height;
	public float ppuX;	// pixels per unit on the X axis
	public float ppuY;	// pixels per unit on the Y axis
	
	TimerTask task;
	Timer timer;
	
	Texture texture;
	public  Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
	//public  Map<String, Texture> textures = new HashMap<String, Texture>();
	public  Map<String,  Animation> animations;// = new HashMap<String,  Animation>();
	Sprite arrowLeft;
	Sprite arrowRight;
	Sprite arrowUp;
	Sprite arrowDown;
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;  
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		//this.world.setSize (w,h);
	}
	
	public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);
	
		this.cam.update();
	}
	
	public void init(World world, float w, float h, boolean debug){
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);		
		SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
		this.cam.update();
		
		foundDoor = false;
		currentSound = CurrentSound.none;
		   
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		
		createTimer();
		 playMainTheme();
	}
	public WorldRenderer(/*World world, float w, float h, boolean debug*/) {
	
		
		
		//createTimer();
		 //playMainTheme();
		 animations = new HashMap<String,  Animation>();
		 textureRegions = new HashMap<String, TextureRegion>();
		loadTextures();
		loadSounds();
		//loadMusics();
		
		//
	}
	
	/*private void loadMusics(){
		musics.put("stage-main-theme", Gdx.audio.newMusic(Gdx.files.internal("audio/stage-main-theme.ogg")));
		musics.put("stage-main-theme-find-the-door", Gdx.audio.newMusic(Gdx.files.internal("audio/stage-main-theme-find-the-door.ogg")));
	
	}*/
	private void loadSounds(){
		sounds.put("stage-main-theme", Gdx.audio.newSound(Gdx.files.internal("audio/stage-main-theme.ogg")));
		sounds.put("stage-main-theme-find-the-door", Gdx.audio.newSound(Gdx.files.internal("audio/stage-main-theme-find-the-door.ogg")));
	
		/*musics.put("dying", Gdx.audio.newMusic(Gdx.files.internal("audio/dying.ogg")));
		musics.put("left-right", Gdx.audio.newMusic(Gdx.files.internal("audio/left-right.ogg")));
		musics.put("just-died", Gdx.audio.newMusic(Gdx.files.internal("audio/just-died.mp3")));
		musics.put("up-down", Gdx.audio.newMusic(Gdx.files.internal("audio/up-down.ogg")));
		musics.put("boom", Gdx.audio.newMusic(Gdx.files.internal("audio/boom.ogg")));
		musics.put("put-bomb", Gdx.audio.newMusic(Gdx.files.internal("audio/put-bomb.ogg")));*/
		sounds.put("dying", Gdx.audio.newSound(Gdx.files.internal("audio/dying.ogg")));
		sounds.put("left-right", Gdx.audio.newSound(Gdx.files.internal("audio/left-right.ogg")));
		sounds.put("just-died", Gdx.audio.newSound(Gdx.files.internal("audio/just-died.mp3")));
		sounds.put("level-complete", Gdx.audio.newSound(Gdx.files.internal("audio/level-complete.mp3")));
		sounds.put("up-down", Gdx.audio.newSound(Gdx.files.internal("audio/up-down.ogg")));
		sounds.put("boom", Gdx.audio.newSound(Gdx.files.internal("audio/boom.ogg")));
		sounds.put("put-bomb", Gdx.audio.newSound(Gdx.files.internal("audio/put-bomb.ogg")));

	}
	
	
	/*
	private void loadBaloomAnimation(){
		Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[Balloom.FRAME_COLS * Balloom.FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < Balloom.FRAME_ROWS; i++)
			for (int j = 0; j < Balloom.FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];


			
	
		animations.put(Balloom.Name, new Animation(2* Balloom.SPEED, textureFrames));
	}*/
	/*
	private void loadBoomAnimation(){
		Texture texture  = new Texture(Gdx.files.internal("images/BoomSprite.png"));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Boom.FRAME_COLS, texture.getHeight() / Boom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[Boom.FRAME_COLS * Boom.FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < Boom.FRAME_ROWS; i++)
			for (int j = 0; j < Boom.FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];


			
	
		animations.put(Boom.Name, new Animation(Boom.SPEED, textureFrames));
	}*/
	
	/*
	private void loadBombAnimation(){
		Texture texture  = new Texture(Gdx.files.internal(Bomb.textureName));
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Bomb.FRAME_COLS, texture.getHeight() / Bomb.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[Bomb.FRAME_COLS * Bomb.FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < Bomb.FRAME_ROWS; i++)
			for (int j = 0; j < Bomb.FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];


			
	
		animations.put(Bomb.Name, new Animation(8F, textureFrames));
	}*/
	
	
	
	private void loadRegions(){
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth()/ 2, texture.getHeight() );
		
		TextureRegion tmpMain[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth(), tmpLeftRight[0][0].getRegionHeight()/2);
		TextureRegion tmpTop[][] =  tmpMain[0][0].split(tmpMain[0][0].getRegionWidth()/2, tmpMain[0][0].getRegionHeight());
		
		TextureRegion tmp[][] = tmpTop[0][0].split(tmpTop[0][0].getRegionWidth()/ 8, tmpTop[0][0].getRegionHeight() / 16);	
	
		textureRegions.put("Door", tmp[0][0]);
		textureRegions.put("HardBrick", tmp[0][1]);
		textureRegions.put("wallpass", tmp[0][2]);
		textureRegions.put("flames", tmp[0][3]);
		textureRegions.put("bombs", tmp[1][0]);
		textureRegions.put("speed", tmp[1][1]);
		textureRegions.put("detonator", tmp[1][2]);
		textureRegions.put("bombpass", tmp[1][3]);
		textureRegions.put("flamepass", tmp[8][7]);
		textureRegions.put("mystery", tmp[9][7]);
		loadBrickAnimation(tmp);
		loadBombAnimation(tmp);
		loadBoomAnimation(tmp);
		loadBaloomAnimation(tmp);
		loadDollAnimation(tmp);
		loadOnealAnimation(tmp);
		loadMinvoAnimation(tmp);
		loadPassAnimation(tmp);
		loadPontanAnimation(tmp);
		loadOvapiAnimation(tmp);
		loadKondoriaAnimation(tmp);
		
		TextureRegion tmpBomber[][] =  tmpTop[0][1].split(tmpTop[0][1].getRegionWidth(),tmpTop[0][1].getRegionHeight()/2);		
		loadBombermanAnimation(tmpBomber[0][0].split(tmpBomber[0][0].getRegionWidth()/8,tmpBomber[0][0].getRegionHeight()/8));
		
		TextureRegion controls[][] = tmpLeftRight[0][1].split(tmpLeftRight[0][1].getRegionWidth(), tmpLeftRight[0][1].getRegionHeight()/2);
		textureRegions.put("navigation-arrows",   controls[0][0]);
		
		TextureRegion controls2[][] = controls[1][0].split( controls[1][0].getRegionWidth()/2,  controls[1][0].getRegionHeight()/2);
		textureRegions.put("khob",   controls2[0][1]);
		arrowLeft = new  com.badlogic.gdx.graphics.g2d.Sprite( controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
		//arrowLeft.rotate(90);
		
		arrowLeft.rotate90(true);
		arrowLeft.rotate90(true); 
		arrowLeft.rotate90(true);
		arrowRight = new  com.badlogic.gdx.graphics.g2d.Sprite( controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
		
		arrowRight.rotate90(true);
		
		arrowDown = new  com.badlogic.gdx.graphics.g2d.Sprite( controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
	
		arrowDown.rotate90(true);
		arrowDown.rotate90(true);
		arrowUp = new  com.badlogic.gdx.graphics.g2d.Sprite( controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
		//textureRegions.put("arrows-nes",   controls2[0][0].split(controls2[0][0].getRegionWidth()/2,controls2[0][0].getRegionHeight()/2)[0][0]);
		textureRegions.put("home",   (controls2[1][0].split(controls2[1][0].getRegionWidth()/2,  controls2[1][0].getRegionHeight()/2))[0][0]);
		textureRegions.put("resume",   (controls2[1][0].split(controls2[1][0].getRegionWidth()/2,  controls2[1][0].getRegionHeight()/2))[0][1]);
		textureRegions.put("fon",   (controls2[1][0].split(controls2[1][0].getRegionWidth(),  controls2[1][0].getRegionHeight()/2))[1][0]);
		
		TextureRegion tmpBot[][] = tmpMain[1][0].split(tmpMain[1][0].getRegionWidth()/2, tmpMain[1][0].getRegionHeight());
		
		TextureRegion tmpFont[][] =  tmpBot[0][0].split(tmpBot[0][0].getRegionWidth()/ 4, tmpBot[0][0].getRegionHeight() / 8);	
		
		textureRegions.put("1",  tmpFont[0][0]);
		textureRegions.put("2",  tmpFont[0][1]);
		textureRegions.put("3",  tmpFont[0][2]);
		textureRegions.put("4",  tmpFont[0][3]);
		textureRegions.put("5",  tmpFont[1][0]);
		textureRegions.put("6",  tmpFont[1][1]);
		textureRegions.put("7",  tmpFont[1][2]);
		textureRegions.put("8",  tmpFont[1][3]);
		textureRegions.put("9",  tmpFont[2][0]);
		textureRegions.put("0",  tmpFont[2][1]);
		
		textureRegions.put("t",  tmpFont[3][0]);
		textureRegions.put("i",  tmpFont[3][1]);
		textureRegions.put("m",  tmpFont[3][2]);
		textureRegions.put("e",  tmpFont[3][3]);
		
		textureRegions.put("l",  tmpFont[4][0]);
		textureRegions.put("f",  tmpFont[4][1]);
		
		textureRegions.put("cG",  tmpFont[7][2]);
		textureRegions.put("iG",  tmpFont[7][1]);
		textureRegions.put("sG",  tmpFont[7][0]);
		textureRegions.put("tG",  tmpFont[5][0]);
		textureRegions.put("aG",  tmpFont[5][1]);
		textureRegions.put("gG",  tmpFont[5][2]);
		textureRegions.put("eG",  tmpFont[5][3]);
		
		textureRegions.put("bG",  tmpFont[6][0]);
		textureRegions.put("oG",  tmpFont[6][1]);
		textureRegions.put("nG",  tmpFont[6][2]);
		textureRegions.put("uG",  tmpFont[6][3]);
		
		//textureRegions.put("circle", tmpFont[6][3]);
		
	}
	/*
	private void loadRegionsOld(){
		texture  = new Texture(Gdx.files.internal("images/atlas.png"));
		TextureRegion tmpMain[][] = TextureRegion.split(texture, texture.getWidth(), texture.getHeight() / 2);
		TextureRegion tmpTop[][] =  tmpMain[0][0].split(tmpMain[0][0].getRegionWidth()/2, tmpMain[0][0].getRegionHeight());
		
		TextureRegion tmp[][] = tmpTop[0][0].split(tmpTop[0][0].getRegionWidth()/ 8, tmpTop[0][0].getRegionHeight() / 16);	
	
		textureRegions.put("Door", tmp[0][0]);
		textureRegions.put("HardBrick", tmp[0][1]);
		textureRegions.put("wallpass", tmp[0][2]);
		textureRegions.put("flames", tmp[0][3]);
		textureRegions.put("bombs", tmp[1][0]);
		textureRegions.put("speed", tmp[1][1]);
		textureRegions.put("detonator", tmp[1][2]);
		textureRegions.put("bombpass", tmp[1][3]);
	
		loadBrickAnimation(tmp);
		loadBombAnimation(tmp);
		loadBoomAnimation(tmp);
		loadBaloomAnimation(tmp);
		loadDollAnimation(tmp);
		loadOnealAnimation(tmp);
		loadMinvoAnimation(tmp);
		loadPassAnimation(tmp);
		loadPontanAnimation(tmp);
		loadOvapiAnimation(tmp);
		loadKondoriaAnimation(tmp);
		textureRegions.put("navigation-arrows",   tmpMain[1][0]);
		
		TextureRegion tmpCircleBomber[][] =  tmpTop[0][1].split(tmpTop[0][1].getRegionWidth(), tmpTop[0][1].getRegionHeight()/2);
		TextureRegion tmpCircle[][] =  tmpCircleBomber[1][0].split(tmpCircleBomber[1][0].getRegionWidth()/4, tmpCircleBomber[1][0].getRegionHeight()/4);
		textureRegions.put("circle",  tmpCircle[0][0]);
		textureRegions.put("1",  tmpCircle[0][1]);
		textureRegions.put("2",  tmpCircle[0][2]);
		textureRegions.put("3",  tmpCircle[0][3]);
		textureRegions.put("4",  tmpCircle[1][0]);
		textureRegions.put("5",  tmpCircle[1][1]);
		textureRegions.put("6",  tmpCircle[1][2]);
		textureRegions.put("7",  tmpCircle[1][3]);
		textureRegions.put("8",  tmpCircle[2][0]);
		textureRegions.put("9",  tmpCircle[2][1]);
		textureRegions.put("0",  tmpCircle[2][2]);
		
		textureRegions.put("t",  tmpCircle[3][0]);
		textureRegions.put("i",  tmpCircle[3][1]);
		textureRegions.put("m",  tmpCircle[3][2]);
		textureRegions.put("e",  tmpCircle[3][3]);
		TextureRegion tmpBomberFont[][] =  tmpCircleBomber[0][0].split(tmpCircleBomber[0][0].getRegionWidth(),tmpCircleBomber[0][0].getRegionHeight()/2);		
		loadBombermanAnimation(tmpBomberFont[0][0].split(tmpBomberFont[0][0].getRegionWidth()/8,tmpBomberFont[0][0].getRegionHeight()/4));
		
		
		TextureRegion tmpFont1[][] = tmpBomberFont[1][0].split(tmpBomberFont[1][0].getRegionWidth()/4,tmpBomberFont[1][0].getRegionHeight()/2);
		textureRegions.put("l",  tmpFont1[0][0]);
		textureRegions.put("f",  tmpFont1[0][1]);
		textureRegions.put("sG",  tmpFont1[0][2]);
		textureRegions.put("tG",  tmpFont1[0][3]);
		textureRegions.put("aG",  tmpFont1[1][0]);
		textureRegions.put("gG",  tmpFont1[1][1]);
		textureRegions.put("eG",  tmpFont1[1][2]);
	
		
	}
*/
	private void loadOnealAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[7];
		int index = 0;
		for (int j = 0; j <=6; j++)
			textureFrames[index++] = tmp[8][j];


		
	
		animations.put(Oneal.Name, new Animation(Oneal.ANIMATIONSPEED, textureFrames));
	}
	private void loadBombermanAnimation(TextureRegion tmp[][]){
		TextureRegion[] textureFrames = new TextureRegion[24];
		int index = 0;
		for (int i = 0; i <=2 ; i++)
			for (int j = 0; j <=7; j++)
				textureFrames[index++] = tmp[i][j];
		animations.put("bomberman", new Animation(Bomberman.ANIMATIONSPEED, textureFrames));
	}
	
	private void loadBaloomAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[Balloom.FRAME_COLS * Balloom.FRAME_ROWS];
		int index = 0;
		for (int i = 4; i <=7 ; i++)
			for (int j = 0; j <=3; j++)
				textureFrames[index++] = tmp[i][j];


			
	
		animations.put(Balloom.Name, new Animation(2* Balloom.SPEED, textureFrames));
	}
	
	
	private void loadDollAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[7];
		int index = 0;
		for (int j = 0; j <=6; j++)
				textureFrames[index++] = tmp[12][j];


			
	
		animations.put(Doll.Name, new Animation(Doll.ANIMATIONSPEED, textureFrames));
	}
	 

	private void loadMinvoAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[7];
		int index = 0;
		for (int j = 0; j <=6; j++)
				textureFrames[index++] = tmp[13][j];


			
	
		animations.put(Minvo.Name, new Animation(Minvo.ANIMATIONSPEED, textureFrames));
	}
	
	private void loadPassAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[7];
		int index = 0;
		for (int j = 0; j <=6; j++)
				textureFrames[index++] = tmp[9][j];


			
	
		animations.put(Pass.Name, new Animation(Pass.ANIMATIONSPEED, textureFrames));
	}
	
	private void loadPontanAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[7];
		int index = 0;
		for (int j = 0; j <=6; j++)
				textureFrames[index++] = tmp[11][j];


			
	
		animations.put(Pontan.Name, new Animation(Pontan.ANIMATIONSPEED, textureFrames));
	}
	
	private void loadKondoriaAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[7];
		int index = 0;
		for (int j = 0; j <=6; j++)
				textureFrames[index++] = tmp[14][j];


			
	
		animations.put(Kondoria.Name, new Animation(Kondoria.ANIMATIONSPEED, textureFrames));
	}
	private void loadOvapiAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BalloomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Balloom.FRAME_COLS, texture.getHeight() / Balloom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[7];
		int index = 0;
		for (int j = 0; j <=6; j++)
				textureFrames[index++] = tmp[15][j];


			
	
		animations.put(Ovapi.Name, new Animation(Ovapi.ANIMATIONSPEED, textureFrames));
	}
	private void loadBoomAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal("images/BoomSprite.png"));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Boom.FRAME_COLS, texture.getHeight() / Boom.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[Boom.FRAME_COLS * Boom.FRAME_ROWS];
		int index = 0;
		for (int i = 0; i <= 6; i++)
			for (int j = 4; j <= 7; j++)
				textureFrames[index++] = tmp[i][j];


			
	
		animations.put(Boom.Name, new Animation(Boom.SPEED, textureFrames));
	}
	private void loadBombAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Gdx.files.internal(Bomb.textureName));
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Bomb.FRAME_COLS, texture.getHeight() / Bomb.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[Bomb.FRAME_COLS * Bomb.FRAME_ROWS];
		int index = 0;
		for (int j = 4; j <= 7; j++)
			textureFrames[index++] = tmp[7][j];


			
	
		animations.put(Bomb.Name, new Animation(8F, textureFrames));
	}
	
	private void loadBrickAnimation(TextureRegion tmp[][]){
		//Texture texture  = new Texture(Brick.textureName);
		//TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / Brick.FRAME_COLS, texture.getHeight() / Brick.FRAME_ROWS);
		TextureRegion[] textureFrames = new TextureRegion[Brick.FRAME_COLS * Brick.FRAME_ROWS];
		
		int index = 0;
		for (int i = 2; i <= 3; i++)
			for (int j = 0; j < 4; j++)
				textureFrames[index++] = tmp[i][j];
		
		/* int index = 0;
			for (int i = 0; i < Brick.FRAME_ROWS; i++)
				for (int j = 0; j < Brick.FRAME_COLS; j++)
					textureFrames[index++] = tmp[i][j];*/
			
	
		animations.put(Brick.Name, new Animation(Brick.SPEED, textureFrames));
	}
	private void loadTextures() {
		
		//textures.put("HardBrick", new Texture(Gdx.files.internal(HardBrick.textureName)));
		//loadBrickAnimation();
		//loadBaloomAnimation();
		//loadBoomAnimation();
		//loadBombAnimation();
		loadRegions();
		//textures.put("Brick", new Texture(Gdx.files.internal(Brick.textureName)));
		//textures.put("Balloom", new Texture(Gdx.files.internal(Balloom.textureName)));
		//bobTexture = new  Texture(pixmap );
		//bobTexture = new  Texture(256, 256, Format.RGB888);
		//blockTexture = new Texture(Gdx.files.internal("images/block.png"));
		//bobTexture = new  Texture(Gdx.files.internal("images/bomberman.png"));
		//blockTexture = new Texture(Gdx.files.internal("images/block.png"));
	}
	
	
	/*private void gameOver(){
		
	}*/
	
	public void dispose(){
		this.stopMusic();
		this.clearTimer();
		this.disposeSounds();
		
		try{
			
			spriteBatch.dispose();
			debugRenderer.dispose();
			bgRenderer.dispose();
			animations.clear();
			textureRegions.clear();
			texture.dispose();
		
			world.dispose();
		}
		catch(Exception e){
			
		}
		
	}
	public int timeLeft;
	public void clearTimer(){
		if(task != null){
			timer.cancel();
			timer.purge();
			timer = null;
			task.cancel();
			task = null;
		}
	}
	private void createTimer(){
		timeLeft = 0;
		task = new TimerTask(){
			public void run()
		      {
				if(!WorldController.paused)
					++timeLeft;
				if(!world.bonus)
				{
					/*if(timeLeft>=5)
						world.getBomberman().setState(Bomberman.State.WON);*/
					if(timeLeft >= 200){
						timeLeft = 200;
						world.generateNpc(6,8);
						clearTimer();
					}
				}
				else
				{
					if(timeLeft %3 == 0 && world.getNpcCount()<=15)
						world.generateBonusNpc(2); 
					if(timeLeft >= 30){
						timeLeft = 30;
						clearTimer();
						world.getBomberman().setState(Bomberman.State.WON);
						playMusic();
					}
				} 
				//if(timeLeft>=1000)
				//	gameOver();
		      }
		};
		timer=new Timer();
		
		timer.schedule(task, 1,1000);
	}
	
	private void drawPoints(){
		String points = Integer.toString(world.points);
		int length =  points.length();
    	for(int i=0;i<length;++i){
    		spriteBatch.draw(textureRegions.get(points.substring(i,i+1)),(CAMERA_WIDTH*0.48F+0.7F*i)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
    	}
		 //CharSequence p = Integer.toString(world.points);
		 //font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		 //font.setScale(2,2);
		 //font.draw(spriteBatch, p,CAMERA_WIDTH*0.48F*ppuX, CAMERA_HEIGHT*ppuY);
		 //font.draw(spriteBatch, Integer.toString(world.Left),CAMERA_WIDTH*0.78F*ppuX, CAMERA_HEIGHT*ppuY);
		 
	}
	//BitmapFont font = new BitmapFont();
	private void drawTimer(){
		//try{
		spriteBatch.draw(textureRegions.get("t"),CAMERA_WIDTH*0.05F*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("i"),(CAMERA_WIDTH*0.05F+0.7F)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("m"),(CAMERA_WIDTH*0.05F+1.4F)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("e"),(CAMERA_WIDTH*0.05F+2.1F)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
    	
		if(world.bonus && timeLeft >30) timeLeft = 30;
		if(!world.bonus && timeLeft>=200) timeLeft = 200;
		//timeLeft = 201;
		String tLeft = ((world.bonus) ? Integer.toString(30-timeLeft) : Integer.toString(200-timeLeft));
		int length =  tLeft.length();
    	for(int i=0;i<length;++i){
    		spriteBatch.draw(textureRegions.get(tLeft.substring(i,i+1)),(CAMERA_WIDTH*0.05F+3.5F+0.7F*i)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
    	}
		 //CharSequence stage = "Time "+(200-timeLeft);
		 //font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		 //font.setScale(2,2);
		 //font.draw(spriteBatch, stage,CAMERA_WIDTH*0.08F*ppuX, CAMERA_HEIGHT*ppuY);
		 //font.draw(spriteBatch, Integer.toString(world.Left),CAMERA_WIDTH*0.78F*ppuX, CAMERA_HEIGHT*ppuY);
    	spriteBatch.draw(textureRegions.get("l"),CAMERA_WIDTH*0.8F*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("e"),(CAMERA_WIDTH*0.8F+0.7F)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("f"),(CAMERA_WIDTH*0.8F+1.4F)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
		spriteBatch.draw(textureRegions.get("t"),(CAMERA_WIDTH*0.8F+2.1F)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY); 		 
    	
		String lLeft = Integer.toString(world.Left);
		//lLeft = "-1";
		int length2 =  lLeft.length();
		for(int i=0;i<length2;++i) 
			spriteBatch.draw(textureRegions.get(lLeft.substring(i,i+1)),(CAMERA_WIDTH*0.8F+3F+0.7F*i)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY);
		//}
		//catch(NullPointerException e){}
		
		//spriteBatch.draw(textureRegions.get(Integer.toString(world.Left)),(CAMERA_WIDTH*0.8F+3F)*ppuX, (CAMERA_HEIGHT-1F)*ppuY, 1F*ppuX, 1F*ppuY);
		 
	}
	
	
	/*
	private void findTheDoor(){
		
	}*/
	
	private void drawTop(){
		
		
		//for(Bomb bomb : world.getBombsTop()){
			//npc.getVelocity().y =-3F;	
			//Log.e("bomb", "bomb: "+bomb.getPosition().x+ " - "+bomb.getPosition().y);
			//spriteBatch.draw(Bomb.getZeroFrame(), CAMERA_WIDTH*0.9F*ppuX,1F*ppuY, Bomb.SIZE*2*ppuX, Bomb.SIZE*2*ppuY);
		
			//spriteBatch.draw(bomb.getZeroFrame(), CAMERA_WIDTH*0.9F*ppuX,(CAMERA_HEIGHT-1F)*ppuY, Bomb.SIZE*ppuX, Bomb.SIZE*ppuY);
			//spriteBatch.draw(textures.get(npc.getName()), 2, 2, NpcBase.SIZE * ppuX, NpcBase.SIZE * ppuY);
		//}
		drawPoints();
		drawTimer();
	}
	private void drawParts(){
		//
		bgRenderer.setProjectionMatrix(cam.combined);
		bgRenderer.begin(ShapeType.FilledRectangle);
		bgRenderer.setColor(new Color(188F/255, 188F/255, 188F/255, 1));
		bgRenderer.filledRect(-CAMERA_WIDTH, 0, CAMERA_WIDTH +4.1F, CAMERA_HEIGHT );
		bgRenderer.filledRect(world.width-3.5F, 0, CAMERA_WIDTH , CAMERA_HEIGHT );
		
		bgRenderer.filledRect(0, CAMERA_HEIGHT -1F, world.width , 1F );
		bgRenderer.end();
	}
	private boolean insideCameraBorder(){
		return CAMERA_WIDTH / 2f<= world.getBomberman().getPosition().x && world.width-CAMERA_WIDTH/2F >=world.getBomberman().getPosition().x  ;
	}
	/*
	private boolean insideCameraBorderForRender(){
		return CAMERA_WIDTH / 2f<= world.getBomberman().getPosition().x && world.width-CAMERA_WIDTH/2F >=world.getBomberman().getPosition().x +Bomberman.WIDTH  ;
	}*/
	
	public void render() {
		drawParts();
		
	    Bomberman bomberman = world.getBomberman();
		
		if(oldX == 0) oldX = bomberman.getPosition().x;
		
		if( oldX != bomberman.getPosition().x ){
			if(insideCameraBorder())
			{
				SetCamera(bomberman.getPosition().x , CAMERA_HEIGHT / 2f );
				oldX = bomberman.getPosition().x ;
			}
		}
		
		spriteBatch.begin();
			spriteBatch.disableBlending();
		 
			drawObjects();
			drawBricks();
			spriteBatch.enableBlending();
			
			if(!WorldController.paused) drawBooms();
			drawBombs();
		
			drawBomberman();
			drawNpcs();
			drawTop();
			//if(!WorldController.paused) drawBooms();
			//drawBombs();
			if(WorldController.joy)
				drawWalkingControlCircle();
			else
				drawWalkingControlArrows();
			if(WorldController.paused) drawPauseControlls();
		spriteBatch.end();	
		
	
		
		if (debug)
			drawDebug();
		
		if(WorldController.soundOn && !WorldController.paused) playMusic();
		
	}
	
	public void drawPauseControlls(){
		spriteBatch.setColor(0.9f, 0.9f, 0.9f, 0.7f);
		spriteBatch.draw(textureRegions.get("fon"), 
				(CAMERA_WIDTH/2-4F)* ppuX , (CAMERA_HEIGHT/2-2F) * ppuY, 
				8F * ppuX,4F * ppuY);
		spriteBatch.setColor(1f, 1f, 1f, 1f);
		
		spriteBatch.draw(textureRegions.get("home"), 
				(CAMERA_WIDTH/2+1F)* ppuX , (CAMERA_HEIGHT/2-1F) * ppuY, 
				2F * ppuX,2F * ppuY);
		  
		spriteBatch.draw(textureRegions.get("resume"), 
				(CAMERA_WIDTH/2-3F)* ppuX , (CAMERA_HEIGHT/2-1F) * ppuY, 
				2F * ppuX,2F * ppuY);
		
		//Log.e("pausedraw","true");
		/*controlRenderer.setColor(1, 0, 0, 0.1F);
		controlRenderer.setProjectionMatrix(cam.combined);
		
		controlRenderer.begin(ShapeType.FilledRectangle);
		//controlRenderer.setColor(new Color(0.9F, 0.9F, 0.9F, 0.2F));
	
		
		controlRenderer.filledRect(CAMERA_WIDTH/2-2F, CAMERA_HEIGHT/2-2F, 4F,4F);
		controlRenderer.setColor(1, 0, 0, 0.1F);
		controlRenderer.end();*/
		
	}
	private void drawWalkingControlArrows(){
		
		WalkingControlArrows control = world.getWalkingControlArrows();
		/*
		arrow.setPosition(control.positionLeft.x*ppuX, control.positionLeft.y*ppuY);
		arrow.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		arrow.rotate(90);*/
		
		arrowLeft.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		arrowRight.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
			arrowDown.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
			arrowUp.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
			
		
		arrowLeft.setPosition(control.positionLeft.x*ppuX, control.positionLeft.y*ppuY);
		arrowRight.setPosition(control.positionRight.x*ppuX, control.positionRight.y*ppuY);
		arrowUp.setPosition(control.positionUp.x*ppuX, control.positionUp.y*ppuY);
		arrowDown.setPosition(control.positionDown.x*ppuX, control.positionDown.y*ppuY);
		
		arrowLeft.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		arrowDown.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		arrowRight.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		arrowUp.setSize(WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		
		
		
		arrowLeft.draw(spriteBatch);
		arrowRight.draw(spriteBatch);
		arrowUp.draw(spriteBatch);
		arrowDown.draw(spriteBatch);
		
		
		/*
		spriteBatch.draw(animations.get(Bomb.Name).getKeyFrame(0,true), CAMERA_WIDTH*0.9F*ppuX,1F*ppuY, Bomb.SIZE*2*ppuX, Bomb.SIZE*2*ppuY);
		
		if(world.getPower("detonator")>=1 || world.inreasedPowerUp == DetonatorPower.Name )
		spriteBatch.draw(textureRegions.get(DetonatorPower.Name), CAMERA_WIDTH*0.9F*ppuX,4F*ppuY, HiddenObject.SIZE*2*ppuX, HiddenObject.SIZE*2*ppuY);
		*/
		spriteBatch.setColor(1F, 1F, 1F, 1F/(11-	WalkingControlArrows.Opacity));
		spriteBatch.draw(animations.get(Bomb.Name).getKeyFrame(0,true), control.bombPosition.x*ppuX,control.bombPosition.y*ppuY,WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		
		if(world.getPower("detonator")>=1 || world.inreasedPowerUp == DetonatorPower.Name )
		spriteBatch.draw(textureRegions.get(DetonatorPower.Name), control.detonatorPosition.x*ppuX,control.detonatorPosition.y*ppuY, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuY);
		
		spriteBatch.setColor(1F, 1F, 1F, 1F);
		/*spriteBatch.draw(textureRegions.get("arrows-nes"), 
				control.positionLeft.x*ppuX, control.positionLeft.y*ppuY, 
				control.positionLeft.x*ppuX, control.positionLeft.y*ppuY,
				WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient* ppuY,
				1,1,90);
		
		
		spriteBatch.draw(textureRegions.get("arrows-nes"), 
				control.positionRight.x*ppuX, control.positionRight.y*ppuY, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient* ppuY,
				WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient* ppuY,
				1,1,-90);
		
		
		spriteBatch.draw(textureRegions.get("arrows-nes"), 
				control.positionUp.x*ppuX, control.positionUp.y*ppuY, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient* ppuY);
		
		
		spriteBatch.draw(textureRegions.get("arrows-nes"), 
				control.positionDown.x*ppuX, control.positionDown.y*ppuY, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient* ppuY,
				WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient*ppuX, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient* ppuY,
				1,1,180);*/
		
		/*
		spriteBatch.draw(textureRegions.get("arrows-nes"), 
				0, 0, 2*ppuX, 2* ppuY);*/
		
		//arrows-nes
	}
	private void drawWalkingControlCircle(){
		
		WalkingControl control = world.getWalkingControl();
		
		 
		spriteBatch.setColor(1F, 1F, 1F, 1F/(11-	WalkingControl.Opacity)); 
		
		spriteBatch.draw(textureRegions.get("navigation-arrows"), 
				control.getPosition().x*ppuX, control.getPosition().y*ppuY, WalkingControl.SIZE * WalkingControl.Coefficient*ppuX, WalkingControl.SIZE*WalkingControl.Coefficient * ppuY);
		
		spriteBatch.draw(textureRegions.get("khob"), 
				(float)(control.getPosition().x+WalkingControl.SIZE*WalkingControl.Coefficient/2-WalkingControl.CSIZE*WalkingControl.Coefficient/2+control.getOffsetPosition().x)*ppuX, 
				(float)(control.getPosition().y+WalkingControl.SIZE*WalkingControl.Coefficient/2-WalkingControl.CSIZE*WalkingControl.Coefficient/2+control.getOffsetPosition().y)*ppuY, 
				WalkingControl.CSIZE *WalkingControl.Coefficient* ppuX, WalkingControl.CSIZE *WalkingControl.Coefficient* ppuY);
		
		 
	
		spriteBatch.draw(animations.get(Bomb.Name).getKeyFrame(0,true), control.bombPosition.x*ppuX,control.bombPosition.y*ppuY,WalkingControl.BSIZE*WalkingControl.Coefficient*ppuX, WalkingControl.BSIZE*WalkingControl.Coefficient*ppuY);
		
		if(world.getPower("detonator")>=1 || world.inreasedPowerUp == DetonatorPower.Name )
		spriteBatch.draw(textureRegions.get(DetonatorPower.Name), control.detonatorPosition.x*ppuX,control.detonatorPosition.y*ppuY, WalkingControl.BSIZE*WalkingControl.Coefficient*ppuX, WalkingControl.BSIZE*WalkingControl.Coefficient*ppuY);
		
		spriteBatch.setColor(1F, 1F, 1F, 1F);
		
		/*
		spriteBatch.draw(textureRegions.get("circle"), 
				(float)(control.getPosition().x+WalkingControl.SIZE/2-WalkingControl.CSIZE/2+Math.cos(angle)*WalkingControl.CIRCLERADIUS)*ppuX, (float)(control.getPosition().y+WalkingControl.SIZE/2-WalkingControl.CSIZE/2+Math.sin(angle)*WalkingControl.CIRCLERADIUS)*ppuY, WalkingControl.CSIZE * ppuX, WalkingControl.CSIZE * ppuY);
		*/
		
		//Log.e("sin", Double.toString(Math.sin(angle)));
		/*
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(new Color(1F, 1F, 1F, 0.6F));
		pixmap.fillRectangle(0,0, 1, 1); 
		
		Texture mTexture = new Texture(pixmap);
		 
		pixmap.dispose();
		//Bomberman bomberman = world.getBomberman();
		
		//if(!insideCameraBorder()){
			spriteBatch.draw(mTexture,control.getPosition().x*ppuX, control.getPosition().y*ppuY, WalkingControl.SIZE*ppuX, WalkingControl.SIZE*ppuY);
			//controlRenderer.filledRect(control.getPosition().x, control.getPosition().y, WalkingControl.SIZE, WalkingControl.SIZE);
			*/
		//}
		//else
		//	spriteBatch.draw(mTexture,control.getPosition().x+(bomberman.getPosition().x-CAMERA_WIDTH/2), control.getPosition().y, WalkingControl.SIZE, WalkingControl.SIZE);		
			//controlRenderer.filledRect(control.getPosition().x+(bomberman.getPosition().x-CAMERA_WIDTH/2), control.getPosition().y, WalkingControl.SIZE, WalkingControl.SIZE);
				
		
		/*
		
		controlRenderer.setProjectionMatrix(cam.combined);
		controlRenderer.begin(ShapeType.FilledRectangle);
		controlRenderer.setColor(218F/255, 218F/255, 218F/255, 0F);
		//controlRenderer.setColor(new Color(218F/255, 218F/255, 218F/255, 0F));
		Bomberman bomberman = world.getBomberman();
		
		if(!insideCameraBorder()){

			controlRenderer.filledRect(control.getPosition().x, control.getPosition().y, WalkingControl.SIZE, WalkingControl.SIZE);
			
		}
		else
			controlRenderer.filledRect(control.getPosition().x+(bomberman.getPosition().x-CAMERA_WIDTH/2), control.getPosition().y, WalkingControl.SIZE, WalkingControl.SIZE);
		
		//controlRenderer.filledRect(control.getPosition().x, control.getPosition().y, WalkingControl.SIZE, WalkingControl.SIZE);
		controlRenderer.end();*/
		
	}
	private void drawObjects(){
		for(HiddenObject object: world.getHiddenObjects()){
			//Float d = object.getPosition().x;
			//TextureRegion t = textureRegions.get(object.getName());
			spriteBatch.draw(textureRegions.get(object.getName()), (object.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, object.getPosition().y * ppuY, HiddenObject.SIZE * ppuX, HiddenObject.SIZE * ppuY);
			
		}
	}
	private void drawBooms(){ 
		try{
		for(Boom boom : world.getBooms())
			//if(boom!=null)
			for(BoomPart boomPart: boom.getParts()) 
			  //if(boomPart!=null && boom!=null)	
				//animations.get(Boom.Name).getKeyFrame(boomPart.getAnimationState(),true);
				//float tt =(boomPart.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX;
				//tt = boomPart.getPosition().y * ppuY;
			
				  spriteBatch.draw(animations.get(Boom.Name).getKeyFrame(boomPart.getAnimationState(),true), (boomPart.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, boomPart.getPosition().y * ppuY,BoomPart.SIZE * ppuX, BoomPart.SIZE * ppuY);
		}
		catch(NullPointerException e){
			if(e.getMessage() != null) Log.e("drawBooms", e.getMessage());
			if(e.getLocalizedMessage() != null) Log.e("drawBooms", e.getLocalizedMessage());
		}
			
	}
	
	public void playBoomSound(){
		//musics.get("boom").setVolume(0.7F);
		if(WorldController.soundOn) sounds.get("boom").play(0.7F);
		//musics.put("boom", Gdx.audio.newMusic(Gdx.files.internal("audio/boom.ogg")));
		//musics.put("put-bomb", Gdx.audio.newMusic(Gdx.files.internal("audio/put-bomb.ogg")));
	}
	
	public void playPutingBombSound(){
		//sounds.get("put-bomb").setVolume(0.7F);
		if(WorldController.soundOn) sounds.get("put-bomb").play(0.7F);
	}
	
	public void playFindDoorMusic(){
		foundDoor = true;
		if(!WorldController.soundOn) return;
		stopMainTheme();
		/*if(!musics.get("stage-main-theme-find-the-door").isPlaying()){
			foundDoor = true;
			musics.get("stage-main-theme-find-the-door").setLooping(true);		
			musics.get("stage-main-theme-find-the-door").setVolume(0.7F);
			musics.get("stage-main-theme-find-the-door").play();
			
		}*/
		//sounds.get("stage-main-theme-find-the-door").loop(0.7F);	
		playMainTheme();
	}
	public void disposeSounds(){
		stopMusic();
		sounds.get("up-down").dispose();
		sounds.get("left-right").dispose();
		sounds.get("just-died").dispose();
		sounds.get("dying").dispose();
		sounds.get("boom").dispose();
		sounds.get("put-bomb").dispose();
		sounds.get("stage-main-theme").dispose();
		sounds.get("stage-main-theme-find-the-door").dispose();
	}
	
	public void stopMusic(){
		stopMainTheme();
		
		
		sounds.get("up-down").stop();	
		sounds.get("left-right").stop();		
		sounds.get("just-died").stop();	
		sounds.get("dying").stop();		
		sounds.get("boom").stop();
		sounds.get("put-bomb").stop();
		/*if(musics.get("up-down").isPlaying())
			musics.get("up-down").stop();
		if(musics.get("left-right").isPlaying())
			musics.get("left-right").stop();
		if(musics.get("just-died").isPlaying())
			musics.get("just-died").stop();
		if(musics.get("dying").isPlaying())
			musics.get("dying").stop();
		if(musics.get("boom").isPlaying())
			musics.get("boom").stop();
		if(musics.get("put-bomb").isPlaying())
			musics.get("put-bomb").stop();*/
	}
	public void stopMainTheme(){
		sounds.get("stage-main-theme").stop();
		sounds.get("stage-main-theme-find-the-door").stop();
		/*if(musics.get("stage-main-theme").isPlaying())
			musics.get("stage-main-theme").stop();
		if(musics.get("stage-main-theme-find-the-door").isPlaying())
			musics.get("stage-main-theme-find-the-door").stop();*/
				
	}
	//boolean isPlaying;
	public boolean foundDoor;
	//boolean isDying;
	//boolean isPlayingBackgroundMusic = false;
	
	enum CurrentSound {walking, dying, dying2, none, won};
	CurrentSound currentSound;
	
	//public boolean findDoorMusic;
	
	public void playMainTheme(){
		if(WorldController.soundOn){
		if(foundDoor)
			sounds.get("stage-main-theme-find-the-door").loop(0.6F);	
		else
			sounds.get("stage-main-theme").loop(1F);
		}
	}
	
	private void playMusic(){
		if(!WorldController.soundOn) return;
		Bomberman bomberman = world.getBomberman();
		if(bomberman.getState() ==Bomberman.State.WON){
			if(!(currentSound == CurrentSound.won)){
				stopMusic();
				currentSound = CurrentSound.won;
				sounds.get("level-complete").play(0.8F);
			}
		}
		if(currentSound == CurrentSound.won) return;
		/*if(!musics.get("stage-main-theme").isPlaying() && !foundDoor){
			//stopMainTheme();
			musics.get("stage-main-theme").setLooping(true);	
			musics.get("stage-main-theme").setVolume(1);
			musics.get("stage-main-theme").play();
		
			//isPlayingBackgroundMusic = true;
		}*/
		if(bomberman.getState() ==Bomberman.State.DYING){
			if(!(currentSound == CurrentSound.dying)){
				stopMusic();
				currentSound = CurrentSound.dying;
				sounds.get("dying").play(0.8F);
			}
			//if(!isDying){
			//sounds.get("dying").play();
			//	isDying = true;
			//}
			//else
			//	if(!musics.get("dying").isPlaying())
			//	{
			//		bomberman.setState(Bomberman.State.DYING2);
			//		musics.get("just-died").play();
					
					
			//	}
		}
		if(bomberman.getState() ==Bomberman.State.DYING2){
			if(!(currentSound == CurrentSound.dying2)){
				currentSound = CurrentSound.dying2;
				sounds.get("just-died").play(0.8F);
			}
			//bomberman.setState(Bomberman.State.DYING2);
			stopMainTheme();
			//sounds.get("just-died").play();
			/*
			if(!musics.get("just-died").isPlaying()){
				//musics.get("stage-main-theme").stop();
				bomberman.setState(Bomberman.State.DEAD);
			}*/
		}
		if(bomberman.getState() ==Bomberman.State.DEAD ||bomberman.getState() == Bomberman.State.WON )
			stopMainTheme();
		/*if(bomberman.getState() ==Bomberman.State.DYING2){
			if(!musics.get("just-died").isPlaying()){
				musics.get("stage-main-theme").stop();
				bomberman.setState(Bomberman.State.DEAD);
			}
		}*/
		//Log.e("tate", bomberman.getState().toString());
		if(bomberman.getState() == Bomberman.State.WALKING){
			if(! (currentSound == CurrentSound.walking))
			//if(!isPlaying)
			{
				//musics.get("left-right").stop();
				//musics.get("up-down").stop();
				//musics.get("left-right").stop();
				//isPlaying = true;
				currentSound = CurrentSound.walking;
				
				
				if((bomberman.getDirection(Bomberman.Direction.LEFT) || bomberman.getDirection(Bomberman.Direction.RIGHT)) &&
						!(bomberman.getDirection(Bomberman.Direction.UP) || bomberman.getDirection(Bomberman.Direction.DOWN))){
					//sounds.get("left-right").play();//.loop();
					sounds.get("left-right").loop(0.5F);
					//sounds.get("left-right").setVolume(0.5F);
					//sounds.get("left-right").setLooping(true);
				}
				if((bomberman.getDirection(Bomberman.Direction.UP) || bomberman.getDirection(Bomberman.Direction.DOWN))){
					//sounds.get("up-down").play(0.5F);//.loop();
					sounds.get("up-down").loop(0.5F);
					//sounds.get("up-down").setVolume(0.5F);
					//sounds.get("up-down").setLooping(true);
				}
				//if((bomberman.getDirection(Bomberman.Direction.LEFT) || bomberman.getDirection(Bomberman.Direction.RIGHT)) && (bomberman.getDirection(Bomberman.Direction.UP) || bomberman.getDirection(Bomberman.Direction.DOWN)))
				//	musics.get("double-direction").loop();
				
			}
			
		}
		else{
			if(currentSound == CurrentSound.walking){
			//if(isPlaying){
				//isPlaying = false;
				currentSound = CurrentSound.none;
				sounds.get("left-right").stop();
				sounds.get("up-down").stop();
				//musics.get("double-direction").stop();
			
			}
		}
		
		
	}

	private void drawBombs() {
		for(Bomb bomb : world.getBombs()){
			//npc.getVelocity().y =-3F;	
			
			spriteBatch.draw(animations.get(Bomb.Name).getKeyFrame(bomb .getAnimationState(),true), (bomb.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, bomb.getPosition().y * ppuY, Bomb.SIZE * ppuX,  Bomb.SIZE * ppuY);
			
			//spriteBatch.draw(bomb.getCurrentFrame()/*textures.get(npc.getName())*/, (bomb.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, bomb.getPosition().y * ppuY, Bomb.SIZE * ppuX,  Bomb.SIZE * ppuY);
			//spriteBatch.draw(textures.get(npc.getName()), 2, 2, NpcBase.SIZE * ppuX, NpcBase.SIZE * ppuY);
		}
	}
	
	private void drawNpcs() {
		for(NpcBase npc : world.getNpcs()){
			//npc.getVelocity().y =-3F;	
			
			//spriteBatch.draw(npc.getCurrentFrame()/*textures.get(npc.getName())*/, (npc.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, npc.getPosition().y * ppuY, NpcBase.SIZE * ppuX, NpcBase.SIZE * ppuY);
			spriteBatch.draw(animations.get(npc.getName()).getKeyFrame(npc.getAnimationState(), true), (npc.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, npc.getPosition().y * ppuY, NpcBase.SIZE * ppuX, NpcBase.SIZE * ppuY);
			
			//spriteBatch.draw(textures.get(npc.getName()), 2, 2, NpcBase.SIZE * ppuX, NpcBase.SIZE * ppuY);
		}
	}
	private void drawBricks() {
		for (BrickBase block : world.getBlocks()) {
			if(block.getName() == HardBrick.Name)
				//spriteBatch.draw(textures.get(block.getName()), (block.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, block.getPosition().y * ppuY, BrickBase.SIZE * ppuX, BrickBase.SIZE * ppuY);
				//Log.e("draw", block.getName() +" - "+ HardBrick.Name);
				spriteBatch.draw(textureRegions.get("HardBrick"), (block.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, block.getPosition().y * ppuY, BrickBase.SIZE * ppuX, BrickBase.SIZE * ppuY);
			
				
			//spriteBatch.draw(blockTexture, (block.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, block.getPosition().y * ppuY, BrickBase.SIZE * ppuX, BrickBase.SIZE * ppuY);
			//spriteBatch.draw(HardBrick.texture, (block.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, block.getPosition().y * ppuY, BrickBase.SIZE * ppuX, BrickBase.SIZE * ppuY);
			
			if(block.getName() == Brick.Name)
				spriteBatch.draw(animations.get(Brick.Name).getKeyFrame(block.getAnimationState(),true), (block.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, block.getPosition().y * ppuY, BrickBase.SIZE * ppuX, BrickBase.SIZE * ppuY);
				//spriteBatch.draw(block.getCurrentFrame(), (block.getPosition().x - cam.position.x+CAMERA_WIDTH/2)* ppuX, block.getPosition().y * ppuY, BrickBase.SIZE * ppuX, BrickBase.SIZE * ppuY);
			
		}
	}

	/*private void ShowPos(){
		
		Bomberman bomberman = world.getBomberman();
		SpriteBatch spriteBatch = new SpriteBatch();
		spriteBatch.begin();
		BitmapFont font = new BitmapFont();
		 CharSequence str = Float.toString(bomberman.getPosition().x)+" - "+Float.toString(oldX);
		 font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		 font.draw(spriteBatch, str, 125, 260);
		 spriteBatch.end();
	}*/
	float oldX = 0;
	private void drawBomberman() {
		
		
		Bomberman bomberman = world.getBomberman();
		
		//if (!bomberman.isDead) {
			//if (vedroid.isFacingLeft()){
			//	spriteBatch.draw(bobTexture, vedroid.getPosition().x * ppuX, vedroid.getPosition().y * ppuY, vedroid.WIDTH * ppuX, vedroid.HEIGHT * ppuY);
			//}else{
			//ShowPos();
			//cam.position.x - renderer.CAMERA_WIDTH/2
					
			//	spriteBatch.draw(bobTexture, (bomberman.getPosition().x- (bomberman.getPosition().x-CAMERA_WIDTH/2))* ppuX, bomberman.getPosition().y * ppuY, Bomberman.WIDTH * ppuX,Bomberman.HEIGHT * ppuY);			
			
			/*
			 //cool
			spriteBatch.draw(bomberman.texture, 
					(bomberman.getPosition().x- (bomberman.getPosition().x-CAMERA_WIDTH/2))* ppuX, bomberman.getPosition().y * ppuY, 
					Bomberman.WIDTH * ppuX,Bomberman.HEIGHT * ppuY);		*/	
			if(world.invincibility)
				spriteBatch.setColor(0.9f, 0.9f, 0.9f, 0.7f);
 
			if(!insideCameraBorder()){
				float fromX = 0;
				if(!(bomberman.getPosition().x < world.width/2))
					fromX = world.width - CAMERA_WIDTH;
				spriteBatch.draw(animations.get("bomberman").getKeyFrame(bomberman.getAnimationState(),true), 
						(bomberman.getPosition().x-fromX)* ppuX , bomberman.getPosition().y * ppuY, 
						Bomberman.SIZE * ppuX,Bomberman.SIZE * ppuY);
			}
			else
				spriteBatch.draw(animations.get("bomberman").getKeyFrame(bomberman.getAnimationState(),true), 
					(bomberman.getPosition().x- (bomberman.getPosition().x-CAMERA_WIDTH/2))* ppuX, bomberman.getPosition().y * ppuY, 
					Bomberman.SIZE * ppuX,Bomberman.SIZE * ppuY);
			spriteBatch.setColor(1f, 1f, 1f, 1f);
			//}
		//}
		//else {
		//	spriteBatch.draw(bobDeadTexture, bob.getPosition().x * ppuX, vedroid.getPosition().y * ppuY, Bob.WIDTH * ppuX, Bob.HEIGHT * ppuY);
		//}
	}

	
	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		for (BrickBase block : world.getBlocks()) {
			Rectangle rect = block.getBounds();
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		// render bomberman
		float fromX = 0;
		Bomberman bomberman = world.getBomberman();
		Rectangle rect = bomberman.getBounds();
		float x1 = bomberman.getPosition().x;// + rect.x;
		float y1 = bomberman.getPosition().y;// + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		//debugRenderer.rect(x1, y1, rect.width, rect.height);
		
		if(!insideCameraBorder()){
			//if(!(bomberman.getPosition().x < world.width/2))
			//	fromX = world.width - CAMERA_WIDTH;
			//else
			//	fromX = world.width - 1.5F*CAMERA_WIDTH;
			debugRenderer.rect(x1+fromX, y1, rect.width, rect.height);
		}
		else
			//if(!(bomberman.getPosition().x > world.width/2))
				debugRenderer.rect(x1, y1, rect.width, rect.height);
		
		
		
		// render npcs
		
	
		
		for (NpcBase npc: world.getNpcs()) {
			Rectangle rectnpc = npc.getBounds();
			float x1npc = npc.getPosition().x;// + rect.x;
			float y1npc = npc.getPosition().y;// + rect.y;
			debugRenderer.setColor(new Color(255F/256,255F/256, 0, 1));
			debugRenderer.rect(x1npc, y1npc, rectnpc.width, rectnpc.height);
		}
		
		WalkingControlArrows control = world.getWalkingControlArrows();
		
		debugRenderer.setColor(new Color(36F/256,36F/256, 200F/256, 1));
		debugRenderer.rect(control.positionLeft.x, control.positionLeft.y, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient);
		
		debugRenderer.rect(control.positionRight.x, control.positionRight.y, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient);
		
		debugRenderer.rect(control.positionUp.x, control.positionUp.y, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient);
		
		debugRenderer.rect(control.positionDown.x, control.positionDown.y, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient, WalkingControlArrows.BSIZE*WalkingControlArrows.Coefficient);
		
	
		
		
		debugRenderer.end();
	}
}

