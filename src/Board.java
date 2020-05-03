import java.util.ArrayList;

public class Board {
    /*
    Board initialization key:
    X/O: these are the players
    -  : is an empty space on the board
    #  : will be a taken space
    */

    private char[][] board;

    Player player1;
    Player player2;
    ArrayList<String> log;


    /**
     * @param one - player 1
     * @param two - player 2
     */
    public Board(Player one, Player two){

        player1 = one;
        player2 = two;

        log = new ArrayList<>();

        board = new char[][]{
                {'X', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', 'O'}};

    }

    /**
     * Returns a copy of the board
     * @param b
     * @return copy
     */
    public static Board copyBoard(Board b){
        Board copy =  new Board(b.player1, b.player2);
        copy.board = b.getBoard().clone();
        return copy;
    }

    /**
     * @return board
     */
    public char[][] getBoard(){ return board; }

    /**
     * Adds a move to the log for printing player turns
     * @param move
     */
    public void addToLog(String move){
        log.add(move);
    }

    /**
     * This validates if the player's has open moves around it's position
     * @param p
     * @return boolean true
     */
    boolean terminalTest(Player p) {
        if (    this.isValidMove(p, p.move_diagonal_down_left())  ||
                this.isValidMove(p, p.move_diagonal_down_right()) ||
                this.isValidMove(p, p.move_diagonal_up_left())    ||
                this.isValidMove(p, p.move_diagonal_up_right())   ||
                this.isValidMove(p, p.move_down())                ||
                this.isValidMove(p, p.move_up())                  ||
                this.isValidMove(p, p.move_left())                ||
                this.isValidMove(p, p.move_right())               )
            return false;
        return true;
    }


    /**
     * This updates the board based on player's new position
     * @param player
     * @param move
     * #Desc
     */
    public void updateBoard(Player player, Move move){
        if(player == player1){
            this.getBoard()[player1.getX()][player1.getY()] = '#';
            player1.setXPosition(move.getX());
            player1.setYPosition(move.getY());
            this.getBoard()[player1.getX()][player1.getY()] = 'X';
        }
        else{
            this.getBoard()[player2.getX()][player2.getY()] = '#';
            player2.setXPosition(move.getX());
            player2.setYPosition(move.getY());
            this.getBoard()[player2.getX()][player2.getY()] = 'O';
        }
    }

    /**
     * Reverts a board state to its previous state given its previous move
     * @param player
     * @param move
     */
    public void undo_Move(Player player, Move move){
        if(player == player1){
            this.getBoard()[player1.getX()][player1.getY()] = '-';
            player1.setXPosition(move.getX());
            player1.setYPosition(move.getY());
            this.getBoard()[player1.getX()][player1.getY()] = 'X';
        }
        else{
            this.getBoard()[player2.getX()][player2.getY()] = '-';
            player2.setXPosition(move.getX());
            player2.setYPosition(move.getY());
            this.getBoard()[player2.getX()][player2.getY()] = 'O';
        }
    }

    /**
     * @param move
     * @return true if a move is out of bounds
     */
    boolean outOfBounds(Move move) {
        return (move.getX() < 0 || move.getX() > 7 || move.getY() < 0 || move.getY() > 7);
    }

    /**
     * @param player
     * @param move
     * @return true if a player's position and a desired move share a diagonal
     */
    boolean sharedDiagonal(Player player, Move move) {
        return Math.abs(move.getX() - player.getX()) == Math.abs(move.getY() - player.getY());
    }

    /**
     *
     * @param player
     * @param move
     * @return true if a player's position and a desired move share a column
     */
    boolean sharedCol(Player player, Move move){ return  player.getY() == move.getY(); }


    /**
     * @param player
     * @param move
     * @return true if a player's position and a desired move share a row
     */
    boolean sharedRow(Player player, Move move) {
        return player.getX() == move.getX();
    }

    /**
     * @param x
     * @param y
     * @return true if a desired position is filled
     */
    boolean isFilled(int x, int y) { return board[x][y] != '-'; }

   // boolean isAvailable(int x, int y) { return !isFilled(x, y); }

    /**
     * @param player
     * @param move
     * @return true if a player can move north
     */
    boolean canMoveNorth(Player player, Move move) {
            if (player.getX() > move.getX()) {
                for (int i = move.getX(); i < player.getX(); i++) {
                    if(isFilled(i, move.getY()))
                        return false;
                }
                return true;
            }
        return false;
    }

    /**
     * @param player
     * @param move
     * @return true if a player can move south
     */
    boolean canMoveSouth(Player player, Move move) {
        if (player.getX() < move.getX()) {
            for (int i = move.getX(); i > player.getX(); i--) {
                if(isFilled(i, move.getY()))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @param player
     * @param move
     * @return true if a player can move east
     */
    boolean canMoveEast(Player player, Move move) {
        if (player.getY() < move.getY()) {
            for (int i = move.getY(); i > player.getY(); i--) {
                if (isFilled(move.getX(), i))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @param player
     * @param move
     * @return true if a player can move west
     */
    boolean canMoveWest(Player player, Move move) {
        if (player.getY() > move.getY()) {
            for (int i = move.getY(); i < player.getY(); i++) {
                if (isFilled(move.getX(), i))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @param player
     * @param move
     * @return true if a player can move north east
     */
    boolean canMoveNorthEast(Player player, Move move) {
        if (player.getY() < move.getY() && player.getX() > move.getX()) {
            for (int i = 0;( move.getX() + i < player.getX()) && (move.getY() - i > player.getY()); i++){
                if (isFilled(move.getX() + i, move.getY() - i))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @param player
     * @param move
     * @return true if a player can move north west
     */
    boolean canMoveNorthWest(Player player, Move move) {
        if (player.getY() > move.getY() && player.getX() > move.getX()) {
            for (int i = 0; (move.getX() + i < player.getX()) && (move.getY() + i > player.getY()); i++) {
                if (isFilled(move.getX() + i, move.getY() + i))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @param player
     * @param move
     * @return true if a player can move south east
     */
    boolean canMoveSouthEast(Player player, Move move) {
        if (player.getY() < move.getY() && player.getX() < move.getX()) {
            for (int i = 0; move.getX() - i > player.getX() && move.getY() - i > player.getY(); i++) {
                if (isFilled(move.getX() - i, move.getY() - i))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @param player
     * @param move
     * @return true if a player can move south west
     */
    boolean canMoveSouthWest(Player player, Move move){
        if(player.getY() > move.getY() && player.getX() < move.getX()){
            for(int i = 0; move.getX() + i > player.getX() && move.getY() + i < player.getY(); i++){
                if(isFilled(move.getX() - i, move.getY() + i))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @param player
     * @param move
     * @return true if a player can move to a desired position
     */
    public boolean isValidMove(Player player, Move move){

        if(outOfBounds(move)){
            return false;
        }

        if(isFilled(move.getX(), move.getY()))
            return false;

        //if moving vertical
        if (sharedCol(player, move)){
            if(!(canMoveNorth(player, move) || canMoveSouth(player, move)))
                return false;
        }

        //if moving horizontal
        if (sharedRow(player, move)) {
            if (!(canMoveEast(player, move) || canMoveWest(player, move)))
                return false;
        }

        //if moving diagonal
        if(sharedDiagonal(player, move)) {
            if (!(canMoveNorthEast(player, move) ||
                    canMoveNorthWest(player, move) ||
                    canMoveSouthEast(player, move) ||
                    canMoveSouthWest(player, move)))
                return false;
        }
        return true;
    }

    /**
     * Prints the board
     * @return board.toString()
     */
    public String toString(){
        String p1, p2;
        if(player1.getAI()){
            p1 = "Computer";
            p2 = "Opponent";
        }
        else{
            p2 = "Computer";
            p1 = "Opponent";
        }
        String board = "  | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 "   + p1 + " vs. " + p2;
        for(int i = 0; i < 15; i++){
            if (i < 8)
            board += ("\n" + (char)(65 + i)) + " ";
            for(int j = 0; j < 8; j++){
                if (i < 8)
                board += ("| " + this.board[i][j] + " ");
            }
            if (!log.isEmpty() && (i * 2) < log.size()) {
                if (i > 7)
                    board += ("\n               " +
                            "                   ");

                board += ("     " + log.get(i * 2));

            if ((i * 2) + 1 < log.size())
                board += ("           " + log.get((i*2) + 1));
            }

        }
        return board;
    }
}
