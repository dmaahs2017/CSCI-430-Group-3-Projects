import java.util.*;
import java.text.*;
import java.io.*;

public class ClientTest {

    public static void main(String[] s) {
        Client c1 = new Client("jkb", "lrnz", "street name and other things", "lol45");
        Client c2 = new Client("Oscar", "Kilo", "rd name and some things", "ok91");
        ClientList list = ClientList.instance();
        list.insertClient(c1);
        list.insertClient(c2);

        Iterator<Client> clients = list.getClients();
        while (clients.hasNext()) {
            System.out.println(clients.next());
        }

        System.out.println(list.toString());
    }
}
