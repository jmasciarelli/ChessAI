package game;

import pieces.*;

public class Board {

/**Author: Jesse Masciarelli 6243109
 * 
 * Board class defines how the chess board will be represented.
 * Boards are used to allow pieces to find their possible moves, and to
 * output the game state to the user.
*/

    Piece[][] gameBoard; //the raw board is stored as a 2D array of pieces
    
    public Board(){
        gameBoard = new Piece[8][8];
    }


/**This method will initiaize the board as the starting setup for a chess game, creating
 * all of necessary pieces and assigning ther starting locations
 */
public void initialize(){    
    //first create all p2 pieces and add them to the board
    for(int i=0; i<8; i++){ //add player 2 pawns
    int[] lp = {1,i};
    gameBoard[1][i] = new Pawn(lp,2);
    }
    gameBoard[0][0] = new Rook(new int[] {0,0},2);
    gameBoard[0][1] = new Knight(new int[] {0,1},2);
    gameBoard[0][2] = new Bishop(new int[] {0,2},2);
    gameBoard[0][3] = new Queen(new int[] {0,3},2);
    gameBoard[0][4] = new King(new int[] {0,4},2);
    gameBoard[0][5] = new Bishop(new int[] {0,5},2);
    gameBoard[0][6] = new Knight(new int[] {0,6},2);
    gameBoard[0][7] = new Rook(new int[] {0,7},2);

    //next create all p1 pieces and add them to the board
    for(int i=0; i<8; i++){ //add player 2 pawns
        int[] lp2 = {6,i};
        gameBoard[6][i] = new Pawn(lp2,1);
        }
        gameBoard[7][0] = new Rook(new int[] {7,0},1);
        gameBoard[7][1] = new Knight(new int[] {7,1},1);
        gameBoard[7][2] = new Bishop(new int[] {7,2},1);
        gameBoard[7][3] = new Queen(new int[] {7,3},1);
        gameBoard[7][4] = new King(new int[] {7,4},1);
        gameBoard[7][5] = new Bishop(new int[] {7,5},1);
        gameBoard[7][6] = new Knight(new int[] {7,6},1);
        gameBoard[7][7] = new Rook(new int[] {7,7},1);

}

/**This method, display, will be what converts the board list/array to a visual representation
 * that the user will seee and interact with
 */
    public void display(){
        System.out.println("     A   B   C   D   E   F   G   H");
        System.out.println("   ---------------------------------");
        for(int x=0; x<8; x++){
            System.out.print(x+"  |");
            for(int y=0; y<8; y++){
                if(gameBoard[x][y]!=null){
                    System.out.print(gameBoard[x][y].getName());
                    System.out.print(" |");
                }else{
                    System.out.print("   |");
                }
            }
            System.out.println("");
            if(x!=7)System.out.println("   ---------------------------------");
        }
        System.out.println("   ---------------------------------");
        System.out.println("");
    }

    public Piece[][] getBoard(){
        return gameBoard;
    }
    
}
