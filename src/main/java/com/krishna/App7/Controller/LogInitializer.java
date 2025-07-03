package com.krishna.App7.Controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.krishna.App7.Filters.MaskFilter;

public class LogInitializer {
	
    static 
    {
        try
        {
        	FileInputStream configFile = new FileInputStream("C:/Users/morth/eclipse-workspace/App7/logging.properties");
            LogManager.getLogManager().readConfiguration(configFile);
    		Logger logger=Logger.getLogger("");
    		
    		
    		for (Handler handler : logger.getHandlers())
    		{
    		    handler.setFormatter(new CustomFormatter());
    		}
        } 
        catch (IOException e) 
        {
            System.err.println(e.getMessage());
        }
    }

    public static void init()
    {
        
    }
}
