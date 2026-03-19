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

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Ghost extends Agent implements Comparator<Ghost.Node> {
	public static class Node{
		public final int row;
		public final int column;
		public final Board.Cell type;
		private int cost;
		private final Map<Action,Node> neighbours;
		private Node(final int row, final int column, final Board.Cell type) {
			this.row				= row;
			this.column			= column;
			this.type			= type;
			this.cost			= Integer.MAX_VALUE;
			this.neighbours	= new HashMap<>(Action.values().length);
		}
		public int getCost(){
			return this.cost;
		}
		public void setCost(int newCost){
			this.cost = newCost;
		}
		private void addNeighbour(final Action action,final Node neighbour){
			this.neighbours.put(action,neighbour);
		}
		public Node getNeighbour(final Action action){
			return this.neighbours.get(action);
		}
		public boolean hasNeighbour(final Action action){
			return this.neighbours.containsKey(action);
		}
		@Override
		public boolean equals(Object node){
			if(node instanceof Node) return equals((Node)node);
			return super.equals(node);
		}
		public boolean equals(Node node){
			return row == node.row && column == node.column;
		}
		@Override
		public int hashCode(){
			return Integer.valueOf(row*LevelFactory.MAX_ROWS+column).hashCode();
		}
	}
	private final Color color;
	private boolean vulnerable;
	public Ghost(Color color,int initX, int initY){
		super(initX,initY);
		this.color	= color;
		this.vulnerable	= false;
	}
	public final boolean isVulnerable(){
		return vulnerable;
	}
	public Color getColor(){
		return color;
	}
	public Action action(final Board board, final Pacman pacman){
		final int rows = board.getHeight();
		final int columns = board.getWidth();
		Node[][] nodes = new Node[rows][columns];
		
		for(int y=0; y<rows;y++){
			for(int x=0; x<columns;x++){
				nodes[y][x] = new Node(y,x,board.getCellAt(x,y));
			}
		}
		int nx, ny;
		for(int y=0; y<rows;y++){
			for(int x=0; x<columns;x++){
				for(Action action:Action.values()) {
					nx = (x + action.dx + columns) % columns;
					ny = (y + action.dy + rows) % rows;
					if (board.canWalk(x, y, action))
						nodes[y][x].addNeighbour(action, nodes[ny][nx]);
				}
			}
		}
		return action(nodes[getY()][getX()],nodes[pacman.getY()][pacman.getX()]);
	}
	protected abstract Action action(final Node self, final Node pacman);
	
	void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}
	
	@Override
	void respawn(){
		super.respawn();
		this.vulnerable = false;
	}
	@Override
	public int compare(Node a, Node b) {
		return a.getCost()-b.getCost();
	}
}
