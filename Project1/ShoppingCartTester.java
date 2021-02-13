import java.util.*;
public class ShoppingCartTester {
  
  public static void main(String[] s) {
    ShoppingCart shoppingCart = ShoppingCart.instance();
    Product p1 = new Product("123", "product1", 1, 1.99, 0.99); //String id, String name, int quantity, double salePrice, double supplyPrice
    Product p2 = new Product("456", "product2", 1, 4.99, 2.50);

    System.out.println("Shopping Cart Should be empty");
    Iterator<Product> cartProduct = shoppingCart.getShoppingCartProducts();
    while (cartProduct.hasNext()){
      System.out.println(cartProduct.next());
    }

    // test adding 1 of product 'product1' to the shopping cart
    shoppingCart.insertProductToCart(p1,1);

    System.out.println("Shopping Cart should have 1 of product 'p1'");
    cartProduct = shoppingCart.getShoppingCartProducts();
    while (cartProduct.hasNext()){
      System.out.println(cartProduct.next());
    }

    // test adding 3 of product 'product2' to the shopping cart
    shoppingCart.insertProductToCart(p2, 3);

    System.out.println("Shopping Cart should have 2 products: 1 of 'p1', and 3 of 'p2'");
    cartProduct = shoppingCart.getShoppingCartProducts();
    while (cartProduct.hasNext()){
      System.out.println(cartProduct.next());
    }
  }
}
