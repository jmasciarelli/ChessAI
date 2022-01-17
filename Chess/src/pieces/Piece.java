package pieces;

import game.Board;

/**Author: Jesse Masciarelli 6243109
 * 
 * The abstract class piece will be extended by all specific piece types.
 * Every piece will have a location (x and y coordinates), a player number (1 or 2)
 */
public abstract class Piece {

    public int[] location;
    public int playerNum;
    public String name; //the piece name will be a number followed by a letter to denote player and type
    
    public Piece(int[] loc, int player) {
        location = loc;
        playerNum = player;
    }

/**genPossibleMoves() will create a board of booleans corresponding to where
 * the current piece can move based on a given board
 * @param b the current game board
 */
public abstract boolean[][] genPossibleMoves(Board b);


/** setLocation allows for setting the location of a piece when moved */
    public void setLocation(int x, int y){
        location[0] = x;
        location[1] = y;
    }

/** getName returns the name of the piece as a string. */
    public String getName(){
        return name;
    }
   
/** getLocation returns the location of the piece. */
    public int[] getLocation(){
        return location;
    }

}
