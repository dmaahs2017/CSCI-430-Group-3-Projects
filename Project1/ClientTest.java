import java.util.*;

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

        c1.setClientId("test007");
        System.out.println("client id should be test007 : " + c1.getClientId());

        c1.setFirstName("Alpha");
        c1.setLastName("Bravo");
        System.out.println("client name should be Alpha Bravo : " + c1.getFirstName() + " " + c1.getLastName());

        c1.setAddress("court yard name and city");
        System.out.println("client address should be court yard name and city : " + c1.getAddress());

        System.out.println(list.toString());
    }
}
