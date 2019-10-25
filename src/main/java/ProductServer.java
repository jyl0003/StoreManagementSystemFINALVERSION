import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class ProductServer {
     static final String DB_FILE = "C:\\Users\\jlee9\\StoreManagementSystem\\Activity10.db";

    public static void main(String[] args) {
        // HashMap<Integer, String> loginUsers = new HashMap<>();
        int port = 1000;
        /*if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            99dbfile = args[1];
        }*/
        try {
            ServerSocket server = new ServerSocket(port);

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                String command = in.nextLine();
                if (command.equals("GET")) {
                    //   String username = in.nextLine();
                    // String password = in.nextLine();
                    String str = in.nextLine();
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
                }
                if (command.equals("PUT")) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}


    }
}