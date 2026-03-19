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

import be.belegkarnil.game.board.pacman.event.GameEvent;
import be.belegkarnil.game.board.pacman.event.GameListener;
import be.belegkarnil.game.board.pacman.event.MoveEvent;

import java.util.LinkedList;
import java.util.List;

public class Game implements Runnable{
	public static final int VULNERABLE_DURATION = 10; // TODO
	public static final int DOT_SCORE = 1; // TODO
	public static final int POWER_PELLET_SCORE = 10; // TODO
	public static final int EAT_GHOST_SCORE = 20;
	public static final int EATEN_PENALITY = 50;
	private final Board board;
	private final Pacman pacman;
	private final Ghost[] ghosts;
	private List<GameListener> gameListeners;
	
	public Game(Board board, Pacman pacman, Ghost[] ghosts){
		this.board				= board;
		this.pacman				= pacman;
		this.ghosts				= ghosts;
		this.gameListeners	= new LinkedList<GameListener>();
	}
	public void run(){
		pacman.resetScore();
		fireGameStarted(new GameEvent(this));
		boolean finished = false;
		final Agent[] agents = getAgents();
		final List<GameListener> agentsListeners = new LinkedList<GameListener>();
		for(Agent agent:agents){
			final GameListener listener = agent.wantsToListen();
			if(listener != null){
				agentsListeners.add(listener);
				this.addGameListener(listener);
			}
		}
		boolean win = false;
		int dots	= countDots();
		int vulnerableDuration = 0;
		List<Agent> dead = new LinkedList<Agent>();
		
		while(!finished){
			for(Agent agent:dead){
				final int x = agent.getX();
				final int y = agent.getY();
				agent.respawn();
				fireMove(new MoveEvent(this, agent, null, x,y));
			}
			dead.clear();
			
			
			if(vulnerableDuration > 0){
				vulnerableDuration--;
				if(vulnerableDuration == 0){
					for(Ghost ghost:ghosts){
						ghost.setVulnerable(false);
					}
				}
			}
			
			Agent.Action action;
			for(final Agent agent:agents) {
				action = agent.action(board,pacman);
				if (action == null || board.canWalk(agent, action)) {
					final int x = agent.getX();
					final int y = agent.getY();
					agent.move(action);
					fireMove(new MoveEvent(this, agent, action, x,y));
				} else {
					fireInvalidMove(new MoveEvent(this, agent, action));
				}
			}
			int score = 0;
			switch (board.getCellAt(pacman)){
				case POWER_PELLET:
					for(Ghost ghost: ghosts) ghost.setVulnerable(true);
					vulnerableDuration = VULNERABLE_DURATION;
					score += POWER_PELLET_SCORE - DOT_SCORE;
				case DOT:
					score += DOT_SCORE;
					board.eat(pacman);
					dots--;
					break;
			}
			if(dots == 0){
				win		= true;
				finished	= true;
			}else{
				for (Ghost ghost:ghosts){
					if(ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()){
						if(ghost.isVulnerable()){ // pacman eat
							score += EAT_GHOST_SCORE;
							dead.add(ghost);
						}else{ // pacman eaten
							score -= EATEN_PENALITY;
							dead.add(pacman);
							pacman.die();
							finished = !pacman.hasMoreLives();
						}
					}
				}
			}
			pacman.addScore(score);
			fireStep(new GameEvent(this,win));
		}
		fireGameEnded(new GameEvent(this, win));
		for(GameListener listener:agentsListeners)
			this.removeGameListener(listener);
	}
	
	private int countDots() {
		int count = 0;
		for(int y=0; y<board.getHeight(); y++){
			for(int x=0; x<board.getWidth(); x++){
				switch(board.getCellAt(x,y)){
					case DOT:
					case POWER_PELLET:
						count++;
				}
			}
		}
		return count;
	}
	
	protected void fireMove(final MoveEvent me){
		for(GameListener listener:gameListeners)
			listener.onMove(me);
	}
	protected void fireInvalidMove(final MoveEvent me){
		for(GameListener listener:gameListeners)
			listener.onInvalidMove(me);
	}
	protected void fireGameStarted(final GameEvent ge){
		for(GameListener listener:gameListeners)
			listener.onGameStarted(ge);
	}
	protected void fireGameEnded(final GameEvent ge){
		for(GameListener listener:gameListeners)
			listener.onGameEnded(ge);
	}
	protected void fireStep(final GameEvent ge){
		for(GameListener listener:gameListeners)
			listener.onStep(ge);
	}
	public void removeGameListener(GameListener listener){
		if(listener == null)return;
		this.gameListeners.remove(listener);
	}
	public void addGameListener(GameListener listener){
		if(listener == null)return;
		this.gameListeners.add(listener);
	}
	
	public Board getBoard() {
		return this.board;
	}
	public Pacman getPacman() {
		return this.pacman;
	}
	public Ghost[] getGhosts() {
		final Ghost[] ghosts = new Ghost[this.ghosts.length];
		for(int i=0; i<ghosts.length; i++) ghosts[i] = this.ghosts[i];
		return ghosts;
	}
	
	public Agent[] getAgents() {
		Agent[] agents = new Agent[ghosts.length+1];
		agents[0] = pacman;
		for(int i=0; i<ghosts.length; i++) agents[i+1] = this.ghosts[i];
		return agents;
	}
}
