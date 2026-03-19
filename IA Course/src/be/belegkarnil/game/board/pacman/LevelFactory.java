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

import be.belegkarnil.game.board.pacman.ghost.Blinky;
import be.belegkarnil.game.board.pacman.ghost.Clyde;
import be.belegkarnil.game.board.pacman.ghost.Inky;
import be.belegkarnil.game.board.pacman.ghost.Pinky;
import be.belegkarnil.game.board.pacman.ghost.Randy;
import be.belegkarnil.game.board.pacman.Pacman;

public final class LevelFactory {
	public static final int MAX_ROWS = 1024;
	public static final int MAX_COLS = 4096;
	private static final Board.Cell[][] SMALL_LEVEL = new Board.Cell[][]{
			{Board.Cell.NORTHWEST_CORNER, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.NORTHEAST_CORNER},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.POWER_PELLET, Board.Cell.NORTHWEST_CORNER, Board.Cell.HORIZONTAL_WALL, Board.Cell.EAST_ENDING, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.SOUTHWEST_CORNER, Board.Cell.HORIZONTAL_WALL, Board.Cell.EAST_ENDING, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.SOUTHWEST_CORNER, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.SOUTHEAST_CORNER}
	};
	
	private static final Board.Cell[][] MEDIUM_LEVEL = new Board.Cell[][]{
			{Board.Cell.NORTHWEST_CORNER, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.NORTHEAST_CORNER},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.DOT,	Board.Cell.NORTH_ENDING, Board.Cell.EMPTY, Board.Cell.DOT, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.EMPTY,	Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.DOT,	Board.Cell.SOUTH_ENDING, Board.Cell.EMPTY, Board.Cell.DOT, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL},
			{Board.Cell.SOUTHWEST_CORNER, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.SOUTHEAST_CORNER}
	};
	
	private static final Board.Cell[][] BIG_LEVEL = new Board.Cell[][]{
			{Board.Cell.NORTHWEST_CORNER, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.NORTH_T_CROSS, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.NORTH_T_CROSS, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.NORTHEAST_CORNER},
			{Board.Cell.VERTICAL_WALL, Board.Cell.POWER_PELLET, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.NORTHWEST_CORNER, Board.Cell.EAST_ENDING, Board.Cell.DOT, Board.Cell.SOUTH_ENDING, Board.Cell.DOT, Board.Cell.WEST_ENDING, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.EAST_ENDING, Board.Cell.DOT, Board.Cell.SOUTH_ENDING, Board.Cell.DOT, Board.Cell.WEST_ENDING, Board.Cell.NORTHEAST_CORNER, Board.Cell.DOT, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.SOUTH_ENDING, Board.Cell.DOT, Board.Cell.WEST_ENDING, Board.Cell.EAST_ENDING, Board.Cell.DOT, Board.Cell.NORTHWEST_CORNER, Board.Cell.EAST_ENDING, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.WEST_ENDING, Board.Cell.NORTHEAST_CORNER, Board.Cell.DOT, Board.Cell.WEST_ENDING, Board.Cell.EAST_ENDING, Board.Cell.DOT, Board.Cell.SOUTH_ENDING, Board.Cell.DOT, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.NORTH_ENDING, Board.Cell.DOT, Board.Cell.WEST_ENDING, Board.Cell.EAST_ENDING, Board.Cell.DOT, Board.Cell.SOUTHWEST_CORNER, Board.Cell.EAST_ENDING, Board.Cell.EMPTY, Board.Cell.EMPTY, Board.Cell.WEST_ENDING, Board.Cell.SOUTHEAST_CORNER, Board.Cell.DOT, Board.Cell.WEST_ENDING, Board.Cell.EAST_ENDING, Board.Cell.DOT, Board.Cell.NORTH_ENDING, Board.Cell.DOT, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.SOUTHWEST_CORNER, Board.Cell.EAST_ENDING, Board.Cell.DOT, Board.Cell.NORTH_ENDING, Board.Cell.DOT, Board.Cell.WEST_ENDING, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.EAST_ENDING, Board.Cell.DOT, Board.Cell.NORTH_ENDING, Board.Cell.DOT, Board.Cell.WEST_ENDING, Board.Cell.SOUTHEAST_CORNER, Board.Cell.DOT, Board.Cell.VERTICAL_WALL},
			{Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.EMPTY, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.VERTICAL_WALL, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.DOT, Board.Cell.POWER_PELLET, Board.Cell.VERTICAL_WALL},
			{Board.Cell.SOUTHWEST_CORNER, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.SOUTH_T_CROSS, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.SOUTH_T_CROSS, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.HORIZONTAL_WALL, Board.Cell.SOUTHEAST_CORNER}
	};
	
	public static Game createSmallLevel(Behavior behavior){
		Board board = new Board(SMALL_LEVEL);
		Pacman pacman = new Pacman(behavior,1,1);
		Ghost[] ghosts = {new Randy(board.getWidth()-2,board.getHeight()-2)};
		return new Game(board,pacman,ghosts);
	}
	
	public static String[] getAvailableLevels(){
		return new String[]{"Small", "Medium", "Big"};
	}
	
	public static Game createLevel(String levelName, Behavior behavior){
		levelName = levelName.toLowerCase();
		switch (levelName){
			case "small":	return createSmallLevel(behavior);
			case "medium":	return createMediumLevel(behavior);
			case "big":		return createBigLevel(behavior);
		}
		return null;
	}
	
	public static Game createMediumLevel(Behavior behavior){
		Board board = new Board(MEDIUM_LEVEL);
		Pacman pacman = new Pacman(behavior,1,2);
		Ghost[] ghosts = {new Inky(board.getWidth()-2,board.getHeight()-2)};
		return new Game(board,pacman,ghosts);
	}
	
	public static Game createBigLevel(Behavior behavior){
		// bleu à gauche, rouge à droite
		// pcman en bas
		Board board = new Board(BIG_LEVEL);
		Pacman pacman = new Pacman(behavior,9,board.getHeight()-2);
		Ghost[] ghosts = {
				new Inky( 8,5),
				new Blinky(11,5)
		};
		return new Game(board,pacman,ghosts);
	}
}
