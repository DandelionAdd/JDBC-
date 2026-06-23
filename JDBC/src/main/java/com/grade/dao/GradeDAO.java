package com.grade.dao;

import com.grade.model.Grade;
import com.grade.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    public List<Grade> getAll() {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT * FROM xueshengchengji";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Grade(
                    rs.getString("学生学号"),
                    rs.getString("课程编码"),
                    rs.getString("工作证号"),
                    rs.getInt("补考标志"),
                    rs.getInt("最终成绩"),
                    rs.getString("选课学期"),
                    rs.getString("选课学年"),
                    rs.getDate("课程注册日期")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(Grade grade) {
        String sql = "INSERT INTO xueshengchengji VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grade.getStudentId());
            pstmt.setString(2, grade.getCourseId());
            pstmt.setString(3, grade.getTeacherId());
            pstmt.setInt(4, grade.getIsResit());
            pstmt.setInt(5, grade.getScore());
            pstmt.setString(6, grade.getSemester());
            pstmt.setString(7, grade.getAcademicYear());
            pstmt.setDate(8, grade.getEnrollDate());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String studentId, String courseId) {
        String sql = "DELETE FROM xueshengchengji WHERE 学生学号 = ? AND 课程编码 = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, courseId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getStatistics() {
        return getStatistics(null);
    }

    public String getStatistics(String studentId) {
        String baseSql = "SELECT " +
                     "(SELECT COUNT(*) FROM xueshengxinxi) as stu_count, " +
                     "(SELECT COUNT(*) FROM jiaoshixinxi) as tea_count, " +
                     "(SELECT COUNT(*) FROM kechengxinxi) as course_count, " +
                     "AVG(最终成绩) as avg_score, " +
                     "MAX(最终成绩) as max_score, " +
                     "MIN(最终成绩) as min_score " +
                     "FROM xueshengchengji";
        
        if (studentId != null) {
            baseSql += " WHERE 学生学号 = ?";
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(baseSql)) {
            
            if (studentId != null) {
                pstmt.setString(1, studentId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String prefix = (studentId != null) ? "我的成绩统计 - " : "系统概览 - ";
                    return prefix + String.format("学生:%d | 教师:%d | 课程:%d | 平均分:%.1f | 最高:%d | 最低:%d",
                            rs.getInt("stu_count"),
                            rs.getInt("tea_count"),
                            rs.getInt("course_count"),
                            rs.getDouble("avg_score"),
                            rs.getInt("max_score"),
                            rs.getInt("min_score"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "数据统计加载失败";
    }
    public List<Grade> getGradesByStudentId(String studentId) {
    List<Grade> list = new ArrayList<>();
    String sql = "SELECT * FROM xueshengchengji WHERE 学生学号 = ? ORDER BY 选课学年, 选课学期";
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, studentId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Grade(
                    rs.getString("学生学号"),
                    rs.getString("课程编码"),
                    rs.getString("工作证号"),
                    rs.getInt("补考标志"),
                    rs.getInt("最终成绩"),
                    rs.getString("选课学期"),
                    rs.getString("选课学年"),
                    rs.getDate("课程注册日期")
                ));
            }
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
}
}
