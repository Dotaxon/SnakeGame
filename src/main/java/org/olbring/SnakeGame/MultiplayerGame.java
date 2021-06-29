package org.olbring.SnakeGame;

import org.olbring.board.Board;
import org.olbring.items.SnakeHead;
import org.olbring.items.SnakeTail;
import org.olbring.react.MultiPlayerListener;

import javax.swing.*;

public class MultiplayerGame extends Game {
    protected boolean isP1WallCollision = false; //Kolision mit der Wand
    protected boolean isP2WallCollision = false;
    protected boolean isP1AddMove = false; // wenn ein Appfel gefressen wurd wird eine bestimmte move methode verwendet
    protected boolean isP2AddMove = false;

    MultiplayerGame(){
        super(); //Aufruf des Konstruktores der Oberklasse

        Main.board.addKeyListener(new MultiPlayerListener()); //fügt einen den Multiplayer Key Listener hinzu


        initSnakes();   //initialisiert die Schlange Player 1 gelb (Pfeiltasten)
                        //Player 2 grün (WASD)


        spawnObstacles(obstacleCount); //Müssen nach der Schlange gespawnt werden, damit sie nicht in der Schlange sind







        //playing();		Playing wird in Main.gui.checkBtnPlay gestartet, damit Main.game nicht = null
    }



    @Override
    protected void playing() {
        Main.board.setVisible(true); //zeigt Spielfeld

        //wieeder holt bis Tod einer Schlange
        while (true){

            //Bewegt die Schlangen
            moveP1Snake();
            moveP2Snake();

            //delay
            try{Thread.sleep(speed);}catch (Exception e){e.printStackTrace();}

            //Wand abfrage
            isP1WallCollision = hasP1WallCollision();   //Muss vor der Abfrage von checkApple() und hasSnakeCollision()
            isP2WallCollision = hasP2WallCollision();    //stehn um die möglichkeit des WallCrash "ein" "aus" zu ermöglichen*/

            //Player 1 Tod an Wand
            if (isP1WallCollision && wallCrashEnabled){
                gameOverP1();
                break;
            }

            //Player 2 Tod an Wand
            if (isP2WallCollision && wallCrashEnabled){
                gameOverP2();
                break;
            }

            //Player 1 Apfel gefressen
            if (checkApple() == 1) {
                ++p1score;
                spwanApple();
                isP1AddMove = true;
            }

            //Player 2 Apfel gefressen
            if (checkApple() == 2 ){
                ++p2score;
                spwanApple();
                isP2AddMove = true;
            }

            //Player 1 Tod an Schlange/Obstacle
            if (hasP1Collision()){
                gameOverP1();
                break;
            }

            //Player 2 Tod an Schlange/Obstacle
            if (hasP2Collision()){
                gameOverP2();
                break;
            }

            //Refresh Information Panel (panelLeft)
            Board.refreshPanelLeft();

        }

    }
    /**
    Geprüft wird immer ob das Icon des nächsten Feldes des Kopfes nicht "null" ist und nicht "der Apfel"
    wenn alle zutreffen wurde eine etwas getroffen
     */
    private boolean hasP1Collision(){
        int currentRow = p1Head.getRow();
        int currentCol = p1Head.getColumn();



        if(!isP1WallCollision) { //Wenn die Wand nicht durchschritten wird
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
        if (isP1WallCollision && !wallCrashEnabled){ //Wenn die Wand durch Schritten wird und WallCrash ausgeschaltet ist

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
    Geprüft wird immer ob das Icon des nächsten Feldes des Kopfes nicht "null" ist und nicht "der Apfel"
    wenn alle zutreffen wurde eine etwas getroffen
    */
    private boolean hasP2Collision(){
        int currentRow = p2Head.getRow();
        int currentCol = p2Head.getColumn();


        if(!isP2WallCollision) { //Wenn die Wand nicht durchschritten wird
            //left
            if (p2direction == 0
                    && Board.getFields()[currentRow][currentCol - 1].getIcon() != null
                    && Board.getFields()[currentRow][currentCol - 1].getIcon() != apple.getIcon()) return true;

            //top
            if (p2direction == 1
                    && Board.getFields()[currentRow - 1][currentCol].getIcon() != null
                    && Board.getFields()[currentRow - 1][currentCol].getIcon() != apple.getIcon()) return true;

            //right
            if (p2direction == 2
                    && Board.getFields()[currentRow][currentCol + 1].getIcon() != null
                    && Board.getFields()[currentRow][currentCol + 1].getIcon() != apple.getIcon()) return true;

            //bottom
            if (p2direction == 3
                    && Board.getFields()[currentRow + 1][currentCol].getIcon() != null
                    && Board.getFields()[currentRow + 1][currentCol].getIcon() != apple.getIcon()) return true;

        }
        if (isP2WallCollision && !wallCrashEnabled){ //Wenn die Wand durch Schritten wird und WallCrash ausgeschaltet ist

            //Left
            if (p2direction == 0 //gleiche Zeile, aber ganz rechts
                    && Board.getFields()[currentRow][Board.getColumns()-1].getIcon() != null
                    && Board.getFields()[currentRow][Board.getColumns()-1].getIcon() != apple.getIcon()) return true;

            //top
            if (p2direction == 1 //gleiche Spalte, aber ganz unten
                    && Board.getFields()[Board.getRows()-1][currentCol].getIcon() != null
                    && Board.getFields()[Board.getRows()-1][currentCol].getIcon() != apple.getIcon()) return true;

            //right
            if (p2direction == 2 //gleiche Zeile, aber ganz links
                    && Board.getFields()[currentRow][0].getIcon() != null
                    && Board.getFields()[currentRow][0].getIcon() != apple.getIcon()) return true;

            //bottom
            if (p2direction == 3 //gleiche Spalte, aber ganz oben
                    && Board.getFields()[0][currentCol].getIcon() != null
                    && Board.getFields()[0][currentCol].getIcon() != apple.getIcon()) return true;

        }

        //default
        return false;
    }

    /**
     * Prüft ob der Apfel gefressen wurde
     * @return 0 = Appfel nicht gefressen; 1 = Player 1 ; 2 = Player 2
     */
    private byte checkApple(){

        //Player 1
        if(p1Head.getRow() == apple.getRow() && p1Head.getColumn() == apple.getColumn())
            return 1;


        //Player 2
        if(p2Head.getRow() == apple.getRow() && p2Head.getColumn() == apple.getColumn())
            return 2;


        return 0;
    }

    private void moveP1Snake(){
        if (p1direction == 4) return;
        int tempRow = p1Head.getRow();  //speicheren die Letzt position des Kopfes
        int tempCol = p1Head.getColumn();
        int goalRow; //Ziel Kordinaten des Tails
        int goalCol;



        //Bewege den Kopf

        if (!isP1WallCollision)  //Wenn keine Wand getoffen wird
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
        else if (isP1WallCollision && !wallCrashEnabled)  // Wenn eine Wand getroffen wird und WallCrash ausgeschaltet ist
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
            throw new IllegalStateException("Unexpected value: " + isP1WallCollision);
        }


        //Bewege erstes Tail Element
        p1Tails.get(0).loadRemoveAt(tempRow, tempCol);

        // zieht den schlange körper hinter her
        for (int i = 1; i < p1Tails.size(); i++) {
            goalRow = p1Tails.get(i-1).getOldRow();
            goalCol = p1Tails.get(i-1).getOldColumn();

            p1Tails.get(i).loadRemoveAt(goalRow, goalCol);
        }

        //fügt am ende ein Schlangen körper an wenn Apfel gefressen wurde
        if (isP1AddMove){
            this.isP1AddMove = false;

            goalRow = p1Tails.get(p1Tails.size()-1).getOldRow();
            goalCol = p1Tails.get(p1Tails.size()-1).getOldColumn();

            SnakeTail snakeTail = new SnakeTail(p1snakeTailpath);
            snakeTail.loadAt(goalRow, goalCol);
            p1Tails.add(snakeTail);

        }
    }

    private void moveP2Snake(){
        if (p2direction == 4) return;
        int tempRow = p2Head.getRow();  //speicheren die Letzt position des Kopfes
        int tempCol = p2Head.getColumn();
        int goalRow; //Ziel Kordinaten des Tails
        int goalCol;



        //Bewege den Kopf

        if (!isP2WallCollision)  //Wenn keine Wand getoffen wird
        {
            if (p2direction == 0) //Eine Spalte nach links
                p2Head.loadRemoveAt(tempRow, tempCol - 1);

            if (p2direction == 1) //Eine Zeile nach oben
                p2Head.loadRemoveAt(tempRow - 1, tempCol);

            if (p2direction == 2) //Eine Spalte nach recht
                p2Head.loadRemoveAt(tempRow, tempCol + 1);

            if (p2direction == 3) //Eine Zeile nach unten
                p2Head.loadRemoveAt(tempRow + 1, tempCol);
        }
        else if (isP2WallCollision && !wallCrashEnabled)  // Wenn eine Wand getroffen wird und WallCrash ausgeschaltet ist
        {
            if (p2direction == 0) //Aktuelle Zeile, spalte ganz rechts
                p2Head.loadRemoveAt(tempRow, Board.getColumns()-1);

            if (p2direction == 1) //Zeile ganz unten, Aktuelle Spalte
                p2Head.loadRemoveAt(Board.getRows()-1, tempCol);

            if (p2direction == 2) //Aktuelle Zeile, Spalte ganz links
                p2Head.loadRemoveAt(tempRow, 0);

            if (p2direction == 3) //Zeile ganz oben, Aktuelle Spalte
                p2Head.loadRemoveAt(0, tempCol);
        } else {
            throw new IllegalStateException("Unexpected value: " + isP2WallCollision);
        }


        //Bewege erstes Tail Element
        p2Tails.get(0).loadRemoveAt(tempRow, tempCol);

        // zieht den schlange körper hinter her
        for (int i = 1; i < p2Tails.size(); i++) {
            goalRow = p2Tails.get(i-1).getOldRow();
            goalCol = p2Tails.get(i-1).getOldColumn();

            p2Tails.get(i).loadRemoveAt(goalRow, goalCol);
        }

        //fügt am ende ein Schlangen körper an wenn Apfel gefressen wurde
        if (isP2AddMove){
            this.isP2AddMove = false;

            goalRow = p2Tails.get(p2Tails.size()-1).getOldRow();
            goalCol = p2Tails.get(p2Tails.size()-1).getOldColumn();

            SnakeTail snakeTail = new SnakeTail(p2snakeTailpath);
            snakeTail.loadAt(goalRow, goalCol);
            p2Tails.add(snakeTail);

        }
    }

    private void initSnakes(){
        //Player One Yellow
        p1Head = new SnakeHead(p1snakeHeadpath);
        p1Head.loadAt(7,8);

        p1Tails.add(new SnakeTail(p1snakeTailpath));
        p1Tails.add(new SnakeTail(p1snakeTailpath));
        p1Tails.get(0).loadAt(7,9);
        p1Tails.get(1).loadAt(7,10);


        //Player Two Green
        p2Head = new SnakeHead(p2snakeHeadpath);
        p2Head.loadAt(11,8);

        p2Tails.add(new SnakeTail(p2snakeTailpath));
        p2Tails.add(new SnakeTail(p2snakeTailpath));
        p2Tails.get(0).loadAt(11,9);
        p2Tails.get(1).loadAt(11,10);
    }

    /**
     *
     * @return true Player 1 hat trifft Wand
     */
    private boolean hasP1WallCollision() {
        int currentRow = p1Head.getRow();
        int currentCol = p1Head.getColumn();

        /*
         * jetzige richtung und die position die im nächsten schritt erreicht wird werden kontrolliert
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

    /**
     *
     * @return Player 2 trifft Wand
     */
    private boolean hasP2WallCollision() {
        int currentRow = p2Head.getRow();
        int currentCol = p2Head.getColumn();

        /*
         * currentCol/Row + 1 die position die im nächsten schritt erreicht wird
         */


        //left
        if(p2direction == 0 && currentCol-1 < 0) return true;

        //top
        if(p2direction == 1 && currentRow-1 < 0) return true;

        //right
        if(p2direction == 2 && currentCol+1 >= Board.getRows()) return true;

        //bottom
        if(p2direction == 3 && currentRow+1 >= Board.getRows())return true;

        return false;
    }

    //Game overs
    private void gameOverP1() {
        JOptionPane.showMessageDialog(null,"Player 2 (Green) won!", "Winner", JOptionPane.PLAIN_MESSAGE);
    }
    private void gameOverP2() {
        JOptionPane.showMessageDialog(null,"Player 1 (Yellow) won!", "Winner", JOptionPane.PLAIN_MESSAGE);
    }

}
