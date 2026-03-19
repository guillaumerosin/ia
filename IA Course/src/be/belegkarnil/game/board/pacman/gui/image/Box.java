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
import java.awt.image.BufferedImage;

public abstract class Box extends BufferedImage {
	private final Color BACKGROUND_COLOR;
	protected Box(Color bgColor) {
		super(Constants.BOX_SIZE, Constants.BOX_SIZE, TYPE_INT_ARGB);
		this.BACKGROUND_COLOR = bgColor;
		update();
	}
	public Box() {
		this(Constants.GROUND_COLOR);
	}
	public void update(){
		Graphics2D g = createGraphics();
		g.setBackground(BACKGROUND_COLOR);
		g.clearRect(0,0,Constants.BOX_SIZE,Constants.BOX_SIZE);
		paintComponent(g);
	}
	void fillCircle(Graphics2D g, int x, int y, int r){
		g.fillOval(x-r,y-r,r << 1,r << 1);
	}
	protected abstract void paintComponent(Graphics2D g);
}
