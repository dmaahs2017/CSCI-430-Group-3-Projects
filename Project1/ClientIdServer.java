import java.io.*;
public class ClientIdServer implements Serializable {
  private  int idCounter;
  private static ClientIdServer server;
  private ClientIdServer() {
    idCounter = 1;
  }
  public static ClientIdServer instance() {
    if (server == null) {
      return (server = new ClientIdServer());
    } else {
      return server;
    }
  }
  public String generateId() {
    idCounter++;
    return "c" + idCounter;
  }
  public String toString() {
    return ("IdServer" + idCounter);
  }
  public static void retrieve(ObjectInputStream input) {
    try {
      server = (ClientIdServer) input.readObject();
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
        server = (ClientIdServer) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
