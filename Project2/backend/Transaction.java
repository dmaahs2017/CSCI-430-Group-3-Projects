package backend;
import java.io.*;
import java.text.*;
import java.util.*;

public class Transaction implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
  private String date;
  private String description;
  private double amount;


  public Transaction(String desc, double amt) {
    date = dateFormat.format(new Date());
    description = desc;
    amount = amt;
  }

  public String getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

  public double getAmount() {
    return amount;
  }

  public String toString() {
      return "date: " + date + ", description: " + description + ", Transaction Amount: $" + amount;
  }
}
