public class WaitItem {
    private Client client;
    private Product product;
    private int quantity;

    public WaitItem(Client c, Product p, int q) {
        client = c;
        product = p;
        quantity = q;
    }

    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString() {
        return "Client: " + client.toString() + " is waiting on " + quantity + " of " + " Product: " + product.toString();
    }
}
