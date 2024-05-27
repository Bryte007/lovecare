package lovecare;

import com.mysql.jdbc.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.plaf.ColorUIResource;

public class DOCTOR extends JFrame implements ActionListener {

    private JPanel p, p1, p2;
    private JButton DOC,  PATIENT_DIAGNOSIS;
    private JButton LOGOUT;

    private JLabel title;
    private JLabel USERTYPE2;
    private JLabel USERNAME2;

    private Timer textAnimationTimer;
    private Timer backgroundAnimationTimer;
    private String[] textOptions = { "LOVE CARE", "Healthcare", "Wellness", "Patient Care" };
    private Color[] backgroundColors = {new ColorUIResource(0,255,127),new ColorUIResource(0,250,154) ,new ColorUIResource(60,179,113) , new ColorUIResource(0, 75, 73) };
    private int textIndex = 0;
    private int colorIndex = 0;

    public DOCTOR() {
        setSize(540, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        Font font1 = new Font("ARIAL ROUNDED MT BOLD", Font.BOLD, 25);
        Font font = new Font("ARIAL", Font.BOLD, 15);

        p = new JPanel();
        p.setLayout(null);
        p.setBounds(0, 0, 300, 700);
        add(p);
        
        PATIENT_DIAGNOSIS = new JButton("DIAGNOSIS");
       PATIENT_DIAGNOSIS.setBounds(80, 60, 200, 50);
        PATIENT_DIAGNOSIS.setBackground(Color.WHITE);
        PATIENT_DIAGNOSIS.setForeground(Color.BLACK);
      PATIENT_DIAGNOSIS.setFont(font);
        p.add(PATIENT_DIAGNOSIS);
        PATIENT_DIAGNOSIS.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                PatientDiagnosis patientDiagnosis = new PatientDiagnosis();
               patientDiagnosis.setVisible(true);
            }
        });
        
        DOC = new JButton("PATIENT REPORT");
        DOC.setBounds(80, 120, 200, 50);
        DOC.setBackground(Color.WHITE);
        DOC.setForeground(Color.BLACK);
        DOC.setFont(font);
        p.add(DOC);
        DOC.addActionListener(this);

        LOGOUT = new JButton("LOGOUT");
        LOGOUT.setBounds(100, 200, 150, 50);
        LOGOUT.setBackground(Color.WHITE);
        LOGOUT.setForeground(Color.BLACK);
        LOGOUT.setFont(font);
        p.add(LOGOUT);
        LOGOUT.addActionListener(this);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(300, -50, 700, 700);
        p1.setBackground(backgroundColors[0]);

        title = new JLabel(textOptions[0]);
        title.setBounds(50, 100, 375, 50);
        title.setFont(font1);
        title.setForeground(Color.WHITE);
        p1.add(title);

        add(p1);

        p2 = new JPanel();
        p2.setLayout(null);
        p2.setBounds(0, 150, 500, 300);
        p1.add(p2);

        // Load and set the background image for p2
        ImageIcon backgroundImageIcon =
                
      new ImageIcon("C:\\Users\\Humble-Lyon-PC\\Desktop\\Hospital Management\\LoveCare\\LOVECARE\\src\\lovecare\\12.jpg"); // Replace with your image file
        Image scaledImage = backgroundImageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        ImageIcon backgroundImage = new ImageIcon(scaledImage);

        JLabel backgroundImageLabel = new JLabel(backgroundImage);
        backgroundImageLabel.setBounds(0, 0, 500, 300);

        // Make the JLabel non-opaque so that the panel's background color shows through
        backgroundImageLabel.setOpaque(false);

        p2.add(backgroundImageLabel);

        // Initialize timers for text and background color animation
        textAnimationTimer = new Timer(5000, this); // Change text every 5 seconds
        textAnimationTimer.setActionCommand("TextAnimation");

        backgroundAnimationTimer = new Timer(5000, this); // Change background color every 5 seconds
        backgroundAnimationTimer.setActionCommand("BackgroundAnimation");

        // Start the timers
        textAnimationTimer.start();
        backgroundAnimationTimer.start();
    }

    DOCTOR(Connection con, String username, String usertype) {
        setVisible(true);
    }

    public void setUsername(String username) {
        USERNAME2.setText(username);
    }

    public void setUsertype(String usertype) {
        USERTYPE2.setText(usertype);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("TextAnimation".equals(e.getActionCommand())) {
            textIndex = (textIndex + 1) % textOptions.length;
            title.setText(textOptions[textIndex]);
        } else if ("BackgroundAnimation".equals(e.getActionCommand())) {
            colorIndex = (colorIndex + 1) % backgroundColors.length;
            p1.setBackground(backgroundColors[colorIndex]);
        } else if (e.getSource() == DOC) {
            PATIENT_REPORT R = new PATIENT_REPORT(DatabaseConnection.getConnection());
            dispose();
            R.setVisible(true);
        } else if (e.getSource() == LOGOUT) {
            new LOGIN();
            dispose();
        }
    }

    public static void main(String[] args) {
        new DOCTOR().setVisible(true);
    }
}
