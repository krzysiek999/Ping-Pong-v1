/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.codejava.pingpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Krzysztof Słuja
 */
public class GamePanel extends JPanel implements Runnable
{
//Initializing the variables
   private Paddle first = new Paddle(1, 500, Color.GREEN);
   private Paddle second = new Paddle(2, 500, Color.RED);
   private GameFrame frame;
   private Ball ball;
   private int gameForm;
   private Thread main;
   private boolean run = false;
   private boolean gameStarted = false;
    
    public GamePanel(int a, int b, int form, GameFrame frame) {
        this.gameForm = form;
        this.frame = frame;
        ball = new Ball(first, second, 500, gameForm);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new Steering(this)); //adding ability to ster the paddle/s
        main = new Thread(this);
        
       
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
   first.draw(g); //drawing first paddle
   second.draw(g); // drawing second paddle
   ball.draw(g); // drawing ball
   if (ball.getScoreEnd()) ball.paintResult(g); //If game ends, the score with information will be displayed
   if (!gameStarted) drawInfo(g); //Displaying informations at the beginning and at the end
    }
    
    //getting mode of the game
    public int getGameForm()
    {
        return this.gameForm;
    }
    
    
    private void drawInfo(Graphics g)
    {
        //Information at the beginning
        if(!ball.getScoreEnd())
        {
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("PRESS ENTER TO START", 330, 200);
        }
        //Information at the end
        if(ball.getScoreEnd())
        {
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("PRESS ESCAPE TO EXIT", 330, 200);
        }
    }
    
//Thread running
    @Override
    public void run() {
        while(run)
        {
        try {
            ball.ballMovement();
            ball.ballCollision();
            ball.setSpeed();
            if(gameForm == 1) ball.AIPaddleMovement();
            Thread.sleep(ball.getSpeed());
            if(ball.getScoreEnd()) throw new InterruptedException();
            repaint();
            
        } catch (InterruptedException ex) 
        {
            gameStarted = false;
            run = false;
            repaint();
        }
    }
    }
    
    class Steering extends KeyAdapter
    {
GamePanel panel;
        public Steering(GamePanel panel) 
        {
        this.panel = panel;
        }
        

        @Override
        public void keyPressed(KeyEvent e) 
        {
            //Moving the first paddle
        if (e.getKeyCode() == KeyEvent.VK_W) 
        {
            first.setDirection(true);
            first.paddleMoving();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_S) 
        {
           first.setDirection(false);
           first.paddleMoving();
        }
        //Moving the second paddle only if it is 2-player game
        if(gameForm == 2)
        {
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
             second.setDirection(true);
            second.paddleMoving();
        }
        
         if (e.getKeyCode() == KeyEvent.VK_DOWN) 
        {
           second.setDirection(false);
           second.paddleMoving();
        }
        }
        
        //If ENTER gets pressed at the beginning, the thread will start doing its tasks
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !gameStarted)
        {
          
            main.start();
            run = true;
            gameStarted = !gameStarted;
        }
        
        //If ESCAPE gets pressed at the end, the start menu will be displayed
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE && !gameStarted)
        {
           frame.dispose();
           StartingMenu a = new StartingMenu();
           a.setVisible(true);
        }
        }
    }
}



