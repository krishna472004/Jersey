package com.krishna.App7.Model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "students")
public class Students {

    private List<Student> studentList;

    public Students() {}

    public Students(List<Student> studentList) {
        this.studentList = studentList;
    }

    @XmlElement(name = "student")
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
