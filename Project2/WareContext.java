import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import backend.*;
import utils.*;
public class WareContext {
  
  private int currentState;
  private static Warehouse warehouse;
  private static WareContext context;
  private int currentUser;
  private String userID;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  public static final int IsClient = 0;
  public static final int IsClerk = 1;
  public static final int IsManager = 2;
  private static JFrame warehouseFrame;
  private WareState[] states;
  private int[][] nextState;


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

  public JFrame getFrame() {
      return warehouseFrame;
  }

  public void setLogin(int code)
  {currentUser = code;}

  public void setUser(String uID)
  { userID = uID;}

  public int getLogin()
  { return currentUser;}

  public String getUser()
  { return userID;}

  private WareContext() { //constructor
    //System.out.println("In WareContext constructor");
    if (InputUtils.yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
    // set up the FSM and transition table;
    states = new WareState[6];
    states[0] = ClientState.instance();
    states[1] = ClerkState.instance();
    states[2] = ManagerState.instance();
    states[3]=  LoginState.instance();
    states[4] = ShoppingCartState.instance();
    states[5] = ClientQueryState.instance();


    nextState = new int[6][5];
    // ClientState Transitions
    nextState[0][0] = 3; // -> login state
    nextState[0][1] = 1; // -> clerk state
    nextState[0][2] = 2; // Manager state
    nextState[0][3] = -2; // Error
    nextState[0][4] = 4; // -> ShoppingCartState

    // ClerkState Transitions
    nextState[1][0] = 3;
    nextState[1][1] = 0;
    nextState[1][2] = 2;
    nextState[1][3] = -2;
    nextState[1][4] = 5; // -> ClientQueryState

    // ManagerState Transitions
    nextState[2][0] = 3;
    nextState[2][1] = 0;
    nextState[2][2] = 1;
    nextState[2][3] = -2; 

    // LoginState Transitions
    nextState[3][0] = 0;
    nextState[3][1] = 1;
    nextState[3][2] = 2;
    nextState[3][3] = -1;

    // ShoppingCartState Transitions
    nextState[4][0] = 0;
    nextState[4][3] = -2;

    // ClientQueryState Transitions
    nextState[5][0] = 1; // -> Clerk State
    nextState[5][3] = -2; // Error

    //initial state
    currentState = 3;



    // setup GUI JFrame
    warehouseFrame = new JFrame("Library GUI");
	warehouseFrame.addWindowListener(new WindowAdapter()
       {public void windowClosing(WindowEvent e){System.exit(0);}});
    warehouseFrame.setSize(400,400);
    warehouseFrame.setLocation(400, 400);
  }

  public void changeState(int transition)
  {
    currentState = nextState[currentState][transition];
    if (currentState == -2) {
      System.out.println("Error has occurred");
      terminate();
    }
    if (currentState == -1) {
      terminate();
    }
    states[currentState].run();
  }

  private void terminate()
  {
   if (InputUtils.yesOrNo("Save data?")) {
      if (Warehouse.save()) {
         System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
       } else {
         System.out.println(" There has been an error in saving \n" );
       }
     }
   System.out.println(" Goodbye \n "); System.exit(0);
  }

  public static WareContext instance() {
    if (context == null) {
       System.out.println("calling constructor");
      context = new WareContext();
    }
    return context;
  }

  public void process(){
    states[currentState].run();
  }
  
  public static void main (String[] args){
    WareContext.instance().process(); 
  }
}
