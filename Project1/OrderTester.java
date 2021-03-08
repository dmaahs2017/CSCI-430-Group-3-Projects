import java.util.*;
public class OrderTester {
  
  public static void main(String[] s) {
    Client c1 = new Client("bob", "smith", "address");
    Product p1 = new Product("product1", 1, 1.99, 0.99);
    Product p2 = new Product("product2", 1, 4.99, 2.50);
    
    c1.getShoppingCart().insertProductToCart(p1, 1);
    c1.getShoppingCart().insertProductToCart(p2, 3);

    Iterator<ShoppingCartItem> cartIterator = c1.getShoppingCart().getShoppingCartProducts();
    System.out.println("Client's shopping cart should contain '1 of product1' and '3 of product2'");
    System.out.println("Client's shopping cart:");
    while (cartIterator.hasNext()){
      System.out.println(cartIterator.next());
    }

    Order order = new Order(c1);

    System.out.println("New order should have a generated orderId and client id, and ordered_products should include product1, product2");
    System.out.println("New order:");
    System.out.println(order.toString());
  }
}
