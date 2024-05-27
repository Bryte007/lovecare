package lovecare;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DOCTOR_REGISTRATION extends JFrame {

    JLabel DOC_NUM, DOC_NUM_TEXT;

    JLabel title;
    JLabel DOC_NAME;
    JTextField DOC_NAME1;

    JLabel PHONE_NUMBER;
    JTextField PHONE_NUMBER1;

    JLabel SPECIALIZATION;
    JTextArea SPECIALIZATION1;

    JButton ADD, UPDATE, DELETE, EXIT;

    JPanel p;

    JTable table;
    DefaultTableModel tableModel;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    String maxid;

    public DOCTOR_REGISTRATION(final Connection con) {
        this.con = con;
        Connect();
       // AutoID();
        // patient_table();

        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

       

p = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Load and draw the background image here
        ImageIcon backgroundImageIcon = new ImageIcon("C:\\Users\\Humble-Lyon-PC\\Desktop\\Hospital Management\\LoveCare\\LOVECARE\\src\\lovecare\\13.jpg"); // Replace with your image file
        Image backgroundImage = backgroundImageIcon.getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
};
        p.setLayout(null);
        p.setBounds(0, 0, 1000, 700);
        add(p);

    

        title = new JLabel("DOCTOR'S REGISTRATION");
        title.setBounds(350, -100, 450, 250);
        Font font1 = new Font("ARIAL ROUNDED MT BOLD", Font.BOLD, 25);
        Font font = new Font("ARIAL", Font.BOLD, 15);
        title.setFont(font1);
        title.setForeground(Color.WHITE);
        p.add(title);

        DOC_NUM = new JLabel("DOCTOR_NO.");
        DOC_NUM.setBounds(70, 70, 250, 25);
        DOC_NUM.setBackground(Color.white);
        DOC_NUM.setForeground(Color.white);
        DOC_NUM.setFont(font);
        p.add(DOC_NUM);

        DOC_NUM_TEXT = new JLabel("DOCTOR_NO.TEXT");
        DOC_NUM_TEXT.setBounds(250, 70, 250, 25);
        DOC_NUM_TEXT.setForeground(Color.WHITE);
        DOC_NUM_TEXT.setFont(font);
        p.add(DOC_NUM_TEXT);

        DOC_NAME = new JLabel("NAME");
        DOC_NAME.setBounds(70, 120, 250, 25);
        DOC_NAME.setBackground(Color.white);
        DOC_NAME.setForeground(Color.white);
        DOC_NAME.setFont(font);
        p.add(DOC_NAME);

        DOC_NAME1 = new JTextField();
        DOC_NAME1.setBounds(250, 120, 250, 25);
        DOC_NAME1.setBackground(Color.WHITE);
        DOC_NAME1.setForeground(Color.BLACK);
        DOC_NAME1.setFont(font);
        p.add(DOC_NAME1);

        PHONE_NUMBER = new JLabel("NUMBER");
        PHONE_NUMBER.setBounds(70, 170, 250, 25);
        PHONE_NUMBER.setBackground(Color.white);
        PHONE_NUMBER.setForeground(Color.white);
        PHONE_NUMBER.setFont(font);
        p.add(PHONE_NUMBER);

        PHONE_NUMBER1 = new JTextField();
        PHONE_NUMBER1.setBounds(250, 170, 250, 25);
        PHONE_NUMBER1.setBackground(Color.white);
        PHONE_NUMBER1.setForeground(Color.BLACK);
        PHONE_NUMBER1.setFont(font);
        p.add(PHONE_NUMBER1);

        SPECIALIZATION = new JLabel("SPECIALIZATION");
        SPECIALIZATION.setBounds(70, 220, 250, 25);
        SPECIALIZATION.setBackground(Color.white);
        SPECIALIZATION.setForeground(Color.white);
        SPECIALIZATION.setFont(font);
        p.add(SPECIALIZATION);

        SPECIALIZATION1 = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(SPECIALIZATION1,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(250, 220, 250, 100);
        SPECIALIZATION1.setBackground(Color.white);
        SPECIALIZATION1.setForeground(Color.BLACK);
        SPECIALIZATION1.setFont(font);
        p.add(scrollPane);

        String[] columnNames = {"Doctor No.", "Doctor's Name", "Phone", "Specialization"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex >= 0) {
                    DefaultTableModel d1 = (DefaultTableModel) table.getModel();
                    DOC_NAME.setText(d1.getValueAt(selectedRowIndex, 0).toString());
                    DOC_NAME1.setText(d1.getValueAt(selectedRowIndex, 1).toString());
                    PHONE_NUMBER1.setText(d1.getValueAt(selectedRowIndex, 2).toString());
                    SPECIALIZATION1.setText(d1.getValueAt(selectedRowIndex, 3).toString());

                    ADD.setEnabled(false);
                }
            }
        });

// Remove the unnecessary mousePressed, mouseReleased, mouseEntered, and mouseExited methods
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(600, 70, 350, 500);
        p.add(tableScrollPane);

        // Initialize ADD button
        ADD = new JButton("ADD");
        ADD.setBounds(70, 350, 100, 30);
        p.add(ADD);
        // Inside the ADD ActionListener
        ADD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//        String pno = PATIENT_NO_TEXT.getText();
                String pname = DOC_NAME1.getText();
                String phone = PHONE_NUMBER1.getText(); // Get the phone number

                // Remove spaces and non-numeric characters
                phone = phone.replaceAll("[^0-9]", "");

                // Validate phone number length and format
                if (phone.length() <= 10) {// 10-digit phone number
                    String specialization = SPECIALIZATION1.getText();

                    try {
                        pst = con.prepareStatement("INSERT INTO doctor( NAME, PHONE, SPECIALIZATION) VALUES (?, ?, ?)");
//                pst.setString(1, pno);
                        pst.setString(1, pname);
                        pst.setString(2, phone); 
                        pst.setString(3, specialization);
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(null, "DOCTOR'S RECORD INSERTED");
                        patient_table();

                        AutoID();
                        patient_table();
                        DOC_NAME1.setText("");
//                PATIENT_NO_TEXT.setText("");
                        SPECIALIZATION1.setText("");
                        DOC_NAME1.requestFocus();

                    } catch (SQLException ex) {
                        Logger.getLogger(PATIENT.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid phone number format.");
                }
            }
        });

        // Initialize UPDATE button
        UPDATE = new JButton("UPDATE");
        UPDATE.setBounds(190, 350, 100, 30);
        p.add(UPDATE);
      UPDATE.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {

        String pname = DOC_NAME1.getText();
        String phone = PHONE_NUMBER1.getText();
        String specialization = SPECIALIZATION1.getText();
        String pno = DOC_NUM_TEXT.getText(); // Use the correct label for the primary key

        try {
            pst = con.prepareStatement("UPDATE doctor SET NAME = ?, PHONE = ?, SPECIALIZATION = ? WHERE NAME = ?"); // Use NAME as the primary key

            pst.setString(1, pname);
            pst.setString(2, phone);
            pst.setString(3, specialization);
            pst.setString(4, pno);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "DOCTOR RECORDS UPDATED");

            AutoID();
            DOC_NAME1.setText("");
            PHONE_NUMBER1.setText("");
            SPECIALIZATION1.setText("");
            patient_table();
            UPDATE.setEnabled(true);

        } catch (SQLException ex) {
            Logger.getLogger(PATIENT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});

        // Initialize DELETE button
        DELETE = new JButton("DELETE");
        DELETE.setBounds(310, 350, 100, 30);
        p.add(DELETE);
        DELETE.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String pno = DOC_NAME.getText();

                try {
                    pst = con.prepareStatement("DELETE FROM doctor WHERE DOCNO = ?");

                    pst.setString(1, pno);

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "DOCTOR RECORD DELETED");

                    AutoID();
                    DOC_NAME1.setText("");
                    PHONE_NUMBER1.setText("");
                    SPECIALIZATION1.setText("");
                    patient_table();
                    DELETE.setEnabled(true);

                } catch (SQLException ex) {
                    Logger.getLogger(PATIENT.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        EXIT = new JButton("EXIT");
        EXIT.setBounds(430, 350, 100, 30);
        p.add(EXIT);

        EXIT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame M = new MainFrame();
                dispose();
                M.setVisible(true);
            }
        });

    }

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lovecare", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (con != null) {
            DOCTOR_REGISTRATION D = new DOCTOR_REGISTRATION(con);
            D.setVisible(true);
        } else {
            System.out.println("Failed to establish a database connection.");

        }
    }

    DOCTOR_REGISTRATION() {

    }

    private void Connect() {
        try {
            Statement s = con.createStatement();
            rs = s.executeQuery("SELECT MAX(DOCNO) FROM doctor");

            rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(PATIENT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void patient_table() {
        try {
            pst = con.prepareStatement("SELECT * FROM doctor");
            rs = pst.executeQuery();

            ResultSetMetaData Rsm = rs.getMetaData();
            int c;
            c = Rsm.getColumnCount();
            DefaultTableModel df = (DefaultTableModel) table.getModel();

            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getInt("DOCNO"));
                    v2.add(rs.getString("NAME"));
                    v2.add(rs.getString("PHONE"));
                    v2.add(rs.getString("SPECIALIZATION"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PATIENT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AutoID() {
        try {
            Statement s = con.createStatement();
            rs = s.executeQuery("SELECT DOCNO FROM doctor");

            int maxId = 0;

            while (rs.next()) {
                int DOCNO = rs.getInt("DOCNO");
                if (DOCNO > maxId) {
                    maxId = DOCNO;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(PATIENT.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
