import java.util.*;
public class UndoManager {
  private static Model model;
  private Stack history;
  private Stack redoStack;
  private Command currentCommand;
  public UndoManager() {
    history = new Stack();
    redoStack = new Stack();
  }
  public static void setModel(Model model) {
    UndoManager.model = model;
  }
  public void beginCommand(Command command) {
    if (currentCommand != null) {
      if (currentCommand.end()) {
        history.push(currentCommand);
      }
    }
    currentCommand = command;
    redoStack.clear();
    command.execute();
  }
  public void endCommand(Command command) {
    command.end();
    history.push(command);
    currentCommand = null;
    model.setChanged();
  }
  
  public void cancelCommand() {
    currentCommand = null;
    model.setChanged();
  }
  
  public void undo() {
    if (!(history.empty())) {
      Command command = (Command) (history.peek());
      if (command.undo()) {
        history.pop();
        redoStack.push(command);
      }
    }
  }
  public void redo() {
    if (!(redoStack.empty())) {
      Command command = (Command)(redoStack.peek());
      if (command.redo()) {
        redoStack.pop();
        history.push(command);
      }
    }
  }
}