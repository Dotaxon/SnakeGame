package org.olbring.react;

import org.olbring.SnakeGame.Game;
import org.olbring.SnakeGame.SingelPlayerGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Listener implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {

	}
	/**
	 * ändert die richtungs Variabeln je nach gedrückter Taste
	 *
	 * Player 1 -> Pfeil tasten
	 *
	 *
	 * direction
	 *          = 0 links
	 *          = 1 top
	 *          = 2 rechts
	 *          = 3 bottom
	 *          = 4 ist stehen nur am Anfang
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		int key_id = e.getKeyCode();

		if(key_id == KeyEvent.VK_LEFT && !(Game.p1direction == 2))
		{
			Game.p1direction = 0;
		}
		if(key_id == KeyEvent.VK_UP && !(Game.p1direction == 3))
		{
			Game.p1direction = 1;
		}									// != 4 damit man Am Anfang nicht in sich selbst fährt
		if(key_id == KeyEvent.VK_RIGHT && !(Game.p1direction == 0) && !(Game.p1direction == 4))
		{
			Game.p1direction = 2;
		}

		if(key_id == KeyEvent.VK_DOWN && !(Game.p1direction == 1))
		{
			Game.p1direction = 3;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

}
