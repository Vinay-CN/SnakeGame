import javax.swing.JFrame;

public class SnakeGame extends JFrame{
    Board board ;
    SnakeGame()
    {

        board = new Board() ;
        add(board);
        pack();
        setResizable(false); //to stop resizing
        setVisible(true);


    }
    public static void main(String[] args)
    {
        SnakeGame snakeGame = new SnakeGame();
    }
}