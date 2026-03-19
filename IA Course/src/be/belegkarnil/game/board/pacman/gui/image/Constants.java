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

public final class Constants {
	public static final Color WALL_COLOR = new Color(76,76,162);
	public static final Color GROUND_COLOR = Color.BLACK;
	public static final Color PACMAN_COLOR = new Color(255,255,54);
	public static final Color DOT_COLOR = new Color(255,184,174);
	public static final Color VULNERABLE_COLOR = new Color(33,33,255);
	public static final Color VULNERABLE_EYE_COLOR = new Color(255,184,174);
	public static final Color EYES_COLOR = new Color(33,33,255);
	public static final int PACMAN_MOUTH = 20;
	public static final int PACMAN_HALF_MOUTH = PACMAN_MOUTH >> 1;
	public static final long SLOWDOWN_DURATION_MILIS = 300;
	public static Color AGENT_GROUND_COLOR = new Color(0,0,0,0);
	public static Color SCLEROTIC_COLOR = Color.WHITE;
	public static int GHOST_EYE_RADIUS = 3;
	public static int GHOST_VULNERABLE_EYE_RADIUS = 4;
	public static int SCLEROTIC_RADIUS = 6;
	public static final int WALL_SPACE	= 20;
	public static final int WALL_WEIGHT	= 4;
	public static final int BOX_SIZE = 48;
	public static final int POWER_PELLET_RADIUS = 10;
	public static final int DOT_RADIUS	= 4;
	public static final int CENTER	= BOX_SIZE >> 1;
	public static final int HALF_CENTER	= CENTER >> 1;
	public static final int QUARTER_CENTER	= CENTER >> 2;
	public static final int PACMAN_RADIUS = 18;
	public static final int GHOST_RADIUS = 16;
}
