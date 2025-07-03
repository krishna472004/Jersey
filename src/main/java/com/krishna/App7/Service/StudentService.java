package com.krishna.App7.Service;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishna.App7.Model.Student;
import com.krishna.App7.Model.Students;
import com.krishna.App7.Repository.StudentRepo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class StudentService 
{
	static StudentRepo repo;
	static
	{
		try 
		{
			repo=new StudentRepo();
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
	}
	
	
	public String convertStudentToXml(Student s) throws JAXBException
	{
		JAXBContext context=JAXBContext.newInstance(Student.class);
		Marshaller marshaller=context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer=new StringWriter();
		marshaller.marshal(s, writer);
		
		return writer.toString();
	}
	
	public Student convertXmlToStudent(String xml) throws JAXBException
	{
		
		JAXBContext context=JAXBContext.newInstance(Student.class);
		Unmarshaller unmarshaller=context.createUnmarshaller();
		StringReader reader=new StringReader(xml);
		
		return (Student)unmarshaller.unmarshal(reader);
	}
	
	public String convertStudentToJson(Student s) throws JsonProcessingException
	{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(s);
	}
	
	public Student convertJsonToStudent(String json) throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.readValue(json, Student.class);
	}
	
	public String convertStudentsToJson(List<Student> list) throws JsonProcessingException
	{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(list);
	}
	
	public List<Student> convertJsonToStudents(String json) throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.readValue(json, new TypeReference<List<Student>>() {});
	}
	
	public String convertStudentsToXml(List<Student> list) throws JAXBException
	{
		Students students=new Students(list);
		JAXBContext context=JAXBContext.newInstance(Students.class);
		Marshaller marshaller=context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer=new StringWriter();
		marshaller.marshal(students, writer);
		
		return writer.toString();
	}
	
	public List<Student> convertXmlToStudents(String xml) throws JAXBException
	{
		JAXBContext context=JAXBContext.newInstance(Students.class);
		Unmarshaller unmarshaller=context.createUnmarshaller();
		StringReader reader=new StringReader(xml);
		Students students=(Students) unmarshaller.unmarshal(reader);
		
		return students.getStudentList();
	}
	
	public List<Student> findAll() throws SQLException
	{
		return repo.getAll();
	}
	
	public boolean add(Student s) throws SQLException
	{
		return repo.add(s);
	}
	
	public Student find(int id) throws SQLException
	{
		return repo.find(id);
	}
	
	public boolean update(Student s) throws SQLException
	{
		return repo.update(s);
	}
	
	public boolean delete(int id) throws SQLException
	{
		return repo.delete(id);
	}
	
	public boolean updateSome(Student s) throws SQLException
	{
		return repo.updateSome(s);
	}
}
