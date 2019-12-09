import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerUI {
        public JFrame view;

        public JButton editProfile = new JButton("Edit Profile");
        public JButton btnProduct = new JButton("Add/Update Product");
        public JButton btnReport = new JButton("Summary Report");

        public ManagerUI(UserModel user) {
            this.view = new JFrame();
            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view.setTitle("Manager");
            view.setSize(1000,600);
            view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

            JLabel title = new JLabel("Welcome " + user.mName + "!");
            title.setFont(title.getFont().deriveFont(24.0f));
            view.getContentPane().add(title);

            JPanel panel = new JPanel((new FlowLayout()));
            panel.add(editProfile);
            panel.add(btnProduct);
            panel.add(btnReport);

            view.getContentPane().add(panel);

            editProfile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EditProfile ui = new EditProfile(user);
                    ui.run();
                }
            });

            btnProduct.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddProductView ui = new AddProductView();
                    ui.run();
                }
            });
            btnReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SummaryReport ui = new SummaryReport();
                    ui.run();
                }
            });
        }
    }

