package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.MatteBorder;

/*
 * Login Window
 */
@SuppressWarnings("serial")
public class AdminLogin extends JFrame {
    private JPanel contentPanel = new JPanel();
    // Label component to store the background image
    private JLabel label;
    // Setting up buttons
    private JButton login = new JButton("Login");
    private JButton cancel = new JButton("Cancel");
    private JLabel jlb1 = new JLabel("Username:"), jlb2 = new JLabel("Password:"), jlbtitle = new JLabel("Login Page");
    // Setting up text fields
    private JTextField admin = new JTextField();
    JPasswordField password = new JPasswordField();

    public AdminLogin() {
        this.init();
        this.addListener();
    }

    private void init() {
        this.setTitle("Administrator Login Page");
        this.setSize(500, 350);
        this.setIconImage(Main.WINDOWS_ICON);
        label = new JLabel(new ImageIcon("image\\aa.jpeg"));
        label.setBounds(0, 0, 1000, 400);
        // Adding two image labels on the LayeredPane, with label2 on top of label1
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        // Setting the content panel to be transparent to display the background added to LayeredPane.
        ((JPanel) this.getContentPane()).setOpaque(false);

        /*
         * Adding components to the contentPanel container with a free layout.
         */
        contentPanel.setLayout(null);
        add(admin);
        add(password);
        add(login);
        add(cancel);
        add(jlb1);
        add(jlb2);
        add(jlbtitle);

        /*
         * Absolute positioning of components
         */
        jlb1.setBounds(50, 130, 80, 25);
        jlb1.setForeground(Color.black);
        jlb1.setFont(Main.DEFAULT_FOUNT_18);
        jlb1.setHorizontalAlignment(SwingConstants.RIGHT);

        admin.setBounds(130, 130, 300, 25);
        password.setBounds(130, 164, 300, 25);
        jlb2.setBounds(50, 164, 80, 25);
        jlb2.setForeground(Color.black);
        jlb2.setFont(Main.DEFAULT_FOUNT_18);
        jlb2.setHorizontalAlignment(SwingConstants.RIGHT);
        login.setBounds(150, 225, 90, 20);
        cancel.setBounds(250, 225, 90, 20);
        jlbtitle.setBounds(180, 45, 200, 50);
        Font f = new Font("微软雅黑", Font.BOLD, 30);
        jlbtitle.setFont(f);
        jlbtitle.setForeground(Color.BLUE);

        /*
         * Making components transparent
         */
        admin.setOpaque(true);
        password.setOpaque(true);
        contentPanel.setOpaque(false);
        getContentPane().add(contentPanel);

        /*
         * Setting component border colors
         */
        textSet(admin);
        textSet(password);

        setLocationRelativeTo(null);
    }

    /*
     * Method to set JTextField text fields.
     */
    private void textSet(JTextField field) {
        field.setBackground(new Color(255, 255, 255));
        field.setPreferredSize(new Dimension(150, 28));
        MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(192, 192, 192));
        field.setBorder(border);
    }

    /*
     * Event listeners
     */
    private void addListener() {
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forLogin(admin.getText(), password.getText());
            }

        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });

    }

    // Login method
    public void forLogin(String admin, String pwd) {
        if (!"root".equals(admin)) {
            GUIUtil.dialog(this, "Incorrect username");
            return;
        }
        if (!"123456".equals(pwd)) {
            GUIUtil.dialog(this, "Incorrect password");
            return;
        }
        MyJFrame mf = new MyJFrame();
        mf.show();
        this.dispose();
    }

}
