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
package be.belegkarnil.game.board.pacman.ghost;

import be.belegkarnil.game.board.pacman.Ghost;

import java.awt.Color;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Clyde extends Ghost {
	public Clyde(final int initX,final int initY){
		super(new Color(255,184,81),initX,initY);
	}
	
	@Override
	protected Action action(final Node self, final Node pacman){
		Queue<Node> frontiers = new LinkedList<Node>();
		frontiers.offer(self);

		Map<Node,Node> preds = new HashMap<Node,Node>();
		preds.put(self, null);

		while(!frontiers.isEmpty()){
			Node current = frontiers.poll();

			if(current.equals(pacman)){
				List<Node> path = resolve(preds, pacman);
				if(path.size() < 2) return null;
				Node src = path.get(0);
				Node dst = path.get(1);

				for(Action action : Action.values()){
					if(src.hasNeighbour(action) && src.getNeighbour(action).equals(dst)){
						return action;
					}
				}
				return null;
			}

			for(Action action : Action.values()){
				if(current.hasNeighbour(action)){
					Node neighbour = current.getNeighbour(action);
					if(!preds.containsKey(neighbour)){
						frontiers.offer(neighbour);
						preds.put(neighbour, current);
					}
				}
			}
		}
		return Action.values()[(int)(Math.random() * Action.values().length)];
	}
	private static List <Node> resolve(Map<Node,Node> preds, Node dst){
		//TODO
		LinkedList<Node> path = new LinkedList<Node>();
		path.add(dst);

		Node current = dst;

		while(preds.get(current) != null){
			current = preds.get(current);
			path.addFirst(current);
		}
		return path;
	}
}
