import java.util.*;
import java.io.*;
public class ShoppingCart implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Product> cart;
  private double totalPrice;

  public ShoppingCart() {
    cart = new LinkedList<Product>();
    totalPrice = 0;
  }

  // insert product(s) into the shopping cart and update total price
  public boolean insertProductToCart(Product product, int quantity) {
    product.setQuantity(quantity);
    cart.add(product);
    totalPrice += (product.getSalePrice() * quantity);
    return true;
  }
  public Iterator<Product> getShoppingCartProducts() {
    return cart.iterator();
  }

  public double getTotalPrice() {
    return totalPrice;
  }
  
  public String toString() {
    return cart.toString();
  }
}
