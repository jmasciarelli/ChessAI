package pieces;

import game.Board;

/**Author: Jesse Masciarelli 6243109 
 * 
 * The Queen is an extension of the abstract piece class. It has a unique set of rules
 * for determining its possible moves, and its own respective naming convention.
 */

public class Queen extends Piece {


    public Queen(int[] loc, int player) {
        super(loc, player);
        
        name = player+"Q"; //name is player num followed by P for pawn

    }

    @Override
    public boolean[][] genPossibleMoves(Board b) {
        boolean[][] possibleMoves = new boolean[8][8]; //array to be returned, initialized as all false
        
        int xCoord = this.location[0];
        int yCoord = this.location[1];

        try { //wrap in try catch for when we go out of bounds on the board
            int tempX=xCoord+1;
            int tempY=yCoord;
            while(b.getBoard()[tempX][tempY]==null){ //tempX increases to check all moves going "down"
                possibleMoves[tempX][tempY] = true;
                tempX+=1;
            }    
            if(b.getBoard()[tempX][tempY]!=null && b.getBoard()[tempX][tempY].playerNum != playerNum) //after reaching a player, see if its an enemy 
                possibleMoves[tempX][tempY] = true;
        } catch (Exception e) {}

        try { 
            int tempX=xCoord-1;
            int tempY=yCoord;
            while(b.getBoard()[tempX][tempY]==null){ //tempX increases to check all moves going "up"
                possibleMoves[tempX][tempY] = true;
                tempX-=1;
            }    
            if(b.getBoard()[tempX][tempY]!=null && b.getBoard()[tempX][tempY].playerNum != playerNum) //after reaching a player, see if its an enemy 
                possibleMoves[tempX][tempY] = true;
        } catch (Exception e) {}

        try { 
            int tempX=xCoord;
            int tempY=yCoord+1;
            while(b.getBoard()[tempX][tempY]==null){ //tempY increases to check all moves going "right"
                possibleMoves[tempX][tempY] = true;
                tempY+=1;
            }    
            if(b.getBoard()[tempX][tempY]!=null && b.getBoard()[tempX][tempY].playerNum != playerNum) //after reaching a player, see if its an enemy 
                possibleMoves[tempX][tempY] = true;
        } catch (Exception e) {}

        try { 
            int tempX=xCoord;
            int tempY=yCoord-1;
            while(b.getBoard()[tempX][tempY]==null){ //tempX increases to check all moves going "left"
                possibleMoves[tempX][tempY] = true;
                tempY-=1;
            }    
            if(b.getBoard()[tempX][tempY]!=null && b.getBoard()[tempX][tempY].playerNum != playerNum) //after reaching a player, see if its an enemy 
                possibleMoves[tempX][tempY] = true;
        } catch (Exception e) {}


        //now check diagonals

        try { //wrap in try catch for when we go out of bounds on the board
            int tempX=xCoord+1;
            int tempY=yCoord+1;
            while(b.getBoard()[tempX][tempY]==null){ //tempX and Y increases to check all moves going SE
                possibleMoves[tempX][tempY] = true;
                tempX+=1;
                tempY+=1;
            }    
            if(b.getBoard()[tempX][tempY]!=null && b.getBoard()[tempX][tempY].playerNum != playerNum) //after reaching a player, see if its an enemy 
                possibleMoves[tempX][tempY] = true;
        } catch (Exception e) {}

        try { 
            int tempX=xCoord-1;
            int tempY=yCoord-1;
            while(b.getBoard()[tempX][tempY]==null){ //tempX increases to check all moves going "NW"
                possibleMoves[tempX][tempY] = true;
                tempX-=1;
                tempY-=1;
            }    
            if(b.getBoard()[tempX][tempY]!=null && b.getBoard()[tempX][tempY].playerNum != playerNum) //after reaching a player, see if its an enemy 
                possibleMoves[tempX][tempY] = true;
        } catch (Exception e) {}

        try { 
            int tempX=xCoord-1;
            int tempY=yCoord+1;
            while(b.getBoard()[tempX][tempY]==null){ //tempY increases to check all moves going "NE"
                possibleMoves[tempX][tempY] = true;
                tempY+=1;
                tempX-=1;
            }    
            if(b.getBoard()[tempX][tempY]!=null && b.getBoard()[tempX][tempY].playerNum != playerNum) //after reaching a player, see if its an enemy 
                possibleMoves[tempX][tempY] = true;
        } catch (Exception e) {}

        try { 
            int tempX=xCoord+1;
            int tempY=yCoord-1;
            while(b.getBoard()[tempX][tempY]==null){ //tempX increases to check all moves going "SW"
                possibleMoves[tempX][tempY] = true;
                tempY-=1;
                tempX+=1;
            }    
            if(b.getBoard()[tempX][tempY]!=null && b.getBoard()[tempX][tempY].playerNum != playerNum) //after reaching a player, see if its an enemy 
                possibleMoves[tempX][tempY] = true;
        } catch (Exception e) {}
        
        return possibleMoves;
    }

    

}
