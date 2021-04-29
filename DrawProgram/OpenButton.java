import javax.swing.*;
import java.awt.event.*;
public class OpenButton  extends JButton implements ActionListener {
  protected View view;
  private UndoManager undoManager;
  public OpenButton(UndoManager undoManager, View jFrame) {
    super("Open");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
  }
  public void actionPerformed(ActionEvent e){
    String string = JOptionPane.showInputDialog(view, "Please type new file name");
    OpenCommand command = new OpenCommand(string);
    view.setFileName(string);
    undoManager.beginCommand(command);
    undoManager.endCommand(command);
  }
}
