import java.io.*;
import java.util.*;

public class Invoice implements Serializable {
  private static final long serialVersionUID = 1L;
  private String invoiceId;
  private ShoppingCart invoiceCart;
  private double totalPrice;

  public Invoice(Order invoicedOrder) {
    this.invoiceId = (InvoiceIdServer.instance()).generateId();
    this.invoiceCart = invoicedOrder.getOrderedCart();
    this.totalPrice = invoicedOrder.getOrderedCart().getTotalPrice();
  }
  
  public Iterator<ShoppingCartItem> getInvoiceProducts() {
    return invoiceCart.getShoppingCartProducts();
  }

  public String getInvoiceId() {
    return invoiceId;
  }

  public ShoppingCart getInvoiceCart() {
    return invoiceCart;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public Boolean equals(String id) {
    return this.invoiceId.equals(id);
  }

  public String toString() {
      return "invoiceId " + invoiceId + " total price " + totalPrice + " Invoiced Products " + invoiceCart.toString();
  }
}
