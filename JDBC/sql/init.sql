-- 成绩管理系统数据库设计 (新版 7张表结构)
CREATE DATABASE IF NOT EXISTS zuoye424 CHARACTER SET utf8mb4;
USE zuoye424;

-- 0. 用户表 (用于系统登录，不在 7 张主表内但运行必须)
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    role ENUM('admin', 'teacher', 'student') NOT NULL
);
INSERT IGNORE INTO users VALUES ('admin', '123456', 'admin');
INSERT IGNORE INTO users VALUES ('teacher1', '123456', 'teacher');
INSERT IGNORE INTO users VALUES ('S001', '123456', 'student');

-- 1. 学院表
CREATE TABLE xueyuan (
    学院编码 VARCHAR(20) NOT NULL PRIMARY KEY,
    学院名称 VARCHAR(50) NOT NULL,
    定编人数 INT,
    现定编人数 INT,
    院下属专业数 INT
);

-- 2. 专业表
CREATE TABLE zhuanye (
    专业编码 VARCHAR(20) NOT NULL PRIMARY KEY,
    专业名称 VARCHAR(20) NOT NULL,
    学院编码 VARCHAR(20) NOT NULL,
    FOREIGN KEY (学院编码) REFERENCES xueyuan(学院编码)
);

-- 3. 教师信息表
CREATE TABLE jiaoshixinxi (
    工作证号 VARCHAR(20) NOT NULL PRIMARY KEY,
    教师姓名 VARCHAR(20) NOT NULL,
    职称 VARCHAR(20) NOT NULL,
    家庭住址 VARCHAR(50) NOT NULL,
    联系电话 VARCHAR(20) NOT NULL,
    现任职务 VARCHAR(20) NOT NULL,
    电子邮箱 VARCHAR(20) NOT NULL,
    专业编码 VARCHAR(20) NOT NULL,
    FOREIGN KEY (专业编码) REFERENCES zhuanye(专业编码)
);

-- 4. 学生信息表
CREATE TABLE xueshengxinxi(
    学生学号 VARCHAR(20) NOT NULL PRIMARY KEY,
    学生性别 TINYINT(1) NOT NULL,
    学生姓名 VARCHAR(20) NOT NULL,
    家庭住址 VARCHAR(50) NOT NULL,
    邮政编码 VARCHAR(20) NOT NULL,
    通信地址 VARCHAR(50) NOT NULL,
    电子邮箱 VARCHAR(20) NOT NULL,
    联系电话 VARCHAR(20) NOT NULL,
    出生年月 DATE NOT NULL,
    班级号 VARCHAR(20) NOT NULL,
    专业编码 VARCHAR(20) NOT NULL,
    FOREIGN KEY (专业编码) REFERENCES zhuanye(专业编码)
);

-- 5. 课程信息表
CREATE TABLE kechengxinxi (
    课程编码 VARCHAR(20) NOT NULL PRIMARY KEY,
    课程名称 VARCHAR(20) NOT NULL,
    开设学期 VARCHAR(20) NOT NULL,
    开设学年 VARCHAR(20) NOT NULL,
    学分数 INT NOT NULL,
    计划学时数 INT NOT NULL,
    实验时数 INT NOT NULL,
    周学时数 INT NOT NULL,
    课程性质 VARCHAR(20) NOT NULL,
    考试类别 VARCHAR(20) NOT NULL,
    专业编码 VARCHAR(20) NOT NULL,
    备注 TEXT NULL,
    FOREIGN KEY (专业编码) REFERENCES zhuanye(专业编码)
);

-- 6. 学生成绩表
CREATE TABLE xueshengchengji (
    学生学号 VARCHAR(20) NOT NULL,
    课程编码 VARCHAR(20) NOT NULL,
    工作证号 VARCHAR(20) NOT NULL,
    补考标志 TINYINT(1) NOT NULL,
    最终成绩 INT NOT NULL,
    选课学期 VARCHAR(20) NOT NULL,
    选课学年 VARCHAR(20) NOT NULL,
    课程注册日期 DATE NOT NULL,
    FOREIGN KEY (学生学号) REFERENCES xueshengxinxi(学生学号),
    FOREIGN KEY (课程编码) REFERENCES kechengxinxi(课程编码),
    FOREIGN KEY (工作证号) REFERENCES jiaoshixinxi(工作证号),
    PRIMARY KEY (学生学号, 课程编码)
);

-- 7. 教学任务表
CREATE TABLE jiaoxuerenwu(
    课程编码 VARCHAR(20) NOT NULL,
    工作证号 VARCHAR(20) NOT NULL,
    实际开设学年 VARCHAR(20) NOT NULL,
    实际开设学期 VARCHAR(20) NOT NULL,
    完成课程情况 VARCHAR(20) NOT NULL,
    FOREIGN KEY (课程编码) REFERENCES kechengxinxi(课程编码),
    FOREIGN KEY (工作证号) REFERENCES jiaoshixinxi(工作证号),
    PRIMARY KEY (课程编码, 工作证号)
);

-- 初始测试数据
INSERT IGNORE INTO xueyuan VALUES ('XY001', '计算机学院', 100, 80, 5);
INSERT IGNORE INTO zhuanye VALUES ('ZY001', '软件工程', 'XY001');
INSERT IGNORE INTO jiaoshixinxi VALUES ('T001', '李老师', '教授', '某街道1号', '13800000000', '系主任', 'li@test.com', 'ZY001');
INSERT IGNORE INTO xueshengxinxi VALUES ('S001', 1, '张三', '某住宅', '100000', '某地址', 'zhang@test.com', '13900000001', '2004-01-01', '2101', 'ZY001');
INSERT IGNORE INTO kechengxinxi VALUES ('C001', 'JAVA开发', '第一学期', '2023', 4, 64, 32, 4, '必修', '考试', 'ZY001', '核心课');
INSERT IGNORE INTO xueshengchengji VALUES ('S001', 'C001', 'T001', 0, 95, '第一学期', '2023', '2023-09-01');
INSERT IGNORE INTO jiaoxuerenwu VALUES ('C001', 'T001', '2023', '第一学期', '进行中');
