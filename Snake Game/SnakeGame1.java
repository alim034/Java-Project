import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class SnakeGame1 extends JPanel implements ActionListener, KeyListener 
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / UNIT_SIZE;
    private static final int DELAY = 75;
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 6;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;



    public SnakeGame1() 
    {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        startGame();
    }


    public void startGame() 
    {
        running = true;
        spawnApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }


    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g) 
    {
        if (running) 
        {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);


            
            for (int i = 0; i < bodyParts; i++) 
            {
                if (i == 0) 
                {
                    g.setColor(Color.green);
                } 
                else 
                {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.white);
            g.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } 
        else 
        {
            gameOver(g);
        }
    }


    public void spawnApple() 
    {
        Random random = new Random();
        appleX = random.nextInt((int) (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }


    public void move() 
    {

        for (int i = bodyParts; i > 0; i--) 
        {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) 
        {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }


    public void checkApple() 
    {
        if (x[0] == appleX && y[0] == appleY) 
        {
            bodyParts++;
            applesEaten++;
            spawnApple();
        }
    }


    public void checkCollisions() 
    {
        for (int i = bodyParts; i > 0; i--) 
        {
            if (x[0] == x[i] && y[0] == y[i]) 
            {
                running = false;
            }
        }
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) 
        {
            running = false;
        }
        if (!running) 
        {
            timer.stop();
        }
    }


    public void gameOver(Graphics g) 
    {
        g.setColor(Color.white);
        g.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH - metrics1.stringWidth("Game Over")) / 2, HEIGHT / 2);
        g.setColor(Color.white);
        g.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    }

    

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) 
    {

        switch (e.getKeyCode()) 
        {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') 
                {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') 
                {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') 
                {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') 
                {
                    direction = 'D';
                }
                break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) 
    {

    }

    @Override
    public void keyReleased(KeyEvent e) 
    {

    }

    
    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame1 snakeGame = new SnakeGame1();
        frame.add(snakeGame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
