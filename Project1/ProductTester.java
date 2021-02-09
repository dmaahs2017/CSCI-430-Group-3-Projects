import java.util.*;
import java.text.*;
import java.io.*;
public class ProductTester {
  
  public static void main(String[] s) {
     Product p1 = new Product("123", "product1", 3, 1.99, 0.99); //String id, String name, int quantity, double salePrice, double supplyPrice
     Product p2 = new Product("456", "product2", 1, 4.99, 2.50);
     ProductList productList = ProductList.instance();
     productList.insertProduct(p1);
     productList.insertProduct(p2);
     Iterator products = productList.getProducts();
     System.out.println("List of products");
     while (products.hasNext()){
       System.out.println(products.next());
     }
	 // test getters and setters
	 System.out.println("set p2 productId to '430'");
	 p2.setId("430");
	 System.out.println(p2.getId() + " should be '430'");
	 
	 System.out.println("set p2 name to 'newProduct2'");
	 p2.setName("newProduct2");
	 System.out.println(p2.getName() + " should be 'newProduct2'");
	 
	 System.out.println("set p2 quantity to '10'");
	 p2.setQuantity(10);
	 System.out.println(p2.getQuantity() + " should be '10'");
	 
	 System.out.println("set p2 salePrice to '10.49'");
	 p2.setSalePrice(10.49);
	 System.out.println(p2.getSalePrice() + " should be '10.49'");
	 
	 System.out.println("set p2 quantity to '5.55'");
	 p2.setSupplyPrice(5.55);
	 System.out.println(p2.getSupplyPrice() + " should be '5.55'");
	 
	 // print list again
	 products = productList.getProducts();
     System.out.println("List of products");
     while (products.hasNext()){
       System.out.println(products.next());
     }
  }
}
