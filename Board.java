/*
 * @Author: Thejus Singh Jagadish
 * @Date: 02/12/2017
 * This class creates the GUI with pawns placed at appropriate positions
 * 
 */


package Chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Board extends JFrame implements ActionListener {
	
	JPanel [][] squares;
	static char[][] board = {
			{'r','k','b','q','a','b','k','r'},
			{'p','p','p','p','p','p','p','p'},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'P','P','P','P','P','P','P','P'},
			{'R','K','B','Q','A','B','K','R'}
	};
	
	ArrayList<char[][]> capturedPawn = new ArrayList<char[][]>();
	int pawnCapturedIndex = 0;
	int pawnCapturedCurrentIndex = 1;
	
	ReadMoves readMoves = new ReadMoves();
	
	char[][][] pawnCaptured = new char[ReadMoves.chessMoves.size()*2][8][8];
	
	static JButton previousButton, nextButton;
	JPanel buttonPanel;
	
	/*
	 * This constructor sets up the initial layout of the chess board
	 * 
	 */
	public Board(){
		JPanel chessBoardPanel = new JPanel();
		
		//Previous Button
		previousButton = new JButton("Prev");
		previousButton.setActionCommand("Prev");
		previousButton.setEnabled(false);
		previousButton.addActionListener( this );
		
		//Next Button
		nextButton = new JButton("Next");
		nextButton.setActionCommand("Next");
		nextButton.addActionListener( this );
		
		buttonPanel = new JPanel();
		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);
		
		
		Container chessBoard = getContentPane();
		chessBoardPanel.setLayout(new GridLayout(8,8,1,1)); 
        squares = new JPanel[8][8];
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                squares[i][j] = new JPanel();
                if((i+j)%2 == 0)
                    squares[i][j].setBackground(new Color(255,200,100));
                else
                    squares[i][j].setBackground(new Color(150,50,30));
                chessBoardPanel.add(squares[i][j]);
            }
        }
       
        chessBoard.add(chessBoardPanel);
        chessBoard.add(buttonPanel, BorderLayout.SOUTH);
        
        for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				arrangePawns(board[i][j],i,j);
			}
		}
        
        captureBoard(board, pawnCaptured[pawnCapturedIndex]);
        
	}


	
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonClicked(e.getActionCommand());
	}
	
	private void buttonClicked(String actionCommand) {
		switch(actionCommand){
			case "Prev":
				prevButtonClicked();
				break;
			case "Next":
				nextButtonClicked();
				break;
			default:
				break;
		}
	}
	
	
	
	private void nextButtonClicked() {
		ReadMoves.clickCount++;
		readMoves.printMoves();
		pawnCapturedIndex++;
		if(pawnCapturedIndex > 0){
			previousButton.setEnabled(true);
		}
		else if(pawnCapturedIndex == ReadMoves.chessMoves.size()*2 - 1){
			nextButton.setEnabled(false);
		}
		if(ReadMoves.castling == 0){
			if(pawnCapturedIndex == pawnCapturedCurrentIndex){
				
				if(ReadMoves.clickCount % 2 != 0){
					if(ReadMoves.promotion == 1){
						board[ReadMoves.whiteMoveToRow][ReadMoves.whiteMoveToCol] = ReadMoves.promotionChar;
						ReadMoves.promotion = 0;
					}
					
					else{
						board[ReadMoves.whiteMoveToRow][ReadMoves.whiteMoveToCol] = board[ReadMoves.whiteMoveFromRow][ReadMoves.whiteMoveFromCol];
					}
					board[ReadMoves.whiteMoveFromRow][ReadMoves.whiteMoveFromCol] = ' ';
				}
				else{
					if(ReadMoves.promotion == 1){
						board[ReadMoves.blackMoveToRow][ReadMoves.blackMoveToCol]  = ReadMoves.promotionChar;
						ReadMoves.promotion = 0;
					}
					else{
						System.out.println(ReadMoves.blackMoveToRow);
						System.out.println(ReadMoves.blackMoveToCol);
						System.out.println(ReadMoves.blackMoveFromRow);
						System.out.println(ReadMoves.blackMoveFromCol);
						board[ReadMoves.blackMoveToRow][ReadMoves.blackMoveToCol] = board[ReadMoves.blackMoveFromRow][ReadMoves.blackMoveFromCol];
					}
					board[ReadMoves.blackMoveFromRow][ReadMoves.blackMoveFromCol] = ' ';
					ReadMoves.movesCount++;
				}
				captureBoard(board, pawnCaptured[pawnCapturedCurrentIndex]);
				pawnCapturedCurrentIndex++;
			}
		}
		else{
			if(ReadMoves.clickCount % 2 == 0){
				ReadMoves.movesCount++;
			}
			captureBoard(board, pawnCaptured[pawnCapturedCurrentIndex]);
			pawnCapturedCurrentIndex++;
		}
		
		
		for(int i=0; i<8 ; i++){
			for(int j=0; j<8 ; j++){
				squares[i][j].removeAll();
				squares[i][j].revalidate();
				squares[i][j].repaint();
			}
		}
		
		
		for(int i=0; i<8 ; i++){
			for(int j=0; j<8 ; j++){
				arrangePawns(pawnCaptured[pawnCapturedIndex][i][j], i, j);
			}
		}	
	}
	
	
	
	
	private void prevButtonClicked() {
		pawnCapturedIndex--;
		if(pawnCapturedIndex == 0){
			previousButton.setEnabled(false);
		}
		else if(pawnCapturedIndex < pawnCapturedCurrentIndex){
			nextButton.setEnabled(true);
		}
		
		for(int i=0; i<8 ; i++){
			for(int j=0; j<8 ; j++){
				squares[i][j].removeAll();
				squares[i][j].revalidate();
				squares[i][j].repaint();
			}
		}
		
		for(int i=0; i<8 ; i++){
			for(int j=0; j<8 ; j++){
				arrangePawns(pawnCaptured[pawnCapturedIndex][i][j], i, j);
			}
		}
		
	}
	
	
	
	public void captureBoard(char[][] board2, char[][] cs) {
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				cs[i][j] = board2[i][j];
			}
		}
	}
	
	
	
	public void arrangePawns(char pawn, int i, int j){
		switch(pawn){
			case 'r': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/black_rook.png",i,j);
				break;
			case 'k': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/black_knight.png",i,j); 
				break;
			case 'b': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/black_bishop.png",i,j);
				break;
			case 'q': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/black_queen.png",i,j);
				break;
			case 'a': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/black_king.png",i,j);
				break;
			case 'p': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/black_pawn.png",i,j);
				break;
			case 'R': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/white_rook.png",i,j);
				break;
			case 'K': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/white_knight.png",i,j);
				break;
			case 'B': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/white_bishop.png",i,j);
				break;
			case 'Q': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/white_queen.png",i,j);
				break;
			case 'A': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/white_king.png",i,j);
				break;
			case 'P': 
				addToBoard("/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/white_pawn.png",i,j);
				break;
			default : break;
		}
	}
	
	
	
	public void addToBoard(String piece_name,int i, int j)
    {
		Icon image = new ImageIcon(piece_name);
		JLabel imageIcon = new JLabel();
		imageIcon.setIcon(image);
		squares[i][j].add(imageIcon);
		squares[i][j].revalidate();
		squares[i][j].repaint();
    }

}

