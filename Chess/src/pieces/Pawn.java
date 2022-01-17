package pieces;

import game.Board;

/**Author: Jesse Masciarelli 6243109 
 * 
 * The Pawn is an extension of the abstract piece class. It has a unique set of rules
 * for determining its possible moves, and its own respective naming convention.
 */

public class Pawn extends Piece {


    public Pawn(int[] loc, int player) {
        super(loc, player);
        
        name = player+"P"; //name is player num followed by P for pawn

    }

    @Override
    public boolean[][] genPossibleMoves(Board b) {
        // pawns moves differ depending on which player
        boolean[][] possibleMoves = new boolean[8][8]; //array to be returned, initialized as all false
        int xCoord = location[0];
        int yCoord = location[1];

        if(playerNum == 1){ //player 1 pawns move "up"
            try { 
                int tempX=xCoord-1;
                int tempY=yCoord;
                if(b.getBoard()[tempX][tempY]==null){ //check one move going "up"
                    possibleMoves[tempX][tempY] = true;
                    tempX-=1;
                    if(xCoord==6){ //if we are in the pawns starting line, we can check for a double move, one more move "up"
                        if(b.getBoard()[tempX][tempY]==null){
                            possibleMoves[tempX][tempY] = true;
                        }
                    }
                }  
            } catch (Exception e) {}

            try {
                //now check up and right from the pieces starting location
                if(b.getBoard()[xCoord-1][yCoord+1]!=null && b.getBoard()[xCoord-1][yCoord+1].playerNum!=playerNum){
                    possibleMoves[xCoord-1][yCoord+1] = true;
                }
            } catch (Exception e) {}
            
            try {
                //now check up and left from the pieces starting location
                if(b.getBoard()[xCoord-1][yCoord-1]!=null && b.getBoard()[xCoord-1][yCoord-1].playerNum!=playerNum){
                    possibleMoves[xCoord-1][yCoord-1] = true;
                }
            } catch (Exception e) {}
        }


        if(playerNum == 2){ //player 2 pawns move "down"
            try { 
                int tempX=xCoord+1;
                int tempY=yCoord;
                if(b.getBoard()[tempX][tempY]==null){ //check one move going "up"
                    possibleMoves[tempX][tempY] = true;
                    tempX+=1;
                    if(xCoord==1){ //if we are in the pawns starting line, we can check for a double move, one more move "up"
                        if(b.getBoard()[tempX][tempY]==null){
                            possibleMoves[tempX][tempY] = true;
                        }
                    }
                }    
            } catch (Exception e) {}
            try {
                //now check down and right from the pieces starting location
                if(b.getBoard()[xCoord+1][yCoord+1]!=null && b.getBoard()[xCoord+1][yCoord+1].playerNum!=playerNum){
                    possibleMoves[xCoord+1][yCoord+1] = true;
                }
            } catch (Exception e) {}
            
            try {
                //now check down and left from the pieces starting location
                if(b.getBoard()[xCoord+1][yCoord-1]!=null && b.getBoard()[xCoord+1][yCoord-1].playerNum!=playerNum){
                    possibleMoves[xCoord+1][yCoord-1] = true;
                }
            } catch (Exception e) {}
        }


        return possibleMoves;
    }

    

}
