public class Player {

    private int playerXPosition;
    private int playerYPosition;
    protected boolean ai;

    /**
     * Default Construction
     */
    Player(){}

    /**
     * @param xPosition
     * @param yPosition
     * @param ai
     */
    Player(int xPosition, int yPosition, boolean ai) {
        playerXPosition = xPosition;
        playerYPosition = yPosition;
        this.ai = ai;
    }

    /**
     * @return new Move
     */
    Move getLocation(){
        return new Move(this.getX(), this.getY());
    }

    /**
     * @return ai
     */
    public boolean getAI(){
        return ai;
    }

    /**
     * @return playerXPosition
     */
    int getX() {return playerXPosition;}

    /**
     * @return playerYPosition
     */
    int getY() {return playerYPosition;}

    /**
     * @param playerXPosition
     */
    void setXPosition(int playerXPosition) {
        this.playerXPosition = playerXPosition;
    }

    /**
     * @param playerYPosition
     */
    void setYPosition(int playerYPosition) {
        this.playerYPosition = playerYPosition;
    }

    /**
     * An up right position
     * @return Move(this.getX() + 1, this.getY() - 1)
     */
    public Move move_diagonal_up_right(){
        return new Move(this.getX() + 1, this.getY() - 1);
    }

    /**
     * An up-left position
     * @return new Move(this.getX() - 1, this.getY() - 1)
     */
    public Move move_diagonal_up_left(){
        return new Move(this.getX() - 1, this.getY() - 1);
    }

    /**
     * A down-right position
     * @return Move(this.getX() + 1, this.getY() + 1)
     */
    public Move move_diagonal_down_right(){
        return new Move(this.getX() + 1, this.getY() + 1);
    }

    /**
     * A down-left position
     * @return Move(this.getX() - 1, this.getY() + 1)
     */
    public Move move_diagonal_down_left(){
        return new Move(this.getX() - 1, this.getY() + 1);
    }

    /**
     * An up move position
     * @return Move(this.getX() , this.getY() - 1)
     */
    public Move move_up(){
        return new Move(this.getX() , this.getY() - 1);
    }

    /**
     * A down move position
     * @return Move(this.getX(), this.getY() + 1)
     */
    public Move move_down(){
        return new Move(this.getX(), this.getY() + 1);
    }

    /**
     * A left move position
     * @return
     */
    public Move move_left(){
        return new Move(this.getX() - 1, this.getY());
    }

    /**
     * A right move position
     * @return Move(this.getX() + 1, this.getY())
     */
    public Move move_right(){
        return new Move(this.getX() + 1, this.getY());
    }
}
