import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class RegisterPage extends JFrame implements ActionListener{
    JButton cancel; 
    JButton register;
    JTextField textFieldUsername;
    JPasswordField textFieldPassword;
    JLabel message;
    protected String username, password;

    public RegisterPage(){
      
        register = new JButton();
        cancel = new JButton();
        JLabel username = new JLabel();
        JLabel password = new JLabel();
        textFieldUsername = new JTextField();
        textFieldPassword = new JPasswordField();
        message = new JLabel();
        
        message.setForeground(new Color(255, 0, 0));
        username.setText("Username");
        password.setText("Password");
        register.setText("Register");
        cancel.setText("Cancel");

        message.setBounds(100, 200,  500, 30);
        username.setBounds(100, 100, 80, 30);   
        textFieldUsername.setBounds(200, 100, 200, 30);
        password.setBounds(100, 150, 80, 30);
        textFieldPassword.setBounds(200, 150, 200, 30);
        register.setBounds(150, 300, 100, 30);
        cancel.setBounds(260, 300, 100, 30);    


        cancel.addActionListener(this);
        register.addActionListener(this);

        add(message);
        add(textFieldPassword);
        add(register);
        add(cancel);
        add(username);
        add(password);
        add(textFieldUsername);

        setUndecorated(true);
        setSize(500,500);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(179, 173, 173));
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

                            BufferedWriter userWriter = new BufferedWriter(new FileWriter(fileUsername));
                            BufferedWriter passwordWriter = new BufferedWriter(new FileWriter(filePassword));
                            
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