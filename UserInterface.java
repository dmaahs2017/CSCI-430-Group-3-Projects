import java.util.*;
import java.text.*;
import java.io.*;
public class UserInterface {
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Library library;
  private static final int EXIT = 0;
  private static final int ADD_MEMBER = 1;
  private static final int ADD_BOOKS = 2;
  private static final int ISSUE_BOOKS = 3;
  private static final int RETURN_BOOKS = 4;
  private static final int RENEW_BOOKS = 5;
  private static final int REMOVE_BOOKS = 6;
  private static final int PLACE_HOLD = 7;
  private static final int REMOVE_HOLD = 8;
  private static final int PROCESS_HOLD = 9;
  private static final int GET_TRANSACTIONS = 10;
  private static final int SHOW_MEMBERS = 11;
  private static final int SHOW_BOOKS = 12;
  private static final int SAVE = 13;
  private static final int RETRIEVE = 14;
  private static final int HELP = 15;
  private UserInterface() {
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      library = Library.instance();
    }
  }
  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
  }
  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number between 0 and 12 as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_MEMBER + " to add a member");
    System.out.println(ADD_BOOKS + " to  add books");
    System.out.println(ISSUE_BOOKS + " to  issue books to a  member");
    System.out.println(RETURN_BOOKS + " to  return books ");
    System.out.println(RENEW_BOOKS + " to  renew books ");
    System.out.println(REMOVE_BOOKS + " to  remove books");
    System.out.println(PLACE_HOLD + " to  place a hold on a book");
    System.out.println(REMOVE_HOLD + " to  remove a hold on a book");
    System.out.println(PROCESS_HOLD + " to  process holds");
    System.out.println(GET_TRANSACTIONS + " to  print transactions");
    System.out.println(SHOW_MEMBERS + " to  print members");
    System.out.println(SHOW_BOOKS + " to  print books");
    System.out.println(SAVE + " to  save data");
    System.out.println(RETRIEVE + " to  retrieve");
    System.out.println(HELP + " for help");
  }

  public void addMember() {
    String name = getToken("Enter member name");
    String address = getToken("Enter address");
    String phone = getToken("Enter phone");
    Member result;
    result = library.addMember(name, address, phone);
    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println(result);
  }

  public void addBooks() {
    Book result;
    do {
      String title = getToken("Enter  title");
      String bookID = getToken("Enter id");
      String author = getToken("Enter author");
      result = library.addBook(title, author, bookID);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Book could not be added");
      }
      if (!yesOrNo("Add more books?")) {
        break;
      }
    } while (true);
  }
  public void issueBooks() {
         System.out.println("Dummy Action");
  }
  public void renewBooks() {
      System.out.println("Dummy Action");
  }

  public void showBooks() {
      Iterator allBooks = library.getBooks();
      while (allBooks.hasNext()){
	  Book book = (Book)(allBooks.next());
          System.out.println(book.toString());
      }
  }

  public void showMembers() {
      Iterator allMembers = library.getMembers();
      while (allMembers.hasNext()){
	  Member member = (Member)(allMembers.next());
          System.out.println(member.toString());
      }
  }

  public void returnBooks() {
      System.out.println("Dummy Action");
  }
  public void removeBooks() {
      System.out.println("Dummy Action");   
  }
  public void placeHold() {
      System.out.println("Dummy Action");   
  }
  public void removeHold() {
      System.out.println("Dummy Action");   
  }
  public void processHolds() {
      System.out.println("Dummy Action");   
  }
  public void getTransactions() {
      System.out.println("Dummy Action");   
  }
  private void save() {
    if (library.save()) {
      System.out.println(" The library has been successfully saved in the file LibraryData \n" );
    } else {
      System.out.println(" There has been an error in saving \n" );
    }
  }
  private void retrieve() {
    try {
      Library tempLibrary = Library.retrieve();
      if (tempLibrary != null) {
        System.out.println(" The library has been successfully retrieved from the file LibraryData \n" );
        library = tempLibrary;
      } else {
        System.out.println("File doesnt exist; creating new library" );
        library = Library.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }
  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_MEMBER:        addMember();
                                break;
        case ADD_BOOKS:         addBooks();
                                break;
        case ISSUE_BOOKS:       issueBooks();
                                break;
        case RETURN_BOOKS:      returnBooks();
                                break;
        case REMOVE_BOOKS:      removeBooks();
                                break;
        case RENEW_BOOKS:       renewBooks();
                                break;
        case PLACE_HOLD:        placeHold();
                                break;
        case REMOVE_HOLD:       removeHold();
                                break;
        case PROCESS_HOLD:      processHolds();
                                break;
        case GET_TRANSACTIONS:  getTransactions();
                                break;
        case SAVE:              save();
                                break;
        case RETRIEVE:          retrieve();
                                break;
        case SHOW_MEMBERS:	showMembers();
                                break; 		
        case SHOW_BOOKS:	showBooks();
                                break; 		
        case HELP:              help();
                                break;
      }
    }
  }
  public static void main(String[] s) {
    UserInterface.instance().process();
  }
}
