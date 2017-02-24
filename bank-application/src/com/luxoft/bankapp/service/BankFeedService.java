package com.luxoft.bankapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class BankFeedService {

	
	public void loadFeed (String folderName) throws IOException 
	{
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		
		String lineReadFromStream;
		
		for (int i=0; i < listOfFiles.length; i++)
		{
			 File realFile = new File(listOfFiles[i].getCanonicalPath());
			if(realFile.isDirectory()) {continue;}
				
  try (FileReader fr = new FileReader(realFile);
	   BufferedReader br = new BufferedReader (fr); )
            {
	            while ( (lineReadFromStream=br.readLine()) !=null ) 
	            {
	        	 parseLine(lineReadFromStream);                       
	            }
            }					
		}
	}
	private void parseLine(String line)
	{
		Map <String,String> feed = new HashMap<>();
		String[] substringArray = line.split(";");
		for (String s : substringArray)
		{			
			String [] smallPieces = s.split("=");
			feed.put(smallPieces[0], smallPieces[1]);
		}
		BankCommander.currentBank.parseFeed(feed);
	}
}
