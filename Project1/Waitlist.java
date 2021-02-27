import java.util.*;
import java.io.*;

public class Waitlist implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<WaitItem> waitlist = new LinkedList<WaitItem>();
    private static Waitlist instance;

    public static Waitlist instance() {
        if (instance == null) {
            return (instance = new Waitlist());
        } else {
            return instance;
        }
    }

    public boolean insertWaitItem(Client client, Product product, int quantity) {
        WaitItem item = new WaitItem(client, product, quantity);
        waitlist.add(item);
        return true;
    }

    public Iterator<WaitItem> waitlist() {
        return waitlist.iterator();
    }

    public String toString() {
        return waitlist.toString();
    }
}

