import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GymLogPage implements ActionListener{
    public boolean registerCall = false;
    JTextField textFieldUsername;
    JPasswordField textFieldPassword;
    JButton buttonRegister;
    JButton buttonLogin;
    JLabel progressBarText;
    JFrame frame;
    JProgressBar bar;
    protected String username, password;
    public GymLogPage(){

        File username = new File("username.txt");
        File password = new File("password.txt");
        
        try{
            if(!username.exists()){
                username.createNewFile();
            }
            if(!password.exists()){
                password.createNewFile();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        frame = new JFrame();
        
        //JLabel for Username
        JLabel labelUsername = new JLabel(); //Creates the label Username;
        labelUsername.setText("Username");
        labelUsername.setBounds(210, 300, 75, 25);

        //JLabel for Password
        JLabel labelPassword = new JLabel(); //Creates the label Password;
        labelPassword.setText("Password");
        labelPassword.setBounds(210,400, 75, 25);
    
        //JButton for Login 
        buttonLogin = new JButton(); //Creates the button Login;
        buttonLogin.addActionListener(this);
        buttonLogin.setText("Log in");
        buttonLogin.setBounds(200, 550, 90, 25);

        //JButton for Register
        buttonRegister = new JButton(); // Creates the button Register
        buttonRegister.addActionListener(this);//
        buttonRegister.setText("Register");
        buttonRegister.setBounds(200,600, 90, 25);

        //TextField for Username 
        textFieldUsername = new JTextField(); //Creates the textfield Username
        textFieldUsername.setBounds(140,330,200,30);
        
        //TextField for Password
        textFieldPassword = new JPasswordField(); //Creates the textfield Password
        textFieldPassword.setBounds(140,430, 200,30);

        //Sets the the image icon in a specified folder;
        ImageIcon image = new ImageIcon("Resources\\img\\GymIcon\\GymIcon-8.jpg");
        
        frame.setIconImage(image.getImage());
        frame.setTitle("Gym Log In");
        frame.getContentPane().setBackground(new Color(179, 173, 173));
        frame.setResizable(false);
        frame.setSize(500,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
    
        frame.add(labelUsername);
        frame.add(labelPassword);
        frame.add(buttonLogin);
        frame.add(buttonRegister);
        frame.add(textFieldUsername);
        frame.add(textFieldPassword);
            
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttonRegister){
            frame.dispose();
            RegisterPage registerPage = new RegisterPage();
        }

        if(e.getSource() == buttonLogin){
            
            username = textFieldUsername.getText();
            password = String.valueOf(textFieldPassword.getPassword());

            if(username.equals("admin") && password.equals("admin123")){
                frame.dispose();
                AdminPage admin = new AdminPage();
            }
            else{
                if(!usernameExist(username) || !passwordExist(password)){
                    JOptionPane.showMessageDialog(null, "Wrong Username or Password", "WARNING", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Login Successful", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
                }  
            }
        
        }
    }

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


