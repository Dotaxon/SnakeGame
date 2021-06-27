package org.olbring.SnakeGame;

import org.olbring.tools.Tools;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.IOException;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JToggleButton btnPlay;
	private JComboBox comBoxDifficulty; // Geschwindigkeit (Schwierigkeit)
	private JComboBox comBoxWallCrash; //Stirbt man an Wänden
	private JComboBox comBoxGameMode; //hier drin wird der Gamemode gespeichert
	private JTextField txtFieldObstacleCount;  //hier drin steht die Anzahl an Obstacles
	private JLabel lblDifficulty;
	private JLabel lblWallCrash;

	private int[] settingsIndexArray; //Index 0 -> WallCrash ; Index 1 -> Difficulty ; Index 2 -> Gamemode ; Index 3 -> ObstacleCount
	private int beginDifficultyIndex;
	private int beginWallCrashIndex;
	private int beginGamemodeIndex;
	private int beginObstacleCount;
	private String settingsSaveFile = "snakeGame.txt";




	/**
	 *
	 *
	 * In dem GUI Fenster hast man die Möglichkeiten aller Hand einstellungen die relevant für das Spiel sind zutreffen
	 * diese Einstellungen werden in einer Datei für den nächsten Spiel aufruf gespeichert, dazu wird ein Integer
	 * Array mit folgender Sturktur erstellt:
	 *
	 * Index 0 -> comBoxWallCrash selected Item Index
	 * Index 1 -> comBoxDifficulty selceted Item Index
	 * Index 2 -> comBoxGamemode selected Item Index
	 * Index 3 -> txtFieldObstacle Count Inhalt wenn es sich um einen Falschen wert handelt wird 0 gespeichert
	 *
	 *
	 *
	 */
	public GUI() {
		initGamesettings(); //hier lädt man die gespeicheten einstellungen
		initFrame(); //hier wird der Frame erstellt
		setVisible(true);

	}

	/**
	 * hier werden die vorigen spiel einstellungen geladen
	 */
	private void initGamesettings(){

		//laden der eventul vorigen einstellungen geladen wenn es keine gab ist die rückgabe {0,0,0,0}
		settingsIndexArray = Tools.loadOldSettings(settingsSaveFile);


		beginWallCrashIndex = settingsIndexArray[0];
		beginDifficultyIndex = settingsIndexArray[1];
		beginGamemodeIndex = settingsIndexArray[2];
		beginObstacleCount = settingsIndexArray[3];
	}

	/**
	 * hier werden die spiel einstellung nach dieser Stucktur gespeichert:
	 *
	 * Index 0 -> comBoxWallCrash selected Item Index
	 * Index 1 -> comBoxDifficulty selceted Item Index
	 * Index 2 -> comBoxGamemode selected Item Index
	 * Index 3 -> txtFieldObstacle Count Inhalt wenn es sich um einen Falschen wert handelt wird 0 gespeichert
	 */
	private void saveGamesettings(){
		settingsIndexArray[0] = comBoxWallCrash.getSelectedIndex();
		settingsIndexArray[1] = comBoxDifficulty.getSelectedIndex();
		settingsIndexArray[2] = comBoxGameMode.getSelectedIndex();

		//Versucht Obstacle Count zu laden und zu int zu casten, sonst 0
		try {
			settingsIndexArray[3] = Integer.parseInt(txtFieldObstacleCount.getText());
		}catch (Exception e){
			settingsIndexArray[3] = 0;
		}


		try {
			Tools.saveOldSettings(settingsSaveFile, settingsIndexArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initFrame(){
		setTitle("SnakeGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Button
		btnPlay = new JToggleButton("Play");
		btnPlay.setBounds(74, 224, 89, 23);
		contentPane.add(btnPlay);

		//ComboBox Difficulty
		comBoxDifficulty = new JComboBox();
		comBoxDifficulty.setModel(new DefaultComboBoxModel(new String[] {"Easy", "Normal", "Hard"}));
		comBoxDifficulty.setSelectedIndex(beginDifficultyIndex);
		comBoxDifficulty.setBounds(74, 73, 89, 22);
		contentPane.add(comBoxDifficulty);
		//Label Difficulty
		lblDifficulty = new JLabel("Schwierigkeit:");
		lblDifficulty.setBounds(74, 59, 89, 14);
		contentPane.add(lblDifficulty);

		
		//ComboBox Wall Crash
		comBoxWallCrash = new JComboBox();
		comBoxWallCrash.setModel(new DefaultComboBoxModel(new String[] {"Ein", "Aus"}));
		comBoxWallCrash.setSelectedIndex(beginWallCrashIndex);
		comBoxWallCrash.setBounds(74, 24 ,89 , 22);
		contentPane.add(comBoxWallCrash);				
		//Label Wall Crash
		lblWallCrash = new JLabel("Wall Crash:");
		lblWallCrash.setBounds(74, 11, 89, 14);
		contentPane.add(lblWallCrash);


		//Label und ComBox Gamemode
		JLabel lblGameMode = new JLabel("Gamemode");
		lblGameMode.setBounds(74, 106, 89, 14);
		contentPane.add(lblGameMode);
		comBoxGameMode = new JComboBox();
		comBoxGameMode.setModel(new DefaultComboBoxModel(new String[] {"Singleplayer", "Multiplayer"}));
		comBoxGameMode.setSelectedIndex(beginGamemodeIndex);
		comBoxGameMode.setBounds(74, 120, 89, 22);
		contentPane.add(comBoxGameMode);


		//Label und textFiel für Obstacle count
		JLabel lblObstacleCount = new JLabel("Anzahl Hindernisse");
		lblObstacleCount.setBounds(74, 153, 125, 14);
		contentPane.add(lblObstacleCount);
		
		txtFieldObstacleCount = new JTextField();
		txtFieldObstacleCount.setText(String.valueOf(beginObstacleCount));
		txtFieldObstacleCount.setBounds(74, 166, 89, 20);
		contentPane.add(txtFieldObstacleCount);
		txtFieldObstacleCount.setColumns(1);






	}

	//Wenn Play Button (JToggleButton) gedrückt starte spiel
	void checkBtnPlay(){
	boolean isSelected;  //speichert den Zustand des Play Buttons, er ist ein Toggle Button
		while (true) {
			isSelected = btnPlay.isSelected(); //Holt sich den zustand des Play Buttons


			 //ohne Funktioniert es nicht das Programm scheint eine kurze verzögerung zu brauchen
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			if (isSelected == true) {
				break;
			}
		}
		Main.gui.dispose(); //lässt dieses Fenster verschwinden

		saveGamesettings(); //speichert die Einstellungen

		//erstellt das entsperechende Spiel
		if(comBoxGameMode.getSelectedItem().equals("Singleplayer")){
			Main.game = new SingelPlayerGame();
			Main.gamemode = "Singleplayer";
		}
		if (comBoxGameMode.getSelectedItem().equals("Multiplayer")){
			Main.game = new MultiplayerGame();
			Main.gamemode = "Multiplayer";
		}

		//Startet die playing Funktion des entsprechenden Spiels
		Main.game.playing(); //Wird von hier gestartet, damit Main.game nicht = null
	}

	public JComboBox getComBoxDifficulty() {
		return comBoxDifficulty;
	}

	public JComboBox getComBoxWallCrash() {
		return comBoxWallCrash;
	}

	public JTextField getTxtFieldObstacleCount() {
		return txtFieldObstacleCount;
	}
}
