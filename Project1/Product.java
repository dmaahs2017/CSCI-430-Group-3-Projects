import java.io.*;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private String name;
  private int quantity;
  private double salePrice;
  private double supplyPrice;

  public Product(String id, String name, int quantity, double salePrice, double supplyPrice) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
	this.salePrice = salePrice;
	this.supplyPrice = supplyPrice;
  }

  // getters
  public String getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public int getQuantity() {
    return quantity;
  }
  public double getSalePrice() {
    return salePrice;
  }
  public double getSupplyPrice() {
	  return supplyPrice;
  }
  
  // setters
  public void setId(String newId) {
    id = newId;
  }
  public void setName(String newName) {
    name = newName;
  }
  public void setQuantity(int newQuantity) {
    quantity = newQuantity;
  }
  public void setSalePrice(double newSalePrice) {
    salePrice = newSalePrice;
  }
  public void setSupplyPrice(double newSupplyPrice) {
	supplyPrice = newSupplyPrice;
  }

  public String toString() {
      return "id " + id + " name " + name + " quantity " + quantity + " salePrice " + salePrice + " supplyPrice " + supplyPrice;
  }
}
