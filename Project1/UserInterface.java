import java.util.*;
import java.io.*;


public class UserInterface {
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;

  enum Actions {
    EXIT,
    ADD_CLIENT,
    EDIT_CLIENT,
    ADD_SUPPLIER,
    ADD_PRODUCTS,
    EDIT_PRODUCTS,
    SHOW_CLIENTS,
    SHOW_SUPPLIERS,
    SHOW_PRODUCTS,
    DISPLAY_CART,
    ADD_TO_CART,
    EMPTY_CART,
    PLACE_ORDER,
    SHOW_ORDERS,
    SHOW_INVOICES,
    WAITLIST_ITEM,
    SHOW_WAITLIST,
    SHOW_BALANCE,
    SHOW_OUTSTANDING,
    SHOW_PRODUCTS_WAITLIST,
    MAKE_PAYMENT,
    SHOW_TRANSACTIONS,
    EDIT_SHOPPING_CART,
    SHOW_INVENTORY,
    RECEIVE_SHIPMENT,
    SAVE,
    HELP,
  }

  private UserInterface() {
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
  }

  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
  }

  public void help() {
    System.out.println("Enter a number between " + Actions.EXIT.ordinal() + " and " + Actions.HELP.ordinal() + " as explained below:");
    System.out.println(Actions.EXIT.ordinal() + " to Exit\n");
    System.out.println(Actions.ADD_CLIENT.ordinal() + " to add a client");
    System.out.println(Actions.EDIT_CLIENT.ordinal() + " to edit a client");
    System.out.println(Actions.ADD_SUPPLIER.ordinal() + " to add a supplier");
    System.out.println(Actions.ADD_PRODUCTS.ordinal() + " to add products");
    System.out.println(Actions.EDIT_PRODUCTS.ordinal() + " to edit products");
    System.out.println(Actions.SHOW_CLIENTS.ordinal() + " to display all clients");
    System.out.println(Actions.SHOW_SUPPLIERS.ordinal() + " to display all suppliers");
    System.out.println(Actions.SHOW_PRODUCTS.ordinal() + " to display all products");
    System.out.println(Actions.DISPLAY_CART.ordinal() + " to display all products in a client's shopping cart");
    System.out.println(Actions.ADD_TO_CART.ordinal() + " to add products to a client's shopping cart");
    System.out.println(Actions.EMPTY_CART.ordinal() + " to remove all products from a client's shopping cart");
    System.out.println(Actions.PLACE_ORDER.ordinal() + " to place an order");
    System.out.println(Actions.SHOW_ORDERS.ordinal() + " to display all orders");
    System.out.println(Actions.SHOW_INVOICES.ordinal() + " to display all invoices");
    System.out.println(Actions.WAITLIST_ITEM.ordinal() + " to add to the waitlist");
    System.out.println(Actions.SHOW_WAITLIST.ordinal() + " to display the waitlist");
    System.out.println(Actions.SHOW_BALANCE.ordinal() + " to display a client's balance");
    System.out.println(Actions.MAKE_PAYMENT.ordinal() + " to add to a client's balance");
    System.out.println(Actions.SHOW_TRANSACTIONS.ordinal() + " to display a list of a client's transactions");
    System.out.println(Actions.SHOW_OUTSTANDING.ordinal() + " to display all oustanding balances");
    System.out.println(Actions.SHOW_PRODUCTS_WAITLIST.ordinal() + " to display products, stock, and waitlist amt");
    System.out.println(Actions.EDIT_SHOPPING_CART.ordinal() + " to edit the shopping cart");
    System.out.println(Actions.SHOW_INVENTORY.ordinal() + " to view the warehouse's inventory");
    System.out.println(Actions.RECEIVE_SHIPMENT.ordinal() + " to receive a shipment");
    System.out.println(Actions.SAVE.ordinal() + " to save the current state of the warehouse");
    System.out.println(Actions.HELP.ordinal() + " for help");
  }

  private void save() {
    if (warehouse.save()) {
      System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
    } else {
      System.out.println(" There has been an error in saving \n" );
    }
  }

  private void retrieve() {
    try {
      Warehouse tempWarehouse = Warehouse.retrieve();
      if (tempWarehouse != null) {
        System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnt exist; creating new warehouse" );
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
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

  public Actions getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + Actions.HELP.ordinal() + " for help"));
        for ( Actions action : Actions.values() ) {
          if ( value == action.ordinal() ) {
            return action;
          }
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
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

  public void showWaitlist() {
    Iterator<WaitItem> waitlist = warehouse.getWaitlist();

    while (waitlist.hasNext()){
      WaitItem item = waitlist.next();
      System.out.println(item.toString());
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
        System.out.println("Shopping Cart Total: $" + client.getShoppingCart().getTotalPrice());
        if(yesOrNo("Are you sure you wish to place an order?")) {
          if(warehouse.placeOrder(clientId)) {
            System.out.println("Order placed, total price charged to client's balance,");
            System.out.println("invoice generated, and shopping cart has been emptied.");
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

  public void showBalance() {
    Client result;
    String id = getToken("Enter client id to see balance");

    result = warehouse.getClientById(id);
    if (result != null) {
      System.out.println("Current Balance: $" + result.getBalance());
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

  public void showInvoices() {
    Iterator<Invoice> allInvoices = warehouse.getInvoices();

    while (allInvoices.hasNext()){
      Invoice invoice = allInvoices.next();
      System.out.println(invoice.toString());
    }
  }

  public void processPayment() {
    Client client;

    String clientId = getToken("Enter client id to make a payment");
    client = warehouse.getClientById(clientId);
    if (client != null) {      
      Double paymentAmount = getDouble("Enter payment amount");
      if(warehouse.makePayment(clientId, paymentAmount)) {
        System.out.println("Payment Successful, new balance: " + client.getBalance());
      }
    } else {
      System.out.println("Could not find that client id");
    }
  }

  public void showOutstanding() {
    Iterator<Client> allClients = warehouse.getClients();
    while (allClients.hasNext()){
      Client temp = allClients.next();
      if (temp.getBalance() < 0){
         System.out.println(temp.toString());
      }
    }
  }

  public void showProductsWaitlist() {
    int amt = 0;
    Iterator<Product> allProducts = warehouse.getProducts();
    while(allProducts.hasNext()) {
      Product tempProduct = allProducts.next();
      Iterator<WaitItem> waitList = warehouse.getWaitlist();
      while(waitList.hasNext()) {
        WaitItem tempWaitItem = waitList.next();
        if(tempProduct == tempWaitItem.getProduct()) {
          amt += tempWaitItem.getQuantity();
        }
      }
      System.out.println(tempProduct.toString() + " " + amt);
      amt = 0;
    }
  }

  public void showManufacturerAndPrice() {
    Client client;

    String clientId = getToken("Enter client id to make a payment");
    client = warehouse.getClientById(clientId);
    if (client != null) {      
      Double paymentAmount = getDouble("Enter payment amount");
      if(warehouse.makePayment(clientId, paymentAmount)) {
        System.out.println("Payment Successful, new balance: " + client.getBalance());
      }
    } else {
      System.out.println("Could not find that client id");
    }
  }

  public void showTransactions() {
    Client client;
    String clientId = getToken("Enter client id to see transactions");
    client = warehouse.getClientById(clientId);
    if (client != null) {
      System.out.println("Transaction List: ");
      Iterator<Transaction> transactions = warehouse.getTransactions(clientId);
      while (transactions.hasNext()){
        System.out.println(transactions.next().toString());
    }
    } else {
      System.out.println("Could not find that client id");
    }
  }

  public void editShoppingCart() {
    String clientId = getToken("Enter client id to edit shopping cart");
    Client client = warehouse.getClientById(clientId);
    Boolean done = false;
    if (client == null) {
      System.out.println("Client does not exist");
      return;
    }


    ShoppingCart cart = client.getShoppingCart();
    while (!done) {
      System.out.println("Shopping Cart:");
      System.out.println(cart.toString());
      String productId = getToken("Enter Product ID in cart to edit");
      Iterator<Product> cartIter = cart.getShoppingCartProducts();

      // find the product in in the shopping cart
      Product p = null;
      while ( cartIter.hasNext() ) {
        Product next = cartIter.next();
        if (cartIter.next().getId() == productId) {
          p = next;
          break;
        }
      }

      if ( p == null ) {
        done = !yesOrNo("That ID was not found in the shoping cart? Continue?");
      } else {
        int newQuantity = getInt("Enter the desired amount to put in your shopping cart.");
        p.setQuantity(newQuantity);
        done = !yesOrNo("Would you like to edit more items in your cart?");
      }
    }
  }
  public void showInventory() {
    Iterator<Product> inventoryIterator = warehouse.getInventory();
    while (inventoryIterator.hasNext()){
      System.out.println(inventoryIterator.next().toString());
    }
  }

  public void recieveShipment() {
    Product p;
    do {
      String productId = getToken("Enter productId");
      p = warehouse.getProductById(productId);
      if(p != null) {
        int quantity = getInt("Enter quantity");

        //check for waitlisted orders
        List<WaitItem> waitlistedOrders = warehouse.getWaitItemsByProductId(productId);
        Iterator<WaitItem> waitlistedOrdersIterator = waitlistedOrders.iterator();

        while(waitlistedOrdersIterator.hasNext()) {
          WaitItem waitItem = waitlistedOrdersIterator.next();
          System.out.println("Waitlisted Order found for provided product:");
          System.out.println(waitItem.toString());
          if(yesOrNo("Fill waitlisted order?")) {
            quantity -= waitItem.getQuantity();
            waitItem.setOrderFilled(true);
            System.out.println("Order filled.");
          } else {
            System.out.println("Order was not filled.");
          }
        }
        // add remaining product to inventory
        warehouse.addToInventory(productId, quantity);
      } else {
        System.out.println("Product not found");
      }
      if (!yesOrNo("Receive another product?")) {
        break;
      }
    } while(true);
  }

  public void process() {
    Actions command;
    help();
    while ((command = getCommand()) != Actions.EXIT) {
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
        case SHOW_INVOICES:
          showInvoices();
          break;
        case WAITLIST_ITEM:
          waitlistItem();
          break;
        case SHOW_WAITLIST:
          showWaitlist();
          break;
        case SHOW_BALANCE:
          showBalance();
          break;
        case SHOW_OUTSTANDING:
          showOutstanding();
          break;
        case SHOW_PRODUCTS_WAITLIST:
          showProductsWaitlist();
          break;
        case MAKE_PAYMENT:
          processPayment();
          break;
        case SHOW_TRANSACTIONS:
          showTransactions();
          break;
        case SHOW_INVENTORY:
          showInventory();
          break;
        case RECEIVE_SHIPMENT:
          recieveShipment();
          break;
        case SAVE:
          save();
          break;
        case EDIT_SHOPPING_CART:
          editShoppingCart();
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
