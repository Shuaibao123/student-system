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
        ImageIcon bg = new ImageIcon("D:\\aa.jpeg"); // Interface background image
        setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
        JLabel _label=new JLabel(bg);//Add background image to label
        _label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());

        mjt = new MyJTable();
        jt = new JTable(mjt);
        // jt.setModel(mjt);
        jpup = new JPanel();
        jpdown = new JPanel();
        jpcenter = new JPanel();
        text = new JTextField(14);
        button[0] = new JButton(" SELECT  ");
        button[1] = new JButton(" INSERT  ");
        button[2] = new JButton(" UPDATE  ");
        button[3] = new JButton(" DELETE  ");
        for (int i = 0; i < 4; i++)
            button[i].addActionListener(this);
        JLabel label = new JLabel("user information");
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        jpup.add(label);
        jpup.add(text);
        jpup.add(button[0]);
        for (int i = 1; i < 4; i++)
            jpdown.add(button[i]);
        jt.setBackground(Color.CYAN);
        jsp = new JScrollPane(jt);
        jpcenter.add(jsp);
        // Set up some layouts borderLayouts
        this.add(jpup, BorderLayout.NORTH);
        this.add(jpcenter, BorderLayout.CENTER);
        this.add(jpdown, BorderLayout.SOUTH);

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
                    JOptionPane.showMessageDialog(button[0], "enter one user name");
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