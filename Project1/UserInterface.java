import java.util.*;
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
  private static final int DISPLAY_CART = 9;
  private static final int ADD_TO_CART = 10;
  private static final int EMPTY_CART = 11;
  private static final int PLACE_ORDER = 12;
  private static final int SHOW_ORDERS = 13;
  private static final int WAITLIST_ITEM = 14;
  private static final int HELP = 15;

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
    System.out.println(SHOW_CLIENTS + " to display all clients");
    System.out.println(SHOW_SUPPLIERS + " to display all suppliers");
    System.out.println(SHOW_PRODUCTS + " to display all products");
    System.out.println(DISPLAY_CART + " to display all products in a client's shopping cart");
    System.out.println(ADD_TO_CART + " to add products to a client's shopping cart");
    System.out.println(EMPTY_CART + " to remove all products from a client's shopping cart");
    System.out.println(PLACE_ORDER + " to place an order");
    System.out.println(SHOW_ORDERS + " to display all orders");
    System.out.println(HELP + " for help");
  }

  public void addClient() {
    String firstName = getToken("Enter client's first name");
    String lastName = getToken("Enter client's last name");
    String address = getToken("Enter address");

    Client result = warehouse.addClient(firstName, lastName, address);

    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println(result);
  }

  public void waitlistItem() {
      String clientId = getToken("Enter existing client id");
      String productId = getToken("Enter exising product id");
      int quantity = getInt("Enter quantity to waitlist");

      boolean result = warehouse.waitlistItem(clientId, productId, quantity);
      if ( result ) {
          System.out.println("Successfully waitlisted items");
      } else {
          System.out.println("Could not waitlist item");

      }
  }

  public void addSupplier() {
    String name = getToken("Enter supplier name");

    Supplier result = warehouse.addSupplier(name);

    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println(result);
  }

  public void addProducts() {
    Product result;
    do {
      String name = getToken("Enter name");
      int quantity = getInt("Enter quantity");
      double salePrice = getDouble("Enter Sale Price");
      double supplyPrice = getDouble("Enter Supply Price");

      result = warehouse.addProduct(name, quantity, salePrice, supplyPrice);
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

  public void addToCart() {
    Client client;
    Product product;

    String clientId = getToken("Enter client id to add to their shopping cart");
    client = warehouse.getClientById(clientId);
    if (client != null) {
      System.out.println("Client found:");
      System.out.println(client);
      do {
        String productId = getToken("Enter product id");
        product = warehouse.getProductById(productId);
        if(product != null) {
          System.out.println("Product found:");
          System.out.println(product);
          int productQuantity = getInt("Enter enter quantity");
          warehouse.addToCart(clientId, product, productQuantity);
        } else {
          System.out.println("Could not find that product id");
        }
        if (!yesOrNo("Add another product to the shopping cart?")) {
          break;
        }
      } while (true);
    } else {
      System.out.println("Could not find that client id");
    }
  }

  public void displayCart() {
    Client client;

    String clientId = getToken("Enter client id to view to their shopping cart");
    client = warehouse.getClientById(clientId);
    if (client != null) {
      System.out.println("Client found:");
      System.out.println(client);
      System.out.println("Shopping Cart:");
      warehouse.displayCart(clientId);
    } else {
      System.out.println("Could not find that client id");
    }
  }

  public void emptyCart() {
    Client client;

    String clientId = getToken("Enter client id to empty to their shopping cart");
    client = warehouse.getClientById(clientId);
    if (client != null) {
      System.out.println("Client found:");
      System.out.println(client);
      if(!yesOrNo("Are you sure you wish to empty the shopping cart?")) {
        warehouse.emptyCart(clientId);
        System.out.println("Shopping Cart has been emptied");
      } else {
        System.out.println("Canceled, shopping cart was not emptied");
      }
    } else {
      System.out.println("Could not find that client id");
    }
  }

  public void placeOrder() {
    Client client;

    String clientId = getToken("Enter client id to place an order");
    client = warehouse.getClientById(clientId);
    if (client != null) {
      System.out.println("Client found:");
      System.out.println(client);
      
      //ensure the cart is not empty
      Iterator<Product> cartIterator = client.getShoppingCart().getShoppingCartProducts();
      if (cartIterator.hasNext()) {
        if(yesOrNo("Are you sure you wish to place an order?")) {
          if(warehouse.placeOrder(clientId)) {
            System.out.println("Order placed and shopping cart has been emptied");
          } else {
            System.out.println("Unable to place order");
          }
          } else {
            System.out.println("Canceled, order was not placed");
          }
        } else {
          System.out.println("Shopping cart is empty, unable to place order");
        }
    } else {
      System.out.println("Could not find that client id");
    }
  }

  public void showOrders() {
    Iterator<Order> allOrders = warehouse.getOrders();

    while (allOrders.hasNext()){
      Order order = allOrders.next();
      System.out.println(order.toString());
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
        case DISPLAY_CART:
          displayCart();
          break;
        case ADD_TO_CART:
          addToCart();
          break;
        case EMPTY_CART:
          emptyCart();
          break;
        case PLACE_ORDER:
          placeOrder();
          break;
        case SHOW_ORDERS:
          showOrders();
          break;
        case WAITLIST_ITEM:
          waitlistItem();
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
