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
package be.belegkarnil.game.board.pacman.event;

import be.belegkarnil.game.board.pacman.Agent;
import be.belegkarnil.game.board.pacman.Game;
import be.belegkarnil.game.board.pacman.Pacman;

import java.util.EventObject;

public class MoveEvent extends EventObject{
	public final Agent agent;
	public final Agent.Action move;
	public final int x,y;
	
	public MoveEvent(Game game, Agent agent, Agent.Action move){
		this(game,agent,move, agent.getX(), agent.getY());
	}
	
	public MoveEvent(Game game, Agent agent, Agent.Action move,final int oldX, final int oldY){
		super(game);
		this.agent	= agent;
		this.move	= move;
		this.x		= oldX;
		this.y		= oldY;
	}
	public int getPreviousX(){
		return this.x;
	}
	public int getCurrentX(){
		return this.agent.getX();
	}
	public int getPreviousY(){
		return this.y;
	}
	public int getCurrentY(){
		return this.agent.getY();
	}
	
	public Agent getAgent(){
		return this.agent;
	}
	public Agent.Action getMove(){
		return this.move;
	}
	
	public boolean isPacman(){
		if(this.getAgent() == null) return false;
		return this.getAgent() instanceof Pacman;
	}
	
	public Game getGame(){
		return (Game) getSource();
	}
	
}
