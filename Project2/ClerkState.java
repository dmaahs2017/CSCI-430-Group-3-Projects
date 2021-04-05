import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;
public class ClerkState extends WareState {
  private static ClerkState clerkState = new ClerkState();
  private static Warehouse warehouse;

  enum Operations {
    Exit,
    AddClient,
    QueryClients,
    ShowProducts,
    BecomeClient,
    DisplayProductWaitlist,
    RecieveShipment,
    Help
  }

  private ClerkState() {
    warehouse = Warehouse.instance();
  }

  public static ClerkState instance() {
      return clerkState;
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
    System.out.println(Operations.AddClient.ordinal() + " to add a new client");
    System.out.println(Operations.QueryClients.ordinal() + " to query clients");
    System.out.println(Operations.ShowProducts.ordinal() + " to show products");
    System.out.println(Operations.BecomeClient.ordinal() + " to become a specific client, gives access to client operations");
    System.out.println(Operations.DisplayProductWaitlist.ordinal() + " to display waitlist for a product");
    System.out.println(Operations.RecieveShipment.ordinal() + " to recieve a shipment");
    System.out.println(Operations.Exit.ordinal() + " to logout");
  }

  public void addClient() {
    String firstName = InputUtils.getToken("Enter client's first name");
    String lastName = InputUtils.getToken("Enter client's last name");
    String address = InputUtils.getToken("Enter address");

    Client result = warehouse.addClient(firstName, lastName, address);

    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println(result);
  }

  public void showProducts() {
      Iterator<Product> allProducts = warehouse.getProducts();

      while (allProducts.hasNext()){
        Product product = allProducts.next();
        System.out.println(product.toString());
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

  public void recieveShipment() {
    Product p;
    do {
      String productId = InputUtils.getToken("Enter productId");
      p = warehouse.getProductById(productId);
      if(p != null) {
        int quantity = InputUtils.getNumber("Enter quantity");

        //check for waitlisted orders
        List<WaitItem> waitlistedOrders = warehouse.getWaitItemsByProductId(productId);
        Iterator<WaitItem> waitlistedOrdersIterator = waitlistedOrders.iterator();

        while(waitlistedOrdersIterator.hasNext()) {
          WaitItem waitItem = waitlistedOrdersIterator.next();
          System.out.println("Waitlisted Order found for provided product:");
          System.out.println(waitItem.toString());
          if(InputUtils.yesOrNo("Fill waitlisted order?")) {
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
      if (!InputUtils.yesOrNo("Receive another product?")) {
        break;
      }
    } while(true);
  }

  private void becomeClient() {
    String user = InputUtils.getToken("Please input the client id: ");
    if (Warehouse.instance().getClientById(user) != null){
      (WareContext.instance()).setUser(user.toString());      
      (WareContext.instance()).changeState(1);
    } else {
      System.out.println("Invalid client id.");
    }
  }

  public void queryClientsTransition() {
      WareContext.instance().changeState(4); // Transition to ClientQueryState
  }

  public void process() {
    Operations command;
    help();
    while ((command = getCommand()) != Operations.Exit) {
      switch (command) {
        case Help:
          help();
          break;
        case AddClient:
          addClient();
          break;
        case QueryClients:
          queryClientsTransition();
          break;
        case ShowProducts:
          showProducts();
          break;
        case BecomeClient:
          becomeClient();
          break;
        case DisplayProductWaitlist:
          showProductsWaitlist();
          break;
        case RecieveShipment:
          recieveShipment();
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
    if (WareContext.instance().getLogin() == WareContext.IsClerk)
       {  //system.out.println(" going to login \n");
        (WareContext.instance()).changeState(0); // exit to login with a code 0
       }
    else if (WareContext.instance().getLogin() == WareContext.IsManager)
       {  //system.out.println(" going to manager \n");
        (WareContext.instance()).changeState(2); // exit to manager with a code 0
       }
    else 
       (WareContext.instance()).changeState(3); // exit code 3, indicates error
  }
 
}
