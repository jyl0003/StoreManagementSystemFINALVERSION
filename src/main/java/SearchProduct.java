import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SearchProduct {
    //CUSTOMER FUNCTION THAT ALLOWS CUSTOMER TO SEARCH FOR PRODUCT
    public JFrame view;
    JPanel jPanel = new JPanel();
    static String DB_FILE = "C:\\Users\\jlee9\\StoreManagementSystem\\Activity10.db";

    public JButton btnLoad = new JButton("Search Product");

    public JTextField txtName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);

    public JLabel txtProductID = new JLabel("");
    public JLabel txtQuantity = new JLabel("");

    public SearchProduct() {
        this.view = new JFrame();
        view.setTitle("Search Product Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));
        // String[] labels = {"ProductId: ", "Name: ", "Price: ", "Quantity: "};
        //int numPairs = labels.length;

        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(btnLoad);
        view.getContentPane().add(panel);


        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name "));
        line2.add(txtName);
        view.getContentPane().add(line2);
        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Price "));
        line3.add(txtPrice);
        view.getContentPane().add(line3);


        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("ProductID "));
        line1.add(txtProductID);
        view.getContentPane().add(line1);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtQuantity);
        view.getContentPane().add(line4);


        btnLoad.addActionListener(new LoadButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
          //  Gson gson = new Gson();
            /*String id = txtProductID.getText();
            if (id.equals("")) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!!");
                return;
            }
            try {
                product.mProductID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!!");
                return;
            }*/

            SQLiteDataAdapter sqLiteDataAdapter = new SQLiteDataAdapter();
            sqLiteDataAdapter.connect(DB_FILE);
            String name = txtName.getText();
            String quantity = txtQuantity.getText();
           /* if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "Name cannot be null!!");
               // return;
            }
            else if (quantity.equals("")) {
                JOptionPane.showMessageDialog(null, "Name cannot be null!!");
               // return;
            }*/
            //else {
                product = sqLiteDataAdapter.loadProductTable(txtName.getText(), Double.parseDouble(txtPrice.getText()));
                //            //JScrollPane sp = new JScrollPane(summaryList);
                if (product.mProductID == 0) {
                    JOptionPane.showMessageDialog(null, "Product Does not EXIST");
                    txtProductID.setText("");
                    txtQuantity.setText("");
                } else {
                    txtProductID.setText(Integer.toString(product.mProductID));
                    txtQuantity.setText(Double.toString(product.mQuantity));
                    sqLiteDataAdapter.disconnect();
               }
            //}
            //DO CLIENT/SERBER SIDE
            // product.mProductID = Integer.parseInt(AddProductController.this.view.txtProductID.getText());
           /* try {
                Socket socket = new Socket("localhost", 1000);
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_PRODUCT;
                msg.data = Integer.toString(product.mProductID);
                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Product NOT exists!");
                } else {
                    product = gson.fromJson(msg.data, ProductModel.class);
                    if (product == null) {
                        JOptionPane.showMessageDialog(null, "Product ID DOES NOT exists!");
                    }
                    txtName.setText(product.mName);
                    txtPrice.setText(Double.toString(product.mPrice));
                    txtQuantity.setText(Double.toString(product.mQuantity));
                }
                /*output.println("GET");
                output.println(product.mProductID);
*/
                /*product.mName = input.nextLine();

                if (product.mName.equals("null")) {
                    JOptionPane.showMessageDialog(null, "Product NOT exists!");
                    return;
                }

                txtName.setText(product.mName);

                product.mPrice = input.nextDouble();
                txtPrice.setText(Double.toString(product.mPrice));

                product.mQuantity = input.nextDouble();
                txtQuantity.setText(Double.toString(product.mQuantity));*/

            /*} catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }
}
