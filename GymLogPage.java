import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;

public class GymLogPage implements ActionListener {
    
    JTextField textFieldUsername;
    JPasswordField textFieldPassword;
    RoundedButton buttonRegister;
    RoundedButton buttonLogin;
    JLabel progressBarText;
    JFrame frame;
    JProgressBar bar;
    protected String username, password;

    public GymLogPage() {
        File username = new File("username.txt");
        File password = new File("password.txt");
        
        try {
            if (!username.exists()) {
                username.createNewFile();
            }
            if (!password.exists()) {
                password.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        frame = new JFrame();
        
        //JButton for Login
        buttonLogin = new RoundedButton("LOGIN"); //Creates the button Login
        buttonLogin.addActionListener(this);
        buttonLogin.setBounds(275, 295, 241, 45);

        //JButton for Register
        buttonRegister = new RoundedButton("REGISTER"); // Creates the button Register
        buttonRegister.addActionListener(this);
        buttonRegister.setBounds(275, 361, 241, 45);

        //TextField for Username
        textFieldUsername = new JTextField("USERNAME"); //Creates the textfield Username
        textFieldUsername.setBounds(275, 212, 241, 21);

        textFieldUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == ' ') e.consume();
            }
        });

        //TextField for Password
        textFieldPassword = new JPasswordField("PASSWORD");
        textFieldPassword.setBounds(275, 246, 241, 21);
        textFieldPassword.setEchoChar((char) 0);

        textFieldPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(textFieldPassword.getPassword()).equals("PASSWORD")) {
                    textFieldPassword.setText("");
                    textFieldPassword.setEchoChar('*');
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(textFieldPassword.getPassword()).isEmpty() || 
                    String.valueOf(textFieldPassword.getPassword()).equalsIgnoreCase("PASSWORD")) {
                    textFieldPassword.setText("PASSWORD");
                    textFieldPassword.setEchoChar((char) 0);
                }
            }
        });

        Font montserratFont = null;
        try {
            montserratFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Montserrat\\static\\Montserrat-Thin.ttf"));
            montserratFont = montserratFont.deriveFont(Font.PLAIN, 13); 

            final Font finalMonteserratFont = montserratFont;
            textFieldUsername.setFont(finalMonteserratFont);

            textFieldUsername.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String text = textFieldUsername.getText();
                    if (text.isEmpty() || text.equalsIgnoreCase("USERNAME")) {
                        textFieldUsername.setFont(finalMonteserratFont); // Default font
                    }  else {
                        textFieldUsername.setFont(finalMonteserratFont.deriveFont(Font.BOLD, 13)); 
                    }
                }
            });

            textFieldUsername.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (textFieldUsername.getText().equals("USERNAME")) {
                        textFieldUsername.setText("");
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (textFieldUsername.getText().isEmpty() || textFieldUsername.getText().equalsIgnoreCase("USERNAME")) {
                        textFieldUsername.setText("USERNAME");
                    }
                }
            });
            
            textFieldPassword.setFont(finalMonteserratFont);

        } catch (Exception e) {
            System.out.println("Error: Could not load Montserrat font.");
        }

        ImageIcon imageLogo = new ImageIcon("Resources\\img\\Logo Gym.jpg");
        Image image = imageLogo.getImage();
        Image resizedImage = image.getScaledInstance(301, 203, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        JLabel logo = new JLabel(resizedImageIcon);
        logo.setBounds(245, 20, 301, 203);  

        frame.setIconImage(resizedImage); 
        frame.setTitle("Gym Log In");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setResizable(false);
        frame.setSize(785, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.add(logo);
        frame.add(buttonLogin);
        frame.add(buttonRegister);
        frame.add(textFieldUsername);
        frame.add(textFieldPassword);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonLogin) {
            username = textFieldUsername.getText();
            password = String.valueOf(textFieldPassword.getPassword());

            if (username.equalsIgnoreCase("USERNAME") || password.equalsIgnoreCase("PASSWORD")) {
                JOptionPane.showMessageDialog(null, "Please enter valid credentials.", "WARNING", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.equals("admin") && password.equals("admin123")) {
                frame.dispose();
                AdminPage admin = new AdminPage();
            } else if (!credentialsExist(username, password)) {
                JOptionPane.showMessageDialog(null, "Wrong Username or Password", "WARNING", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Login Successful", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if(e.getSource() == buttonRegister){
            frame.dispose();
            RegisterPage registerPage = new RegisterPage();
        }
    }

    private boolean credentialsExist(String username, String password) {
        File fileUsername = new File("username.txt");
        File filePassword = new File("password.txt");

        try {
            BufferedReader usernameReader = new BufferedReader(new FileReader(fileUsername));
            BufferedReader passwordReader = new BufferedReader(new FileReader(filePassword));
            String usernameCurrentLine;
            String passwordCurrentLine;

            while ((usernameCurrentLine = usernameReader.readLine()) != null && 
                   (passwordCurrentLine = passwordReader.readLine()) != null) {
                if (usernameCurrentLine.equals(username) && passwordCurrentLine.equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return false;
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
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        return shape.contains(x, y);
    } 
}