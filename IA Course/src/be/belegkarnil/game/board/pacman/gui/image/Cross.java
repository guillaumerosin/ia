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
import java.awt.SystemTray;

public class Cross extends Box{
	private final boolean topClosed,bottomClosed,leftClosed,rightClosed;
	
	public Cross(boolean topClosed, boolean bottomClosed, boolean leftClosed, boolean rightClosed){
		this.topClosed		= topClosed;
		this.bottomClosed	= bottomClosed;
		this.leftClosed	= leftClosed;
		this.rightClosed	= rightClosed;
		update();
	}
	public Cross(){
		this(false,false,false,false);
	}
	
	private void drawHorizontal(Graphics2D g){
		int x = 0, width = Constants.BOX_SIZE;
		final int yup		= Constants.CENTER - ((Constants.WALL_SPACE + Constants.WALL_WEIGHT) >> 1);
		final int ydown	=  Constants.CENTER + (Constants.WALL_SPACE >> 1);
		
		if(leftClosed){
			x		+= yup;
			width	-= yup;
		}
		if(rightClosed) width -= yup;
		g.fillRect(x,yup,width,Constants.WALL_WEIGHT);
		g.fillRect(x,ydown,width,Constants.WALL_WEIGHT);
	}
	private void drawVertical(Graphics2D g) {
		int y=0, height = Constants.BOX_SIZE;
		final int xleft	= Constants.CENTER - ((Constants.WALL_SPACE + Constants.WALL_WEIGHT) >> 1);
		final int xright	= Constants.CENTER + (Constants.WALL_SPACE >> 1);
		if(topClosed){
			y			+= xleft;
			height	-= xleft;
		}
		if(bottomClosed) height	-= xleft;
		
		g.fillRect(xleft,y,Constants.WALL_WEIGHT,height);
		g.fillRect(xright,y,Constants.WALL_WEIGHT,height);
	}
	
	@Override
	protected void paintComponent(Graphics2D g) {
		g.setColor(Constants.WALL_COLOR);
		drawHorizontal(g);
		drawVertical(g);
		
		final int CENTER = Constants.CENTER -  (Constants.WALL_SPACE >> 1);
		final int half_weight = Constants.WALL_WEIGHT >> 1;
		final int double_weight = Constants.WALL_WEIGHT << 1;
		
		if(!leftClosed){
			g.clearRect(CENTER - Constants.WALL_WEIGHT, CENTER + half_weight,  double_weight, Constants.WALL_SPACE - half_weight);
		}
		if(!rightClosed){
			g.clearRect(CENTER - Constants.WALL_WEIGHT + Constants.WALL_SPACE, CENTER + half_weight,  double_weight, Constants.WALL_SPACE - half_weight);
		}
		
		if(!topClosed) {
			g.clearRect(CENTER + half_weight, CENTER - Constants.WALL_WEIGHT, Constants.WALL_SPACE - half_weight, double_weight);
		}
		if(!bottomClosed){
			g.clearRect(CENTER + half_weight, CENTER - Constants.WALL_WEIGHT + Constants.WALL_SPACE, Constants.WALL_SPACE - half_weight, double_weight);
		}
	}
}
