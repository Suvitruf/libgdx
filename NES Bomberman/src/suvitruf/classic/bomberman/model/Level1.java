/*******************************************************************************
 * Copyright (c) [2013], [Apanasik Andrey]
 * http://suvitruf.ru/
 * https://github.com/Suvitruf/libgdx/tree/master/NES%20Bomberman
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *   Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 *   Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 * 
 *   Neither the name of the {organization} nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
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
