import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EditProfile {
    public JFrame view;
    public UserModel user1;
    public JButton edit = new JButton("Save Changes");
    public JButton cancel = new JButton("Cancel");

    public JTextField txtPassword = new JPasswordField(20);
    public JTextField txtName = new JTextField(20);

    public JLabel labPassword = new JLabel("Password");
    public JLabel labName = new JLabel("Name");

    public EditProfile(UserModel user) {
        user1 = user;
        this.view = new JFrame();
        view.setTitle("Edit Profile");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        txtPassword.setText(user.mPassword);
        txtName.setText(user.mName);
        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("New Password"));
        line1.add(txtPassword);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Edit Name"));
        line2.add(txtName);
        view.getContentPane().add(line2);

        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(edit);
        panel.add(cancel);
        view.getContentPane().add(panel);

        edit.addActionListener(new AddButtonListener());
    }
    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            UserModel userModel = new UserModel();
            userModel = user1;
            Gson gson = new Gson();
            String password = txtPassword.getText();
            if (edit.equals("")) {
                JOptionPane.showMessageDialog(null, "password cannot be null!!");
                return;
            }
            try {
                userModel.mPassword = password;
            } catch (Exception m) {
                JOptionPane.showMessageDialog(null, "Username cannot be null!!");
                return;
            }
            String name = txtName.getText();
            if (password.equals("")) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!");
                return;
            }
            try {
                userModel.mName = name;
            }
            catch (Exception a) {
                JOptionPane.showMessageDialog(null, "Password cannot be null");
            }

            try {
                Socket socket = new Socket("localhost", 1000);
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.EDIT_USER;
                msg.data = gson.toJson(userModel);

                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);
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
