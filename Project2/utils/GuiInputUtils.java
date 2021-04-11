package utils;

import javax.swing.*;

public class GuiInputUtils {
  public static String promptInput(JFrame frame, String prompt) {
    return JOptionPane.showInputDialog(frame, prompt);
  }

  public static void informUser(JFrame frame, String message) {
    JOptionPane.showMessageDialog(frame, message);
  }
}
