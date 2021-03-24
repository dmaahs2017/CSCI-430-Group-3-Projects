package backend;
import java.io.*;
import java.util.*;
public class Order implements Serializable {
  private static final long serialVersionUID = 1L;
  private Client client;
  private String orderId;
  private ShoppingCart orderedCart;  

  public Order(Client client) {
    this.orderId = (OrderIdServer.instance()).generateId();
    this.client = client;
    this.orderedCart = client.getShoppingCart();
  }

  public Iterator<ShoppingCartItem> getOrderProducts() {
    return orderedCart.getShoppingCartProducts();
  }

  public Client getOrderClient() {
    return client;
  }

  public String getOrderId() {
    return orderId;
  }

  public ShoppingCart getOrderedCart() {
    return orderedCart;
  }

  public Boolean equals(String id) {
    return this.orderId.equals(id);
  }

  public String toString() {
      return "orderId " + orderId + " clientId " + client.getClientId() + " ordered_products " + orderedCart.toString();
  }
}
