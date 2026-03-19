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
package be.belegkarnil.game.board.pacman;

import java.util.Arrays;

public class Board {
	private final Cell[][] data;
	Board(Cell[][] level){
		this.data = new Cell[level.length][];
		for(int i=0; i<level.length; i++)
			this.data[i] = Arrays.copyOf(level[i],level[i].length);
	}
	public boolean canWalk(Agent agent, Agent.Action action) {
		return canWalk(agent.getX(), agent.getY(),action);
	}
	public boolean canWalk(int x, int y, Agent.Action action) {
		x = (x + action.dx + getWidth()) % getWidth();
		y = (y + action.dy + getHeight()) % getHeight();
		switch (this.data[y][x]){
			case HORIZONTAL_WALL:
			case VERTICAL_WALL:
			case WEST_ENDING:
			case EAST_ENDING:
			case NORTH_ENDING:
			case SOUTH_ENDING:
			case CROSS:
			case NORTHEAST_CORNER:
			case NORTHWEST_CORNER:
			case SOUTHEAST_CORNER:
			case SOUTHWEST_CORNER:
			case NORTH_T_CROSS:
			case EAST_T_CROSS:
			case WEST_T_CROSS:
			case SOUTH_T_CROSS: return false;
			default: return true;
		}
	}
	
	public int getWidth() {
		return this.data[0].length;
	}
	public int getHeight() {
		return this.data.length;
	}
	
	public Cell getCellAt(int x, int y) {
		return this.data[y][x];
	}
	public Cell getCellAt(final Agent agent) {
		return getCellAt(agent.getX(),agent.getY());
	}
	void eat(final Agent agent) {
		this.data[agent.getY()][agent.getX()] = Cell.EMPTY;
	}
	
	public static enum Cell{
		EMPTY,
		HORIZONTAL_WALL,
		VERTICAL_WALL,
		HORIZONTAL_CLOSED_WALL,
		VERTICAL_CLOSED_WALL,
		WEST_ENDING,
		EAST_ENDING,
		NORTH_ENDING,
		SOUTH_ENDING,
		CROSS,
		NORTHEAST_CORNER,
		NORTHWEST_CORNER,
		SOUTHEAST_CORNER,
		SOUTHWEST_CORNER,
		NORTH_T_CROSS,
		EAST_T_CROSS,
		WEST_T_CROSS,
		SOUTH_T_CROSS,
		DOT,
		POWER_PELLET;
	}
}