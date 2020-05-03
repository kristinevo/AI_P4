import java.util.*;

public class Isolation implements Runnable{

    private Board board;
    private Agent ai;
    private Move aiMove;
    private Player player;
    private Move player_move = new Move(0,0);
    private static double time;
    private static String play_again = "y";
    private static Scanner user_Input = new Scanner(System.in);
    private static boolean player_turn;
    private static volatile boolean dontStopMeNow = true;
    private static boolean finishedThread;
    Thread timer;


    public void run(){
        if (player_turn) {
            this.getPlayerMove();

        } else {
            this.getAiMove();
        }
    }

    public synchronized int getPlayerMove(){
        String move_coordinate;
        while (true) {
            if(!dontStopMeNow){
                return -1;
            }
            if (!board.terminalTest(player)) {
                System.out.println("Where would you like to move?\nPlease follow an alpha-numeric format.");
                move_coordinate = user_Input.next();
                //Uses ascii values to make smoother transition to arr indices
                player_move.setX((int) move_coordinate.charAt(0) - 65);
                player_move.setY((int) move_coordinate.charAt(1) - 49);

                if (move_coordinate.length() != 2 || !board.isValidMove(player, player_move)) {
                    System.out.println("That's an invalid input. [A-H][1-8]");
                } else {
                    if(!dontStopMeNow){
                        return -1;
                    }
                    board.addToLog(move_coordinate);
                    board.updateBoard(player, player_move);
                    System.out.println(board.toString());
                    player_turn = false;
                    finishedThread = true;
                    timer.interrupt();
                    break;
                }
            }
            else {
                while (true) {
                    System.out.println("YOU LOST. I'M SORRY. TRY AGAIN? (y/n)");
                    play_again = user_Input.next();
                    if(play_again.equalsIgnoreCase("y") || play_again.equalsIgnoreCase("n")){
                        break;
                    }
                }
                break;
            }
        }
        return 0;

    }

    public synchronized void getAiMove(){
        String move_coordinate = "";
        aiMove = ai.alphaBetaSearch(board, new Move(ai.getX(), ai.getY()));

        if(aiMove.getX() == -1 && aiMove.getY() == -1){
            while(true) {
                System.out.println("YOU'VE WON AGAINST OUR AI. CONGRATS! Would you like to play again? (y/n)");
                play_again = user_Input.next();
                if(play_again.equalsIgnoreCase("y") || play_again.equalsIgnoreCase("n")){
                    break;
                }
            }
        }

        else{
            board.updateBoard(ai, aiMove);
            System.out.println("The computer moved: " + (char)(aiMove.getX() + 65) + (char)(aiMove.getY() + 49));
            move_coordinate += ((char)(aiMove.getX() + 65)) + "" + ((char)(aiMove.getY() + 49));
            board.addToLog(move_coordinate);
            System.out.println(board.toString());
            player_turn = true;
            finishedThread = true;
            timer.interrupt();
        }
    }

    public static void main(String[] args) {
        Isolation iso = new Isolation();
        System.out.println("Welcome! You will be playing Isolation against an AI.");

        while(true){
            System.out.println("Would you like to go first? (y/n)");
            String user_player = user_Input.next();

            if(user_player.equalsIgnoreCase("y")){
                iso.player = new Player(0,0, false);
                iso.ai = new Agent(2, iso.player);
                iso.board = new Board(iso.player, iso.ai);
                player_turn = true;
                break;
            }

            else if(user_player.equalsIgnoreCase("n")){
                iso.player = new Player(7,7,false);
                iso.ai = new Agent(1, iso.player);
                iso.board = new Board(iso.ai, iso.player);
                player_turn = false;
                break;
            }

            else{
                System.out.println("That is an invalid choice.");
            }
        }

        while(true){
            try{
                System.out.println("How many seconds would you like per interval?");
                time = user_Input.nextDouble();
                if(time >= 0){
                    iso.ai.setTime(time);
                    break;
                }
            }catch(NumberFormatException e){
                System.out.print("Not a valid value.");
            }
        }

        System.out.println(iso.board.toString());

        while(play_again.equalsIgnoreCase("y")) {
            Thread t1 = new Thread(iso);
            Thread timer = new Thread();
            t1.start();
            /*try {
                t1.join();
            }catch(InterruptedException e){

            }*/

            try {
                timer.sleep((long) time * 1000);
                throw new InterruptedException();

            } catch (InterruptedException e) {
                if(finishedThread){
                    finishedThread = false;
                }
                else {
                    System.out.print("The turn took too long, ");
                    if (player_turn) {
                        System.out.println("you lose.");
                    } else {
                        System.out.println("you win by default.");
                    }
                    System.out.println("Thank you for playing!");
                    System.exit(0);
                }
            }
        }
    }
}

