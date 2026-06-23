package com.grade.dao;

import com.grade.model.TeachingTask;
import com.grade.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeachingTaskDAO {
    public List<TeachingTask> getAll() {
        List<TeachingTask> list = new ArrayList<>();
        String sql = "SELECT * FROM jiaoxuerenwu";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new TeachingTask(
                    rs.getString("课程编码"),
                    rs.getString("工作证号"),
                    rs.getString("实际开设学年"),
                    rs.getString("实际开设学期"),
                    rs.getString("完成课程情况")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(TeachingTask task) {
        String sql = "INSERT INTO jiaoxuerenwu VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getCourseId());
            pstmt.setString(2, task.getTeacherId());
            pstmt.setString(3, task.getActualYear());
            pstmt.setString(4, task.getActualSemester());
            pstmt.setString(5, task.getCompletionStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String courseId, String teacherId) {
        String sql = "DELETE FROM jiaoxuerenwu WHERE 课程编码 = ? AND 工作证号 = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            pstmt.setString(2, teacherId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
