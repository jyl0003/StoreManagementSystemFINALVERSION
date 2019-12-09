import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUI {

    public JFrame view;

    public JButton editProfile = new JButton("Edit Profile");
    public JButton btnMakePurchase = new JButton("Make Purchase");
    public JButton btnPurchaseHistory = new JButton("Purchase History");
    public JButton btnCancelPurchase = new JButton("Search product");
    public CustomerModel customer;
    static String DB_FILE = "C:\\Users\\jlee9\\StoreManagementSystem\\Activity10.db";

    public CustomerUI(UserModel user) {
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Customer");
        view.setSize(1000,600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Welcome " + user.mName + "!", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(24.0f));
        view.getContentPane().add(title);

        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(editProfile);
        panel.add(btnMakePurchase);
        panel.add(btnPurchaseHistory);
        panel.add(btnCancelPurchase);

        view.getContentPane().add(panel);

        //customer = new CustomerModel();
        SQLiteDataAdapter sqLiteDataAdapter = new SQLiteDataAdapter();
        sqLiteDataAdapter.connect(DB_FILE);
        customer = sqLiteDataAdapter.loadCustomerUser(user.mUsername);
        //customer = sqLiteDataAdapter.loadCustomer(1);
       /* System.out.println(user.mUsername);
        System.out.println("HellO" + customer);*/
        sqLiteDataAdapter.disconnect();

        editProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditProfile ui = new EditProfile(user);
                ui.run();
            }
        });

        btnMakePurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MakePurchaseUI ui = new MakePurchaseUI(customer);
                ui.run();
            }
        });
        btnPurchaseHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PurchaseHistoryUI purchaseHistoryUI = new PurchaseHistoryUI(customer);
                purchaseHistoryUI.run();
            }
        });
        btnCancelPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchProduct ui = new SearchProduct();
                ui.run();
            }
        });
    }

}
