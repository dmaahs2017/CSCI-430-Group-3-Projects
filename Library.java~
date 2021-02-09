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
  public int placeHold(String memberId, String bookId, int duration) {
    Book book = catalog.search(bookId);
    if (book == null) {
      return(BOOK_NOT_FOUND);
    }
    if (book.getBorrower() == null) {
      return(BOOK_NOT_ISSUED);
    }
    Member member = memberList.search(memberId);
    if (member == null) {
      return(NO_SUCH_MEMBER);
    }
    Hold hold = new Hold(member, book, duration);
    book.placeHold(hold);
    member.placeHold(hold);
    return(HOLD_PLACED);
  }
  public Member searchMembership(String memberId) {
    return memberList.search(memberId);
  }
  public Member processHold(String bookId) {
    Book book = catalog.search(bookId);
    if (book == null) {
      return (null);
    }
    Hold hold = book.getNextHold();
    if (hold == null) {
      return (null);
    }
    hold.getMember().removeHold(bookId);
    hold.getBook().removeHold(hold.getMember().getId());
    return (hold.getMember());
  }
  public int removeHold(String memberId, String bookId) {
    Member member = memberList.search(memberId);
    if (member == null) {
      return (NO_SUCH_MEMBER);
    }
    Book book = catalog.search(bookId);
    if (book == null) {
      return(BOOK_NOT_FOUND);
    }
    return member.removeHold(bookId) && book.removeHold(memberId)? OPERATION_COMPLETED: NO_HOLD_FOUND;
  }
  public void removeInvalidHolds() {
    for (Iterator catalogIterator = catalog.getBooks(); catalogIterator.hasNext(); ) {
      for (Iterator iterator = ((Book) catalogIterator.next()).getHolds(); iterator.hasNext(); ) {
        Hold hold = (Hold) iterator.next();
        if (!hold.isValid()) {
          hold.getBook().removeHold(hold.getMember().getId());
          hold.getMember().removeHold(hold.getBook().getId());
        }
      }
    }
  }
  public Book issueBook(String memberId, String bookId) {
    Book book = catalog.search(bookId);
    if (book == null) {
      return(null);
    }
    if (book.getBorrower() != null) {
      return(null);
    }
    Member member = memberList.search(memberId);
    if (member == null) {
      return(null);
    }
    if (!(book.issue(member) && member.issue(book))) {
      return null;
    }
    return(book);
  }
  public Book renewBook(String bookId, String memberId) {
    Book book = catalog.search(bookId);
    if (book == null) {
      return(null);
    }
    Member member = memberList.search(memberId);
    if (member == null) {
      return(null);
    }
    if ((book.renew(member) && member.renew(book))) {
      return(book);
    }
    return(null);
  }
  public Iterator getBooks(String memberId) {
    Member member = memberList.search(memberId);
    if (member == null) {
      return(null);
    } else {
      return (member.getBooksIssued());
    }
  }
  public int removeBook(String bookId) {
    Book book = catalog.search(bookId);
    if (book == null) {
      return(BOOK_NOT_FOUND);
    }
    if (book.hasHold()) {
      return(BOOK_HAS_HOLD);
    }
    if ( book.getBorrower() != null) {
      return(BOOK_ISSUED);
    }
    if (catalog.removeBook(bookId)) {
      return (OPERATION_COMPLETED);
    }
    return (OPERATION_FAILED);
  }
  public int returnBook(String bookId) {
    Book book = catalog.search(bookId);
    if (book == null) {
      return(BOOK_NOT_FOUND);
    }
    Member member = book.returnBook();
    if (member == null) {
      return(BOOK_NOT_ISSUED);
    }
    if (!(member.returnBook(book))) {
      return(OPERATION_FAILED);
    }
    if (book.hasHold()) {
      return(BOOK_HAS_HOLD);
    }
    return(OPERATION_COMPLETED);
  }
  public Iterator getTransactions(String memberId, Calendar date) {
    Member member = memberList.search(memberId);
    if (member == null) {
      return(null);
    }
    return member.getTransactions(date);
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