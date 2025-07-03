package com.krishna.App7.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import java.util.List;

import com.krishna.App7.Model.Student;
public class StudentRepo 
{


	private final String url="jdbc:postgresql://localhost:5432/student";
    private final String user="postgres";
    private final String pass="root";
    private Connection con=null;
    
	public StudentRepo() throws ClassNotFoundException, SQLException
	{
		
		Class.forName("org.postgresql.Driver");
		con=DriverManager.getConnection(url,user,pass);
		
	}
	
	public List<Student> getAll() throws SQLException
	{
		List<Student> list=new ArrayList<>();
		String query="select * from students";
		PreparedStatement st=con.prepareStatement(query);
		ResultSet rs=st.executeQuery();
		
		while(rs.next())
		{
			Student s=new Student();
			s.setId(rs.getInt(1));
			s.setName(rs.getString(2));
			s.setDob(rs.getDate(3).toString());
			s.setMarks(rs.getInt(4));
			s.setEmail(rs.getString(5));
			list.add(s);
		}
		
		return list;
	}

	public boolean add(Student s) throws SQLException 
	{
		String query="insert into students (id,name,dob,marks) values (?,?,?,?)";
		PreparedStatement st=con.prepareStatement(query);
		st.setInt(1, s.getId());
		st.setString(2, s.getName());
		st.setDate(3, Date.valueOf(s.getDob()));
		st.setInt(4, s.getMarks());
		
		return st.executeUpdate()!=0;

		
	}

	public Student find(int id) throws SQLException 
	{
		
		Student s=new Student();
		String query="select * from students where id=?";
		PreparedStatement st=con.prepareStatement(query);
		st.setInt(1, id);
		ResultSet rs=st.executeQuery();
		
		if(rs.next())
		{
			
			s.setId(rs.getInt(1));
			s.setName(rs.getString(2));
			s.setDob(rs.getDate(3).toString());
			s.setMarks(rs.getInt(4));
			s.setEmail(rs.getString(5));
		}
		
		return s;
	}
	
	public boolean update(Student s) throws SQLException
	{
		String query="update students set name=?,dob=?,marks=? where id=?";
		PreparedStatement st=con.prepareStatement(query);
		
		st.setString(1, s.getName());
		st.setDate(2, Date.valueOf(s.getDob()));
		st.setInt(3, s.getMarks());
		st.setInt(4, s.getId());
		
		return st.executeUpdate()!=0;
		
	}

	public boolean delete(int id) throws SQLException {
		String query="delete from students where id=?";
		PreparedStatement st=con.prepareStatement(query);
		st.setInt(1, id);
		return st.executeUpdate()!=0;
		
		
		
	}

	public boolean updateSome(Student s) throws SQLException 
	{
		StringBuilder query=new StringBuilder("update students set ");
		
		int ind=1;
		boolean needComma=false;
		if(s.getName()!=null)
		{
			query.append("name=?");
			needComma=true;
		}
		
		if(s.getDob()!=null)
		{
			if(needComma)
				query.append(",");
			
			query.append("dob=?");
			needComma=true;
		}
		
		if(s.getMarks()!=0)
		{
			if(needComma)
				query.append(",");
			
			query.append("marks=?");
			needComma=true;
		}
		
		query.append(" where id=?");
		PreparedStatement st=con.prepareStatement(query.toString());
		
		if(s.getName()!=null)
			st.setString(ind++, s.getName());
		
		if(s.getDob()!=null)
			st.setDate(ind++, Date.valueOf(s.getDob()));
		
		if(s.getMarks()!=0)
			st.setInt(ind++, s.getMarks());
		
		if(s.getId()!=0)
			st.setInt(ind, s.getId());
		else
			return false;
		
		
		return st.executeUpdate()!=0;
		
		
	}
}
