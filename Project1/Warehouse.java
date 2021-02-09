import java.util.*;
public class Warehouse {
    //add client
    public Client addClient(String clientId, String fname, String lname, String address) {
        Client client = new Client(fname, lname, address, clientId);

        if (ClientList.instance().insertClient(client)) {
            return client;
        } else {
            return null;
        }
    }

    //add product
    public Product addProduct(String id, String name, int quantity, double salePrice, double supplyPrice) {
        Product product = new Product(id, name, quantity, salePrice, supplyPrice);

        if (ProductList.instance().insertProduct(product)) {
            return product;
        } else {
            return null;
        }
    }

    //get product by id
    public Product getProductById(String id) {
        Iterator<Product> products = ProductList.instance().getProducts();

        Product p = null;
        while (products.hasNext() || p != null) {
            Product tmp = products.next();
            if ( tmp.getId() == id ) {
                p = tmp;
            }
        }

        return p;
    }

    //set product info (name quant price)
    //get client by id
    //set client info (fname, lname, addrs)
    //add supplier
    //set supplier info ()
}
