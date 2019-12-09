import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
            return CLOSE_CONNECTION_FAILED;
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

    public ProductModel loadProductTable(String name, Double price ) {
        ProductModel product = new ProductModel();
        //ArrayList<String, String, String, String> list =  new ArrayList<>();
        ArrayList<String> summary  = new ArrayList<>();
        String summaryLine;
        JTable table;
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Product Name ");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity ");

        try {
            String sql = "SELECT ProductID, Quantity FROM Product WHERE Name = " + "\"" + name + "\"" + " AND Price = " + price;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            DecimalFormat df = new DecimalFormat("$###,###,###.00");

            product.mProductID = rs.getInt("ProductID");
            product.mQuantity = rs.getInt("Quantity");
            product.mName = name;
            product.mPrice = price;;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public CustomerModel loadCustomer(int id) {
        CustomerModel customerModel = new CustomerModel();

        try {
            String sql = "SELECT CustomerID, Username, Name, Address, Phone, PaymentInfo FROM Customer WHERE CustomerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customerModel.mCustomerID = rs.getInt("CustomerID");
            customerModel.mUsername = rs.getString("Username");
            customerModel.mName = rs.getString("Name");
            customerModel.mAddress = rs.getString("Address");
            customerModel.mPhone = rs.getString("Phone");
            customerModel.mPaymentInfo = rs.getString("PaymentInfo");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customerModel;
    }
    public CustomerModel loadCustomerUser(String username) {
        CustomerModel customerModel = new CustomerModel();

        try {
            System.out.println(username);
            String sql = "Select * from Customer where Username = " + "\"" + username + "\"";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customerModel.mCustomerID = rs.getInt("CustomerID");
            customerModel.mUsername = username;
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
            String sql = "INSERT INTO Customer(CustomerID, Username, Name, Address, Phone, PaymentInfo) VALUES " + customer;
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
            purchaseModel.mPurchaseId = rs.getInt("PurchaseID");
            purchaseModel.mProductID = rs.getInt("ProductID");
            purchaseModel.mCustomerID = rs.getInt("CustomerID");
            purchaseModel.mQuantity = rs.getInt("Quantity");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return purchaseModel;
    }
    //new method
    public JTable loadPurchase() {
        PurchaseModel purchaseModel;
        //ArrayList<String, String, String, String> list =  new ArrayList<>();
        ArrayList<String> summary  = new ArrayList<>();
        String summaryLine;
        JTable table;
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Purchase ID");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("Customer Name");
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Product Name ");
        tableModel.addColumn("Total Sale");

        try {
            String sql = "SELECT * FROM Purchase";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            DecimalFormat df = new DecimalFormat("$###,###,###.00");


            while (rs.next()) {
                CustomerModel customer = new CustomerModel();
                ProductModel product = new ProductModel();
                purchaseModel = new PurchaseModel();

                purchaseModel.mPurchaseId = rs.getInt("PurchaseID");
                purchaseModel.mProductID = rs.getInt("ProductID");
                purchaseModel.mCustomerID = rs.getInt("CustomerID");
                purchaseModel.mQuantity = rs.getInt("Quantity");

                customer = loadCustomer(purchaseModel.mCustomerID);
                product = loadProduct(purchaseModel.mProductID);

                double costNoTax = product.mPrice * purchaseModel.mQuantity;
                double tax = costNoTax * .10;
                //double cost = product.mPrice * purchase.mQuantity));
                //double costNoTax = productModel.mPrice * purchase.mQuantity;
                double totalCost = costNoTax + tax;
                String cost = df.format(totalCost);

                tableModel.addRow(new Object[]{purchaseModel.mPurchaseId, customer.mCustomerID, customer.mName, product.mProductID, product.mName, cost});
               /*summaryLine = "PurchaseID: " + purchaseModel.mPurchaseId
                        + "\t\t          CustomerID: " + purchaseModel.mCustomerID +
                        "  \t\t          Customer Name: " + customer.mName
                        + "\t\t          ProductID: " + product.mProductID
                        + "\t\t          Product Name: " + product.mName
                        + "\t\t          Total Sale: " + cost;*/


                //summary.add(summaryLine);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //table = new JTable(tableModel);
        return new JTable(tableModel);
    }
    public JTable loadCustomerPurchase(CustomerModel customer) {
        PurchaseModel purchaseModel;
        //ArrayList<String, String, String, String> list =  new ArrayList<>();
        ArrayList<String> summary  = new ArrayList<>();
        String summaryLine;
        JTable table;
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Purchase ID");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("Customer Name");
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Product Name ");
        tableModel.addColumn("Total Price");

        try {
            String sql = "SELECT * FROM Purchase WHERE CustomerID = " + customer.mCustomerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            DecimalFormat df = new DecimalFormat("$###,###,###.00");


            while (rs.next()) {
                ProductModel product = new ProductModel();
                purchaseModel = new PurchaseModel();

                purchaseModel.mPurchaseId = rs.getInt("PurchaseID");
                purchaseModel.mProductID = rs.getInt("ProductID");
                purchaseModel.mCustomerID = rs.getInt("CustomerID");
                purchaseModel.mQuantity = rs.getInt("Quantity");

                //customer = loadCustomer(purchaseModel.mCustomerID);
                product = loadProduct(purchaseModel.mProductID);

                double costNoTax = product.mPrice * purchaseModel.mQuantity;
                double tax = costNoTax * .10;
                //double cost = product.mPrice * purchase.mQuantity));
                //double costNoTax = productModel.mPrice * purchase.mQuantity;
                double totalCost = costNoTax + tax;
                String cost = df.format(totalCost);

                tableModel.addRow(new Object[]{purchaseModel.mPurchaseId, customer.mCustomerID, customer.mName, product.mProductID, product.mName, cost});
               /*summaryLine = "PurchaseID: " + purchaseModel.mPurchaseId
                        + "\t\t          CustomerID: " + purchaseModel.mCustomerID +
                        "  \t\t          Customer Name: " + customer.mName
                        + "\t\t          ProductID: " + product.mProductID
                        + "\t\t          Product Name: " + product.mName
                        + "\t\t          Total Sale: " + cost;*/


                //summary.add(summaryLine);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        table = new JTable(tableModel);
        return table;
    }
    public int updatePurchase(PurchaseModel purchaseModel) {
        int purchaseID = purchaseModel.mPurchaseId;
        int productID = purchaseModel.mProductID;
        int customerID = purchaseModel.mCustomerID;
        double quantity = purchaseModel.mQuantity;
        //String paymentInfo = customerModel.mPaymentInfo;
        try {
            String sql = "UPDATE Purchase" +
                    "     Set ProductID = ?, CustomerID = ?, Quantity = ?" +
                    "     WHERE PurchaseID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, productID);
            preparedStatement.setInt(2, customerID);
            preparedStatement.setDouble(3, quantity);
            preparedStatement.setInt(4, purchaseID);
            //  preparedStatement.setInt(5, customerID);

            preparedStatement.executeUpdate();
           /* Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);*/
            /*product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");*/
            return PURCHASE_SAVED_OK;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return PURCHASE_SAVED_FAILED;
    }
    public int savePurchase(PurchaseModel purchaseModel) {
        try {
            String sql = "INSERT INTO Purchase(PurchaseID, ProductID, CustomerID, Quantity) VALUES " + purchaseModel;
            System.out.println(sql);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_SAVED_FAILED;
        }
        return PURCHASE_SAVED_OK;
    }

    public UserModel loadUser(String username, String password) {
        UserModel user = null;
        try {
            String sql = "SELECT * FROM Users WHERE UserName = \"" + username + "\"" + "AND PassWord = \"" + password + "\"";
                         //   "AND PassWord =" + password;
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                user = new UserModel();
                user.mUsername = username;
                user.mPassword = rs.getString("PassWord");
                user.mName = rs.getString("Name");
                user.mUserType = rs.getInt("Type");
            }/*
            user.mUsername = rs.getString("UserName");
            user.mPassword = rs.getString("PassWord");
            user.mName = rs.getString("Name");
            user.mUserType = rs.getInt("Type");*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public int saveUser(UserModel userModel) {
        try {
            String sql = "INSERT INTO Users(UserName, PassWord, Name, Type) VALUES " + userModel;
            System.out.println(sql);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("constraint failed"))
                return 0;
        }
        return 1;
    }
    public int updateUser(UserModel userModel) {
        String username = userModel.mUsername;
        String password = userModel.mPassword;
        int type = userModel.mUserType;
        try {
            String sql = "UPDATE Users" +
                    "     Set Type = ?" +
                    "     WHERE UserName = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, type);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
           /* Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);*/
            /*product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");*/
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int editUser(UserModel userModel) {
        String username = userModel.mUsername;
        String password = userModel.mPassword;
        String name = userModel.mName;
        try {
            String sql = "UPDATE Users" +
                    "     Set PassWord = ?, Name = ?" +
                    "     WHERE UserName = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, username);

            preparedStatement.executeUpdate();
           /* Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);*/
            /*product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");*/
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
