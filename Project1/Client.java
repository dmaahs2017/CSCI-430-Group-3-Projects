import java.io.*;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientId;
    private String firstName;
    private String lastName;
    private String address;
    private static final String CLIENT_STRING = "C"; // easy way to distinguish diffrent class ids
    private ShoppingCart shoppingCart;

    public Client (String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        clientId = CLIENT_STRING + (ClientIdServer.instance()).getId();
        shoppingCart = new ShoppingCart();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getClientId() {
        return clientId;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public void setAddress(String newAddressName) {
        address = newAddressName;
    }

    public void setClientId(String newClientId) {
        clientId = newClientId;
    }

    public void setShoppingCart(ShoppingCart cart) {
        shoppingCart = cart;
    }

    public boolean equals(String id) {
        return this.clientId.equals(id);
    }

    public String toString() {
        String string = "Client id: " + clientId + " Client name: " + firstName + " " + lastName + " Client address: " + address;
        return string;
    }
}
