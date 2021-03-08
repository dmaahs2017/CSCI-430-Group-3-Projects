public class WaitItem {
    private Client client;
    private Product product;
    private int quantity;
    private boolean orderFilled;

    public WaitItem(Client c, Product p, int q) {
        client = c;
        product = p;
        quantity = q;
        orderFilled = false;
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

    public boolean getOrderFilled() {
        return orderFilled;
    }

    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    public void setOrderFilled(boolean bool) {
        orderFilled = bool;
    }

    public String toString() {
        return "Order Filled Status: " + orderFilled + "; Client: " + client.toString() + " is waiting on " + quantity + " of " + " Product: " + product.toString();
    }
}
