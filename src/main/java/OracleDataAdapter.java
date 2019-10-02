public class OracleDataAdapter implements IDataAdapter {
    public int connect(String dbFile) {
        //...
        return OPEN_CONNECTION_OK;
    }

    public int disconnect() {
        // ...
        return CLOSE_CONNECTION_OK;

    }

    public ProductModel loadProduct(int id) {
        return null;
    }
    public int saveProduct(ProductModel model) {
        return PRODUCT_SAVED_OK;
    }
    public CustomerModel loadCustomer(int id) {
        return null;
    }
    public int saveCustomer(CustomerModel model) {
        return CUSTOMER_SAVED_OK;
    }
}
