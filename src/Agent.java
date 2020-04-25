import java.util.ArrayList;

public class Agent extends Player{

    Player opponent;
    private int MAX_DEPTH;
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
        double v = maxValue(state, Integer.MAX_VALUE, Integer.MAX_VALUE);
        return v;
    }

    double maxValue(Board state, double alpha, double beta) {
        if (terminalTest(state))
            return 0;

        double value = negativeInfinity;
        ArrayList<Move> successors = generateSuccessors(state);

        for (int s = 0; s < successors.size(); s++) {
            value = Math.max(value, minValue(state, alpha, beta));
            if (value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }

        return value;
    }

    double minValue(Board state, double alpha, double beta) {
        if (terminalTest(state))
            return 0;

        double value = infinity;
        ArrayList<Move> successors = generateSuccessors(state);

        for (int s = 0; s < successors.size(); s++) {
            value = Math.min(value, maxValue(state, alpha, beta));
            if (value <= beta)
                return value;
            alpha = Math.min(alpha, value);
        }

        return value;
    }

    boolean terminalTest(Board state) {

        //this section checks given that the opponent isn't against a vertical wall
        if (opponent.getXPosition() > 0 && opponent.getXPosition() < 8) {

            //no horizontal wall
            if (opponent.getYPosition() > 0 && opponent.getYPosition() < 7) {
                if (state.isValidMove(opponent, opponent.move_diagonal_down_left()) ||
                        state.isValidMove(opponent, opponent.move_diagonal_down_right()) ||
                        state.isValidMove(opponent, opponent.move_diagonal_up_left()) ||
                        state.isValidMove(opponent, opponent.move_diagonal_up_right()) ||
                        state.isValidMove(opponent, opponent.move_down()) ||
                        state.isValidMove(opponent, opponent.move_up()) ||
                        state.isValidMove(opponent, opponent.move_left()) ||
                        state.isValidMove(opponent, opponent.move_right())) {
                    return false;
                }
            }

            //wall beneath
            else if (opponent.getYPosition() == 7) {
                if (state.isValidMove(opponent, opponent.move_diagonal_up_left()) ||
                        state.isValidMove(opponent, opponent.move_diagonal_up_right()) ||
                        state.isValidMove(opponent, opponent.move_up()) ||
                        state.isValidMove(opponent, opponent.move_left()) ||
                        state.isValidMove(opponent, opponent.move_right())) {
                    return false;
                }
            }

            //wall above
            else if (opponent.getYPosition() == 0) {
                if (state.isValidMove(opponent, opponent.move_diagonal_down_left()) ||
                        state.isValidMove(opponent, opponent.move_diagonal_down_right()) ||
                        state.isValidMove(opponent, opponent.move_down()) ||
                        state.isValidMove(opponent, opponent.move_left()) ||
                        state.isValidMove(opponent, opponent.move_right())) {
                    return false;
                }
            }
        }

        // up against a vertical wall to left
        else if (opponent.getXPosition() == 0) {

            //no horizontal wall
            if (opponent.getYPosition() > 0 && opponent.getYPosition() < 8) {
                if (state.isValidMove(opponent, opponent.move_diagonal_down_right()) ||
                        state.isValidMove(opponent, opponent.move_diagonal_up_right()) ||
                        state.isValidMove(opponent, opponent.move_down()) ||
                        state.isValidMove(opponent, opponent.move_up()) ||
                        state.isValidMove(opponent, opponent.move_right())) {
                    return false;
                }
            }

            //wall beneath
            else if (opponent.getYPosition() == 7) {
                if (state.isValidMove(opponent, opponent.move_diagonal_up_right()) ||
                        state.isValidMove(opponent, opponent.move_up()) ||
                        state.isValidMove(opponent, opponent.move_right())) {
                    return false;
                }
            }

            //wall above
            else if (opponent.getYPosition() == 0) {
                if (state.isValidMove(opponent, opponent.move_diagonal_down_right()) ||
                        state.isValidMove(opponent, opponent.move_down()) ||
                        state.isValidMove(opponent, opponent.move_right())) {
                    return false;
                }
            }
        }

        //wall to the right
        if (opponent.getXPosition() == 7) {

            //no horizontal wall
            if (opponent.getYPosition() > 0 && opponent.getYPosition() < 8) {
                if (
                        state.isValidMove(opponent, opponent.move_diagonal_down_left()) ||
                                state.isValidMove(opponent, opponent.move_diagonal_up_left()) ||
                                state.isValidMove(opponent, opponent.move_down()) ||
                                state.isValidMove(opponent, opponent.move_up()) ||
                                state.isValidMove(opponent, opponent.move_left())) {
                    return false;
                }
            }

            //wall above
            else if (opponent.getYPosition() == 7) {
                if (state.isValidMove(opponent, opponent.move_diagonal_down_left()) ||
                        state.isValidMove(opponent, opponent.move_down()) ||
                        state.isValidMove(opponent, opponent.move_left())) {
                    return false;
                }
            }

            //wall beneath
            else if (opponent.getYPosition() == 0) {
                if (state.isValidMove(opponent, opponent.move_diagonal_up_left()) ||
                        state.isValidMove(opponent, opponent.move_up()) ||
                        state.isValidMove(opponent, opponent.move_left())) {
                    return false;
                }
            }
        }
        return true;
    }

    ArrayList<Move> generateSuccessors(Board board){
        char position_status = ' ';
        int i = 1;
        Move m = new Move(this.getXPosition(), this.getYPosition());
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
