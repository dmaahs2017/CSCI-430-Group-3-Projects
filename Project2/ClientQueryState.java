import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;
public class ClientQueryState extends WareState {
  private static ClientQueryState state;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;

  enum Operation {
    Exit,
    DisplayAllClients,
    DisplayClientsWithBalances,
    DisplayClientsWithNoTransactions,
    Help,
  }

  private ClientQueryState() {
    warehouse = Warehouse.instance();
  }

  public static ClientQueryState instance() {
    if (state == null) {
      return state = new ClientQueryState();
    } else {
      return state;
    }
  }

  public Operation getCommand() {
    do {
      try {
        int value = Integer.parseInt(InputUtils.getToken("Enter command:" + Operation.Help.ordinal() + " for help"));
        for ( Operation op : Operation.values() ) {
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
    System.out.println("\nEnter a number between " + Operation.Exit + " and " + Operation.Help + " as explained below:");
    System.out.println(Operation.DisplayAllClients.ordinal() + " to display all clients");
    System.out.println(Operation.DisplayClientsWithBalances.ordinal() + " to display clients with outstanding balances");
    System.out.println(Operation.DisplayClientsWithNoTransactions.ordinal() + " to display clients with no transactions");
    System.out.println(Operation.Exit.ordinal() + " to go back");
  }


  public void viewCart() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);
    System.out.println("\n  Shopping Cart Contents:\n");
    Iterator<ShoppingCartItem> cIterator = client.getShoppingCart().getShoppingCartProducts();
    while(cIterator.hasNext()) {
      ShoppingCartItem item = cIterator.next();
      System.out.println("Product id: " + item.getProduct().getId() + ", name: " + item.getProduct().getName() + 
        ", sale price: $" + item.getProduct().getSalePrice() + ", Quantity in cart: " + item.getQuantity());
    }
    System.out.println("\n  End of cart. \n" );
  }
  
  public void addToCart() {
    String clientId = WareContext.instance().getUser();
    do {
      String productId = InputUtils.getToken("Enter product id");
      Product product = warehouse.getProductById(productId);
      if(product != null) {
        System.out.println("Product found:");
        System.out.println("id:" + product.getId() + ", name: " + product.getName() + ", Sale Price: $" + product.getSalePrice() + "\n");
        int productQuantity = InputUtils.getNumber("Enter quantity");
        warehouse.addToCart(clientId, product, productQuantity);
      } else {
        System.out.println("Could not find that product id");
      }
      if (!InputUtils.yesOrNo("Add another product to the shopping cart?")) {
        break;
      }
    } while (true);
  }

  public void removeProduct() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);
    ShoppingCart cart = client.getShoppingCart();
    Boolean doneEditing = false;

    while (!doneEditing) {
      viewCart();
      String productId = InputUtils.getToken("Enter Product ID from cart to remove");

      boolean wasSuccessful = cart.removeItem(productId);
      if ( !wasSuccessful ) {
        doneEditing = !InputUtils.yesOrNo("That ID was not found in the shoping cart? Continue?");
      } else {
        System.out.println("Successfully Removed Product");
        doneEditing = !InputUtils.yesOrNo("Remove More Products?");
      }
    }
  }

  public void modifyCart() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);
    ShoppingCart cart = client.getShoppingCart();
    Boolean doneEditing = false;

    while (!doneEditing) {
      viewCart();
      String productId = InputUtils.getToken("Enter Product ID from cart to edit");

      // find the product in the shopping cart
      ShoppingCartItem item = null;
      Iterator<ShoppingCartItem> cartIter = cart.getShoppingCartProducts();
      while ( cartIter.hasNext() ) {
        ShoppingCartItem next = cartIter.next();
        if (next.getProduct().getId().equals(productId)) {
          item = next;
          break;
        }
      }

      if ( item == null ) {
        doneEditing = !InputUtils.yesOrNo("That ID was not found in the shoping cart? Continue?");
      } else {
        int newQuantity = InputUtils.getNumber("Enter the desired amount to put in your shopping cart.");

        item.setQuantity(newQuantity);
        doneEditing = !InputUtils.yesOrNo("Would you like to edit more items in your cart?");
      }
    }
  }
  
  public void showClients() {
      Iterator<Client> allClients = warehouse.getClients();

      while (allClients.hasNext()){
        Client client = allClients.next();
        System.out.println(client.toString());
      }
  }

  public void displayClientsWithBalances() {
    Iterator<Client> allClients = warehouse.getClients();

    while (allClients.hasNext()){
      Client client = allClients.next();
      if (client.getBalance() <= 0.0) {
        System.out.println("\t" + client);
      }
    }
  }

  public void displayClientsWithNoTransactions() {
    Iterator<Client> allClients = warehouse.getClients();

    while (allClients.hasNext()){
      Client client = allClients.next();
      if (client.getTransactionList().isEmpty()) {
        System.out.println("\t" + client);
      }
    }
  }

  public void process() {
    Operation command;
    help();
    while ((command = getCommand()) != Operation.Exit) {
      switch (command) {
        case Help:
          help();
          break;
        case DisplayAllClients:
          showClients();
          break;
        case DisplayClientsWithBalances:
          displayClientsWithBalances();
          break;
        case DisplayClientsWithNoTransactions:
          displayClientsWithNoTransactions();
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
     (WareContext.instance()).changeState(0); // -> Manager
  }
 
}
