import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class StoreServer {
     static String DB_FILE = "C:\\Users\\jlee9\\StoreManagementSystem\\Activity10.db";

    public static void main(String[] args) {
        // HashMap<Integer, String> loginUsers = new HashMap<>();
        int port = 1000;
        if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            DB_FILE = args[1];
        }
        try {
            SQLiteDataAdapter sqLiteDataAdapter = new SQLiteDataAdapter();
            Gson gson = new Gson();
            sqLiteDataAdapter.connect(DB_FILE);
            ServerSocket server = new ServerSocket(port);

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                MessageModel msg = gson.fromJson(in.nextLine(), MessageModel.class);
               // String command = in.nextLine();
                if (msg.code == MessageModel.GET_PRODUCT) {
                    System.out.println("GET product with id = " + msg.data);
                    ProductModel p = sqLiteDataAdapter.loadProduct(Integer.parseInt(msg.data));
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(p);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.SAVE_PRODUCT) {
                    System.out.println("PUT product with id = " + msg.data);
                    ProductModel productModel = gson.fromJson(msg.data, ProductModel.class);
                    int res = sqLiteDataAdapter.saveProduct(productModel);
                    if (res == IDataAdapter.PRODUCT_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                    //   String username = in.nextLine();
                    // String password = in.nextLine();
                  /*  String str = in.nextLine();
                    System.out.println("GET product with id = " + str);
                    int productID = Integer.parseInt(str);
                    Connection conn = null;
                    try {
                        String url = "jdbc:sqlite:" + DB_FILE;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM Product WHERE ProductID = " + productID;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            out.println(rs.getString("Name"));
                            out.println(rs.getDouble("Price"));
                            out.println(rs.getDouble("Quantity"));
                        } else {
                            out.println("null");
                        }
                        //conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    conn.close();
                }*/
                if (msg.code == MessageModel.UPDATE_PRODUCT) {
                    ProductModel p = gson.fromJson(msg.data, ProductModel.class);
                    System.out.println("Update command with Product = " + p);
                    //int res = sqLiteDataAdapter.saveProduct(p);
                    int res = sqLiteDataAdapter.updateProduct(p);
                    if (res == IDataAdapter.PRODUCT_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.GET_CUSTOMER) {
                    System.out.println("GET customer with id = " + msg.data);
                    CustomerModel p = sqLiteDataAdapter.loadCustomer(Integer.parseInt(msg.data));
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(p);
                    }
                    out.println(gson.toJson(msg));
                }
                if (msg.code == MessageModel.SAVE_CUSTOMER) {
                    CustomerModel customer = gson.fromJson(msg.data, CustomerModel.class);
                    System.out.println("PUT command with Customer = " + customer);
                    //sqLiteDataAdapter.deleteCustomer(p);
                    int res = sqLiteDataAdapter.saveCustomer(customer);
                    if (res == IDataAdapter.CUSTOMER_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }
                if (msg.code == MessageModel.UPDATE_CUSTOMER) {
                    CustomerModel customer = gson.fromJson(msg.data, CustomerModel.class);
                    System.out.println("Update command with Customer = " + customer);
                    //sqLiteDataAdapter.deleteCustomer(p);
                    int res = sqLiteDataAdapter.updateCustomer(customer);
                    if (res == IDataAdapter.CUSTOMER_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }
            }
                    /*
                    String id = in.nextLine();  // read all information from client
                    String name = in.nextLine();
                    String price = in.nextLine();
                    String quantity = in.nextLine();
                    int id1 = Integer.parseInt(id);
                    double price1 = Double.parseDouble(price);
                    double quanitity1 = Double.parseDouble(quantity);
                    System.out.println("PUT command with ProductID = " + id);

                    try {
                        String url = "jdbc:sqlite:" + DB_FILE;
                        Connection conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM Product WHERE ProductID = " + id;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            rs.close();
                            stmt.execute("DELETE FROM Product WHERE ProductID = " + id);
                        }
                        sql = "INSERT INTO Product VALUES (" + id + ",\"" + name + "\","
                                + price + "," + quantity + ")";
                        System.out.println("SQL for PUT: " + sql);
                        stmt.execute(sql);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //    }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}


    }
}