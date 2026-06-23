package com.grade.dao;

import com.grade.model.College;
import com.grade.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollegeDAO {
    public List<College> getAll() {
        List<College> list = new ArrayList<>();
        String sql = "SELECT * FROM xueyuan";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new College(
                    rs.getString("学院编码"),
                    rs.getString("学院名称"),
                    rs.getInt("定编人数"),
                    rs.getInt("现定编人数"),
                    rs.getInt("院下属专业数")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(College college) {
        String sql = "INSERT INTO xueyuan VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, college.getId());
            pstmt.setString(2, college.getName());
            pstmt.setInt(3, college.getPlannedCount());
            pstmt.setInt(4, college.getActualCount());
            pstmt.setInt(5, college.getMajorCount());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM xueyuan WHERE 学院编码 = ?";
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
