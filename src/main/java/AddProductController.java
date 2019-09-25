import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductController {
//    AddButtonListener addButtonListener = new AddButtonListener();
//    CancelButtonListener cancelButtonListener = new CancelButtonListener();
    public AddProductView view;
    public SQLiteDataAdapter adapter;

    public AddProductController(AddProductView view, SQLiteDataAdapter adapter)   {
        this.view = view;
        this.adapter = adapter;

        this.view.button.addActionListener(new AddButtonListener());
        this.view.cancel.addActionListener(new CancelButtonListener());

    }
    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            String id = AddProductController.this.view.txtProductID.getText();
            if (id.equals("")) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!!");
                return;
            }
            try {
                product.mProductID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!!");
                return;
            }
            // product.mProductID = Integer.parseInt(AddProductController.this.view.txtProductID.getText());
            String name = AddProductController.this.view.txtName.getText();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty!!");
                return;
            }
            product.mName = name;

            String price = AddProductController.this.view.txtPrice.getText();
            try {
                product.mPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is Invalid!!");
                return;
            }

            String quantity = AddProductController.this.view.txtQuantity.getText();
            try {
                product.mQuantity = Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is Invalid!!");
                return;
            }
            JOptionPane.showMessageDialog(null, "You want to add " + product);
            switch (adapter.saveProduct(product)) {
                case SQLiteDataAdapter.PRODUCT_SAVED_FAILED:
                    JOptionPane.showMessageDialog(null, "Product NOT added successfully! Duplicate product ID!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Product added successfully!" + product);
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