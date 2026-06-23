package com.grade.dao;

import com.grade.model.Major;
import com.grade.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO {
    public List<Major> getAll() {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT * FROM zhuanye";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Major(
                    rs.getString("专业编码"),
                    rs.getString("专业名称"),
                    rs.getString("学院编码")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(Major major) {
        String sql = "INSERT INTO zhuanye VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, major.getId());
            pstmt.setString(2, major.getName());
            pstmt.setString(3, major.getCollegeId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM zhuanye WHERE 专业编码 = ?";
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
