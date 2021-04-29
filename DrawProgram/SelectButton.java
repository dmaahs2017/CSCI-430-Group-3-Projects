import javax.swing.*;
import java.awt.event.*;
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
    selectCommand = new SelectCommand();
    drawingPanel.addMouseListener(mouseHandler);
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
    }
  }
}
