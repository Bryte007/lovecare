package lovecare;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LOGIN {

    private Connection con;
    private PreparedStatement pst;
    JComboBox<String> USERTYPE11;
    JTextField USERNAME1;
    JPasswordField PASSWORD1;
    ResultSet rs;
    JPanel backgroundPanel;

    public LOGIN() {
        Connect();
        createLoginForm();
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/lovecare", "root", "");
            } catch (SQLException ex) {
                Logger.getLogger(LOGIN.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LOGIN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createLoginForm() {
        final JFrame f = new JFrame("LOVE CARE");
        f.setSize(500, 600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setResizable(false);

        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon background
                        = new ImageIcon("C:\\Users\\Humble-Lyon-PC\\Desktop\\Hospital Management\\LoveCare\\LOVECARE\\src\\lovecare\\14.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(null);
        f.add(backgroundPanel);

        Font font1 = new Font("ARIAL ROUNDED MT BOLD", Font.BOLD, 25);
        Font font = new Font("ARIAL", Font.BOLD, 16);

        //

    // Create and configure the JLabel
        final JLabel loveCareLabel = new JLabel("LOVE CARE");
        loveCareLabel.setFont(new Font("ARIAL ROUNDED MT BOLD", Font.BOLD, 35));
        loveCareLabel.setForeground(new Color(0, 75, 73));
        final int y = 150; // Y position
        loveCareLabel.setBounds(0, y, 250, 50);
        backgroundPanel.add(loveCareLabel);

        // Create a Timer for the animation
        Timer timer = new Timer(10, new ActionListener() {
            int x = 0;
            boolean moveRight = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (moveRight) {
                    x += 2; // Move right
                    loveCareLabel.setBounds(x, y, 250, 50);
                    if (x >= f.getWidth()) {
                        moveRight = false;
                    }
                } else {
                    x -= 2; // Move left
                    loveCareLabel.setBounds(x, y, 250, 50);
                    if (x <= -loveCareLabel.getWidth()) {
                        moveRight = true;
                    }
                }
            }
        });

        timer.start(); // Start the animation


        JLabel USERNAME = new JLabel("USERNAME");
        USERNAME.setBounds(60, 240, 100, 15); // Adjusted position
        USERNAME.setFont(font);
        USERNAME.setForeground(Color.black);
        backgroundPanel.add(USERNAME);

        USERNAME1 = new JTextField();
        USERNAME1.setBounds(180, 230, 250, 25); // Adjusted position
        backgroundPanel.add(USERNAME1);

        JLabel PASSWORD = new JLabel("PASSWORD");
        PASSWORD.setBounds(60, 280, 100, 15); // Adjusted position
        PASSWORD.setFont(font);
        PASSWORD.setForeground(Color.black);
        backgroundPanel.add(PASSWORD);

        PASSWORD1 = new JPasswordField();
        PASSWORD1.setBounds(180, 270, 250, 25); // Adjusted position
        backgroundPanel.add(PASSWORD1);

        JLabel USERTYPE = new JLabel("USERTYPE");
        USERTYPE.setBounds(60, 320, 100, 15);
        USERTYPE.setFont(font);
        USERTYPE.setForeground(Color.black);
        backgroundPanel.add(USERTYPE);

        String[] USERTYPE1 = {"Receptionist", "Doctor"};
        USERTYPE11 = new JComboBox<>(USERTYPE1);
        USERTYPE11.setBounds(180, 310, 250, 25);
        backgroundPanel.add(USERTYPE11);

        JButton sub = new JButton("LOGIN");
        sub.setBounds(180, 360, 100, 50);
        sub.setFont(font);
        sub.setBackground(Color.BLACK);
        sub.setForeground(Color.WHITE);
        backgroundPanel.add(sub);

        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = USERNAME1.getText();
                String password = PASSWORD1.getText();
                String usertype = USERTYPE11.getSelectedItem().toString();

                try {
                    pst = (PreparedStatement) con.prepareStatement("SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ? AND USERTYPE = ?");
                    pst.setString(1, username);
                    pst.setString(2, password);
                    pst.setString(3, usertype);

                    rs = pst.executeQuery();
                    if (rs.next()) {
                        int userid = rs.getInt("ID");
                        f.setVisible(false);
                        if (usertype.equals("Doctor")) {
                          //  JOptionPane.showMessageDialog(null, "Login successfully");
                            new DOCTOR();

                        } else {
                            //JOptionPane.showMessageDialog(null, "Login successfully");
                            new MainFrame().setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username or password do not match, login unsuccessful");
                        USERNAME1.setText("");
                        PASSWORD1.setText("");
                        USERTYPE11.setSelectedIndex(-1);
                        USERNAME1.requestFocus();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LOGIN.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        f.setVisible(true);
    }

    public static void main(String[] args) {
        new LOGIN();
    }
}
