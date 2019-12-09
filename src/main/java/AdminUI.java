import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI {

    public JFrame view;

    public JButton editProfile = new JButton("Edit Profile");
    public JButton btnConfigure = new JButton("Update User");
    public JButton btnAddUser = new JButton("Add User");

    public AdminUI(UserModel user) {
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Admin");
        view.setSize(1000,600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Welcome " + user.mName + "!");
        title.setFont(title.getFont().deriveFont(24.0f));
        view.getContentPane().add(title);

        JPanel panel = new JPanel((new FlowLayout()));
        panel.add(editProfile);
        panel.add(btnConfigure);
        panel.add(btnAddUser);

        view.getContentPane().add(panel);

        editProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditProfile ui = new EditProfile(user);
                ui.run();
            }
        });
        btnConfigure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SysConfig sysConfig = new SysConfig();
                sysConfig.run();
            }
        });
        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUserUI addUser = new AddUserUI();
                addUser.run();
            }
        });
    }
}
