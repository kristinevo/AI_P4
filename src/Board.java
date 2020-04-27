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

    public char[][] getBoard(){ return board; }

    public void addToLog(String move){
        log.add(move);
    }

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

    //update board based on player's new position
    public void updateBoard(Player player, Move move){
        if(player == player1){
            board[player1.getXPosition()][player1.getYPosition()] = '#';
            player1.setXPosition(move.getX());
            player1.setYPosition(move.getY());
            board[player1.getXPosition()][player1.getYPosition()] = 'X';
        }
        else{
            board[player2.getXPosition()][player2.getYPosition()] = '#';
            player2.setXPosition(move.getX());
            player2.setYPosition(move.getY());
            board[player2.getXPosition()][player2.getYPosition()] = 'O';
        }
    }

    //legal move check
    public boolean isValidMove(Player player, Move move){

        int p_x, p_y;

        int m_x = move.getX();
        int m_y = move.getY();

        m_x--;
        m_y--;

        if(player == player1){
            p_x = player1.getXPosition();
            p_y = player1.getYPosition();
        }

        else{
            p_x = player2.getXPosition();
            p_y = player2.getYPosition();
        }

        //check's user input
        if(Math.abs(m_x - p_y) != Math.abs((m_y - p_y)) ||
           move.getX() < 0     || move.getX() > 7       ||
           move.getY() < 0     || move.getY() > 7       ){
            return false;
        }

        //is the desired position open?
        else if(board[m_x][m_y] != '-')
            return false;

        //if moving vertical
        else if(p_y == m_y){

            //up
            if(p_x < m_x) {
                for (int i = m_x; i > p_x; i--) {
                    if (board[i][p_y] != '-')
                        return false;
                }
            }

            //down
            if(p_x > m_x){
                for(int i = p_x; i < m_x; i++){
                    if(board[i][p_y] != '-')
                        return false;
                }
            }
        }

        //if moving horizontal
        else if(p_x == m_x){
            //horizontal-left
            if(p_y > m_y){
                for(int i = p_y; i > m_y; i--){
                    if(board[p_x][i] != '-')
                        return false;
                }
            }

            //horizontal-right
            if(p_y < m_y) {
                for(int i = p_y; i < m_y; i++){
                    if(board[p_x][i] != '-')
                        return false;
                }
            }
        }

        //if moving diagonal
        else{
            //diagonal to right
            if(p_y < m_y){
                //diagonal right-up
                if(p_x > m_x){
                    for(int i = 1; i < m_x ; i++){
                        if(board[p_x - i][p_y + i] != '-')
                            return false;
                    }
                }
                //diagonal right-down
                else{
                    for(int i = 1; i < m_x; i++){
                        if(board[p_x + i][p_y + i] != '-')
                            return false;
                    }
                }

            }

            //diagonal to left
            if(p_y > m_y){
                //diagonal left-up
                if(p_x < m_x){
                    for(int i = 1; i < m_x; i++){
                        if(board[p_x - i][p_y - i] != '-')
                            return false;
                    }
                }
                //diagonal left-down
                else{
                    for(int i = 1; i < m_x; i++){
                        if(board[p_x + i][p_y - i] != '-')
                            return false;
                    }

                }
            }

        }
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
