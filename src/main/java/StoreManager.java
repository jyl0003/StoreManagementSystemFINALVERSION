import javax.swing.*;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class StoreManager extends JFrame{

  /*  public JButton addProduct = new JButton("Add Product");
    public JButton addCustomer = new JButton("Add Customer");

    public StoreManager() {
        this.setTitle("Add Customer");
        this.setSize(600,400);
        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(addProduct);
        panel.add(addCustomer);
    }*/

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Hello class!");
        //Class.forName("org.sqlite.jdbc");
        SQLiteDataAdapter adapter = new SQLiteDataAdapter();
        adapter.connect();
        /*AddProductView apView = new AddProductView();
        AddProductController apCtr = new AddProductController(apView, adapter);
        apView.setVisible(true);*/

        AddCustomerView cstmrView = new AddCustomerView();
        AddCustomerController cstmrCtr = new AddCustomerController(cstmrView, adapter);
        cstmrView.setVisible(true);
    }
}
