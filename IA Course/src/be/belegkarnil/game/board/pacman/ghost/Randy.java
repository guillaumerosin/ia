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

import be.belegkarnil.game.board.pacman.Board;
import be.belegkarnil.game.board.pacman.Ghost;
import be.belegkarnil.game.board.pacman.Pacman;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Randy extends Ghost {
	public Randy(final int initX,final int initY){
		super(new Color(0,255,0),initX,initY);
	}
	
	@Override
	public Action action(final Board board, final Pacman pacman){
		List<Action> actions = new ArrayList<Action>(4);
		if(board.canWalk(this,Action.UP)) actions.add(Action.UP);
		if(board.canWalk(this,Action.DOWN)) actions.add(Action.DOWN);
		if(board.canWalk(this,Action.LEFT)) actions.add(Action.LEFT);
		if(board.canWalk(this,Action.RIGHT)) actions.add(Action.RIGHT);
		Collections.shuffle(actions);
		if(actions.size() > 0) return actions.get(0);
		return null;
	}
	
	@Override
	protected Action action(final Node self, final Node pacman){
		return null;
	}
}
