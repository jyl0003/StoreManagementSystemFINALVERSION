import javax.swing.*;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerView {
    public JFrame view;
    public JButton addButton = new JButton("Add");
    public JButton cancel = new JButton("Cancel");

    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);
    public JTextField txtPaymentInfo = new JTextField(20);

    public AddCustomerView() {
        this.view = new JFrame();
        view.setTitle("Add Customer");
        view.setSize(600,400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));
        String[] labels = {"CustomerID: ", "Name: ", "Address: ", "Phone: ", "Payment Info: "};
        int numPairs = labels.length;

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID "));
        line1.add(txtCustomerID);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name "));
        line2.add(txtName);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address "));
        line3.add(txtAddress);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone "));
        line4.add(txtPhone);

        JPanel line5 = new JPanel(new FlowLayout());
        line5.add(new JLabel("Payment Info "));
        line5.add(txtPaymentInfo);
//Create and populate the panel.
        view.getContentPane().add(line1);
        view.getContentPane().add(line2);
        view.getContentPane().add(line3);
        view.getContentPane().add(line4);
        view.getContentPane().add(line5);

        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(addButton);
        panel.add(cancel);
        view.getContentPane().add(panel);

        addButton.addActionListener(new AddButtonListener());

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

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            String id = txtCustomerID.getText();
            if (id.equals("")) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!!");
                return;
            }
            try {
                customer.mCustomerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!!");
                return;
            }
            // product.mProductID = Integer.parseInt(AddProductController.this.view.txtProductID.getText());
            String name = txtName.getText();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty!!");
                return;
            }
            customer.mName = name;

            String address = txtAddress.getText();
            if (address.equals("")) {
                JOptionPane.showMessageDialog(null, "Address cannot be empty!!");
                return;
            }
            customer.mAddress = address;

            String phone = txtPhone.getText();
            if (phone.equals("")) {
                JOptionPane.showMessageDialog(null, "Phone cannot be empty!!");
                return;
            }
            customer.mPhone = phone;

            String paymentInfo = txtPaymentInfo.getText();
            if (paymentInfo.equals("")) {
                JOptionPane.showMessageDialog(null, "PaymentInfo cannot be empty!!");
                return;
            }
            customer.mPaymentInfo = paymentInfo;

            JOptionPane.showMessageDialog(null, "You want to add " + customer);
            switch (StoreManager.getInstance().getDataAdapter().saveCustomer(customer)) {
                case SQLiteDataAdapter.PRODUCT_SAVED_FAILED:
                    JOptionPane.showMessageDialog(null, "Customer NOT added successfully! Duplicate Customer ID!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Customer added successfully!" + customer);
            }
        }
    }
}


