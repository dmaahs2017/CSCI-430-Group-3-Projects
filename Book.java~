import java.util.*;
import java.lang.*;
import java.io.*;
public class Book implements Serializable {
  private static final long serialVersionUID = 1L;
  private String title;
  private String author;
  private String id;
  private Member borrowedBy;
  private List holds = new LinkedList();
  private Calendar dueDate;

  public Book(String title, String author, String id) {
    this.title = title;
    this.author = author;
    this.id = id;
  }
  public boolean issue(Member member) {
    borrowedBy = member;
    dueDate = new GregorianCalendar();
    dueDate.setTimeInMillis(System.currentTimeMillis());
    dueDate.add(Calendar.MONTH, 1);
    return true;
  }
  public Member returnBook() {
    if (borrowedBy == null) {
      return null;
    } else {
      Member borrower = borrowedBy;
      borrowedBy = null;
      return borrower;
    }
  }
  public boolean renew(Member member) {
    if (hasHold()) {
      return false;
    }
    if ((member.getId()).equals(borrowedBy.getId())) {
      return (issue(member));
    }
    return false;
  }
  public void placeHold(Hold hold) {
    holds.add(hold);
  }
  public boolean removeHold(String memberId) {
    for (ListIterator iterator = holds.listIterator(); iterator.hasNext(); ) {
      Hold hold = (Hold) iterator.next();
      String id = hold.getMember().getId();
      if (id.equals(memberId)) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }
  public Hold getNextHold() {
    for (ListIterator iterator = holds.listIterator(); iterator.hasNext(); ) {
      Hold hold = (Hold) iterator.next();
      iterator.remove();
      if (hold.isValid()) {
        return hold;
      }
    }
    return null;
  }
  public boolean hasHold() {
    ListIterator iterator = holds.listIterator();
    if (iterator.hasNext()) {
      return true;
    }
    return false;
  }
  public Iterator getHolds() {
    return holds.iterator();
  }
  public String getAuthor() {
    return author;
  }
  public String getTitle() {
    return title;
  }
  public String getId() {
    return id;
  }
  public Member getBorrower() {
    return borrowedBy;
  }
  public String getDueDate() {
      return (dueDate.getTime().toString());
  }
  public String toString() {
    return "title " + title + " author " + author + " id " + id + " borrowed by " + borrowedBy;
  }
}