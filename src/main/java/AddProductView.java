import javax.swing.*;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductView extends  JFrame{
    public JButton button = new JButton("Add");
    public JButton cancel = new JButton("Cancel");

    public JTextField txtProductID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);

    public AddProductView() {
        this.setTitle("Add Product");
        this.setSize(600,400);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        String[] labels = {"ProductId: ", "Name: ", "Price: ", "Quantity: "};
        int numPairs = labels.length;

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("ProductID "));
        line1.add(txtProductID);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name "));
        line2.add(txtName);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Price "));
        line3.add(txtPrice);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtQuantity);
//Create and populate the panel.
        this.getContentPane().add(line1);
        this.getContentPane().add(line2);
        this.getContentPane().add(line3);
        this.getContentPane().add(line4);
       // Container p = this.getContentPane();
       // for (String label : labels) {
           /* JLabel l = new JLabel(label, JLabel.TRAILING);
            p.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            p.add(textField);*/
        //}
        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(button);
        panel.add(cancel);
        this.getContentPane().add(panel);
//Lay out the panel.
        /*SpringUtilities.makeCompactGrid(p,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad*/
    }
}


