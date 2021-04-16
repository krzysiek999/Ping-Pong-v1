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
import java.util.Random;

/**
 *
 * @author Krzysztof Słuja
 */
public class Paddle 
{
//Initializing variables
private int height = 80;
private int width = 10;
private int xPaddle = 5;
private int yPaddle = 180;
private int dy = 10;
private int option = 0;
private int screenHeight = 0;
private int score = 0;

private boolean direction = true;
private Color playerColor;

private Random generator = new Random();

    public Paddle(int opcja, int sHeight, Color color) 
    {
    this.option = opcja;
    if(opcja == 2) xPaddle = 970;
    this.screenHeight = sHeight;
    playerColor = color;
    }

    //Drawing paddle and the score in the condition number of the paddle
    public void draw(Graphics g)
    {
        g.setColor(playerColor);
        g.fillRect(xPaddle, yPaddle, width, height);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
       if(option == 1) g.drawString("" + this.score, 200, 200);
       if(option == 2) g.drawString("" + this.score, 800, 200);
    }
    
    //Function setting the x of the paddle
    public void setX(int x)
    {
        this.xPaddle = x;
    }
    
    //Function setting the y of the paddle
    public void setY(int y)
    {
        this.yPaddle = y;
    }
    
    //Function getting the x of the paddle
    public int getX()
    {
        return this.xPaddle;
    }
    
    //Function getting the y of the paddle
    public int getY()
    {
        return this.yPaddle;
    }
    
    //Function getting the score of the player related with the paddle
    public int getScore()
    {
        return this.score;
    }
    
    public boolean getDirection()
    {
        return this.direction;
    }
    
    public void setDirection(boolean wartosc)
    {
        this.direction = wartosc;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public int getHeight()
    {
        return this.height;
    }
    
    public void setScore()
    {
        this.score += 1;
    }
    
    
void paddleMoving()
{
    //Limiting the paddle movement so that it does not disappear from the board
    if(yPaddle <= 0) yPaddle = 0; 
    if((yPaddle + height) >= screenHeight - 50) yPaddle = screenHeight - height - 50;
    
    //direction TRUE - up, FALSE - down
    if(direction) yPaddle -= dy;
    else yPaddle += dy;
}
    

public void AIpaddleMoving(int xBall, int yBall, boolean limitCross)
{
    
    //reacting to the ball while the limit is not exceeded
    if (xBall > 550 && !limitCross)
        {
             if (yPaddle > yBall - 10) yPaddle -= dy;
             if (yPaddle < yBall - 10) yPaddle += dy;
     
        }
    //Let player earn the point
    else if(xBall > 550 && limitCross)
    {
        if(yPaddle > yBall + 50) yPaddle -= dy;
        if (yPaddle < yBall + 50) yPaddle += dy;
    }
    
    //Limiting the paddle movement so that it does not disappear from the board
    if(yPaddle <= 0) yPaddle = 0;
    if((yPaddle + height) >= screenHeight - 50) yPaddle = screenHeight - height - 50;
    
    
    }
}
