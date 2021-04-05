import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;
public class ClientState extends WareState {
  private static ClientState clientState;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static final int EXIT = 0;
  private static final int SHOW_CLIENT_DETAILS = 1;
  private static final int SHOW_TRANSACTIONS = 2;
  private static final int SHOW_WAITLIST = 3;
  private static final int SHOW_PRODUCTS = 4;
  private static final int MANAGE_CART = 5;
  private static final int PLACE_ORDER = 6;
  private static final int HELP = 7;
  private ClientState() {
    warehouse = Warehouse.instance();
  }

  public static ClientState instance() {
    if (clientState == null) {
      return clientState = new ClientState();
    } else {
      return clientState;
    }
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(InputUtils.getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("\nEnter a number between " + EXIT + " and " + HELP + " as explained below:");
    System.out.println(SHOW_CLIENT_DETAILS + " to view your client details");
    System.out.println(SHOW_TRANSACTIONS + " to view your transactions");
    System.out.println(SHOW_WAITLIST + " to view your waitlisted items");
    System.out.println(SHOW_PRODUCTS + " to view available products and prices");
    System.out.println(MANAGE_CART + " to view, edit, and add products to your cart");
    System.out.println(PLACE_ORDER + " to place order");
    System.out.println("\n" + HELP + " for help");
    System.out.println(EXIT + " to logout");
  }

  public void showClientDetails() {
    String id = WareContext.instance().getUser();
    Client client = warehouse.getClientById(id);
    System.out.println(client.toString());
  }

  public void showTransactions() {
    System.out.println("\n  List of Transactions:\n");
    Iterator<Transaction> result;
    String clientID = WareContext.instance().getUser();
    //Calendar date  = getDate("Please enter the date for which you want records as mm/dd/yy");
    result = warehouse.getTransactions(clientID);
    if (result == null) {
      System.out.println("Invalid Client ID");
    } else {
      while(result.hasNext()) {
        Transaction transaction = (Transaction) result.next();
        System.out.println(transaction.getDescription() + ", Date: " + transaction.getDate() + ", Total Cost: $" + transaction.getAmount());
      }
      System.out.println("\n  There are no more transactions. \n" );
    }
  }

  public void showWaitlist() {
    System.out.println("\n  Waitlist:\n");
    String id = WareContext.instance().getUser();
    Client client = warehouse.getClientById(id);
    Iterator<WaitItem> waitList = warehouse.getWaitlist();
    while(waitList.hasNext()) {
      WaitItem tempWaitItem = waitList.next();
      if(client.equals(tempWaitItem.getClient().getClientId())) {
        System.out.println(tempWaitItem.toString() + "\n");
      }
    }
    System.out.println("  There are no more waitlisted items. \n");
  }

  public void showProducts() {
    System.out.println("\n  List of available products:\n");
    Iterator<InventoryItem> inventory = warehouse.getInventory();
    while(inventory.hasNext()) {
      InventoryItem tempItem = inventory.next();
      if (tempItem.getQuantity() > 0) {
        System.out.println("Product info: id: " + tempItem.getProduct().getId() + ", name: " + tempItem.getProduct().getName()
          + ", sale price: $" + tempItem.getProduct().getSalePrice() + ", quantity in stock: " + tempItem.getQuantity());
      }
    }
    System.out.println("\n  There are no more products in the inventory. \n");
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

  public void manageCart() {
     WareContext.instance().changeState(4); // Transition to ShoppingCartState
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

  public void placeOrder() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);

    Iterator<ShoppingCartItem> cartIterator = client.getShoppingCart().getShoppingCartProducts();
    if (cartIterator.hasNext()) {
    viewCart();
    System.out.println("Shopping Cart Total: $" + client.getShoppingCart().getTotalPrice());
      if(InputUtils.yesOrNo("Are you sure you wish to place an order?")) {
        if(warehouse.placeOrder(clientId)) {
          System.out.println("Order placed: total price charged to your balance,");
          System.out.println("shopping cart has been emptied, and invoice generated.");
        } else {
          System.out.println("Unable to place order");
        }
        } else {
          System.out.println("Order Canceled");
        }
    } else {
        System.out.println("Shopping cart is empty, unable to place the order");
    }
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case HELP:
          help();
          break;
        case SHOW_CLIENT_DETAILS:
          showClientDetails();
          break;
        case SHOW_TRANSACTIONS:
          showTransactions();
          break;
        case SHOW_WAITLIST:
          showWaitlist();
          break;
        case SHOW_PRODUCTS:
          showProducts();
          break;
        case MANAGE_CART:
          manageCart();
          break;
        case PLACE_ORDER:
          placeOrder();
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
    if ((WareContext.instance()).getLogin() == WareContext.IsClient)
       { //system.out.println(" going to login \n ");
         (WareContext.instance()).changeState(0); // exit to login with a code 0
        }
    else if (WareContext.instance().getLogin() == WareContext.IsClerk)
       {  //system.out.println(" going to clerk \n");
        (WareContext.instance()).changeState(1); // exit to clerk with a code 0
       }
    else if (WareContext.instance().getLogin() == WareContext.IsManager)
       {  //system.out.println(" going to manager \n");
        (WareContext.instance()).changeState(2); // exit to manager with a code 0
       }
    else 
       (WareContext.instance()).changeState(3); // exit code 3, indicates error
  }
 
}
