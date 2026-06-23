package com.grade.dao;

import com.grade.model.Teacher;
import com.grade.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    public List<Teacher> getAll() {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM jiaoshixinxi";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Teacher(
                    rs.getString("工作证号"),
                    rs.getString("教师姓名"),
                    rs.getString("职称"),
                    rs.getString("家庭住址"),
                    rs.getString("联系电话"),
                    rs.getString("现任职务"),
                    rs.getString("电子邮箱"),
                    rs.getString("专业编码")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(Teacher teacher) {
        String sql = "INSERT INTO jiaoshixinxi VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teacher.getId());
            pstmt.setString(2, teacher.getName());
            pstmt.setString(3, teacher.getTitle());
            pstmt.setString(4, teacher.getAddress());
            pstmt.setString(5, teacher.getPhone());
            pstmt.setString(6, teacher.getPosition());
            pstmt.setString(7, teacher.getEmail());
            pstmt.setString(8, teacher.getMajorId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM jiaoshixinxi WHERE 工作证号 = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
