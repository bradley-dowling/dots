// *********************************
// ** Bradley Dowling - CSE223 - PA4 - 5/20/2021
// **
// ** This is a programming assignment that explores Swing and JFrames.
// ** Dots.java is a program that opens a window and allows 2 users
// ** to play a game of "dots and boxes". The components of the window
// ** are here within Dots.java, while the extension of a JPanel that
// ** the users click within is in MyPanel.java. The code that controls
// ** the behavior of the individual boxes is housed within Box.java.
// **


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JTextArea;

public class Dots extends JFrame {

	private JPanel contentPane;
	private MyPanel board;
	private JTextField player1NameField;
	private JTextField player2NameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dots frame = new Dots();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Dots() {
		setResizable(false);
		
		setTitle("Dots");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 524, 663);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// board is the actual game board that the user will play on:
		board = new MyPanel();
		board.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		board.setBounds(12, 12, 500, 500);
		contentPane.add(board);
		
		// player information components:
		player1NameField = new JTextField();
		player1NameField.setBounds(12, 532, 85, 20);
		contentPane.add(player1NameField);
		player1NameField.setColumns(10);
		
		JLabel player1Label = new JLabel("Player 1:");
		player1Label.setBounds(12, 515, 66, 15);
		contentPane.add(player1Label);
		
		JLabel player2Label = new JLabel("Player 2:");
		player2Label.setBounds(12, 559, 66, 15);
		contentPane.add(player2Label);
		
		player2NameField = new JTextField();
		player2NameField.setBounds(12, 576, 85, 20);
		contentPane.add(player2NameField);
		player2NameField.setColumns(10);
		
		// score information components:
		JLabel lblScore = new JLabel("SCORE");
		lblScore.setFont(new Font("Dialog", Font.BOLD, 16));
		lblScore.setBounds(226, 524, 66, 15);
		contentPane.add(lblScore);
		
		JLabel player1Score = new JLabel("0");
		player1Score.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		player1Score.setFont(new Font("Courier 10 Pitch", Font.BOLD, 30));
		player1Score.setHorizontalAlignment(SwingConstants.CENTER);
		player1Score.setHorizontalTextPosition(SwingConstants.CENTER);
		player1Score.setBounds(173, 546, 50, 50);
		contentPane.add(player1Score);
		
		JLabel player2Score = new JLabel("0");
		player2Score.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		player2Score.setHorizontalTextPosition(SwingConstants.CENTER);
		player2Score.setHorizontalAlignment(SwingConstants.CENTER);
		player2Score.setFont(new Font("Courier 10 Pitch", Font.BOLD, 30));
		player2Score.setBounds(289, 546, 50, 50);
		contentPane.add(player2Score);
		
		JLabel player1ScoreLabel = new JLabel("P1");
		player1ScoreLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		player1ScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player1ScoreLabel.setBounds(173, 524, 50, 15);
		contentPane.add(player1ScoreLabel);
		
		JLabel player2ScoreLabel = new JLabel("P2");
		player2ScoreLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		player2ScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player2ScoreLabel.setBounds(289, 524, 50, 15);
		contentPane.add(player2ScoreLabel);
		
		// messageField is the component that will display a variety of messages to the user
		// regarding who's turn it is, if the game is over, if a move is valid or not, etc...
		JLabel messageField = new JLabel("Enter player names and then press Start to begin...");
		messageField.setHorizontalAlignment(SwingConstants.TRAILING);
		messageField.setBounds(12, 611, 498, 15);
		contentPane.add(messageField);
		
		
		// action listeners:
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// pressing 'Start' or 'Restart' button
				
				// First, check if either player1NameField or player2NameField is blank/empty
				if (player1NameField.getText().isEmpty() || player2NameField.getText().isEmpty()) {
					messageField.setText("You must enter player names...");
				} else {
					// if not empty, check to see if board has already been initialized at some point
					// if it has, this is a 'restart'. if not, this is a new 'start' to the game
					if (board.hasBeenInitialized) {
						board.resetBoard();
					} else {
						board.initializeBoard();
						player1NameField.setEditable(false);
						player2NameField.setEditable(false);
					}
					// set the players initials and then set up the score display appropriately
					board.setPlayerInitials(player1NameField.getText(), player2NameField.getText());
					player1ScoreLabel.setText(board.getPlayer1Initials());
					player2ScoreLabel.setText(board.getPlayer2Initials());
					player1Score.setText("" + board.getPlayer1Score());
					player2Score.setText("" + board.getPlayer2Score());
					
					startButton.setText("Restart"); // set the startButtons text to 'Restart'
					messageField.setText(board.getCurrentPlayerInitials() + "'s turn..."); // display that it is player 1's turn first
					board.repaint();
				}
			}
		});
		startButton.setBounds(422, 524, 90, 50);
		contentPane.add(startButton);
		
		board.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// First, check if game has been initialized...
				if (!board.hasBeenInitialized) {
					messageField.setText("Uh, you gotta start the game first...");
					return;
				}
				
				// Next, check if the game is already over...
				if (board.gameOver) return;
				
				// Next, make sure that the user has clicked inside the actual margins of the board...
				if (e.getX() >= 50 && e.getX() <= 450) {
					if(e.getY() >= 50 && e.getX() <= 450) {
						// user has clicked within the board, check if this was a valid move and act accordingly
						
						if (!board.playerTurn(e.getX(), e.getY())) {
							// invalid move, let the user know about (nicely)
							switch(board.invalidPlays) {
							case 1:
								messageField.setText("Invalid move, try again there " + board.getCurrentPlayerInitials() + "...");
								break;
							case 2:
								messageField.setText("Yeah, you're gonna have to try that again " + board.getCurrentPlayerInitials() + "...");
								break;
							case 3:
								messageField.setText("Dude... c'mon " + board.getCurrentPlayerInitials() + "...");
								break;
							case 4:
								messageField.setText("DUDE!!... C'MON " + board.getCurrentPlayerInitials() + "...");
								break;
							case 5:
								messageField.setText("...Yeah " + board.getCurrentPlayerInitials() + ", you get one more chance before I flip this board over...");
								break;
							case 6:
								// follow steps to reset the board because user CAN'T BE BOTHERED TO PLAY THE GAME!
								board.resetBoard();
								board.setPlayerInitials(player1NameField.getText(), player2NameField.getText());
								player1ScoreLabel.setText(board.getPlayer1Initials());
								player2ScoreLabel.setText(board.getPlayer2Initials());
								player1Score.setText("" + board.getPlayer1Score());
								player2Score.setText("" + board.getPlayer2Score());
								startButton.setText("Restart");
								messageField.setText(board.getCurrentPlayerInitials() + "'s turn...");
								board.repaint();
							}
						} else {
							// this was a valid move and the board has been adjusted
							board.repaint();
							
							// check if the game is over and adjust the display if it has been
							if (board.gameOver) {
								if (board.getPlayer1Score() > board.getPlayer2Score()) {
									messageField.setText("GAME OVER! " + board.getPlayer1Initials() + " wins!");
								} else if (board.getPlayer2Score() > board.getPlayer1Score()) {
									messageField.setText("GAME OVER! " + board.getPlayer2Initials() + " wins!");
								} else {
									messageField.setText("GAME OVER! IT'S A TIE!");
								}
							} else {
								// game not yet over, so continue on...
								player1Score.setText("" + board.getPlayer1Score());
								player2Score.setText("" + board.getPlayer2Score());
								messageField.setText(board.getCurrentPlayerInitials() + "'s turn...");
							}
						}
					}
				}
			}
		});
	}
}
