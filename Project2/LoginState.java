import java.util.*;
import java.io.*;
import backend.*;
import utils.*;

public class LoginState extends WareState{
  private static final int CLIENT_LOGIN = 0;
  private static final int CLERK_LOGIN = 1;
  private static final int MANAGER_LOGIN = 2;
  private static final int HELP = 3;
  private static final int EXIT = 4;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static LoginState instance;
  private LoginState() {
      super();
  }

  public static LoginState instance() {
    if (instance == null) {
      instance = new LoginState();
    }
    return instance;
  }

  public void help() {
    System.out.println("\nEnter a number between " + CLIENT_LOGIN + " and " + EXIT + " as explained below:");
    System.out.println(CLIENT_LOGIN + " to login as a client");
    System.out.println(CLERK_LOGIN + " to login as a clerk");
    System.out.println(MANAGER_LOGIN + " to login as a manager");
    System.out.println("\n" + HELP + " for help");
    System.out.println(EXIT + " to exit");
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(InputUtils.getToken("Enter command:" ));
        if (value <= EXIT && value >= CLIENT_LOGIN) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  private void client(){
    SecuritySystem ss = new SecuritySystem();
    String user = InputUtils.getToken("Please input the client username: ");
    if (Warehouse.instance().getClientById(user) != null){
      String pass = InputUtils.getToken("Please input the client password: ");
      if (ss.verifyPassword(user, pass)) {
        (WareContext.instance()).setLogin(WareContext.IsClient);
        (WareContext.instance()).setUser(user.toString());      
        (WareContext.instance()).changeState(0);
      } else {
        System.out.println("Invalid client password.");
      }      
    } else {
      System.out.println("Invalid client username.");
    }
  }

  private void clerk(){
    SecuritySystem ss = new SecuritySystem();
    String clerk = InputUtils.getToken("Please input the clerk username: ");
    if (clerk.equals("clerk")) { 
      String pass = InputUtils.getToken("Please input the clerk password: ");
      if (ss.verifyPassword(clerk, pass)){
        (WareContext.instance()).setLogin(WareContext.IsClerk);
        (WareContext.instance()).setUser("clerk");      
        (WareContext.instance()).changeState(1);
      } else {
        System.out.println("Invalid clerk password.");
      }
    } else {
      System.out.println("Invalid clerk username.");
    }
  }

  private void manager(){
    SecuritySystem ss = new SecuritySystem();
    String manager = InputUtils.getToken("Please input the manager username: ");
    if (manager.equals("manager")) { 
      String pass = InputUtils.getToken("Please input the manager password: ");
      if (ss.verifyPassword(manager, pass)){
        (WareContext.instance()).setLogin(WareContext.IsManager);
        (WareContext.instance()).setUser("manager");      
        (WareContext.instance()).changeState(1);
      } else {
        System.out.println("Invalid manager password.");
      }
    } else {
      System.out.println("Invalid manager username.");
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
        case CLIENT_LOGIN:
          client();
          break;
        case CLERK_LOGIN:
          clerk();
          break;
        case MANAGER_LOGIN:
          manager();
          break;
        default:
          System.out.println("Invalid choice");
                                
      }
      help();
    }
    (WareContext.instance()).changeState(3); // exit
  }

  public void run() {
    process();
  }
}
