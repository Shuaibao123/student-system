package src;

import javax.swing.*;
import java.awt.*;

/**
 * GUI Utility Class
 */
public class GUIUtil {
    /**
     * Display Information Dialog
     * @param component
     * @param msg
     */
    public static void dialog(Component component, String msg) {
        JOptionPane.showMessageDialog(component, msg, "Prompt", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Navigate to a Page
     * @param source
     * @param target
     */
    public static void toPage(JFrame source, JFrame target) {
        try {
            target.setVisible(true);
            source.dispose(); // Close the current page
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Debugging View
     * @param root
     */
    public static void debugView(JComponent root) {
        root.setBackground(Color.BLUE); // Set the background color to blue
        root.setBorder(BorderFactory.createLineBorder(Color.RED));
    }
}
