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

import java.awt.Color;
import java.awt.Graphics2D;

public class Ghost extends Box{
	private final Color color,eyes;
	private final Agent.Action direction;
	private final boolean vulnerable;
	
	public Ghost(Color color, Agent.Action direction){
		this(color,direction,false);
	}
	public Ghost(Color color, Agent.Action direction, boolean vulnerable){
		super(Constants.AGENT_GROUND_COLOR);
		this.color			= vulnerable ? Constants.VULNERABLE_COLOR : color;
		this.eyes			= vulnerable ? Constants.VULNERABLE_EYE_COLOR : Constants.EYES_COLOR;
		this.direction 	= direction;
		this.vulnerable	= vulnerable;
		update();
	}
	
	
	private int drawCamel(Graphics2D g, final int y){
		final int WIDTH = 4;
		final int WEIGHT = 4;
		drawCamelPart(g,Constants.CENTER-(WIDTH<<1),y, WIDTH, WEIGHT);
		drawCamelPart(g,Constants.CENTER,y, WIDTH, WEIGHT);
		return drawCamelPart(g,Constants.CENTER+(WIDTH<<1),y, WIDTH, WEIGHT);
	}
	
	private int drawCamelPart(Graphics2D g, int x, int y, final int WIDTH, final int WEIGHT){
		final int HALF_WIDTH		= WIDTH >>1;
		final int HALF_WEIGHT	= WEIGHT >> 1;
		
		g.fillRect(x-HALF_WIDTH,y-HALF_WEIGHT,WIDTH,WEIGHT);
		g.fillRect(x-HALF_WIDTH -HALF_WIDTH,y+HALF_WEIGHT,HALF_WIDTH,WEIGHT);
		g.fillRect(x+HALF_WIDTH,y+HALF_WEIGHT,HALF_WIDTH,WEIGHT);
		
		return y+HALF_WEIGHT;
	}
	
	@Override
	protected void paintComponent(Graphics2D g) {
		if(color == null) return;
		g.setColor(color);
		drawMainShape(g);
		
		if(vulnerable){
			g.setColor(eyes);
			final int shift = 1;
			int x1 = Constants.CENTER - Constants.QUARTER_CENTER-shift;
			int x2 = Constants.CENTER + Constants.QUARTER_CENTER+shift;
			int y = 16;
			fillCircle(g,x1,y,Constants.GHOST_VULNERABLE_EYE_RADIUS);
			fillCircle(g,x2,y,Constants.GHOST_VULNERABLE_EYE_RADIUS);
			drawCamel(g,Constants.BOX_SIZE - Constants.HALF_CENTER);
		}else {
			g.setColor(Constants.SCLEROTIC_COLOR);
			final int shift = 1;
			int x1 = Constants.CENTER - Constants.QUARTER_CENTER-shift;
			int x2 = Constants.CENTER + Constants.QUARTER_CENTER+shift;
			int y = 16;
			fillCircle(g,x1,y,Constants.SCLEROTIC_RADIUS);
			fillCircle(g,x2,y,Constants.SCLEROTIC_RADIUS);
			
			final int iris_shift = 4;
			if(direction != null) {
				switch (direction) {
					case UP:
						y -= iris_shift;
						break;
					case DOWN:
						y += iris_shift;
						break;
					case LEFT:
						x1 -= iris_shift;
						x2 -= iris_shift;
						break;
					case RIGHT:
						x1 += iris_shift;
						x2 += iris_shift;
						break;
				}
			}
			g.setColor(eyes);
			fillCircle(g,x1,y,Constants.GHOST_EYE_RADIUS);
			fillCircle(g,x2,y,Constants.GHOST_EYE_RADIUS);
		}
	}
	
	private void drawMainShape(Graphics2D g) {
		fillCircle(g,Constants.CENTER, Constants.CENTER,Constants.GHOST_RADIUS);
		final int ymax = drawCamel(g,Constants.BOX_SIZE - Constants.QUARTER_CENTER);
		final int xstart = findColumnFirstColor(color.getRGB(),ymax)-2;
		
		final int[] x = new int[4];
		final int[] y = new int[4];
		
		x[1] = Constants.CENTER-Constants.GHOST_RADIUS;
		x[0] = Constants.CENTER+Constants.GHOST_RADIUS;
		y[0] = y[1] = Constants.CENTER;
		
		x[2] = xstart;
		x[3] = Constants.BOX_SIZE-xstart;
		y[2] = y[3] = ymax;
		
		g.fillPolygon(x,y,x.length);
	}
	
	private int findColumnFirstColor(final int color, int ymax) {
		for(int x=0; x<Constants.BOX_SIZE; x++)
			if(getRGB(x,ymax) == color)
				return x;
		return -1;
	}
}
