package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Define an interface for adding data
class AddJFrame extends JDialog implements ActionListener {

    // Text fields for entering information
    private String sex = null;
    JTextField[] jt = new JTextField[5];
    JButton[] jb = new JButton[2];
    // Radio buttons
    JRadioButton[] jradio = new JRadioButton[2];
    // Grouping radio buttons
    ButtonGroup group;
    // For setting up BoxLayout
    Box[] mybox = new Box[4];
    JPanel jp, jp1;
    // Common SQL variable types
    Connection ct = null;
    PreparedStatement ps = null;
    private JPanel backgroundPanel;
    private MyJFrame father;
    public AddJFrame(Frame father, boolean Model) {
        this.father=(MyJFrame)father;
        // Using a modal dialog
//        super(Father, Model);
        setLocationRelativeTo(null);
        backgroundImage = new ImageIcon("image:\\aa.jpeg").getImage();
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED); // Set background color to red
                g.fillRect(0, 0, getWidth(), getHeight()); // Fill the entire background
            }
        };
        for (int i = 0; i < 5; i++)
            jt[i] = new JTextField(10);
        jb[0] = new JButton("Confirm");
        jb[0].addActionListener(this);
        jb[1] = new JButton("Cancel");
        jb[1].addActionListener(this);
        jradio[0] = new JRadioButton("Male");
        jradio[0].addActionListener(this);
        jradio[1] = new JRadioButton("Female");
        jradio[1].addActionListener(this);
        // Setting up vertically
        mybox[0] = Box.createVerticalBox();
        mybox[0].add(Box.createVerticalStrut(15));
        mybox[0].add(new JLabel("Student ID:"));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("Name:"));
        mybox[0].add(Box.createVerticalStrut(20));
        mybox[0].add(new JLabel("Sex:"));
        mybox[0].add(Box.createVerticalStrut(25));
        mybox[0].add(new JLabel("Age:"));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("Home Address:"));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("Department:"));
        mybox[0].add(Box.createVerticalStrut(10));
        // Setting up the other side vertically
        mybox[1] = Box.createVerticalBox();
        mybox[1].add(Box.createVerticalStrut(18));
        mybox[1].add(jt[0]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[1]);
        mybox[1].add(Box.createVerticalStrut(8));
        jp = new JPanel();
        // Radio button group
        group = new ButtonGroup();
        group.add(jradio[0]);
        group.add(jradio[1]);
        jp.add(jradio[0]);
        jp.add(jradio[1]);
        mybox[1].add(jp);

        mybox[1].add(Box.createVerticalStrut(5));
        mybox[1].add(jt[2]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[3]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[4]);
        mybox[1].add(Box.createVerticalStrut(8));
        // Button section
        jp1 = new JPanel();
        jp1.add(jb[0], BorderLayout.EAST);
        jp1.add(jb[1], BorderLayout.WEST);
        mybox[2] = Box.createHorizontalBox();
        mybox[2].add(Box.createHorizontalStrut(25));
        mybox[2].add(mybox[0]);
        mybox[2].add(Box.createHorizontalStrut(10));
        mybox[2].add(mybox[1]);
        mybox[2].add(Box.createHorizontalStrut(25));
        this.add(mybox[2], BorderLayout.NORTH);
        this.add(jp1, BorderLayout.SOUTH);
        init();
        setLocationRelativeTo(null);
    }

    // Display panel section
    public void init() {
        this.setTitle("Add Student Information");
        ImageIcon bg = new ImageIcon("image:\\aa.jpeg"); // Interface background image
        setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
        JLabel _label=new JLabel(bg);// Adding background image to label
        _label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
//        this.setBounds(800, 600, 600, 600);
        setSize(400,280);
        this.setVisible(true);
    }
    private Image backgroundImage;
    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // Writing data to the database
        if (e.getSource() == jb[0]) {
            // Processing the entered data
            int i= StudentJDBC.getInstance().insert(
                    jt[0].getText().toString().trim(),
                    jt[1].getText().toString().trim(),
                    this.getSex().trim(),
                    Integer.valueOf(jt[2].getText().toString().trim()).intValue(),
                    jt[3].getText().toString().trim(),
                    jt[4].getText().toString().trim()
            );
            if (1 == i)
                JOptionPane.showMessageDialog(this, "Added successfully!");
            else
                JOptionPane.showMessageDialog(this, "Addition failed!");

            father.refresh();
            this.dispose();
        } else if (e.getSource() == jb[1])
            // Close this window, exit the panel
            this.dispose();
        else if (jradio[0].isSelected()) {
            // Change the name to male
            this.setSex("Male");
        } else if (jradio[1].isSelected()) {
            // Change the name to female
            this.setSex("Female");
        }
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
