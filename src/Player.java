public class Player {

    private int playerXPosition;
    private int playerYPosition;
    private double time;
    protected boolean ai;

    Player(){}

    Player(int xPosition, int yPosition, boolean ai) {
        playerXPosition = xPosition;
        playerYPosition = yPosition;
        this.ai = ai;
    }

    Move getLocation(){
        return new Move(this.getX(), this.getY());
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public boolean getAI(){
        return ai;
    }

    int getX() {return playerXPosition;}

    int getY() {return playerYPosition;}

    void setXPosition(int playerXPosition) {
        this.playerXPosition = playerXPosition;
    }

    void setYPosition(int playerYPosition) {
        this.playerYPosition = playerYPosition;
    }

    public Move move_diagonal_up_right(){
        return new Move(this.getX() + 1, this.getY() - 1);
    }

    public Move move_diagonal_up_left(){
        return new Move(this.getX() - 1, this.getY() - 1);
    }

    public Move move_diagonal_down_right(){
        return new Move(this.getX() + 1, this.getY() + 1);
    }

    public Move move_diagonal_down_left(){
        return new Move(this.getX() - 1, this.getY() + 1);
    }

    public Move move_up(){
        return new Move(this.getX() , this.getY() - 1);
    }

    public Move move_down(){
        return new Move(this.getX(), this.getY() + 1);
    }

    public Move move_left(){
        return new Move(this.getX() - 1, this.getY());
    }

    public Move move_right(){
        return new Move(this.getX() + 1, this.getY());
    }
}
