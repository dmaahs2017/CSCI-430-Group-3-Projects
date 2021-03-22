public class SecuritySystem {
  public Boolean verifyPassword(String user, String pass) {
    if(user.equals(pass)) {
      return true;
    } else {
      return false;
    }
  }
}
