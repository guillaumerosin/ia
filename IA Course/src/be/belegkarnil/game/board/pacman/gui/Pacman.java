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
import be.belegkarnil.game.board.pacman.Behavior;
import be.belegkarnil.game.board.pacman.Board;
import be.belegkarnil.game.board.pacman.Game;
import be.belegkarnil.game.board.pacman.KeyboardManager;
import be.belegkarnil.game.board.pacman.LevelFactory;
import be.belegkarnil.game.board.pacman.behavior.HMIBehavior;
import be.belegkarnil.game.board.pacman.event.GameEvent;
import be.belegkarnil.game.board.pacman.event.GameListener;
import be.belegkarnil.game.board.pacman.event.MoveEvent;
import be.belegkarnil.game.board.pacman.ghost.Blinky;
import be.belegkarnil.game.board.pacman.ghost.Clyde;
import be.belegkarnil.game.board.pacman.ghost.Inky;
import be.belegkarnil.game.board.pacman.ghost.Pinky;
import be.belegkarnil.game.board.pacman.ghost.Randy;
import be.belegkarnil.game.board.pacman.gui.image.Constants;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pacman extends JFrame implements ActionListener, GameListener, ItemListener {
	private final Behavior behavior;
	private JComboBox<String> level;
	private JButton play;
	private Game game;
	private final Object hmiLock;
	private HMIBehavior hmi;
	private JPanel boardPanel;
	
	private CellPanel[][] cells;
	private final KeyboardManager keyboard;
	public Pacman(){
		this(new HMIBehavior());
	}
	public Pacman(Behavior behavior){
		final JPanel content = new JPanel(new BorderLayout());
		this.keyboard	= new KeyboardManager(content);
		this.game		= null;
		this.hmiLock	= new Object();
		this.hmi			= null;
		this.behavior	= behavior;
		
		setTitle("BelegPacman");
		setContentPane(content);
		add(createSettingsPanel(), BorderLayout.NORTH);
		add(createBoardPanel(), BorderLayout.CENTER);
		add(createActionPanel(), BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private JComponent createActionPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		play = new JButton("Play");
		play.addActionListener(this);
		panel.add(play, BorderLayout.CENTER);
		return panel;
	}
	
	private JComponent createSettingsPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Pacman behavior"));
		level = new JComboBox<String>(LevelFactory.getAvailableLevels());
		level.addItemListener(this);
		panel.add(level);
		return panel;
	}
	
	private JComponent createBoardPanel() {
		boardPanel = new JPanel(new BorderLayout());
		changeLevel((String)(level.getSelectedItem()));
		return boardPanel;
	}
	private void reload(Game game){
		boardPanel.removeAll();
		cells = new CellPanel[game.getBoard().getHeight()][game.getBoard().getWidth()];
		
		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill	= GridBagConstraints.HORIZONTAL;
		for(int y=0; y<game.getBoard().getHeight();y++){
			for(int x=0; x<game.getBoard().getWidth();x++){
				cells[y][x] = new CellPanel(game.getBoard().getCellAt(x,y));
				cells[y][x].setPreferredSize(new Dimension(Constants.BOX_SIZE,Constants.BOX_SIZE));
				c.gridx = x;
				c.gridy = y;
				content.add(cells[y][x],c);
			}
		}
		for(Agent agent:game.getAgents()){
			cells[agent.getY()][agent.getX()].agentEntered(agent, null);
		}
		
		final JPanel resizable = new JPanel(new FlowLayout());
		resizable.add(content);
		boardPanel.add(resizable,BorderLayout.CENTER);
		
		resizable.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int size = Math.min(resizable.getWidth() / game.getBoard().getWidth(), resizable.getHeight() / game.getBoard().getHeight());
				content.setSize(size*game.getBoard().getWidth(),size*game.getBoard().getHeight());
				for(JPanel[] row:cells)
					for(JPanel cell:row) {
						cell.setPreferredSize(new Dimension(size, size));
						cell.setSize(size, size);
					}
				content.revalidate();
			}
		});
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		freezeInterface(true);
		game = LevelFactory.createLevel((String) (level.getSelectedItem()),behavior);
		game.addGameListener(this);
		new Thread(game).start();
	}
	
	@Override
	public void onGameStarted(GameEvent ge) {
		this.reload(ge.getGame());
		if(ge.getGame().getPacman().getBehavior() instanceof HMIBehavior){
			synchronized (hmiLock){
				this.hmi = (HMIBehavior) ge.getGame().getPacman().getBehavior();
			}
		}
		timeStep();// Force first event
	}
	
	@Override
	public void onGameEnded(GameEvent ge) {
		synchronized (hmiLock){
			this.hmi = null;
		}
		freezeInterface(false);
	}
	
	@Override
	public void onInvalidMove(MoveEvent me) {
		System.out.println("Invalid move: "+me.getAgent().getName()+" tried "+me.getMove().name());
	}
	
	@Override
	public void onMove(MoveEvent me) {
		int x,y;
		
		x = me.getPreviousX();
		y = me.getPreviousY();
		this.cells[y][x].agentExited(me.getAgent(),me.getMove());
		this.cells[y][x].setState(me.getGame().getBoard().getCellAt(x,y));
		this.cells[y][x].invalidate();
		this.cells[y][x].revalidate();
		
		x = me.getCurrentX();
		y = me.getCurrentY();
		this.cells[y][x].agentEntered(me.getAgent(),me.getMove());
		this.cells[y][x].setState(me.getGame().getBoard().getCellAt(x,y));
		this.cells[y][x].invalidate();
		this.cells[y][x].revalidate();
	}
	
	private void freezeInterface(boolean freeze){
		freeze = !freeze;
		this.level.setEnabled(freeze);
		this.play.setEnabled(freeze);
	}
	
	private void timeStep(){
		try {
			Thread.sleep(Constants.SLOWDOWN_DURATION_MILIS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		synchronized (hmiLock){
			if(hmi != null)
				hmi.setAction(keyboard.getAction());
		}
	}
	
	@Override
	public void onStep(GameEvent ge) {
		boardPanel.repaint();
		timeStep();
	}
	
	private void changeLevel(String levelName){
		this.reload(LevelFactory.createLevel(levelName , new Behavior() {
			@Override
			public Agent.Action action(Board board, be.belegkarnil.game.board.pacman.Pacman pacman) {
				return null;
			}
		}));
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		changeLevel((String)(level.getSelectedItem()));
		invalidate();
		revalidate();
	}
}
