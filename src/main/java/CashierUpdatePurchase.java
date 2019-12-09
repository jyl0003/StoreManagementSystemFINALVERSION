import javax.swing.*;
import java.awt.*;
import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.FlowLayout;
import java.text.DecimalFormat;

public class CashierUpdatePurchase {

    public static final double TAX_RATE = .10;

    public JFrame view;

    //Buttons that allow configuration of purchases
    public JButton btnLoad = new JButton("Load");
    public JButton update = new JButton("Update Purchase");
   // public JButton save = new JButton("Add New Purchase");

    //TextFields
    public JTextField txtPurchaseID = new JTextField(20);
    //public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);

    public JLabel labCustomerName = new JLabel("");
    public JLabel labProductName = new JLabel("");

    //Constructor that allows population of UI
    public CashierUpdatePurchase() {
        this.view = new JFrame();
        view.setTitle("Add Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));
        String[] labels = {"PurchaseID: ", "ProductID: ", "CustomerID: ", "Quantity: "};
        //int numPairs = labels.length;

        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(btnLoad);
        panel.add(update);
        //panel.add(save);
        view.getContentPane().add(panel);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID "));
        line1.add(txtPurchaseID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("ProductID "));
        line2.add(labProductName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("CustomerID "));
        line3.add(labCustomerName);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtQuantity);
        view.getContentPane().add(line4);


        /*JPanel panel = new JPanel((new FlowLayout()));
        panel.add(button);
        panel.add(cancel);
        view.getContentPane().add(panel);*/

     //   save.addActionListener(new SaveButtonListener());

        btnLoad.addActionListener(new LoadActionListener());

        update.addActionListener(new UpdateButtonListener());
    }

    public void run() {
        // labDate.setText("Date of purchase: " + java.time.LocalDateTime.now());
        view.setVisible(true);
    }



    //Load button class and function
    class LoadActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PurchaseModel purchase = new PurchaseModel();
            Gson gson = new Gson();
            CustomerModel customerModel;
            ProductModel productModel;
            String purchaseID = txtPurchaseID.getText();
            if (purchaseID.equals("")) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!!");
                return;
            }
            try {
                purchase.mPurchaseId = Integer.parseInt(purchaseID);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!!");
                return;
            }
            try {
                Socket socket = new Socket("localhost", 1000);
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.LOAD_PURCHASE;
                msg.data = Integer.toString(purchase.mPurchaseId);
                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);
                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Purchase DOES NOT exists!");
                } else {
                    purchase = gson.fromJson(msg.data, PurchaseModel.class);
                    customerModel = gson.fromJson(msg.customerData, CustomerModel.class);
                    productModel = gson.fromJson(msg.productData, ProductModel.class);
                    // StoreManager.getInstance().getDataAdapter().disconnect();
                    //   if (productModel.mName == null || customerModel.mName == null) {
                    //     JOptionPane.showMessageDialog(null, "Invalid CustomerID or ProductID");
                    //  } else {
                    txtQuantity.setText(Double.toString(purchase.mQuantity));
                    labCustomerName.setText(Integer.toString(customerModel.mCustomerID));
                    labProductName.setText(Integer.toString(productModel.mProductID));
                    if (msg.code == MessageModel.OPERATION_OK) {
                        JOptionPane.showMessageDialog(null, "Purchase updated Successfully");
                    }

                }
            } catch (Exception d) {
                d.printStackTrace();
            }

        }
    }

    //Update button listener
    class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PurchaseModel purchase = new PurchaseModel();
            Gson gson = new Gson();
            CustomerModel customerModel;
            ProductModel productModel;
            String purchaseID = txtPurchaseID.getText();
            if (purchaseID.equals("")) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!!");
                return;
            }
            try {
                purchase.mPurchaseId = Integer.parseInt(purchaseID);
            } catch (NumberFormatException a) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!!");
                return;
            }

         /*   String productID = txtProductID.getText();
            if (productID.equals("")) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be empty!!");
                return;
            }
            purchase.mProductID = Integer.parseInt(productID);
            // labProductName.setText(StoreManager.getInstance().getDataAdapter().loadProductName(Integer.parseInt(productID)));
            String customerID = txtCustomerID.getText();
            if (customerID.equals("")) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be empty!!");
                return;
            }*
            purchase.mCustomerID = Integer.parseInt(customerID);*/
            //labCustomerName.setText(StoreManager.getInstance().getDataAdapter().loadCustomerID_NAME(Integer.parseInt(customerID)));
            purchase.mProductID = Integer.parseInt(labProductName.getText());
            purchase.mCustomerID = Integer.parseInt(labCustomerName.getText());
            String purchaseQuantity = txtQuantity.getText();
            try {
                purchase.mQuantity = Integer.parseInt(purchaseQuantity);
            } catch (NumberFormatException el) {
                JOptionPane.showMessageDialog(null, "Quantity is Invalid!!");
                return;
            }
            if (purchase.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null, "Quantity is Invalid!!");
            }
            try {
                Socket socket = new Socket("localhost", 1000);
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.UPDATE_PURCHASE;
                msg.data = gson.toJson(purchase);

                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);
                //purchase = gson.fromJson(msg.data, PurchaseModel.class)
                /*
                output.println("PUT");
                output.println(product.mProductID);
                output.println(product.mName);
                output.println(product.mPrice);
                output.println(product.mQuantity);*/

            } catch (Exception m) {
                m.printStackTrace();
            }
        }
    }
}
