public class Board {
    /*
    Board initialization key:
    X/O: these are the players
    -  : is an empty space on the board
    #  : will be a taken space
    */

    private char[][] board;
    private int p1_x_pos = 0;
    private int p1_y_pos = 0;
    private int p2_x_pos = 7;
    private int p2_y_pos = 7;

    public Board(){
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

    //update board based on player's new position
    public void updateBoard(int new_x, int new_y, int player){
        if(player == 1){
            board[p1_x_pos][p1_y_pos] = '#';
            p1_x_pos = new_x - 1;
            p1_y_pos = new_y - 1;
            board[p1_x_pos][p1_y_pos] = 'X';
        }
        else{
            board[p2_x_pos][p2_y_pos] = '#';
            p2_x_pos = new_x - 1;
            p2_y_pos = new_y - 1;
            board[p1_x_pos][p1_y_pos] = 'O';
        }
    }

    //legal move check
    public boolean check(int player, int m_x, int m_y){

        int p_x = 0, p_y = 0;
        m_x--;
        m_y--;

        if(player == 1){
            p_x = p1_x_pos;
            p_y = p1_y_pos;
        }

        else{
            p_x = p2_x_pos;
            p_y = p2_y_pos;
        }

        //user input verification should be done in the main class
        if(Math.abs(m_x - p_y) != Math.abs((m_y - p_y))){
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
        String board = "  | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8";
        for(int i = 0; i < 8; i++){
            board += ("\n" + Character.toString((char) (65 + i)) + " ");
            for(int j = 0; j < 8; j++){
                board += ("| " + this.board[i][j] + " ");
            }
        }
        return board;
    }

    public static void main(String args[]){
        Board a = new Board();
        System.out.println(a);

        if(a.check(1, 5,5))
            a.updateBoard(5, 5,1);

        System.out.println(a);

    }
}
