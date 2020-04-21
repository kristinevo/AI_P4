public class Player {

    private int playerID;
    private int playerXPosition;
    private int playerYPosition;


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
}
