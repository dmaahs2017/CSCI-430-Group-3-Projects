import java.util.*;

public class SupplierTester {
    public static void main(String[] args) {
         Supplier s1 = new Supplier("s1_name");
         Supplier s2 = new Supplier("s2_name");

         SupplierList supplierList = SupplierList.instance();
         supplierList.insertSupplier(s1);
         supplierList.insertSupplier(s2);

         //test getName
         System.out.println(s1.getName() + " should be 's1_name'");
         System.out.println(s2.getName() + " should be 's2_name'");

         //test getId
         System.out.println(s1.getId() + " should be 'S1'");
         System.out.println(s2.getId() + " should be 'S2'");

         //test setName
         s1.setName("s1_new_name");
         System.out.println(s1.getId() + " should be 's1_new_name'");

         
         System.out.println("Should print 2 suppliers:");
         Iterator<Supplier> suppliers = supplierList.getSuppliers();
         while(suppliers.hasNext()) {
           System.out.println(suppliers.next());
         }
    }
}
