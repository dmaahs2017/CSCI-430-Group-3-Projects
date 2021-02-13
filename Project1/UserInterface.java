import java.util.*;
import java.text.*;
import java.io.*;
public class UserInterface {
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;

  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int EDIT_CLIENT = 2;
  private static final int ADD_SUPPLIER = 3;
  private static final int ADD_PRODUCTS = 4;
  private static final int EDIT_PRODUCTS = 5;
  private static final int SHOW_CLIENTS = 6;
  private static final int SHOW_SUPPLIERS = 7;
  private static final int SHOW_PRODUCTS = 8;
  private static final int HELP = 9;

  private UserInterface() {
    warehouse = Warehouse.instance();
  }

  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
  }

  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }

  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }

  public int getInt(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.parseInt(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }

  public double getDouble(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Double num = Double.parseDouble(item);
        return num.doubleValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(EDIT_CLIENT + " to edit a client");
    System.out.println(ADD_SUPPLIER + " to add a supplier");
    System.out.println(ADD_PRODUCTS + " to add products");
    System.out.println(EDIT_PRODUCTS + " to edit products");
    System.out.println(SHOW_CLIENTS + " to dislpay all clients");
    System.out.println(SHOW_SUPPLIERS + " to dislpay all suppliers");
    System.out.println(SHOW_PRODUCTS + " to dislpay all products");
    System.out.println(HELP + " for help");
  }

  public void addClient() {
    String id = getToken("Enter new client id");
    String firstName = getToken("Enter client's first name");
    String lastName = getToken("Enter client's last name");
    String address = getToken("Enter address");

    Client result = warehouse.addClient(id, firstName, lastName, address);

    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println(result);
  }

  public void addSupplier() {
    String id = getToken("Enter new supplier id");
    String name = getToken("Enter supplier name");

    Supplier result = warehouse.addSupplier(name, id);

    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println(result);
  }

  public void addProducts() {
    Product result;
    do {
      String id = getToken("Enter id");
      String name = getToken("Enter name");
      int quantity = getInt("Enter quantity");
      double salePrice = getDouble("Enter Sale Price");
      double supplyPrice = getDouble("Enter Supply Price");

      result = warehouse.addProduct(id, name, quantity, salePrice, supplyPrice);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Product could not be added");
      }
      if (!yesOrNo("Add more products?")) {
        break;
      }
    } while (true);
  }

  public void editClient() {
      Client result;
      String id = getToken("Enter client id to edit");

      result = warehouse.getClientById(id);
      if (result != null) {
        System.out.println(result);
        if (yesOrNo("Edit name?")) {
          String fname = getToken("Enter new first name");
          String lname = getToken("Enter new last name");
          result.setFirstName(fname);
          result.setLastName(lname);
        }
        if (yesOrNo("Edit Address?")) {
          String address = getToken("Enter new address");
          result.setAddress(address);
        }
      } else {
        System.out.println("Could not find that client id");
      }
  }

  public void editProducts() {
    Product result;
    do {
      String id = getToken("Enter product id to edit");

      result = warehouse.getProductById(id);
      if (result != null) {
        System.out.println(result);
        if (yesOrNo("Edit name?")) {
          String name = getToken("Enter new product name");
          result.setName(name);
        }
        if (yesOrNo("Edit Sale Price")) {
          double price = getDouble("Enter new sale price");
          result.setSalePrice(price);
        }
        if (yesOrNo("Edit Supply Price")) {
          double price = getDouble("Enter new supply price");
          result.setSupplyPrice(price);
        }

      } else {
        System.out.println("Could not find that product id");
      }
      if (!yesOrNo("Edit more products?")) {
        break;
      }
    } while (true);
  }


  public void showClients() {
      Iterator<Client> allClients = warehouse.getClients();

      while (allClients.hasNext()){
        Client client = allClients.next();
        System.out.println(client.toString());
      }
  }

  public void showSuppliers() {
      Iterator<Supplier> allSuppliers = warehouse.getSuppliers();

      while (allSuppliers.hasNext()){
        Supplier supplier = allSuppliers.next();
        System.out.println(supplier.toString());
      }
  }

  public void showProducts() {
      Iterator<Product> allProducts = warehouse.getProducts();

      while (allProducts.hasNext()){
        Product product = allProducts.next();
        System.out.println(product.toString());
      }
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_CLIENT:
          addClient();
          break;
        case EDIT_CLIENT:
          editClient();
          break;
        case ADD_SUPPLIER: 
          addSupplier();
          break;
        case ADD_PRODUCTS: 
          addProducts();
          break;
        case EDIT_PRODUCTS:
          editProducts();
          break;
        case SHOW_CLIENTS:
          showClients();
          break;
        case SHOW_SUPPLIERS:
          showSuppliers();
          break;
        case SHOW_PRODUCTS:
          showProducts();
          break;
        case HELP:
          help();
          break;
      }
    }
  }

  public static void main(String[] s) {
      UserInterface.instance().process();
  }
}
