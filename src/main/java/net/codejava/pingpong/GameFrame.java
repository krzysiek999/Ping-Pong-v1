/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.codejava.pingpong;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author Krzysztof Słuja
 */
public class GameFrame extends JFrame
{
    private int gameForm;
    private GamePanel panel;
    private int startingX = Toolkit.getDefaultToolkit().getScreenSize().width/2 - 500;
    private int startingY = Toolkit.getDefaultToolkit().getScreenSize().height/2 - 250;
    
    
    public GameFrame(int form) throws HeadlessException 
    {
    this.gameForm = form;
   this.setResizable(false);
   this.setTitle("Ping Pong V1");
    panel  = new GamePanel(1000,500,form,this);
    this.setBounds(startingX, startingY,1000,500);
    this.getContentPane().add(panel);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    



    
}
