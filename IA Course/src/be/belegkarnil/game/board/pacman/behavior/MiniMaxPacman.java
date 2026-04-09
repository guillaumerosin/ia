package be.belegkarnil.game.board.pacman.behavior;

import be.belegkarnil.game.board.pacman;
import be.belegkarnil.game.board.pacman.Agent;
import be.belegkarnil.game.board.pacman.Behavior;
import be.belegkarnil.game.board.pacman.Board;

public class MiniMaxPacman implements Behavior{
    @Override
    public Agent.Action action(Board board, Pacman pacman){
        for(Agent.Action action : Agent.Action.values()){
            if(board.canWalk(pacman.action)){
                maxValue(board, action);
            }
        }
        return null;

        
    }
    private boolean isTerminal(Board board, Pacman pacman){
        if(!state.hasBullet()) return true;
        if(pacman.isDead()) return true;
        return false;
    }
    private int utility(int depthOrMoves,Board board, Pacman pacman){
        if(pacman.isDead()) return -100;
        return 100;
        
        
    } 
}
