/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.codejava.pingpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Krzysztof Słuja
 */
public class Ball 
{
//initialize the variables
   private int x, y, dx, dy, size; 
   private int speed;
   private boolean direction = true, directionVertical = true; //direction of moving ball
    //Variables related to the pads
  private  Paddle first;   
  private  Paddle second;
  private  int screenHeight;
    
 private   boolean scoreEnd = false; //it turns to TRUE when one of the players reaches 11 points
 private   boolean limitCross = false; //variable related to generator and counter of the
    
 private   int gameForm; // 1 - one player, 2 - two players
    
private    Random generator = new Random();
    
private    int counterPickup = 0; //counts amount of the collisions of the ball with the paddle
private    int pickUpLimit = 1 + generator.nextInt(6); //related with AIPaddle - amount of the ball which AI can bounce
private    int counter = 0, speedLimit = 1 + generator.nextInt(4); //counter - counts amount of the collisions of the ball with the paddle, speedLimit - after exceeding this amount the ball will increase its speed for one collision
    
    //Initializing
    public Ball(Paddle first, Paddle second, int Ssize,int gameForm) 
    {
       
    x = 465;
    y = 20 + generator.nextInt(450);
    dx = 4;
    dy = -4;
    size = 20;
    speed = 2;
    this.first = first;
    this.second = second;
    this.screenHeight = Ssize; 
    this.gameForm = gameForm;
        System.out.println("Limit: " + pickUpLimit);
    }
    
    //get the speed of the ball
    public int getSpeed()
    {
        return this.speed;
    }
    
    //get information if game ends
    public boolean getScoreEnd()
    {
        return this.scoreEnd;
    }
    
    //Drawing the ball
    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval(x, y, size, size);
    }
    
    //Enables automatic movement of the second paddle
    public void AIPaddleMovement()
    {
        second.AIpaddleMoving(this.x, this.y, limitCross);
    }
    
    //direction TRUE - ball is moving to the right side, FALSE - to the left
    public void ballMovement()
    {
        if(direction) x += dx;
        else x -= dx;
        
        if(directionVertical) y += dy;
        else y -= dy;
    }
    
    //Setting new limit when the conditions of the game have changed
    private int setNewLimit()
    {
        return 1+ generator.nextInt(6);
    }
    
    //Checking the ball collisions
    void ballCollision()
    {
        //if ball reaches upper or lower border, its Y direction will be changed to the opposite
        if(y < 0) 
        {
            y = 0;
            dy *= -1;
        }
        
        if((y + size) > (screenHeight - 30)) 
        {
            y = screenHeight - 30 - size;
            dy *= -1;
        }
    
        //If ball collides with first paddle, its X direction will be changed and counter will be increased 
       if( x < (first.getX() + first.getWidth()) && (y > first.getY() && y < first.getY() + first.getHeight())  ) 
       {
           dx *= -1;
           counter += 1;
           
       }
       
        //If ball collides with second paddle, its X direction will be changed and counter will be increased 
       else if ( (x + size) > second.getX() && (y > second.getY() && y < second.getY() + second.getHeight()) )
       {
           dx *= -1;
           counter += 1;
           
           //If the 1-player mode is selected, the counterPickup will increase
           if(gameForm == 1) 
           {
               System.out.println("a: " + counterPickup);
               counterPickup += 1;
               if(counterPickup == pickUpLimit) limitCross = true ;
               
           };
       }
       
       //Earning the point
       else if ( x < (first.getX() - 5) || (x+size) > second.getX() + 5) ballOut();
    }
    
    //Setting the ball speed
    public void setSpeed()
    {
        if(counter >= speedLimit) this.speed = 4; //After specified amount of collision the speed is increased
        else this.speed = 8; //Normal speed of the ball
        
        //Reseting counter and setting new limit
        if(counter == speedLimit + 1) 
        {
            counter = 0;
            speedLimit = setNewLimit();
        }
    }
    
    //Painting the result of the game in the end
    public void paintResult(Graphics g)
    {
        int opcja = 0;
        if(first.getScore() == 11)
        {
            opcja = 1;
            g.setColor(Color.GREEN);
        }
        else 
        {
            opcja = 2;
            g.setColor(Color.red);
        }
        String statement = "PLAYER " + opcja + " WON";
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString(statement, 400, 250);
    }
    
    //Function related with earning points (lack of collision with paddle)
    private void ballOut()
    {
        if (x < first.getX()) second.setScore(); //if the first paddle doesn't bounce the ball second player earns point
        else if( (x + size) > second.getX()) first.setScore(); //if the second paddle doesn't bounce the ball second player earns point
        
        //Continuating game if none of the player has 11 points;
        if (first.getScore() < 11 && second.getScore() < 11) 
        {
            x = 465;
            y = 20 + generator.nextInt(450); 
            pickUpLimit = setNewLimit();
            counterPickup = 0;
            this.speed = 12;
            direction = !direction;
            // reseting position of the paddles
            first.setY(180);
            second.setY(180);
        }
        else scoreEnd = true; //variable related with game end
        
        //reseting counter and setting new limit
        if(limitCross)
        {
            pickUpLimit = setNewLimit();
            counterPickup = 0;
            limitCross = !limitCross;
            System.out.println("bb: " + pickUpLimit);
        }
    }
    

    
}
