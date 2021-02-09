import java.util.*;
import java.io.*;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientId;
    private String firstName;
    private String lastName;
    private String adress;

    public Client (String firstName, String lastName, String adress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.clientId = clientId; // Todo: auto id generation
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAdress() {
        return adress;
    }

    public String getClientId() {
        return clientId;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public void setAdress(String newAdressName) {
        adress = newAdressName;
    }

    public void setClientId(String newClientId) {
        clientId = newClientId;
    }

    public boolean equals(String id) {
        return this.clientId.equals(id);
    }

    public String toString() {
        String string = "Client id: " + clientId + " Client name: " + firstName + " " + lastName + " Client adress: " + adress;
        return string;
    }
}
