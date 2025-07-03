package com.krishna.App7.Model;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder= {"id","name","dob","email","marks"})
public class Student {
    
	private int id;
    private String name;

    private String dob;
    private String email;
    public String getEmail() {
		return email;
	}
    
    @XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	private int marks;

    //private List<Link> links=new ArrayList<>();
    
    public String getDob() {
		return dob;
	}
    @XmlElement
	public void setDob(String date) {
		this.dob = date;
	}
	public Student() {}
    public Student(int id,String name, int marks) {
    	this.id=id;
        this.name = name;
        this.marks = marks;
        
    }
   
//	public List<Link> getLinks() {
//		return links;
//	}
//	public void setLinks(List<Link> links) {
//		this.links = links;
//	}
	public int getId() {
		return id;
	}
	 @XmlElement
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
        return name;
    }
	 @XmlElement
    public void setName(String name) {
        this.name = name;
    }


    
	public int getMarks() {
        return marks;
    }
	@XmlElement
    public void setMarks(int marks) {
        this.marks = marks;
    }

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", dob=" + dob + ", email=" + email + ", marks=" + marks + "]";
	}
    
	
//    public void addLink(String rel,String href)
//    {
//    	links.add(new Link(rel,href));
//    }
}
