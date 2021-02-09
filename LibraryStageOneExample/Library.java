import java.util.*;
import java.io.*;
public class Library implements Serializable {
  private static final long serialVersionUID = 1L;
  public static final int BOOK_NOT_FOUND  = 1;
  public static final int BOOK_NOT_ISSUED  = 2;
  public static final int BOOK_HAS_HOLD  = 3;
  public static final int BOOK_ISSUED  = 4;
  public static final int HOLD_PLACED  = 5;
  public static final int NO_HOLD_FOUND  = 6;
  public static final int OPERATION_COMPLETED= 7;
  public static final int OPERATION_FAILED= 8;
  public static final int NO_SUCH_MEMBER = 9;
  private Catalog catalog;
  private MemberList memberList;
  private static Library library;
  private Library() {
    catalog = Catalog.instance();
    memberList = MemberList.instance();
  }
  public static Library instance() {
    if (library == null) {
      MemberIdServer.instance(); // instantiate all singletons
      return (library = new Library());
    } else {
      return library;
    }
  }
  public Book addBook(String title, String author, String id) {
    Book book = new Book(title, author, id);
    if (catalog.insertBook(book)) {
      return (book);
    }
    return null;
  }
  public Member addMember(String name, String address, String phone) {
    Member member = new Member(name, address, phone);
    if (memberList.insertMember(member)) {
      return (member);
    }
    return null;
  }


  public Iterator getBooks() {
      return catalog.getBooks();
  }

  public Iterator getMembers() {
      return memberList.getMembers();
  }
  public static Library retrieve() {
    try {
      FileInputStream file = new FileInputStream("LibraryData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      MemberIdServer.retrieve(input);
      return library;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }
  public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("LibraryData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(library);
      output.writeObject(MemberIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(library);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (library == null) {
        library = (Library) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  public String toString() {
    return catalog + "\n" + memberList;
  }
}
