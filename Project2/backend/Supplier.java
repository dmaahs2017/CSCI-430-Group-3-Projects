package backend;
import java.io.*;
public class Supplier implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String id;


  public Supplier(String name) {
    this.name = name;
    id = (SupplierIdServer.instance()).generateId();
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
