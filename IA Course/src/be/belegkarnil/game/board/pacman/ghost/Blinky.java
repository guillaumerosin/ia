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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Blinky extends Ghost{
	public Blinky(final int initX,final int initY){
		super(new Color(255,0,0),initX,initY);
	}
	
	@Override
	protected Action action(final Node self, final Node pacman){
		self.setCost(0);
		PriorityQueue<Node> pq = new PriorityQueue<>(this);
		pq.offer(self);

		Set<Node> visited = new HashSet<>();
		Map<Node, Action> firstActions = new HashMap<>(); 

		while(!pq.isEmpty()){
			Node current = pq.poll();

			if(visited.contains(current)) continue;
			visited.add(current);

			if(current.equals(pacman)){
				return firstActions.get(current);
			}

			for(Action action : Action.values()){
				if(!current.hasNeighbour(action)) continue;
				Node neighbour = current.getNeighbour(action);
				if(visited.contains(neighbour)) continue;

				int newCost = current.getCost() + 1;
				if(newCost < neighbour.getCost()){
					neighbour.setCost(newCost);
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
