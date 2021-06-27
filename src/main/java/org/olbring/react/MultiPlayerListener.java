package org.olbring.react;

import org.olbring.SnakeGame.Game;
import org.olbring.SnakeGame.SingelPlayerGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiPlayerListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }


    /**
     * ändert die richtungs Variabeln je nach gedrückter Taste
     *
     * Player 1 -> Pfeil tasten
     * Player 2 -> WASD
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

        //Player 1
        if(key_id == KeyEvent.VK_LEFT && !(Game.p1direction == 2))
        {
            Game.p1direction = 0; //Left
        }
        if(key_id == KeyEvent.VK_UP && !(Game.p1direction == 3))
        {
            Game.p1direction = 1;//Top
        }                                   // != 4 damit man Am Anfang nicht in sich selbst fährt
        if(key_id == KeyEvent.VK_RIGHT && !(Game.p1direction == 0) && !(Game.p1direction == 4))
        {
            Game.p1direction = 2;//right
        }

        if(key_id == KeyEvent.VK_DOWN && !(Game.p1direction == 1))
        {
            Game.p1direction = 3;//bottom
        }

        //Player 2
        if(key_id == KeyEvent.VK_A && !(Game.p2direction == 2))
        {
            Game.p2direction = 0;//Left
        }
        if(key_id == KeyEvent.VK_W && !(Game.p2direction == 3))
        {
            Game.p2direction = 1;//top
        }                                   // != 4 damit man Am Anfang nicht in sich selbst fährt
        if(key_id == KeyEvent.VK_D && !(Game.p2direction == 0) && !(Game.p2direction ==4))
        {
            Game.p2direction = 2;//right
        }

        if(key_id == KeyEvent.VK_S && !(Game.p2direction == 1))
        {
            Game.p2direction = 3;//bottom
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {


    }
}
