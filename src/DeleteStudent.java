package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class DeleteStudent extends JDialog implements ActionListener {

    // Set up two buttons for confirmation and cancellation
    JButton[] button = new JButton[2];
    JPanel jp;
    JTable jt;
    JScrollPane jsp = null;
    // Set up a form for deletion
    MyJTable mytable;
    // Common SQL variables

    Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private String name;
    private MyJFrame father;

    public DeleteStudent(Frame father, String name, boolean modal) {
        this.father = (MyJFrame) father;

        this.setName(name);
        jp = new JPanel();
        button[0] = new JButton("OK");
        button[0].addActionListener(this);
        button[1] = new JButton("Cancel");
        button[1].addActionListener(this);
        jp.add(button[0]);
        jp.add(button[1]);

        // Set up a table for displaying data
        mytable = new MyJTable(name);
        jt = new JTable(mytable);
        JScrollPane jsp = new JScrollPane(jt);
        this.add(jp, BorderLayout.SOUTH);
        this.add(jsp, BorderLayout.NORTH);
        this.setTitle("Delete Interface");
        this.setSize(800, 500);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button[0]) {
            int i = StudentJDBC.getInstance().delete(this.getName().trim());

            if (1 == i)
                JOptionPane.showMessageDialog(this, "Deletion Successful");
            else
                JOptionPane.showMessageDialog(this, "Deletion Failed!");
            father.refresh();
        }

        this.dispose();
    }
}
