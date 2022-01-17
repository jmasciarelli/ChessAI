package game;

import java.util.Scanner;
import pieces.Piece;
import pieces.Queen;

/**Author: Jesse Masciarelli 6243109
 * 
 * ChessGame is the main class of this project. It handles all of the functionality involved in
 * prompting the user for inputs, carrying out the chosen gamemode, and checking for changes in
 * the state of the game that require interference.
 * 
 * This class incorporates the board class to maintain a current game state, all piece classes to 
 * handle piece behaviour, and the heuristic class to allow the minimax algorithm AI to choose moves.
 */

public class ChessGame {
    
    int gameMode = -1; //0 for human vs human, 1 for human vs ai
    Scanner reader = new Scanner(System.in);
    Board board = new Board();

    Piece currentPiece; //current piece being manipulated
    int currentMoveStartX; //x coord of piece to be moved
    int currentMoveStartY; //y coord of piece to be moved
    int currentMoveEndX; //x coord of destination space
    int currentMoveEndY; //y coord of destination space
    int currentPlayer; //the number of the current player
    int treeDepth; //the users chosen tree spanning depth

    public ChessGame(){
        
        board.initialize();
        board.display();
        setMode();
        if(gameMode==0){
            playHumanVsHuman();
        }
        if(gameMode==1){
                treeDepth=0;
                while(treeDepth<1 | treeDepth>10){
                    System.out.println("DEPTH: Enter the desired depth between 1 and 10. *Depth over 4 will take 30+ seconds to compute*");
                    try{
                        treeDepth = reader.nextInt();
                    }catch(Exception e){reader.next();}
                }    
            playHumanVsAi();
        }
    }

/**This method prompts the user to enter the game mode, and sets the mode for the duration of
 * the programs execution.
 *  */    
    public void setMode(){
        while(gameMode != 0 && gameMode != 1){
            System.out.println("Choose Game Mode: \n 0 - Human vs Human \n 1 - Human vs AI");
            try{
                gameMode = reader.nextInt();
            }catch(Exception e){
                reader.next();
            }
        }
    }


/**this method is a helper for the human vs human game mode. It handles prompting for and
 * verifying the players start and end move choices.
 */
    public void getFullHumanTurn(){
        String pieceLocation;
        String pieceEnd;
        boolean failed = true;
        do {
            pieceLocation = getStartMoveInput();
            if(verifyStartingChoice(pieceLocation)){
                failed = false;
            }else{
                System.out.println("Either that is not your piece, or it can't be moved");
            }
        } while (failed);

        failed = true;
        do {
            pieceEnd = getEndMoveInput();
            if(verifyEndingChoice(pieceEnd)){
                failed = false;
            }else{
                System.out.println("You can't move there");
            }
        } while (failed);
    }

/**This method checks if the game is over simply by counting the number of kings on the board. 
 * 
 * @return True if there are still 2 kings, false otherwise
 */
    private boolean gameOver(Board b){
        int kingCount = 0;
        for(Piece[] row: b.gameBoard)
        for(Piece p: row)
        if(p!=null && (p.getName().equals("1K") | p.getName().equals("2K"))) kingCount+=1;
        return (kingCount==2)? false: true;
    }

/**This method checks if either players king is in check, and writes it out to the console if 
 * this is the case
 */
    public void checkExistsCheck(){
        Piece p = null;
        int kx = -1;
        int ky = -1; 
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                p = board.getBoard()[i][j];
                if(p!=null && (p.getName().equals("1K"))){
                    kx = i;
                    ky = j;
                }}}
        if(canBeHit(kx,ky)) System.out.println("! Player 1 is in check !");
        
        Piece p2 = null;
        int k2x = -1;
        int k2y = -1;
        for(int a=0; a<8; a++){
            for(int b=0; b<8; b++){
                p2 = board.getBoard()[a][b];
                if(p2!=null && (p2.getName().equals("2K"))){
                    k2x = a;
                    k2y = b;
                }}}
        if(canBeHit(k2x,k2y)) System.out.println("! Player 2 is in check !");
    }


/**This method will determine if a given location can be ht by any enemy piece on the board.
 * 
 * @param x the x coord of the desired location
 * @param y the y coord of the desired location
 * @return true if it can be hit, false otherwise
 */
    public boolean canBeHit(int x, int y){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++)
            if(board.getBoard()[i][j]!=null){
                board.getBoard()[i][j].setLocation(i, j);
                if(board.getBoard()[i][j].genPossibleMoves(board)[x][y]==true) return true;
            } 
        }
        return false;
    }

/**This method, whenever called, will check for either players pawn pieces being in place of
 * promotion on the board, and will recreate the pawn as a queen if it reaches promotion
 */
private void checkPromotions(){
    for(int i=0; i<8; i++){
        if(board.getBoard()[0][i]!=null){ //check top row for p1 pawns
            if(board.getBoard()[0][i].name.equals("1P")){ //if we find p1 pawn, create a queen to replace it
                int[] loc = new int[2];
                loc[0] = 0;
                loc[1] = i;
                Queen q = new Queen(loc,1);
                board.gameBoard[0][i] = q;
                System.out.println("Promotion has occured for P1");
                board.display();
            }
        } 
    }

    for(int i=0; i<8; i++){
        if(board.getBoard()[7][i]!=null){ //check bottom row for p2 pawns
            if(board.getBoard()[7][i].name.equals("2P")){ //if we find p1 pawn, create a queen to replace it
                int[] loc = new int[2];
                loc[0] = 7;
                loc[1] = i;
                Queen q = new Queen(loc,2);
                board.gameBoard[7][i] = q;
                System.out.println("Promotion has occured for P2");
                board.display();
            }
        } 
    }

}

/** This method simply takes in a board and a given move and computes and returns the new board
 * 
 * @param b the starting board
*  @param sx the x coord of the piece to move
 * @param sy the y coord of the piece to move
 * @param ex the x coord of the destination of the move
 * @param ey the y coord of the destination of the move
 * @return the board in its new state
 */
    public Board makeMove(Board b, int sx, int sy, int ex, int ey){
        Piece piece = b.getBoard()[sx][sy];
        Piece tempPiece = piece;
        Piece newPiece = tempPiece;
        newPiece.setLocation(ex, ey);
        Board newBoard = new Board();
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                newBoard.gameBoard[x][y] = b.getBoard()[x][y]; //create new board to score
        }}
        newBoard.gameBoard[ex][ey] = newPiece;
        newBoard.gameBoard[sx][sy] = null;
        return newBoard;
    }

/**This method represents the game mode where 2 humans play against each other. It will alternate
 * between player 1 and player 2, accepting moves until the game reaches an end state.
 */
    public void playHumanVsHuman(){
        currentPlayer = 1;
        while(true){
        System.out.println("---PLAYER "+currentPlayer+"'S TURN---");
        getFullHumanTurn();
        //now we have a full legal move
        board = makeMove(board, currentMoveStartX, currentMoveStartY, currentMoveEndX, currentMoveEndY);
        board.display();
        if(gameOver(board)) break;
        checkPromotions();
        checkExistsCheck();
        currentPlayer = (currentPlayer == 1) ? 2 : 1; //alternate turns
        }
    }

/**This method represents the game mode where 1 human plays against the AI. It will alternate
 * between player 1 and AI turns, accepting moves until the game reaches an end state.
 */
    private void playHumanVsAi(){ 
        while(true){ //continue until a game over state breaks out
        currentPlayer=1; //player 1 moves are obtained identically to a human vs human game
        System.out.println("---PLAYER 1'S TURN---");
        getFullHumanTurn();
        //now we have a full legal move
        board = makeMove(board, currentMoveStartX, currentMoveStartY, currentMoveEndX, currentMoveEndY);
        board.display();
        if(gameOver(board)) break; //end at game over state
        checkExistsCheck(); //inform the users of check
        checkPromotions(); //handle necessary pawn promotions

        currentPlayer=2;
        System.out.println("---AI IS COMPUTING IT'S TURN---");
        Board newBoard = new Board();
        for(int i=0; i<8; i++)
        for(int j=0; j<8; j++)
        newBoard.gameBoard[i][j] = board.getBoard()[i][j]; //create a clone board to compute minimax
        int[] aiMove = minimaxAB(newBoard, treeDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true); //initial minimax call
        board = makeMove(board, aiMove[1], aiMove[2], aiMove[3], aiMove[4]); //minimax return array holds the AIs chosen move
        board.display();
        if(gameOver(board)) break;
        checkExistsCheck();
        checkPromotions();
        }
        System.out.println("G A M E   O V E R");
    }

/**The minimax alpha-beta algorithm will compute the recursive tree searching algorithm required for
 * the AI to choose a legal move, using the heuristic calculator to determine the best move.
 * 
 * Alpha beta pruning is used to cut off "useless" branches and make computation quicker
 * 
 * @param b the chess board, which holds all of the pieces that can be moved
 * @param depth the number of layers to compute in tree searching
 * @param alpha initialized as neg infinity, to be used to store alpha values for pruning
 * @param beta initialized as pos infinity, to be used to store beta values for pruning
 * @param maximizingPlayer dictates whether the AI is computing its turn or the enemies turn
 * @return an integer array of length 5, including the 4 part move (x,y -> x,y) and its score
 */
    public int[] minimaxAB(Board b, int depth, int alpha, int beta, boolean maximizingPlayer){
        Heuristic hCalculator = new Heuristic();
        boolean terminalNode = gameOver(b); //check if board state is terminal
        int player = maximizingPlayer ? 2 : 1;
        int[] returnArray = new int[5]; //5 return values: Score, starting x, starting y, ending x, ending y
        if(depth == 0 || terminalNode){
            if(terminalNode){                
                    returnArray[0] = maximizingPlayer ? -1000 : 1000; //If game over and its "your turn", you've lost                   
                    return returnArray;            
            }
            else{
                returnArray[0] = hCalculator.getScore(b, maximizingPlayer); //return heuristic score                   
                return returnArray;
            }
        }

        if(maximizingPlayer){ //the AIs turn
            int value = -1*(Integer.MAX_VALUE); //initialize as worst possible score
            int moveChoiceSX = 0; //initialize the best move choice
            int moveChoiceSY = 0;  
            int moveChoiceEX = 0;   
            int moveChoiceEY = 0;            

            for (int x=0; x<8; x++) {
                for (int y=0; y<8; y++) {
                Piece p = b.getBoard()[x][y];
                if(p!=null && p.playerNum == player){ //if a piece is yours
                    int locationx = x;
                    int locationy = y;
                    p.setLocation(locationx, locationy);
                    boolean[][] options = p.genPossibleMoves(b); //our array of options for our current piece
                    for(int i=0; i<8; i++){
                        for(int j=0; j<8; j++){ 
                            if(options[i][j]){ //if we find a place we can go
                            int newValue = minimaxAB(makeMove(b,locationx,locationy,i,j), depth-1, alpha, beta, false)[0]; //recurse with this new board, returning the score
                            if(newValue > value){ //if we found a better move, return the score and its column
                                value = newValue;
                                moveChoiceSX = locationx;
                                moveChoiceSY = locationy;  
                                moveChoiceEX = i;   
                                moveChoiceEY = j;
                            }
                            //alpha beta pruning
                            alpha = Math.max(value, alpha);
                            if(alpha >= beta) break;

                            returnArray[0] = value;
                            returnArray[1] = moveChoiceSX; //column value will come from call before heuristic 
                            returnArray[2] = moveChoiceSY;
                            returnArray[3] = moveChoiceEX;
                            returnArray[4] = moveChoiceEY;                 
                }
            }}}}} //end for each loops   
            return returnArray;    
        }

        else{ //the human minimizing player, ssme function as maximizing, but aiming for lower scores
            int value = Integer.MAX_VALUE; //initialize as worst possible score
            int moveChoiceSX = 1; //initialize the best move choice
            int moveChoiceSY = 1;  
            int moveChoiceEX = 1;   
            int moveChoiceEY = 1;            
            for (int x=0; x<8; x++) {
                for (int y=0; y<8; y++) {
                Piece p = b.getBoard()[x][y];
                if(p!=null && p.playerNum == player){ //if a piece is yours
                    int locationx = x;
                    int locationy = y;
                    p.setLocation(locationx, locationy);
                    boolean[][] options = p.genPossibleMoves(b); //our array of options for our current piece
                    for(int i=0; i<8; i++){
                        for(int j=0; j<8; j++){ //if we can move the selected piece to this space
                            if(options[i][j]){ //if we find a place we can go
                            int newValue = minimaxAB(makeMove(b,locationx,locationy,i,j), depth-1, alpha, beta, true)[0]; //recurse with this new board, returning the score
                            if(newValue < value){ //if we found a better move, return the score and its column
                                value = newValue;
                                moveChoiceSX = locationx;
                                moveChoiceSY = locationy;  
                                moveChoiceEX = i;   
                                moveChoiceEY = j;
                            }
                            //alpha beta pruning
                            beta = Math.min(beta, value);
                            if(alpha >= beta) break;

                            returnArray[0] = value;
                            returnArray[1] = moveChoiceSX; //column value will come from call before heuristic 
                            returnArray[2] = moveChoiceSY;
                            returnArray[3] = moveChoiceEX;
                            returnArray[4] = moveChoiceEY;               
                }
            }}}}} //end for each loops   
            return returnArray; 
        }
    }

    /** This method will prompt the user for their piece choice in the form A0. It will ensure that
 * an existing board location is chosen.
 * 
 * @return the starting move location in the form A0
 */
public String getStartMoveInput(){
    String move = "x";
    while(!move.matches("A0|A1|A2|A3|A4|A5|A6|A7|B0|B1|B2|B3|B4|B5|B6|B7|C0|C1|C2|C3|C4|C5|C6|C7|D0|D1|D2|D3|D4|D5|D6|D7|E0|E1|E2|E3|E4|E5|E6|E7|F0|F1|F2|F3|F4|F5|F6|F7|G0|G1|G2|G3|G4|G5|G6|G7|H0|H1|H2|H3|H4|H5|H6|H7|")){
        System.out.println("Choose the piece to move using the format 'A0', you will not be able to change your choice");
        try{
            move = reader.next();
        }catch(Exception e){reader.next();}
    }
    return move;
}

/**this method will first verify that a chosen board location has the correct players piece,
* and then set the global current piece to that chosen piece.
* 
* @param move the chosen move in string form
* @param player the current player number
* @return
*/
public boolean verifyStartingChoice(String move){

int moveY = 1;
switch(move.substring(0,1)){ //convert Aalpha numeric choice to integer value
    case "A": moveY=0; break;
    case "B": moveY=1; break;
    case "C": moveY=2; break;
    case "D": moveY=3; break;
    case "E": moveY=4; break;
    case "F": moveY=5; break;
    case "G": moveY=6; break;
    case "H": moveY=7; break;
}
int moveX = Integer.parseInt(move.substring(1));
Piece piece = board.getBoard()[moveX][moveY]; //get the piece at the location (null if no piece)
if(piece!=null && piece.playerNum == currentPlayer){
    piece.setLocation(moveX, moveY);
    boolean[][] moves = piece.genPossibleMoves(board);
    for(boolean[] row: moves)
        for(boolean option: row) 
            if(option){//if there exists a possible move, set this as the move
                currentMoveStartX = moveX;
                currentMoveStartY = moveY;
                currentPiece = piece;
                return true;
            }
}
return false;
}

/** This method will prompt the user for their move destination choice in the form A0. It will ensure that
* an existing board location is chosen.
* 
* @return the move destination location in the form A0
*/
public String getEndMoveInput(){
String move = "x";
while(!move.matches("A0|A1|A2|A3|A4|A5|A6|A7|B0|B1|B2|B3|B4|B5|B6|B7|C0|C1|C2|C3|C4|C5|C6|C7|D0|D1|D2|D3|D4|D5|D6|D7|E0|E1|E2|E3|E4|E5|E6|E7|F0|F1|F2|F3|F4|F5|F6|F7|G0|G1|G2|G3|G4|G5|G6|G7|H0|H1|H2|H3|H4|H5|H6|H7|")){
    System.out.println("Choose the destination using the format 'A0'");
    try{
        move = reader.next();
    }catch(Exception e){reader.next();}
}
return move;
}

/**this method will verify that a chosen destination board location can be moved to by the current piece
* 
* @param move the chosen move in string form
* @return true if the currently chosen piece can move here, false otherwise
*/
public boolean verifyEndingChoice(String move){
int moveY = 1;
switch(move.substring(0,1)){
    case "A": moveY=0; break;
    case "B": moveY=1; break;
    case "C": moveY=2; break;
    case "D": moveY=3; break;
    case "E": moveY=4; break;
    case "F": moveY=5; break;
    case "G": moveY=6; break;
    case "H": moveY=7; break;
}
int moveX = Integer.parseInt(move.substring(1));
currentPiece.setLocation(currentMoveStartX, currentMoveStartY);
if(currentPiece.genPossibleMoves(board)[moveX][moveY] == true){
    currentMoveEndX = moveX;
    currentMoveEndY = moveY;
    return true;               
}
return false;
}
    public static void main(String[] args) throws Exception {new ChessGame();}
}
