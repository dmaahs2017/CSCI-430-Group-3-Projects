package backend;
import java.util.*;
import java.io.*;

public class InvoiceList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Invoice> invoices = new LinkedList<Invoice>();
  private static InvoiceList invoiceList;
  private InvoiceList() {
  }
  public static InvoiceList instance() {
    if (invoiceList == null) {
      return (invoiceList = new InvoiceList());
    } else {
      return invoiceList;
    }
  }

  public boolean insertInvoice(Invoice invoice) {
    invoices.add(invoice);
    return true;
  }

  public Iterator<Invoice> getInvoices(){
     return invoices.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(invoiceList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (invoiceList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (invoiceList == null) {
          invoiceList = (InvoiceList) input.readObject();
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
    return invoices.toString();
  }
}
