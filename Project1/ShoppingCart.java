import java.util.*;
import java.io.*;
public class ShoppingCart implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Product> cart = new LinkedList<Product>();
  private static ShoppingCart shoppingCart;
  private ShoppingCart() {
  }
  public static ShoppingCart instance() {
    if (shoppingCart == null) {
      return (shoppingCart = new ShoppingCart());
    } else {
      return shoppingCart;
    }
  }

  // insert product(s) into the shopping cart
  public boolean insertProductToCart(Product product, int quantity) {
    product.setQuantity(quantity);
    cart.add(product);
    return true;
  }
  public Iterator<Product> getShoppingCartProducts() {
    return cart.iterator();
  }
  
  public String toString() {
    return cart.toString();
  }
}
