package backend;
import java.util.*;
import java.io.*;
public class Inventory implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<InventoryItem> inventoryList = new LinkedList<InventoryItem>();
  private static Inventory inventory;
  private Inventory() {
  }
  public static Inventory instance() {
    if (inventory == null) {
      return (inventory = new Inventory());
    } else {
      return inventory;
    }
  }

  public boolean addToInventory(Product product, int quantity) {
    InventoryItem item = new InventoryItem(product, quantity);
    inventoryList.add(item);
    return true;
  }

  public Iterator<InventoryItem> getInventory(){
     return inventoryList.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(inventory);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (inventory != null) {
        return;
      } else {
        input.defaultReadObject();
        if (inventory == null) {
          inventory = (Inventory) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  public String toString() {
    return inventoryList.toString();
  }
}
