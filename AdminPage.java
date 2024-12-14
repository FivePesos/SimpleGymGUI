import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class AdminPage extends JFrame implements ActionListener {

    JLabel logoLabel;
    JPanel buttonContainer;
    SideButtons members;
    SideButtons activeMembers;
    SideButtons logOut;
    SideButtons lastClickedButton;

    public JPanel mainPanel; // Panel with CardLayout
    public CardLayout cardLayout;

    AdminPage() {

        ImageIcon imageLogo = new ImageIcon("Resources\\img\\Logo Gym.jpg");
        Image image = imageLogo.getImage();
        Image resizedImage = image.getScaledInstance(174, 88, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        logoLabel = new JLabel(resizedImageIcon, JLabel.CENTER);
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        members = new SideButtons("MEMBERS");
        activeMembers = new SideButtons("ACTIVE MEMBERS");
        logOut = new SideButtons("LOGOUT");

        buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(4, 1));
        buttonContainer.setBackground(new Color(255, 255, 255));
        buttonContainer.setPreferredSize(new Dimension(200, 500));

        buttonContainer.add(logoLabel);
        buttonContainer.add(members);
        buttonContainer.add(activeMembers);
        buttonContainer.add(logOut);

        members.addActionListener(this);
        activeMembers.addActionListener(this);
        logOut.addActionListener(this);

        // Main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel memberDetailsPanel = new JPanel();
        mainPanel.add(memberDetailsPanel, "MEMBER DETAILS");
        mainPanel.add(createMainPanel(), "WELCOME");
        mainPanel.add(createMembersPanel(), "MEMBERS");
        mainPanel.add(createActiveMembersPanel(), "ACTIVE MEMBERS");
        cardLayout.show(mainPanel, "WELCOME");
    

        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "WELCOME");
                if (lastClickedButton != null) {
                    lastClickedButton.setDefaultColors();
                }
            }
        });

        frame();
    }

    public void frame() {
        setUndecorated(true);
        setSize(785, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 255, 255));

        add(buttonContainer, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createMembersPanel() {
        Font semiBold = loadFont("Resources\\Montserrat\\static\\Montserrat-SemiBold.ttf",48);
        JPanel membersPanel = new JPanel(new BorderLayout());
        membersPanel.setBackground(Color.WHITE);
    
        JLabel title = new JLabel("MEMBERS", JLabel.CENTER);
        title.setFont(semiBold);
        membersPanel.add(title, BorderLayout.NORTH);
    
        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 10, 10)); 
        contentPanel.setBackground(Color.WHITE);
    
       
        try {
            
            BufferedReader nameReader = new BufferedReader(new FileReader("fullname.txt"));
            BufferedReader balanceReader = new BufferedReader(new FileReader("balance.txt"));
    
            String name;
            String balance;
            while ((name = nameReader.readLine()) != null && (balance = balanceReader.readLine()) != null) {
                contentPanel.add(createMemberRow(name, balance));
            }
    
            nameReader.close();
            balanceReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    
        membersPanel.add(scrollPane, BorderLayout.CENTER);
        return membersPanel;
    }
    
    

    private JPanel createActiveMembersPanel() {
        Font semiBoldTitle = loadFont("Resources\\Montserrat\\static\\Montserrat-SemiBold.ttf", 48);
        JPanel activeMembersPanel = new JPanel(new BorderLayout());
        activeMembersPanel.setBackground(Color.WHITE);
    
        JLabel title = new JLabel("ACTIVE MEMBERS", JLabel.CENTER);
        title.setFont(semiBoldTitle);
        activeMembersPanel.add(title, BorderLayout.NORTH);
    
        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        contentPanel.setBackground(Color.WHITE);
    
        try (
            BufferedReader nameReader = new BufferedReader(new FileReader("fullname.txt"));
            BufferedReader activeReader = new BufferedReader(new FileReader("active.txt"))
        ) {
            String nameLine;
            String activeStatus;
            int index = 0; // Track the index for active.txt
    
            while ((nameLine = nameReader.readLine()) != null &&
                   (activeStatus = activeReader.readLine()) != null) {
                
                if (activeStatus.trim().equalsIgnoreCase("true")) {
                    contentPanel.add(createActiveMemberRow(nameLine, index));
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    
        activeMembersPanel.add(scrollPane, BorderLayout.CENTER);
        return activeMembersPanel;
    }
    
    private JPanel createActiveMemberRow(String name, int index) {
        Font semiBold = loadFont("Resources\\Montserrat\\static\\Montserrat-SemiBold.ttf", 20);
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(semiBold);
    
        JButton removeButton = new JButton("REMOVE");
        removeButton.setFont(semiBold);
        removeButton.setForeground(Color.RED);
        removeButton.setBackground(Color.WHITE);
        removeButton.setFocusPainted(false);
    
        // Add ActionListener to the button
        removeButton.addActionListener(e -> {
            try {
                updateActiveStatus(index, "false"); // Update the active status in the file
                refreshActiveMembersPanel(); // Refresh the panel after removing
                JOptionPane.showMessageDialog(this, "Member removed from active list.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to update active status!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        row.add(nameLabel, BorderLayout.WEST);
        row.add(removeButton, BorderLayout.EAST);
    
        return row;
    }

    private void refreshActiveMembersPanel() {
        JPanel activeMembersPanel = createActiveMembersPanel();
        mainPanel.add(activeMembersPanel, "ACTIVE MEMBERS");
        cardLayout.show(mainPanel, "ACTIVE MEMBERS");
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void updateActiveStatus(int index, String status) throws IOException {
        List<String> activeStatuses = new ArrayList<>();
    
        // Read the active statuses from the file
        try (BufferedReader activeReader = new BufferedReader(new FileReader("active.txt"))) {
            String line;
            while ((line = activeReader.readLine()) != null) {
                activeStatuses.add(line);
            }
        }
    
        // Update the status at the specified index
        if (index >= 0 && index < activeStatuses.size()) {
            activeStatuses.set(index, status);
        }
    
        // Write the updated statuses back to the file
        try (BufferedWriter activeWriter = new BufferedWriter(new FileWriter("active.txt"))) {
            for (String activeStatus : activeStatuses) {
                activeWriter.write(activeStatus);
                activeWriter.newLine();
            }
        }
    }
    
    
    private JPanel createMemberRow(String name, String balance) {
        Font semiBold = loadFont("Resources\\Montserrat\\static\\Montserrat-SemiBold.ttf",20);
        
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(semiBold);

        RoundedBalanceButton balanceLabel = new RoundedBalanceButton(balance);
        balanceLabel.setFont(semiBold);
        balanceLabel.setForeground(Color.WHITE);

        balanceLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Show the details panel when a member is clicked
                showMemberDetailsPanel(name, balance);
            }
        });

        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMemberDetailsPanel(name, balance);
            }
        });
        row.add(nameLabel, BorderLayout.WEST);
        row.add(balanceLabel, BorderLayout.EAST);

        return row;
    }

    private void showMemberDetailsPanel(String name, String balance) {
        System.out.println("showMemberDetailsPanel called for: " + name + " with balance " + balance);
    
        JPanel memberDetailsPanel = new JPanel(new BorderLayout());
        memberDetailsPanel.setBackground(Color.WHITE);
    
        JLabel title = new JLabel("Member: " + name, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        memberDetailsPanel.add(title, BorderLayout.NORTH);
    
        JPanel balancePanel = new JPanel(new FlowLayout());
        balancePanel.setBackground(Color.WHITE);
    
        JLabel balanceLabel = new JLabel("Current Balance: ");
        JTextField balanceField = new JTextField(balance);
        balanceField.setPreferredSize(new Dimension(100, 30));
        balancePanel.add(balanceLabel);
        balancePanel.add(balanceField);
    
        JButton setBalanceButton = new JButton("SET BALANCE");
        setBalanceButton.setBackground(Color.BLACK);
        setBalanceButton.setForeground(Color.WHITE);
        setBalanceButton.setFocusPainted(false);
    
        setBalanceButton.addActionListener(e -> {
            String newBalance = balanceField.getText();
            System.out.println("Updated balance for " + name + ": " + newBalance);
        
            try {
                updateBalanceFile(name, newBalance);
                JOptionPane.showMessageDialog(this, "Balance updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
              
                refreshMembersPanel();
        
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to update balance!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
    
        JButton removeButton = new JButton("REMOVE MEMBER");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
    
        removeButton.addActionListener(e -> {
            try{
                removeMemberFromFiles(name);
                refreshMembersPanel();
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        
        
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.add(setBalanceButton);
        buttonsPanel.add(removeButton);
    
        memberDetailsPanel.add(balancePanel, BorderLayout.CENTER);
        memberDetailsPanel.add(buttonsPanel, BorderLayout.SOUTH);
    
        mainPanel.add(memberDetailsPanel, "MEMBER DETAILS");
        cardLayout.show(mainPanel, "MEMBER DETAILS");
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void removeMemberFromFiles(String memberName) throws IOException {
        
        File nameFile = new File("fullname.txt");
        File balanceFile = new File("balance.txt");
        File usernameFile = new File("username.txt");
        File passwordFile = new File("password.txt");
        File activeFile = new File("active.txt");
    
        
        List<String> updatedNames = new ArrayList<>();
        List<String> updatedBalances = new ArrayList<>();
        List<String> updatedUsernames = new ArrayList<>();
        List<String> updatedPasswords = new ArrayList<>();
        List<String> updatedActiveStatuses = new ArrayList<>();
    
        // Reading all the files and removing the member with the given name
        try (BufferedReader nameReader = new BufferedReader(new FileReader(nameFile));
             BufferedReader balanceReader = new BufferedReader(new FileReader(balanceFile));
             BufferedReader usernameReader = new BufferedReader(new FileReader(usernameFile));
             BufferedReader passwordReader = new BufferedReader(new FileReader(passwordFile));
             BufferedReader activeReader = new BufferedReader(new FileReader(activeFile))) {
    
            String nameLine, balanceLine, usernameLine, passwordLine, activeLine;
    
            while ((nameLine = nameReader.readLine()) != null &&
                   (balanceLine = balanceReader.readLine()) != null &&
                   (usernameLine = usernameReader.readLine()) != null &&
                   (passwordLine = passwordReader.readLine()) != null &&
                   (activeLine = activeReader.readLine()) != null) {
    
                if (!nameLine.equals(memberName)) {  
                    updatedNames.add(nameLine);
                    updatedBalances.add(balanceLine);
                    updatedUsernames.add(usernameLine);
                    updatedPasswords.add(passwordLine);
                    updatedActiveStatuses.add(activeLine);
                }
            }
        }
    
        try (BufferedWriter nameWriter = new BufferedWriter(new FileWriter(nameFile));
             BufferedWriter balanceWriter = new BufferedWriter(new FileWriter(balanceFile));
             BufferedWriter usernameWriter = new BufferedWriter(new FileWriter(usernameFile));
             BufferedWriter passwordWriter = new BufferedWriter(new FileWriter(passwordFile));
             BufferedWriter activeWriter = new BufferedWriter(new FileWriter(activeFile))) {
    
            
            for (String updatedName : updatedNames) {
                nameWriter.write(updatedName);
                nameWriter.newLine();
            }
    
            for (String updatedBalance : updatedBalances) {
                balanceWriter.write(updatedBalance);
                balanceWriter.newLine();
            }
    
            for (String updatedUsername : updatedUsernames) {
                usernameWriter.write(updatedUsername);
                usernameWriter.newLine();
            }
    
            for (String updatedPassword : updatedPasswords) {
                passwordWriter.write(updatedPassword);
                passwordWriter.newLine();
            }
    
            for (String updatedActiveStatus : updatedActiveStatuses) {
                activeWriter.write(updatedActiveStatus);
                activeWriter.newLine();
            }
        }
    }
     
    
    private void updateBalanceFile(String memberName, String newBalance) throws IOException {
        List<String> updatedBalances = new ArrayList<>();
        List<String> updatedNames = new ArrayList<>();
    
        try (BufferedReader balanceReader = new BufferedReader(new FileReader("balance.txt"));
             BufferedReader nameReader = new BufferedReader(new FileReader("fullname.txt"))) {
    
            String balanceLine, nameLine;
            while ((balanceLine = balanceReader.readLine()) != null &&
                   (nameLine = nameReader.readLine()) != null) {
    
            
                if (nameLine.trim().isEmpty() || balanceLine.trim().isEmpty()) {
                    continue;  
                }
    
                if (nameLine.equals(memberName)) {
                    updatedBalances.add(newBalance);
                } else {
                    updatedBalances.add(balanceLine); 
                }
                updatedNames.add(nameLine);
            }
        }
    
        try (BufferedWriter balanceWriter = new BufferedWriter(new FileWriter("balance.txt"));
             BufferedWriter nameWriter = new BufferedWriter(new FileWriter("fullname.txt"))) {
    
            for (String updatedName : updatedNames) {
                nameWriter.write(updatedName);
                nameWriter.newLine();
            }
    
            for (String updatedBalance : updatedBalances) {
                balanceWriter.write(updatedBalance);
                balanceWriter.newLine();
            }
        }
    }
    
    

    private void refreshMembersPanel() {
        
        JPanel membersPanel = createMembersPanel();
        
        
        mainPanel.removeAll();
        mainPanel.add(membersPanel, "MEMBERS");
        
        
        cardLayout.show(mainPanel, "MEMBERS");
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    
    private JPanel createMainPanel() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.WHITE);

        JLabel adminPage = new JLabel("ADMIN PAGE", JLabel.CENTER);
        JLabel welcomeLabel = new JLabel("WELCOME ADMIN", JLabel.CENTER);

        Font montserratAdminPage = loadFont("Resources\\Montserrat\\static\\Montserrat-SemiBold.ttf", 48);
        Font montserratWelcomeLabel = loadFont("Resources\\Montserrat\\static\\Montserrat-SemiBold.ttf", 20);

        adminPage.setFont(montserratAdminPage != null ? montserratAdminPage : new Font("Arial", Font.BOLD, 48));
        welcomeLabel.setFont(montserratWelcomeLabel != null ? montserratWelcomeLabel : new Font("Arial", Font.BOLD, 20));

        welcomePanel.add(adminPage, BorderLayout.NORTH);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        return welcomePanel;
    }

    private static Font loadFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            return font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            System.out.println("Error loading font: " + path);
            return null; 
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SideButtons clickedButton = (SideButtons) e.getSource();

        if (lastClickedButton != null) {
            lastClickedButton.setDefaultColors();
        }

        clickedButton.setSelectedColors();
        lastClickedButton = clickedButton;

        if (e.getSource() == members) {
            cardLayout.show(mainPanel, "MEMBERS");
            
        } else if (e.getSource() == activeMembers) {
            cardLayout.show(mainPanel, "ACTIVE MEMBERS");
            
        } else if (e.getSource() == logOut) {
            dispose();
            new GymLogPage(); 
        }
    }
}

class SideButtons extends JButton {
    private Font montserratFont;

    public SideButtons(String text) {
        super(text);
        setBorder(new LineBorder(Color.BLACK));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setFocusPainted(false);
        setOpaque(true); 
        try {
            montserratFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Montserrat\\static\\Montserrat-SemiBold.ttf"));
            montserratFont = montserratFont.deriveFont(Font.PLAIN, 16); 
            setFont(montserratFont); 
        } catch (FontFormatException | IOException e) {
            System.out.println("Error: Could not load Montserrat font.");
            setFont(new Font("Arial", Font.PLAIN, 16)); 
        }
    }

    public void setDefaultColors() {
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
    }

    public void setSelectedColors() {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
    }
}

class RoundedBalanceButton extends JButton{
    private int cornerRadius = 50; 

    public RoundedBalanceButton(String text) {
        super(text);
        setOpaque(false); 
        setFocusPainted(false); 
        setBorderPainted(false); 
        setBackground(Color.BLACK); 
        setForeground(Color.WHITE); 
        setPreferredSize(new Dimension(136, 30));  
        setMinimumSize(new Dimension(136, 10));
        setMaximumSize(new Dimension(136, 10));  
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(Color.GRAY); 
        } else {
            g.setColor(Color.BLACK); 
        }

        g.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g);
    }

    
}

