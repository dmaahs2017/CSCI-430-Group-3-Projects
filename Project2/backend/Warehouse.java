package backend;
import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Warehouse warehouse;
    private ClientList clientList;
    private Inventory inventory;
    private InvoiceList invoiceList;
    private OrderList orderList;
    private ProductList productList;
    private SupplierList supplierList;
    private Waitlist waitlist;
    private ClientIdServer clientIdServer;
    private InvoiceIdServer invoiceIdServer;
    private OrderIdServer orderIdServer;
    private ProductIdServer productIdServer;
    private SupplierIdServer supplierIdServer;

    private Warehouse() {
        clientList = ClientList.instance();
        inventory = Inventory.instance();
        invoiceList = InvoiceList.instance();
        orderList = OrderList.instance();
        productList = ProductList.instance();
        supplierList = SupplierList.instance();
        waitlist = Waitlist.instance();
        clientIdServer = ClientIdServer.instance();
        invoiceIdServer = InvoiceIdServer.instance();
        orderIdServer = OrderIdServer.instance();
        productIdServer = ProductIdServer.instance();
        supplierIdServer = SupplierIdServer.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            return warehouse = new Warehouse();
        } else {
            return warehouse;
        }
    }

    public static Warehouse retrieve() {
        try {
          FileInputStream file = new FileInputStream("WarehouseData");
          ObjectInputStream input = new ObjectInputStream(file);
          input.readObject();
          input.close();
          return warehouse;
        } catch(IOException ioe) {
          ioe.printStackTrace();
          return null;
        } catch(ClassNotFoundException cnfe) {
          cnfe.printStackTrace();
          return null;
        }
      }
      public static  boolean save() {
        try {
          FileOutputStream file = new FileOutputStream("WarehouseData");
          ObjectOutputStream output = new ObjectOutputStream(file);
          output.writeObject(Warehouse.instance());
          output.writeObject(ClientIdServer.instance());
          output.writeObject(InvoiceIdServer.instance());
          output.writeObject(InvoiceList.instance());
          output.writeObject(OrderIdServer.instance());
          output.writeObject(OrderList.instance());
          output.writeObject(ProductIdServer.instance());
          output.writeObject(ProductList.instance());
          output.writeObject(SupplierIdServer.instance());
          output.writeObject(SupplierList.instance());
          output.writeObject(Waitlist.instance());
          output.close();
          return true;
        } catch(IOException ioe) {
          ioe.printStackTrace();
          return false;
        }
      }
      private void writeObject(java.io.ObjectOutputStream output) {
        try {
          output.defaultWriteObject();
          output.writeObject(warehouse);
        } catch(IOException ioe) {
          System.out.println(ioe);
        }
      }
      private void readObject(java.io.ObjectInputStream input) {
        try {
          input.defaultReadObject();
          if (warehouse == null) {
            warehouse = (Warehouse) input.readObject();
          } else {
            input.readObject();
          }
        } catch(IOException ioe) {
          ioe.printStackTrace();
        } catch(Exception e) {
          e.printStackTrace();
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
    public Product addProduct(String name, int quantity, double salePrice, double supplyPrice, String supplierId) {
        Product product = new Product(name, quantity, salePrice, supplyPrice, supplierId);

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

    //get item in inventory by id
    public InventoryItem getInventoryItemById(String id) {
        Iterator<InventoryItem> inventory = Inventory.instance().getInventory();

        InventoryItem item = null;
        while (inventory.hasNext() && item == null) {
            InventoryItem tmp = inventory.next();
            if ( tmp.equals(id)) {
                item = tmp;
            }
        }

        return item;
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
        Iterator<ShoppingCartItem> cartIterator = client.getShoppingCart().getShoppingCartProducts();
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
    public Boolean placeOrder(String clientId) {
        Client client = this.getClientById(clientId);
        if ( client == null ) {
            return false;
        }

        Iterator<ShoppingCartItem> cartIterator = client.getShoppingCart().getShoppingCartProducts();
        while(cartIterator.hasNext()) {
            ShoppingCartItem cartItem = cartIterator.next();
            String productId = cartItem.getProduct().getId();
            InventoryItem inventoryItem = getInventoryItemById(productId);
            
            if(inventoryItem != null) {
                int quantityInStock = inventoryItem.getQuantity();
                int cartQuantity = cartItem.getQuantity();
                int newQuantityInStock = 0;
                newQuantityInStock = quantityInStock - cartQuantity;
                if(newQuantityInStock < 0) {
                    int waitItemQuantity = newQuantityInStock * -1;
                    waitlistItem(clientId, productId, waitItemQuantity);
                    inventoryItem.setQuantity(0);
                } else {
                    inventoryItem.setQuantity(newQuantityInStock);
                }
            }
        }


        Transaction transaction = new Transaction("Order Placed", client.getShoppingCart().getTotalPrice());
        client.getTransactionList().insertTransaction(transaction);
        Order order = new Order(client);
        Invoice invoice = new Invoice(order);
        OrderList.instance().insertOrder(order);
        InvoiceList.instance().insertInvoice(invoice);
        client.subtractBalance(client.getShoppingCart().getTotalPrice());
        emptyCart(client.getClientId());
        return true;
    }

    // get iterator for OrderList
    public Iterator<Order> getOrders() {
        return OrderList.instance().getOrders();
    }

    // waitlist an N number of a product
    public Boolean waitlistItem(String clientId, String productId, int quantity) {
        Client c = getClientById(clientId);
        Product p = getProductById(productId);

        // can't waitlist a nonexistent item
        // Do not want to waitlist for quantity <= 0
        if ( c == null || p == null || quantity <= 0 ) {
            return false;
        }

        Waitlist waitlist = Waitlist.instance();
        waitlist.insertWaitItem(c, p, quantity);

        return true;
    }

    // get iterator for Waitlist
    public Iterator<WaitItem> getWaitlist() {
        return Waitlist.instance().waitlist();
    }
    
    // get iterator for InvoiceList
    public Iterator<Invoice> getInvoices() {
        return InvoiceList.instance().getInvoices();
    }

    //get order by id
    public Order getOrderById(String id) {
        Iterator<Order> orders = OrderList.instance().getOrders();

        Order o = null;
        while (orders.hasNext() && o == null) {
            Order tmp = orders.next();
            if ( tmp.equals(id)) {
                o = tmp;
            }
        }

        return o;
    }

    //get invoice by id
    public Invoice getInvoiceById(String id) {
        Iterator<Invoice> invoices = InvoiceList.instance().getInvoices();

        Invoice i = null;
        while (invoices.hasNext() && i == null) {
            Invoice tmp = invoices.next();
            if ( tmp.equals(id)) {
                i = tmp;
            }
        }

        return i;
    }

    //get total quantity of a waitlisted product by id
    public int getWaitlistProductQuantity(String productId) {
        int quantity = 0;
        Product p = getProductById(productId);
        Iterator<WaitItem> waitlistIterator = getWaitlist();

        while (waitlistIterator.hasNext() && p != null) {
            WaitItem item = waitlistIterator.next();
            if(p.equals(item.getProduct().getId()) && !item.getOrderFilled()) {
                quantity += item.getQuantity();
            }
        }

        return quantity;
    }

    //get a list of items on the waitlist with the mathcing productId
    public List<WaitItem> getWaitItemsByProductId(String id) {
        List<WaitItem> foundItemsList = new LinkedList<WaitItem>();
        Iterator<WaitItem> waitlist = Waitlist.instance().waitlist();

        Product p = getProductById(id);
        while (waitlist.hasNext() && p != null) {
            WaitItem item = waitlist.next();
            if(p.equals(item.getProduct().getId()) && !item.getOrderFilled()) {
                foundItemsList.add(item);
            }
        }

        return foundItemsList;
    }

    // add product to inventory
    public Boolean addToInventory(String productId, int quantity) {
        Product product = this.getProductById(productId);
        if ( product == null ) {
            return false;
        }
        InventoryItem item = getInventoryItemById(productId);
        if(item == null) {
            Inventory.instance().addToInventory(product, quantity);
        } else {
            int currentQuantity = item.getQuantity();
            int newQuantity = currentQuantity += quantity;
            item.setQuantity(newQuantity);
        }
        return true;
    }

    // make payment
    public Boolean makePayment(String clientId, double amount) {
        Client client = this.getClientById(clientId);
        if ( client == null ) {
            return false;
        }
        client.addBalance(amount);
        Transaction transaction = new Transaction("Payment Made", amount);
        client.getTransactionList().insertTransaction(transaction);
        return true;
    }

    // get a client's transactions
    public Iterator<Transaction> getTransactions(String clientId) {
        Client client = this.getClientById(clientId);
        if ( client == null ) {
            return null;
        }
        return client.getTransactionList().getTransactions();
    }

    public Iterator<InventoryItem> getInventory() {
        return Inventory.instance().getInventory();
    }

    public Boolean addProductToInventory(String id, int quantity) {
        Product p = getProductById(id);
        if(p == null) {
            return false;
        }
        Inventory.instance().addToInventory(p, quantity);
        return true;
    }
    
}
