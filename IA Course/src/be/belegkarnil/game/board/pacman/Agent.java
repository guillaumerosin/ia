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

import be.belegkarnil.game.board.pacman.event.GameListener;

import java.util.Arrays;

public abstract class Agent implements Behavior{
	private final int initY;
	private final int initX;
	
	public static enum Action{
		UP(0,-1),
		DOWN(0,1),
		LEFT(-1,0),
		RIGHT(1,0);
		public final int dx,dy;
		private Action(int dx,int dy){
			this.dx	= dx;
			this.dy	= dy;
		}
	};
	private final String name;
	private int x,y;
	public Agent(final int initX, final int initY){
		this.name	= getClass().getSimpleName();
		this.x		= initX;
		this.y		= initY;
		this.initX	= initX;
		this.initY	= initY;
	}
	public String getName(){
		return name;
	}
	void setLocation(int x,int y){
		this.x	= x;
		this.y	= y;
	}
	void move(Action action){
		if(action == null) return;
		this.x += action.dx;
		this.y += action.dy;
	}
	public int getX(){ return x; }
	public int getY(){ return y; }
	int getInitialX(){ return initX; }
	int getInitialY(){ return initY; }
	
	protected static int stateToXCoordinate(int state, int width){
		return state % width;
	}
	protected static int stateToYCoordinate(int state, int width){
		return state / width;
	}
	protected static int coordinateToState(int x, int y, int width){
		return y*width+x;
	}
	
	protected static int countState(Board board){
		return board.getWidth() * board.getHeight();
	}
	
	protected int[][] boardToMatrix(Board board) {
		final int nStates = countState(board);
		final int width = board.getWidth();
		final int height = board.getHeight();
		int[][] matrix = new int[nStates][nStates];
		for (int[] row : matrix)
			Arrays.fill(row, 0);
		
		int x, y, newX, newY, newState;
		for (int state = 0; state < nStates; state++) {
			x = stateToXCoordinate(state, width);
			y = stateToYCoordinate(state, width);
			for (Action action : Action.values()) {
				if (board.canWalk(x, y, action)) {
					newX = (x + action.dx + width) % width;
					newY = (y + action.dy + height) % height;
					newState = coordinateToState(newX, newY, width);
					matrix[state][newState] = 1; // TODO use significant value ?
				}
			}
		}
		return matrix;
	}
	void respawn(){
		this.x = initX;
		this.y = initY;
	}
	protected GameListener wantsToListen(){
		return null;
	}
}
