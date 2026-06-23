package com.grade;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import com.formdev.flatlaf.FlatLightLaf;
import com.grade.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // 启用现代化的皮肤 (FlatLaf)
        FlatLightLaf.setup();
        
        // 设置全局中文字体和大小，让界面更舒适
        Font globalFont = new Font("Microsoft YaHei", Font.PLAIN, 15);
        UIManager.put("defaultFont", globalFont);
        
        // 启动图形化通用界面
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
