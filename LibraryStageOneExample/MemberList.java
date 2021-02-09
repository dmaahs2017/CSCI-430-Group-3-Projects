import java.util.*;
import java.io.*;
public class MemberList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List members = new LinkedList();
  private static MemberList memberList;
  private MemberList() {
  }
  public static MemberList instance() {
    if (memberList == null) {
      return (memberList = new MemberList());
    } else {
      return memberList;
    }
  }

  public boolean insertMember(Member member) {
    members.add(member);
    return true;
  }

  public Iterator getMembers(){
     return members.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(memberList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (memberList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (memberList == null) {
          memberList = (MemberList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  public String toString() {
    return members.toString();
  }
}
