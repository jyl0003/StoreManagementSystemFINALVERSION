import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierPurchaseView {

    public JFrame view;

    public JButton btnProduct = new JButton("Add Purchase");
    public JButton btnReport = new JButton("Update Purchase");

    public CashierPurchaseView() {
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Cashier");
        view.setSize(250, 250);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));
        view.setLocation(750, 250);
        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(btnProduct);
        panel.add(btnReport);

        view.getContentPane().add(panel);

        btnProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashierNewPurchase ui = new CashierNewPurchase();
                ui.run();
            }
        });
        btnReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashierUpdatePurchase ui = new CashierUpdatePurchase();
                ui.run();
            }
        });
    }
}

