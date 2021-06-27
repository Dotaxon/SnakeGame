package org.olbring.SnakeGame;

import org.apache.commons.lang3.time.StopWatch;
import org.olbring.board.Board;
import org.olbring.items.Apple;
import org.olbring.items.Obstacle;
import org.olbring.items.SnakeHead;
import org.olbring.items.SnakeTail;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Game {
    //Objekts
    protected SnakeHead p1Head;  //Yellow
    protected SnakeHead p2Head;  //Green
    protected Apple apple;
    protected StopWatch timer = new StopWatch();
    protected ArrayList<SnakeTail> p1Tails = new ArrayList<>();
    protected ArrayList<SnakeTail> p2Tails = new ArrayList<>();
    protected ArrayList<Obstacle> obstacles;

    //Paths
    protected String p1snakeHeadpath = "resources/SnakeHead.png";
    protected String p2snakeHeadpath = "resources/SnakeHeadPlayer2.png";
    protected String p1snakeTailpath = "resources/SnakeTail.png";
    protected String p2snakeTailpath = "resources/SnakeTailPlayer2.png";
    protected String applePath = "resources/Apple.png";
    protected String obstaclePath = "resources/obstacle.png";

    //running changes
    protected int p1score = 0;  // zählt wie viele Äpfel schon gefressen wurden
    protected int p2score = 0;
    public static byte p2direction = 4;
    public static byte p1direction = 4;   /*0 Left
	 							1 Top
	 							2 right
	 							3 bottom
	 							4 start
	 							*/
    //Game settings
    protected String difficulty;
    protected int speed;
    protected int easySpeed = 250;  //Speed is delay between the moves higher delay -> Easier
    protected int noramlSpeed = 120;
    protected int hardSpeed = 60;
    protected boolean wallCrashEnabled; //einstellung ob man an der Wand stirbt
    protected int obstacleCount;

    /**
     * Ein Game besteht aus mindesten einer Schlange und einem Apfel außerdem kann es Obstacles geben.
     *
     * Der Konsturktor dieser Klasse also der Oberklasse enthält so gut wie alle Variabeln auch wenn sie nicht
     * von Allen Unterklassen gebraucht werden.
     *
     * Zuerst wird der Apfel initalisiert, das heißt er wird an einer bestimmten stelle gespawnt
     *
     * Danch werden die GameSettings die Man sich aus dem Main.gui Objekt holt umgesetzt
     *
     * Dann wird der Timer gestartet
     *
     * nun gängt der Konsturktor der Unterklasse an
     *
     */

    public Game(){
        initApple();
        initGameSettings();
        initTimer();
    }


    /**
        In dieser Funktion wird es eine Schleife geben in der die Bewegungen so wie die Abfragen auf Kolision etc.
        abgefragt werden
     */
    protected abstract void playing();

    protected void initGameSettings(){
        String selectedItem = (String) Main.gui.getComBoxDifficulty().getSelectedItem();

        if (selectedItem.equals("Easy")){
            difficulty = "Easy";
            speed = easySpeed;
        }
        else if (selectedItem.equals("Normal")){
            difficulty = "Normal";
            speed = noramlSpeed;
        }
        else if (selectedItem.equals("Hard")){
            difficulty = "Hard";
            speed = hardSpeed;
        }

        selectedItem = (String) Main.gui.getComBoxWallCrash().getSelectedItem();

        if(selectedItem.equals("Ein")) wallCrashEnabled = true;
        if(selectedItem.equals("Aus")) wallCrashEnabled = false;


        try {//hier versucht man die Anzahl der Obstacles zuholen schaft man es nicht gibt es keine Obstacles
            obstacleCount = Integer.parseInt(Main.gui.getTxtFieldObstacleCount().getText());
        }
        catch (Exception e){
            e.printStackTrace();
            obstacleCount = 0;
        }

        //verhindet, das die Anzahl an Obstacles größer als 100 wird
        if (obstacleCount > 100){
            obstacleCount = 100;
        }
    }

    protected void initApple(){
        apple = new Apple(applePath);
        spwanApple();
    }

    /**
     * spwant einen Apfel an einer Zufälligen stelle, an der sich keine Schlange und kein Obstacle befindet
     */
    protected void spwanApple(){
        int randomRow;
        int randomCol;
        boolean success = false;

        do {
            randomRow = ThreadLocalRandom.current().nextInt(0, Board.getRows());
            randomCol = ThreadLocalRandom.current().nextInt(0,Board.getColumns());

            // guckt ob der Appfel an der Steller platziert werden darf
            if (Board.getFields()[randomRow][randomCol].getIcon() == null) {
                apple.loadAt(randomRow, randomCol);
                success = true;

                //neue Koordinaten zuweisen
                apple.setRow(randomRow);
                apple.setColumn(randomCol);
            }

        }while (!success);
    }

    /**
     * start den Spiel Timer
     */
    protected void initTimer(){
        timer.reset();
        timer.start();
    }


    /**
     * Spawn die Obstacles an Zufaelligen stellen, nur an stellen wo nichts ist
     *
     * @param count beschreibt die Anzahl an Obstacles
     */
    protected void spawnObstacles(int count){
        obstacles  = new ArrayList<>();
        int randRow, randCol;
        boolean succes = false; //gibt an ob obstacle schon platziert wurde

        for (int i = 0; i < count; i++) {

            succes = false; //obstacle wurde noch nicht platziert
            do {
                randRow = ThreadLocalRandom.current().nextInt(0, Board.getRows());
                randCol = ThreadLocalRandom.current().nextInt(0, Board.getColumns());


                //guckt ob obstacle an der stell platziert werden darf
                if (Board.getFields()[randRow][randCol].getIcon() == null) {

                    obstacles.add(new Obstacle(obstaclePath));

                    obstacles.get(i).loadAt(randRow, randCol);

                    succes = true; //obstacle wurde platziert
                }

            }while (!succes);
        }
    }

    /*
    Getter und Setter
     */
    public StopWatch getTimer() {
        return timer;
    }

    public int getP1score() {
        return p1score;
    }

    public int getP2score() {
        return p2score;
    }
}
