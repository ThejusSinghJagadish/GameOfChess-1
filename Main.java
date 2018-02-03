/*
 * @Author: Thejus Singh Jagadish
 * @Date: 02/12/2017
 * 
 */
package Chess;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board chessBoard = new Board();
		chessBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chessBoard.setSize(600, 600);
		chessBoard.setVisible(true);
		chessBoard.setResizable(false);
	}

}
