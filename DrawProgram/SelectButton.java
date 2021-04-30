import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class SelectButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private SelectCommand selectCommand;
  private UndoManager undoManager;
  public SelectButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Select");
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    this.undoManager = undoManager;
    mouseHandler = new MouseHandler();
  }
  public void actionPerformed(ActionEvent event) {
    view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    drawingPanel.addMouseListener(mouseHandler);
    selectCommand = new SelectCommand();
    undoManager.beginCommand(selectCommand);
  }
  private class MouseHandler extends MouseAdapter {
    public void mouseClicked(MouseEvent event) {
       if (selectCommand.setPoint(View.mapPoint(event.getPoint())))
	   {
		drawingPanel.removeMouseListener(this);
		undoManager.endCommand(selectCommand);
	   }
	   else {
		   undoManager.cancelCommand();
	   }
      view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }
}
