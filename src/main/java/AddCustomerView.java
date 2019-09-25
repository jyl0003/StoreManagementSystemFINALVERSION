import javax.swing.*;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerView extends  JFrame{
    public JButton addButton = new JButton("Add");
    public JButton cancel = new JButton("Cancel");

    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);
    public JTextField txtPaymentInfo = new JTextField(20);

    public AddCustomerView() {
        this.setTitle("Add Customer");
        this.setSize(600,400);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
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
        this.getContentPane().add(line1);
        this.getContentPane().add(line2);
        this.getContentPane().add(line3);
        this.getContentPane().add(line4);
        this.getContentPane().add(line5);
        // Container p = this.getContentPane();
        // for (String label : labels) {
           /* JLabel l = new JLabel(label, JLabel.TRAILING);
            p.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            p.add(textField);*/
        //}
        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(addButton);
        panel.add(cancel);
        this.getContentPane().add(panel);
//Lay out the panel.
        /*SpringUtilities.makeCompactGrid(p,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad*/
    }
}


