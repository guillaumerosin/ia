/*
 *  Copyright 2024 Belegkarnil
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the “Software”), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 *  so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package be.belegkarnil.game.board.pacman.gui.image;

import java.awt.Color;
import java.awt.Graphics2D;

public class VerticalWall extends Box{
	
	private final boolean top, bottom;
	public VerticalWall(){
		this(false,false);
	}
	public VerticalWall(boolean top, boolean bottom){
		this.top		= top;
		this.bottom	= bottom;
		update();
	}
	@Override
	protected void paintComponent(Graphics2D g) {
		final int SPACE = Constants.WALL_SPACE;
		final int WEIGHT =  Constants.WALL_WEIGHT;
		final int CENTER = Constants.CENTER;
		final int SIZE		= Constants.BOX_SIZE;
		int y			= 0;
		int height	= SIZE;
		g.setColor(Constants.WALL_COLOR);
		
		if(top){
			y		 = 15;
			height -= 15;
			fillCircle(g,CENTER,y,WEIGHT + (SPACE >> 1) );
			g.setColor(Constants.GROUND_COLOR);
			fillCircle(g,CENTER,y, (SPACE >> 1) );
			g.clearRect(0,y,SIZE,WEIGHT + (SPACE >> 1));
			g.setColor(Constants.WALL_COLOR);
		}
		if(bottom) {
			height -= 15;
			fillCircle(g,CENTER,SIZE-15,WEIGHT + (SPACE >> 1) );
			g.setColor(Constants.GROUND_COLOR);
			fillCircle(g,CENTER,SIZE-15, (SPACE >> 1) );
			g.clearRect(0, SIZE-15-WEIGHT - (SPACE >> 1),SIZE,WEIGHT + (SPACE >> 1));
			g.setColor(Constants.WALL_COLOR);
		}
		
		g.fillRect(CENTER - ((SPACE + WEIGHT) >> 1),y,WEIGHT,height);
		g.fillRect(CENTER + (SPACE >> 1),y,WEIGHT,height);
	}
}
