package backend;
import java.util.*;

public class ClientTest {

    public static void main(String[] s) {
        Client c1 = new Client("jkb", "lrnz", "street name and other things");
        Client c2 = new Client("Oscar", "Kilo", "rd name and some things");
        ClientList list = ClientList.instance();
        list.insertClient(c1);
        list.insertClient(c2);

        Iterator<Client> clients = list.getClients();
        while (clients.hasNext()) {
            System.out.println(clients.next()); // test id autogen
        }

        c1.setClientId("test007");
        System.out.println("client id should be test007 : " + c1.getClientId());

        c1.setFirstName("Alpha");
        c1.setLastName("Bravo");
        System.out.println("client name should be Alpha Bravo : " + c1.getFirstName() + " " + c1.getLastName());

        c1.setAddress("court yard name and city");
        System.out.println("client address should be court yard name and city : " + c1.getAddress());

        System.out.println(list.toString());
        
        // set and show balance
        c1.setBalance(101.01);
        System.out.println("client's balance should be 101.01 : " + c1.getBalance());
        
        // shopping cart test
        System.out.println("Client Shopping Cart Test:");

        Product p1 = new Product("product1", 1, 1.00, .50);
        Product p2 = new Product("product2", 1, 4.99, 2.49);

        System.out.println("Test Products:");
        System.out.println(p1.toString());
        System.out.println(p2.toString());

        System.out.println("'2 of product1' to be added to " + c1.getFirstName() + " " + c1.getLastName() + "'s shopping cart:");
        System.out.println("'5 of product2' to be added to " + c2.getFirstName() + " " + c2.getLastName() + "'s shopping cart:");
        c1.getShoppingCart().insertProductToCart(p1, 2);
        c2.getShoppingCart().insertProductToCart(p2, 5);

        // print c1's shopping cart contents
        System.out.println(c1.getFirstName() + " " + c1.getLastName() + "'s Shopping Cart contents:");
        Iterator<ShoppingCartItem> cart1Iterator = c1.getShoppingCart().getShoppingCartProducts();
        while (cart1Iterator.hasNext()) {
            System.out.println(cart1Iterator.next());
        }

        // print c2's shopping cart contents
        System.out.println(c2.getFirstName() + " " + c2.getLastName() + "'s Shopping Cart contents:");
        Iterator<ShoppingCartItem> cart2Iterator = c2.getShoppingCart().getShoppingCartProducts();
        while (cart2Iterator.hasNext()) {
            System.out.println(cart2Iterator.next());
        }
    }
}
