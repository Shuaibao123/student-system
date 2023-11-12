package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class MyJFrame extends JFrame implements ActionListener {

    JTable jt;
    MyJTable mjt;
    // Necessary panel components
    JPanel jpup, jpdown, jpcenter;
    // Implementing CRUD (Create, Read, Update, Delete) operations for data
    JButton[] button = new JButton[4];
    // Text field for assisting in the search module
    JTextField text;

    JScrollPane jsp = null;

    /*
     * Constructor Function Function: Initializes the window
     */
    MyJFrame() {
        JPanel root = new JPanel() {
            // Override paint method to set background image
        };
        this.getLayeredPane().add(root, new Integer(-1));
        root.setOpaque(false);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        this.setIconImage(Main.WINDOWS_ICON);
        mjt = new MyJTable();
        jt = new JTable(mjt);
        jpup = new JPanel();
        jpdown = new JPanel();
        jpcenter = new JPanel();
        text = new JTextField(14);
        button[0] = new JButton(" Search  ");
        button[0].setIcon(new ImageIcon("image\\select.png"));
        button[1] = new JButton(" Add  ");
        button[1].setIcon(new ImageIcon("image\\addto.png"));
        button[2] = new JButton(" Update  ");
        button[2].setIcon(new ImageIcon("image\\edit.png"));
        button[3] = new JButton(" Delete  ");
        button[3].setIcon(new ImageIcon("image\\delete.png"));
        for (int i = 0; i < 4; i++)
            button[i].addActionListener(this);
        JLabel label = new JLabel("User Information");
        jpup.add(label);
        jpup.add(text);
        jpup.add(button[0]);
        for (int i = 1; i < 4; i++)
            jpdown.add(button[i]);
        jt.setBackground(Color.CYAN);
        jsp = new JScrollPane(jt);
        jpcenter.add(jsp);
        // Add some layout components (borderLayout)
        root.add(jpup, BorderLayout.NORTH);
        root.add(jpcenter, BorderLayout.CENTER);
        root.add(jpdown, BorderLayout.SOUTH);

        this.add(root);

        // Set background image
        {
            JLabel labelBg = new JLabel(new ImageIcon("image\\aa.jpeg"));
            labelBg.setBounds(0, 0, 600, 400);
            this.getLayeredPane().add(labelBg, new Integer(Integer.MIN_VALUE));
            ((JPanel) this.getContentPane()).setOpaque(false);
        }
        // Set window title
        this.setTitle("Student Information Management System");
        // Set window size
        this.setSize(600, 600);
        // Window size is not resizable
        // this.setResizable(false);
        // Set window visibility
        this.setVisible(false);
        // Set window close operation mode
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
    }

    public void refresh() {
        String cmd = text.getText().toString().trim();
        if (cmd.isEmpty()) {
            mjt = new MyJTable();
        } else {
            mjt = new MyJTable(cmd);
        }
        jt.setModel(mjt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i = 0;
        for (i = 0; i < 4; i++)
            if (e.getSource() == button[i])
                break;
        String cmd = text.getText().toString().trim();
        switch (i) {
            // Search
            case 0:
                if (cmd.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a username", "Information", JOptionPane.INFORMATION_MESSAGE);
                    mjt = new MyJTable();
                } else
                    mjt = new MyJTable(cmd);
                jt.setModel(mjt);
                break;
            // Add
            case 1:
                AddJFrame myAdd = new AddJFrame(this, true);
                mjt = new MyJTable();
                jt.setModel(mjt);
                break;
            // Update
            case 2:
                // Get the selected row and column numbers
                int rownum = this.jt.getSelectedRow();
                if (-1 == rownum) {
                    JOptionPane.showMessageDialog(this, "Please select a person to update");
                    return;
                } else {
                    String name = (String) mjt.getValueAt(rownum, 0);
                    UpdateJFrame Uj = new UpdateJFrame(this, true, name);
                    mjt = new MyJTable();
                    jt.setModel(mjt);
                }
                break;
            // Delete
            case 3:
                int row = this.jt.getSelectedRow();
                if (-1 == row) {
                    JOptionPane.showMessageDialog(this, "Please select a person to delete");
                    return;
                } else {
                    String name = (String) mjt.getValueAt(row, 1);
                    DeleteStudent det = new DeleteStudent(this, name.trim(), true);
                }
                mjt = new MyJTable();
                jt.setModel(mjt);
                break;
            // Do nothing
            default:

                break;
        }
    }
}
