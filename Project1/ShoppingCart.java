import java.util.*;
import java.io.*;
public class ShoppingCart implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Product> cart;

  public ShoppingCart() {
    cart = new LinkedList<Product>();
  }

  // insert product(s) into the shopping cart and update total price
  public boolean insertProductToCart(Product product, int quantity) {
    product.setQuantity(quantity);
    cart.add(product);
    return true;
  }
  public Iterator<Product> getShoppingCartProducts() {
    return cart.iterator();
  }

  public double getTotalPrice() {
    double totalPrice = 0;
    Iterator<Product> cartIterator = cart.iterator();

    while (cartIterator.hasNext()){
      Product p = cartIterator.next();
      totalPrice += (p.getSalePrice() * p.getQuantity());
    }
    
    return totalPrice;
  }
  
  public String toString() {
    return cart.toString();
  }
}
