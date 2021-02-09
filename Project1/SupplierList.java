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

    public String toString() {
        return suppliers.toString();
    }
}
