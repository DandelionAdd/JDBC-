package com.grade.dao;

import com.grade.model.Student;
import com.grade.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM xueshengxinxi";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(
                    rs.getString("学生学号"),
                    rs.getInt("学生性别"),
                    rs.getString("学生姓名"),
                    rs.getString("家庭住址"),
                    rs.getString("邮政编码"),
                    rs.getString("通信地址"),
                    rs.getString("电子邮箱"),
                    rs.getString("联系电话"),
                    rs.getDate("出生年月"),
                    rs.getString("班级号"),
                    rs.getString("专业编码")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(Student student) {
        String sql = "INSERT INTO xueshengxinxi VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getId());
            pstmt.setInt(2, student.getGender());
            pstmt.setString(3, student.getName());
            pstmt.setString(4, student.getAddress());
            pstmt.setString(5, student.getZipCode());
            pstmt.setString(6, student.getContactAddress());
            pstmt.setString(7, student.getEmail());
            pstmt.setString(8, student.getPhone());
            pstmt.setDate(9, student.getBirthDate());
            pstmt.setString(10, student.getClassNo());
            pstmt.setString(11, student.getMajorId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM xueshengxinxi WHERE 学生学号 = ?";
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
