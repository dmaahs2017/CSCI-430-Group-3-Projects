import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;
public class ManagerState extends WareState {
  private static ManagerState managerState = new ManagerState();
  private static Warehouse warehouse;

  enum Operations {
    Exit,
    ADD_SUPPLIER,
    ADD_PRODUCTS,
    SHOW_SUPPLIERS,
    SHOW_SUPPLIERS_FOR_PRODUCT,
    SHOW_PRODUCTS_FOR_SUPPLIER,
    BECOME_SALESCLERK,
    Help
  }

  private ManagerState() {
    warehouse = Warehouse.instance();
  }

  public static ManagerState instance() {
      return managerState;
  }

  public Operations getCommand() {
    do {
      try {
        int value = Integer.parseInt(InputUtils.getToken("Enter command:" + Operations.Help.ordinal() + " for help"));
        for ( Operations op : Operations.values() ) {
          if ( value == op.ordinal() ) {
            return op;
          }
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("\nEnter a number between " + Operations.Exit + " and " + Operations.Help + " as explained below:");
    System.out.println(Operations.ADD_SUPPLIER.ordinal() + " to add a supplier");
    System.out.println(Operations.ADD_PRODUCTS.ordinal() + " to add products");
    System.out.println(Operations.SHOW_SUPPLIERS.ordinal() + " to display all suppliers");
    System.out.println(Operations.SHOW_SUPPLIERS_FOR_PRODUCT.ordinal() + " to display all suppliers for a product");
    System.out.println(Operations.SHOW_PRODUCTS_FOR_SUPPLIER.ordinal() + " to display all products from a supplier");
    System.out.println(Operations.BECOME_SALESCLERK.ordinal() + " to become a specific clerk, gives access to clerk operations");
    System.out.println(Operations.Exit.ordinal() + " to logout");
  }

  public void addSupplier() {
    String name = InputUtils.getToken("Enter supplier name");

    Supplier result = warehouse.addSupplier(name);

    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println(result);
  }

  public void addProducts() {
    Product result;
    do {
      String name = InputUtils.getToken("Enter name");
      int quantity = InputUtils.getInt("Enter quantity");
      double salePrice = InputUtils.getDouble("Enter Sale Price");
      double supplyPrice = InputUtils.getDouble("Enter Supply Price");

      result = warehouse.addProduct(name, quantity, salePrice, supplyPrice);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Product could not be added");
      }
      if (!InputUtils.yesOrNo("Add more products?")) {
        break;
      }
    } while (true);
  }

  public void showSuppliers() {
      Iterator<Supplier> allSuppliers = warehouse.getSuppliers();

      while (allSuppliers.hasNext()){
        Supplier supplier = allSuppliers.next();
        System.out.println(supplier.toString());
      }
  }

  public void showSuppliersForProduct() {
      Iterator<Supplier> allSuppliers = warehouse.getSuppliers();

      while (allSuppliers.hasNext()){
        Supplier supplier = allSuppliers.next();
        System.out.println(supplier.toString());
      }
  }

  public void showProductsForSupplier() {
      Iterator<Supplier> allSuppliers = warehouse.getSuppliers();

      while (allSuppliers.hasNext()){
        Supplier supplier = allSuppliers.next();
        System.out.println(supplier.toString());
      }
  }

  private void becomeClerk() {
    String user = InputUtils.getToken("Please input the clerk id: ");
    if (user.equals("clerk")) {
      (WareContext.instance()).setUser(user.toString());
      (WareContext.instance()).changeState(1);
    } else {
      System.out.println("Invalid clerk id.");
    }
  }

  public void process() {
    Operations command;
    help();
    while ((command = getCommand()) != Operations.Exit) {
      switch (command) {
        case ADD_PRODUCTS:
          addProducts();
          break;
        case ADD_SUPPLIER:
          addSupplier();
          break;
        case SHOW_SUPPLIERS:
          showSuppliers();
          break;
        case SHOW_SUPPLIERS_FOR_PRODUCT:
          showSuppliersForProduct();
          break;
        case SHOW_PRODUCTS_FOR_SUPPLIER:
          showProductsForSupplier();
          break;
        case BECOME_SALESCLERK:
          becomeClerk();
          break;
        case Help:
          help();
          break;
        default:
          System.out.println("Invalid choice");
      }
    }
    logout();
  }

  public void run() {
    process();
  }

  public void logout()
  {
    if (WareContext.instance().getLogin() == WareContext.IsManager)
       {  //system.out.println(" going to manager \n");
        (WareContext.instance()).changeState(0); // exit to login with a code 0
       }
    else
       (WareContext.instance()).changeState(3); // exit code 3, indicates error
  }
}
