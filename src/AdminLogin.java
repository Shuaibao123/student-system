
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
    // Label labels store background images
    private JLabel label;
    // Set button components
    private JButton login = new JButton("LOG ON");
    private JButton cancel = new JButton("CANCEL");
    private JLabel jlb1 = new JLabel("USERNAME:"), jlb2 = new JLabel("CODE:"), jlbtitle = new JLabel("Login interface");
    // Set Text Box Component
    private JTextField admin = new JTextField();
    JPasswordField password = new JPasswordField();

    public AdminLogin() {
        this.init();
        this.addListener();
    }

    private void init() {
        this.setTitle("Administrator login interface");
        this.setSize(500, 350);
        ImageIcon image1 = new ImageIcon("image\\aa.jpeg"); // Interface background image
        JLabel backLabel = new JLabel();
        backLabel.setIcon(image1);
        label = new JLabel(image1);
        label.setBounds(0, 0, 1000, 400);
        // Add two labels with images on the bottom layer of Layered Panel, and label 2 is above label
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        // Set the content panel to transparent and you can see the background added to the Layered Panel.
        ((JPanel) this.getContentPane()).setOpaque(false);

        /*
         * Add components to the contentPanel container with a free layout.
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
         * Absolute position of components
         */
        jlb1.setBounds(50, 130, 90, 25);
        jlb1.setForeground(Color.black);
        admin.setBounds(95, 130, 300, 25);
        password.setBounds(95, 154, 300, 25);
        jlb2.setBounds(50, 154, 90, 25);
        jlb2.setForeground(Color.black);
        login.setBounds(115, 225, 90, 20);
        cancel.setBounds(215, 225, 90, 20);
        jlbtitle.setBounds(180, 45, 200, 50);
        Font f = new Font("微软雅黑", Font.BOLD, 30);
        jlbtitle.setFont(f);
        jlbtitle.setForeground(Color.BLUE);

        /*
         * Component transparency
         */
        admin.setOpaque(true);
        password.setOpaque(true);
        contentPanel.setOpaque(false);
        getContentPane().add(contentPanel);

        /*
         * Component Border Color
         */
        textSet(admin);
        textSet(password);
    }

    /*
     * JTextField text box setting method
     */
    private void textSet(JTextField field) {
        field.setBackground(new Color(255, 255, 255));
        field.setPreferredSize(new Dimension(150, 28));
        MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(192, 192, 192));
        field.setBorder(border);
    }

    /*
     * event listeners
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
        if ("root".equals(admin) && "123456".equals(pwd)){
            MyJFrame mf = new MyJFrame();
            mf.show();
            this.dispose();
        }
    }

}