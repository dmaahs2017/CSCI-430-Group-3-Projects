import javax.swing.*;
import java.awt.event.*;
public class UndoButton  extends JButton implements ActionListener {
  private UndoManager undoManager;
  public UndoButton(UndoManager undoManager) {
    super("Undo");
    this.undoManager = undoManager;
    addActionListener(this);
  }
  public void actionPerformed(ActionEvent event) {
    undoManager.undo();
  }
}