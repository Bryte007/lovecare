package lovecare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.plaf.ColorUIResource;
import lovecare.DatabaseConnection;
import lovecare.MainFrame;

public class USER extends JFrame implements ActionListener {

    private JTextField NAME2, USERNAME1, PASSWORD1;
    private JComboBox<String> USERTYPE11;
    private Connection con;
    private BufferedImage backgroundImage;

    public USER() {
        con = DatabaseConnection.getConnection(); 

        // Frame
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setBackground(Color.gray);
        setVisible(true);

        try {
            backgroundImage =
                    ImageIO.read(new File("C:\\Users\\Humble-Lyon-PC\\Desktop\\Hospital Management\\LoveCare\\LOVECARE\\src\\lovecare\\11.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(null);
        panel.setBounds(50, 30, 600, 650);
        //panel.setBackground(new ColorUIResource(0, 75, 73));
        Font font1 = new Font("ARIAL ROUNDED MT BOLD", Font.BOLD, 30);
        Font font = new Font("ARIAL", Font.BOLD, 15);
        add(panel);

        JLabel Title = new JLabel("USER CREATION");
        Title.setBounds(190, -50, 375, 200);
        Title.setFont(font1);
        Title.setForeground(new ColorUIResource(0,105,148));
        panel.add(Title);

        // NAME label
        JLabel NAME = new JLabel("NAME");
        NAME.setBounds(80, 100, 250, 25);
        NAME.setForeground(Color.BLACK);
        NAME.setFont(font);
        panel.add(NAME);

        // username text field
        NAME2 = new JTextField();
        NAME2.setBounds(200, 100, 250, 25);
        NAME2.setBackground(Color.WHITE);
        NAME2.setFont(font);
        panel.add(NAME2);

        // USERTYPE LABEL
        JLabel USERNAME = new JLabel("USERNAME");
        USERNAME.setBounds(80, 150, 250, 25);
        USERNAME.setForeground(Color.BLACK);
        USERNAME.setFont(font);
        panel.add(USERNAME);

        // username textfield
        USERNAME1 = new JTextField();
        USERNAME1.setBounds(200, 150, 250, 25);
        USERNAME1.setBackground(Color.WHITE);
        USERNAME1.setFont(font);
        panel.add(USERNAME1);

        // PASSWORD LABEL
        JLabel PASSWORD = new JLabel("PASSWORD");
        PASSWORD.setBounds(80, 200, 250, 25);
        PASSWORD.setForeground(Color.BLACK);
        PASSWORD.setFont(font);
        panel.add(PASSWORD);

        // PASSWORD textfield
        PASSWORD1 = new JPasswordField();
        PASSWORD1.setBounds(200, 200, 250, 25);
        PASSWORD1.setBackground(Color.WHITE);
        PASSWORD1.setFont(font);
        panel.add(PASSWORD1);

        // USERTYPE LABEL
        JLabel USERTYPE = new JLabel("USERTYPE");
        USERTYPE.setBounds(80, 250, 250, 25);
        USERTYPE.setForeground(Color.black);
        USERTYPE.setFont(font);
        panel.add(USERTYPE);

        // USERTYPE TYPE FIELD
        String[] USERTYPE3 = { "Receptionist", "Doctor", "Pharmacist" };
        USERTYPE11 = new JComboBox<>(USERTYPE3);
        USERTYPE11.setBounds(200, 250, 250, 25);
       // USERTYPE11.setForeground(Color.white);
        USERTYPE11.setBackground(Color.white);
        panel.add(USERTYPE11);

        // SUBMIT BUTTONS
        // Submit Button
        JButton add = new JButton("ADD");
        add.setBounds(200, 300, 100, 50);
        add.setFont(font);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        panel.add(add);

        add.addActionListener(this);

        // Cancel button
        JButton cancel = new JButton("CANCEL");
        cancel.setBounds(320, 300, 100, 50);
        cancel.setFont(font);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        panel.add(cancel);
        cancel.addActionListener(this);

        // Add the panel to the frame
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ADD")) {
            String name = NAME2.getText();
            String username = USERNAME1.getText();
            String password = PASSWORD1.getText();
            String usertype = USERTYPE11.getSelectedItem().toString();

            try {
                PreparedStatement pst = con.prepareStatement("INSERT INTO user (NAME,USERNAME,PASSWORD,USERTYPE) VALUES (?,?,?,?)");
                pst.setString(1, name);
                pst.setString(2, username);
                pst.setString(3, password);
                pst.setString(4, usertype);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "User Inserted");
                NAME2.setText("");
                USERNAME1.setText("");
                PASSWORD1.setText("");
                USERTYPE11.setSelectedIndex(-1);
                NAME2.requestFocus();

            } catch (SQLException ex) {
                Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getActionCommand().equals("CANCEL")) {
            setVisible(false);
            MainFrame main = new MainFrame();
            dispose();
            main.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new USER();
    }
}
