import java.util.ArrayList;

public class Agent extends Player{

    final double INFINITY = Double.MAX_VALUE;
    final double NEGATIVE_INFINITY = Double.MIN_VALUE;
    final private int MAX_DEPTH = 3;
    private int current_depth = 0;

    private Player opponent;
    private Move ai_next_move = new Move(0,0);

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
    }

    private Move aplhaBetaSearch(Board state) {
        ArrayList<Move> successors = generateSuccessors(state);
        double v, best_v = INFINITY;
        double alpha = NEGATIVE_INFINITY;
        current_depth++;

        if(successors.isEmpty()){
            return new Move(-1,-1);
        }

        for(Move i: successors) {

            v = minValue(state, Integer.MAX_VALUE, Integer.MAX_VALUE);
            if(v >= best_v){
                ai_next_move = i;
            }

            alpha = Math.max(alpha, v);
        }

        return ai_next_move;
    }

    double maxValue(Board state, double alpha, double beta) {
        //TODO: have a function which calculates the score of the state
        if(current_depth == MAX_DEPTH || terminalTest(state))
            return 0;

        current_depth++;
        double value = NEGATIVE_INFINITY;
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
        if(current_depth == MAX_DEPTH || terminalTest(state))
            return 0;

        current_depth++;
        double value = INFINITY;
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

    double heuristic_function(Board board, Move move){
        //TODO: pythagorean distance from center + (moves ai has - moves opponent has)
        double agent_distance = 0;
        Move agent_temp_pos = new Move(this.getXPosition(), this.getYPosition());
        Move opponent_temp_pos = new Move(opponent.getXPosition(), opponent.getYPosition());
        int agent_moves = 0;
        int opponent_moves = 0;

        //distance: lower number, the better
        agent_distance += Math.abs(move.getY() - 0) / Math.abs(move.getX() - 0);
        agent_distance += Math.abs(move.getY() - 0) / Math.abs(move.getX() - 7);
        agent_distance += Math.abs(move.getY() - 7) / Math.abs(move.getX() - 0);
        agent_distance += Math.abs(move.getY() - 7) / Math.abs(move.getX() - 7);
        agent_distance /= 4;

        //moves
        /*if(board.isValidMove(this,  this.move_up()))
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
        */

        return 0;
    }

    boolean terminalTest(Board state) {

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
        Move up = new Move(this.getXPosition(), this.getYPosition());
        Move down = new Move(this.getXPosition(), this.getYPosition());
        Move left = new Move(this.getXPosition(), this.getYPosition());
        Move right = new Move(this.getXPosition(), this.getYPosition());
        Move up_right = new Move(this.getXPosition(), this.getYPosition());
        Move up_left = new Move(this.getXPosition(), this.getYPosition());
        Move down_right = new Move(this.getXPosition(), this.getYPosition());
        Move down_left = new Move(this.getXPosition(), this.getYPosition());

        while(true){
            up.setX(up.getX() - i);
            down.setX(up.getX() + i);
            right.setY(right.getY() + i);
            left.setY(left.getY() - i);
            up_left.setX(up_left.getX() - i);
            up_left.setY(up_left.getY() - i);
            up_right.setX(up_right.getX() - i);
            up_right.setY(up_right.getY() + i);
            down_left.setX(down_left.getX() + i);
            down_left.setY(down_left.getY() - i);
            down_right.setX(down_right.getX() + i);
            down_right.setY(down_left.getY() + i);

            if(board.isValidMove(this, up))
                successors.add(up);

            else if(board.isValidMove(this, down))
                successors.add(down);

            else if(board.isValidMove(this, left))
                successors.add(left);

            else if(board.isValidMove(this,  right))
                successors.add(right);

            else if(board.isValidMove(this, up_left))
                successors.add(up_left);

            else if(board.isValidMove(this,  up_right))
                successors.add(up_right);

            else if(board.isValidMove(this, down_right))
                successors.add(down_right);

            else if(board.isValidMove(this, down_left))
                successors.add(down_left);

            else
                break;

            i++;
        }

        return successors;
    }

}
