import java.io.*;
public class Supplier implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String SUPPLIER_STRING = "S"; // easy way to see id type
  private String name;
  private String id;


  public Supplier(String name) {
    this.name = name;
    id = SUPPLIER_STRING + (SupplierIdServer.instance()).getId();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getId() {
    return id;
  }

  public Boolean equals(String id) {
    return this.id.equals(id);
  }

  public String toString() {
      return "Name " + name + " id " + id;
  }
}
