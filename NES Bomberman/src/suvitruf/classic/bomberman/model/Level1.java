package suvitruf.classic.bomberman.model;



import com.badlogic.gdx.math.Vector2;

public class Level1 extends LevelBase {
	 
	public Level1(int w, int h) {
		super(w, h);
		createLevel();
	}
	
	public void putBomb(int x, int y , boolean auto, int length){
		boolean exist = false;
		for (Bomb bomb : bombs) {
			if(bomb.position.x == x && bomb.position.y == y)
			{
				exist = true;
			}
		}
		
		if(!exist){
			bombs.add(new Bomb(new Vector2(x, y),auto,length));
			bomberman.isInsideBomb = true;
		}
	}
	public void createLevel() {
		
			
		/*bomberman = new Bomberman(this, new Vector2(1.1F, 1.1F));

		
		for (int i = 0; i < width-1; i++) {			
			blocks.add(new HardBrick(new Vector2(i, 0)));
			blocks.add(new HardBrick(new Vector2(i, height-2)));
		}
		for (int i = 1; i < height-1; i++) {
			
			blocks.add(new HardBrick(new Vector2(0, i)));
			blocks.add(new HardBrick(new Vector2(width-1, i)));
		}
		
		for (int i = 2; i < width-1; i++) {
			for (int j = 1; j< height-1; j++) {
				if(i%2==0 && j%2 == 0)
					blocks.add(new HardBrick(new Vector2(i, j)));
			}
		}
		

		
		int bricksCount = 6;// (int)((width-2)*(height-2)/5);
		generateBricks(bricksCount);
		generateBallooms(3);*/
		
	}
	
	
}
