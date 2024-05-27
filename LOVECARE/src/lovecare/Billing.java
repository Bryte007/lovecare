package lovecare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Billing extends JFrame implements ActionListener, Printable {

    private JTextField patientNameTextField;
    private JTextField drugTextField;
    private JTextField amountTextField;
    private JButton generateButton;
    JButton cancelButton; // Added "Cancel" button
    private JButton printButton ,Return;
    private Connection con;
    private JTable billingTable;
    private DefaultTableModel tableModel;

    public Billing() {
        // Initialize the database connection (You can use your existing connection method)
        con = DatabaseConnection.getConnection();

        setTitle("Patient Billing");
        setSize(1000, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        initComponents();
        createTable();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Handle window close event to ensure the database connection is closed
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

     private void initComponents() {
        setLayout(new BorderLayout());

        // Create a JPanel with a background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Load and draw the background image
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Humble-Lyon-PC\\Desktop\\Hospital Management\\LoveCare\\LOVECARE\\src\\lovecare\\15.jpg"); // Replace with your image file path
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

      

        JLabel patientNameLabel = new JLabel("Patient Name:");
        panel.add(patientNameLabel);

        patientNameTextField = new JTextField(15);
        panel.add(patientNameTextField);

        JLabel drugLabel = new JLabel("Drug:");
        panel.add(drugLabel);

        drugTextField = new JTextField(15);
        panel.add(drugTextField);

        JLabel amountLabel = new JLabel("Amount:");
        panel.add(amountLabel);

        amountTextField = new JTextField(10);
        panel.add(amountTextField);

//        generateButton = new JButton("Generate");
//        generateButton.addActionListener(this);
//        panel.add(generateButton);

        cancelButton = new JButton("Cancel"); 
        cancelButton.addActionListener(this); 
        panel.add(cancelButton); 

        printButton = new JButton("Print Bill");
        printButton.addActionListener(this);
        panel.add(printButton);

     Return = new JButton("Return");
     panel.add(Return);

       Return.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              dispose();
                MainFrame mainFrame = new MainFrame();
                
                mainFrame.setVisible(true);
                
            }
        });
    
        
        add(panel, BorderLayout.SOUTH);
    }

    private void createTable() {
        // Create the table model and set column names
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Billing ID", "Patient Name", "Medication", "Amount(GH₡)"});

        billingTable = new JTable(tableModel);
        billingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow selecting one row at a time

        // Add a selection listener to the table
        billingTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = billingTable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected
                    // Get the values from the selected row and display in the text fields
                    String patientName = tableModel.getValueAt(selectedRow, 1).toString();
                    String drug = tableModel.getValueAt(selectedRow, 2).toString();
                    String amount = tableModel.getValueAt(selectedRow, 3).toString();
                    patientNameTextField.setText(patientName);
                    drugTextField.setText(drug);
                    amountTextField.setText(amount);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(billingTable);
        scrollPane.setPreferredSize(new Dimension(550, 200));

        // Add the table to the right side of the frame
        add(scrollPane, BorderLayout.CENTER);

        // Fetch billing data from the database and populate the table
        fetchBillingData();
    }

    private void fetchBillingData() {
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM patient_treatment");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int billingID = rs.getInt("BILLING_ID");
                String patientName = rs.getString("PATIENT_NAME");
                String drug = rs.getString("DRUG");
                double amount = rs.getDouble("AMOUNT");

                tableModel.addRow(new Object[]{billingID, patientName, drug, amount});
            }

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Billing.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error Fetching Billing Data: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            // Modified behavior: Do not insert a new row when the "Generate" button is clicked
            // Instead, you can print the selected row
            int selectedRow = billingTable.getSelectedRow();

            if (selectedRow != -1) { // If a row is selected
                String patientName = tableModel.getValueAt(selectedRow, 1).toString();
                String drug = tableModel.getValueAt(selectedRow, 2).toString();
                String amount = tableModel.getValueAt(selectedRow, 3).toString();

                // Implement your printing logic here
                printBill(patientName, drug, amount);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to generate the bill.");
            }
        } else if (e.getSource() == cancelButton) {
            // Implement your cancel logic here (e.g., clearing fields)
            clearFields();
        } else if (e.getSource() == printButton) {
            int selectedRow = billingTable.getSelectedRow();

            if (selectedRow != -1) { // If a row is selected
                String patientName = tableModel.getValueAt(selectedRow, 1).toString();
                String drug = tableModel.getValueAt(selectedRow, 2).toString();
                String amount = tableModel.getValueAt(selectedRow, 3).toString();

                // Implement your printing logic here
                printBill(patientName, drug, amount);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to generate the bill.");
            }
        }
    }

    private void clearFields() {
        // Implement your logic to clear fields here
        patientNameTextField.setText("");
        drugTextField.setText("");
        amountTextField.setText("");
    }

    private void printBill(String patientName, String drug, String amount) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, "Error printing: " + e.getMessage());
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Define the bill content to be printed
        String patientName = patientNameTextField.getText();
        String drug = drugTextField.getText();
        String amount = amountTextField.getText();
        String billContent = "Patient Name: " + patientName + "\nDrug: " + drug + "\nAmount: " + amount;

        // Print the bill content
        g2d.drawString(billContent, 100, 100);

        return PAGE_EXISTS;
    }

    public static void main(String[] args) {
        new Billing().setVisible(true);
    }
}
