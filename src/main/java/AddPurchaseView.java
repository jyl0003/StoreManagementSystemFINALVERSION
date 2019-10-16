import javax.swing.*;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class AddPurchaseView {

    public static final double TAX_RATE = .10;

    public JFrame view;

    public JButton button = new JButton("Add");
    public JButton cancel = new JButton("Cancel");

    public JTextField txtProductID = new JTextField(20);
    public JTextField txtPurchaseID = new JTextField(20);
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labDate = new JLabel("Date of Purchase: ");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");

    public JLabel labCost = new JLabel("Cost: ");
    public JLabel labTax = new JLabel("Tax: ");
    public JLabel labTotalCost = new JLabel("Total Cost: ");

    public AddPurchaseView() {
        this.view = new JFrame();
        view.setTitle("Add Purchase");
        view.setSize(600,400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));
        String[] labels = {"PurchaseID: ", "ProductID: ", "CustomerID: ", "Quantity: "};
        //int numPairs = labels.length;

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID "));
        line1.add(txtPurchaseID);
        line1.add(labDate);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("ProductID "));
        line2.add(txtProductID);
        line2.add(labProductName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("CustomerID "));
        line3.add(txtCustomerID);
        line3.add(labCustomerName);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtQuantity);
        line4.add(labPrice);
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
        labDate.setText("Date of purchase: " + java.time.LocalDateTime.now());
        view.setVisible(true);
    }

    public static int okcancel(String theMessage) {
        return JOptionPane.showConfirmDialog((Component) null, theMessage,
                "alert", JOptionPane.OK_CANCEL_OPTION);
    }
    class AddButtonListener implements ActionListener {

        public void printReceipt(PurchaseModel purchaseModel, CustomerModel customerModel, ProductModel productModel) {
            JOptionPane.showMessageDialog(null, "Date: " + java.time.LocalDateTime.now()
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
            CustomerModel customerModel;
            ProductModel productModel;
            String purchaseID = txtPurchaseID.getText();
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
            String customerID = txtCustomerID.getText();
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
            }
            else {
                customerModel = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);
                productModel = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
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
                    JOptionPane.showMessageDialog(null, "You want to add " + purchase + "?");
                    int number = okcancel("Are you sure?");
                    if (number == 0) {
                        String result = StoreManager.getInstance().getDataAdapter().savePurchase(purchase);
                        if (result.contains("Added")) {
                            JOptionPane.showMessageDialog(null, "Purchase added successfully!" + purchase);
                            printReceipt(purchase, customerModel, productModel);
                        } else {
                            JOptionPane.showMessageDialog(null, result);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Purchase Not Added!");
                    }

                }
            }
        }
        //public void printReceipt(purchase)
    }

}