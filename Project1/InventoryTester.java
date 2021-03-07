import java.util.*;
public class InventoryTester {
  
  public static void main(String[] s) {
     Product p1 = new Product("product1", 1, 1.99, 0.99);
     Product p2 = new Product("product2", 1, 4.99, 2.50);
     Inventory inventory = Inventory.instance();
     inventory.addToInventory(p1, 2);
     inventory.addToInventory(p2, 4);
     Iterator<Product> inventoryIterator = inventory.getInventory();
     System.out.println("List of inventory should include 2 of product1, and 4 of product2");
     while (inventoryIterator.hasNext()){
       System.out.println(inventoryIterator.next());
     }
  }
}
