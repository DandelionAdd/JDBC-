package com.grade.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.List;
import java.sql.Date;
import com.grade.dao.*;
import com.grade.model.*;

public class MainFrame extends JFrame {
    private User currentUser;
    private CollegeDAO collegeDAO = new CollegeDAO();
    private MajorDAO majorDAO = new MajorDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private GradeDAO gradeDAO = new GradeDAO();
    private TeachingTaskDAO teachingTaskDAO = new TeachingTaskDAO();

    // 表格和模型
    private JTable collegeTable, majorTable, teacherTable, studentTable, courseTable, gradeTable, teachingTaskTable;
    private DefaultTableModel collegeTableModel, majorTableModel, teacherTableModel, studentTableModel, courseTableModel, gradeTableModel, teachingTaskTableModel;
    private JLabel lblStats; // 新增统计标签

    // 当前数据列表
    private List<College> currentColleges;
    private List<Major> currentMajors;
    private List<Teacher> currentTeachers;
    private List<Student> currentStudents;
    private List<Course> currentCourses;
    private List<Grade> currentGrades;
    private List<TeachingTask> currentTeachingTasks;

    public MainFrame(User user) {
        this.currentUser = user;
        setTitle("成绩管理系统 - 欢迎您, " + user.getUsername() + " (" + user.getRole() + ")");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 初始化 UI
        initUI();
        
        // 加载数据
        refreshData();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        String role = currentUser.getRole();

        // 根据角色添加标签页
        if (role.equals("admin")) {
            tabbedPane.addTab("学院信息", createCollegePanel());
            tabbedPane.addTab("专业信息", createMajorPanel());
            tabbedPane.addTab("教师信息", createTeacherPanel());
            tabbedPane.addTab("学生信息", createStudentPanel());
            tabbedPane.addTab("课程信息", createCoursePanel());
            tabbedPane.addTab("成绩信息", createGradePanel());
            tabbedPane.addTab("教学任务", createTeachingTaskPanel());
        } else if (role.equals("teacher")) {
            tabbedPane.addTab("教师信息", createTeacherPanel());
            tabbedPane.addTab("学生信息", createStudentPanel());
            tabbedPane.addTab("课程信息", createCoursePanel());
            tabbedPane.addTab("成绩信息", createGradePanel());
            tabbedPane.addTab("教学任务", createTeachingTaskPanel());
        } else { // student
            tabbedPane.addTab("学生信息", createStudentPanel());
            tabbedPane.addTab("课程信息", createCoursePanel());
            tabbedPane.addTab("成绩信息", createGradePanel());
        }

        add(tabbedPane, BorderLayout.CENTER);

        // 底部状态栏美化 - 卡片式统计面板
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(240, 242, 245));
        statusPanel.setPreferredSize(new Dimension(getWidth(), 60));
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 223, 230)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // 左侧用户信息卡片
        JPanel userCard = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        userCard.setOpaque(false);
        JLabel lblUserIcon = new JLabel("👤");
        lblUserIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        JLabel lblUser = new JLabel(currentUser.getUsername() + " [" + role.toUpperCase() + "]");
        lblUser.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        lblUser.setForeground(new Color(48, 49, 51));
        userCard.add(lblUserIcon);
        userCard.add(lblUser);
        
        // 中间统计信息卡片
        JPanel statsCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        statsCard.setOpaque(false);
        lblStats = new JLabel(" 数据统计加载中... ");
        lblStats.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        lblStats.setForeground(new Color(64, 158, 255));
        statsCard.add(lblStats);
        
        statusPanel.add(userCard, BorderLayout.WEST);
        statusPanel.add(statsCard, BorderLayout.CENTER);
        
        add(statusPanel, BorderLayout.SOUTH);
        
        // 初始加载统计数据
        updateStatistics();
    }

    private void updateStatistics() {
        if (currentUser.getRole().equals("student")) {
            lblStats.setText(" " + gradeDAO.getStatistics(currentUser.getUsername()));
        } else {
            lblStats.setText(" " + gradeDAO.getStatistics());
        }
    }

    private JPanel createToolbar(String title, Runnable refreshAction, Runnable addAction, Runnable deleteAction) {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(new Color(245, 245, 245)); // 浅灰色背景
        
        JButton btnRefresh = new JButton("刷新数据");
        btnRefresh.setIcon(UIManager.getIcon("Tree.refreshIcon")); // 使用系统内置图标
        btnRefresh.addActionListener(e -> refreshAction.run());
        toolbar.add(btnRefresh);

        String role = currentUser.getRole();
        boolean canEdit = false;

        if (role.equals("admin")) {
            canEdit = true;
        } else if (role.equals("teacher")) {
            if (title.equals("成绩")) {
                canEdit = true;
            }
        }

        if (canEdit) {
            JButton btnAdd = new JButton("添加" + title);
            btnAdd.setBackground(new Color(63, 154, 108)); // 柔和的绿色
            btnAdd.setForeground(Color.WHITE);
            btnAdd.addActionListener(e -> addAction.run());
            toolbar.add(btnAdd);

            JButton btnDelete = new JButton("删除记录");
            btnDelete.setBackground(new Color(217, 83, 79)); // 柔和的红色
            btnDelete.setForeground(Color.WHITE);
            btnDelete.addActionListener(e -> deleteAction.run());
            toolbar.add(btnDelete);
        }
        
        // 添加装饰性的边框
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        return toolbar;
    }

    // --- 各个面板创建方法 ---

    private JPanel createCollegePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createToolbar("学院", this::refreshCollegeData, () -> showAddDialog("学院"), this::handleCollegeDelete), BorderLayout.NORTH);
        collegeTableModel = new DefaultTableModel(new String[]{"学院编码", "学院名称", "定编人数", "现定编人数", "院下属专业数"}, 0);
        collegeTable = new JTable(collegeTableModel);
        panel.add(new JScrollPane(collegeTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMajorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createToolbar("专业", this::refreshMajorData, () -> showAddDialog("专业"), this::handleMajorDelete), BorderLayout.NORTH);
        majorTableModel = new DefaultTableModel(new String[]{"专业编码", "专业名称", "学院编码"}, 0);
        majorTable = new JTable(majorTableModel);
        panel.add(new JScrollPane(majorTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTeacherPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createToolbar("教师", this::refreshTeacherData, () -> showAddDialog("教师"), this::handleTeacherDelete), BorderLayout.NORTH);
        teacherTableModel = new DefaultTableModel(new String[]{"工作证号", "教师姓名", "职称", "家庭住址", "联系电话", "现任职务", "电子邮箱", "专业编码"}, 0);
        teacherTable = new JTable(teacherTableModel);
        panel.add(new JScrollPane(teacherTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 创建工具栏
        JPanel toolbar = createToolbar("学生", this::refreshStudentData, () -> showAddDialog("学生"), this::handleStudentDelete);
        
        // 额外添加“分析成绩”按钮
        JButton btnChart = new JButton("分析成绩曲线");
        btnChart.setBackground(new Color(64, 158, 255)); // 蓝色
        btnChart.setForeground(Color.WHITE);
        btnChart.addActionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row != -1) {
                String studentId = (String) studentTable.getValueAt(row, 0);
                showGradeChart(studentId);
            } else {
                JOptionPane.showMessageDialog(this, "请先选择一名学生");
            }
        });
        toolbar.add(btnChart);
        
        panel.add(toolbar, BorderLayout.NORTH);
        
        studentTableModel = new DefaultTableModel(new String[]{"学生学号", "性别", "姓名", "家庭住址", "邮政编码", "通信地址", "电子邮箱", "联系电话", "出生年月", "班级号", "专业编码"}, 0);
        studentTable = new JTable(studentTableModel);
        
        // 双击学生行查看成绩曲线图
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = studentTable.getSelectedRow();
                    if (row != -1) {
                        String studentId = (String) studentTable.getValueAt(row, 0);
                        showGradeChart(studentId);
                    }
                }
            }
        });
        
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createToolbar("课程", this::refreshCourseData, () -> showAddDialog("课程"), this::handleCourseDelete), BorderLayout.NORTH);
        courseTableModel = new DefaultTableModel(new String[]{"课程编码", "课程名称", "开设学期", "开设学年", "学分数", "计划学时数", "实验时数", "周学时数", "课程性质", "考试类别", "专业编码", "备注"}, 0);
        courseTable = new JTable(courseTableModel);
        panel.add(new JScrollPane(courseTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGradePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createToolbar("成绩", this::refreshGradeData, () -> showAddDialog("成绩"), this::handleGradeDelete), BorderLayout.NORTH);
        gradeTableModel = new DefaultTableModel(new String[]{"学生学号", "课程编码", "工作证号", "补考标志", "最终成绩", "选课学期", "选课学年", "课程注册日期"}, 0);
        gradeTable = new JTable(gradeTableModel);
        
        // 美化表格渲染
        gradeTable.setRowHeight(30); // 增加行高
        gradeTable.setIntercellSpacing(new Dimension(10, 5)); // 增加单元格间距
        gradeTable.getTableHeader().setFont(new Font("Microsoft YaHei", Font.BOLD, 15)); // 表头加粗
        
        panel.add(new JScrollPane(gradeTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTeachingTaskPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createToolbar("教学任务", this::refreshTeachingTaskData, () -> showAddDialog("任务"), this::handleTeachingTaskDelete), BorderLayout.NORTH);
        teachingTaskTableModel = new DefaultTableModel(new String[]{"课程编码", "工作证号", "实际开设学年", "实际开设学期", "完成课程情况"}, 0);
        teachingTaskTable = new JTable(teachingTaskTableModel);
        panel.add(new JScrollPane(teachingTaskTable), BorderLayout.CENTER);
        return panel;
    }

    // --- 刷新数据逻辑 ---

    private void refreshData() {
        String role = currentUser.getRole();
        if (role.equals("admin")) {
            refreshCollegeData();
            refreshMajorData();
            refreshTeacherData();
            refreshStudentData();
            refreshCourseData();
            refreshGradeData();
            refreshTeachingTaskData();
        } else if (role.equals("teacher")) {
            refreshTeacherData();
            refreshStudentData();
            refreshCourseData();
            refreshGradeData();
            refreshTeachingTaskData();
        } else {
            refreshStudentData();
            refreshCourseData();
            refreshGradeData();
        }
    }

    private void refreshCollegeData() {
        if (collegeTableModel == null) return;
        collegeTableModel.setRowCount(0);
        currentColleges = collegeDAO.getAll();
        for (College c : currentColleges) {
            collegeTableModel.addRow(new Object[]{c.getId(), c.getName(), c.getPlannedCount(), c.getActualCount(), c.getMajorCount()});
        }
    }

    private void refreshMajorData() {
        if (majorTableModel == null) return;
        majorTableModel.setRowCount(0);
        currentMajors = majorDAO.getAll();
        for (Major m : currentMajors) {
            majorTableModel.addRow(new Object[]{m.getId(), m.getName(), m.getCollegeId()});
        }
    }

    private void refreshTeacherData() {
        if (teacherTableModel == null) return;
        teacherTableModel.setRowCount(0);
        currentTeachers = teacherDAO.getAll();
        for (Teacher t : currentTeachers) {
            teacherTableModel.addRow(new Object[]{t.getId(), t.getName(), t.getTitle(), t.getAddress(), t.getPhone(), t.getPosition(), t.getEmail(), t.getMajorId()});
        }
    }

    private void refreshStudentData() {
        if (studentTableModel == null) return;
        studentTableModel.setRowCount(0);
        currentStudents = studentDAO.getAll();
        
        String role = currentUser.getRole();
        String username = currentUser.getUsername();

        for (Student s : currentStudents) {
            // 如果是学生权限，且不是 admin，则只显示与用户名（学号）匹配的记录
            if (role.equals("student") && !s.getId().equalsIgnoreCase(username)) {
                continue;
            }
            studentTableModel.addRow(new Object[]{s.getId(), s.getGender() == 1 ? "男" : "女", s.getName(), s.getAddress(), s.getZipCode(), s.getContactAddress(), s.getEmail(), s.getPhone(), s.getBirthDate(), s.getClassNo(), s.getMajorId()});
        }
    }

    private void refreshCourseData() {
        if (courseTableModel == null) return;
        courseTableModel.setRowCount(0);
        currentCourses = courseDAO.getAll();
        for (Course c : currentCourses) {
            courseTableModel.addRow(new Object[]{c.getId(), c.getName(), c.getSemester(), c.getAcademicYear(), c.getCredits(), c.getPlannedHours(), c.getLabHours(), c.getWeeklyHours(), c.getNature(), c.getExamCategory(), c.getMajorId(), c.getRemark()});
        }
    }

    private void refreshGradeData() {
        if (gradeTableModel == null) return;
        gradeTableModel.setRowCount(0);
        currentGrades = gradeDAO.getAll();
        
        String role = currentUser.getRole();
        String username = currentUser.getUsername();

        for (Grade g : currentGrades) {
            // 如果是学生权限，只显示自己的成绩
            if (role.equals("student") && !g.getStudentId().equalsIgnoreCase(username)) {
                continue;
            }
            gradeTableModel.addRow(new Object[]{g.getStudentId(), g.getCourseId(), g.getTeacherId(), g.getIsResit() == 1 ? "是" : "否", g.getScore(), g.getSemester(), g.getAcademicYear(), g.getEnrollDate()});
        }
    }

    private void refreshTeachingTaskData() {
        if (teachingTaskTableModel == null) return;
        teachingTaskTableModel.setRowCount(0);
        currentTeachingTasks = teachingTaskDAO.getAll();
        for (TeachingTask t : currentTeachingTasks) {
            teachingTaskTableModel.addRow(new Object[]{t.getCourseId(), t.getTeacherId(), t.getActualYear(), t.getActualSemester(), t.getCompletionStatus()});
        }
    }

    private void showAddDialog(String type) {
        switch (type) {
            case "学院": showCollegeAddDialog(); break;
            case "专业": showMajorAddDialog(); break;
            case "教师": showTeacherAddDialog(); break;
            case "学生": showStudentAddDialog(); break;
            case "课程": showCourseAddDialog(); break;
            case "成绩": showGradeAddDialog(); break;
            case "教学任务": showTeachingTaskAddDialog(); break;
            default: JOptionPane.showMessageDialog(this, "添加" + type + "功能待开发");
        }
    }

    private void showCollegeAddDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField txtId = new JTextField();
        JTextField txtName = new JTextField();
        JTextField txtPlanned = new JTextField();
        JTextField txtActual = new JTextField();
        JTextField txtMajorCount = new JTextField();

        panel.add(new JLabel("学院编码:")); panel.add(txtId);
        panel.add(new JLabel("学院名称:")); panel.add(txtName);
        panel.add(new JLabel("定编人数:")); panel.add(txtPlanned);
        panel.add(new JLabel("现定编人数:")); panel.add(txtActual);
        panel.add(new JLabel("下属专业数:")); panel.add(txtMajorCount);

        if (JOptionPane.showConfirmDialog(this, panel, "添加学院", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                College c = new College(txtId.getText(), txtName.getText(), 
                    Integer.parseInt(txtPlanned.getText()), 
                    Integer.parseInt(txtActual.getText()), 
                    Integer.parseInt(txtMajorCount.getText()));
                if (collegeDAO.add(c)) { refreshCollegeData(); }
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "输入格式错误"); }
        }
    }

    private void showMajorAddDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField txtId = new JTextField();
        JTextField txtName = new JTextField();
        JTextField txtCollegeId = new JTextField();

        panel.add(new JLabel("专业编码:")); panel.add(txtId);
        panel.add(new JLabel("专业名称:")); panel.add(txtName);
        panel.add(new JLabel("学院编码:")); panel.add(txtCollegeId);

        if (JOptionPane.showConfirmDialog(this, panel, "添加专业", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Major m = new Major(txtId.getText(), txtName.getText(), txtCollegeId.getText());
            if (majorDAO.add(m)) refreshMajorData();
        }
    }

    private void showTeacherAddDialog() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
        JTextField txtId = new JTextField();
        JTextField txtName = new JTextField();
        JTextField txtTitle = new JTextField();
        JTextField txtPos = new JTextField();
        JTextField txtAddr = new JTextField();
        JTextField txtPhone = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtMajorId = new JTextField();

        panel.add(new JLabel("工作证号:")); panel.add(txtId);
        panel.add(new JLabel("姓名:")); panel.add(txtName);
        panel.add(new JLabel("职称:")); panel.add(txtTitle);
        panel.add(new JLabel("职务:")); panel.add(txtPos);
        panel.add(new JLabel("住址:")); panel.add(txtAddr);
        panel.add(new JLabel("电话:")); panel.add(txtPhone);
        panel.add(new JLabel("邮箱:")); panel.add(txtEmail);
        panel.add(new JLabel("专业编码:")); panel.add(txtMajorId);

        if (JOptionPane.showConfirmDialog(this, panel, "添加教师", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Teacher t = new Teacher(txtId.getText(), txtName.getText(), txtTitle.getText(), txtAddr.getText(), txtPhone.getText(), txtPos.getText(), txtEmail.getText(), txtMajorId.getText());
            if (teacherDAO.add(t)) refreshTeacherData();
        }
    }

    private void showStudentAddDialog() {
        JPanel panel = new JPanel(new GridLayout(6, 4, 5, 5));
        JTextField txtId = new JTextField();
        JTextField txtName = new JTextField();
        JComboBox<String> cbGender = new JComboBox<>(new String[]{"女", "男"});
        JTextField txtBirth = new JTextField("yyyy-mm-dd");
        JTextField txtClass = new JTextField();
        JTextField txtMajor = new JTextField();
        JTextField txtAddr = new JTextField();
        JTextField txtZip = new JTextField();
        JTextField txtContact = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtPhone = new JTextField();

        panel.add(new JLabel("学号:")); panel.add(txtId);
        panel.add(new JLabel("姓名:")); panel.add(txtName);
        panel.add(new JLabel("性别:")); panel.add(cbGender);
        panel.add(new JLabel("出生日期:")); panel.add(txtBirth);
        panel.add(new JLabel("班级:")); panel.add(txtClass);
        panel.add(new JLabel("专业编码:")); panel.add(txtMajor);
        panel.add(new JLabel("家庭住址:")); panel.add(txtAddr);
        panel.add(new JLabel("邮编:")); panel.add(txtZip);
        panel.add(new JLabel("通信地址:")); panel.add(txtContact);
        panel.add(new JLabel("电子邮箱:")); panel.add(txtEmail);
        panel.add(new JLabel("联系电话:")); panel.add(txtPhone);

        if (JOptionPane.showConfirmDialog(this, panel, "添加学生", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Student s = new Student(txtId.getText(), cbGender.getSelectedIndex(), txtName.getText(), txtAddr.getText(), txtZip.getText(), txtContact.getText(), txtEmail.getText(), txtPhone.getText(), Date.valueOf(txtBirth.getText()), txtClass.getText(), txtMajor.getText());
                if (studentDAO.add(s)) refreshStudentData();
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "日期格式错误(yyyy-mm-dd)"); }
        }
    }

    private void showGradeAddDialog() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
        JTextField txtSid = new JTextField();
        JTextField txtCid = new JTextField();
        JTextField txtTid = new JTextField();
        JTextField txtScore = new JTextField();
        JTextField txtYear = new JTextField();
        JTextField txtSem = new JTextField();
        JComboBox<String> cbResit = new JComboBox<>(new String[]{"否", "是"});
        JTextField txtDate = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        panel.add(new JLabel("学生学号:")); panel.add(txtSid);
        panel.add(new JLabel("课程编码:")); panel.add(txtCid);
        panel.add(new JLabel("教师工号:")); panel.add(txtTid);
        panel.add(new JLabel("成绩:")); panel.add(txtScore);
        panel.add(new JLabel("学年:")); panel.add(txtYear);
        panel.add(new JLabel("学期:")); panel.add(txtSem);
        panel.add(new JLabel("是否补考:")); panel.add(cbResit);
        panel.add(new JLabel("注册日期:")); panel.add(txtDate);

        if (JOptionPane.showConfirmDialog(this, panel, "添加成绩", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Grade g = new Grade(txtSid.getText(), txtCid.getText(), txtTid.getText(), cbResit.getSelectedIndex(), Integer.parseInt(txtScore.getText()), txtSem.getText(), txtYear.getText(), Date.valueOf(txtDate.getText()));
                if (gradeDAO.add(g)) {
                    refreshGradeData();
                    updateStatistics(); // 数据变化后更新统计
                }
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "输入格式错误"); }
        }
    }

    private void showTeachingTaskAddDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 5, 5));
        JTextField txtCid = new JTextField();
        JTextField txtTid = new JTextField();
        JTextField txtYear = new JTextField();
        JTextField txtSem = new JTextField();
        JTextField txtStatus = new JTextField();

        panel.add(new JLabel("课程编码:")); panel.add(txtCid);
        panel.add(new JLabel("教师工号:")); panel.add(txtTid);
        panel.add(new JLabel("开设学年:")); panel.add(txtYear);
        panel.add(new JLabel("开设学期:")); panel.add(txtSem);
        panel.add(new JLabel("完成情况:")); panel.add(txtStatus);

        if (JOptionPane.showConfirmDialog(this, panel, "添加教学任务", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            TeachingTask t = new TeachingTask(txtCid.getText(), txtTid.getText(), txtYear.getText(), txtSem.getText(), txtStatus.getText());
            if (teachingTaskDAO.add(t)) refreshTeachingTaskData();
        }
    }

    private void showCourseAddDialog() {
        JPanel panel = new JPanel(new GridLayout(6, 4, 5, 5));
        JTextField txtId = new JTextField();
        JTextField txtName = new JTextField();
        JTextField txtSemester = new JTextField();
        JTextField txtYear = new JTextField();
        JTextField txtCredits = new JTextField();
        JTextField txtPlanned = new JTextField();
        JTextField txtLab = new JTextField();
        JTextField txtWeekly = new JTextField();
        JTextField txtNature = new JTextField();
        JTextField txtExam = new JTextField();
        JTextField txtMajor = new JTextField();
        JTextField txtRemark = new JTextField();

        panel.add(new JLabel("课程编码:")); panel.add(txtId);
        panel.add(new JLabel("课程名称:")); panel.add(txtName);
        panel.add(new JLabel("开设学期:")); panel.add(txtSemester);
        panel.add(new JLabel("开设学年:")); panel.add(txtYear);
        panel.add(new JLabel("学分数:")); panel.add(txtCredits);
        panel.add(new JLabel("计划学时:")); panel.add(txtPlanned);
        panel.add(new JLabel("实验学时:")); panel.add(txtLab);
        panel.add(new JLabel("周学时:")); panel.add(txtWeekly);
        panel.add(new JLabel("课程性质:")); panel.add(txtNature);
        panel.add(new JLabel("考试类别:")); panel.add(txtExam);
        panel.add(new JLabel("专业编码:")); panel.add(txtMajor);
        panel.add(new JLabel("备注:")); panel.add(txtRemark);

        int result = JOptionPane.showConfirmDialog(this, panel, "添加课程信息", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Course c = new Course();
                c.setId(txtId.getText());
                c.setName(txtName.getText());
                c.setSemester(txtSemester.getText());
                c.setAcademicYear(txtYear.getText());
                c.setCredits(Integer.parseInt(txtCredits.getText()));
                c.setPlannedHours(Integer.parseInt(txtPlanned.getText()));
                c.setLabHours(Integer.parseInt(txtLab.getText()));
                c.setWeeklyHours(Integer.parseInt(txtWeekly.getText()));
                c.setNature(txtNature.getText());
                c.setExamCategory(txtExam.getText());
                c.setMajorId(txtMajor.getText());
                c.setRemark(txtRemark.getText());

                if (courseDAO.add(c)) {
                    JOptionPane.showMessageDialog(this, "课程添加成功！");
                    refreshCourseData();
                } else {
                    JOptionPane.showMessageDialog(this, "添加失败，请检查编码是否冲突或专业编码是否存在");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "输入格式错误，学分/学时等必须为数字");
            }
        }
    }

    private void handleCollegeDelete() {
        int row = collegeTable.getSelectedRow();
        if (row != -1) {
            String id = (String) collegeTableModel.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "确定删除学院 " + id + " 吗？") == JOptionPane.YES_OPTION) {
                if (collegeDAO.delete(id)) {
                    refreshCollegeData();
                    updateStatistics();
                }
            }
        }
    }

    private void handleMajorDelete() {
        int row = majorTable.getSelectedRow();
        if (row != -1) {
            String id = (String) majorTableModel.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "确定删除专业 " + id + " 吗？") == JOptionPane.YES_OPTION) {
                if (majorDAO.delete(id)) refreshMajorData();
            }
        }
    }

    private void handleTeacherDelete() {
        int row = teacherTable.getSelectedRow();
        if (row != -1) {
            String id = (String) teacherTableModel.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "确定删除教师 " + id + " 吗？") == JOptionPane.YES_OPTION) {
                if (teacherDAO.delete(id)) refreshTeacherData();
            }
        }
    }

    private void handleStudentDelete() {
        int row = studentTable.getSelectedRow();
        if (row != -1) {
            String id = (String) studentTableModel.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "确定删除学生 " + id + " 吗？") == JOptionPane.YES_OPTION) {
                if (studentDAO.delete(id)) refreshStudentData();
            }
        }
    }

    private void handleCourseDelete() {
        int row = courseTable.getSelectedRow();
        if (row != -1) {
            String id = (String) courseTableModel.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "确定删除课程 " + id + " 吗？") == JOptionPane.YES_OPTION) {
                if (courseDAO.delete(id)) refreshCourseData();
            }
        }
    }

    private void handleGradeDelete() {
        int row = gradeTable.getSelectedRow();
        if (row != -1) {
            String sid = (String) gradeTableModel.getValueAt(row, 0);
            String cid = (String) gradeTableModel.getValueAt(row, 1);
            if (JOptionPane.showConfirmDialog(this, "确定删除该成绩记录吗？") == JOptionPane.YES_OPTION) {
                if (gradeDAO.delete(sid, cid)) refreshGradeData();
            }
        }
    }

    private void handleTeachingTaskDelete() {
        int row = teachingTaskTable.getSelectedRow();
        if (row != -1) {
            String cid = (String) teachingTaskTableModel.getValueAt(row, 0);
            String tid = (String) teachingTaskTableModel.getValueAt(row, 1);
            if (JOptionPane.showConfirmDialog(this, "确定删除该教学任务吗？") == JOptionPane.YES_OPTION) {
                if (teachingTaskDAO.delete(cid, tid)) refreshTeachingTaskData();
            }
        }
    }
    private void showGradeChart(String studentId) {
        List<Grade> grades = gradeDAO.getGradesByStudentId(studentId);
        if (grades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "该学生暂无成绩记录");
            return;
        }

        // 创建数据集
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Grade g : grades) {
            dataset.addValue(g.getScore(), "分数", g.getAcademicYear() + " " + g.getSemester());
        }

        // 创建折线图
        JFreeChart lineChart = ChartFactory.createLineChart(
                "成绩趋势图 (学号: " + studentId + ")",
                "学年学期",
                "分数",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // 设置中文字体防止乱码
        Font font = new Font("Microsoft YaHei", Font.BOLD, 15);
        lineChart.getTitle().setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        lineChart.getLegend().setItemFont(font);
        lineChart.getCategoryPlot().getDomainAxis().setLabelFont(font);
        lineChart.getCategoryPlot().getDomainAxis().setTickLabelFont(font);
        lineChart.getCategoryPlot().getRangeAxis().setLabelFont(font);

        // 弹出窗口显示
        ChartPanel chartPanel = new ChartPanel(lineChart);
        JFrame chartFrame = new JFrame("学生成绩分析");
        chartFrame.add(chartPanel);
        chartFrame.setSize(800, 500);
        chartFrame.setLocationRelativeTo(this);
        chartFrame.setVisible(true);
    }
}
