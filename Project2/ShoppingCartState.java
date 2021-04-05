import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;
public class ShoppingCartState extends WareState {
  private static ShoppingCartState cartState;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;

  enum Operation {
    Exit,
    ViewCartContents,
    AddProduct,
    RemoveProduct,
    ChangeQuantityOfProduct,
    Help,
  }

  private ShoppingCartState() {
    warehouse = Warehouse.instance();
  }

  public static ShoppingCartState instance() {
    if (cartState == null) {
      return cartState = new ShoppingCartState();
    } else {
      return cartState;
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
    System.out.println(Operation.ViewCartContents.ordinal() + " to view your cart");
    System.out.println(Operation.AddProduct.ordinal() + " to add products to your cart");
    System.out.println(Operation.RemoveProduct.ordinal() + " to remove products from your cart");
    System.out.println(Operation.ChangeQuantityOfProduct.ordinal() + " to edit quantities of products in your cart");
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

  public void process() {
    Operation command;
    help();
    while ((command = getCommand()) != Operation.Exit) {
      switch (command) {
        case Help:
          help();
          break;
        case AddProduct:
          addToCart();
          break;
        case ViewCartContents:
          viewCart();
          break;
        case RemoveProduct:
          removeProduct();
          break;
        case ChangeQuantityOfProduct:
          modifyCart();
          break;
        default:
          System.out.println("Invalid choice");
      }
    }
    back();
  }

  public void run() {
    process();
  }

  public void back()
  {
    if ((WareContext.instance()).getLogin() == WareContext.IsClient)
      {
         (WareContext.instance()).changeState(0); // client transition
      }
    else if (WareContext.instance().getLogin() == WareContext.IsClerk)
      {
        (WareContext.instance()).changeState(1); // clerk transition
      }
    else if (WareContext.instance().getLogin() == WareContext.IsManager)
       {
        (WareContext.instance()).changeState(2);  // manager transition
       }
    else 
       (WareContext.instance()).changeState(3); // error
  }
 
}
