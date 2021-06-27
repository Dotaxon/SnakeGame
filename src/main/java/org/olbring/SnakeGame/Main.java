package org.olbring.SnakeGame;


import org.olbring.board.Board;


public class Main {
	public static Board board; //hier wird das Spielfeld gespeichert
	public static Game game;  //hier wird das Game gespeichert
	public static GUI gui;		//hier wird das Startfenster gespeichert
	public static String gamemode; //hier der Gamemode "multiplayer" oder "Singleplayer"

	
	public static void main(String[] args) {

		// Aus diese Schleife sorgt dafür, das man ohne großen aufwand immer neu spielen kann
		// hier wird auch das spiel raus gestartet
		while (true){

		board = new Board(); //erstellt das Fenster des Spielfeldes
		gui = new GUI();	//erstellt das Fenster des Einstellungs/Start fensters
		gui.checkBtnPlay(); //wird von hier aufgerufen damit gui nicht = null ist und objekt erstellung abgeschlossen wird
							//in dieser Funktion wird auch das spiel gestartet

		//Wenn man hier angekommen ist ist das Aktuelle spiel zuende
		board.dispose();



		//Zurück setzen der der Richtung
		// 4 heißt keine Bewegung
		Game.p1direction = 4;
		Game.p2direction = 4;
	}


		
	}
	/*
	To Do List:
	Highscore  ✓
	Timer 	✓
	Multiplayer ✓
	Speicher der Einstellungen ✓
	Wiederholtes spielen ✓
	Doppelklich ausführbar ✓
	Hindernisse ✓
	Todes animation

	*/
	





}
