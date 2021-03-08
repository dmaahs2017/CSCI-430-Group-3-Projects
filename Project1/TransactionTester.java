import java.util.*;
public class TransactionTester {
  
  public static void main(String[] s) {
    Client c1 = new Client("bob", "tom", "address");
    System.out.println("Test Client Information: " + c1.toString());
    Transaction t1 = new Transaction("order placed", 20.21);
    System.out.println("Test Transaction Information: " + t1.toString());
    c1.getTransactionList().insertTransaction(t1);
    System.out.println("Test Client's Transaction List should include Test Transaction: ");

    Iterator<Transaction> transIterator = c1.getTransactionList().getTransactions();
    while(transIterator.hasNext()) {
      System.out.println(transIterator.next().toString());
    }
  }
}
