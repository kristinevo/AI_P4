public class Isolation {









    public static void main(String args[]){
        Board a = new Board();
        System.out.println(a);
        Move move = new Move(5,5);

        if(a.isValidMove(a.player1, move))
            a.updateBoard(a.player1, move);


        System.out.println();
        System.out.println(a);

    }


}
