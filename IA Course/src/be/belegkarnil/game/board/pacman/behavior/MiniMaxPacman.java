package be.belegkarnil.game.board.pacman.behavior;

import be.belegkarnil.game.board.pacman.Agent;
import be.belegkarnil.game.board.pacman.Behavior;
import be.belegkarnil.game.board.pacman.Board;
import be.belegkarnil.game.board.pacman.Game;
import be.belegkarnil.game.board.pacman.Ghost;
import be.belegkarnil.game.board.pacman.Pacman;
import be.belegkarnil.game.board.pacman.event.GameEvent;
import be.belegkarnil.game.board.pacman.event.GameListener;
import be.belegkarnil.game.board.pacman.event.MoveEvent;

import java.awt.Color;

public class MiniMaxPacman extends Agent implements GameListener{
	private static final int MAX_DEPTH = 4;
	private Game game;

	public MiniMaxPacman(int initX, int initY){
		super(initX, initY);
		game = null;
	}

	@Override
	public Agent.Action action(Board board, Pacman pacman){
		int v = Integer.MIN_VALUE;
		Agent.Action bestAction = null;
		for(Agent.Action action : Agent.Action.values()){
			if(game.getBoard().canWalk(pacman, action)){
				Game newState = derive(game,pacman,action);
				int opponent = value(newState, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
				if(opponent > v){
					v = opponent;
					bestAction = action;
				}
			}
		}
		return bestAction;
	}

	private int maxValue(Game game, int depth, Agent agent, int alpha, int beta){
		int v = Integer.MIN_VALUE;
		for(Agent.Action action : Agent.Action.values()){
			if(game.getBoard().canWalk(agent, action)){
				Game newState = derive(game,agent,action);
				int opponent = value(newState, depth+1, alpha, beta);
				if(opponent > v){
					v = opponent;
				}
				if(v >= beta) return v; // coupure bêta
				alpha = Math.max(alpha, v);
			}
		}
		return v;
	}

	private int minValue(Game game, int depth, Agent agent, int alpha, int beta){
		int v = Integer.MAX_VALUE;
		for(Agent.Action action : Agent.Action.values()){
			if(game.getBoard().canWalk(agent, action)){
				Game newState = derive(game,agent,action);
				int opponent = value(newState, depth+1, alpha, beta);
				if(opponent < v){
					v = opponent;
				}
				if(v <= alpha) return v; // coupure alpha
				beta = Math.min(beta, v);
			}
		}
		return v;
	}

	private Game derive(Game game, Agent agent, Action action){
		Board.Cell[][] cells = new Board.Cell[game.getBoard().getHeight()][game.getBoard().getWidth()];
		for(int y=0; y<cells.length; y++){
			for(int x=0; x<cells[y].length; x++){
				cells[y][x] = game.getBoard().getCellAt(x,y);
			}
		}

		int ax = game.getPacman().getX();
		int ay = game.getPacman().getY();
		if(agent.equals(game.getPacman())){
			ax += action.dx;
			ay += action.dy;
		}
		Pacman newPacman = new Pacman(null,ax, ay);
		switch(cells[ay][ax]){
			case DOT:
			case POWER_PELLET:
				cells[ay][ax] = Board.Cell.EMPTY;
		}

		Ghost[] newGhosts = new Ghost[game.getGhosts().length];
		for(int i=0;i<newGhosts.length; i++){
			ax = game.getGhosts()[i].getX();
			ay = game.getGhosts()[i].getY();

			if(agent == game.getGhosts()[i]){
				ax += action.dx;
				ay += action.dy;
			}

			newGhosts[i] = new Ghost(game.getGhosts()[i].getColor(), ax, ay){
				@Override
				protected Action action(Node self, Node pacman){
					return null;
				}
			};
		}

		// TODO POWER_PELLET, respawn

		Board newBoard = new Board(cells);
		return new Game(newBoard, newPacman, newGhosts);
	}

	private int value(Game game, int depth, int alpha, int beta){
		if(isTerminal(game)) return utility(depth, game);
		if(depth >= MAX_DEPTH) return utility(depth, game);
		Agent[] agents = game.getAgents();
		Agent nextAgent = agents[depth % agents.length];
		if(nextAgent.equals(game.getPacman())) return maxValue(game, depth, nextAgent, alpha, beta);
		return minValue(game, depth, nextAgent, alpha, beta);
	}

	private boolean isTerminal(Game game){
		if(!hasBullets(game.getBoard())) return true;
		if(isPacmanDead(game)) return true;
		return false;
	}

	private int utility(int depthOrMoves, Game game){
		if(isPacmanDead(game)) return -100;
		return 100;
	}

	private static boolean isPacmanDead(Game game){
		int x = game.getPacman().getX();
		int y = game.getPacman().getY();
		for(Ghost ghost:game.getGhosts()){
			if(ghost.getX() == x && ghost.getY() == y){
				return true;
			}
		}
		return false;
	}

	private static boolean hasBullets(Board board){
		return countBullets(board) > 0;
	}

	private static int countBullets(Board board){
		int count = 0;
		for(int x=0; x<board.getWidth(); x++){
			for(int y=0; y<board.getHeight(); y++){
				switch(board.getCellAt(x,y)){
					case DOT:
					case POWER_PELLET: count++;
				}
			}
		}
		return count;
	}

	@Override
	protected GameListener wantsToListen(){
		return this;
	}

	@Override
	public void onGameStarted(GameEvent ge){
		this.game = ge.getGame();
	}

	@Override
	public void onGameEnded(GameEvent ge){}
	@Override
	public void onInvalidMove(MoveEvent me){}
	@Override
	public void onMove(MoveEvent me){}
	@Override
	public void onStep(GameEvent ge){}
}