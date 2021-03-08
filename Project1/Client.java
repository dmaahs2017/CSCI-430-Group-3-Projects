import java.io.*;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientId;
    private String firstName;
    private String lastName;
    private String address;
    private double balance;
    private ShoppingCart shoppingCart;
    private TransactionList transactionList;

    public Client (String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        clientId = (ClientIdServer.instance()).generateId();
        balance = 0;
        shoppingCart = new ShoppingCart();
        transactionList = new TransactionList();
        
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

    public double getBalance() {
        return balance;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public TransactionList getTransactionList() {
        return transactionList;
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

    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public void addBalance(double bal) {
        balance += bal;
    }

    public void subtractBalance(double bal) {
        balance -= bal;
    }

    public boolean equals(String id) {
        return this.clientId.equals(id);
    }

    public String toString() {
        String string = "Client id: " + clientId + " Client name: " + firstName + " " + lastName + " Client address: " + address + " Client balance: " + balance;
        return string;
    }
}
