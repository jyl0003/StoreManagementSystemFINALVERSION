import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerController {
    //    AddButtonListener addButtonListener = new AddButtonListener();
//    CancelButtonListener cancelButtonListener = new CancelButtonListener();
    public AddCustomerView view;
    public SQLiteDataAdapter adapter;

    public AddCustomerController(AddCustomerView view, SQLiteDataAdapter adapter)   {
        this.view = view;
        this.adapter = adapter;

        this.view.addButton.addActionListener(new AddButtonListener());
        this.view.cancel.addActionListener(new CancelButtonListener());

    }
    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customerModel = new CustomerModel();
            String id = AddCustomerController.this.view.txtCustomerID.getText();
            if (id.equals("")) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!!");
                return;
            }
            try {
                customerModel.mCustomerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!!");
                return;
            }
            // product.mProductID = Integer.parseInt(AddProductController.this.view.txtProductID.getText());
            String name = AddCustomerController.this.view.txtName.getText();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty!!");
                return;
            }
            customerModel.mName = name;

            String address = AddCustomerController.this.view.txtAddress.getText();
            try {
                customerModel.mAddress = address;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Address is Invalid!!");
                return;
            }

            String phone = AddCustomerController.this.view.txtPhone.getText();
            try {
                customerModel.mPhone = phone;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Phone is Invalid!!");
                return;
            }
            String paymentInfo = AddCustomerController.this.view.txtPaymentInfo.getText();
            try {
                customerModel.mPaymentInfo = paymentInfo;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Payment Info is Invalid!!");
                return;
            }

            JOptionPane.showMessageDialog(null, "You want to add " + customerModel);

            switch (adapter.saveCustomer(customerModel)) {
                case SQLiteDataAdapter.PRODUCT_SAVED_FAILED:
                    JOptionPane.showMessageDialog(null, "Product NOT added successfully! Duplicate product ID!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Product added successfully!" + customerModel);
            }
        }
    }

    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(null, "You click on Cancel button!!!");
        }
    }
}