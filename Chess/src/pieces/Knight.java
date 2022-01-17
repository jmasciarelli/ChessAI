package pieces;

import game.Board;

/**Author: Jesse Masciarelli 6243109 
 * 
 * The Knight is an extension of the abstract piece class. It has a unique set of rules
 * for determining its possible moves, and its own respective naming convention.
 */

public class Knight extends Piece {


    public Knight(int[] loc, int player) {
        super(loc, player);
        
        name = player+"k"; //name is player num followed by P for pawn

    }

    @Override
    public boolean[][] genPossibleMoves(Board b) {
        boolean[][] possibleMoves = new boolean[8][8]; //array to be returned, initialized as all false
        int xCoord = this.location[0];
        int yCoord = this.location[1];

        //we will check each of the 8 possible "L" moves individually

        try {
            int tempX = xCoord-2; //2 spaces up
            int tempY = yCoord-1; //1 space left    
            if(b.getBoard()[tempX][tempY]==null){
                possibleMoves[tempX][tempY] = true;
            }else{
                if(b.getBoard()[tempX][tempY].playerNum!=playerNum)
                possibleMoves[tempX][tempY] = true;
            }
        } catch (Exception e) {}

        try {
            int tempX = xCoord-2; //2 spaces up
            int tempY = yCoord+1; //1 space right    
            if(b.getBoard()[tempX][tempY]==null){
                possibleMoves[tempX][tempY] = true;
            }else{
                if(b.getBoard()[tempX][tempY].playerNum!=playerNum)
                possibleMoves[tempX][tempY] = true;
            }
        } catch (Exception e) {}

        try {
            int tempX = xCoord-1; //1 space up
            int tempY = yCoord-2; //2 spaces left    
            if(b.getBoard()[tempX][tempY]==null){
                possibleMoves[tempX][tempY] = true;
            }else{
                if(b.getBoard()[tempX][tempY].playerNum!=playerNum)
                possibleMoves[tempX][tempY] = true;
            }
        } catch (Exception e) {}

        try {
            int tempX = xCoord+1; //1 space down
            int tempY = yCoord-2; //2 spaces left    
            if(b.getBoard()[tempX][tempY]==null){
                possibleMoves[tempX][tempY] = true;
            }else{
                if(b.getBoard()[tempX][tempY].playerNum!=playerNum)
                possibleMoves[tempX][tempY] = true;
            }
        } catch (Exception e) {}

        try {
            int tempX = xCoord-1; //1 space up
            int tempY = yCoord+2; //2 spaces right    
            if(b.getBoard()[tempX][tempY]==null){
                possibleMoves[tempX][tempY] = true;
            }else{
                if(b.getBoard()[tempX][tempY].playerNum!=playerNum)
                possibleMoves[tempX][tempY] = true;
            }
        } catch (Exception e) {}

        try {
            int tempX = xCoord+1; //1 space down
            int tempY = yCoord+2; //2 spaces right    
            if(b.getBoard()[tempX][tempY]==null){
                possibleMoves[tempX][tempY] = true;
            }else{
                if(b.getBoard()[tempX][tempY].playerNum!=playerNum)
                possibleMoves[tempX][tempY] = true;
            }
        } catch (Exception e) {}

        try {
            int tempX = xCoord+2; //2 spaces down
            int tempY = yCoord-1; //1 space left    
            if(b.getBoard()[tempX][tempY]==null){
                possibleMoves[tempX][tempY] = true;
            }else{
                if(b.getBoard()[tempX][tempY].playerNum!=playerNum)
                possibleMoves[tempX][tempY] = true;
            }
        } catch (Exception e) {}

        try {
            int tempX = xCoord+2; //2 spaces down
            int tempY = yCoord+1; //1 space right    
            if(b.getBoard()[tempX][tempY]==null){
                possibleMoves[tempX][tempY] = true;
            }else{
                if(b.getBoard()[tempX][tempY].playerNum!=playerNum)
                possibleMoves[tempX][tempY] = true;
            }
        } catch (Exception e) {}

        return possibleMoves;
    }

    

}
