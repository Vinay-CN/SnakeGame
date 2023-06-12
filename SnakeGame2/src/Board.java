import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class Board extends JPanel implements ActionListener {
    int B_Height = 400;
    int B_Width = 400;

    int MAX_SIZE = 1601; //400 * 400
    int DOT_SIZE = 10;
    int DOTS;

    int[] x = new int[MAX_SIZE];
    int[] y = new int[MAX_SIZE];
    Image head, body, apple;

    Timer timer;
    int DELAY = 150; //its milisecond

    public boolean leftDirection = true;
    public boolean rightDirection = false;
    public boolean upDirection = false;
    public boolean downDirection = false;

    boolean inGame = true;
    Board() {
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        //define the dimension of the board
        setPreferredSize(new DimensionUIResource(B_Width, B_Height));
        setBackground(Color.BLACK); //define black
        initGame();
        loadImages();
        timer = new Timer(DELAY, this);
        timer.start();

    }

    int apple_x;
    int apple_y;

    public void initGame() {
        DOTS = 3;  //game always strats with snakes size


        x[0] = 250;
        y[0] = 250;
        for (int i = 0; i < DOTS; i++) {
            x[i] = x[0] + DOT_SIZE * i;
            y[i] = y[0];
        }

        locateApple();

    }


    //load images from resources  folder to Image object
    public void loadImages() {

        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();

        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();

        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
    }

    //locate the apple position
    public void locateApple() {
        apple_x = (int) (Math.random() * 39) * DOT_SIZE; //random will generate nums from 0 to 9
        apple_y = (int) (Math.random() * 39) * DOT_SIZE;
    }
    private void checkCollision() {

        for (int i = DOTS; i > 0; i--) {

            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }

        if (y[0] >= B_Height) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_Width) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }
    //draw images at sanke and apples position
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
            if(inGame)
            {
                g.drawImage(apple, apple_x, apple_y, this);

                for (int i = 0; i < DOTS; i++) {
                    if (i == 0) {
                        g.drawImage(head, x[0], y[0], this);
                    } else {
                        g.drawImage(body, x[i], y[i], this);
                    }
                }
            }
            else
            {
                gameOver(g);
                timer.stop();
            }


    }
    public void gameOver(Graphics g) {

        String msg = "Game Over";
        int score = (DOTS-3)*100;
        String scoremsg = "\nScore: "+ Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_Width - metr.stringWidth(msg)) / 2, (B_Height / 2)-10);
        g.drawString(scoremsg, (B_Width - metr.stringWidth(scoremsg)) / 2,(B_Height / 2)+10 );
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public void move() {
        for (int i = DOTS - 1; i >= 1; i--) {

            x[i] = x[i - 1];
            y[i] = y[i - 1];

        }
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            DOTS++;
            locateApple();
        }
    }
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}

