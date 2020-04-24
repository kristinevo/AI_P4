import java.util.*;

public class AlphaBetaSearch extends Player{

    Player opponent;
    private int MAX_DEPTH;
    double infinity = Double.MAX_VALUE;
    double negativeInfinity = Double.MIN_VALUE;

    AlphaBetaSearch(int turn, Player opponent) {
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

    private Move aplhaBetaSearch(Board state) {
        double value = maxValue(state, Integer.MAX_VALUE, Integer.MAX_VALUE);
        //place holder
        //return move in scccessors(state) with value;
        return new Move(0,0);
    }

    double maxValue(Board state, double alpha, double beta) {
        //we have to have the heuristic function
        if (terminalTest(state))
            return 0;

        double value = negativeInfinity;
        ArrayList<Board> successors = generateSuccessors(state);

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
        ArrayList<Board> successors = generateSuccessors(state);

        for (int s = 0; s < successors.size(); s++) {
            value = Math.min(value, maxValue(state, alpha, beta));
            if (value <= beta)
                return value;
            alpha = Math.min(alpha, value);
        }

        return value;
    }

    boolean terminalTest(Board state){
        //get the position of the other player
        //check if they can move when you go to your new spot
        return true;
    }

    ArrayList<Board> generateSuccessors(Board b){


        return new ArrayList<Board>();
    }



}
