import java.util.*;
import java.awt.*;
class MoveCommand extends Command {
  private Vector<Item> originalItemList;
  private Point p1, p2;
  private int pointCounter;

  public MoveCommand(Point p) {
    this();
    p1 = p;
    ++pointCounter;
  }

  public MoveCommand () {
    pointCounter = 0;
    p1 = p2 = null;
    originalItemList = new Vector<Item>();
    Enumeration<Item> enumeration = model.getSelectedItems();
    while (enumeration.hasMoreElements()) {
      Item item = enumeration.nextElement();
      originalItemList.add(item);
    }
  }

  public void setNextPoint(Point p) {
    ++pointCounter;
    if (pointCounter == 1) {
      p1 = p;
    } else if (pointCounter == 2) {
      p2 = p;
    }
  }

  public boolean undo() {
    Enumeration<Item> enumeration = originalItemList.elements();
    while (enumeration.hasMoreElements()) {
      Item item = enumeration.nextElement();
      model.addItem(item);
      model.markSelected(item);
    }
    return true;
  }
  public boolean redo() {
    execute();
    return true;
  }
  public void execute() {
    for (Item item : originalItemList) {
      item.translate((int) p2.getX() - (int) p1.getX(), (int) p2.getY() - (int) p1.getY());
    }
  }
}
