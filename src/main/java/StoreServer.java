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


                //PRODUCT
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
                //CUSTOMER
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
                //Purchase
                if (msg.code == MessageModel.LOAD_PURCHASE) {
                    System.out.println("GET command with Purchase = " + msg.data);
                    PurchaseModel purchase = sqLiteDataAdapter.loadPurchase(Integer.parseInt(msg.data));
                    if (purchase == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    } else {
                        msg.code = MessageModel.OPERATION_OK;
                        msg.data = gson.toJson(purchase);
                        msg.productData = gson.toJson(sqLiteDataAdapter.loadProduct(purchase.mProductID));
                        msg.customerData = gson.toJson(sqLiteDataAdapter.loadCustomer(purchase.mCustomerID));
                    }
                    out.println(gson.toJson(msg));
                }

                if(msg.code == MessageModel.UPDATE_PURCHASE) {
                    PurchaseModel purchaseModel = gson.fromJson(msg.data, PurchaseModel.class);
                    System.out.println("Update command with Purchase = " + purchaseModel);
                    int res = sqLiteDataAdapter.updatePurchase(purchaseModel);
                    if (res == IDataAdapter.PURCHASE_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                        msg.productData = gson.toJson(sqLiteDataAdapter.loadProduct(purchaseModel.mProductID));
                        msg.customerData = gson.toJson(sqLiteDataAdapter.loadCustomer(purchaseModel.mCustomerID));
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }
                if (msg.code == MessageModel.SAVE_PURCHASE) {
                    PurchaseModel purchase = gson.fromJson(msg.data, PurchaseModel.class);
                    System.out.println("PUT command with Purchase = " + purchase);
                    ProductModel productModel = sqLiteDataAdapter.loadProduct(purchase.mProductID);
                    CustomerModel customerModel = sqLiteDataAdapter.loadCustomer(purchase.mCustomerID);
                    //sqLiteDataAdapter.deleteCustomer(p);
                    int res = sqLiteDataAdapter.savePurchase(purchase);
                    if (res == IDataAdapter.PURCHASE_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                        msg.productData = gson.toJson(productModel);
                        msg.customerData = gson.toJson(customerModel);
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }
                //LOGIN AND USER
                if (msg.code == MessageModel.LOGIN) {
                   System.out.println("Login with username = " + msg.Username + "\tPassword: " + msg.Password);
                    UserModel user = sqLiteDataAdapter.loadUser(msg.Username, msg.Password);
                    if (user == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(user);
                    }
                    out.println(gson.toJson(msg));
                }
                if (msg.code == MessageModel.ADD_USER) {
                    UserModel userModel = gson.fromJson(msg.data, UserModel.class);
                    System.out.println("Create command with User = " + userModel);
                    //sqLiteDataAdapter.deleteCustomer(p);
                    int res = sqLiteDataAdapter.saveUser(userModel);
                    if (res == 1) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }
                if (msg.code == MessageModel.UPDATE_USER) {
                    UserModel userModel = gson.fromJson(msg.data, UserModel.class);
                    System.out.println("Update command with User = " + userModel);
                    //sqLiteDataAdapter.deleteCustomer(p);
                    int res = sqLiteDataAdapter.updateUser(userModel);
                    if (res == 1) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }
                if (msg.code == MessageModel.EDIT_USER) {
                    UserModel userModel = gson.fromJson(msg.data, UserModel.class);
                    System.out.println("EDIT command with User = " + userModel);
                    //sqLiteDataAdapter.deleteCustomer(p);
                    int res = sqLiteDataAdapter.editUser(userModel);
                    if (res == 1) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //}


    }
}