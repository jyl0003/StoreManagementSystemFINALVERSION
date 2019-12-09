import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoginUI {
    public JFrame view;

    public JButton btnLogin = new JButton("Login");
    public JButton btnLogout = new JButton("Logout");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JPasswordField(20);

    Socket link;
    Scanner input;
    PrintWriter output;

    int accessToken;

    public LoginUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Login");
        view.setSize(300, 150);
        view.setLocation(750, 250);
        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Username"));
        line.add(txtUsername);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Password"));
        line.add(txtPassword);
        pane.add(line);

       JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(btnLogin);
        jPanel.add(btnLogout);
        view.getContentPane().add(jPanel);
        btnLogin.addActionListener(new LoginActionListener());

        btnLogout.addActionListener(new LogoutActionListener());


    }
    public static void main(String[] args) {
        //int port = 1000;
        LoginUI ui = new LoginUI();
        ui.view.setVisible(true);

    }
    class LoginActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            UserModel user = new UserModel();

            user.mUsername = txtUsername.getText();
            user.mPassword = txtPassword.getText();

            if (user.mUsername.length() == 0 || user.mPassword.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be null!");
                return;
            }

            Gson gson = new Gson();


            // SocketNetworkAdapter net = new SocketNetworkAdapter();
            // msg = net.exchange(msg, "localhost", 1000);
            try {
                Socket socket = new Socket("localhost", 1000);
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.LOGIN;
                msg.Username = user.mUsername;
                msg.Password = user.mPassword;
               // msg.data = user.mUsername;
                //msg.Password = user.mPassword;

                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED)
                    JOptionPane.showMessageDialog(null, "Invalid username or password! Access denied!");
                else {
                    msg.ssid = msg.code;
                    accessToken = msg.ssid;
                    JOptionPane.showMessageDialog(null, "Access granted with access token = " + accessToken);
                    view.setVisible(false);


                    user = gson.fromJson(msg.data, UserModel.class);
                    if (user.mUserType==UserModel.ADMIN) {
                        AdminUI ui = new AdminUI(user);
                        ui.view.setVisible(true);
                    }
                    else if (user.mUserType==UserModel.MANAGER) {
                        ManagerUI ui = new ManagerUI(user);
                        ui.view.setVisible(true);
                    }
                    else if (user.mUserType==UserModel.CASHIER) {
                        CashierUI ui = new CashierUI(user);
                        ui.view.setVisible(true);
                    }
                    else if (user.mUserType==UserModel.CUSTOMER) {
                        CustomerUI ui = new CustomerUI(user);
                        ui.view.setVisible(true);
                    }
                    System.out.println("User = " + user);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


                        /*if (user.mUserType == UserModel.MANAGER) {
                            ManagerUI ui = new ManagerUI();
                            ui.view.setVisible(true);
                        }
                        if (user.mUserType == UserModel.CASHIER) {
                            CashierUI ui = new CashierUI();
                            System.out.println("MainUI = " + ui);
                            ui.view.setVisible(true);
                        }*/
        }

    }

    class LogoutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                link = new Socket("localhost", 1000);
                input = new Scanner(link.getInputStream());
                output = new PrintWriter(link.getOutputStream(), true);

                output.println("LOGOUT");
                output.println(accessToken);
                int res = input.nextInt();
                System.out.println("Sent LOGOUT " + accessToken + " received " + res);

                if (res == 0)
                    JOptionPane.showMessageDialog(null, "Invalid token for logout!");
                else
                    JOptionPane.showMessageDialog(null, "Logout successfully = ");
            } catch (Exception eg) {
                eg.printStackTrace();
            }
        }
    }
}

