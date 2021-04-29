import java.util.*;
class DeleteCommand extends Command {
  private Vector itemList;
  public DeleteCommand () {
    itemList = new Vector();
    Enumeration enumeration = model.getSelectedItems();
    while (enumeration.hasMoreElements()) {
      Item item = (Item)(enumeration.nextElement());
      itemList.add(item);
    }
    model.deleteSelectedItems();
  }
  public boolean undo() {
    Enumeration enumeration = itemList.elements();
    while (enumeration.hasMoreElements()) {
      Item item = (Item)(enumeration.nextElement());
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
    model.deleteSelectedItems();
  }
}
