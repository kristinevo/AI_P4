import java.util.*;


public class AlphaBetaSearch {

    Board curState;
    int MAX_DEPTH = 3;

    AlphaBetaSearch(Board state) {
        this.curState = state;
    }

    Move search() {
        Move move = new Move(5,5);

        //Iterative Deepening
        for (int depth = 1; depth < MAX_DEPTH; depth++) {

        }
        return move;
    }

}
