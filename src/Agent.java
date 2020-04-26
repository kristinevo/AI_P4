import java.util.ArrayList;

public class Agent extends Player{

    Player opponent;
    private int MAX_DEPTH = 3;
    private int current_depth = 0;
    double infinity = Double.MAX_VALUE;
    double negativeInfinity = Double.MIN_VALUE;
    Move ai_next_move = new Move(0,0);

    Agent(int turn, Player opponent) {
        super();

        if(turn == 1) {
            this.setXPosition(0);
            this.setYPosition(0);
        }

        if(turn == 2) {
            this.setXPosition(7);
            this.setYPosition(7);
        }

        this.opponent = opponent;
        MAX_DEPTH = 3;
    }

    //Where Min-Max w/ alpha-beta pruning will be implemented
   /* Move search(Board curState) {
        Move move = new Move(5,5);
        int xPos, yPos;
        xPos = player.getXPosition();
        yPos = player.getYPosition();

        //Iterative Deepening
        for (int depth = 1; depth < MAX_DEPTH; depth++) {



        }
        return move;
    }*/

    private double aplhaBetaSearch(Board state) {
        //TODO: planning on having the ai store it's next move here, it's a WIP
        double v;
        current_depth = 0;
        v = maxValue(state, Integer.MAX_VALUE, Integer.MAX_VALUE);
        return v;
    }

    double maxValue(Board state, double alpha, double beta) {
        //TODO: have a function which calculates the score of the state
        if(current_depth == MAX_DEPTH)
            return 0;

        //TODO: probably smart to make it return as the MAX_SCORE
        if (terminalTest(state))
            return 0;

        double value = negativeInfinity;
        ArrayList<Move> successors = generateSuccessors(state);

        for (int s = 0; s < successors.size(); s++) {
            value = Math.max(value, minValue(state, alpha, beta));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }

        return value;
    }

    double minValue(Board state, double alpha, double beta) {
        //TODO: have a function which calculates the score of the state
        if(current_depth == MAX_DEPTH)
            return 0;

        //TODO: probably smart to make it return as the MAX_SCORE
        if (terminalTest(state))
            return 0;

        double value = infinity;
        ArrayList<Move> successors = generateSuccessors(state);

        for (int s = 0; s < successors.size(); s++) {
            value = Math.min(value, maxValue(state, alpha, beta));
            if (value <= beta) {
                return value;
            }
            alpha = Math.min(alpha, value);
        }

        return value;
    }

    boolean terminalTest(Board state) {

        //this section checks given that the opponent isn't against a vertical wall
        if (    state.isValidMove(opponent, opponent.move_diagonal_down_left())  ||
                state.isValidMove(opponent, opponent.move_diagonal_down_right()) ||
                state.isValidMove(opponent, opponent.move_diagonal_up_left())    ||
                state.isValidMove(opponent, opponent.move_diagonal_up_right())   ||
                state.isValidMove(opponent, opponent.move_down())                ||
                state.isValidMove(opponent, opponent.move_up())                  ||
                state.isValidMove(opponent, opponent.move_left())                ||
                state.isValidMove(opponent, opponent.move_right())               )
            return false;
        return true;
    }

    ArrayList<Move> generateSuccessors(Board board){
        int i = 1;
        ArrayList<Move> successors = new ArrayList<Move>();

        while(this.getXPosition() + i < 7 || this.getXPosition() - i > 0 || this.getYPosition() + i < 7 || this.getXPosition() - i > 0){
            if(board.isValidMove(this,  this.move_up()))
                successors.add(this.move_up());
            if(board.isValidMove(this,  this.move_down()))
                successors.add(this.move_down());
            if(board.isValidMove(this,  this.move_left()))
                successors.add(this.move_left());
            if(board.isValidMove(this,  this.move_right()))
                successors.add(this.move_right());
            if(board.isValidMove(this,  this.move_diagonal_up_left()))
                successors.add(this.move_diagonal_up_left());
            if(board.isValidMove(this,  this.move_diagonal_up_right()))
                successors.add(this.move_diagonal_up_right());
            if(board.isValidMove(this,  this.move_diagonal_down_right()))
                successors.add(this.move_diagonal_down_right());
            if(board.isValidMove(this,  this.move_diagonal_down_left()))
                successors.add(this.move_diagonal_down_left());
            i++;
        }

        return successors;
    }

}
