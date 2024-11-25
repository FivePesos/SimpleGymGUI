import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.*;

public class AdminPage extends JFrame implements ActionListener {

    JPanel logo;
    JPanel title;
    JPanel body;
    JButton members;
    JButton pendingMembers;
    JButton activeMembers;
    JButton about;
    JButton logout;
    
    AdminPage(){
        logo = new JPanel();
        logo.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 2));
        logo.setBackground(Color.LIGHT_GRAY);
        logo.setBounds(0,0, 200, 600);

        title = new JPanel();
        title.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 2));
        title.setBackground(Color.LIGHT_GRAY);
        title.setBounds(200, 0,600,150);
        
        body = new JPanel();
        body.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 2));
        body.setBackground(Color.LIGHT_GRAY);
        body.setBounds(200,150,600,450);
        frame();
    }
    
    public void frame(){
        add(logo);
        add(title);
        add(body);

        setUndecorated(true);
        setSize(800,600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(179, 173, 173));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }
}
