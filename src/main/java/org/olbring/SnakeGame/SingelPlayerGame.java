package org.olbring.SnakeGame;

import org.olbring.board.Board;
import org.olbring.items.SnakeHead;
import org.olbring.items.SnakeTail;
import org.olbring.react.Listener;
import org.olbring.tools.Tools;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class SingelPlayerGame extends Game{
	//running changes
	protected boolean isWallCollision = false; //Kolision mit der Wand
	protected boolean isAddMove = false; // wenn ein Appfel gefressen wurd wird eine bestimmte move methode verwendet


	
	public SingelPlayerGame() {
		super(); //Aufruf des Konsturktors der Oberklasse

		Main.board.addKeyListener(new Listener());
		initSnake();
		spawnObstacles(obstacleCount);//muss nach der Schlange erstellt werden

		//playing();		Playing wird in Main.gui.checkBtnPlay gestartet, damit Main.game nicht = null

	}
	
	public void playing() {
		Main.board.setVisible(true); //zeigt Spielfeld


		//wieeder holt bis Tod einer Schlange
		while(true) {

			//Bewegung
			snakeMove();

			//Delay			
			try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}

			//Wand abfrage
			isWallCollision = hasWallCollision();   /*Muss vor der Abfrage von checkApple() und hasSnakeCollision()
													stehn um die möglichkeit des WallCrash "ein" "aus" zu ermöglichen*/

			//Tod an der Wand
			if (isWallCollision && wallCrashEnabled){
				gameOver();
				break;
			}

			//Apfel
			if (checkApple()){
				spwanApple();
				isAddMove = true;
			}

			//Collision mit Schlangenkörper/Obstacle
			if(hasCollision(isWallCollision, wallCrashEnabled)) {
				gameOver();
				break;
			}

			//Refresh Information Panel (panelLeft)
			Board.refreshPanelLeft();



		}
		
	}

	/**
	 *
 	 * @return true wenn Apfel gefressen wurde
	 */
	private boolean checkApple(){
		if(p1Head.getRow() == apple.getRow() && p1Head.getColumn() == apple.getColumn()){
			p1score++; //erhöht den scroe
			return true;
		}
		return false;
	}

	private void snakeMove(){
		if (p1direction == 4) return;
		int tempRow = p1Head.getRow();  //speicheren die Letzt position des Kopfes
		int tempCol = p1Head.getColumn();
		int goalRow;
		int goalCol;



		//Bewege den Kopf

		if (!isWallCollision)  //Wenn keine Wand getoffen wird
		{
			if (p1direction == 0) //Eine Spalte nach links
				p1Head.loadRemoveAt(tempRow, tempCol - 1);

			if (p1direction == 1) //Eine Zeile nach oben
				p1Head.loadRemoveAt(tempRow - 1, tempCol);

			if (p1direction == 2) //Eine Spalte nach recht
				p1Head.loadRemoveAt(tempRow, tempCol + 1);

			if (p1direction == 3) //Eine Zeile nach unten
				p1Head.loadRemoveAt(tempRow + 1, tempCol);
		}
		else if (isWallCollision && !wallCrashEnabled)  // Wenn eine Wand getroffen wird und WallCrash ausgeschaltet ist
		{
			if (p1direction == 0) //Aktuelle Zeile, spalte ganz rechts
				p1Head.loadRemoveAt(tempRow, Board.getColumns()-1);

			if (p1direction == 1) //Zeile ganz unten, Aktuelle Spalte
				p1Head.loadRemoveAt(Board.getRows()-1, tempCol);

			if (p1direction == 2) //Aktuelle Zeile, Spalte ganz links
				p1Head.loadRemoveAt(tempRow, 0);

			if (p1direction == 3) //Zeile ganz oben, Aktuelle Spalte
				p1Head.loadRemoveAt(0, tempCol);
		} else {
			throw new IllegalStateException("Unexpected value: " + isWallCollision);
		}


		//Bewege erstes Tail Element
		p1Tails.get(0).loadRemoveAt(tempRow, tempCol);

		// zieht den schlange körper hinter her
		for (int i = 1; i < p1Tails.size(); i++) {
			goalRow = p1Tails.get(i-1).getOldRow();
			goalCol = p1Tails.get(i-1).getOldColumn();

			p1Tails.get(i).loadRemoveAt(goalRow, goalCol);
		}

		//fügt am ende ein Schlangen körper an
		if (isAddMove){
			this.isAddMove = false;

			goalRow = p1Tails.get(p1Tails.size()-1).getOldRow();
			goalCol = p1Tails.get(p1Tails.size()-1).getOldColumn();

			SnakeTail snakeTail = new SnakeTail(p1snakeTailpath);
			snakeTail.loadAt(goalRow, goalCol);
			p1Tails.add(snakeTail);

		}
	}

	/**
	 *
	 * @param isWallCollision true wenn eine Wand im nächsten Zug getroffen wird
	 * @param wallCrashEnabled zeigt an ob man an Wänden sterben kann
	 * @return true wenn es eine Kollision gibt an der man stribt
	 */
	private boolean hasCollision(boolean isWallCollision, boolean wallCrashEnabled){
		int currentRow = p1Head.getRow();
		int currentCol = p1Head.getColumn();
		/*
        Geprüft wird immer ob das Icon des nächsten Feldes des Kopfes nicht "null" ist und nicht "der Apfel"
        wenn alle zutreffen wurde eine Schlange getroffen
         */

		if(!isWallCollision) { //Wenn die Wand nicht durchschritten wird
			//left
			if (p1direction == 0
					&& Board.getFields()[currentRow][currentCol - 1].getIcon() != null
					&& Board.getFields()[currentRow][currentCol - 1].getIcon() != apple.getIcon()) return true;

			//top
			if (p1direction == 1
					&& Board.getFields()[currentRow - 1][currentCol].getIcon() != null
					&& Board.getFields()[currentRow - 1][currentCol].getIcon() != apple.getIcon()) return true;

			//right
			if (p1direction == 2
					&& Board.getFields()[currentRow][currentCol + 1].getIcon() != null
					&& Board.getFields()[currentRow][currentCol + 1].getIcon() != apple.getIcon()) return true;

			//bottom
			if (p1direction == 3
					&& Board.getFields()[currentRow + 1][currentCol].getIcon() != null
					&& Board.getFields()[currentRow + 1][currentCol].getIcon() != apple.getIcon()) return true;

		}
		if (isWallCollision && !wallCrashEnabled){ //Wenn die Wand durch Schritten wird und WallCrash ausgeschaltet ist

			//Left
			if (p1direction == 0 //gleiche Zeile, aber ganz rechts
					&& Board.getFields()[currentRow][Board.getColumns()-1].getIcon() != null
					&& Board.getFields()[currentRow][Board.getColumns()-1].getIcon() != apple.getIcon()) return true;

			//top
			if (p1direction == 1 //gleiche Spalte, aber ganz unten
					&& Board.getFields()[Board.getRows()-1][currentCol].getIcon() != null
					&& Board.getFields()[Board.getRows()-1][currentCol].getIcon() != apple.getIcon()) return true;

			//right
			if (p1direction == 2 //gleiche Zeile, aber ganz links
					&& Board.getFields()[currentRow][0].getIcon() != null
					&& Board.getFields()[currentRow][0].getIcon() != apple.getIcon()) return true;

			//bottom
			if (p1direction == 3 //gleiche Spalte, aber ganz oben
					&& Board.getFields()[0][currentCol].getIcon() != null
					&& Board.getFields()[0][currentCol].getIcon() != apple.getIcon()) return true;

		}

		//default
		return false;
	}

	/**
	 *
	 * @return sagt ob man im nächsten Schritt eine Wand trifft
	 */
	private boolean hasWallCollision() {
		int currentRow = p1Head.getRow();
		int currentCol = p1Head.getColumn();
		
		/*
		 * currentCol/Row + 1 die position die im nächsten schritt erreicht wird
		 */
		
		
		//left
		if(p1direction == 0 && currentCol-1 < 0) return true;
		
		//top
		if(p1direction == 1 && currentRow-1 < 0) return true;
		
		//right
		if(p1direction == 2 && currentCol+1 >= Board.getRows()) return true;
		
		//bottom
		if(p1direction == 3 && currentRow+1 >= Board.getRows())return true;
		
		return false;
	}
	
	private void initSnake() {
		p1Head = new SnakeHead(p1snakeHeadpath);
		p1Head.loadAt(9, 8);
		
		//fügt tail elemente hinzu
		p1Tails.add(new SnakeTail(p1snakeTailpath));
		p1Tails.get(0).loadAt(9, 9);
		
		p1Tails.add(new SnakeTail(p1snakeTailpath));
		p1Tails.get(1).loadAt(9, 10);

		
	}

	/**
	 * Game over mit Score und Zeit anzeige
	 */
	private void gameOver() {
		JOptionPane.showMessageDialog(null, 	"Game Over:\n" +
																	"Dein Score: " + p1score + "\n" +
																	"Deine Zeit: " +
				Tools.formatSecondsNicely(timer.getTime(TimeUnit.SECONDS)),
				"Game Over", JOptionPane.ERROR_MESSAGE);
	}



}
