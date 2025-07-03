package com.krishna.App7.Filters;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
public class MaskFilter implements Filter
{

	@Override
	public boolean isLoggable(LogRecord record) 
	{
		String message=record.getMessage();
		
		if(!message.contains("@"))
		{
			return true;
		}
		
		String words[]=message.split(" ");
		String res="";
		int ind;
		
		for(String word:words)
		{
			if(word.contains("@"))
			{
				ind=word.indexOf("@");
				res+=word.substring(0,2)+"*".repeat(ind-2)+word.substring(ind);
				res+=" ";
				continue;
			}
			res+=word;
			res+=" ";
			
		}
		record.setMessage(res);
		return true;
	}

}
