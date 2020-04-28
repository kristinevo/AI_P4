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


    public Board(Player one, Player two){

        player1 = one;
        player2 = two;

        log = new ArrayList<String>();

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

    public static Board copyBoard(Board b){
        Board copy =  new Board(b.player1, b.player2);
        copy.board = b.getBoard().clone();
        return copy;
    }

    public char[][] getBoard(){ return board; }

    public void addToLog(String move){
        log.add(move);
    }

    boolean terminalTest(Player p) {
        if (    this.isNotValidMove(p, p.move_diagonal_down_left())  ||
                this.isNotValidMove(p, p.move_diagonal_down_right()) ||
                this.isNotValidMove(p, p.move_diagonal_up_left())    ||
                this.isNotValidMove(p, p.move_diagonal_up_right())   ||
                this.isNotValidMove(p, p.move_down())                ||
                this.isNotValidMove(p, p.move_up())                  ||
                this.isNotValidMove(p, p.move_left())                ||
                this.isNotValidMove(p, p.move_right())               )
            return false;
        return true;
    }

    //update board based on player's new position
    public void updateBoard(Player player, Move move){
        if(player == player1){
            this.getBoard()[player1.getXPosition()][player1.getYPosition()] = '#';
            player1.setXPosition(move.getX());
            player1.setYPosition(move.getY());
            this.getBoard()[player1.getXPosition()][player1.getYPosition()] = 'X';
        }
        else{
            this.getBoard()[player2.getXPosition()][player2.getYPosition()] = '#';
            player2.setXPosition(move.getX());
            player2.setYPosition(move.getY());
            this.getBoard()[player2.getXPosition()][player2.getYPosition()] = 'O';
        }
    }

    public void undo_Move(Player player, Move move){
        if(player == player1){
            this.getBoard()[player1.getXPosition()][player1.getYPosition()] = '-';
            player1.setXPosition(move.getX());
            player1.setYPosition(move.getY());
            this.getBoard()[player1.getXPosition()][player1.getYPosition()] = 'X';
        }
        else{
            this.getBoard()[player2.getXPosition()][player2.getYPosition()] = '-';
            player2.setXPosition(move.getX());
            player2.setYPosition(move.getY());
            this.getBoard()[player2.getXPosition()][player2.getYPosition()] = 'O';
        }
    }

    boolean sharedDiagonal(Player player, Move move) {
        return Math.abs(move.getX() - player.getXPosition()) == Math.abs(move.getY() - player.getYPosition());
    }

    boolean sharedRow(Player player, Move move) {
        return player.getXPosition() == move.getX();
    }

    boolean sharedCol(Player player, Move move){
        return  player.getYPosition() == move.getY();
    }

    boolean canMoveVertically(Player player, Move move) {

        if(player.getYPosition() == move.getY()){

            //up
            if(player.getXPosition() > move.getX()) {
                for (int i = move.getX(); i <= player.getXPosition(); i++) {
                    if(i == player.getYPosition())
                        break;
                    if (board[i][player.getYPosition()] != '-')
                        return false;
                }
            }

            //down
            if(player.getXPosition() < move.getX()){
                for(int i = move.getX(); player.getXPosition() <= i ; --i){
                    if(i == player.getXPosition())
                        break;
                    if(board[i][player.getYPosition()] != '-')
                        return false;
                }
            }
        }
        return true;
    }

    boolean canMoveHorizontally(Player player, Move move) {
        if(player.getXPosition() == move.getX()){
            //horizontal-left
            if(player.getYPosition() > move.getY()){
                for(int i = move.getY(); i <= player.getYPosition(); i++){
                    if(i == player.getYPosition())
                        break;
                    if(board[player.getXPosition()][i] != '-')
                        return false;
                }
            }

            //horizontal-right
            if(player.getYPosition() < move.getY()) {
                for(int i = move.getY(); i >= player.getYPosition(); i--){
                    if(i == player.getYPosition())
                        break;
                    if(board[player.getXPosition()][i] != '-')
                        return false;
                }
            }
        }
        return true;
    }

    boolean canMoveDiagonally(Player player, Move move) {
        //diagonal to right
        if(player.getYPosition() < move.getY()){
            //diagonal right-up
            if(player.getXPosition() > move.getX()){

                for (int i = 1; i <= move.getX() && i <= move.getY(); i++) {
                    if(!((player.getXPosition() - 1) > 0) && !((player.getXPosition() + 1) < 7) && !((player.getYPosition() + 1) < 7) && !((player.getYPosition() - 1) > 0)) {
                        if (board[player.getXPosition() - i][player.getYPosition() + i] != '-')
                            return false;
                    }
                }
            }
        }
        //diagonal right-down
        else {
            if ((player.getXPosition() - 1) > 0 && (player.getXPosition() + 1) < 7 && (player.getYPosition() + 1) < 7 && (player.getYPosition() - 1) > 0) {
                for (int i = 1; i <= move.getX() && i <= move.getY(); i++) {
                    if(!((player.getXPosition() - 1) > 0) && !((player.getXPosition() + 1) < 7) && !((player.getYPosition() + 1) < 7) && !((player.getYPosition() - 1) > 0)) {
                        if (board[player.getXPosition() + i][player.getYPosition() + i] != '-')
                            return false;
                    }
                }
            }
        }
        //diagonal to left
        if(player.getYPosition() > move.getY()){
            //diagonal left-up
                if (player.getXPosition() < move.getX()) {
                    for (int i = 1; i <= move.getX() && i <= move.getX(); i++) {
                        if(!((player.getXPosition() - 1) > 0) && !((player.getXPosition() + 1) < 7) && !((player.getYPosition() + 1) < 7) && !((player.getYPosition() - 1) > 0)) {

                            if (board[player.getXPosition() - i][player.getYPosition() - i] != '-')
                            return false;
                    }
                }
            }
            //diagonal left-down
            else{
                    for (int i = 1; i <= move.getX() && i <= move.getX(); i++) {
                        if(!((player.getXPosition() - 1) > 0) && !((player.getXPosition() + 1) < 7) && !((player.getYPosition() + 1) < 7) && !((player.getYPosition() - 1) > 0)) {
                            if (board[player.getXPosition() + i][player.getYPosition() - i] != '-') {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    //legal move check
    public boolean isNotValidMove(Player player, Move move){


        //check's user input
        if(move.getX() < 0 || move.getX() > 7 || move.getY() < 0 || move.getY() > 7){
            return false;
        }

        if (!sharedCol(player, move) && !sharedRow(player, move)) {
            if (!sharedDiagonal(player, move)){
                return false;
            }
        }


        //is the desired position open?
        if(board[move.getX()][move.getY()] != '-')
            return false;

        //if moving vertical
        if (sharedCol(player, move)){
            if(!canMoveVertically(player, move))
                return false;
        }

        //if moving horizontal
        if (sharedRow(player, move)) {
            if (!canMoveHorizontally(player, move))
                return false;
        }

        //if moving diagonal

        if (!canMoveDiagonally(player, move))
            return false;
        return true;
    }

    //prints board
    public String toString(){

        //TODO: format so that the log is printed
        String board = "  | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8   Computer vs. Opponent";
        for(int i = 0; i < 8; i++){
            board += ("\n" + (char)(65 + i)) + " ";
            for(int j = 0; j < 8; j++){
                board += ("| " + this.board[i][j] + " ");
            }
        }
        return board;
    }
}
