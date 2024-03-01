package edu.ezip.ing1.pds.front;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

    public static void main(String[] args) {
        new MainFrame();
    }
}
    class MainFrame extends JFrame{
        MainFrame (){
            //---------partie fenetre-------------------------
            super("epital-employee-data");
            setSize(500,500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
            //-------------------------------------------------
            //--------PartiePrincipale-------------------------
            JPanel ptitle = new JPanel();
            JPanel pButton = new JPanel();
            ptitle.setBackground(Color.cyan);
            pButton.setBackground(Color.white);
            JLabel lp = new JLabel("EPITAL");
            lp.setFont(new Font("Times New Roman", Font.ITALIC, 50));
            lp.setForeground(Color.white);
            JButton b1 = new JButton("insert");
            b1.setContentAreaFilled(false);
            
            JButton b2 = new JButton("select");
            b2.setContentAreaFilled(false);
            ptitle.add(lp);
            pButton.add(b1);
            pButton.add(b2);
            getContentPane().add(ptitle, BorderLayout.NORTH);
            getContentPane().add(pButton, BorderLayout.CENTER);
        //----------------------------------------------------
        //-----------------InsertPanel------------------------
            b1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                remove(pButton);
                try {
                    getContentPane().add(new InsertPanel(), BorderLayout.CENTER);
                } catch (IOException | InterruptedException e1) {
                    
                    e1.printStackTrace();
                } 
                validate();
                invalidate();
                repaint();   
                }
                
            });
        //----------------------------------------------------------

        //------------------SelectPanel-----------------------------
            b2.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    remove(pButton);
                    getContentPane().add(new SelectPanel(), BorderLayout.CENTER);
                    validate();
                    invalidate();
                    repaint();
                                   }
                
            });
        //----------------------------------------------------------


        //--------Actualiser------------------------------------
        validate();
        invalidate();
        repaint();
        //---------------------------------------------------------



        }
    }
    