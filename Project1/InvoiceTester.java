import java.util.*;
public class InvoiceTester {
  
  public static void main(String[] s) {
    Client c1 = new Client("bob", "smith", "address");
    Client c2 = new Client("tom", "jerry", "address");
    Product p1 = new Product("product1", 1, 1.99, 0.99);
    Product p2 = new Product("product2", 1, 4.99, 2.50);
    Product p3 = new Product("product3", 1, 5.00, 2.22);
    
    c1.getShoppingCart().insertProductToCart(p1, 1);
    c1.getShoppingCart().insertProductToCart(p2, 3);
    Order order1 = new Order(c1);
    System.out.println("order 1 details:");
    System.out.println(order1.toString());

    c2.getShoppingCart().insertProductToCart(p3, 2);
    Order order2 = new Order(c2);
    System.out.println("order 2 details:");
    System.out.println(order2.toString());

    Invoice invoice1 = new Invoice(order1);
    System.out.println("new invoice created for order 1");
    System.out.println(invoice1.toString());

    Invoice invoice2 = new Invoice(order2);
    System.out.println("new invoice created for order 2");
    System.out.println(invoice2.toString());

    InvoiceList invoiceList = InvoiceList.instance();
    invoiceList.insertInvoice(invoice1);
    invoiceList.insertInvoice(invoice2);
    System.out.println("invoices added to invoiceList");
    Iterator<Invoice> invoices = invoiceList.getInvoices();
     System.out.println("List of invoices:");
     while (invoices.hasNext()){
       System.out.println(invoices.next());
     }
  }
}
