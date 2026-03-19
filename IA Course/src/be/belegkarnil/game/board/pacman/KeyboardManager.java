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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardManager {
	private Object lock;
	private int keyCode;
	private static final int NO_KEY = -1;
	private static final int NO_MODIFIERS = 0;
	public KeyboardManager(JComponent toListen){
		lock		= new Object();
		keyCode	= NO_KEY;
		
		final InputMap keyMap		= toListen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		final ActionMap actionMap	= toListen.getActionMap();
		
		for(String direction:getDirections()){
			final int[] keyCodes = getKeyCodesFor(direction);
			for(String event:getKeyEvent()){
				final String key				= direction+event;
				final boolean onKeyRelease	= event == "Released";
				actionMap.put(direction+event,createActionFor(keyCodes[0],onKeyRelease));
				
				for(int keyCode: keyCodes)
					keyMap.put(KeyStroke.getKeyStroke(keyCode,NO_MODIFIERS,onKeyRelease),key);
			}
		}
	}
	
	private static int[] getKeyCodesFor(String direction) {
		switch (direction){
			case "up":		return new int[]{KeyEvent.VK_UP, KeyEvent.VK_KP_UP};
			case "down":	return new int[]{KeyEvent.VK_DOWN, KeyEvent.VK_KP_DOWN};
			case "left":	return new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT};
			case "right":	return new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT};
		}
		return null;
	}
	
	private static String[] getDirections(){
		return new String[]{"up","down","left","right"};
	}
	private static String[] getKeyEvent(){
		return new String[]{"Pressed","Released"};
	}
	
	private Action createActionFor(final int keyCode,final boolean onKeyRelease){
		final String KEY_CODE_KEY				= "KEY_CODE";
		final Action action;
		
		if(onKeyRelease){
			action = new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final int code = ((Integer) getValue(KEY_CODE_KEY)).intValue();
					synchronized (KeyboardManager.this.lock){
						if(code == KeyboardManager.this.keyCode){
							// Current key released, maybe another pressed but do not care
							KeyboardManager.this.keyCode = NO_KEY;
						}
					}
				}
			};
		}else{
			action = new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final int code = ((Integer) getValue(KEY_CODE_KEY)).intValue();
					synchronized (KeyboardManager.this.lock){
						if(code != KeyboardManager.this.keyCode){
							// New key pressed => Last key pressed = new direction
							KeyboardManager.this.keyCode = code;
						}
					}
				}
			};
		}
		
		action.putValue(KEY_CODE_KEY,Integer.valueOf(keyCode));
		return action;
	}
	
	public Agent.Action getAction(){
		final int code;
		Agent.Action action = null;
		synchronized (lock){
			code = keyCode;
		}
		switch(code){
			case KeyEvent.VK_UP:
				action = Agent.Action.UP;
				break;
			case KeyEvent.VK_DOWN:
				action = Agent.Action.DOWN;
				break;
			case KeyEvent.VK_LEFT:
				action = Agent.Action.LEFT;
				break;
			case KeyEvent.VK_RIGHT:
				action = Agent.Action.RIGHT;
				break;
		}
		return action;
	}
}
