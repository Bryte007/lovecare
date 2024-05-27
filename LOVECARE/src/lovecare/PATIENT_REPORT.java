package lovecare;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lovecare.DatabaseConnection;

public class PATIENT_REPORT extends JFrame implements ActionListener {

    JTextArea reportTextArea;
    JButton generateButton;
    JButton printButton;
    JButton exitButton;
    Connection con;

    JLabel USERTYPE2, USERNAME2;

    public PATIENT_REPORT(Connection con) {
        this.con = con;

        setTitle("Patient Report");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    // Font size
    Font font1 = new Font("ARIAL ROUNDED MT BOLD", Font.BOLD, 30);
    Font font = new Font("ARIAL", Font.BOLD, 15);

    private void initComponents() {
        reportTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        add(scrollPane, BorderLayout.CENTER);
        reportTextArea.setFont(font);

        // Create a custom JPanel for the button panel
        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Load the background image
                    Image backgroundImage = 
                 ImageIO.read(new File("C:\\Users\\Humble-Lyon-PC\\Desktop\\Hospital Management\\LoveCare\\LOVECARE\\src\\lovecare\\10.jpg"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        generateButton = new JButton("Generate Report");
        generateButton.setFont(font);
        generateButton.addActionListener(this);

        printButton = new JButton("Print");
        printButton.setFont(font);
        printButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.setFont(font);
        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DOCTOR();
            }
        });
       
        
        buttonPanel.add(generateButton);
        buttonPanel.add(printButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
 private void generateReport() {
    try {
        PreparedStatement pst = con.prepareStatement("SELECT p.NAME, p.PHONE, p.ADDRESS, d.DIAGNOSIS, t.DRUG " +
                "FROM patient p " +
                "INNER JOIN patient_diagnosis d ON p.NAME = d.PATIENT_NAME " +
                "INNER JOIN patient_treatment t ON p.NAME = t.PATIENT_NAME");
        ResultSet rs = pst.executeQuery();

        reportTextArea.setText("PATIENT REPORT:\n");
        reportTextArea.append("----------------------------\n");

        while (rs.next()) {
            String name = rs.getString("NAME");
            String phone = rs.getString("PHONE");
            String address = rs.getString("ADDRESS");
            String diagnosis = rs.getString("DIAGNOSIS");
            String drug = rs.getString("DRUG"); 

            reportTextArea.append("Name: " + name + "\n");
            reportTextArea.append("Phone: " + phone + "\n");
            reportTextArea.append("Address: " + address + "\n");
            reportTextArea.append("Diagnosis: " + diagnosis + "\n");
            reportTextArea.append("Medication: " + drug + "\n"); 
            reportTextArea.append("----------------------------\n");
        }

        rs.close();
        pst.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    private void printReport() {
        try {
            reportTextArea.print();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            generateReport();
        } else if (e.getSource() == printButton) {
            printReport();
        } else if (e.getSource() == exitButton) {
            // Replace this part with your desired exit action
            // DOCTOR d = new DOCTOR();
            // dispose();
            // d.setVisible(true);
            System.exit(0); //
        }
    }

    public static void main(String[] args) {
        Connection con = DatabaseConnection.getConnection();

        PATIENT_REPORT reportFrame = new PATIENT_REPORT(con);
        reportFrame.setVisible(true);
    }
}
