import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyJFrame extends JFrame implements ActionListener {

    JTable jt;
    MyJTable mjt;
    // Necessary distribution of plates
    JPanel jpup, jpdown, jpcenter;
    // Implement crud operations for adding, deleting, checking, and modifying data
    JButton[] button = new JButton[4];
    // Boxes used to assist in finding modules
    JTextField text;

    JScrollPane jsp = null;

    /*
     * Constructor function: Implement window initialization
     */
    MyJFrame() {
//        ImageIcon bg = new ImageIcon("D:\\aa.jpeg"); // Interface background image
//        bg=  new ImageIcon("image\\aa.jpeg");

//        setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
//        JLabel _label=new JLabel(bg);//Add background image to label
//        _label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
        JPanel root = new JPanel() {
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        g.drawImage(new ImageIcon("image\\aa.jpeg").getImage(), 0, 0, 900,700, this);
//    }
        };
        this.getLayeredPane().add(root, new Integer(-1));
        root.setOpaque(false);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        this.setIconImage(Main.WINDOWS_ICON);
        mjt = new MyJTable();
        jt = new JTable(mjt);
        // jt.setModel(mjt);
        jpup = new JPanel();
        jpdown = new JPanel();
        jpcenter = new JPanel();
        text = new JTextField(14);
        button[0] = new JButton(" SELECT  ");
        button[0].setIcon(new ImageIcon("image\\select.png"));
        button[1] = new JButton(" INSERT  ");
        button[1].setIcon(new ImageIcon("image\\addto.png"));
        button[2] = new JButton(" UPDATE  ");
        button[2].setIcon(new ImageIcon("image\\edit.png"));
        button[3] = new JButton(" DELETE  ");
        button[3].setIcon(new ImageIcon("image\\delete.png"));
        for (int i = 0; i < 4; i++)
            button[i].addActionListener(this);
        JLabel label = new JLabel("USER INFORMATION");
//        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        jpup.add(label);
        jpup.add(text);
        jpup.add(button[0]);
        for (int i = 1; i < 4; i++)
            jpdown.add(button[i]);
        jt.setBackground(Color.CYAN);
        jsp = new JScrollPane(jt);
        jpcenter.add(jsp);
        // Set up some layouts borderLayouts
        root.add(jpup, BorderLayout.NORTH);
        root.add(jpcenter, BorderLayout.CENTER);
        root.add(jpdown, BorderLayout.SOUTH);

        this.add(root);

        {
            JLabel labelBg = new JLabel(new ImageIcon("image\\aa.jpeg"));
            labelBg.setBounds(0, 0, 1000, 400);
            // Add two labels with images on the bottom layer of Layered Panel, and label 2 is above label
            this.getLayeredPane().add(labelBg, new Integer(Integer.MIN_VALUE));
            // Set the content panel to transparent and you can see the background added to the Layered Panel.
            ((JPanel) this.getContentPane()).setOpaque(false);
        }
        // Set the title of the window
        this.setTitle("student information management system");
        // Set the size of the window
        this.setSize(1000, 600);
        // The size of the window cannot be changed
        // this.setResizable(false);
        // Set whether the window is visible
        this.setVisible(false);
        // Set the logout mode of the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        int i = 0;
        for (i = 0; i < 4; i++)
            if (e.getSource() == button[i])
                break;
        String cmd = text.getText().toString().trim();
        switch (i) {
            // SELECT
            case 0:
                if (cmd.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "enter one user name", "prompt", JOptionPane.INFORMATION_MESSAGE);
                    mjt = new MyJTable();
                } else
                    mjt = new MyJTable(cmd);
                jt.setModel(mjt);
                break;
            // INSERT
            case 1:
                AddJFrame myAdd = new AddJFrame(this, true);
                mjt = new MyJTable();
                jt.setModel(mjt);
                break;
            // UPDATE
            case 2:
                // We need to obtain the row and column numbers in our point
                int rownum = this.jt.getSelectedRow();
                if (-1 == rownum) {
                    JOptionPane.showMessageDialog(this, "Please select the person you want to modify");
                    return;
                } else {
                    String name = (String) mjt.getValueAt(rownum, 0);
                    UpdataJFrame Uj = new UpdataJFrame(this, true, name);
                    mjt = new MyJTable();
                    jt.setModel(mjt);
                }
                break;
            // DELETE
            case 3:
                int row = this.jt.getSelectedRow();
                if (-1 == row) {
                    JOptionPane.showMessageDialog(this, "Please select the person you want to modify");
                    return;
                } else {
                    String name = (String) mjt.getValueAt(row, 1);
                    DeleteStudent det = new DeleteStudent(this, name.trim(), true);
                }
                mjt = new MyJTable();
                jt.setModel(mjt);
                break;
            // NONE
            default:

                break;
        }
    }

}