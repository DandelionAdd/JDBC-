package com.grade.dao;

import com.grade.model.Course;
import com.grade.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public List<Course> getAll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM kechengxinxi";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Course(
                    rs.getString("课程编码"),
                    rs.getString("课程名称"),
                    rs.getString("开设学期"),
                    rs.getString("开设学年"),
                    rs.getInt("学分数"),
                    rs.getInt("计划学时数"),
                    rs.getInt("实验时数"),
                    rs.getInt("周学时数"),
                    rs.getString("课程性质"),
                    rs.getString("考试类别"),
                    rs.getString("专业编码"),
                    rs.getString("备注")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(Course course) {
        String sql = "INSERT INTO kechengxinxi VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getId());
            pstmt.setString(2, course.getName());
            pstmt.setString(3, course.getSemester());
            pstmt.setString(4, course.getAcademicYear());
            pstmt.setInt(5, course.getCredits());
            pstmt.setInt(6, course.getPlannedHours());
            pstmt.setInt(7, course.getLabHours());
            pstmt.setInt(8, course.getWeeklyHours());
            pstmt.setString(9, course.getNature());
            pstmt.setString(10, course.getExamCategory());
            pstmt.setString(11, course.getMajorId());
            pstmt.setString(12, course.getRemark());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM kechengxinxi WHERE 课程编码 = ?";
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
