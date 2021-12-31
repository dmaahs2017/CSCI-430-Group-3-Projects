import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.*;
import java.text.*;
import java.io.*;
import backend.*;
import utils.*;

public class ManagerState extends WareState {
  private static ManagerState managerState = new ManagerState();
  private static Warehouse warehouse;

  // GUI Fields
  private JFrame frame;
  private AbstractButton logoutButton, addSupplierButton, addProductsButton, modifyPurchasePriceButton, addSupplierForProductButton,
          showSuppliersButton, showSuppliersForProductButton, showProductsForSupplierButton, becomeClientButton, becomeClerkButton;

  // Constructor
  private ManagerState() {
    warehouse = Warehouse.instance();
  }

  // Instance
  public static ManagerState instance() {
      return managerState;
  }

  public void addSupplier() {
    String name = GuiInputUtils.promptInput(frame, "Enter supplier name");

    Supplier result = warehouse.addSupplier(name);

    if (result == null) {
      GuiInputUtils.informUser(frame, "Could not add member");
    }

    GuiInputUtils.informUser(frame, "Added Supplier:\n\t" + result.toString());
  }

  public void addProducts() {
    Product result;
    do {
      String name = GuiInputUtils.promptInput(frame, "Enter name");
      int quantity = GuiInputUtils.getNumber(frame, "Enter quantity");
      double salePrice = GuiInputUtils.getDouble(frame, "Enter Sale Price");
      double supplyPrice = GuiInputUtils.getDouble(frame, "Enter Supply Price");
      String supplierId = GuiInputUtils.promptInput(frame, "Enter Supplier Id");

      result = warehouse.addProduct(name, quantity, salePrice, supplyPrice, supplierId);

      if (result != null) {
        GuiInputUtils.informUser(frame, "Product Added:\n\t" + result.toString());
      } else {
        GuiInputUtils.informUser(frame, "Product could not be added");
      }
      if (!GuiInputUtils.yesOrNo(frame, "Add more products?")) {
        break;
      }
    } while (true);
  }

  public void showSuppliers() {
      Iterator<Supplier> allSuppliers = warehouse.getSuppliers();

      String output = new String();
      while (allSuppliers.hasNext()){
        Supplier supplier = allSuppliers.next();
        output += "\t" + supplier.toString() + "\n";
      }

      if (output.length() > 0)
        GuiInputUtils.informUser(frame, "Suppliers:\n" + output);
      else 
        GuiInputUtils.informUser(frame, "No Suppliers Exist");
  }

  public void showSuppliersForProduct() {
      String targetProductId = GuiInputUtils.promptInput(frame, "Enter product Id");
      Iterator<Product> allProducts = warehouse.getProducts();

      String output = new String();
      while (allProducts.hasNext()){
        Product product = allProducts.next();
        if (product.equals(targetProductId)) {
          output += "\tSupplier: " + product.getSupplierId() + "\tPurchase Price: $" + product.getSalePrice() + "\n";
        }
      }

      if (output.length() > 0)
        GuiInputUtils.informUser(frame, "Suppliers For Product:\n" + output);
      else 
        GuiInputUtils.informUser(frame, "No Suppliers Exist For Product with Id " + targetProductId);
  }

  public void showProductsForSupplier() {
      String targetSupplierId = GuiInputUtils.promptInput(frame, "Enter supplierId");
      Iterator<Product> allProducts = warehouse.getProducts();


      String output = new String();
      while (allProducts.hasNext()){
        Product product = allProducts.next();
        if (product.equalsSupplierId(targetSupplierId)) {
          output += "\t" + product.toString() + "\n";
        }
      }

      if (output.length() > 0)
        GuiInputUtils.informUser(frame, "Products from Supplier " + targetSupplierId + "\n" + output);
      else 
        GuiInputUtils.informUser(frame, "Supplier supplies no products");
  }

  private void becomeClerk() {
    (WareContext.instance()).changeState(2);
  }

  private void becomeClient() {
    String user = GuiInputUtils.promptInput(frame, "Please input the client id: ");
    if (Warehouse.instance().getClientById(user) != null){
      (WareContext.instance()).setUser(user.toString());      
      (WareContext.instance()).changeState(1);
    } else {
      GuiInputUtils.informUser(frame, "Invalid client id.");
    }
  }


  public void run() {
     frame = WareContext.instance().getFrame();
     frame.getContentPane().removeAll();
     frame.getContentPane().setLayout(new FlowLayout());

     logoutButton = new JButton("Logout");
     addSupplierButton = new JButton("Add Supplier");
     addProductsButton = new JButton("Add New Products");
     modifyPurchasePriceButton = new JButton("Modify Purchase Price for Product");
     addSupplierForProductButton = new JButton("Add Supplier For Product");
     showSuppliersButton = new JButton("Show All Suppliers");
     showSuppliersForProductButton = new JButton("Show Suppliers for a Product");
     showProductsForSupplierButton = new JButton("Show a Supplier's Products");
     becomeClientButton = new JButton("Become a Client");
     becomeClerkButton = new JButton("Become a Clerk");


     // Add listeners 
     logoutButton.addActionListener(x -> this.logout());
     addSupplierButton.addActionListener(x -> this.addSupplier());
     addProductsButton.addActionListener(x -> this.addProducts());
     modifyPurchasePriceButton.addActionListener(x -> this.addProducts());
     addSupplierForProductButton.addActionListener(x -> this.addSupplier());
     showSuppliersButton.addActionListener(x -> this.showSuppliers());
     showSuppliersForProductButton.addActionListener(x -> this.showSuppliersForProduct());
     showProductsForSupplierButton.addActionListener(x -> this.showProductsForSupplier());
     becomeClientButton.addActionListener(x -> this.becomeClient());
     becomeClerkButton.addActionListener(x -> this.becomeClerk());

     // Add Buttons to the frame
     frame.getContentPane().add(this.addSupplierButton);
     frame.getContentPane().add(this.addProductsButton);
     frame.getContentPane().add(this.modifyPurchasePriceButton);
     frame.getContentPane().add(this.addSupplierButton);
     frame.getContentPane().add(this.showSuppliersButton);
     frame.getContentPane().add(this.showSuppliersForProductButton);
     frame.getContentPane().add(this.showProductsForSupplierButton);
     frame.getContentPane().add(this.becomeClientButton);
     frame.getContentPane().add(this.becomeClerkButton);
     frame.getContentPane().add(this.logoutButton);

     frame.setVisible(true);
     frame.paint(frame.getGraphics()); 
     frame.toFront();
     frame.requestFocus();
  }

  public void logout()
  {
    if (WareContext.instance().getLogin() == WareContext.IsManager) 
      WareContext.instance().changeState(0); // exit to login with a code 0
    else
      WareContext.instance().changeState(3); // exit code 3, indicates error
  }
}
