import java.util.*;

public class Isolation{

    /*
    Playing around with the TimerTask to try and figure out
    how to limit the time it takes for the agent to make a move.
     */

    Move aiMove;
    Board board;
    AlphaBetaSearch search;
    Timer timer;


    Isolation() {
        board = new Board();
        timer = new Timer();
    }

    /*
    The TimerTask is just counting seconds, right now it just prints the board with
    a new move after 5 seconds but when implemented this is where a new move will come from.
    If there is no new move after x seconds, throw a Timeout exception.
     */
    TimerTask task = new TimerTask() {
        public void run() {

            int test = 0;
            if (test == 2) {
                System.out.println("Found a solution!");
                timer.cancel();
                System.exit(0);
            }

            search = new AlphaBetaSearch(board);
            aiMove = search.search();
            board.updateBoard(board.player1, aiMove);
            System.out.println(board);
            System.exit(0);
        }
    };

    public void cancelTimer() {
        this.timer.cancel();
        System.out.println("Canceled Timer!");
    }


    //Called in main to use the TimerTask
    public void getAiMove() {
        System.out.println(board);
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
