package org.olbring.board;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.time.StopWatch;
import org.olbring.SnakeGame.Main;
import org.olbring.tools.Tools;

public class Board extends JFrame {

	private static JLabel[][] fields; //siehe erklärung im Consturctor
	private static JPanel contentPane;
	private static JPanel panelLeft; //hier werden Timer etc angezeigt
	private static JPanel panelPlayground; //hier ist das Spielfeld
	private static JLabel lblTimer; //Zeigt die vergange Zeit
	private static JLabel lblP1Score; //Zeigt den Score von Player 1
	private static JLabel lblP2Score; //Zeigt den Score von Player 2
	private static int rows = 20;
	private static int columns = 20;
	
	
	
	public Board() {
		/**Spielfeld erklärung:
		Das Spielfeld besteht aus zwei Panel ein Linkes panel an dem Informationen angezeigt werden und
		ein Playground Panel (rechts) auf dem gespielt wird

		das Playground Panel besitzt ein Gridlayout mit 20x20 (row x Columns) in jeder Zelle ist ein JLabel,
		diese Labels werden in dem 2D Array fields gespeichert, so kann man das Feld über eine
		 bestimmte "koordinate" aufrufen (fields[row][colum]).

		 die Schlangen, Äpfel und Hindernisse werden über Icon (19 x 19 Pixel) der JLabel dargestelt
		 */


		initFrame();
		initPlayground();
		setVisible(true);


	}
	

	
	/**
	 * Create the frame.
	 *
	 * contentPane 570x415
	 * panelPlayGround moet be 415x415 -> Icon 19,8 
	 *
	 */
	private void initFrame() {

//		addKeyListener(new Listener());
		setResizable(false);
		setTitle("SnakeGame by Vincent Olbring");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		//Informations Panel panelLeft
		panelLeft = new JPanel();
		panelLeft.setBackground(Color.DARK_GRAY);
		panelLeft.setBounds(0, 0, 155, 415);
		panelLeft.setLayout(new GridLayout(6,1,0,2));
		contentPane.add(panelLeft);

		//Spielfeld Panel hier liegt das Spiefeld
		panelPlayground = new JPanel();
		panelPlayground.setBackground(Color.DARK_GRAY);
		panelPlayground.setBounds(155, 0, 415, 415);
		panelPlayground.setLayout(new GridLayout(rows, columns, 1, 1));
		contentPane.add(panelPlayground);

		//hier drin wird die vergangene Zeit angezeigt
		lblTimer = new JLabel("TIMER");
		lblTimer.setForeground(Color.LIGHT_GRAY);
		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
		panelLeft.add(lblTimer);

		//hier drin wird der Score von Player 1 angezeigt
		lblP1Score = new JLabel();
		lblP1Score.setForeground(Color.LIGHT_GRAY);
		lblP1Score.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblP1Score.setHorizontalAlignment(SwingConstants.CENTER);
		panelLeft.add(lblP1Score);

		//hier drin wird der Score von Player 2 angezeigt
		lblP2Score = new JLabel();
		lblP2Score.setForeground(Color.LIGHT_GRAY);
		lblP2Score.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblP2Score.setHorizontalAlignment(SwingConstants.CENTER);
		panelLeft.add(lblP2Score);

	}
	/*
	 * Erstellt die Felder in der die Äpfel und teile der schlange abgebildet sind.
	 */
	
	private void initPlayground() {
		fields = new JLabel[rows][columns];
		
		for (int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < columns ; j++) {
				
				fields[i][j] = new JLabel(); 
				fields[i][j].setOpaque(true);
				fields[i][j].setBackground(Color.gray);
				panelPlayground.add(fields[i][j]);
			}
		}
		
	}

	public static void clearField(int row, int column) {
		fields[row][column].setIcon(null);
	}

	public static void refreshPanelLeft(){

		//Timer refresh
		StopWatch timer = Main.game.getTimer(); //holt sich den Timer
		long time = timer.getTime(TimeUnit.SECONDS);  //holt sich die Zeit
		lblTimer.setText(Tools.formatSecondsNicely(time));


		//Score Refresh
		//Singleplayer
		if (Main.gamemode.equalsIgnoreCase("SinglePlayer")){
			int score = Main.game.getP1score();
			lblP1Score.setText("Score: " + score);
		}
		//Multiplayer
		if (Main.gamemode.equalsIgnoreCase("MultiPlayer")){
			int scoreP1 = Main.game.getP1score();
			lblP1Score.setText("Score P1: " + scoreP1);

			int scoreP2 = Main.game.getP2score();
			lblP2Score.setText("Score P2: " + scoreP2);
		}
	}



	/*
	 * Getter und Setter
	 */
	public static JLabel[][] getFields() {
		return fields;
	}
	public static int getRows() {
		return rows;
	}

	public static int getColumns() {
		return columns;
	}
}
