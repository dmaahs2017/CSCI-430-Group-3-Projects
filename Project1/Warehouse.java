import java.util.*;
public class Warehouse {
    private static Warehouse warehouse;

    public static Warehouse instance() {
        if (warehouse == null) {
            return warehouse = new Warehouse();
        } else {
            return warehouse;
        }
    }

    //add client
    public Client addClient(String fname, String lname, String address) {
        Client client = new Client(fname, lname, address);

        if (ClientList.instance().insertClient(client)) {
            return client;
        } else {
            return null;
        }
    }

    //add product
    public Product addProduct(String name, int quantity, double salePrice, double supplyPrice) {
        Product product = new Product(name, quantity, salePrice, supplyPrice);

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
        while (products.hasNext() && p == null) {
            Product tmp = products.next();
            if ( tmp.equals(id)) {
                p = tmp;
            }
        }

        return p;
    }

    public Iterator<Product> getProducts() {
        return ProductList.instance().getProducts();
    }

    //set product info (Product, name quant price)
    public Boolean setProductInfo(String id, String name, int quant, double salePrice, double supplyPrice) {
        Product p = this.getProductById(id);
        if ( p == null ) {
            return false;
        }
        p.setName(name);
        p.setQuantity(quant);
        p.setSalePrice(salePrice);
        p.setSupplyPrice(supplyPrice);
        return true;
    }


    //get client by id
    public Client getClientById(String id) {
        Iterator<Client> clients = ClientList.instance().getClients();

        Client p = null;
        while (clients.hasNext() && p == null) {
            Client tmp = clients.next();
            if ( tmp.equals(id) ) {
                p = tmp;
            }
        }

        return p;
    }

    public Iterator<Client> getClients() {
      return ClientList.instance().getClients();
    }

    //set client info (fname, lname, addrs)
    public Boolean setClientInfo(String id, String fname, String lname, String address) {
        Client p = this.getClientById(id);
        if ( p == null ) {
            return false;
        }
        p.setFirstName(fname);
        p.setLastName(lname);
        p.setAddress(address);
        return true;

    }

    //add supplier
    public Supplier addSupplier(String name) {
        Supplier supplier = new Supplier(name);

        if (SupplierList.instance().insertSupplier(supplier)) {
            return supplier;
        } else {
            return null;
        }
    }

    // get supplier by id
    public Supplier getSupplierById(String id) {
        Iterator<Supplier> suppliers = SupplierList.instance().getSuppliers();

        Supplier s = null;
        while (suppliers.hasNext() && s == null) {
            Supplier tmp = suppliers.next();
            if ( tmp.equals(id)) {
                s = tmp;
            }
        }

        return s;
    }

    public Iterator<Supplier> getSuppliers() {
      return SupplierList.instance().getSuppliers();
    }

    //set supplier info
    public Boolean setSupplierInfo(String id, String name) {
        Supplier supplier = this.getSupplierById(id);
        if ( supplier == null ) {
            return false;
        }
        supplier.setName(name);
        return true;

    }

    // display a client's shopping cart
    public Boolean displayCart(String clientId) {
        Client client = this.getClientById(clientId);
        if ( client == null ) {
            return false;
        }
        Iterator<Product> cartIterator = client.getShoppingCart().getShoppingCartProducts();
        while (cartIterator.hasNext()){
            System.out.println(cartIterator.next());
         }
        return true;
    }

    // add product to a client's shopping cart
    public Boolean addToCart(String clientId, Product product, int quantity) {
        Client client = this.getClientById(clientId);
        if ( client == null ) {
            return false;
        }
        client.getShoppingCart().insertProductToCart(product, quantity);
        return true;
    }

    // empty a client's shopping cart
    public Boolean emptyCart(String clientId) {
        Client client = this.getClientById(clientId);
        if ( client == null ) {
            return false;
        }
        client.setShoppingCart(new ShoppingCart());;
        return true;
    }

    // place order and empty client's shopping cart
    public Boolean placeOrder(String orderId, String clientId) {
        Client client = this.getClientById(clientId);
        if ( client == null ) {
            return false;
        }
        Order order = new Order(orderId, client);
        OrderList.instance().insertOrder(order);
        emptyCart(client.getClientId());
        return true;
    }

    // get iterator for OrderList
    public Iterator<Order> getOrders() {
        return OrderList.instance().getOrders();
      }
}
