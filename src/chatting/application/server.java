package chatting.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class server extends JFrame implements ActionListener{
    
    JPanel p1;
    JTextField T1;
    JButton B1;
    static JTextArea TA;
    
     static ServerSocket sktk;
    static Socket S;
    static DataInputStream din;
    static DataOutputStream dout;
    
    Boolean typing;
    server(){
       
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0 ,450, 70);
        add(p1);
        
        // Create Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT); // image stored in i2
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1= new JLabel(i3);
        l1.setBounds(5, 17, 30, 30);
        p1.add(l1);
        l1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ae){
                    System.exit(0);
            }
            });
       
         ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icons/Hema.jpg"));
        Image i5 = i4.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2= new JLabel(i6);
        l2.setBounds(40, 5, 40, 40);
        p1.add(l2);
        
           ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l4= new JLabel(i9);
        l4.setBounds(290, 20, 30, 30);
        p1.add(l4);
        
         ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel l5= new JLabel(i12);
        l5.setBounds(360, 20, 35, 30);
        p1.add(l5);
        
         ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icons/dots.png"));
        Image i14 = i13.getImage().getScaledInstance(15, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel l6= new JLabel(i15);
        l6.setBounds(405, 20, 13, 30);
        p1.add(l6);
        
        
        // Create a User name
        JLabel l3 = new JLabel("HEMANTH");
        l3.setFont(new Font("Corbel", Font.BOLD, 10));
        l3.setForeground(Color.WHITE);
        l3.setBounds(110, 10, 100, 20);
        p1.add(l3);
        
        //
        JLabel l7 = new JLabel("Active Now");
        l7.setFont(new Font("Corbel", Font.PLAIN, 14));
        l7.setForeground(Color.WHITE);
        l7.setBounds(110, 35, 100, 20);
        p1.add(l7);
        
         Timer t = new Timer(1, new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent ae){
               if(!typing){
                   l4.setText("Active Now");
               }
           }
       });
       
       t.setInitialDelay(2000);
       
        // Create Textarea
        TA = new JTextArea();
        TA.setBounds(5, 75, 440, 570);
        TA.setBackground(Color.WHITE);
        TA.setFont(new Font("calibri", Font.PLAIN, 16));
        TA.setEditable(false);  // using these line you can't remove the text on the textfield.
        TA.setLineWrap(true);
        TA.setWrapStyleWord(true);
        add(TA);
        
         // Create TextField
        T1 = new JTextField();
        T1.setBounds(5, 655, 310, 40);
        T1.setFont(new Font("Calibri", Font.PLAIN, 16));
        add(T1);
        
         
       T1.addKeyListener(new KeyAdapter(){
           @Override
           public void keyPressed(KeyEvent ke){
               l4.setText("typing...");
               
               t.stop();
               
               typing = true;
           }
           
           @Override
           public void keyReleased(KeyEvent ke){
               typing = false;
               
               if(!t.isRunning()){
                   t.start();
               }
           }
       });
        
        //Create Button
        B1 = new JButton("SEND");
        B1.setBounds(320, 655, 123, 40);
        B1.setBackground(new Color(7, 94, 84));
        B1.setForeground(Color.WHITE);
        B1.setFont(new Font("Corbel", Font.PLAIN, 16));
        B1.addActionListener(this);
        add(B1);
        
        // Frame size and details
        getContentPane().setBackground(Color.pink);     // Background color of the gui Frame.
        setLayout(null);
        setSize(450, 750);
        setLocation(400, 200);
        //setUndecorated(true);
        setVisible(true);
        
    }
    
    @Override
     public void actionPerformed(ActionEvent ae){
        try{
            String out = T1.getText();
        TA.setText(TA.getText()+"\n\t\t\t"+out);     // \t\t\t  - Text data shows on left side.
        dout.writeUTF(out);
        T1.setText("");    // after sending the message and it'll remove the data on textfield.
    }catch (Exception e){
        System.out.println(e);
    }
    }
    
    public static void main(String[] a){
        new server().setVisible(true);
        
        String msginput = "";
        
    try{
        // Create Socket 
        sktk = new ServerSocket(6001);
        S = sktk.accept();
        din = new DataInputStream(S.getInputStream()); 
        System.out.println("message received");// Receive the data from client to server.
        dout = new DataOutputStream(S.getOutputStream()); 
        System.out.println("message send");// Send the Data from server to client.
        //Socket s = new Socket();sss
        msginput = din.readUTF();
        TA.setText(TA.getText()+"\n"+msginput);
        sktk.close();  // Socket connection closed
        S.close();     // Server connection also closed
    }
    catch (Exception e){
        
    }
    }
}
