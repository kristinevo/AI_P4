import java.util.*;

public class Isolation{

    /*
    Playing around with the TimerTask to try and figure out
    how to limit the time it takes for the agent to make a move.
     */

    Board board;
    Timer timer;
    Agent ai;
    Move aiMove;
    Player player;


    Isolation() {
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

            //the turn is a place holder for now
            ai = new Agent(1, player);

            aiMove = ai.search(board);
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

        /*try {
            isolation.getAiMove();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }*/

        Scanner user_Input = new Scanner(System.in);
        boolean player_turn;

        System.out.println("Welcome! You will be playing Isolation against an AI.");

        //INIT THE GAME
        while(true){
            System.out.println("Would you like to go first? (y/n)");
            String user_player = user_Input.next();

            if(user_player.equalsIgnoreCase("y")){
                isolation.player = new Player(0,0);
                isolation.ai = new Agent(2, isolation.player);
                isolation.board = new Board(isolation.player, isolation.ai);
                player_turn = true;
                break;
            }

            else if(user_player.equalsIgnoreCase("n")){
                isolation.player = new Player(7,7);
                isolation.ai = new Agent(1, isolation.player);
                isolation.board = new Board(isolation.ai, isolation.player);
                player_turn = false;
                break;
            }

            else{
                System.out.println("That is an invalid choice.");
            }
        }

        while(true){
            if(player_turn){


            }
            else{
                isolation.aiMove = isolation.ai.aplhaBetaSearch(isolation.board);

            }
        }


    }
}

}
