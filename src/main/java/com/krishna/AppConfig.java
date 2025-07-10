package com.krishna;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

import com.krishna.App7.Controller.StudentController;
import com.krishna.App7.Filters.CORSFilter;
import com.krishna.pikachu.Tester;


public class AppConfig extends Application 
{
	@Override
    public Set<Class<?>> getClasses() 
    {
        Set<Class<?>> classes=new HashSet<>();
   
        classes.add(StudentController.class);
        classes.add(Tester.class);
        classes.add(CORSFilter.class);
        return classes;
    }
}
