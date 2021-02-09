import java.util.*;
import java.util.function.Supplier;
import java.io.*;

public class SupplierList implements Serializable {
  private List<Supplier> suppliers = new LinkedList<Supplier>();
  private static SupplierList supplierList;
  private SupplierList() {
  }
  public static SupplierList instance() {
    if (supplierList == null) {
      return (supplierList = new SupplierList());
    } else {
      return supplierList;
    }
  }

  public boolean insertSupplier(Supplier supplier) {
    suppliers.add(supplier);
    return true;
  }

  public Iterator getSupplier(){
     return suppliers.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(supplierList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (supplierList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (supplierList == null) {
          supplierList = (SupplierList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  public String toString() {
    return suppliers.toString();
  }
}
