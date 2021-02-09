import java.util.*;
import java.lang.*;
import java.io.*;
public class Book implements Serializable {
  private static final long serialVersionUID = 1L;
  private String title;
  private String author;
  private String id;


  public Book(String title, String author, String id) {
    this.title = title;
    this.author = author;
    this.id = id;
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

  public String toString() {
      return "title " + title + " author " + author + " id " + id;
  }
}
