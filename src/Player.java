public class Player {

    private int playerXPosition;
    private int playerYPosition;


    Player(){}

    Player(int xPosition, int yPosition) {
        playerXPosition = xPosition;
        playerYPosition = yPosition;
    }

    int getXPosition() {return playerXPosition;}

    int getYPosition() {return playerYPosition;}

    void setXPosition(int playerXPosition) {
        this.playerXPosition = playerXPosition;
    }

    void setYPosition(int playerYPosition) {
        this.playerYPosition = playerYPosition;
    }

    public Move move_diagonal_up_right(){
        return new Move(this.getXPosition() + 1, this.getYPosition() - 1);
    }

    public Move move_diagonal_up_left(){
        return new Move(this.getXPosition() - 1, this.getYPosition() - 1);
    }

    public Move move_diagonal_down_right(){
        return new Move(this.getXPosition() + 1, this.getYPosition() + 1);
    }

    public Move move_diagonal_down_left(){
        return new Move(this.getXPosition() - 1, this.getYPosition() + 1);
    }

    public Move move_up(){
        return new Move(this.getXPosition() , this.getYPosition() - 1);
    }

    public Move move_down(){
        return new Move(this.getXPosition(), this.getYPosition() + 1);
    }

    public Move move_left(){
        return new Move(this.getXPosition() - 1, this.getYPosition());
    }

    public Move move_right(){
        return new Move(this.getXPosition() + 1, this.getYPosition());
    }
}
