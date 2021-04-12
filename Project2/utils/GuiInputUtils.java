package utils;

import javax.swing.*;

public class GuiInputUtils {
  public static String promptInput(JFrame frame, String prompt) {
    return JOptionPane.showInputDialog(frame, prompt);
  }

  public static void informUser(JFrame frame, String message) {
    JOptionPane.showMessageDialog(frame, message);
  }

  public static boolean yesOrNo(JFrame frame, String prompt) {
    int result = JOptionPane.showConfirmDialog(frame, prompt);
    return result == JOptionPane.YES_OPTION;
  }

  public static int getNumber(JFrame frame, String prompt) {
    do {
      try {
        String item = promptInput(frame, prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        informUser(frame, "Not a number. Please input a number.");
      }
    } while (true);
  }

  public static double getDouble(JFrame frame, String prompt) {
    do {
      try {
        String item = promptInput(frame, prompt);
        Double num = Double.parseDouble(item);
        return num.doubleValue();
      } catch (NumberFormatException nfe) {
        GuiInputUtils.informUser(frame, "Please input a number ");
      }
    } while (true);
  }

}
