package backend;
import java.util.*;
import java.io.*;

public class TransactionList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Transaction> transactionList;

  public TransactionList() {
    transactionList = new LinkedList<Transaction>();
  }

  public boolean insertTransaction(Transaction transaction) {
    transactionList.add(transaction);
    return true;
  }
  
  public Iterator<Transaction> getTransactions() {
    return transactionList.iterator();
  }
  
  public String toString() {
    return transactionList.toString();
  }
}
