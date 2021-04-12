import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;

public class ShoppingCartState extends WareState implements ActionListener {
  private static ShoppingCartState cartState;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;

  // GUI Fields
  private JFrame frame;
  private AbstractButton backButton, viewButton, addButton, editButton, removeButton;

  // Constructor
  private ShoppingCartState() {
    warehouse = Warehouse.instance();
  }

  // Instance
  public static ShoppingCartState instance() {
    if (cartState == null) {
      return cartState = new ShoppingCartState();
    } else {
      return cartState;
    }
  }

  // ActionListener Interface
  public void actionPerformed(ActionEvent event) {
    if (event.getSource().equals(this.backButton))
       this.back();
    else if (event.getSource().equals(this.viewButton)) 
      this.viewCart();
    else if (event.getSource().equals(this.addButton)) 
      this.addToCart();
    else if (event.getSource().equals(this.editButton)) 
      this.modifyCart();
    else if (event.getSource().equals(this.removeButton)) 
      this.removeProduct();
  } 

  private String getCart() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);

    String output = new String();
    Iterator<ShoppingCartItem> cIterator = client.getShoppingCart().getShoppingCartProducts();
    while(cIterator.hasNext()) {
      ShoppingCartItem item = cIterator.next();
      output += "\tProduct id: " + item.getProduct().getId() + ", name: " + item.getProduct().getName() + 
        ", sale price: $" + item.getProduct().getSalePrice() + ", Quantity in cart: " + item.getQuantity() + "\n";
    }
    return output;
  }

  public void viewCart() {
    String output = getCart();

    if (output.length() > 0)
      GuiInputUtils.informUser(frame, "Shopping Cart:\n" + output);
    else 
      GuiInputUtils.informUser(frame, "Shopping Cart is empty.");

  }
  
  public void addToCart() {
    String clientId = WareContext.instance().getUser();
    do {
      String productId = GuiInputUtils.promptInput(frame, "Enter product id");
      Product product = warehouse.getProductById(productId);
      if(product != null) {
        String output = "\tid:" + product.getId() + ", name: " + product.getName() + ", Sale Price: $" + product.getSalePrice() + "\n";
        int productQuantity = GuiInputUtils.getNumber(frame, "Enter Quantity for Product:\n" + output);
        warehouse.addToCart(clientId, product, productQuantity);
      } else {
        GuiInputUtils.informUser(frame, "Could not find that product id");
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
      String currentCart = getCart();
      String productId = GuiInputUtils.promptInput(frame, "Cart:\n" + currentCart + "\n\nEnter Product ID from cart to remove");

      boolean wasSuccessful = cart.removeItem(productId);
      if ( !wasSuccessful ) {
        doneEditing = !GuiInputUtils.yesOrNo(frame, "That ID was not found in the shoping cart? Continue?");
      } else {
        doneEditing = !GuiInputUtils.yesOrNo(frame, "Successfully Removed Product.\nRemove More Products?");
      }
    }
  }

  public void modifyCart() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);
    ShoppingCart cart = client.getShoppingCart();
    Boolean doneEditing = false;

    while (!doneEditing) {
      String currentCart = getCart();
      String productId = GuiInputUtils.promptInput(frame, "Cart\n" + currentCart + "\n\nEnter Product ID from cart to edit");

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


  public void run() {
     frame = WareContext.instance().getFrame();
     frame.getContentPane().removeAll();
     frame.getContentPane().setLayout(new FlowLayout());

     // Define Buttons
     backButton = new JButton("Back");
     viewButton = new JButton("View Cart");
     addButton = new JButton("Add Product To Cart");
     editButton = new JButton("Edit Quantity of Items in Cart");
     removeButton = new JButton("Remove Items From Cart");

     // Add listeners
     backButton.addActionListener(this);
     viewButton.addActionListener(this);
     addButton.addActionListener(this);
     editButton.addActionListener(this);
     removeButton.addActionListener(this);

     // Add Buttons to the frame
     frame.getContentPane().add(this.viewButton);
     frame.getContentPane().add(this.addButton);
     frame.getContentPane().add(this.editButton);
     frame.getContentPane().add(this.removeButton);
     frame.getContentPane().add(this.backButton);

     frame.setVisible(true);
     frame.paint(frame.getGraphics()); 
     frame.toFront();
     frame.requestFocus();
  }

  public void back()
  {
       (WareContext.instance()).changeState(0); // client transition
  }
 
}
