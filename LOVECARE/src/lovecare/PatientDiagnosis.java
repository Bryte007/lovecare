package lovecare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientDiagnosis extends JFrame implements ActionListener {

    private JComboBox<String> patientNameComboBox; // Use JComboBox for patient names
    private JTextArea diagnosisTextArea;
    private JTextField drugTextField;
    private JTextField amountTextField; // New field for amount
    private JButton addDiagnosisButton;
    private JButton addDrugButton;
    private JButton cancelButton;
    private Connection con;

    public PatientDiagnosis() {
        // Initialize the database connection (You can use your existing connection method)
        con = DatabaseConnection.getConnection();

        setTitle("Patient Diagnosis & Treatment");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("C:\\Users\\Humble-Lyon-PC\\Desktop\\Hospital Management\\LoveCare\\LOVECARE\\src\\lovecare\\6.jpg"); // Replace with your image file path
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JLabel patientNameLabel = new JLabel("Patient Name:");
        patientNameLabel.setBounds(50, 20, 100, 25);
        patientNameLabel.setForeground(Color.white);
        panel.add(patientNameLabel);
        // Create a JComboBox to select patient names
        patientNameComboBox = new JComboBox<>(getPatientNames());
        patientNameComboBox.setBounds(160, 20, 200, 25);
        panel.add(patientNameComboBox);

        JLabel diagnosisLabel = new JLabel("Diagnosis:");
        diagnosisLabel.setBounds(50, 60, 100, 25);
        diagnosisLabel.setForeground(Color.white);
        panel.add(diagnosisLabel);

        diagnosisTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(diagnosisTextArea);
        scrollPane.setBounds(160, 60, 200, 100);
        panel.add(scrollPane);

        JLabel drugLabel = new JLabel("Add Drug:");
        drugLabel.setBounds(50, 180, 100, 25);
        drugLabel.setForeground(Color.white);
        panel.add(drugLabel);

        drugTextField = new JTextField();
        drugTextField.setBounds(160, 180, 200, 25);
        panel.add(drugTextField);

        JLabel amountLabel = new JLabel("Amount:"); // New label for amount
        amountLabel.setForeground(Color.white);
        amountLabel.setBounds(50, 220, 100, 25);
        panel.add(amountLabel);

        amountTextField = new JTextField(); // New text field for amount
        amountTextField.setBounds(160, 220, 200, 25);
        panel.add(amountTextField);

        addDiagnosisButton = new JButton("Add Diagnosis");
        addDiagnosisButton.setBounds(50, 260, 150, 30);
        addDiagnosisButton.addActionListener(this);
        panel.add(addDiagnosisButton);

        addDrugButton = new JButton("Add Drug");
        addDrugButton.setBounds(210, 260, 150, 30);
        addDrugButton.addActionListener(this);
        panel.add(addDrugButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(130, 310, 120, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DOCTOR();
            }
        });
        panel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
    }

    // Method to fetch patient names from the database
    private String[] getPatientNames() {
        List<String> names = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT NAME FROM patient");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("NAME"));
            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(PatientDiagnosis.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error Fetching Patient Names: " + ex.getMessage());
        }
        return names.toArray(new String[0]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDiagnosisButton) {
            String patientName = patientNameComboBox.getSelectedItem().toString();
            String diagnosis = diagnosisTextArea.getText();

            try {
                // Insert diagnosis into the database
                PreparedStatement pst = con.prepareStatement("INSERT INTO patient_diagnosis (PATIENT_NAME, DIAGNOSIS) VALUES (?, ?)");
                pst.setString(1, patientName);
                pst.setString(2, diagnosis);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Diagnosis Added Successfully!");

                // Clear the diagnosis field
                diagnosisTextArea.setText("");

            } catch (SQLException ex) {
                Logger.getLogger(PatientDiagnosis.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error Adding Diagnosis: " + ex.getMessage());
            }
        } else if (e.getSource() == addDrugButton) {
            String patientName = patientNameComboBox.getSelectedItem().toString();
            String drug = drugTextField.getText();
            String amount = amountTextField.getText(); // New field for amount

            try {
                // Insert drug and amount into the database
                PreparedStatement pst = con.prepareStatement("INSERT INTO patient_treatment (PATIENT_NAME, DRUG, AMOUNT) VALUES (?, ?, ?)");
                pst.setString(1, patientName);
                pst.setString(2, drug);
                pst.setString(3, amount); // Set the amount
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Drug Added for Treatment!");

                // Clear the drug and amount fields
                drugTextField.setText("");
                amountTextField.setText("");

            } catch (SQLException ex) {
                Logger.getLogger(PatientDiagnosis.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error Adding Drug: " + ex.getMessage());
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new PatientDiagnosis().setVisible(true);
    }
}
