/*
 * @Author: Thejus Singh Jagadish
 * @Date: 02/12/2017
 * This class reads the input from the text file and places the chess pawns appropriately.
 * 
 */

package Chess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadMoves {
	
	String fileName = "/Users/thejussinghj/Documents/week1/Assignment1/src/Chess/Byrne_v_Fischer.txt";
	String line = null;
	static ArrayList<String> chessMoves = new ArrayList<String>();
	static int movesCount = 0;
	static int whiteMoveFromRow, whiteMoveFromCol, whiteMoveToRow, whiteMoveToCol, blackMoveFromRow, blackMoveFromCol,blackMoveToRow, blackMoveToCol; 
	static int clickCount = 0;
	static int castling = 0;
	static char promotionChar;
	static int promotion = 0;
	
	
	/*
	 * Constructor reads the input from the text file provided and stores it in the ArrayList
	 * 
	 */
	public ReadMoves(){
		try{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null){
				chessMoves.add(line);
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File not foud!! ");
		}
		catch(IOException e){
			System.out.println("Cannot read inputs!! ");
		}
		
	}
	
	
	/*
	 * This function reads a single line from the ArrayList and splits it into white and black moves
	 * 
	 * 
	 */
	public void printMoves(){
		String[] moves;
		moves = chessMoves.get(movesCount).split(" ");
		String[] whiteMoves;
		String[] blackMoves;
		whiteMoves = moves[0].split("-");
		blackMoves = moves[1].split("-");
		
		if(clickCount % 2 != 0){
			if(whiteMoves[0].charAt(0) != 'O'){
				castling = 0;
				whiteMoveFromRow = getRow(whiteMoves[0].charAt(1));
				whiteMoveFromCol = getCol(whiteMoves[0].charAt(0));
				whiteMoveToRow = getRow(whiteMoves[1].charAt(1));
				whiteMoveToCol = getCol(whiteMoves[1].charAt(0));
				System.out.println("Inside White");
				if(whiteMoves[1].length() == 5){
					System.out.println("Promotion");
					promotion = 1;
					promotionChar = whiteMoves[1].charAt(3);
				}
			}
			else{
				castling = 1;
				whitePawnCastling(whiteMoves);
			}
		}
		else{
			if(blackMoves[0].charAt(0) != 'O'){
				castling = 0;
				blackMoveFromRow = getRow(blackMoves[0].charAt(1)); 
				blackMoveFromCol = getCol(blackMoves[0].charAt(0));
				blackMoveToRow = getRow(blackMoves[1].charAt(1)); 
				blackMoveToCol = getCol(blackMoves[1].charAt(0));
				System.out.println("Inside Black");
				if(blackMoves[1].length() == 5){
					System.out.println("Promotion");
					promotion = 1;
					promotionChar = Character.toLowerCase(blackMoves[1].charAt(3));
							
							//blackMoves[1].charAt(3);
				}
			}
			else{
				castling = 1;
				blackPawnCastling(blackMoves);
			}
			
		}
	}
	
	
	/*
	 * This function performs both Queen side castling and King side castling 
	 * for white pawns
	 */
	
	private void whitePawnCastling(String[] whiteMoves) {
		if(whiteMoves.length == 2){
			Board.board[7][6] = 'A';
			Board.board[7][5] = 'R';
			Board.board[7][4] = ' ';
			Board.board[7][7] = ' ';
		}
		else{
			Board.board[7][2] = 'A';
			Board.board[7][3] = 'R';
			Board.board[7][0] = ' ';
			Board.board[7][4] = ' ';
		}
	}
	
	/*
	 * This function performs both Queen side castling and King side castling 
	 * for black pawns
	 */
	private void blackPawnCastling(String[] blackMoves) {
		if(blackMoves.length == 2){
			Board.board[0][6] = 'a';
			Board.board[0][5] = 'r';
			Board.board[0][4] = ' ';
			Board.board[0][7] = ' ';
		}
		else{
			Board.board[0][2] = 'a';
			Board.board[0][3] = 'r';
			Board.board[0][0] = ' ';
			Board.board[0][4] = ' ';
		}
	}
	

	private int getCol(char c) {
		
		int col = -1;
		switch(c){
			case 'A': 
				col = 0;
				break;
			case 'B':
				col = 1;
				break;
			case 'C':
				col = 2;
				break;
			case 'D':
				col = 3;
				break;
			case 'E':
				col = 4;
				break;
			case 'F':
				col = 5;
				break;
			case 'G':
				col = 6;
				break;
			case 'H':
				col = 7;
				break;
			default:
				break;
		}
		return col;
		
	}

	private int getRow(char c) {
		
		int row = -1;
		switch(c){
			case '1': 
				row = 7;
				break;
			case '2':
				row = 6;
				break;
			case '3':
				row = 5;
				break;
			case '4':
				row = 4;
				break;
			case '5':
				row = 3;
				break;
			case '6':
				row = 2;
				break;
			case '7':
				row = 1;
				break;
			case '8':
				row = 0;
				break;
		}
		return row;
		
	}

}
