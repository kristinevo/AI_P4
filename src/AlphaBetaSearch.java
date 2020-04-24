import java.util.*;


public class AlphaBetaSearch {

    Player player;
    private int MAX_DEPTH;
    double infinity = Double.MAX_VALUE;
    double negativeInfinity = Double.MIN_VALUE;

    AlphaBetaSearch(Board state) {
        MAX_DEPTH = 3;
    }

    //Where Min-Max w/ alpha-beta pruning will be implemented
    Move search(Board curState) {
        Move move = new Move(5,5);
        int xPos, yPos;
        xPos = player.getXPosition();
        yPos = player.getYPosition();

        //Iterative Deepening
        for (int depth = 1; depth < MAX_DEPTH; depth++) {



        }
        return move;
    }

    private void aplhaBetaSearch(Board curState) {
        int maxValue = maxValue(curState, Integer.MAX_VALUE, Integer.MAX_VALUE);
        return
    }







    Move alphaBetaSearch(Board state) {

        double value = maxValue(state, negativeInfinity, infinity);
        return move in scccessors(state) with value;
    }

    double maxValue(Board state, double alpha, double beta) {
        double value;
        if (terminalTest(state))
            return utility(state);

        value = negativeInfinity;

        for (int s = 0; s < successors; s++) {
            value = max(v, minValue(state, alpha, beta));
            if (value >= beta)
                return value;
            alpha = max(alpha, value);
        }

        return value;
    }

    double minValue(Board state, double alpha, double beta) {
        double value;

        if (terminalTest(state))
            return utility(state);

        value = infinity;

        for (int s = 0; s < successors.size(); s++) {
            value = min(value, maxValue(s, alpha, beta));
            if (value <= alpha)
                return value;
            beta = Math.min(beta, value);
        }

        return value;
    }



}
