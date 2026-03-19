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

import be.belegkarnil.game.board.pacman.Agent;
import org.w3c.dom.css.Rect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Arrays;

public class Pacman extends Box{
	private final Agent.Action direction;
	
	public Pacman(Agent.Action direction){
		super(Constants.AGENT_GROUND_COLOR);
		this.direction = direction;
		update();
	}
	
	@Override
	protected void paintComponent(Graphics2D g) {
		g.setColor(Constants.PACMAN_COLOR);
		fillCircle(g,Constants.CENTER, Constants.CENTER,Constants.PACMAN_RADIUS);
		
		if(direction == null) return;
		g.setColor(Color.GREEN);
		
		final int[] x = {Constants.BOX_SIZE,Constants.BOX_SIZE,Constants.CENTER};
		final int[] y = {Constants.BOX_SIZE,Constants.BOX_SIZE,Constants.CENTER};
		
		switch (this.direction){
			case UP:
				y[0] = 0;
				y[1] = 0;
			case DOWN:
				x[0] = Constants.CENTER - Constants.PACMAN_HALF_MOUTH;;
				x[1] = x[0] + Constants.PACMAN_MOUTH;
				break;
			case LEFT:
				x[0] = 0;
				x[1] = 0;
			case RIGHT:
				y[0] = Constants.CENTER - Constants.PACMAN_HALF_MOUTH;
				y[1] = y[0] + Constants.PACMAN_MOUTH;
				break;
		}
		
		g.fillPolygon(x,y,x.length);
		
		int xmin = x[0], xmax = x[0];
		int ymin = y[0], ymax = y[0];
		
		for(int i=1; i<x.length;i++){
			xmin = Math.min(xmin,x[i]);
			xmax = Math.max(xmax,x[i]);
			ymin = Math.min(ymin,y[i]);
			ymax = Math.max(ymax,y[i]);
		}
		clear(new Rectangle(xmin,ymin,xmax-xmin, ymax-ymin),Color.GREEN.getRGB());
	}
	public void clear(final Rectangle area, final int mask){
		final int color = Constants.AGENT_GROUND_COLOR.getRGB();
		for(int x=area.x; x<area.x + area.width; x++){
			for(int y=area.y; y<area.y + area.height; y++){
				if(getRGB(x,y) == mask){
					setRGB(x,y,color);
				}
			}
		}
	}
}
