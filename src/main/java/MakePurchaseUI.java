import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class MakePurchaseUI {
    //GENERATE RANDOM 8 DIGIT PURCHASEID WITH RANDOM
    //also get customerid

    public static final double TAX_RATE = .10;

    public JFrame view;

    public JButton button = new JButton("Make Purchase");
    public JButton cancel = new JButton("Cancel");

    public JTextField txtProductID = new JTextField(20);
    // public JTextField txtPurchaseID = new JTextField(20);
    // public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);

    public JLabel labCustomerID = new JLabel("");
    public JLabel labPurchaseID = new JLabel("");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labCost = new JLabel("Cost: ");
    public JLabel labTax = new JLabel("Tax: ");
    public JLabel labTotalCost = new JLabel("Total Cost: ");

    public int purchaseID1;
    public int customerID1;
    public CustomerModel customerModel;
    public MakePurchaseUI(CustomerModel customer) {
        customerModel = customer;
        this.view = new JFrame();
        view.setTitle("Make Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));
        //String[] labels = {"PurchaseID: ", "ProductID: ", "CustomerID: ", "Quantity: "};
        //int numPairs = labels.length;
        Random random = new Random();
        purchaseID1 = random.nextInt(10000000);
        labPurchaseID.setText(Integer.toString(purchaseID1));
        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID "));
        line1.add(labPurchaseID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("ProductID "));
        line2.add(txtProductID);
        view.getContentPane().add(line2);

        customerID1 = customer.mCustomerID;
        labCustomerID.setText(Integer.toString(customerID1));
        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("CustomerID "));
        line3.add(labCustomerID);
        view.getContentPane().add(line3);

     /*    JLabel username1 = new JLabel("UserName: ");
        username1.setText(customer.mUsername);
        JPanel test = new JPanel(new FlowLayout());
        line2.add(new JLabel("username "));
        line2.add(username1);
        view.getContentPane().add(line2);*/

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtQuantity);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        line5.add(labCost);
        line5.add(labTax);
        line5.add(labTotalCost);
        view.getContentPane().add(line5);

        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(button);
        panel.add(cancel);
        view.getContentPane().add(panel);

        button.addActionListener(new AddButtonListener());

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "You click on Cancel button!!!");
            }
        });
    }

    public void run() {
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {

        public void printReceipt(PurchaseModel purchaseModel, CustomerModel customerModel, ProductModel productModel) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            //System.out.println(dateFormat.format(date));
            JOptionPane.showMessageDialog(null, "Date: " + date
                    + "\nPurchaseID: " + purchaseModel.mPurchaseId
                    + "\nCustomerID: " + customerModel.mCustomerID +
                    "       Customer Name: " + customerModel.mName
                    + "\nProductID: " + productModel.mProductID
                    + "     Product Name: " + productModel.mName
                    + "\n" + labCost.getText() + "       " + labTax.getText()
                    + "\n" + labTotalCost.getText(), "Receipt", JOptionPane.PLAIN_MESSAGE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchase = new PurchaseModel();
            Gson gson = new Gson();
            //CustomerModel customerModel;
            ProductModel productModel;
            String purchaseID = Integer.toString(purchaseID1);
            if (purchaseID.equals("")) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!!");
                return;
            }
            try {
                purchase.mPurchaseId = Integer.parseInt(purchaseID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!!");
                return;
            }

            String productID = txtProductID.getText();
            if (productID.equals("")) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be empty!!");
                return;
            }
            purchase.mProductID = Integer.parseInt(productID);
            // labProductName.setText(StoreManager.getInstance().getDataAdapter().loadProductName(Integer.parseInt(productID)));
            String customerID = Integer.toString(customerID1);
            if (customerID.equals("")) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be empty!!");
                return;
            }
            purchase.mCustomerID = Integer.parseInt(customerID);
            //labCustomerName.setText(StoreManager.getInstance().getDataAdapter().loadCustomerID_NAME(Integer.parseInt(customerID)));
            String purchaseQuantity = txtQuantity.getText();
            try {
                purchase.mQuantity = Integer.parseInt(purchaseQuantity);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is Invalid!!");
                return;
            }
            if (purchase.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null, "Quantity is Invalid!!");
            } else {
                try {
                    Socket socket = new Socket("localhost", 1000);
                    Scanner input = new Scanner(socket.getInputStream());
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                    MessageModel msg = new MessageModel();
                    msg.code = MessageModel.SAVE_PURCHASE;
                    msg.data = gson.toJson(purchase);

                    output.println(gson.toJson(msg));

                    msg = gson.fromJson(input.nextLine(), MessageModel.class);

                    if (msg.code == MessageModel.OPERATION_OK) {
                        //customerModel = gson.fromJson(msg.customerData, CustomerModel.class);
                        productModel = gson.fromJson(msg.productData, ProductModel.class);
                        //StoreManager.getInstance().getDataAdapter().disconnect();
                        if (productModel.mName == null || customerModel.mName == null) {
                            JOptionPane.showMessageDialog(null, "Invalid CustomerID or ProductID");
                        } else {
                            DecimalFormat df = new DecimalFormat("$###,###,###.00");
                            labCustomerName.setText("Customer Name: " + customerModel.mName);
                            labProductName.setText("Product Name: " + productModel.mName);
                            labPrice.setText("Price: " + df.format(productModel.mPrice));
                            double costNoTax = productModel.mPrice * purchase.mQuantity;
                            double tax = costNoTax * TAX_RATE;
                            labTax.setText("Tax: " + df.format(tax));
                            labCost.setText("Cost: " + df.format(productModel.mPrice * purchase.mQuantity));
                            // double costNoTax = productModel.mPrice * purchase.mQuantity;
                            double totalCost = costNoTax + costNoTax * TAX_RATE;
                            labTotalCost.setText("Total Cost: " + df.format(totalCost));
                            //JOptionPane.showMessageDialog(null, "You want to add " + purchase + "?");
                            // int number = okcancel("Are you sure?");
                            //if (number == 0) {
                            // int result = StoreManager.getInstance().getDataAdapter().savePurchase(purchase);
                            JOptionPane.showMessageDialog(null, "Purchase added successfully!" + purchase);
                            printReceipt(purchase, customerModel, productModel);

                        }
                    }
                }
                catch (Exception m) {
                    m.printStackTrace();
                }
            }
            //public void printReceipt(purchase)
        }
    }
}
