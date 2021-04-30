import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MoveButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private MoveCommand moveCommand;
  private UndoManager undoManager;
  public MoveButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Move Selected");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    mouseHandler = new MouseHandler();
  }
  public void actionPerformed(ActionEvent event) {
    view.setCursor(new Cursor(Cursor.HAND_CURSOR));
    // Change cursor when button is clicked
    drawingPanel.addMouseListener(mouseHandler);
  // Start listening for mouseclicks on the drawing panel
  }
  private class MouseHandler extends MouseAdapter {
    private int pointCount = 0;
    public void mouseClicked(MouseEvent event) {
    if (++pointCount == 1) {
        moveCommand = new MoveCommand(View.mapPoint(event.getPoint()));
    } else if (pointCount == 2) {
        pointCount = 0;
        moveCommand.setNextPoint(View.mapPoint(event.getPoint()));
        drawingPanel.removeMouseListener(this);
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        undoManager.beginCommand(moveCommand);
        undoManager.endCommand(moveCommand);
      }
    }
  }
}
