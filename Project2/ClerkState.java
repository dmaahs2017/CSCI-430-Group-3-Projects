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
    ShowProducts,
    ShowClients,
    ShowClientsWithOutstandingBalance,
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
    System.out.println(Operations.AddClient.ordinal() + "Add a new client");
    System.out.println(Operations.ShowProducts.ordinal() + "Show products");
    System.out.println(Operations.ShowClients.ordinal() + "Show clients");
    System.out.println(Operations.ShowClientsWithOutstandingBalance.ordinal() + "Show clients and outstanding balance");
    System.out.println(Operations.BecomeClient.ordinal() + "Become a specific client, gives access to client operations");
    System.out.println(Operations.DisplayProductWaitlist.ordinal() + "Display waitlist for a product");
    System.out.println(Operations.RecieveShipment.ordinal() + "Recieve a shipment");
    System.out.println(Operations.Exit.ordinal() + " to logout");
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
            break;
        case ShowProducts:
            break;
        case ShowClients:
            break;
        case ShowClientsWithOutstandingBalance:
            break;
        case BecomeClient:
            break;
        case DisplayProductWaitlist:
            break;
        case RecieveShipment:
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
