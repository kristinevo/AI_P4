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

    public Move aplhaBetaSearch(Board state) {
        ArrayList<Move> successors = generateSuccessors(state);
        double v, best_v = INFINITY;
        double alpha = NEGATIVE_INFINITY;
        current_depth++;

        if(successors.isEmpty()){
            return new Move(-1,-1);
        }

        Board temp;

        for (int s = 0; s < successors.size(); s++) {
            //TODO: the player's are dependent on player choice
            temp = state.copyBoard();
            temp.updateBoard(this, successors.get(s));
            v = minValue(temp, Integer.MAX_VALUE, Integer.MAX_VALUE);
            if(v >= best_v){
                ai_next_move = successors.get(s);
            }

            alpha = Math.max(alpha, v);
        }

        return ai_next_move;
    }

    double maxValue(Board state, double alpha, double beta) {
        //TODO: have a function which calculates the score of the state
        if(current_depth > MAX_DEPTH || state.terminalTest(opponent))
            return -1;

        current_depth++;
        double value = NEGATIVE_INFINITY;
        ArrayList<Move> successors = generateSuccessors(state);
        Board temp;

        for (int s = 0; s < successors.size(); s++) {
            //TODO: the player's are dependent on player choice
            temp = state.copyBoard();
            temp.updateBoard(this, successors.get(s));
            value = Math.max(value, minValue(temp, alpha, beta));
            if(value == -1){
                return heuristic_function(temp, successors.get(s));
            }
            else if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }

        return value;
    }

    double minValue(Board state, double alpha, double beta) {
        //TODO: have a function which calculates the score of the state
        if(current_depth == MAX_DEPTH || state.terminalTest(opponent))
            return -1;

        current_depth++;
        double value = INFINITY;
        ArrayList<Move> successors = generateSuccessors(state);
        Board temp;

        for (int s = 0; s < successors.size(); s++) {
            //TODO: the player's are dependent on player choice
            temp = state.copyBoard();
            temp.updateBoard(this, successors.get(s));
            value = Math.min(value, maxValue(temp, alpha, beta));
            if(value == -1){
                return heuristic_function(temp, successors.get(s));
            }

            else if (value <= beta) {
                return value;
            }
            alpha = Math.min(alpha, value);
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
