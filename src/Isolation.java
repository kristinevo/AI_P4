import java.util.*;

public class Isolation{

    int secondsPassed = 0;
    Move aiMove;
    Board board;

    Isolation() {
        board = new Board();
    }


    TimerTask task = new TimerTask() {
        public void run() {
            secondsPassed++;
            if(secondsPassed > 5) {
                System.out.println("Took too long");
                System.exit(-1);
            } else {
                board.updateBoard(board.player1, aiMove);
                System.out.println(board);
                System.exit(0);
            }
        }
    };


    public void getAiMove() {
        System.out.println(board);
        Timer timer = new Timer();
        timer.schedule(task, 5*1000);
        aiMove = new Move(5, 5);

    }


    public static void main(String args[]) {
        Isolation isolation = new Isolation();

        isolation.getAiMove();
    }


}
