import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.*;

public class AdminPage extends JFrame implements ActionListener {
    
    AdminPage(){
        frame();
    }
    
    public void frame(){
        setUndecorated(true);
        setSize(800,500);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(179, 173, 173));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }
}
