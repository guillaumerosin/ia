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

import be.belegkarnil.game.board.pacman.behavior.QPacman;
import be.belegkarnil.game.board.pacman.behavior.RLBehavior;
import be.belegkarnil.game.board.pacman.event.GameEvent;
import be.belegkarnil.game.board.pacman.event.GameListener;
import be.belegkarnil.game.board.pacman.event.MoveEvent;
import be.belegkarnil.game.board.pacman.gui.Pacman;

import java.awt.Window;

import static be.belegkarnil.game.board.pacman.Game.DOT_SCORE;
import static be.belegkarnil.game.board.pacman.Game.EAT_GHOST_SCORE;
import static be.belegkarnil.game.board.pacman.Game.POWER_PELLET_SCORE;

public class RLPacman implements GameListener {
	public static int
			WIN_REWARD					=  500,
			DEAD_PENALITY				= -500,
			EAT_GHOST_REWARD			=  200,
			EAT_POWER_PELLET_REWARD	=   10,
			EAT_DOT_REWARD				=   10,
			LIVING_PENALITY			=   -1
	;
	
	
	final RLBehavior ai;
	final int epochs;
	private int score;
	private int lives;
	public RLPacman(RLBehavior ai,int epochs){
		this.ai		= ai;
		this.epochs	= epochs;
	}
	
	public void run(){
		ai.setTrain(true);
		for(int i=0;i<epochs; i++) {
			Game game = LevelFactory.createSmallLevel(ai);
			score = game.getPacman().getScore();
			lives = game.getPacman().countLives();
			game.addGameListener(this);
			game.run();
		}
		ai.setTrain(false);
	}
	
	public static void main(String[] args)throws Exception{
		RLPacman rl = new RLPacman(new QPacman(0.2,0.05,0.8),2000);
		rl.run();
		Window window = new Pacman(rl.ai);
		window.pack();
		window.setLocationRelativeTo(window.getParent());
		window.setVisible(true);
	}
	
	@Override
	public void onGameStarted(GameEvent ge) {
	
	}
	
	@Override
	public void onGameEnded(GameEvent ge) {
	}
	
	@Override
	public void onInvalidMove(MoveEvent me) {
	
	}
	
	@Override
	public void onMove(MoveEvent me) {
	}
	
	@Override
	public void onStep(GameEvent ge) {
		if(ge.isPacmanWin()){
			ai.reward(WIN_REWARD);
			return;
		}
		int current = ge.getGame().getPacman().getScore();
		if(ge.getGame().getPacman().countLives() < lives){
			lives = ge.getGame().getPacman().countLives();
			score = current;
			ai.reward(DEAD_PENALITY);
			return;
		}
		if(current >= score+EAT_GHOST_SCORE){
			ai.reward(EAT_GHOST_REWARD);
		}else if(current >= score+POWER_PELLET_SCORE){
			ai.reward(EAT_POWER_PELLET_REWARD);
		}else if(current >= score+DOT_SCORE){
			ai.reward(EAT_DOT_REWARD);
		}else{
			ai.reward(LIVING_PENALITY);
		}
		score = current;
	}
}
