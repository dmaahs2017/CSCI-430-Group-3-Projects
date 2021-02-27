import java.io.*;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private String name;
  private int quantity;
  private double salePrice;
  private double supplyPrice;

  public Product(String name, int quantity, double salePrice, double supplyPrice) {
    id = (ProductIdServer.instance()).generateId();
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

  public Boolean equals(String id) {
    return this.id.equals(id);
  }

  public String toString() {
      return "id " + id + " name " + name + " quantity " + quantity + " salePrice " + salePrice + " supplyPrice " + supplyPrice;
  }
}
