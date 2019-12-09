import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SysConfig {
        public JFrame view;
        public JButton add = new JButton("Update User");
        public JButton cancel = new JButton("Cancel");

        public JTextField txtUsername = new JTextField(20);
        public JTextField txtPassword = new JTextField(20);
        public JTextField txtClass = new JTextField(20);

        public SysConfig() {
            this.view = new JFrame();
            view.setTitle("Update User Class");
            view.setSize(600, 400);
            view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

            JPanel line1 = new JPanel(new FlowLayout());
            line1.add(new JLabel("Username"));
            line1.add(txtUsername);
            view.getContentPane().add(line1);

            JPanel line2 = new JPanel(new FlowLayout());
            line2.add(new JLabel("Password"));
            line2.add(txtPassword);
            view.getContentPane().add(line2);

            JPanel line4 = new JPanel(new FlowLayout());
            line4.add(new JLabel("Class"));
            line4.add(txtClass);
            view.getContentPane().add(line4);

            JPanel panel = new JPanel((new FlowLayout()));
            panel.add(add);
            panel.add(cancel);
            view.getContentPane().add(panel);

            add.addActionListener(new AddButtonListener());
        }
        class AddButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserModel userModel = new UserModel();
                Gson gson = new Gson();
                String username = txtUsername.getText();
                if (username.equals("")) {
                    JOptionPane.showMessageDialog(null, "username cannot be null!!");
                    return;
                }
                try {
                    userModel.mUsername = username;
                } catch (Exception m) {
                    JOptionPane.showMessageDialog(null, "Username cannot be null!!");
                    return;
                }
                String password = txtPassword.getText();
                if (password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Password cannot be empty!");
                    return;
                }
                try {
                    userModel.mPassword = password;
                }
                catch (Exception a) {
                    JOptionPane.showMessageDialog(null, "Password cannot be null");
                }
                
                int type = Integer.parseInt(txtClass.getText());
                if (type < 0 || type > 3)  {
                    JOptionPane.showMessageDialog(null, "Invalid type");
                    return;
                }
                try {
                    userModel.mUserType = type;
                }
                catch (Exception p) {
                    JOptionPane.showMessageDialog(null, "Password cannot be null");
                }
                try {
                    Socket socket = new Socket("localhost", 1000);
                    Scanner input = new Scanner(socket.getInputStream());
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                    MessageModel msg = new MessageModel();
                    msg.code = MessageModel.UPDATE_USER;
                    msg.data = gson.toJson(userModel);

                    output.println(gson.toJson(msg));

                    msg = gson.fromJson(input.nextLine(), MessageModel.class);

                    if (msg.code == MessageModel.OPERATION_OK) {
                        JOptionPane.showMessageDialog(null, "User updated Successfully");
                    }
                /*
                output.println("PUT");
                output.println(product.mProductID);
                output.println(product.mName);
                output.println(product.mPrice);
                output.println(product.mQuantity);*/

                }
                catch (Exception m) {
                    m.printStackTrace();
                }
            }
        }
        public void run() {
            view.setVisible(true);
        }

}
