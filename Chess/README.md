**This README is identical to the Instructions/README section included in the technical report, and
it may be easier to follow by referring to that document with better formatting etc**

Standard Procedure

Within the top layer of the submission folder, there is an executable jar file called FinalProject.jar which can be used to run the project. If for any reasons there are issues with the executable jar file, then the project can be compiled and run through the following path to the main class: Chess -> src -> game -> ChessGame. ChessGame is the main class of the project.

The game will be played in the standard terminal output, and upon running, the user will be presented with a chess board and clear instructions on how to start the game in either mode.
First, you will select the game mode: As the instructions say, you can type 0 and press enter to start a human vs human chess game, or type 1 for human vs computer.

Once you’ve chosen the game mode, player 1 will be prompted to make the first move. All moves made by humans will be done in 2 steps: First the player will select the piece they’d like to move using the alpha numeric coordinate of that piece on the board. Once you’ve pressed enter and confirmed a legal piece selection, you may then choose the destination of your move using the same syntax.

Move Example: If a player wants to move their pawn from ‘A6’ to ‘A4’ on the board, they will first enter A6 as the first choice in their move and they will click enter. If this is a legal piece for them to move, then they can choose the destination by typing A4 and pressing enter. The move will then be made, and the board will be presented in it’s altered state.

In a human vs human game, the exact same process will be presented to player 2, and the game will alternate turns and moves until the game is over.

In a human vs computer game, the human player will first be prompted to choose the desired depth of the AI algorithm. The larger the depth chosen, the “smarter” the AI will be, though it is recommended to use a depth of 2-4 for a good balance, as larger depths take far longer for the AI to compute its moves. After choosing the depth the human will then make human turns identically to as in the human vs human mode and wait after making their turn for the computer to compute its move choice and make that move. The human does not need to do anything other than wait after they’ve made their move in human vs computer mode.

Special move cases (piece promotion, check detection, etc.) will be computed automatically by the program and will output anything that happens to the user. These special cases will be explained further in the “Overview of the System” section of the report.
You are now ready to play a game of chess in either mode. Enjoy.

Altering The Starting Board

While the standard procedure is enough to run the program and play the game of chess, you may want to make alterations to the starting board set-up. If this is case, the following will detail how to make changes to add/remove/rearrange pieces on the starting board before the game starts.
Changes to the starting board will be made in the Board.java class, within the game folder in the project. Specifically, pieces are created and added to the board within the initialize() method in the Board class. Any pieces you wish to add can be added using the following line within the existing pieces being created, making changes to the underlined portions as desired:

gameBoard[x coord][y coord] = new Piece Type(new int[] {x coord, y coord},player number);

Note that the x coordinate corresponds to the x coordinates shown on the game board, but the y coordinate must correspond to the integer value of the letter shown on the game board. For example, a piece located at space “B3” will actually have x-y coordinates as [3,1]. 3 is the x coordinate which comes first, and B is equivalent to 1, as A is 0, B is 1, C is 2 etc. Piece type can be any of Pawn, Rook, Knight, Bishop, or Queen. Player number can be either 1 or 2.

