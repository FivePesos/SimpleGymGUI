import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;

import javax.swing.*;

public class RegisterPage extends JFrame implements ActionListener{
    RoundedButton cancel; 
    RoundedButton register;
    JTextField textFieldFullName;
    JTextField textFieldUsername;
    JPasswordField textFieldPassword;
    JLabel title;
    JLabel message;
    JLabel name;

    protected String username, password;

    public RegisterPage(){
      
        register = new RoundedButton("REGISTER");
        cancel = new RoundedButton("CANCEL");
        JLabel username = new JLabel();
        JLabel password = new JLabel();
        textFieldUsername = new JTextField("USERNAME");
        textFieldPassword = new JPasswordField("SET PASSWORD");
        textFieldFullName = new JTextField("SET FULL NAME");
        name = new JLabel();
        message = new JLabel();
        
        message.setForeground(new Color(255, 0, 0));
        username.setText("USERNAME");
        password.setText("PASSWORD");
        register.setText("REGISTER");
        name.setText("NAME");
        cancel.setText("CANCEL");

        

        textFieldUsername.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if(e.getKeyChar() == ' ')
                 e.consume();
            }
        });

        textFieldPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                if(e.getKeyChar() == ' '){
                    e.consume();
                }
            }
        });
        
        
        try {
            Font montserrat;
            montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Montserrat\\static\\Montserrat-Bold.ttf")).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            // Title Label
            title = new JLabel("REGISTER");
            title.setFont(montserrat.deriveFont(Font.BOLD, 48));
            title.setBounds(266,52,253,50);
        } catch (FontFormatException | IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            Font montserrat;
            montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Montserrat\\static\\Montserrat-Thin.ttf")).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            // Title Label
            name.setFont(montserrat.deriveFont(Font.BOLD, 13));
            username.setFont(montserrat.deriveFont(Font.BOLD, 13));
            password.setFont(montserrat.deriveFont(Font.BOLD, 13));
        } catch (FontFormatException | IOException e) {
            System.out.println(e.getMessage());
        }

        textFieldPassword.setEchoChar((char) 0); // No echo char for placeholder
        textFieldPassword.setText("PASSWORD"); // Initial placeholder text

        textFieldPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(textFieldPassword.getPassword()).equals("PASSWORD")) {
                    textFieldPassword.setText(""); // Clear placeholder
                    textFieldPassword.setForeground(Color.BLACK);
                    textFieldPassword.setEchoChar('*'); // Restore echo char
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(textFieldPassword.getPassword()).isEmpty()) {
                    textFieldPassword.setEchoChar((char) 0); // Hide characters again
                    textFieldPassword.setText("PASSWORD"); // Reset placeholder
                    textFieldPassword.setForeground(Color.GRAY); // Placeholder color
                }
            }
        });

        try {
            Font montserrat;
            montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Montserrat\\static\\Montserrat-Thin.ttf")).deriveFont(13f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat); 

            textFieldUsername.setFont(montserrat.deriveFont(13));

            textFieldUsername.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (textFieldUsername.getText().equals("USERNAME")) {
                        textFieldUsername.setText("");
                        textFieldUsername.setForeground(Color.BLACK); // Set text color to black for user input
                    }
                }
            
                @Override
                public void focusLost(FocusEvent e) {
                    if (textFieldUsername.getText().isEmpty()) {
                        textFieldUsername.setText("USERNAME");
                        textFieldUsername.setForeground(Color.GRAY); // Placeholder text color
                    }
                }
            });
            
            textFieldFullName.setFont(montserrat.deriveFont(13));

            textFieldFullName.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (textFieldFullName.getText().equals("SET FULL NAME")) {
                        textFieldFullName.setText("");
                        textFieldFullName.setForeground(Color.BLACK); // Set text color to black for user input
                    }
                }
            
                @Override
                public void focusLost(FocusEvent e) {
                    if (textFieldFullName.getText().isEmpty()) {
                        textFieldFullName.setText("SET FULL NAME");
                        textFieldFullName.setForeground(Color.GRAY); // Placeholder text color
                    }
                }
            });

         
            textFieldPassword.setFont(montserrat);

        } catch (Exception e) {
            System.out.println("Error: Could not load Montserrat font.");
        }

        textFieldFullName.setBounds(272,162,241,21);
        name.setBounds(282,133,50,13);
        message.setBounds(100, 200,  500, 30);
        username.setBounds(282, 204, 78, 13);   
        textFieldUsername.setBounds(272, 234, 241, 21);
        password.setBounds(280, 276, 78, 13);
        textFieldPassword.setBounds(272, 306, 241, 21);
        register.setBounds(119, 389, 241, 45);
        cancel.setBounds(407, 389, 241, 45);    

        cancel.addActionListener(this);
        register.addActionListener(this);

        add(title);
        add(textFieldFullName);
        add(message);
        add(textFieldPassword);
        add(register);
        add(cancel);
        add(username);
        add(password);
        add(textFieldUsername);
        add(name);
        
        setUndecorated(true);
        setSize(785,500);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255,255,255));
        setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == cancel){
            dispose();
            GymLogPage gymLogPage = new GymLogPage();
        }
        
        if(e.getSource() == register){
            if(textFieldUsername.getText().isEmpty() && String.valueOf(textFieldPassword.getPassword()).isEmpty())
                message.setText("Note: No Username and Password");
            else if(textFieldUsername.getText().isEmpty())
                message.setText("Note: No Username");
            else if(String.valueOf(textFieldPassword.getPassword()).isEmpty())
                message.setText("Note: No Password");
            else{
                username = textFieldUsername.getText();
                password = String.valueOf(textFieldPassword.getPassword());

                if(username.length() <= 6 && password.length() <= 6){
                    message.setText("Note: Username and Password must have atleast a length of 7");
                }
                else if(username.length() <= 6){
                    message.setText("Note: Username must have atleast a length of 7");
                }else if(password.length() <= 6){
                    message.setText("Note: Password must have atleast a length of 7");
                }else{

                    if(usernameExist(username) && passwordExist(password)){
                        message.setText("Note: Please enter another username and password");
                        textFieldUsername.setText("");
                        textFieldPassword.setText("");
                    }else if(usernameExist(username)){
                        message.setText("Note: Please enter another username");
                        textFieldUsername.setText("");
                    }else if(passwordExist(password)){
                        message.setText("Note: Please enter another password");
                        textFieldPassword.setText("");
                    }else{
                        File fileUsername = new File("username.txt");
                        File filePassword = new File("password.txt");

                        try{

                            BufferedWriter userWriter = new BufferedWriter(new FileWriter(fileUsername ,true));
                            BufferedWriter passwordWriter = new BufferedWriter(new FileWriter(filePassword , true));
                            
                            userWriter.write(username + "\n");
                            passwordWriter.write(password + "\n");

                            userWriter.close();
                            passwordWriter.close();

                        }catch(Exception error){
                            System.out.println(error.getMessage());
                        }

                        message.setForeground(new Color(0,255,0));
                        textFieldUsername.setText("");
                        textFieldPassword.setText("");
    
                        JOptionPane.showMessageDialog(this, "Succefully Registered", "REGISTERED", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        GymLogPage gymLogPage = new GymLogPage();
                    }  
                }
            }
        }
    }

    //Checks if the Full name exist already in the fullname.txt
    private static boolean fullNameExist(String fullName){
        boolean match = false;
        File fileFullName = new File("fullname.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileFullName));
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                if(currentLine.equals(fullName)){
                    match = true;
                    break;
                }else{
                    match = false;
                }
            }
            reader.close();
        }catch (Exception e) {
            return false;
        }
        return match;
    }

    //Checks if the username exist already in the username.txt
    private static boolean usernameExist(String username){
        boolean match = false;
        File fileUsername = new File("username.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileUsername));
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                if(currentLine.equals(username)){
                    match = true;
                    break;
                }else{
                    match = false;
                }
            }
            reader.close();
        }catch (Exception e) {
            return false;
        }
        return match;
    }

    //Checks if the password already exist in the password.txt
    private static boolean passwordExist(String password){
        boolean match = false;
        File filePassword = new File("password.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePassword));
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                if(currentLine.equals(password)){
                    match = true;
                    break;
                }else{
                    match = false;
                }
            }
            reader.close();
        }catch (Exception e) {
            return false;
        }
        return match;
    }
}

class RoundedButton extends JButton{

    private int cornerRadius = 10; 
    private Color borderColor = new Color(17, 13, 13); 
    private int borderWidth = 3; 
    private Font montserratFont; 

    
    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);  // Remove the focus border when clicked
        setContentAreaFilled(false);  // Remove default button background
        setOpaque(false);  // Make the button transparent

        // Load Montserrat font
        try {
            montserratFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Montserrat\\static\\Montserrat-SemiBold.ttf"));
            montserratFont = montserratFont.deriveFont(Font.PLAIN, 16); // Set size to 16 (or your preferred size)
            setFont(montserratFont); // Apply the font to the button
        } catch (FontFormatException | IOException e) {
            System.out.println("Error: Could not load Montserrat font.");
            // Fallback to the default font if Montserrat is not available
            setFont(new Font("Arial", Font.PLAIN, 16));
        }

        addMouseListener(new MouseAdapter() {
            
        
            @Override
            public void mousePressed(MouseEvent me) {
                // Click: Set foreground to white and background to black
                setForeground(new Color(255, 255, 255));
                setBackground(new Color(0, 0, 0));
            }
        
            @Override
            public void mouseReleased(MouseEvent me) {
                // Release click: Set foreground to black and background to white
                setForeground(new Color(0, 0, 0));
                setBackground(new Color(255, 255, 255));
            }
        });
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Smooth edges

        // Clip the area to a rounded rectangle to ensure the corners are properly rounded
        g2d.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        // Paint the button's background
        if (getModel().isPressed()) {
            g2d.setColor(new Color(0, 0, 0)); // Color when the button is pressed (black)
        } else {
            g2d.setColor(new Color(255, 255, 255)); // Normal button color (white)
        }
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius); // Draw the button with rounded corners

        // Draw the border
        g2d.setColor(borderColor); // Set border color
        g2d.setStroke(new BasicStroke(borderWidth)); // Set the border width
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius); // Draw the border

        // Set the text color to black by default
        if (getModel().isPressed()) {
            g2d.setColor(Color.WHITE); // Text color when pressed (white)
        } else {
            g2d.setColor(Color.BLACK); // Text color when not pressed (black)
        }

        // Paint the text with the appropriate color
        super.paintComponent(g);  // Paint the text on top of the rounded shape
    }

    @Override
    public boolean contains(int x, int y) {
        // Ensure that mouse events are correctly recognized within the rounded corners
        RoundRectangle2D.Float shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        return shape.contains(x, y);
    } 
}