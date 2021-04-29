import javax.swing.*;
import java.awt.event.*;
public class SaveButton  extends JButton implements ActionListener {
  protected View view;
  private UndoManager undoManager;
  public SaveButton(UndoManager undoManager, View jFrame) {
    super("Save");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
  }
  public void actionPerformed(ActionEvent event) {
    String string = view.getFileName();
    if (string == null) {
      string = JOptionPane.showInputDialog(view, "Please specify file name");
      view.setFileName(string);
    }
    SaveCommand command = new SaveCommand(string);
    undoManager.beginCommand(command);
    undoManager.endCommand(command);
  }
}
