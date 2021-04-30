import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class LineButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private LineCommand lineCommand;
  private UndoManager undoManager;
  public LineButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Line");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    mouseHandler = new MouseHandler();
  }
  public void actionPerformed(ActionEvent event) {
    view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    // Change cursor when button is clicked
    drawingPanel.addMouseListener(mouseHandler);
  // Start listening for mouseclicks on the drawing panel
  }
  private class MouseHandler extends MouseAdapter {
    private int pointCount = 0;
    public void mouseClicked(MouseEvent event) {
    if (++pointCount == 1) {
        lineCommand = new LineCommand(View.mapPoint(event.getPoint()));
        undoManager.beginCommand(lineCommand);
    } else if (pointCount == 2) {
        pointCount = 0;
        lineCommand.setLinePoint(View.mapPoint(event.getPoint()));
        drawingPanel.removeMouseListener(this);
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        undoManager.endCommand(lineCommand);
      }
    }
  }
}