package backend;
import java.io.Serializable;

public class InventoryItem implements Serializable{
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;

    public InventoryItem(Product p, int q) {
        product = p;
        quantity = q;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    public boolean equals(String id) {
        return this.product.equals(id);
    }

    public String toString() {
        return "Product in inventory: " + product.toString() + ", Quantity in inventory: " + quantity;
    }
}
