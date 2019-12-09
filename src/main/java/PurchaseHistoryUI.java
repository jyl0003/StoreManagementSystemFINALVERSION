import javax.swing.*;

public class PurchaseHistoryUI {
    public JFrame view;

    JPanel jPanel = new JPanel();
    static String DB_FILE = "C:\\Users\\jlee9\\StoreManagementSystem\\Activity10.db";

    public  PurchaseHistoryUI(CustomerModel customer) {
        this.view = new JFrame();
        view.setTitle("Purchase History");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        SQLiteDataAdapter sqLiteDataAdapter = new SQLiteDataAdapter();
        sqLiteDataAdapter.connect(DB_FILE);
        //  ArrayList<String> list = sqLiteDataAdapter.loadPurchase();
        //String[] hello = {"" + sqLiteDataAdapter.loadPurchase() + ""};
        // list.add("I GOT HERE");
        // summaryList = new JList(list.toArray());
        //JList summaryList = new JList(hello);
        //summaryList.setLayoutOrientation(JList.VERTICAL_WRAP);
        JTable table = sqLiteDataAdapter.loadCustomerPurchase(customer);
        //JScrollPane sp = new JScrollPane(summaryList);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);
        jPanel.add(jScrollPane);
        view.getContentPane().add(jPanel);
        sqLiteDataAdapter.disconnect();
    }
    public void run() {
        view.setVisible(true);
    }

}
