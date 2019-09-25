public class StoreManager {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Hello class!");
        //Class.forName("org.sqlite.jdbc");
        SQLiteDataAdapter adapter = new SQLiteDataAdapter();
        adapter.connect();

        AddProductView apView = new AddProductView();
        AddProductController apCtr = new AddProductController(apView, adapter);
        apView.setVisible(true);
    }
}
