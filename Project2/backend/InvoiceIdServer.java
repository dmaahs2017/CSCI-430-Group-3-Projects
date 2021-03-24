package backend;
import java.io.*;

public class InvoiceIdServer implements Serializable {
  private static final long serialVersionUID = 1L;
  private  int idCounter;
  private static InvoiceIdServer server;
  private InvoiceIdServer() {
    idCounter = 0;
  }
  public static InvoiceIdServer instance() {
    if (server == null) {
      return (server = new InvoiceIdServer());
    } else {
      return server;
    }
  }
  public String generateId() {
    idCounter++;
    return "i" + idCounter;
  }
  public String toString() {
    return ("IdServer" + idCounter);
  }
  public static void retrieve(ObjectInputStream input) {
    try {
      server = (InvoiceIdServer) input.readObject();
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }
  private void writeObject(java.io.ObjectOutputStream output) throws IOException {
    try {
      output.defaultWriteObject();
      output.writeObject(server);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
    try {
      input.defaultReadObject();
      if (server == null) {
        server = (InvoiceIdServer) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
