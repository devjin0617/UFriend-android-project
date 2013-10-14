package com.mobile.system.resource;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: 11. 9. 19 Time: 오후 6:22
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {

    private String student_no;
    private String user_id;
    private String name;
    private String department;
    private String major;
    private String stu_stat;
    private String main_div;
    private String year;
    private String term;
    private String password;
    private byte[] photo;

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStudent_no() {
        return student_no;
    }

    public void setStudent_no(String student_no) {
        this.student_no = student_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStu_stat() {
        return stu_stat;
    }

    public void setStu_stat(String stu_stat) {
        this.stu_stat = stu_stat;
    }

    public String getMain_div() {
        return main_div;
    }

    public void setMain_div(String main_div) {
        this.main_div = main_div;
    }
}
