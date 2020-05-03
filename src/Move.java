class Move {
    private int x;
    private int y;

    /**
     * @param _x
     * @param _y
     */
    Move(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    /**
     * @return x
     */
    int getX() {return x;}

    /**
     * @return y
     */
    int getY() {return y;}

    /**
     * @param x
     */
    void setX(int x) { this.x = x; }

    /**
     * @param y
     */
    void setY(int y) { this.y = y; }
}
