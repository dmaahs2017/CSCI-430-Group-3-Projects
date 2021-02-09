import java.io.*;
public class Supplier implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String id;


  public Supplier(String name, String id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void getName(String name) {
      this.name = name;
  }

  public String getId() {
    return id;
  }

  public String toString() {
      return "Name " + name + " id " + id;
  }
}
