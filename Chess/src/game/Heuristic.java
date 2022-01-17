package game;

import pieces.Piece;

/**Author: Jesse Masciarelli 6243109 
 * 
 * This heuristic class makes use of multiple functions to score the state of a given chess
 * board. It will be used by the minimaxAB algorithm in the main class to allow the AI to pick
 * the best possible move.
 */


public class Heuristic {

    public int getScore(Board b, boolean AIplayer){
        int p1 = AIplayer? 2 : 1; 
        int p2 = AIplayer? 1 : 2;
        int score = 0; //start at 0 and increase/decrease the score as advantages/disadvantages are spotted
        if(checkIfInCheck(b, p1)) score -= 100; //lose points for being in check
        if(checkIfInCheck(b, p2)) score += 100; //gain points for having enemy in check
        score += evaluateBoard(b, p1);
        score -= 1.25*evaluateBoard(b, p2); //add weight to defence over offence
        return score;
    }

/**This method computes a score based on the given players advantages on the board. It will can be
 * used for either given player, and will score how "good" they look in the current state of the 
 * given board. This is a 3 part system, first evaluating the score based on each players material
 * advantage, then evaluating centre control, and lastly evaluating king safety using helper methods.
 * 
 * @param b the board
 * @param pNum the player to score
 * @return the final score as an integer
 */
    public int evaluateBoard(Board b, int pNum){
        int score = 0;
        //First evaluate board piecewise based on the weight of your pieces on the board vs enemy pieces
        //Here, king safety evaluation also happens to strengthen the heuristic
        for(Piece[] row: b.getBoard()){
            for(Piece p: row){
                    if(p!=null){
                        if(p.playerNum == pNum){
                            switch(p.getName().substring(1)){
                                case "P": score+=2; break;
                                case "R": score+=12; break;
                                case "k": score+=6; break;
                                case "B": score+=6; break;
                                case "Q": score+=18; break;
                                case "K": //when we find the p1 king, evaluate king safety
                                int locx = p.getLocation()[0];
                                int locy = p.getLocation()[1];
                                if(pNum==1) score+=scoreP1KingSafety(b,locx,locy); 
                                else score+=scoreP2KingSafety(b,locx,locy);
                                break;
                            }
                         }else{
                            switch(p.getName().substring(1)){
                                case "P": score-=2; break;
                                case "R": score-=12; break;
                                case "k": score-=6; break;
                                case "B": score-=6; break;
                                case "Q": score-=18; break;
                                case "K": //when we find the p2 king, evaluate king safety
                                int locx = p.getLocation()[0];
                                int locy = p.getLocation()[1];
                                if(pNum==1) score-=scoreP2KingSafety(b,locx,locy); 
                                else score-=scoreP1KingSafety(b,locx,locy);
                                break;
                         }
                        }             
                    }
            }
        }
        // Now we increase the score for the those pieces that are in a dominant postion on the board (central)
        for(int i=0; i<8; i++){
                if(b.getBoard()[3][i]!=null && b.getBoard()[3][i].playerNum == pNum) score+=4; //4th row is dominant centre control
                else score-=4;
                if(b.getBoard()[4][i]!=null && b.getBoard()[4][i].playerNum == pNum) score+=4; //5th row is dominant centre control
                else score-=4;
                if(b.getBoard()[2][i]!=null && b.getBoard()[2][i].playerNum == pNum) score+=2; //3rd row is less dominant, though is stronger than backtracking
                else score-=2;
                if(b.getBoard()[5][i]!=null && b.getBoard()[5][i].playerNum == pNum) score+=2; //6th row is less dominant, though is stronger than backtracking
                else score-=2;
        }

        return score;
    }


/**This method is used in the evaluate function, and determines the safety of player 1s king.
 * This is an evaluation method described further in the project report, and add/subtracts score
 * based on the player 1 king and its surrounding area
 * 
 * @param b the board
 * @param x the x coordinate of player 1 king
 * @param y the y coordinate of player 1 king
 * @return the king safety score
 */
public int scoreP1KingSafety(Board b, int x, int y){
    int s = 0; //score
    try {
        if(b.getBoard()[x-1][y-1].playerNum == 1) s+=4; //check row of 3 dominant positions in front of king
        else s-=4; //if the piece is found, and is not null, and is not p1, its an enemy
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x-1][y].playerNum == 1) s+=4; 
        else s-=4;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x-1][y+1].playerNum == 1) s+=4; 
        else s-=4;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x-2][y-2].playerNum == 1) s+=2; //check row of 5 less dominant positions in front of king
        else s-=2;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x-2][y-1].playerNum == 1) s+=2; 
        else s-=2;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x-2][y].playerNum == 1) s+=2; 
        else s-=2;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x-2][y+1].playerNum == 1) s+=2; 
        else s-=2;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x-2][y+2].playerNum == 1) s+=2; 
        else s-=2;
    } catch (Exception e) {}

    return s;
}

/**This method is used in the evaluate function, and determines the safety of player 2s king.
 * This is an evaluation method described further in the project report, and add/subtracts score
 * based on the player 2 king and its surrounding area
 * 
 * @param b the board
 * @param x the x coordinate of player 2 king
 * @param y the y coordinate of player 2 king
 * @return the king safety score
 */
public int scoreP2KingSafety(Board b, int x, int y){
    int s = 0; //score
    try {
        if(b.getBoard()[x+1][y-1].playerNum == 1) s+=4; //check row of 3 dominant positions in front of king
        else s-=4; //if the piece is found, and is not null, and is not p1, its an enemy
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x+1][y].playerNum == 1) s+=4; 
        else s-=4;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x+1][y+1].playerNum == 1) s+=4; 
        else s-=4;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x+2][y-2].playerNum == 1) s+=2; //check row of 5 less dominant positions in front of king
        else s-=2;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x+2][y-1].playerNum == 1) s+=2; 
        else s-=2;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x+2][y].playerNum == 1) s+=2; 
        else s-=2;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x+2][y+1].playerNum == 1) s+=2; 
        else s-=2;
    } catch (Exception e) {}
    try {
        if(b.getBoard()[x+2][y+2].playerNum == 1) s+=2; 
        else s-=2;
    } catch (Exception e) {}

    return s;
}

/**This method checks if either players king is in check, and writes it out to the console if 
 * this is the case
 * 
 * @param b the board
 */
public boolean checkIfInCheck(Board b, int player){
    String name;
    name = (player==1 ? "1K": "2K");
    Piece p = null;
    int kx = -1;
    int ky = -1; 
    for(int i=0; i<8; i++){
        for(int j=0; j<8; j++){
            p = b.getBoard()[i][j];
            if(p!=null && (p.getName().equals(name))){
                kx = i;
                ky = j;
            }}}
    if(canBeHit(b,kx,ky)) return true;;
    
    return false;
}
    
/**This method will determine if a given location can be ht by any enemy piece on the board.
 * 
 * @param x the x coord of the desired location
 * @param y the y coord of the desired location
 * @return true if it can be hit, false otherwise
 */
public boolean canBeHit(Board b, int x, int y){
    for(int i=0; i<8; i++){
        for(int j=0; j<8; j++)
        if(b.getBoard()[i][j]!=null){
            b.getBoard()[i][j].setLocation(i, j);
            if(b.getBoard()[i][j].genPossibleMoves(b)[x][y]==true) return true;
        } 
    }
    return false;
}

}
