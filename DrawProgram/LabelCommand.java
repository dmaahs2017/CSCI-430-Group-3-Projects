import java.awt.*;
import java.text.*;
public class LabelCommand extends Command {
  private Label label;
  public LabelCommand(Point point) {
    label = new Label(point);
  }
  public void addCharacter(char character) {
    label.addCharacter(character);
    model.setChanged();
  }
  public void removeCharacter() {
    label.removeCharacter();
    model.setChanged();
  }
  public void execute() {
    model.addItem(label);
  }
  public boolean undo() {
    model.removeItem(label);
    return true;
  }
  public boolean redo() {
    execute();
    return true;
  }
}
