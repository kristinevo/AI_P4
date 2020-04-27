import java.util.ArrayList;

public class Agent extends Player{

    final double INFINITY = Double.MAX_VALUE;
    final double NEGATIVE_INFINITY = Double.MIN_VALUE;
    final private int MAX_DEPTH = 3;
    private int current_depth = 0;

    private Player opponent;
    private Move ai_next_move;

    Agent(int turn, Player opponent) {
        super();

        ai_next_move = new Move(0,0);

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

    public Move aplhaBetaSearch(Board state, Move player_original_position) {
        ArrayList<Move> successors = generateSuccessors(state);
        double v, best_v = NEGATIVE_INFINITY;
        double alpha = NEGATIVE_INFINITY;
        current_depth = 1;

        if(successors.isEmpty()){
            return new Move(-1,-1);
        }


        for (int s = 0; s < successors.size(); s++) {
            //TODO: the player's are dependent on player choice
            state.updateBoard(this, successors.get(s));
            v = minValue(state, Integer.MAX_VALUE, Integer.MAX_VALUE, successors.get(s));
            if(v == -1){
                current_depth = 1;
                v = heuristic_function(state, successors.get(s));
            }

            if(v >= best_v){
                ai_next_move = successors.get(s);
            }

            alpha = Math.max(alpha, v);
            state.undo_Move(this, player_original_position);
        }

        return ai_next_move;
    }

    double maxValue(Board state, double alpha, double beta, Move player_original_position) {
        //TODO: have a function which calculates the score of the state
        if(current_depth > MAX_DEPTH || state.terminalTest(opponent)) {
            return -1;
        }

        current_depth++;
        double value = NEGATIVE_INFINITY, minVal = 0;
        ArrayList<Move> successors = generateSuccessors(state);
        Board temp;

        for (int s = 0; s < successors.size(); s++) {
            //TODO: the player's are dependent on player choice
            state.updateBoard(this, successors.get(s));
            minVal = minValue(state, alpha, beta, successors.get(s));
            if(minVal == -1){
                current_depth--;
                state.undo_Move(this, player_original_position);
                return heuristic_function(state, successors.get(s));
            }

            value = Math.max(value, minVal);

            if (value >= beta) {
                return value;
            }

            alpha = Math.max(alpha, value);
            state.undo_Move(this, player_original_position);
        }

        return value;
    }

    double minValue(Board state, double alpha, double beta, Move player_original_position) {
        //TODO: have a function which calculates the score of the state
        if(current_depth == MAX_DEPTH || state.terminalTest(opponent)) {
            return -1;
        }


        current_depth++;
        double value = INFINITY, maxVal = 0;
        ArrayList<Move> successors = generateSuccessors(state);

        for (int s = 0; s < successors.size(); s++) {
            //TODO: the player's are dependent on player choice
            state.updateBoard(this, successors.get(s));
            maxVal = maxValue(state, alpha, beta, player_original_position);
            if(maxVal == -1){
                current_depth--;
                state.undo_Move(this, player_original_position);
                return heuristic_function(state, successors.get(s));
            }

            value = Math.min(value, maxVal);

            if (value <= beta) {
                return value;
            }
            alpha = Math.min(alpha, value);
            state.undo_Move(this, player_original_position);
        }

        return value;
    }

    double heuristic_function(Board board, Move move){
        //pythagorean distance from center + (moves ai has - moves opponent has)
        double agent_distance = 0;

        //TODO: this is ugly. can do better.
        ArrayList<Move> agent_moves = generateSuccessors(board);
        ArrayList<Move> opponent_moves = generateSuccessors(board);

        /*distance: lower number, the better
        Scores based on location:
        23.90 |23.29 |22.88 |22.68 |22.68 |22.88 |23.29 |23.90 |
        23.29 |22.07 |21.47 |21.20 |21.20 |21.47 |22.07 |23.29 |
        22.88 |21.47 |20.67 |20.31 |20.31 |20.67 |21.47 |22.88 |
        22.68 |21.20 |20.31 |19.90 |19.90 |20.31 |21.20 |22.68 |
        22.68 |21.20 |20.31 |19.90 |19.90 |20.31 |21.20 |22.68 |
        22.88 |21.47 |20.67 |20.31 |20.31 |20.67 |21.47 |22.88 |
        23.29 |22.07 |21.47 |21.20 |21.20 |21.47 |22.07 |23.29 |
        23.90 |23.29 |22.88 |22.68 |22.68 |22.88 |23.29 |23.90 |
         */

        agent_distance += Math.sqrt(Math.pow(move.getY() - 0, 2) + Math.pow(move.getX() - 0, 2));
        agent_distance += Math.sqrt(Math.pow(move.getY() - 0, 2) + Math.pow(move.getX() - 7, 2));
        agent_distance += Math.sqrt(Math.pow(move.getY() - 7, 2) + Math.pow(move.getX() - 0, 2));
        agent_distance += Math.sqrt(Math.pow(move.getY() - 7, 2) + Math.pow(move.getX() - 7, 2));

        return agent_distance + (agent_moves.size() - opponent_moves.size());
    }



    ArrayList<Move> generateSuccessors(Board board){
        int i = 1;
        ArrayList<Move> successors = new ArrayList<Move>();

        while(true){

            if(board.isNotValidMove(this, new Move(this.getXPosition() - i, this.getYPosition())))
                successors.add(new Move(this.getXPosition() - i, this.getYPosition()));

            if(board.isNotValidMove(this, new Move(this.getXPosition() + i, this.getYPosition())))
                successors.add(new Move(this.getXPosition() + i, this.getYPosition()));

            if(board.isNotValidMove(this, new Move(this.getXPosition(), this.getYPosition()- i)))
                successors.add(new Move(this.getXPosition(), this.getYPosition()- i));

            if(board.isNotValidMove(this, new Move(this.getXPosition(),this.getYPosition() + i)))
                successors.add(new Move(this.getXPosition(),this.getYPosition() + i));

            if(board.isNotValidMove(this, new Move(this.getXPosition() - i, this.getYPosition() - i)))
                successors.add(new Move(this.getXPosition() - i, this.getYPosition() - i));

            if(board.isNotValidMove(this,  new Move(this.getXPosition() - i, this.getYPosition() + i)))
                successors.add(new Move(this.getXPosition() - i, this.getYPosition() + i));

            if(board.isNotValidMove(this, new Move(this.getXPosition() + i, this.getYPosition() + i)))
                successors.add(new Move(this.getXPosition() + i, this.getYPosition() + i));

            if(board.isNotValidMove(this, new Move(this.getXPosition() + i, this.getYPosition() - i)))
                successors.add(new Move(this.getXPosition() + i, this.getYPosition() - i));

            if(i > 8)
                break;

            i++;
        }

        return successors;
    }

}
