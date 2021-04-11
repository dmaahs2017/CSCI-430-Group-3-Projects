import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import backend.*;
import utils.*;

public class LoginState extends WareState implements ActionListener {
  private static final int CLIENT_LOGIN = 0;
  private static final int CLERK_LOGIN = 1;
  private static final int MANAGER_LOGIN = 2;
  private static final int HELP = 3;
  private static final int EXIT = 4;
  private static LoginState instance;

  private AbstractButton clientButton, clerkButton, managerButton, logoutButton;

  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private JFrame frame;


  private LoginState() {
      super();
  }

  public static LoginState instance() {
    if (instance == null) {
      instance = new LoginState();
    }
    return instance;
  }

  // Clean up the Gui
  public void clear() { 
    frame.getContentPane().removeAll();
    frame.paint(frame.getGraphics());   
  }  

  // ActionListener Interface
  public void actionPerformed(ActionEvent event) {
    if (event.getSource().equals(this.clientButton))
       this.client();
    else if (event.getSource().equals(this.clerkButton)) 
      this.clerk();
    else if (event.getSource().equals(this.managerButton)) 
      this.manager();
    else if (event.getSource().equals(this.logoutButton))
      this.logout();
  } 

  private void logout() {
    clear();
    WareContext.instance().changeState(3);
  }

  private void client(){
    SecuritySystem ss = new SecuritySystem();
    String user = GuiInputUtils.promptInput(frame, "Please input client username: ");
    if (Warehouse.instance().getClientById(user) != null){
      String pass = GuiInputUtils.promptInput(frame, "Please input the client password: ");
      if (ss.verifyPassword(user, pass)) {
        (WareContext.instance()).setLogin(WareContext.IsClient);
        (WareContext.instance()).setUser(user.toString());      
        clear();
        (WareContext.instance()).changeState(0);
      } else {
        GuiInputUtils.informUser(frame, "Invalid Client Password");
      }      
    } else {
      GuiInputUtils.informUser(frame, "Invalid client username.");
    }
  }

  private void clerk(){
    SecuritySystem ss = new SecuritySystem();
    String clerk = GuiInputUtils.promptInput(frame, "Please input the clerk username: ");
    if (clerk.equals("clerk")) { 
      String pass = GuiInputUtils.promptInput(frame, "Please input the clerk password: ");
      if (ss.verifyPassword(clerk, pass)){
        (WareContext.instance()).setLogin(WareContext.IsClerk);
        (WareContext.instance()).setUser("clerk");      
        clear();
        (WareContext.instance()).changeState(1);
      } else {
        GuiInputUtils.informUser(frame, "Invalid clerk password.");
      }
    } else {
      GuiInputUtils.informUser(frame, "Invalid clerk username.");
    }
  }

  private void manager(){
    SecuritySystem ss = new SecuritySystem();
    String manager = GuiInputUtils.promptInput(frame, "Please input the manager username: ");
    if (manager.equals("manager")) { 
      String pass = GuiInputUtils.promptInput(frame, "Please input the manager password: ");
      if (ss.verifyPassword(manager, pass)){
        (WareContext.instance()).setLogin(WareContext.IsManager);
        (WareContext.instance()).setUser("manager");      
        clear();
        (WareContext.instance()).changeState(2);
      } else {
        GuiInputUtils.informUser(frame, "Invalid manager password.");
      }
    } else {
      GuiInputUtils.informUser(frame, "Invalid manager username.");
    }
  } 


  public void run() {
     frame = WareContext.instance().getFrame();
     frame.getContentPane().removeAll();
     frame.getContentPane().setLayout(new FlowLayout());

     // Define Buttons
     clientButton = new JButton("Client");
     clerkButton =  new JButton("Clerk");
     managerButton = new JButton("Manager");
     logoutButton = new JButton("Logout");  

     // Add listeners
     clientButton.addActionListener(this);
     clerkButton.addActionListener(this);
     managerButton.addActionListener(this);
     logoutButton.addActionListener(this);

     // Add Buttons to the frame
     frame.getContentPane().add(this.clientButton);
     frame.getContentPane().add(this.clerkButton);
     frame.getContentPane().add(this.managerButton);
     frame.getContentPane().add(this.logoutButton);

     frame.setVisible(true);
     frame.paint(frame.getGraphics()); 
     frame.toFront();
     frame.requestFocus();
  }
}
