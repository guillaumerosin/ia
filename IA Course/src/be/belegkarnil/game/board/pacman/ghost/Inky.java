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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Inky extends Ghost {
	public Inky(final int initX,final int initY){
		super(new Color(0,255,255),initX,initY);
	}

	private int heuristic(Node node, Node pacman){
		return Math.abs(node.row - pacman.row) + Math.abs(node.column - pacman.column);
	}

	@Override
	protected Action action(final Node self, final Node pacman){
		PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.getCost() - b.getCost());
		self.setCost(heuristic(self, pacman)); 
		pq.offer(self);

		Set<Node> visited = new HashSet<>();
		Map<Node, Action> firstActions = new HashMap<>();
		Map<Node, Integer> gCost = new HashMap<>(); 
		gCost.put(self, 0);

		while(!pq.isEmpty()){
			Node current = pq.poll();

			if(visited.contains(current)) continue;
			visited.add(current);

			if(current.equals(pacman)){
				return firstActions.get(current);
			}

			int g = gCost.get(current);

			for(Action action : Action.values()){
				if(!current.hasNeighbour(action)) continue;
				Node neighbour = current.getNeighbour(action);
				if(visited.contains(neighbour)) continue;

				int newG = g + 1;
				if(newG < gCost.getOrDefault(neighbour, Integer.MAX_VALUE)){
					gCost.put(neighbour, newG);
					int f = newG + heuristic(neighbour, pacman); 
					neighbour.setCost(f);

				
					if(current.equals(self)){
						firstActions.put(neighbour, action);
					} else {
						firstActions.put(neighbour, firstActions.get(current));
					}
					pq.offer(neighbour);
				}
			}
		}
		return null; 
	}
}
