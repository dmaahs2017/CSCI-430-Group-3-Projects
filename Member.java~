import java.util.*;
import java.io.*;
public class Member implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String id;
  private static final String MEMBER_STRING = "M";
  private List booksBorrowed = new LinkedList();
  private List booksOnHold = new LinkedList();
  private List transactions = new LinkedList();
  public  Member (String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    id = MEMBER_STRING + (MemberIdServer.instance()).getId();
  }
  public boolean issue(Book book) {
    if (booksBorrowed.add(book)) {
      transactions.add(new Transaction ("Book issued ", book.getTitle()));
      return true;
    }
    return false;
  }
  public boolean returnBook(Book book) {
    if ( booksBorrowed.remove(book)){
      transactions.add(new Transaction ("Book returned ", book.getTitle()));
      return true;
    }
    return false;
  }

  public boolean renew(Book book) {
    for (ListIterator iterator = booksBorrowed.listIterator(); iterator.hasNext(); ) {
      Book aBook = (Book) iterator.next();
      String id = aBook.getId();
      if (id.equals(book.getId())) {
        transactions.add(new Transaction ("Book renewed ",  book.getTitle()));
        return true;
      }
    }
    return false;
  }
  public Iterator getBooksIssued() {
    return (booksBorrowed.listIterator());
  }
  public void placeHold(Hold hold) {
    transactions.add(new Transaction ("Hold Placed ", hold.getBook().getTitle()));
    booksOnHold.add(hold);
  }
  public boolean removeHold(String bookId) {
    for (ListIterator iterator = booksOnHold.listIterator(); iterator.hasNext(); ) {
      Hold hold = (Hold) iterator.next();
      String id = hold.getBook().getId();
      if (id.equals(bookId)) {
        transactions.add(new Transaction ("Hold Removed ", hold.getBook().getTitle()));
        iterator.remove();
        return true;
      }
    }
    return false;
  }
  public Iterator getTransactions(Calendar date) {
    List result = new LinkedList();
    for (Iterator iterator = transactions.iterator(); iterator.hasNext(); ) {
      Transaction transaction = (Transaction) iterator.next();
      if (transaction.onDate(date)) {
        result.add(transaction);
      }
    }
    return (result.iterator());
  }
  public String getName() {
    return name;
  }
  public String getPhone() {
    return phone;
  }
  public String getAddress() {
    return address;
  }
  public String getId() {
    return id;
  }
  public void setName(String newName) {
    name = newName;
  }
  public void setAddress(String newAddress) {
    address = newAddress;
  }
  public void setPhone(String newPhone) {
    phone = newPhone;
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public String toString() {
    String string = "Member name " + name + " address " + address + " id " + id + "phone " + phone;
    string += " borrowed: [";
    for (Iterator iterator = booksBorrowed.iterator(); iterator.hasNext(); ) {
      Book book = (Book) iterator.next();
      string += " " + book.getTitle();
    }
    string += "] holds: [";
    for (Iterator iterator = booksOnHold.iterator(); iterator.hasNext(); ) {
      Hold hold = (Hold) iterator.next();
      string += " " + hold.getBook().getTitle();
    }
    string += "] transactions: [";
    for (Iterator iterator = transactions.iterator(); iterator.hasNext(); ) {
      string += (Transaction) iterator.next();
    }
    string += "]";
    return string;
  }
}