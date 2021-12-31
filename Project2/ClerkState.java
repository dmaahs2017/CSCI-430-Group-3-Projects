import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;

public class ClerkState extends WareState implements ActionListener {
  private static ClerkState clerkState = new ClerkState();
  private static Warehouse warehouse;

  // Gui fields
  private JFrame frame;
  public AbstractButton logoutButton, addClientButton, queryClientsButton, showProductsButton, becomeClientButton, displayProductWaitlistButton,
         recieveShipmentButton;


  // ActionListener Interface
  public void actionPerformed(ActionEvent event) {
    if (event.getSource().equals(this.logoutButton))
       this.logout();
    else if (event.getSource().equals(this.addClientButton)) 
      this.addClient();
    else if (event.getSource().equals(this.queryClientsButton)) 
      this.queryClientsTransition();
    else if (event.getSource().equals(this.showProductsButton)) 
      this.showProducts();
    else if (event.getSource().equals(this.becomeClientButton)) 
      this.becomeClient();
    else if (event.getSource().equals(this.displayProductWaitlistButton)) 
      this.showProductsWaitlist();
    else if (event.getSource().equals(this.recieveShipmentButton)) 
      this.recieveShipment();
  } 

  // Constructor
  private ClerkState() {
    warehouse = Warehouse.instance();
  }

  // instance
  public static ClerkState instance() {
      return clerkState;
  }

  // Clear Gui Elements. To be used before transitions.
  public void clear() { 
    frame.getContentPane().removeAll();
    frame.paint(frame.getGraphics());   
  }  

  public void addClient() {
    String firstName = GuiInputUtils.promptInput(frame, "Enter client's first name");
    String lastName = GuiInputUtils.promptInput(frame, "Enter client's last name");
    String address = GuiInputUtils.promptInput(frame, "Enter address");

    Client result = warehouse.addClient(firstName, lastName, address);

    if (result == null) {
      GuiInputUtils.informUser(frame, "Could not add member");
    }
    GuiInputUtils.informUser(frame, result.toString());
  }

  public void showProducts() {
      Iterator<Product> allProducts = warehouse.getProducts();

      String products = new String();
      while (allProducts.hasNext()){
        Product product = allProducts.next();

        products += "\t" + product.toString() + "\n";
      }

      if ( products.length() > 0 ) {
        GuiInputUtils.informUser(frame, "Products:\n" + products);
      } else {
        GuiInputUtils.informUser(frame, "No Products Exist");
      }
  }


  public void showProductsWaitlist() {
    int amt = 0;
    Iterator<Product> allProducts = warehouse.getProducts();

    String waitListedProductsString = new String();

    while(allProducts.hasNext()) {
      Product tempProduct = allProducts.next();
      Iterator<WaitItem> waitList = warehouse.getWaitlist();
      while(waitList.hasNext()) {
        WaitItem tempWaitItem = waitList.next();
        if(tempProduct == tempWaitItem.getProduct()) {
          amt += tempWaitItem.getQuantity();
        }
      }

      waitListedProductsString = "\t" + tempProduct.toString() + " " + amt + "\n";
      amt = 0;
    }

    if ( waitListedProductsString.length() > 0 ) {
      GuiInputUtils.informUser(frame, "WaitListed Products:\n" + waitListedProductsString);
    } else {
      GuiInputUtils.informUser(frame, "No Waitlisted Products");
    }
  }

  public void recieveShipment() {
    Product p;
    do {
      String productId = GuiInputUtils.promptInput(frame, "Enter productId");
      p = warehouse.getProductById(productId);
      if(p != null) {
        int quantity = GuiInputUtils.getNumber(frame, "Enter quantity");

        //check for waitlisted orders
        List<WaitItem> waitlistedOrders = warehouse.getWaitItemsByProductId(productId);
        Iterator<WaitItem> waitlistedOrdersIterator = waitlistedOrders.iterator();

        while(waitlistedOrdersIterator.hasNext()) {
          WaitItem waitItem = waitlistedOrdersIterator.next();


          if(GuiInputUtils.yesOrNo(frame, "Fill waitlisted order for item:\n\t" + waitItem.toString())) {
            quantity -= waitItem.getQuantity();
            waitItem.setOrderFilled(true);

            GuiInputUtils.informUser(frame, "Order Filled");
          } else {
            GuiInputUtils.informUser(frame, "Order was not filled.");
          }
        }
        // add remaining product to inventory
        warehouse.addToInventory(productId, quantity);
      } else {
        GuiInputUtils.informUser(frame, "Product not found");
      }
      if (!GuiInputUtils.yesOrNo(frame, "Receive another product?")) {
        break;
      }
    } while(true);
  }

  private void becomeClient() {
    String user = GuiInputUtils.promptInput(frame, "Please input the client id: ");
    if (Warehouse.instance().getClientById(user) != null){
      (WareContext.instance()).setUser(user.toString());      
      clear();
      (WareContext.instance()).changeState(1);
    } else {
      GuiInputUtils.informUser(frame, "Invalid client id.");
    }
  }

  public void queryClientsTransition() {
    clear();
    WareContext.instance().changeState(4); // Transition to ClientQueryState
  }


  public void run() {
     frame = WareContext.instance().getFrame();
     frame.getContentPane().removeAll();
     frame.getContentPane().setLayout(new FlowLayout());

     // Define Buttons
     addClientButton = new JButton("Add Client");
     queryClientsButton = new JButton("Query Clients");
     showProductsButton = new JButton("Show Products");
     becomeClientButton = new JButton("Become Clients");
     displayProductWaitlistButton = new JButton("Display Waitlist for a Product");
     recieveShipmentButton = new JButton("Recieve Shipments");
     logoutButton = new JButton("Logout");

     // Add listeners
     addClientButton.addActionListener(this);
     queryClientsButton.addActionListener(this);
     showProductsButton.addActionListener(this);
     becomeClientButton.addActionListener(this);
     displayProductWaitlistButton.addActionListener(this);
     recieveShipmentButton.addActionListener(this);
     logoutButton.addActionListener(this);

     // Add Buttons to the frame
     frame.getContentPane().add(this.addClientButton);
     frame.getContentPane().add(this.queryClientsButton);
     frame.getContentPane().add(this.showProductsButton);
     frame.getContentPane().add(this.becomeClientButton);
     frame.getContentPane().add(this.displayProductWaitlistButton);
     frame.getContentPane().add(this.recieveShipmentButton);
     frame.getContentPane().add(this.logoutButton);

     frame.setVisible(true);
     frame.paint(frame.getGraphics()); 
     frame.toFront();
     frame.requestFocus();
  }

  public void logout()
  {
    if (WareContext.instance().getLogin() == WareContext.IsClerk) {  
      clear();
      (WareContext.instance()).changeState(0); // exit to login with a code 0
    }
    else if (WareContext.instance().getLogin() == WareContext.IsManager) { 
      clear();
      (WareContext.instance()).changeState(2); // exit to manager with a code 0
     }
    else {
      clear();
      (WareContext.instance()).changeState(3); // exit code 3, indicates error
    }
  }
 
}
