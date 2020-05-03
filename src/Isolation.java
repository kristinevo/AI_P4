import java.util.*;

public class Isolation implements Runnable {

    private Board board;
    private Agent ai;
    private Move aiMove;
    private Player player;
    private Move player_move = new Move(0, 0);
    private static double time;
    private static String play_again = "y";
    private static Scanner user_Input = new Scanner(System.in);
    private static boolean player_turn;
    private static boolean finishedThread = false;

    /**
     * Sets time for player turns
     * @param sleepInterval
     */
    public void setTime(double sleepInterval) {
        time = sleepInterval;
    }

    /**
     * Runs a thread for timing player turns
     */
    public void run() {
        try {
            Thread.sleep((long)time * 1000);
            throw new InterruptedException();

        } catch (InterruptedException e) {
            if(!finishedThread) {
                System.out.print("This turn took long, ");
                if (player_turn)
                    System.out.println("you lose.");
                else {
                    System.out.println("you win by default.");
                }
                System.exit(0);
            }
        }
    }

    /**
     * Prompts the user for their next move
     * @param timerThread
     */
    public void getPlayerMove(Thread timerThread) {
        String move_coordinate;
        while (true) {
            if (!board.terminalTest(player)) {
                System.out.println("Where would you like to move?\nPlease follow an alpha-numeric format.");
                move_coordinate = user_Input.next();
                //Uses ascii values to make smoother transition to arr indices
                player_move.setX((int) move_coordinate.charAt(0) - 65);
                player_move.setY((int) move_coordinate.charAt(1) - 49);

                if (move_coordinate.length() != 2 || !board.isValidMove(player, player_move)) {
                    System.out.println("That's an invalid input. [A-H][1-8]");
                } else {
                    player_turn = false;
                    finishedThread = true;
                    timerThread.interrupt();
                    board.addToLog(move_coordinate);
                    board.updateBoard(player, player_move);
                    System.out.println(board.toString());
                    break;
                }
            } else {
                while (true) {
                    System.out.println("YOU LOST. I'M SORRY. TRY AGAIN? (y/n)");
                    play_again = user_Input.next();
                    if (play_again.equalsIgnoreCase("y") || play_again.equalsIgnoreCase("n")) {
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * Calls the alpha-beta search for the agent's next move
     * @param timerThread
     */
    public void getAiMove(Thread timerThread) {
        String move_coordinate = "";
        aiMove = ai.alphaBetaSearch(board, new Move(ai.getX(), ai.getY()));


        if (aiMove.getX() == -1 && aiMove.getY() == -1) {
            while (true) {
                System.out.println("YOU'VE WON AGAINST OUR AI. CONGRATS! Would you like to play again? (y/n)");
                play_again = user_Input.next();
                if (play_again.equalsIgnoreCase("y") || play_again.equalsIgnoreCase("n")) {
                    break;
                }
            }
        } else {
            player_turn = true;
            finishedThread = true;
            timerThread.interrupt();
            board.updateBoard(ai, aiMove);
            System.out.println("The computer moved: " + (char) (aiMove.getX() + 65) + (char) (aiMove.getY() + 49));
            move_coordinate += ((char) (aiMove.getX() + 65)) + "" + ((char) (aiMove.getY() + 49));
            board.addToLog(move_coordinate);
            System.out.println(board.toString());
        }
    }

    /**
     * Handles player turns
     */
    public void getUserTurn(){
        while (true) {
            System.out.println("Would you like to go first? (y/n)");
            String user_player = user_Input.next();

            if (user_player.equalsIgnoreCase("y")) {
                player = new Player(0, 0, false);
                ai = new Agent(2, player);
                board = new Board(player, ai);
                player_turn = true;
                break;
            } else if (user_player.equalsIgnoreCase("n")) {
                player = new Player(7, 7, false);
                ai = new Agent(1, player);
                board = new Board(ai, player);
                player_turn = false;
                break;
            } else {
                System.out.println("That is an invalid choice.");
            }
        }
    }

    /**
     * Sets the turn time for the player and Agent alpha-beta search
     */
    public void setTimeInterval(){
        while (true) {
            try {
                System.out.println("How many seconds would you like per interval?");
                time = user_Input.nextDouble();
                if (time >= 0) {
                    setTime(time);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Not a valid value.");
            }
        }
    }

    /**
     * Starts the game and alternates player turns
     */
    public void startGame(Isolation iso){
        while (play_again.equalsIgnoreCase("y")) {
            Thread timerThread = new Thread(iso);
            finishedThread = false;

            if (player_turn) {
                timerThread.start();
                getPlayerMove(timerThread);
            } else {
                timerThread.start();
                getAiMove(timerThread);
            }
        }
    }

    public static void main(String[] args){
        Isolation iso = new Isolation();
        System.out.println("Welcome! You will be playing Isolation against an AI.");
        iso.getUserTurn();
        iso.setTimeInterval();
        System.out.println(iso.board.toString());
        iso.startGame(iso);
    }
}

