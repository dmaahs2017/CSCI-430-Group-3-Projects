import java.io.*;
public class MemberIdServer implements Serializable {
  private  int idCounter;
  private static MemberIdServer server;
  private MemberIdServer() {
    idCounter = 1;
  }
  public static MemberIdServer instance() {
    if (server == null) {
      return (server = new MemberIdServer());
    } else {
      return server;
    }
  }
  public int getId() {
    return idCounter++;
  }
  public String toString() {
    return ("IdServer" + idCounter);
  }
  public static void retrieve(ObjectInputStream input) {
    try {
      server = (MemberIdServer) input.readObject();
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
        server = (MemberIdServer) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
}