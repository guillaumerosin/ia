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
package be.belegkarnil.game.board.pacman.gui;

import be.belegkarnil.game.board.pacman.Agent;
import be.belegkarnil.game.board.pacman.Board;
import be.belegkarnil.game.board.pacman.gui.image.Constants;
import be.belegkarnil.game.board.pacman.gui.image.Corner;
import be.belegkarnil.game.board.pacman.gui.image.Cross;
import be.belegkarnil.game.board.pacman.gui.image.Dot;
import be.belegkarnil.game.board.pacman.gui.image.HorizontalWall;
import be.belegkarnil.game.board.pacman.gui.image.Pacman;
import be.belegkarnil.game.board.pacman.gui.image.Ghost;
import be.belegkarnil.game.board.pacman.gui.image.VerticalWall;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class CellPanel extends JPanel {
	private Image type;
	private final List<Agent> agents;
	private final List<Agent.Action> orientation;
	
	public CellPanel(){
		this(Board.Cell.EMPTY);
	}
	public CellPanel(Board.Cell initState){
		this.type			= cellToImage(initState);
		this.agents			= new LinkedList<Agent>();
		this.orientation	= new LinkedList<Agent.Action>();
		setBackground(Constants.GROUND_COLOR);
	}
	
	public void setState(Board.Cell newState){
		this.type = cellToImage(newState);
	}
	
	private Image cellToImage(Board.Cell initState) {
		switch (initState){
			case EMPTY:							return null;
			case HORIZONTAL_WALL:			return new HorizontalWall();
			case HORIZONTAL_CLOSED_WALL:	return new HorizontalWall(true,true);
			case EAST_ENDING:					return new HorizontalWall(false,true);
			case WEST_ENDING:					return new HorizontalWall(true,false);
			case VERTICAL_WALL:				return new VerticalWall();
			case VERTICAL_CLOSED_WALL:		return new VerticalWall(true,true);
			case NORTH_ENDING:				return new VerticalWall(true,false);
			case SOUTH_ENDING:				return new VerticalWall(false,true);
			case DOT:							return new Dot();
			case POWER_PELLET:				return new Dot(true);
			case CROSS:							return new Cross();
			case EAST_T_CROSS:				return new Cross(false,false,false,true);
			case WEST_T_CROSS:				return new Cross(false,false,true,false);
			case NORTH_T_CROSS:				return new Cross(true,false,false,false);
			case SOUTH_T_CROSS:				return new Cross(false,true,false,false);
			case NORTHEAST_CORNER:			return new Corner(true,true);
			case NORTHWEST_CORNER:			return new Corner(true,false);
			case SOUTHEAST_CORNER:			return new Corner(false,true);
			case SOUTHWEST_CORNER:			return new Corner(false,false);
		}
		return null;
	}
	
	void agentEntered(Agent agent, Agent.Action action){
		this.agents.add(agent);
		this.orientation.add(action);
	}
	void agentExited(Agent agent, Agent.Action action){
		int found = -1;
		for(int i=0; i<agents.size(); i++){
			if(agents.get(i).getName().equals(agent.getName())){
				found	= i;
				i		= agents.size();
			}
		}
		if(found >= 0) {
			this.agents.remove(found);
			this.orientation.remove(found);
		}
	}
	
	private void drawImage(Graphics2D g,Image image){
		//g.drawImage(image,0,0,null);
		
		AffineTransform at = new AffineTransform();
		
		at.scale(((float)getWidth()) / Constants.BOX_SIZE, ((float)getHeight()) / Constants.BOX_SIZE);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		
		g.drawImage(image,at,null);
	}
	
	private void paintComponent(Graphics2D g){
		// draw cell
		if(type != null){
			drawImage(g,type);
		}
		// draw vulnerable ghosts
		Agent agent;
		for(int i=0; i<agents.size(); i++){
			agent = agents.get(i);
			if(agent instanceof be.belegkarnil.game.board.pacman.Ghost && ((be.belegkarnil.game.board.pacman.Ghost)agent).isVulnerable()){
				drawImage(g,new Ghost(((be.belegkarnil.game.board.pacman.Ghost)agent).getColor(),orientation.get(i),true));
			}
		}
		
		// draw pacman
		for(int i=0; i<agents.size(); i++){
			agent = agents.get(i);
			if(agent instanceof be.belegkarnil.game.board.pacman.Pacman){
				drawImage(g,new Pacman(orientation.get(i)));
			}
		}
		
		// draw non-vulnerable ghosts
		for(int i=0; i<agents.size(); i++){
			agent = agents.get(i);
			if(agent instanceof be.belegkarnil.game.board.pacman.Ghost && !((be.belegkarnil.game.board.pacman.Ghost)agent).isVulnerable()){
				drawImage(g,new Ghost(((be.belegkarnil.game.board.pacman.Ghost)agent).getColor(),orientation.get(i)));
			}
		}
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		paintComponent((Graphics2D) g);
	}
}
