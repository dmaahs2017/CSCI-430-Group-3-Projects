import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;

public class ClientQueryState extends WareState implements ActionListener {
  private static ClientQueryState state;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;

  // Gui Fields
  private JFrame frame;
  private AbstractButton backButton, displayAllClientsButton, displayClientsWithBalancesButton;

  // Constructor
  private ClientQueryState() {
    warehouse = Warehouse.instance();
  }

  // Instance
  public static ClientQueryState instance() {
    if (state == null) {
      return state = new ClientQueryState();
    } else {
      return state;
    }
  }

  // ActionListener Interface
  public void actionPerformed(ActionEvent event) {
    if (event.getSource().equals(this.backButton))
       this.logout();
    else if (event.getSource().equals(this.displayAllClientsButton)) 
      this.showClients();
    else if (event.getSource().equals(this.displayClientsWithBalancesButton)) 
      this.displayClientsWithBalances();
  } 

  public void viewCart() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);

    String itemsString = new String();

    Iterator<ShoppingCartItem> cIterator = client.getShoppingCart().getShoppingCartProducts();
    while(cIterator.hasNext()) {
      ShoppingCartItem item = cIterator.next();

      itemsString += "\t" + "Product id: " + item.getProduct().getId() + ", name: " + item.getProduct().getName() + 
        ", sale price: $" + item.getProduct().getSalePrice() + ", Quantity in cart: " + item.getQuantity() + "\n";
    }

    if ( itemsString.length() > 0 )      
      GuiInputUtils.informUser(frame, "Shopping Cart Contents:\n" + itemsString);
    else 
      GuiInputUtils.informUser(frame, "Shopping Cart Empty");
    
  }
  
  public void addToCart() {
    String clientId = WareContext.instance().getUser();
    do {
      String productId = GuiInputUtils.promptInput(frame, "Enter product id");
      Product product = warehouse.getProductById(productId);
      if(product != null) {
        String productString = "id:" + product.getId() + ", name: " + product.getName() + ", Sale Price: $" + product.getSalePrice() + "\n";
        int productQuantity = GuiInputUtils.getNumber(frame, "Enter quantity for Product:\n\t" + productString);
        warehouse.addToCart(clientId, product, productQuantity);
      } else {
        GuiInputUtils.informUser(frame, "Could not find with productId:" + productId);
      }
      if (!GuiInputUtils.yesOrNo(frame, "Add another product to the shopping cart?")) {
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
      String productId = GuiInputUtils.promptInput(frame, "Enter Product ID from cart to remove");

      boolean wasSuccessful = cart.removeItem(productId);
      if ( !wasSuccessful ) {
        doneEditing = !GuiInputUtils.yesOrNo(frame, "That ID was not found in the shoping cart? Continue?");
      } else {
        doneEditing = !GuiInputUtils.yesOrNo(frame, "Successfully Removed Prodcut. Remove More Products?");
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
      String productId = GuiInputUtils.promptInput(frame, "Enter Product ID from cart to edit");

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
        doneEditing = !GuiInputUtils.yesOrNo(frame, "That ID was not found in the shoping cart? Continue?");
      } else {
        int newQuantity = GuiInputUtils.getNumber(frame, "Enter the desired amount to put in your shopping cart.");

        item.setQuantity(newQuantity);
        doneEditing = !GuiInputUtils.yesOrNo(frame, "Would you like to edit more items in your cart?");
      }
    }
  }
  
  public void showClients() {
      Iterator<Client> allClients = warehouse.getClients();

      String clientsString = new String();
      while (allClients.hasNext()){
        Client client = allClients.next();
        clientsString += "\t" + client.toString() + "\n";
      }

      if (clientsString.length() > 0) {
        GuiInputUtils.informUser(frame, "Clients:" + clientsString);
      } else {
        GuiInputUtils.informUser(frame, "No Clients In System");
      }
  }

  public void displayClientsWithBalances() {
    Iterator<Client> allClients = warehouse.getClients();

    String clientsString = new String();
    while (allClients.hasNext()){
      Client client = allClients.next();
      if (client.getBalance() <= 0.0) {
        clientsString += "\t" + client.toString() + "\n";
      }
    }

    if (clientsString.length() > 0) {
      GuiInputUtils.informUser(frame, "Clients With Outstanding Balances:" + clientsString);
    } else {
      GuiInputUtils.informUser(frame, "No Clients With Outstanding Balances");
    }
  }

  public void displayClientsWithNoTransactions() {
    Iterator<Client> allClients = warehouse.getClients();

    String clientsString = new String();
    while (allClients.hasNext()){
      Client client = allClients.next();
      if (client.getTransactionList().isEmpty()) {
        clientsString += "\t" + client.toString() + "\n";
      }
    }

    if (clientsString.length() > 0) {
      GuiInputUtils.informUser(frame, "Clients With No Transactions:" + clientsString);
    } else {
      GuiInputUtils.informUser(frame, "No Clients No Transactions");
    }
  }


  public void run() {
     frame = WareContext.instance().getFrame();
     frame.getContentPane().removeAll();
     frame.getContentPane().setLayout(new FlowLayout());

     // Define Buttons
     backButton = new JButton("Back");
     displayAllClientsButton = new JButton("Display All Clients");
     displayClientsWithBalancesButton = new JButton("Display Clients With Outstanding Balances");

     // Add listeners
     backButton.addActionListener(this);
     displayAllClientsButton.addActionListener(this);
     displayClientsWithBalancesButton.addActionListener(this);

     // Add Buttons to the frame
     frame.getContentPane().add(this.displayAllClientsButton);
     frame.getContentPane().add(this.displayClientsWithBalancesButton);
     frame.getContentPane().add(this.backButton);

     frame.setVisible(true);
     frame.paint(frame.getGraphics()); 
     frame.toFront();
     frame.requestFocus();
  }

  // Clear Gui Elements. To be used before transitions.
  public void clear() { 
    frame.getContentPane().removeAll();
    frame.paint(frame.getGraphics());   
  }  

  public void logout()
  {
    clear();
    WareContext.instance().changeState(0); // -> Manager
  }
 
}
