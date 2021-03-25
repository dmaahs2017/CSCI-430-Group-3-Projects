package backend;
import java.io.*;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private String name;
  private int quantity;
  private double salePrice;
  private double supplyPrice;
  private String supplierId;

  public Product(String name, int quantity, double salePrice, double supplyPrice, String supplierId) {
    id = (ProductIdServer.instance()).generateId();
    this.name = name;
    this.quantity = quantity;
	this.salePrice = salePrice;
	this.supplyPrice = supplyPrice;
    this.supplierId = supplierId;
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
  public String getSupplierId() {
    return supplierId;
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
  public void setSupplierId(String newSupplierId) {
    supplierId = newSupplierId;
  }

  public Boolean equals(String id) {
    return this.id.equals(id);
  }

  public Boolean equalsSupplierId(String supplierId) {
    return this.supplierId.equals(supplierId);
  }

  public String toString() {
      return "id " + id + " name " + name + " quantity " + quantity + " salePrice " + salePrice + " supplyPrice " + supplyPrice + " supplierId " + supplierId;
  }
}
