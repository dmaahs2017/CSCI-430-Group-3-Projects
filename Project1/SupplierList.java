import java.util.*;
import java.io.*;

public class SupplierList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Supplier> suppliers = new LinkedList<Supplier>();
    private static SupplierList supplierList;

    public static SupplierList instance() {
        if (supplierList == null) {
            return (supplierList = new SupplierList());
        } else {
            return supplierList;
        }
    }

    public boolean insertSupplier(Supplier client) {
        suppliers.add(client);
        return true;
    }

    public Iterator<Supplier> getSuppliers() {
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
