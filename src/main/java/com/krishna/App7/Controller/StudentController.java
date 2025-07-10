package com.krishna.App7.Controller;
import com.krishna.App7.Service.StudentService;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishna.App7.Filters.MaskFilter;
import com.krishna.App7.Model.Student;
import com.krishna.App7.Model.Students;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBException;
import com.krishna.App7.util.*;
import com.sun.xml.txw2.Document;
@Path("students")
public class StudentController
{
	
	static StudentService service;
	
	static
	{
		try
		{
			LogInitializer.init();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	static Logger logger=Logger.getLogger(StudentController.class.getName());
	

	static
	{
		try
		{
			logger.setFilter(new MaskFilter());
			service=new StudentService();
		
		}
		catch(Exception e)
		{
			logger.log(Level.SEVERE,"Service intialization failed: {0}",e.getMessage());
		}
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getStudents(@Context HttpHeaders header) throws SQLException, JsonMappingException, JsonProcessingException, JAXBException
	{
		logger.info("Fetching all Students");
		List<Student> list;
		String json="";
		String xml="";
		String key="students";
		String accept=header.getHeaderString("Accept");
		
		if(accept.equals("application/json"))
		{
			key+="-json";
		}
		else
		{
			key+="-xml";
		}
		
		if(RedisUtil.exists(key))
		{
			logger.info("Cache hit for fetching of all students");
			
			if(accept.equals("application/json"))
			{
				json=RedisUtil.get(key);
				list=service.convertJsonToStudents(json);
			}
			else
			{
				xml=RedisUtil.get(key);
				list=service.convertXmlToStudents(xml);
			}
		}
		else
		{
			logger.info("Cache miss. Fetching all students from DB");
			list=service.findAll();
			
			logger.info("Adding students data in cache...");
			
			if(accept.equals("application/json"))
			{
				RedisUtil.set(key, service.convertStudentsToJson(list));
			}
			else
			{
				RedisUtil.set(key, service.convertStudentsToXml(list));
			}
		}
	
		logger.log(Level.INFO,"Returned {0} students",list.size());

		return Response.status(Response.Status.OK).entity(new Students(list)).build();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/{id}")
	public Response getStudent(@PathParam("id") int id, @Context HttpHeaders header) throws SQLException, JAXBException, ParserConfigurationException, SAXException, IOException
	{
		String accept=header.getHeaderString("Accept");
		String key="student:"+id;
		Student student;
		String json="";
		String xml="";

		
		if(accept.equals("application/json"))
		{
			key+="-json";
		}
		else
		{
			key+="-xml";
		}
		
		logger.log(Level.INFO,"Fetching student with id: {0}",id);
		  
		if(RedisUtil.exists(key))
		{
			logger.info("Cache hit for student with id: "+id);
			
			if(accept.equals("application/json"))
			{
				json=RedisUtil.get(key);
				student=service.convertJsonToStudent(json);

			}
			else
			{
				xml=RedisUtil.get(key);
				student=service.convertXmlToStudent(xml);

			}

		}
		else
		{
			student=service.find(id);
			logger.info("Cache miss. Fetching all students from DB");
			
			if(student.getId()==0)
			{
				logger.log(Level.WARNING,"Student with id: {0} is not found",id);
				return Response.status(Response.Status.NOT_FOUND).entity("The student id: "+id+" is not found").build();
			}
			
			if(accept.equals("application/json"))
			{
				RedisUtil.set(key,service.convertStudentToJson(student));
			}
			else
			{
				RedisUtil.set(key,service.convertStudentToXml(student));
			}
			
			RedisUtil.setExpiry(key,3600);
			
			logger.info("Student with id: "+id+" is added to cache");
		}
		
		logger.log(Level.INFO,"Student with id: {0} returned successfully",id);
		logger.log(Level.INFO,"The Student email is: "+service.find(id).getEmail());
		
		return Response.ok(student).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.MULTIPART_FORM_DATA})
	public Response createStudent(Student s,@Context HttpHeaders header) throws SQLException, JsonProcessingException, JAXBException
	{
		
		logger.log(Level.INFO,"Creating new student",s);
		
		boolean success=service.add(s);
		String key="student:"+s.getId();
		String contentType=header.getHeaderString("Content-Type");
		
		if(contentType.equals("application/json"))
		{
			key+="-json";
		}
		else
		{
			key+="-xml";
		}
		
		if(success)
		{
			logger.info("The student data is added to db and cache");
			
			if(RedisUtil.exists(key))
			{
				RedisUtil.del("students");
			}
			
			if(contentType.equals("application/json"))
			{
				RedisUtil.set(key,service.convertStudentToJson(s));
			}
			else
			{
				RedisUtil.set(key,service.convertStudentToXml(s));
			}

			RedisUtil.setExpiry(key,3600);
			
			return Response.status(Response.Status.CREATED).entity("The student data is added to db and cache").build();
		}
		
		logger.severe("Failed to add student");
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to add Student").build();
		
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.MULTIPART_FORM_DATA})
	public Response updateStudent(Student s,@Context HttpHeaders header) throws SQLException, JsonProcessingException, JAXBException
	{
		logger.log(Level.INFO,"Updating student with id: {0}",s.getId());
		
		String key="student:"+s.getId();
		Student temp=service.find(s.getId());
		String contentType=header.getHeaderString("Content-Type");
		
		if(contentType.equals("application/json"))
		{
			key+="-json";
		}
		else
		{
			key+="-xml";
		}
		
		if(temp.getId()==0)
		{
			logger.log(Level.WARNING,"Student with id: {0} is not found",s.getId());
			
			return Response.status(Response.Status.NOT_FOUND).entity("The student id: "+s.getId()+" is not found").build();
		}
		
		boolean success=service.update(s);
		
		if(success)
		{
			logger.log(Level.INFO,"Student with id: {0} is updated",s.getId());
			
			if(RedisUtil.exists(key))
			{
				RedisUtil.del(key);
			}
			
			if(contentType.equals("application/json"))
			{
				RedisUtil.set(key,service.convertStudentToJson(s));
			}
			else
			{
				RedisUtil.set(key,service.convertStudentToXml(s));
			}
			
			RedisUtil.setExpiry(key,3600);
			RedisUtil.del("students-json");
			RedisUtil.del("students-xml");
			
			System.out.println(s);
			return Response.ok().entity("The student with id: "+s.getId()+" is updated").build();
		}
		
		logger.log(Level.SEVERE,"Update failed for student with id: {0}",s.getId());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Update failed").build();
		
		
	}
	
	@PATCH
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response update(Student s,@Context HttpHeaders header) throws SQLException, JsonProcessingException, JAXBException
	{
		
		logger.log(Level.INFO,"Updating student with id: {0}",s.getId());
		
		Student temp=service.find(s.getId());
		String key="student:"+s.getId();
		String contentType=header.getHeaderString("Content-Type");
		
		if(contentType.equals("application/json"))
		{
			key+="-json";
		}
		else
		{
			key+="-xml";
		}
		
		if(temp.getId()==0)
		{
			logger.log(Level.WARNING,"Student with id: {0} is not found",s.getId());
			return Response.status(Response.Status.NOT_FOUND).entity("The student id: "+s.getId()+" is not found").build();
		}

		boolean success=service.updateSome(s);
		
		if(success)
		{
			if(RedisUtil.exists(key))
			{
				RedisUtil.del(key);
			}
			
			if(contentType.equals("application/json"))
			{
				RedisUtil.set(key,service.convertStudentToJson(s));
			}
			else
			{
				RedisUtil.set(key,service.convertStudentToXml(s));
			}
			
			RedisUtil.setExpiry(key,3600);
			
			logger.log(Level.WARNING,"Student with id: {0} is updated",s.getId());
			return Response.ok().entity("The student with id: "+s.getId()+" is updated").build();
		}

		logger.log(Level.SEVERE,"Update failed for student with id: {0}",s.getId());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Update failed").build();
		
		
	}
	
	
	@DELETE
	@Path("/{id}")
	public Response deleteStudent(@PathParam("id") int id) throws SQLException
	{
		logger.log(Level.INFO,"Deleting student with id: {0}",id);
		
		Student s=service.find(id);
		boolean success;
		String key1="student:"+id+"-json";
		String key2="student:"+id+"-xml";
		
		if(s.getId()==0)
		{
			logger.log(Level.WARNING,"Student with id: {0} is not found",id);
			return Response.status(Response.Status.NOT_FOUND).entity("The student id: "+s.getId()+" is not found").build();
		}
		
		success=service.delete(id);
		
		if(success)
		{
			if(RedisUtil.exists("students-json"))
			{
				RedisUtil.del("students-json");
			}
			
			if(RedisUtil.exists("students-xml"))
			{
				RedisUtil.del("students-xml");
			}
			
			if(RedisUtil.exists(key1))
			{
				RedisUtil.del(key1);
			}
			
			if(RedisUtil.exists(key2))
			{
				RedisUtil.del(key2);
			}
		
			logger.log(Level.INFO,"Student with id: {0} is deleted",id);
			return Response.ok().entity("The student with id: "+id+" is deleted").build();
		}
		
		logger.log(Level.SEVERE,"Deletion failed for the student with id: {0}",id);
	
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Delete failed").build();
	}

}
