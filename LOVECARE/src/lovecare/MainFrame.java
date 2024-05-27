package lovecare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.plaf.ColorUIResource;

public class MainFrame extends JFrame implements ActionListener {

    private JPanel leftPanel, rightPanel, infoPanel;
    private final Connection connection;
    
    JButton billing;

    public MainFrame() {
        this.connection = DatabaseConnection.getConnection();
        initializeUI();
        
        
    }

    private void initializeUI() {
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        setupLeftPanel();
        setupRightPanel();
        setupInfoPanel();
    }

    private void setupLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 300, 700);
//
//        // Rest of the left panel setup code here...
//        ImageIcon backgroundImageIcon = new ImageIcon("path/to/background/image.jpg");
//        Image scaledImage = backgroundImageIcon.getImage().getScaledInstance(300, 600, Image.SCALE_FAST);
//        ImageIcon backgroundImage = new ImageIcon(scaledImage);
//
//        JLabel backgroundImageLabel = new JLabel(backgroundImage);
//        backgroundImageLabel.setBounds(0, 0, 300, 700);

//        leftPanel.add(backgroundImageLabel);
  add(leftPanel);

        JButton patientButton = createButton("Patient", 80, 60);
        patientButton.addActionListener(this);

        JButton doctorButton = createButton("Doctor", 80, 120);
        doctorButton.addActionListener(this);

        JButton userButton = createButton("Create User", 80, 180);
        userButton.addActionListener(this);
        
        JButton Billing = createButton("Billing", 80, 240);
        Billing.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Billing billing1 = new Billing();
                billing1.setVisible(true);
            }
        });

        JButton logoutButton = createButton("Logout", 80, 300);
        logoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LOGIN();
            }
        });

    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 50);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("ARIAL", Font.BOLD, 15));
        leftPanel.add(button);
        return button;
    }

    private void setupRightPanel() {
    // Right panel setup code...
    rightPanel = new JPanel();
    rightPanel.setLayout(null);
    rightPanel.setBounds(300, 0, 700, 700);
    rightPanel.setBackground(new ColorUIResource(0, 75, 73));
    add(rightPanel);

    Font titleFont = new Font("ARIAL ROUNDED MT BOLD", Font.BOLD, 40);

    final JLabel titleLabel = new JLabel("LOVE CARE");
    titleLabel.setBounds(50, 50, 375, 50);
    titleLabel.setFont(titleFont);
    titleLabel.setForeground(Color.WHITE);
    rightPanel.add(titleLabel);

    // Create a Timer for the animation
    Timer timer = new Timer(500, new ActionListener() {
        boolean isVisible = true;

        @Override
        public void actionPerformed(ActionEvent e) {
            titleLabel.setVisible(isVisible);
            isVisible = !isVisible;
        }
    });

    timer.start(); // Start the animation
}


  private void setupInfoPanel() {
    infoPanel = new JPanel();
    infoPanel.setLayout(null);
    infoPanel.setBounds(0, 150, 500, 300);
    infoPanel.setBackground(new ColorUIResource(0, 75, 73));
    rightPanel.add(infoPanel);

    // Load the background image
    ImageIcon backgroundImageIcon = 
     new ImageIcon("C:\\Users\\Humble-Lyon-PC\\Desktop\\Hospital Management\\LoveCare\\LOVECARE\\src\\lovecare\\bg1.jpg");
    Image scaledImage = backgroundImageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
    ImageIcon backgroundImage = new ImageIcon(scaledImage);

    // Create a JLabel to display the background image
    JLabel backgroundImageLabel = new JLabel(backgroundImage);
    backgroundImageLabel.setBounds(0, 0, 500, 300);

    // Make the JLabel non-opaque so that the panel background shows through
    backgroundImageLabel.setOpaque(false);

    // Add the JLabel to the info panel
    infoPanel.add(backgroundImageLabel);

    Font labelFont = new Font("ARIAL", Font.BOLD, 15);

    // Add other components or content on top of the background image here...
}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Patient")) {
            openPatientWindow();
        } else if (e.getActionCommand().equals("Doctor")) {
            openDoctorRegistrationWindow();
        } else if (e.getActionCommand().equals("Create User")) {
            openUserWindow();
        } else if (e.getActionCommand().equals("Logout")) {
            openLoginWindow();
        }
    }

    private void openPatientWindow() {
        PATIENT patient = new PATIENT(connection);
        dispose();
        patient.setVisible(true);
    }

    private void openDoctorRegistrationWindow() {
        DOCTOR_REGISTRATION doctorRegistration = new DOCTOR_REGISTRATION(connection);
        doctorRegistration.setVisible(true);
    }

    private void openUserWindow() {
        USER user = new USER();
        dispose();
        user.setVisible(true);
    }

    private void openLoginWindow() {
        LOGIN login = new LOGIN();
        dispose();
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
