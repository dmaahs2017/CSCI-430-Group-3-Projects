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
        while (clients.hasNext() || p != null) {
            Client tmp = clients.next();
            if ( tmp.getClientId() == id ) {
                p = tmp;
            }
        }

        return p;
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
    public Supplier addSuplier(String name, String id) {
        Supplier supplier = new Supplier(name, id);

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
        while (suppliers.hasNext() || s != null) {
            Supplier tmp = suppliers.next();
            if ( tmp.getId() == id ) {
                s = tmp;
            }
        }

        return s;
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
}
