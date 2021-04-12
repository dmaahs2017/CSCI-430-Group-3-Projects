import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;

public class ClientState extends WareState implements ActionListener {
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

  // Gui Fields
  private JFrame frame;
  private AbstractButton logoutButton, clientDetailsButton, transactionsButton, waitlistButton, getProductsButton, manageCartButton, placeOrderButton;

  // Constructor
  private ClientState() {
    warehouse = Warehouse.instance();
  }

  // Instance
  public static ClientState instance() {
    if (clientState == null) {
      return clientState = new ClientState();
    } else {
      return clientState;
    }
  }

  // ActionListener Interface
  public void actionPerformed(ActionEvent event) {
    if (event.getSource().equals(this.logoutButton))
       this.logout();
    else if (event.getSource().equals(this.clientDetailsButton)) 
      this.showClientDetails();
    else if (event.getSource().equals(this.transactionsButton)) 
      this.showTransactions();
    else if (event.getSource().equals(this.waitlistButton)) 
      this.showWaitlist();
    else if (event.getSource().equals(this.getProductsButton)) 
      this.showWaitlist();
    else if (event.getSource().equals(this.manageCartButton)) 
      this.manageCart();
    else if (event.getSource().equals(this.placeOrderButton)) 
      this.placeOrder();
  } 


  public void showClientDetails() {
    String id = WareContext.instance().getUser();
    Client client = warehouse.getClientById(id);
    GuiInputUtils.informUser(frame, client.toString());
  }

  public void showTransactions() {
    Iterator<Transaction> result;
    String clientID = WareContext.instance().getUser();
    result = warehouse.getTransactions(clientID);
    if (result == null) {
      GuiInputUtils.informUser(frame, "Invalid Client ID");
    } else {

      String output = new String();
      while(result.hasNext()) {
        Transaction transaction =  result.next();
        output += "\t" + transaction.getDescription() + ", Date: " + transaction.getDate() + ", Total Cost: $" + transaction.getAmount() + "\n";
      }
      if (output.length() > 0 )
        GuiInputUtils.informUser(frame, "Transaction List:\n" + output);
      else 
        GuiInputUtils.informUser(frame, "No Transactions Found");
    }
  }

  public void showWaitlist() {
    String id = WareContext.instance().getUser();
    Client client = warehouse.getClientById(id);
    Iterator<WaitItem> waitList = warehouse.getWaitlist();

    String output = new String();
    while(waitList.hasNext()) {
      WaitItem tempWaitItem = waitList.next();
      if(client.equals(tempWaitItem.getClient().getClientId())) {
        output += "\t" + tempWaitItem.toString() + "\n";
      }
    }

    if ( output.length() > 0 ) 
      GuiInputUtils.informUser(frame, "Waitlist:\n" + output);
    else 
      GuiInputUtils.informUser(frame, "Waitlist Empty.");
  }

  public void showProducts() {
    Iterator<InventoryItem> inventory = warehouse.getInventory();

    String output = new String();
    while(inventory.hasNext()) {
      InventoryItem tempItem = inventory.next();
      if (tempItem.getQuantity() > 0) {
        output += "\tProduct info: id: " + tempItem.getProduct().getId() + ", name: " + tempItem.getProduct().getName()
          + ", sale price: $" + tempItem.getProduct().getSalePrice() + ", quantity in stock: " + tempItem.getQuantity() + "\n";
      }
    }
    if ( output.length() > 0)      
      GuiInputUtils.informUser(frame, "Available Products:\n" + output);
    else 
      GuiInputUtils.informUser(frame, "No Products Available");
  }

  public void viewCart() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);
    Iterator<ShoppingCartItem> cIterator = client.getShoppingCart().getShoppingCartProducts();

    String output = new String();
    while(cIterator.hasNext()) {
      ShoppingCartItem item = cIterator.next();
      output += "\tProduct id: " + item.getProduct().getId() + ", name: " + item.getProduct().getName() + 
        ", sale price: $" + item.getProduct().getSalePrice() + ", Quantity in cart: " + item.getQuantity() + "\n";
    }

    if (output.length() > 0)
      GuiInputUtils.informUser(frame, "Shopping Cart Contents:\t" + output);
    else 
      GuiInputUtils.informUser(frame, "Shopping Cart Empty");
    
  }
  
  public void addToCart() {
    String clientId = WareContext.instance().getUser();
    do {
      String productId = GuiInputUtils.promptInput(frame, "Enter product id");
      Product product = warehouse.getProductById(productId);
      if(product != null) {
        String output = "id:" + product.getId() + ", name: " + product.getName() + ", Sale Price: $" + product.getSalePrice() + "\n";
        int productQuantity = GuiInputUtils.getNumber(frame, "Enter quantity for item:\t" + output);
        warehouse.addToCart(clientId, product, productQuantity);
      } else {
        GuiInputUtils.informUser(frame, "Could not find that product with id: " + productId);
      }
      if (!GuiInputUtils.yesOrNo(frame, "Add another product to the shopping cart?")) {
        break;
      }
    } while (true);
  }

  // Clear Gui Elements. To be used before transitions.
  public void clear() { 
    frame.getContentPane().removeAll();
    frame.paint(frame.getGraphics());   
  }  

  public void manageCart() {
     clear();
     WareContext.instance().changeState(4); // Transition to ShoppingCartState
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

  public void placeOrder() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);

    Iterator<ShoppingCartItem> cartIterator = client.getShoppingCart().getShoppingCartProducts();
    if (cartIterator.hasNext()) {
    viewCart();
    String total = "Shopping Cart Total: $" + client.getShoppingCart().getTotalPrice() + "\n";
      if(GuiInputUtils.yesOrNo(frame, total + "Are you sure you wish to place your order?")) {
        if(warehouse.placeOrder(clientId)) {
          GuiInputUtils.informUser(frame, "Order placed: total price charged to your balance, shopping cart has been emptied, and invoice generated.");
        } else {
          GuiInputUtils.informUser(frame, "Unable to place order");
        }
      } else {
        GuiInputUtils.informUser(frame, "Order Canceled");
      }
    } else {
        GuiInputUtils.informUser(frame, "Shopping cart is empty, unable to place the order");
    }
  }


  public void run() {
     frame = WareContext.instance().getFrame();
     frame.getContentPane().removeAll();
     frame.getContentPane().setLayout(new FlowLayout());

     // Define Buttons
     logoutButton = new JButton("Logout");
     clientDetailsButton = new JButton("View Your Details");
     transactionsButton = new JButton("View Transactions");
     waitlistButton = new JButton("View Your Waitlist");
     getProductsButton = new JButton("See Available Products");
     manageCartButton = new JButton("Manage Your Cart");
     placeOrderButton = new JButton("Place an Order for Items in Your Cart");

     // Add listeners
     logoutButton.addActionListener(this);
     clientDetailsButton.addActionListener(this);
     transactionsButton.addActionListener(this);
     waitlistButton.addActionListener(this);
     getProductsButton.addActionListener(this);
     manageCartButton.addActionListener(this);
     placeOrderButton.addActionListener(this);

     // Add Buttons to the frame
     frame.getContentPane().add(this.clientDetailsButton);
     frame.getContentPane().add(this.transactionsButton);
     frame.getContentPane().add(this.waitlistButton);
     frame.getContentPane().add(this.getProductsButton);
     frame.getContentPane().add(this.manageCartButton);
     frame.getContentPane().add(this.placeOrderButton);
     frame.getContentPane().add(this.logoutButton);

     frame.setVisible(true);
     frame.paint(frame.getGraphics()); 
     frame.toFront();
     frame.requestFocus();
  }

  public void logout()
  {
    if ((WareContext.instance()).getLogin() == WareContext.IsClient) {
      clear();
      WareContext.instance().changeState(0);
    }
    else if (WareContext.instance().getLogin() == WareContext.IsClerk) {
      clear();
      WareContext.instance().changeState(1);
    }
    else if (WareContext.instance().getLogin() == WareContext.IsManager) { 
      clear();
      WareContext.instance().changeState(2);
    }
    else {
      clear();
      WareContext.instance().changeState(3); 
    }
 
  }
}
