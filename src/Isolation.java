import java.text.DecimalFormat;
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

            //aiMove = ai.search(board);
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

        Move player_move = new Move(0,0);
        String play_again = "y";

        while(play_again.equalsIgnoreCase("y")){

            //TODO: add moves to log
            //PLAYER'S TURN
            if(player_turn) {
                String move_coordinate = "";
                while (true) {

                    if (!isolation.board.terminalTest(isolation.player)) {
                        System.out.println("Where would you like to move?\nPlease follow an alpha-numeric format.");
                        move_coordinate = user_Input.next();

                        //Uses ascii values to make smoother transition to arr indices
                        player_move.setX((int) move_coordinate.charAt(0) - 65);
                        player_move.setY((int) move_coordinate.charAt(1) - 49);

                        if (!isolation.board.isValidMove(isolation.player, player_move)) {
                            System.out.println("That's an invalid input. [A-H][1-8]");
                        } else {
                            isolation.board.updateBoard(isolation.player, player_move);
                            player_turn = false;
                            break;
                        }
                    }

                    else {
                        while (!play_again.equalsIgnoreCase("y") || play_again.equalsIgnoreCase("n")) {
                            System.out.println("YOU LOST. I'M SORRY. TRY AGAIN? (y/n)");
                            play_again = user_Input.next();
                            break;
                        }
                    }

                }
            }


            else{
                isolation.aiMove = isolation.ai.aplhaBetaSearch(isolation.board);

                if(isolation.aiMove.getX() == -1 && isolation.aiMove.getY() == -1){
                    while(!play_again.equalsIgnoreCase("y") || play_again.equalsIgnoreCase("n")) {
                        System.out.println("YOU'VE WON AGAINST OUR AI. CONGRATS! Would you like to play again? (y/n)");
                        play_again = user_Input.next();
                    }
                }

                else{
                    isolation.board.updateBoard(isolation.ai, isolation.aiMove);
                    System.out.println("The computer moved: " + (char)(isolation.aiMove.getX() + 65) + ((char)isolation.aiMove.getY() + 49));
                    player_turn = true;
                }
            }
        }
    }
}

