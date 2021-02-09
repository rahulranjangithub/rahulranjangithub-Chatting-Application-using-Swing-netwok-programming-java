package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server11 implements ActionListener{
    
    static JPanel label_pane;
    JTextField text_field;
    JButton button_2;
    static JPanel mid_panel;
    static JFrame f1 = new JFrame();
    JScrollPane scrollpane;
    static Box vertical = Box.createVerticalBox();
    static JButton button_1;
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    
    Boolean typing;
    
    Server11(){
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        label_pane = new JPanel();
        label_pane.setLayout(null);
        label_pane.setBackground(new Color(18, 140, 126));
        label_pane.setBounds(0, 0, 350, 50);
        f1.add(label_pane);
        
        
        
        ImageIcon image_1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/photo.jpg"));
        Image img_1 = image_1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(img_1);
        JLabel jlabel_1 = new JLabel(scaledImage);
        jlabel_1.setBounds(0, 0, 50, 50);
        
        
        label_pane.add(jlabel_1);
        
        button_1 = new JButton("CLOSE");
        button_1.setBounds(245,7,100,35);
        button_1.setBackground(new Color(7,94,84));
        button_1.setForeground(Color.WHITE);
        button_1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        
        label_pane.add(button_1);
        
        button_1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
           }
        });
        
       
        JLabel jlabel_2 = new JLabel("Rahul Ranjan");
        jlabel_2.setBounds(75,0, 145 , 25);
        jlabel_2.setFont(new Font("SansSarif",Font.BOLD,15));
        label_pane.add(jlabel_2);
        
        JLabel jlabel_3 = new JLabel("Active Now");
        jlabel_3.setFont(new Font("SansSarif",Font.BOLD,12));
        jlabel_3.setBounds(75,15,175,25);
        label_pane.add(jlabel_3);

        
        mid_panel = new JPanel();
        //mid_panel.setBounds(0, 50, 350, 400);
        mid_panel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        //f1.add(mid_panel);
        scrollpane = new JScrollPane(mid_panel);
        scrollpane.setBounds(0,50,350,400);
        scrollpane.setBorder(BorderFactory.createEmptyBorder());
        ScrollBarUI scrollui = new BasicScrollBarUI(){
            protected JButton createDecreaseButton(int orientation){
                JButton dec_button = super.createDecreaseButton(orientation);
                dec_button.setBackground(new Color(7,94,84));
                dec_button.setForeground(Color.white);
                this.thumbColor = new Color(7,94,84);
                return dec_button;
            }
            protected JButton createIncreaseButton(int orientation){
                JButton inc_button = super.createIncreaseButton(orientation);
                inc_button.setBackground(new Color(7,94,84));
                inc_button.setForeground(Color.white);
                this.thumbColor = new Color(7,94,84);
                return inc_button;
            }
        };
        scrollpane.getVerticalScrollBar().setUI(scrollui);
        scrollpane.setAutoscrolls(false);
        f1.add(scrollpane);
        
       
       Timer timer = new Timer(1,new ActionListener(){
           @Override
          public void actionPerformed(ActionEvent e){
              if(!typing)
                  jlabel_3.setText("Active Now..");
          } 
       });
       timer.setInitialDelay(5000);
       
       text_field = new JTextField();
       text_field.setBounds(5, 455, 240, 40);
       text_field.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
       f1.add(text_field);
       
       text_field.addKeyListener(new KeyAdapter(){
           public void keyPressed(KeyEvent ke){
               jlabel_3.setText("typing...");
               
               timer.stop();
               
               typing = true;
           }
           
           public void keyReleased(KeyEvent ke){
               typing = false;
               
               if(!timer.isRunning()){
                   timer.start();
               }
           }
       });
       
       button_2 = new JButton("Send");
       button_2.setBounds(255,455,100,40);
       button_2.setBackground(new Color(7, 94, 84));
       button_2.setForeground(Color.WHITE);
       button_2.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
       button_2.addActionListener(this);
       f1.add(button_2);
        
       f1.getContentPane().setBackground(Color.WHITE);
       f1.setLayout(null);
       f1.setSize(350, 500);
       f1.setLocation(400, 100);
       //f1.setLocationRelativeTo(null);
       f1.setUndecorated(true);
       f1.setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        try{
            String out = text_field.getText();
            
            JPanel p2 = formatLabel(out);
            
            mid_panel.setLayout(new BorderLayout());
            
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            
            mid_panel.add(vertical, BorderLayout.PAGE_START);
            
            //a1.add(p2);
            dout.writeUTF(out);
            text_field.setText("");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 10));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
    }
    
    public static void main(String[] args){
        new Server11().f1.setVisible(true);
        
        String msginput = "";
        try{
            skt = new ServerSocket(5000);
            while(true){
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
            
	        while(true){
	                msginput = din.readUTF();
                        JPanel p2 = formatLabel(msginput);
                        
                        JPanel left = new JPanel(new BorderLayout());
                        left.add(p2, BorderLayout.LINE_START);
                        vertical.add(left);
                        f1.validate();
            	}
                
            }
            
        }catch(Exception e){}
    }    
}
