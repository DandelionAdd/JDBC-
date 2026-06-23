package com.grade.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.grade.dao.UserDAO;
import com.grade.model.User;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("成绩管理系统 - 登录");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("用户名:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("密  码:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnLogin = new JButton("登录");
        btnLogin.addActionListener(this::handleLogin);
        add(btnLogin, gbc);
    }

    private void handleLogin(ActionEvent e) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        User user = userDAO.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "登录成功！角色: " + user.getRole());
            this.dispose();
            // 登录成功后打开主界面
            new MainFrame(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
