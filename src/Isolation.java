import java.util.*;

public class Isolation{

    /*
    Playing around with the TimerTask to try and figure out
    how to limit the time it takes for the agent to make a move.
     */

    int secondsPassed = 0;
    Move aiMove;
    Board board;
    AlphaBetaSearch search;


    Isolation() {
        board = new Board();
    }

    /*
    The TimerTask is just counting seconds, right now it just prints the board with
    a new move after 5 seconds but when implemented this is where a new move will come from.
    If there is no new move after x seconds, throw a Timeout exception.
     */
    TimerTask task = new TimerTask() {
        public void run() {
            secondsPassed++;
            if(secondsPassed > 5) {
                System.out.println("Took too long");
                System.exit(-1);
            } else {
                search = new AlphaBetaSearch(board);
                aiMove = search.search();
                board.updateBoard(board.player1, aiMove);
                System.out.println(board);
                System.exit(0);
            }
        }
    };


    //Called in main to use the TimerTask
    public void getAiMove() {
        System.out.println(board);
        Timer timer = new Timer();
        timer.schedule(task, 5*1000);  //This is where the 5 second timer is set

    }


    public static void main(String args[]) {
        Isolation isolation = new Isolation();

        try {
            isolation.getAiMove();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
