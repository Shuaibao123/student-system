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

    // Fill in the information space
    private String sex = null;
    JTextField[] jt = new JTextField[5];
    JButton[] jb = new JButton[2];
    // radio button
    JRadioButton[] jradio = new JRadioButton[2];
    // Combination radio button
    ButtonGroup group;
    // Used to set box layout BoxLayout
    Box[] mybox = new Box[4];
    JPanel jp, jp1;
    // Several commonly used variable types in SQL
    Connection ct = null;
    PreparedStatement ps = null;
    private JPanel backgroundPanel;
    public AddJFrame(Frame Father, boolean Model) {
        // Adopting Mode Dialogs
        super(Father, Model);
        backgroundImage = new ImageIcon("image:\\aa.jpeg").getImage();
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED); // Set the background color to red
                g.fillRect(0, 0, getWidth(), getHeight()); // Fill the entire background
            }
        };
        for (int i = 0; i < 5; i++)
            jt[i] = new JTextField(10);
        jb[0] = new JButton("YES");
        jb[0].addActionListener(this);
        jb[1] = new JButton("NO");
        jb[1].addActionListener(this);
        jradio[0] = new JRadioButton("MAN");
        jradio[0].addActionListener(this);
        jradio[1] = new JRadioButton("WOMAN");
        jradio[1].addActionListener(this);
        // Set Horizontal
        mybox[0] = Box.createVerticalBox();
        mybox[0].add(Box.createVerticalStrut(15));
        mybox[0].add(new JLabel("STUDENT ID："));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("NAME："));
        mybox[0].add(Box.createVerticalStrut(20));
        mybox[0].add(new JLabel("SEX："));
        mybox[0].add(Box.createVerticalStrut(25));
        mybox[0].add(new JLabel("AGE："));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("ADDRESS："));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("DEPARTMENT："));
        mybox[0].add(Box.createVerticalStrut(10));
        // Set the level on the other side
        mybox[1] = Box.createVerticalBox();
        mybox[1].add(Box.createVerticalStrut(18));
        mybox[1].add(jt[0]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[1]);
        mybox[1].add(Box.createVerticalStrut(8));
        jp = new JPanel();
        // Single choice group
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
        mybox[2].add(mybox[0]);
        mybox[2].add(Box.createHorizontalStrut(10));
        mybox[2].add(mybox[1]);
        this.add(mybox[2], BorderLayout.NORTH);
        this.add(jp1, BorderLayout.SOUTH);
        init();
    }

    // Display section
    public void init() {
        this.setTitle("Add student information");
        ImageIcon bg = new ImageIcon("image:\\aa.jpeg"); // Interface background image
        setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
        JLabel _label=new JLabel(bg);//Add background image to label
        _label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
        this.setBounds(800, 600, 600, 600);
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
        // Write data to the database
        if (e.getSource() == jb[0]) {
            // Process the written data
            try {

                String driver = "jdbc:derby://localhost:1527/student;create=true;";
                ct = DriverManager.getConnection(driver);
                ps = ct.prepareStatement("insert into student values(?,?,?,?,?,?)");
                for (int i = 1, j = 0; i <= 6; i++) {
                    if (3 == i) {
                        // Remove excess spaces, but there is a small bug here (a reasonable solution is to use regular expressions)
                        ps.setString(i, this.getSex().trim());
                        // ps.setInt(i,);
                    } else if (4 == i) {
                        String tem = jt[j].getText().toString().trim();
                        int value = Integer.valueOf(tem).intValue();
                        ps.setInt(i, value);
                        ++j;
                    } else {
                        ps.setString(i, jt[j].getText().toString().trim());
                        ++j;
                    }
                }
                int i = ps.executeUpdate();

                if (1 == i)
                    JOptionPane.showMessageDialog(this, "Added successfully！");
                else
                    JOptionPane.showMessageDialog(this, "Add failed！");

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } finally {
                // Close some calling functions of the database
                try {
                    if (ps != null)
                        ps.close();
                    if (ct != null)
                        ct.close();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            this.dispose();
        } else if (e.getSource() == jb[0])
            // Release the window to exit the layout
            this.dispose();
        else if (jradio[0].isSelected()) {
            // Change name to male
            this.setSex("MAN");
        } else if (jradio[1].isSelected()) {
            // Change the name to female
            this.setSex("WOMAN");
        }
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
