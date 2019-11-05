import java.sql.*;

public class SQLiteDataAdapter implements IDataAdapter {
    Connection conn = null;
    public int connect(String dbFile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbFile;

            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return OPEN_CONNECTION_FAILED;
        }
        return OPEN_CONNECTION_OK;
    }
    @Override
    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return  CLOSE_CONNECTION_FAILED;
        }
        return CLOSE_CONNECTION_OK;
    }
    @Override
    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductID, Name, Price, Quantity FROM Product WHERE ProductID = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }
    public int updateProduct(ProductModel product) {
        //ProductModel product = new ProductModel();
        int productID = product.mProductID;
        String name1 = product.mName;
        Double price = product.mPrice;
        Double quantity = product.mQuantity;
        try {
            String sql = "UPDATE Product" +
                    "     Set Name = ? , Price = ?, Quantity = ? " +
                    "     WHERE ProductID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name1);
            preparedStatement.setDouble(2, price);
            preparedStatement.setDouble(3, quantity);
            preparedStatement.setInt(4, productID);
            preparedStatement.executeUpdate();
           /* Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);*/
            /*product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");*/
            return PRODUCT_SAVED_OK;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return PRODUCT_SAVED_FAILED;
    }
    public String loadProductName(int id) {
        ProductModel productModel = new ProductModel();
        String name = "";
        try {
            String sql = "SELECT Name FROM Product WHERE ProductID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            name = rs.getString("Name");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return name;
    }
    public int saveProduct(ProductModel product) {
        try {
            String sql = "INSERT INTO Product(ProductId, Name, Price, Quantity) VALUES " + product;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_SAVED_FAILED;
        }

        return PRODUCT_SAVED_OK;
    }
    public CustomerModel loadCustomer(int id) {
        CustomerModel customerModel = new CustomerModel();

        try {
            String sql = "SELECT CustomerID, Name, Address, Phone, PaymentInfo FROM Customer WHERE CustomerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customerModel.mCustomerID = rs.getInt("CustomerID");
            customerModel.mName = rs.getString("Name");
            customerModel.mAddress = rs.getString("Address");
            customerModel.mPhone = rs.getString("Phone");
            customerModel.mPaymentInfo = rs.getString("PaymentInfo");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customerModel;
    }
    public String loadCustomerID_NAME(int id) {
        CustomerModel customerModel = new CustomerModel();
        String name = "";
        try {
            String sql = "SELECT Name FROM Customer WHERE CustomerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            name = rs.getString("Name");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return name;
    }
    public int updateCustomer(CustomerModel customerModel) {
        int customerID = customerModel.mCustomerID;
        String name1 = customerModel.mName;
        String address = customerModel.mAddress;
        String phone = customerModel.mPhone;
        String paymentInfo = customerModel.mPaymentInfo;
        try {
            String sql = "UPDATE Customer" +
                    "     Set Name = ? , Address = ?, Phone = ?, PaymentInfo = ?" +
                    "     WHERE CustomerID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name1);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, paymentInfo);
            preparedStatement.setInt(5, customerID);

            preparedStatement.executeUpdate();
           /* Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);*/
            /*product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");*/
            return CUSTOMER_SAVED_OK;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return CUSTOMER_SAVED_FAILED;
    }
    public String deleteCustomer(CustomerModel customerModel) {
        //CustomerModel customerModel = new CustomerModel();
        //int id
        String name = "";
        try {
            String sql = "DELETE FROM Customer WHERE  CustomerID = " + customerModel.mCustomerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //name = rs.getString("Name");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return name;
    }
    public int saveCustomer(CustomerModel customer) {
        try {
            String sql = "INSERT INTO Customer(CustomerID, Name, Address, Phone, PaymentInfo) VALUES " + customer;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_SAVED_FAILED;
        }

        return CUSTOMER_SAVED_OK;
    }
    public PurchaseModel loadPurchase(int id) {
        PurchaseModel purchaseModel = new PurchaseModel();

        try {
            String sql = "SELECT PurchaseID, ProductID, CustomerID, Quantity  FROM Purchase WHERE PurchaseID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            purchaseModel.mProductID = rs.getInt("CustomerID");
            purchaseModel.mPurchaseId = rs.getInt("Name");
            purchaseModel.mCustomerID = rs.getInt("Address");
            purchaseModel.mQuantity = rs.getInt("Quantity");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return purchaseModel;
    }
    public String savePurchase(PurchaseModel purchaseModel) {
        try {
            String sql = "INSERT INTO Purchase(PurchaseID, ProductID, CustomerID, Quantity) VALUES " + purchaseModel;
            System.out.println(sql);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        }
        catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("constraint failed"))
                return msg;
        }
        return "Purchase Added Successfully!";
    }

}
