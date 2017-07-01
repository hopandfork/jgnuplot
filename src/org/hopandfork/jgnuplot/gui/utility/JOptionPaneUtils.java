package org.hopandfork.jgnuplot.gui.utility;

import javax.swing.*;
import java.awt.*;

public class JOptionPaneUtils {


    public static void showWarning(Component owner, String title, String message) {
        JOptionPane.showMessageDialog(owner,
                message,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public static void showError(Component owner, String title, String message) {
        JOptionPane.showMessageDialog(owner,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }
}
