import javax.swing.*;
import java.awt.*;

/**
 * GUI Tool Class
 */
public class GUIUtil {
    /**
     * Prompt information dialog box
     * @param component
     * @param msg
     */
    public static void dialog(Component component, String msg) {
        JOptionPane.showMessageDialog(component, msg, "prompt", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Page Jump
     * @param component
     * @param msg
     */
    public static void toPage(JFrame source, JFrame target) {
        try {
            //chatgpt
            target.setVisible(true);
            source.dispose(); // Close the current page
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void debugView(JComponent root) {
        root.setBackground(Color.BLUE); // Set the background color to blue
        root.setBorder(BorderFactory.createLineBorder(Color.RED));
    }
}
